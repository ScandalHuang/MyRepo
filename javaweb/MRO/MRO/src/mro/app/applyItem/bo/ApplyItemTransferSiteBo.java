package mro.app.applyItem.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.app.applyItem.dao.ApplyItemTransferSiteDao;
import mro.base.System.config.basicType.ActionType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.LocationMapBO;
import mro.base.bo.LocationSiteMapBO;
import mro.base.entity.ClassstructureItemSiteSign;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.form.ItemTransferSiteForm;
import mro.utility.ValidationUtils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;

@Component
@Scope("prototype")
public class ApplyItemTransferSiteBo {
	
	private ApplyItemTransferSiteDao applyItemTransferSiteDao;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		applyItemTransferSiteDao=new ApplyItemTransferSiteDao(sessionFactory);
    }
	
	@Transactional(readOnly = true)
	public List<ItemTransferHeaderApply> getItemTransferHeaderApplyList(String empNo){
		StringBuffer str=new StringBuffer();
		if(StringUtils.isNotBlank(empNo)){
			str.append(" and CREATE_BY='"+empNo+"' ");
		}
		str.append(" and status not in('"+SignStatus.APPR+"','"+SignStatus.CAN+"')");
		return applyItemTransferSiteDao.getItemTransferHeaderApplyList(str.toString());
	}
	@Transactional(readOnly = true)
	public List<ItemTransferLineApply> getItemTransferLineApplyList(BigDecimal applyHeaderId){
		StringBuffer str=new StringBuffer();
		if(applyHeaderId!=null){
			str.append(" and APPLY_HEADER_ID='"+applyHeaderId+"'");
		}
		return applyItemTransferSiteDao.getItemTransferLineApplyList(str.toString());
	
	}
	@Transactional(readOnly = false)
	public void onDelteApply(ItemTransferHeaderApply itemTransferHeaderApply){
		applyItemTransferSiteDao.onDeleteApplyLine(itemTransferHeaderApply.getApplyHeaderId());
		applyItemTransferSiteDao.delete(itemTransferHeaderApply);
	
	}
	@Transactional(readOnly = true)
	public String getHeaderNum(){
		return applyItemTransferSiteDao.getHeaderNum();
	}
	
	@Transactional(readOnly = false)
	public void onApplySave(ItemTransferSiteForm itemTransferSiteForm,ActionType type){		
		if (itemTransferSiteForm.getItemTransferHeaderApply().getApplyHeaderNum() == null) { //第一次儲存
			itemTransferSiteForm.getItemTransferHeaderApply().setApplyHeaderNum(this.getHeaderNum());// 申請單編號
			itemTransferSiteForm.getItemTransferHeaderApply().setCreateDate(new Date(System.currentTimeMillis()));
		} else { // 驗證申請單是否在流程中
			ValidationUtils.validateStatus(
					itemTransferSiteForm.getItemTransferHeaderApply().getApplyHeaderNum(),
					itemTransferSiteForm.getItemTransferHeaderApply().getTaskId(),
					ItemStatusType.TYPE_PROCESS_AIS);
		}
		if (type.compareTo(ActionType.submit)==0) {
			itemTransferSiteForm.getItemTransferHeaderApply().setStatus(SignStatus.INPRG.toString());
		}

		itemTransferSiteForm.getItemTransferHeaderApply().setLastUpdateDate(new Date(System.currentTimeMillis()));
		applyItemTransferSiteDao.insertUpdate(itemTransferSiteForm.getItemTransferHeaderApply());
		//==========刪除line=======
		for(ItemTransferLineApply i:itemTransferSiteForm.getListDeleteItemTransferLineApply()){
			applyItemTransferSiteDao.delete(i);
		}
		//==========新增/更新line=======
		for(ItemTransferLineApply i:itemTransferSiteForm.getListItemTransferLineApply()){
			i.setItemTransferHeaderApply(itemTransferSiteForm.getItemTransferHeaderApply());
			i.setStatus("N");//S:更新成功,E:更新失敗,N:待更新
			i.setCreateBy(itemTransferSiteForm.getItemTransferHeaderApply().getCreateBy());
			i.setCreateDate(itemTransferSiteForm.getItemTransferHeaderApply().getCreateDate());
			applyItemTransferSiteDao.insertUpdate(i);
		}
		
	}
	
	@Transactional(readOnly = true)
	public List<Map> getItemTransferApplyCost(BigDecimal applyHeaderId){
		
		return applyItemTransferSiteDao.getItemTransferApplyCost(applyHeaderId);
	
	}
}
