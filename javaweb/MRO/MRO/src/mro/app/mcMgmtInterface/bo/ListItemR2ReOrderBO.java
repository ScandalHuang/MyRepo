package mro.app.mcMgmtInterface.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import mro.app.commonview.dao.ListItemCommonDAO;
import mro.app.commonview.services.Impl.PrlineImpl;
import mro.app.mcMgmtInterface.dao.ListItemR2ReOrderDAO;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.LocationMapBO;
import mro.base.bo.PrBO;
import mro.base.entity.Item;
import mro.base.entity.LocationMap;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.base.entity.PrlineAssigned;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@Component
@Scope("prototype")
public class ListItemR2ReOrderBO {

	private ListItemR2ReOrderDAO listItemR2ReOrderDAO;
	private ListItemCommonDAO listItemCommonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		listItemR2ReOrderDAO = new ListItemR2ReOrderDAO(sessionFactory);
		listItemCommonDAO = new ListItemCommonDAO(sessionFactory);
	}

	@Transactional(readOnly = false)
	public void updateItem(Item item, String reorderDisableFlag, String empNo) {
//		item.setReorderDisableFlag(reorderDisableFlag);
//		item.setReorderLastUpdateBy(empNo);
//		item.setReorderLastUpdate(new Date(System.currentTimeMillis()));
		listItemR2ReOrderDAO.insertUpdate(item);
	}

	@Transactional(readOnly = true)
	public List getR2InventoryList(String[] siteid,String locationSite, String prtype,String itemnum,
			String cmommcontrol) {
		List<Map> list=new ArrayList<Map>();
		StringBuffer condition=new StringBuffer();

		if (StringUtils.isNotBlank(itemnum)) {
			if(itemnum.indexOf("!=")!=-1)
				condition.append("and itemnum not like '" + itemnum.replace("!=", "") + "%' ");
			else
				condition.append("and itemnum like '" + itemnum + "%' ");
		}
		if (StringUtils.isNotBlank(locationSite)) {
			condition.append("and exists (select 1 from location_map "
					+ "where site_id=siteid and location_Site='"+locationSite+"') ");
		}
		if (StringUtils.isNotBlank(cmommcontrol)) {
			condition.append("and cmommcontrol = '" + cmommcontrol + "' ");
		}
		if (siteid!=null && siteid.length>0) {
			String value=Stream.of(siteid).map(l->"'"+l+"'").collect(Collectors.joining(", "));
			condition.append("and siteid in (" + value + ") ");
		}
		if (StringUtils.isNotBlank(prtype)) {
			condition.append("and CLASSSTRUCTUREID in (select CLASSSTRUCTUREID  "
					+ "from CLASSSTRUCTURE_PRTYPE where PRTYPE ='"+ prtype + "')");
			condition.append("and itemnum like '" + ItemType.getItemType(prtype).name() + "%' ");
		}
		return listItemR2ReOrderDAO.getR2InventoryList(condition.toString());
	}

	@Transactional(readOnly = false)
	public void onReorderTransferToPr(Map<String, List> reorderMap,
			String userid, Person person, String prtype, Date needDate) {
		PrBO prBO=SpringContextUtil.getBean(PrBO.class);
		LocationMapBO locationMapBO=SpringContextUtil.getBean(LocationMapBO.class);
		PrlineImpl prlineImpl=new PrlineImpl();
		Date date = new Date(System.currentTimeMillis());
		Iterator iter = reorderMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			List<Map> list = (List<Map>) entry.getValue();
			LocationMap locationMap = Utility.nvlEntity(
					locationMapBO.getLocationMapBySId(entry.getKey().toString()),LocationMap.class);
			// ========================開立pr==================================================
			Pr pr = new Pr();
			pr.setPrnum(prBO.getPrNum(prtype));// 申請單編號
			pr.setIssuedate(date); // 申請日期
			pr.setRequestedby(person.getPersonId());
			pr.setRequestedby2(person.getPersonId());
			pr.setLastSigner(person.getPersonId());
			pr.setStatus(SignStatus.APPR.toString());
			pr.setStatusdate(date); // 異動日期
			pr.setChangedate(date); // 異動日期
			pr.setChangeby(userid);
			pr.setSiteid(entry.getKey().toString());
			pr.setOrgid(locationMap.getOrg());
			pr.setCurrencycode(SystemConfig.defaultCurrency);
			pr.setDeptcode(person.getDeptCode());
			pr.setMDept(person.getMDeptCode());
			pr.setDeptsupervisor(person.getSupervisor());
			pr.setPrtype(prtype);
			pr.setInternal(1);
			pr.setNovendor(1);
			pr.setLangcode(SystemConfig.LANGCODE_ZH);
//			pr.setCmopriority("一般");   //2015/05/06  測試報告中決議取消 
			pr.setTotalcost(new BigDecimal(0));
			pr.setTotalbasecost2(new BigDecimal(0));
			listItemR2ReOrderDAO.insertUpdate(pr);

			// ======================================PrLine==============================================
			int lineNum=1;  //prline項次
			for (Map prlineMap : list) {
				List<PrlineAssigned> prlineAssigneds=this.getNewPrlineAssigneds(pr, prlineMap);
				for(PrlineAssigned l:prlineAssigneds){
					if(l.getRateQty().compareTo(new BigDecimal(0))==0){//如果訂購為0，不需開單
						l.setPrline(null);
						continue; 
					}
					l.getPrline().setPmreqqty(l.getRateQty());
					prlineImpl.setPrline(pr, l.getPrline(), listItemCommonDAO.getItem(l.getItemnum()));
					l.getPrline().setLocation(l.getLocation());
					l.getPrline().setReqdeliverydate(needDate);
					l.getPrline().setCmoremark("重訂購使用");
					l.getPrline().setInspectionEmpno(l.getLastrequestedby2());
					l.getPrline().setInspectionDeptCode(l.getDeptCode());
					l.getPrline().setPrlinenum(lineNum);
					// ===============ERP PR Line Note to Buyer================
					String value=ParameterType.getParameterValue(prlineMap.get("CMOMMCONTROL").toString());
					if(StringUtils.isNotBlank(value)) l.getPrline().setNoteToAgent(value);
					// ====================總價(total)===================================
					pr.setTotalbasecost2(pr.getTotalbasecost2().add(l.getPrline().getLinecost()));
					// =======================================================
					listItemR2ReOrderDAO.insertUpdate(l.getPrline());
					lineNum++;
				}
				listItemR2ReOrderDAO.insertUpdate(prlineAssigneds);
			}
			listItemR2ReOrderDAO.insertUpdate(pr); //更新總價
		}
	}

	@Transactional(readOnly = false)
	public List<PrlineAssigned> getNewPrlineAssigneds(Pr pr,Map prlineMap) {
		List<PrlineAssigned> prlineAssigneds=new ArrayList<PrlineAssigned>(); //組成PrlineAssigned
		PrlineAssigned prlineAssigned = new PrlineAssigned(pr,new Prline(pr));
		prlineAssigned.setItemnum(prlineMap.get("ITEMNUM").toString());
		prlineAssigned.setLocation(prlineMap.get("LOCATION").toString());
		prlineAssigned.setLastrequestedby2(ObjectUtils.toString(prlineMap.get("INSPECTOR"),pr.getRequestedby()));
		prlineAssigned.setCreateDate(new Date(System.currentTimeMillis()));
		prlineAssigned.setDeptCode(prlineMap.get("DEPTCODE").toString());
		prlineAssigned.setPrqty((BigDecimal)prlineMap.get("INVBALANCES_PRQTY"));
		prlineAssigned.setPoqty((BigDecimal)prlineMap.get("INVBALANCES_POQTY"));
		prlineAssigned.setMinlevel((BigDecimal)prlineMap.get("NEW_MINLEVEL"));
		prlineAssigned.setCmommcontrol(prlineMap.get("CMOMMCONTROL").toString());
		prlineAssigned.setDemandQty((BigDecimal) prlineMap.get("DEMAND_QTY"));
		prlineAssigned.setReorderQty((BigDecimal) prlineMap.get("REORDER_QTY"));
		prlineAssigned.setMcMinPackageQuantity((BigDecimal) prlineMap.get("MC_MIN_PACKAGE_QUANTITY"));
		prlineAssigned.setRate((BigDecimal) prlineMap.get("NEW_RATE"));
		prlineAssigned.setRateQty((BigDecimal) prlineMap.get("NEW_RATE_QTY"));
		prlineAssigneds.add(prlineAssigned);
		return prlineAssigneds;
	}
}
