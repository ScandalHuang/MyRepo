package mro.app.mcMgmtInterface.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mro.app.applyItem.bo.ApplyItemPrBo;
import mro.app.mcMgmtInterface.dao.ListItemHighlightDAO;
import mro.app.mcMgmtInterface.form.HighlightForm;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.PrType;
import mro.base.entity.InvbalancesHighlightLog;
import mro.base.entity.MroInvbalancesHighlightV;
import mro.base.entity.Person;
import mro.base.entity.Pr;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.hibernate.ActiveFlag;

@Component
@Scope("prototype")
public class ListItemHighlightBO {

	private ListItemHighlightDAO listItemHighlightDAO;
	
	@Autowired
	private ApplyItemPrBo applyItemPrBo;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		listItemHighlightDAO = new ListItemHighlightDAO(sessionFactory);
	}

	@Transactional(readOnly = true)
	public List<MroInvbalancesHighlightV> getHighlightList(HighlightForm highlightForm) {
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(highlightForm.getSelectitemnum())) {
			condition.append(" and v.itemnum like '"
					+ highlightForm.getSelectitemnum() + "%' ");
		}
		if (StringUtils.isNotBlank(highlightForm.getSelectItemCategory())) {
			condition.append(" and v.itemnum like '"
					+ highlightForm.getSelectItemCategory() + "%' ");
		}
		if (StringUtils.isNotBlank(highlightForm.getSelectDeptcode())) {
			condition.append(" and v.binnum like '"
					+ highlightForm.getSelectDeptcode() + "%' ");
		}
		if (StringUtils.isNotBlank(highlightForm.getSelectSiteId())) {
			condition.append(" and v.siteid like '"
					+ highlightForm.getSelectSiteId() + "%' ");
		}
		if (StringUtils.isNotBlank(highlightForm.getReasonType())) {
			condition.append("and v.REASON_TYPE ='"
					+ highlightForm.getReasonType() + "' ");
		}
		if(StringUtils.isNotBlank(highlightForm.getMonitorType())){
			if(highlightForm.getMonitorType().equals("Y")){
				condition.append("AND SUMOFCONTER_FLAG='Y' ");
			}else if(highlightForm.getMonitorType().equals("N")){
				condition.append("AND THREE_MONTH_AVG_FLAG='Y' ");
			}
		}

		if (highlightForm.isSearchHistory()) { 
			condition.append("AND (SUMOFCONTER_FLAG='Y' OR THREE_MONTH_AVG_FLAG='Y') ");
		}else{
			condition.append("AND (SUMOFCONTER_FLAG='Y' OR THREE_MONTH_AVG_FLAG='Y') "
					+ "AND HIGHLIGHT_FLAG='Y'  ");
		}
		return listItemHighlightDAO.getHighlightList(condition.toString());
	}
	
	@Transactional(readOnly = false)
	public void updateInvbalances(BigDecimal invbalancesid,Person person,Date lastHighlightResponseDate){
		listItemHighlightDAO.updateInvbalances(invbalancesid,
				person.getPersonId(), lastHighlightResponseDate);
	}
	@Transactional(readOnly = false)
	public boolean updateHighLightLog(HighlightForm highlightForm,Person person, String action,
			GlobalGrowl message) {
		StringBuffer otherRemark = new StringBuffer();
		Date highlightDate = new Date(System.currentTimeMillis());
		MroInvbalancesHighlightV highligh=highlightForm.getMroInvbalancesHighlightV();
		String prtype=PrType.getControlPrType(highligh.getCommoditygroup());
		
		if (highlightForm.isEditButton()) {
			highlightForm.getInvbalancesHighlightLog().setType("M"); // 管理員
		}else{
			highlightForm.getInvbalancesHighlightLog().setType("U"); // 一般USER
		}
		
		if (action.equals("reHighlight")) {
			otherRemark.append("ACTION：重新啟用highLigh機制;\n");
			highlightDate = null; // 管理者回覆強制重新啟用highLigh機制
		}else{
			boolean signFlag=true;  //預設簽核
			boolean prFlag=false;  //預設不開單
			if (action.equals("initalMinlevel")) {
				otherRemark.append("ACTION：物管/管理員 下修月用量為"+highlightForm.getChangeQty()+";\n");
				signFlag=false;
				prFlag=true;
			}else{
				BigDecimal changeQty=SystemConfig.highLightCode.get(highlightForm
						.getInvbalancesHighlightLog().getReasonType());
				if (changeQty != null) {
					signFlag=true;
					prFlag=true;
					highlightForm.setChangeQty(changeQty);
				}
			}
			if(prFlag){ // 立即開立控管申請單
				if(highlightForm.getChangeQty()==null){  //如果沒有輸入就為預設值
					highlightForm.setChangeQty();
				}
				Pr pr = this.onHighLightToPr(person,highligh.getLastrequestedby2(),prtype,
						highligh.getItemnum(),highligh.getSiteid(),highligh.getBinnum(),
						highlightForm.getChangeQty(),signFlag,message);
				if(pr==null){return false;}
				otherRemark.append("PRNUM:" + pr.getPrnum() + "\n");
				highlightForm.getInvbalancesHighlightLog().setPr(pr);
			}
		}
			
		if(StringUtils.isNotBlank(highlightForm.getInvbalancesHighlightLog().getRemark())){
			otherRemark.append("說明："+highlightForm.getInvbalancesHighlightLog().getRemark());
		}
		
		highlightForm.getInvbalancesHighlightLog().setRemark(otherRemark.toString());
		highlightForm.getInvbalancesHighlightLog().setItemnum(highligh.getItemnum());
		highlightForm.getInvbalancesHighlightLog().setDeptcode(highligh.getBinnum());
		highlightForm.getInvbalancesHighlightLog().setSiteId(highligh.getSiteid());
		highlightForm.getInvbalancesHighlightLog().setDeleted(new BigDecimal("0"));
		highlightForm.getInvbalancesHighlightLog().setCreateBy(person.getPersonId());
		highlightForm.getInvbalancesHighlightLog().setCreateDate(new Date(System.currentTimeMillis()));
		highlightForm.getInvbalancesHighlightLog().setInvbalancesid(highligh.getInvbalancesid());
		

		listItemHighlightDAO.insertUpdate(highlightForm.getInvbalancesHighlightLog());
		this.updateInvbalances(highlightForm.getMroInvbalancesHighlightV().getInvbalancesid(),
				person, highlightDate);
		return true;
	}

	@Transactional(readOnly = false)
	public void deleteHighLightLog(
			InvbalancesHighlightLog invbalancesHighlightLog, Person person) {
		invbalancesHighlightLog.setDeleted(new BigDecimal("1"));
		invbalancesHighlightLog.setDeletedBy(person.getPersonId());
		invbalancesHighlightLog.setDeletedUpdate(new Date(System
				.currentTimeMillis()));
		listItemHighlightDAO.insertUpdate(invbalancesHighlightLog);
	}

	@Transactional(readOnly = false)
	public Pr onHighLightToPr(Person person,String requestedby2, String prtype,String itemnum,
			String siteId,String deptcode,BigDecimal changeQty,boolean signFlag,
			GlobalGrowl message) {
		return applyItemPrBo.onAutoPr(person,requestedby2, prtype, itemnum, siteId, deptcode, 
				changeQty, signFlag, "安全控管量Monitor機制下修",ActiveFlag.Y, message);
	}
	
	@Transactional(readOnly = false)
	public void updateNonHighLight(){
		listItemHighlightDAO.updateNonHighLight();
	}
}