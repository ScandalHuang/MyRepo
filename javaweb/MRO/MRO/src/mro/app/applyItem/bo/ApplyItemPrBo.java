package mro.app.applyItem.bo;

import java.math.BigDecimal;
import java.util.Date;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.applyItem.dao.ApplyItemPrDao;
import mro.app.applyItem.service.PrInterface;
import mro.app.applyItem.service.impl.PrImpl;
import mro.app.commonview.dao.ListItemCommonDAO;
import mro.app.commonview.jsf.ListPrlineBean;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ActionType;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.LocationMapBO;
import mro.base.bo.PrBO;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.form.PrForm;
import mro.utility.ExceptionUtils;
import mro.utility.ValidationUtils;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.ActiveFlag;

@Component
@Scope("prototype")
public class ApplyItemPrBo {

	private ApplyItemPrDao applyItemPrDao;
	private ListItemCommonDAO listItemCommonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		applyItemPrDao = new ApplyItemPrDao(sessionFactory);
		listItemCommonDAO = new ListItemCommonDAO(sessionFactory); 
	}
	@Transactional(readOnly = true)
	public BigDecimal getUnUseBudget(String siteid,String deptcode,String prtype){
		return applyItemPrDao.getUnUseBudget(siteid,deptcode, prtype);
	}
	@Transactional(readOnly=false)
	public void updatePrOther(Pr pr,SignStatus status){
		pr.setStatus(status.toString());
		pr.setStatusdate(new Date(System.currentTimeMillis()));   //狀態變更日期
		applyItemPrDao.insertUpdate(pr);
		
	}
	@Transactional(readOnly=false)
	public void deletePr(PrForm prForm){
		Pr pr=prForm.getPr();
		applyItemPrDao.deltePrline(pr.getPrid().toString());
		applyItemPrDao.delete(pr);
		ApplyPrUtils.deletePrFile(pr.getPrid().toString(),prForm.getFileHeaderType());
		ApplyPrUtils.deleteFile(pr.getPrid(),prForm.getFileLineType());
		
	}
	@Transactional(readOnly=false)
	public void deletePr(Pr[] pr){
		for(Pr p:pr){
			applyItemPrDao.deltePrlineAssigned(p.getPrid().toString());
			applyItemPrDao.deltePrline(p.getPrid().toString());
			applyItemPrDao.delete(p);
		}
		
	}
    @Transactional(readOnly = false)
    public void onApplySave(ActionType actionType,PrForm prForm){
    	Date date =new Date(System.currentTimeMillis());

    	//===========================================set org id===========================================
    	PrBO prBO=SpringContextUtil.getBean(PrBO.class);
    	LocationMapBO locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
		LocationMap locationMap = Utility.nvlEntity(
				locationMapBO.getLocationMapBySId(prForm.getPr().getSiteid()),LocationMap.class);
		prForm.getPr().setOrgid(locationMap.getOrg());

    	if(prForm.getPr().getPrid()==null){ 
    		prForm.getPr().setIssuedate(date); // 申請日期
    		prForm.getPr().setPrnum(prBO.getPrNum(prForm.getPr().getPrtype()));// 申請單編號
    		prForm.getPr().setStatusdate(date);   //狀態變更日期
    	}else { // 驗證申請單是否在流程中
			ValidationUtils.validateStatus(
					prForm.getPr().getPrnum(),
					prForm.getPr().getTaskId(),
					ItemStatusType.TYPE_PROCESS_AIS);
		}
    	//===========================================Pr===================================================
    	if(actionType.compareTo(ActionType.submit)==0){
    		prForm.getPr().setStatus(SignStatus.INPRG.toString());
    		prForm.getPr().setStatusdate(date);   //狀態變更日期
    		//===============================有設定預算再更新============================================
    		BigDecimal unUseBudget=applyItemPrDao.getUnUseBudget(prForm.getPr().getSiteid(),
    				prForm.getPr().getMDept(),prForm.getPr().getPrtype());
    		if(unUseBudget!=null){
    			BigDecimal budger=unUseBudget.subtract(prForm.getPr().getTotalbasecost2());
    			if(budger.compareTo(new BigDecimal("0"))==-1){
    				ExceptionUtils.showFalilException(prForm.getPr().getPrnum(), "申請單金額超出預算");
    			}
    		}
    	}
    		if(!prForm.isReasonFlag()){prForm.getPr().setReasoncode("");}
    		
    		prForm.getPr().setChangedate(date); //儲存&送審日期
    		applyItemPrDao.insertUpdate(prForm.getPr());
    	//======================================PrLine==========================================================
    	//刪除prline
    	for(Prline p:prForm.getListDeletePrline()){
    		if(p.getPrlineid()!=null){
	    		ApplyPrUtils.deletePrFile(p.getPrlineid().toString(), prForm.getFileLineType());
	    		applyItemPrDao.delete(p);
    		}
    	}
    	//新增/更新 prline
    	prForm.getListPrline().forEach(l->l.initial(prForm.getPr()));
    	applyItemPrDao.insertUpdate(prForm.getListPrline());
    }
    
    //自動開單
	@Transactional(readOnly = false)
	public Pr onAutoPr(Person person,String requestedby2, String prtype,String itemnum,
			String siteId,String deptcode,BigDecimal qty,boolean signFlag,
			String remark,ActiveFlag ignoreDeptFlag,GlobalGrowl message) {
		BigDecimal num = new BigDecimal("0");
		//=================Pr========================================
		PrForm prForm=new PrForm();
		prForm.onNewPr(person,prtype,siteId,deptcode,requestedby2);
		prForm.getPr().setIgnoreDeptFlag(ignoreDeptFlag.toString());
		prForm.getPr().setDescription(remark);
		//================Prline====================================
		ListPrlineBean listPrlineBean=new ListPrlineBean();
		listPrlineBean.addPrLine(prForm.getMaxPrlineNum(), prForm);
		listPrlineBean.setSelectItem(listItemCommonDAO.getItem(itemnum));
		Prline prline=listPrlineBean.getPrLineForm().getPrline();
		prline.setCmoremark(remark);
		
		if(Utility.equals(prtype, PrType.R1CONTROL)){
			prline.setReqmark2(remark); //新平均月耗量說明
			prline.setMinlevel(qty.subtract(prline.getOriminlevel()));
		}else if(Utility.equals(prtype, PrType.R2CONTROL)){
			prline.setReqmark2(remark);//新平均月耗量說明
			prline.setDavguseqty(qty.subtract(prline.getOriavguseqty()));
			if(qty.compareTo(num)==0){
				prline.setSstock(num.subtract(prline.getOrisstock()));
			}
		}else{
			prline.setPmreqqty(qty);
		}
		listPrlineBean.newminlevel(); //更新總價 & 控管量資訊整理()
		
		PrInterface impl=new PrImpl();
		impl.setSelectPrline(prForm, prline, ActionType.add);
		
		ActionType actionType=signFlag?ActionType.submit:ActionType.noSign;
		if(impl.onApplyProcess(prForm, actionType,person,message)){
			return prForm.getPr();
		}
		
		return null;
	}
}
