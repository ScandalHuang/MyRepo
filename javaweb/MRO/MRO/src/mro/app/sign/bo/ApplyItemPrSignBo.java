package mro.app.sign.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mro.app.applyItem.dao.ApplyItemPrDao;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.PrLineType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.InvbalancesBO;
import mro.base.bo.InventoryBO;
import mro.base.bo.LongdescriptionBO;
import mro.base.bo.PrControlConfigBO;
import mro.base.bo.PrlineBO;
import mro.base.entity.Invbalances;
import mro.base.entity.Inventory;
import mro.base.entity.Longdescription;
import mro.base.entity.Pr;
import mro.base.entity.PrControlConfig;
import mro.base.entity.Prline;
import mro.base.workflow.utils.WorkflowActionUtils;
import mro.form.PrForm;
import mro.utility.ExceptionUtils;
import mro.utility.ValidationUtils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.dao.FunctionDAO;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@Component
@Scope("prototype")
public class ApplyItemPrSignBo {
	
	CommonDAO commonDAO;
	FunctionDAO functionDAO;
	ApplyItemPrDao applyItemPrDao;
	PrControlConfigBO prControlConfigBO;
	InvbalancesBO invbalancesBO;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
		functionDAO=new FunctionDAO(sessionFactory);
		applyItemPrDao=new ApplyItemPrDao(sessionFactory);
		prControlConfigBO=SpringContextUtil.getBean(PrControlConfigBO.class);
		invbalancesBO=SpringContextUtil.getBean(InvbalancesBO.class);
    }
	



	@Transactional(readOnly = false)   
	public void onSign(PrForm prForm, SignStatus action, String loginEmpNo) {
		boolean signStaus = false;
		String apprStatus = "";
		if (action.compareTo(SignStatus.REJECT)==0) {
			signStaus = WorkflowActionUtils.onReject(prForm.getPr().getTaskId()
					, loginEmpNo, prForm.getSignComment());
		} else if (action.compareTo(SignStatus.CAN)==0) {
			signStaus = WorkflowActionUtils.onCancel(prForm.getPr().getTaskId()
					, loginEmpNo, prForm.getSignComment());
		} else if (action.compareTo(SignStatus.TRANSFER)==0) {
			signStaus = WorkflowActionUtils
					.onTransfer(prForm.getPr().getTaskId(),
							loginEmpNo, prForm.getSignComment(), prForm
									.getPersonForward().getPersonId());
		} else if (action.compareTo(SignStatus.APPR)==0) {
			apprStatus = WorkflowActionUtils.onAppr(prForm.getPr().getTaskId()
					, loginEmpNo, prForm.getSignComment(),
					prForm.getMailContent(), prForm.getNotifyList());
			if (StringUtils.isNotBlank(apprStatus)) {
				signStaus = true;
			}
		}

		if (!signStaus) {
			ExceptionUtils.showFalilException(prForm.getPr().getPrnum(),prForm.getPr().getTaskId(),
					"執行程序：" + action);
		} else {
			if (action.compareTo(SignStatus.REJECT)==0|| action.compareTo(SignStatus.CAN)==0) {
				updatePrOther(prForm, action);
			} else if (action.compareTo(SignStatus.APPR)==0) {
				if (apprStatus.equals(SignStatus.APPR.toString())) { // Finally Approve
					updatePrFinal(prForm,SignStatus.APPR,loginEmpNo);// 確定同意(最後簽核者)
				}else{
					updateClosePrline(prForm.getPr(),prForm.getListDeletePrline()); //更新狀態
					updateCombinePrline(prForm.getPr(),prForm.getListCombinePrline()); //更新狀態
				}
			}

		}
	}
	
	@Transactional(readOnly=false)
	public void updatePrOther(PrForm prForm,SignStatus status){
		//=====================header=====================================
		prForm.getPr().setStatus(status.toString());
		prForm.getPr().setStatusdate(new Date(System.currentTimeMillis()));  //狀態變更日期
		commonDAO.insertUpdate(prForm.getPr());
	}
	
	@Transactional(readOnly = false)
	public void updatePrFinal(PrForm prForm, SignStatus status, String loginEmpNo) {
		ValidationUtils.validateStatus(prForm.getPr().getPrnum(),
				prForm.getPr().getTaskId(),ItemStatusType.TYPE_PROCESS_AS);
		//=====================header=====================================
		prForm.getPr().setLastSigner(loginEmpNo);
		prForm.getPr().setStatus(status.toString());
		prForm.getPr().setStatusdate(new Date(System.currentTimeMillis()));  //狀態變更日期
		onClosePrLine(prForm);
		commonDAO.insertUpdate(prForm.getPr());
		//=====================close prline==================
		updateClosePrline(prForm.getPr(),prForm.getListClosePrline());
		
		onCombinePrLine(prForm);
		commonDAO.insertUpdate(prForm.getPr());
		//=====================close prline==================
		updateCombinePrline(prForm.getPr(),prForm.getListCombinePrline());
		
		//=====================prline=====================================
		if (prForm.getPr().getPrtype().equals(PrType.R1CONTROL.name()) || 
				prForm.getPr().getPrtype().equals(PrType.R2CONTROL.name())) {
			InventoryBO inventoryBO=SpringContextUtil.getBean(InventoryBO.class);
			LongdescriptionBO longdescriptionBO=SpringContextUtil.getBean(LongdescriptionBO.class);
			InvbalancesBO invbalancesBO=SpringContextUtil.getBean(InvbalancesBO.class);
			for(Prline p:prForm.getListPrline()){
				//================================inventory========================================
				Inventory inventory=inventoryBO.getInventory(p.getItemnum(), null, p.getSiteid(), p.getStoreroom());
				//不存在INVENTORY 或未設定控管模式
				if(inventory==null ||inventory.getInventoryid()==null  
					||StringUtils.isBlank(inventory.getCmommcontrol())){ 
					if(inventory==null ||inventory.getInventoryid()==null){ //不存在INVENTORY
						inventory=new Inventory();
						BeanUtils.copyProperties(p, inventory);
						inventory.setBinnum(null);
						inventory.setMinlevel(new BigDecimal(0));
						inventory.setCategory(SystemConfig.INVENTORY_CATEGORY_STK);
						inventory.setLocation(p.getStoreroom());
						inventory.setOrderqty(1);
						inventory.setSstock(new BigDecimal("0"));
					}
					PrControlConfig pc=prControlConfigBO.getPrControlConfig(inventory);
					inventory.setCmommcontrol(pc==null?null:pc.getControlType());  //預設的控管模式
					inventory.setStockdays(pc==null?null:new BigDecimal(pc.getStockDays())); //201801 add
					//======2016.09.09開放兩個廠區可以多個sstock所以修改成triger============
//					inventory.setSstock(p.getNewsstock()); //廠區設定值=新 最低安全存量
					commonDAO.insertUpdate(inventory);
				}
				//================================Invbalance===============================
					Invbalances invbalances=Utility.nvlEntity(invbalancesBO.getInvbalance(
							p.getItemnum(),p.getMDept(), p.getStoreroom(),p.getSiteid()),Invbalances.class);
					BeanUtils.copyProperties(p, invbalances);
					invbalances.setLastrequestedby(p.getRequestedby());
					invbalances.setLastrequestedby2(p.getRequestedby2());
					invbalances.setLastrequestdate(new Date(System.currentTimeMillis()));
					invbalances.setLocation(p.getStoreroom());
					invbalances.setBinnum(p.getMDept());
					invbalances.setDeptcode(p.getMDept());
					invbalances.setMinlevel(p.getNewminlevel()); 
					invbalances.setStdcost(p.getUnitcost());    
					if(prForm.getPr().getPrtype().equals(PrType.R2CONTROL.name())){
						invbalances.setSstock(p.getNewsstock());//新 最低安全存量  
						invbalances.setOriavguseqty(p.getNewavguseqty());//新平均月耗用量  
					}
					commonDAO.insertUpdate(invbalances);
				//================================Longdescription============================
					Longdescription longdescription=invbalances.getInvbalancesid()!=null?
							longdescriptionBO.getLongdescription(
							invbalances.getInvbalancesid()):null;
					if(longdescription==null){longdescription=new Longdescription();}
					longdescription.setCreateDate(new Date(System.currentTimeMillis()));
					longdescription.setLdkey(invbalances.getInvbalancesid());
					longdescription.setLdownertable("INVBALANCES");
					longdescription.setLdownercol("REMARK");
					longdescription.setLangcode(p.getLangcode());
					longdescription.setLdtext(p.getCmoremark());
					commonDAO.insertUpdate(longdescription);
			}
		}
	}
	
	
	@Transactional(readOnly = false)   
	public void onCanSign(PrForm prForm, String loginEmpNo) {
		if(SignStatus.valueOf(prForm.getPr().getStatus()).equals(SignStatus.APPR)){
			WorkflowActionUtils.onCounterSign(prForm.getPr().getTaskId(), loginEmpNo);
		}
		this.onSign(prForm, SignStatus.CAN, loginEmpNo);
	}

	@Transactional(readOnly = false)
	public void updateClosePrline(Pr pr,List<Prline> prlines) {
		for(Prline prline:prlines){
			prline.setLineCancelFlag(1);
			prline.setLineClosedCode(SignStatus.CLOSE.toString());
			prline.setChangeDate(new Date(System.currentTimeMillis()));
		}
		commonDAO.insertUpdate(prlines.toArray());
	}
	@Transactional(readOnly = false)
	public void updateCombinePrline(Pr pr,List<Prline> prlines) {
		for(Prline prline:prlines){
			prline.setLineCancelFlag(1);
			prline.setLineClosedCode(SignStatus.COMB.toString());
			prline.setChangeDate(new Date(System.currentTimeMillis()));
		}
		commonDAO.insertUpdate(prlines.toArray());
	}
	
	//===========================================================================================
	@Transactional(readOnly=false)  //appr  再做status異動
	public void onClosePrLine(PrForm prForm) {	
		updateClosePrline(prForm.getPr(),prForm.getListDeletePrline()); //更新狀態
		
		PrlineBO prlineBO =SpringContextUtil.getBean(PrlineBO.class);
		//=============================PRLine 是否有全部通過=========================
		if(prlineBO.getPrlineCount(prForm.getPr().getPrid(),SignStatus.CLOSE)>0||
				prlineBO.getPrlineCount(prForm.getPr().getPrid(),SignStatus.COMB)>0){
			prForm.getPr().setPrlineStatus(PrLineType.PART.name());
		}else{
			prForm.getPr().setPrlineStatus(PrLineType.ALL.name());
		}
		commonDAO.insertUpdate(prForm.getPr());
		
	}
	@Transactional(readOnly=false)  //appr  再做status異動
	public void onCombinePrLine(PrForm prForm) {	
		updateCombinePrline(prForm.getPr(),prForm.getListCombinePrline()); //更新狀態
		
		PrlineBO prlineBO =SpringContextUtil.getBean(PrlineBO.class);
		//=============================PRLine 是否有全部通過=========================
		if(prlineBO.getPrlineCount(prForm.getPr().getPrid(),SignStatus.COMB)>0
				||prlineBO.getPrlineCount(prForm.getPr().getPrid(),SignStatus.CLOSE)>0){
			prForm.getPr().setPrlineStatus(PrLineType.PART.name());
		}else{
			prForm.getPr().setPrlineStatus(PrLineType.ALL.name());
		}
		commonDAO.insertUpdate(prForm.getPr());
		
	}
	

}
