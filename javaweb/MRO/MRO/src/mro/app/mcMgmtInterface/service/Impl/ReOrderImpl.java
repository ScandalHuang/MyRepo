package mro.app.mcMgmtInterface.service.Impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.app.mcMgmtInterface.bo.ListItemR2ReOrderBO;
import mro.app.mcMgmtInterface.form.ReorderForm;
import mro.app.mcMgmtInterface.service.ReOrderInterface;
import mro.app.mcMgmtInterface.utils.ListItemReOrderUtils;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.PrType;
import mro.base.entity.Person;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class ReOrderImpl implements ReOrderInterface {

	@Override
	public void setParamter(ReorderForm form) {
		form.setControlMap(ParameterType.CONTROL_TYPE.getOption());
		form.setPrtypeMap(PrType.getReorderOption());
	}
	@Override
	public List onReOrderProcess(Person person,List<Map> list) {
		//==================invbalacnes new minlevel 加總==========================
				Map<BigDecimal, BigDecimal> allNewMinlevel=new HashMap<BigDecimal, BigDecimal>(); //
				Map<BigDecimal, BigDecimal> allReorderQty=new HashMap<BigDecimal, BigDecimal>();
				list.stream().forEach(l ->{
					BigDecimal defult=new BigDecimal("0");
					BigDecimal inventoryid=new BigDecimal(l.get("INVENTORYID").toString());
					BigDecimal minlevel=new BigDecimal(l.get("NEW_MINLEVEL").toString());
					minlevel=minlevel.compareTo(defult)!=1?defult:minlevel; //小於0為0
					allNewMinlevel.put(inventoryid,allNewMinlevel.get(inventoryid)==null?
							minlevel:allNewMinlevel.get(inventoryid).add(minlevel));
					allReorderQty.put(inventoryid,new BigDecimal(l.get("REORDER_QTY").toString()));
					//================驗收人=====================
					if(l.get("INSPECTOR")==null){
						l.put("INSPECTOR", person.getPersonId());
						l.put("INSPECTOR_DEPTCODE", person.getMDeptCode());
					}
				});
				//===============計算實際用量===================
				list.stream().forEach(l->{
					BigDecimal defult=new BigDecimal("0");
					BigDecimal reorderQty=new BigDecimal(l.get("REORDER_QTY").toString());
					BigDecimal inventoryid=new BigDecimal(l.get("INVENTORYID").toString());
					BigDecimal minlevel=new BigDecimal(l.get("NEW_MINLEVEL").toString());
					minlevel=minlevel.compareTo(defult)!=1?defult:minlevel; //小於0為0
					BigDecimal McMinPackageQuantity=new BigDecimal(l.get("MC_MIN_PACKAGE_QUANTITY").toString());
					BigDecimal oriRate=minlevel.compareTo(defult)==0?defult:
							minlevel.divide(allNewMinlevel.get(inventoryid), 3,BigDecimal.ROUND_HALF_UP);
					l.put("ORI_RATE", oriRate); //原始RATE
					//=========================實際比率與數量(正一處理方式為遞減法)==========================
					//20160812 INVBALANCE_ADJUS_FLAG=N 代表維持原本的量
					if(l.get("INVBALANCE_ADJUST_FLAG").toString().equals("N")){ 
						l.put("NEW_RATE",l.get("ORI_RATE"));
						l.put("NEW_RATE_QTY",minlevel);
						return; //stream.foreach底下 return==continute 
					}
					BigDecimal tempReorderQty=allReorderQty.get(inventoryid);
					BigDecimal rateQty = reorderQty.multiply(oriRate)
							.divide(McMinPackageQuantity, 0,BigDecimal.ROUND_CEILING)
							.multiply(McMinPackageQuantity);
					rateQty=rateQty.compareTo(tempReorderQty) == 1 ? tempReorderQty : rateQty;
					l.put("NEW_RATE_QTY",rateQty);
					l.put("NEW_RATE",rateQty.divide(reorderQty, 3,BigDecimal.ROUND_HALF_UP));
					tempReorderQty=tempReorderQty.subtract(rateQty);
					allReorderQty.put(inventoryid,tempReorderQty.compareTo(defult) == -1 ? defult : tempReorderQty);
				});
				//==========開單數量為0不顯示================
				return (List)list.stream().filter(l->((BigDecimal)l.get("NEW_RATE_QTY")).compareTo(
						new BigDecimal("0"))==1).collect(Collectors.toList());
	}

	@Override
	public void search(ReorderForm form,Person person) {
		ListItemR2ReOrderBO listItemR2ReOrderBO=SpringContextUtil.getBean(ListItemR2ReOrderBO.class);
		form.inital();
		List list=listItemR2ReOrderBO.getR2InventoryList(
				form.getSelectOrganization(),form.getsLocationSite(),
				form.getsPrtype(),form.getSelectitemnum(),
				form.getSelectcmommcontrol()); 
		form.setListReOrderItem(onReOrderProcess(person, list));
	}

	@Override
	public void onReOrderToPR(ReorderForm form, Person person,String userId,GlobalGrowl message) {
		if(ListItemReOrderUtils.validate(form.getSelectlistReOrderItem(),message)){
			Map<String,List> map=ListItemReOrderUtils.onReorderTransferToPr(form.getSelectlistReOrderItem());
			ListItemR2ReOrderBO listItemR2ReOrderBO=new SpringContextUtil().getBean(ListItemR2ReOrderBO.class);
			listItemR2ReOrderBO.onReorderTransferToPr(map, userId
					, person, form.getsPrtype(),form.getNeedDate());
			this.search(form,person);
			message.addInfoMessage("ReOrder", "ReOrder successful.");
		}
		
	}


}
