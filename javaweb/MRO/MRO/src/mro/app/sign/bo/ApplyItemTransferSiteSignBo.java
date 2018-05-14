package mro.app.sign.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mro.app.sign.dao.ApplyItemTransferSiteSignDao;
import mro.base.System.config.basicType.ItemSiteTransferType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.ItemBO;
import mro.base.bo.ItemSiteBO;
import mro.base.bo.ItemSiteTransferLogBO;
import mro.base.bo.SapPlantAttributeBO;
import mro.base.entity.Item;
import mro.base.entity.ItemSite;
import mro.base.entity.ItemTransferHeaderApply;
import mro.base.entity.ItemTransferLineApply;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.form.ItemTransferSiteForm;
import mro.utility.ExceptionUtils;
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
public class ApplyItemTransferSiteSignBo {

	ApplyItemTransferSiteSignDao applyItemTransferSiteSignDao;
	private SapPlantAttributeBO sapPlantAttributeBO;
	private ItemSiteTransferLogBO itemSiteTransferLogBO;
	private ItemBO itemBO;
	private ItemSiteBO itemSiteBO;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		applyItemTransferSiteSignDao=new ApplyItemTransferSiteSignDao(sessionFactory);
		sapPlantAttributeBO=SpringContextUtil.getBean(SapPlantAttributeBO.class);
		itemSiteTransferLogBO=SpringContextUtil.getBean(ItemSiteTransferLogBO.class);
		itemBO=SpringContextUtil.getBean(ItemBO.class);
		itemSiteBO=SpringContextUtil.getBean(ItemSiteBO.class);
    }
	
	@Transactional(readOnly = false)
	public void onSign(ItemTransferSiteForm itemTransferSiteForm, SignStatus action,
			String loginEmpNo) {
		boolean signStaus = false;
		String apprStatus = "";
		if (action.compareTo(SignStatus.REJECT)==0) {
			signStaus = WorkflowActionUtils.onReject(itemTransferSiteForm
					.getItemTransferHeaderApply().getTaskId(),
					loginEmpNo, itemTransferSiteForm.getSignComment());
		} else if (action.compareTo(SignStatus.TRANSFER)==0) {
			signStaus = WorkflowActionUtils.onTransfer(itemTransferSiteForm
					.getItemTransferHeaderApply().getTaskId(),
					loginEmpNo, itemTransferSiteForm.getSignComment(),
					itemTransferSiteForm.getPersonForward().getPersonId());
		} else if (action.compareTo(SignStatus.APPR)==0) {
			apprStatus = WorkflowActionUtils
					.onAppr(itemTransferSiteForm.getItemTransferHeaderApply()
							.getTaskId(), loginEmpNo,
							itemTransferSiteForm.getSignComment(), null, null);
			if (StringUtils.isNotBlank(apprStatus)) {
				signStaus = true;
			}
		}

		if (!signStaus) {
			ExceptionUtils.showFalilException(
					itemTransferSiteForm.getItemTransferHeaderApply().getApplyHeaderNum(),
					itemTransferSiteForm.getItemTransferHeaderApply().getTaskId(),
					"執行程序：" + action);
		} else {
			if (action.compareTo(SignStatus.REJECT)==0) {
				update(itemTransferSiteForm.getItemTransferHeaderApply(),
						action);
			} else if (action.compareTo(SignStatus.APPR)==0) {
				if (apprStatus.equals(SignStatus.APPR.toString())) { // Finally Approve
					onFinalAccept(itemTransferSiteForm, loginEmpNo);
				}
			}

		}
	}

	@Transactional(readOnly = false)   // 確定同意(最後簽核者)
	public void onFinalAccept(ItemTransferSiteForm itemTransferSiteForm,
			String loginEmpNo) {
		setFinalAccept(itemTransferSiteForm);
	}
	
	@Transactional(readOnly = true)
	public ItemTransferHeaderApply getItemTransferHeaderApply(String applyHeaderId) {
		StringBuffer str=new StringBuffer();
		str.append(" and APPLY_HEADER_ID ='"+applyHeaderId+"' ");
		return applyItemTransferSiteSignDao.getItemTransferHeaderApply(str.toString());
	}
		
	@Transactional(readOnly = true)
	public List<ItemTransferHeaderApply> getItemTransferHeaderApplyList(List<BigDecimal> wString) {
		StringBuffer str=new StringBuffer();
		str.append("and STATUS ='"+SignStatus.INPRG+"' ");
		str.append("and ( TASK_ID=-1 ");
		for(BigDecimal s:wString){
			str.append(" or TASK_ID="+s);
		}
		str.append(") ");
		return applyItemTransferSiteSignDao.getItemTransferHeaderApplyList(str.toString());
	}
	@Transactional(readOnly = true)
	public List<ItemTransferLineApply> getItemTransferLineApplyList(BigDecimal applyHeaderId){
		StringBuffer str=new StringBuffer();
		if(applyHeaderId!=null){
			str.append(" and APPLY_HEADER_ID='"+applyHeaderId+"'");
		}
		return applyItemTransferSiteSignDao.getItemTransferLineApplyList(str.toString());
	}
	@Transactional(readOnly=false)
	public void update(ItemTransferHeaderApply itemTransferHeaderApply,
			SignStatus status){
		itemTransferHeaderApply.setStatus(status.toString());
		applyItemTransferSiteSignDao.insertUpdate(itemTransferHeaderApply);
	}
	
	@Transactional(readOnly=false)
	public void setFinalAccept(ItemTransferSiteForm itemTransferSiteForm){
		ItemTransferHeaderApply header=itemTransferSiteForm.getItemTransferHeaderApply();
		ValidationUtils.validateStatus(header.getApplyHeaderNum(),header.getTaskId(),
				ItemStatusType.TYPE_PROCESS_AS);
		header.setStatus(SignStatus.SYNC.toString());
		header.setLastUpdateDate(new Date(System.currentTimeMillis()));
		applyItemTransferSiteSignDao.insertUpdate(header);

		//===========================Item_site_transfer_log==========================
		for(ItemTransferLineApply i:itemTransferSiteForm.getListItemTransferLineApply()){
			Item item=itemBO.getItem(i.getItemnum());
			//===========================ITEM_SITE========================================
				if(header.getAction().equals(LocationSiteActionType.I.name())){ //新增廠區
					ItemSite itemSite=itemSiteBO.getItemSite(i.getItemnum(), header.getLocationSite());
					if(itemSite==null){itemSite=new ItemSite();}
					itemSite.setItemid(i.getItemid());
					itemSite.setItemnum(i.getItemnum());
					itemSite.setLocationSite(header.getLocationSite());
					itemSite.setLastUpdateDate(header.getLastUpdateDate());
					itemSite.setLastUpdatedBy(i.getCreateBy());
					applyItemTransferSiteSignDao.insertUpdate(itemSite);
				}			

				// ===========================Item_site_transfer_log==========================
				boolean nonValueFlag=true;
				//=============區域停用不停用無價廠料號
				if(LocationSiteActionType.valueOf(header.getAction()).compareTo(
						LocationSiteActionType.S)==0){
					nonValueFlag=false;
				}
				
				itemSiteTransferLogBO.update(sapPlantAttributeBO.getListBySite(
						header.getLocationSite(),
						item.getClassstructureid(),nonValueFlag),item, 
						header.getApplyHeaderNum(),
						i.getCreateBy(),
						ItemSiteTransferType.bySite, 
						LocationSiteActionType.valueOf(header.getAction()).getTransferType());
		}
	}
}
