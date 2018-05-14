package mro.app.applyItem.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.applyItem.Utils.ApplyClassstructureUtils;
import mro.app.applyItem.bo.ApplyClassstructureBo;
import mro.app.applyItem.service.ApplyClassstructureInterface;
import mro.app.commonview.bo.FileUploadBO;
import mro.app.commonview.bo.ListAlndomainBO;
import mro.app.commonview.bo.ListClassspecBO;
import mro.app.commonview.vo.ListClassspecVO;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ClassstructureApplyType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.SourceCategory;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.AlndomainBO;
import mro.base.bo.AssetattributeBO;
import mro.base.bo.AttachmentBO;
import mro.base.bo.ClassstructureHeaderApplyBO;
import mro.base.bo.ClassstructureLineApplyBO;
import mro.base.bo.PersonBO;
import mro.base.entity.Alndomain;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ClassstructureLineApply;
import mro.base.entity.Person;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.base.workflow.utils.WorkflowUtils;
import mro.form.ClassstructureForm;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ApplyClassstructureImpl implements ApplyClassstructureInterface {

	private transient AlndomainBO alndomainBO;
	private transient ClassstructureHeaderApplyBO classstructureHeaderApplyBO;
	private transient ClassstructureLineApplyBO classstructureLineApplyBO;
	
	public ApplyClassstructureImpl(){
		alndomainBO=SpringContextUtil.getBean(AlndomainBO.class);
		classstructureHeaderApplyBO=SpringContextUtil.getBean(ClassstructureHeaderApplyBO.class);
		classstructureLineApplyBO=SpringContextUtil.getBean(ClassstructureLineApplyBO.class);
	}

	@Override
	public ClassstructureForm getApplyList(
			ClassstructureForm classstructureForm, Person person, int activeI) {
		classstructureForm.intial();
		classstructureForm.setListClassstructureHeaderApply(classstructureHeaderApplyBO
						.getApplyList(person.getPersonId(), ItemStatusType.TYPE_PROCESS_AC));
		classstructureForm.setActiveIndex(activeI);
		classstructureForm.setSignMap(SourceCategory.getInprgMap(
				classstructureForm.getListClassstructureHeaderApply()));
		return classstructureForm;
	}

	@Override
	public ClassstructureForm selectApply(
			ClassstructureForm classstructureForm,
			ClassstructureHeaderApply classstructureHeaderApply) {
		classstructureForm.intial();
		classstructureForm
				.setClassstructureHeaderApply(classstructureHeaderApply);
		PersonBO personBO = SpringContextUtil.getBean(PersonBO.class);
		AssetattributeBO assetattributeBO = SpringContextUtil.getBean(AssetattributeBO.class);
		
		classstructureForm.setListClassstructureLineApply(classstructureLineApplyBO.getList(
				classstructureHeaderApply.getApplyHeaderId()));
		// =============================簽核歷程=========================================================
		classstructureForm.setSignHistoryUrl(WorkflowActionUtils
				.onSignHistory(classstructureHeaderApply.getTaskId()));
		// =============================檔案下載====================================================
		getDownLoadFile(classstructureForm);
		// ============================Page===================================================
		classstructureForm.setActiveIndex(1);
		//=============================申請人=================================================
		classstructureForm.setPerson(personBO.getPerson(classstructureHeaderApply.getCreateBy()));
		classstructureForm.setAssetAttributeMap(
				assetattributeBO.getMap(classstructureHeaderApply.getApplyHeaderId(),
						ClassstructureLineApply.class));
		

		return classstructureForm;
	}

	@Override
	public ClassstructureForm onSignPreView(
			ClassstructureForm classstructureForm) {
		// 取得簽核url
		classstructureForm
				.setSignPreViewUrl(WorkflowActionUtils.onSignPreView(
						getSignParameter(classstructureForm)));
		return classstructureForm;
	}

	@Override
	public void onDelete(ClassstructureForm classstructureForm) {
		GlobalGrowl message = new GlobalGrowl();
		ApplyClassstructureBo applyClassstructureBo = SpringContextUtil
				.getBean(ApplyClassstructureBo.class);
		applyClassstructureBo.onDelteApply(classstructureForm
				.getClassstructureHeaderApply());
		message.addInfoMessage("刪除申請單", "刪除申請單 成功!");
	}

	@Override
	public boolean onApplySave(ClassstructureForm classstructureForm,
			String type) {
		GlobalGrowl message = new GlobalGrowl();
		ApplyClassstructureBo applyClassstructureBo = SpringContextUtil
				.getBean(ApplyClassstructureBo.class);
		String warnMessage = ApplyClassstructureUtils
				.vaildateHeader(classstructureForm
						.getClassstructureHeaderApply());
		if (warnMessage.length() == 0) { // 無錯誤訊息

			if (type.equals("save")) { // 儲存
				applyClassstructureBo.onApplySave(classstructureForm, type);
				message.addInfoMessage("Save", "Save successful.");
				return true;
			} else if (type.equals("submit")) {
				classstructureForm.getClassstructureHeaderApply().setTaskId(
						WorkflowActionUtils.onClassstructureApplySumit(
								classstructureForm.getClassstructureHeaderApply(),
								getSignParameter(classstructureForm)));
				if (classstructureForm.getClassstructureHeaderApply()
						.getTaskId() != null) { // 簽核成功
					applyClassstructureBo.onApplySave(classstructureForm, type);
					message.addInfoMessage("Submit", "Submit successful.");
					return true;
				} else {
					message.addErrorMessage("送審失敗", "請重新送審或與MIS聯繫!");
				}
			}
		} else {
			message.addErrorMessage("Error", warnMessage.toString());
		}
		return false;
	}

	@Override
	public ClassstructureForm setParameter(ClassstructureForm classstructureForm) {
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		classstructureForm.setActionOption(bean.getParameterOption().get(
				ParameterType.CLASSSTRUCTURE_APPLY_ACTION));
		return classstructureForm;
	}

	@Override
	public ClassstructureForm onAddLine(ClassstructureForm classstructureForm) {
		ClassstructureLineApply classstructureLineApply = new ClassstructureLineApply();
		if (classstructureForm.getListClassstructureLineApply() != null
				&& classstructureForm.getListClassstructureLineApply().size() > 0) {
			int size = classstructureForm.getListClassstructureLineApply()
					.size();
			ClassstructureLineApply lastClassstructureLineApply = classstructureForm
					.getListClassstructureLineApply().get(size - 1);
			classstructureLineApply.setLineNum(lastClassstructureLineApply
					.getLineNum().add(new BigDecimal("1")));
		} else {
			classstructureLineApply.setLineNum(new BigDecimal("1"));
		}
		classstructureForm.getListClassstructureLineApply().add(
				classstructureLineApply);

		return classstructureForm;
	}

	@Override
	public ClassstructureForm onDeleteLine(
			ClassstructureForm classstructureForm,
			ClassstructureLineApply classstructureLineApply) {
		int i = 1;
		classstructureForm.getListClassstructureLineApply().remove(
				classstructureLineApply);
		for (ClassstructureLineApply l : classstructureForm
				.getListClassstructureLineApply()) {
			l.setLineNum(new BigDecimal(i));
			i++;
		}
		return classstructureForm;
	}

	@Override
	public Map getAlnDomainMap(ClassstructureForm classstructureForm) {
		Map<String, List<Alndomain>> map = new LinkedHashMap<>();
		ListAlndomainBO listAlndomainBO = SpringContextUtil
				.getBean(ListAlndomainBO.class);
		ClassstructureHeaderApply header = classstructureForm
				.getClassstructureHeaderApply();
		List<Alndomain> listAlndomain = new ArrayList<>();
		if (Utility.equals(header.getAction(),   // 屬性清單失效
				ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_INACTIVE)){
			listAlndomain = listAlndomainBO.getAlndomainList(
					header.getClassstructureid(), true);
		} else if (Utility.equals(header.getAction(), 
				ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ACTIVE)) { // 屬性清單生效
			listAlndomain = listAlndomainBO.getAlndomainList(
					header.getClassstructureid(), false);
		}
		for (Alndomain a : listAlndomain) {
			if (map.get(a.getDomainid()) == null) {
				map.put(a.getDomainid(), new ArrayList<Alndomain>());
			}
			map.get(a.getDomainid()).add(a);
		}
		return map;
	}

	@Override
	public List getClassspecList(ClassstructureForm classstructureForm) {
		ListClassspecBO listClassspecBO = SpringContextUtil
				.getBean(ListClassspecBO.class);
		ClassstructureHeaderApply header = classstructureForm
				.getClassstructureHeaderApply();
		List<ListClassspecVO> list = listClassspecBO.getListClassspec(header
				.getClassstructureid(),SystemConfig.aln,true);
		
		//==============屬性清單失效或生效==============================
		if (Utility.equalsOR(header.getAction(),
				ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_INACTIVE,
				ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ACTIVE)){ 
			List<ListClassspecVO> newlist = new ArrayList<>();
			for (ListClassspecVO l : list) {
				if (classstructureForm.getListAlndomainMap().get(
						l.getDomainid()) == null) {
					newlist.add(l);
				}
			}
			list.removeAll(newlist);
		}
		return list;
	}

	@Override
	public void getDownLoadFile(ClassstructureForm classstructureForm) { // 取得下載檔案
		AttachmentBO attachmentBO = SpringContextUtil.getBean(AttachmentBO.class);
		classstructureForm.getDowloadFileMap().putAll(attachmentBO.getMap(
				classstructureForm.getClassstructureHeaderApply().getApplyHeaderNum(), null, true));
	}

	@Override
	public String getSignParameter(ClassstructureForm classstructureForm) {
		JSONObject json = new JSONObject();
		try {
			json.put("CLASSSTRUCTUREID", classstructureForm.getClassstructureHeaderApply().getClassstructureid());
			json.put("processId", WorkflowUtils.getClassstructureApplyProcessId());
			json.put("empno", classstructureForm.getClassstructureHeaderApply().getCreateBy());
			json.put("price", new BigDecimal("0"));
			json.put("comment", classstructureForm.getSignComment());
		} catch (JSONException e) {
			throw new RuntimeException(e.toString());
		}

		return json.toString();
	}

	@Override
	public void setSubmit(ClassstructureForm classstructureForm) {
		ClassstructureHeaderApply header=classstructureForm.getClassstructureHeaderApply();
		//=====生效和失效 DESCRIPTION
		for(ClassstructureLineApply c:classstructureForm.getListClassstructureLineApply()){
			if(c.getAlndomain()!=null){
				c.setDescription(c.getAlndomain().getDescription());
			}
		}
		//更新CHANGE_TYPE_FLAG
		Map<String,Integer> domainIdMap=alndomainBO.getDomainIdCount(header.getClassstructureid());
		header.setChangeTypeFlag("N");
		for(ClassstructureLineApply c:classstructureForm.getListClassstructureLineApply()){
			c.setChangeTypeFlag("N");
			if (Utility.equals(classstructureForm.getClassstructureHeaderApply().getAction(),
					ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_INACTIVE)) { // 屬性清單失效
				if(domainIdMap.get(c.getDomainid())==1) {
					c.setChangeTypeFlag("Y");
				}
			} else if (Utility.equalsOR(classstructureForm.getClassstructureHeaderApply().getAction(),
					ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ACTIVE,
					ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ADD)) { // 屬性清單生效 or 新增屬性
				if(domainIdMap.get(c.getDomainid())==null || domainIdMap.get(c.getDomainid())==0){ 
					c.setChangeTypeFlag("Y");
				}
			}
			if(c.getChangeTypeFlag().equals("Y")){
				header.setChangeTypeFlag("Y");
			}
		}
		
	}

}
