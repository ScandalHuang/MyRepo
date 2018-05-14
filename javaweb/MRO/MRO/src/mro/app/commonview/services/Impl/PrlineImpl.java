package mro.app.commonview.services.Impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.app.commonview.bo.ListClassstructureBO;
import mro.app.commonview.bo.ListItemCommonBO;
import mro.app.commonview.services.PrlineInterfaces;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.entity.PrMcParameter;
import mro.base.bo.BsstrkhistBO;
import mro.base.bo.ClassstructureSubinventoryBO;
import mro.base.bo.FunctionBO;
import mro.base.bo.InvbalancesBO;
import mro.base.bo.InventoryBO;
import mro.base.bo.MatusetransHalfBO;
import mro.base.bo.PrControlConfigBO;
import mro.base.entity.Bsstrkhist;
import mro.base.entity.Classstructure;
import mro.base.entity.ClassstructureSubinventory;
import mro.base.entity.Invbalances;
import mro.base.entity.Inventory;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.MatusetransHalf;
import mro.base.entity.Pr;
import mro.base.entity.PrControlConfig;
import mro.base.entity.Prline;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class PrlineImpl implements PrlineInterfaces{
	
	@Override
	public void setPrline(Pr pr, Prline prline, Item item) {
		ListItemCommonBO listItemCommonBO=SpringContextUtil.getBean(ListItemCommonBO.class);
		ListClassstructureBO listClassstructureBO=SpringContextUtil.getBean(ListClassstructureBO.class);
		
		ItemAttribute itemAttribute = listItemCommonBO.getItemAttribute(item.getItemid().intValue());
		Classstructure classstructure = listClassstructureBO.getClassstructure(item.getClassstructureid());

		Date date = new Date(System.currentTimeMillis());
		prline.setStoreCategory(classstructure.getStoreCategory());
		prline.setMaxidledays(item.getMaxidledays());
		prline.setItemstatus(item.getStatus());
		prline.setItemnum(item.getItemnum());
		prline.setDescription(item.getDescription());
		prline.setCommodity(item.getCommodity());
		prline.setCommoditygroup(item.getCommoditygroup());
		prline.setClassstructureid(item.getClassstructureid());
		prline.setItemsetid(item.getItemsetid());
		prline.setEnterdate(date); // 建立日期
		prline.setRequestedby2(pr.getRequestedby2());
		prline.setEnterby(pr.getChangeby()); // 異動人帳號
		prline.setRequestedby(pr.getRequestedby());
		prline.setMcOrderQuantity(itemAttribute.getMcOrderQuantity());
		prline.setMcMinPackageQuantity(itemAttribute.getMcMinPackageQuantity());
		prline.setOrideliverytime(itemAttribute.getDeliverytime());
		prline.setDeliverytime(itemAttribute.getDeliverytime());
		//if R2 listItemCommonBO.getR2ItemLt(item.getItemnum())
		if(item.getCommoditygroup().equals("R2")){
			Map ltMap = listItemCommonBO.getR2ItemLt(item.getItemnum());
			if(ltMap!=null) {
				prline.setDeliverytime((BigDecimal)ltMap.get("ITEM_LT"));
				prline.setOrideliverytime((BigDecimal)ltMap.get("ITEM_LT"));
			}
		}
		prline.setChangeDeliverytimeRemark("");
		prline.setChangeUnitcostRemark("");
		prline.setCurrencycode(itemAttribute.getCurrencyCode());
		prline.setMinBasicUnit(itemAttribute.getMinBasicUnit());
		prline.setPackageUnit(itemAttribute.getPackageUnit());
		prline.setTransferQuantity(itemAttribute.getTransferQuantity());
		prline.setVendor(null);
		
		this.setStoreCategory(prline); //預設倉別
		this.setPrice(pr, prline, item, itemAttribute);//價格
		this.setInventory(prline);
		this.setPrControlConfig(prline); //cmommcontrol空值才更新
		this.setInvbalances(pr, prline); //控管資訊
		this.newminlevel(pr, prline);  //更新總價 & 控管量資訊整理()
	}

	@Override
	public void newminlevel(Pr pr, Prline prline) {
		prline.setPmreqqty(Utility.nvlBigDecimal(prline.getPmreqqty(), "0"));
		prline.setUnitcost(Utility.nvlBigDecimal(prline.getUnitcost(), "0"));
		prline.setMinlevel(Utility.nvlBigDecimal(prline.getMinlevel(), "0"));
	
		if (Utility.equals(pr.getPrtype(), PrType.R1CONTROL)) { // R1耗材控管申請
			this.r1Control(prline);
		} else if (Utility.equals(pr.getPrtype(),PrType.R2CONTROL.name())) { // R2需求控管
			this.r2Control(prline);
		} else {
			prline.setLinecost(prline.getUnitcost().multiply(prline.getPmreqqty()));
		}
	}
	
	@Override
	public void r2Control(Prline prline) {
		PrMcParameter prMcParameter=new PrMcParameter(PrType.R2CONTROL.name());
		prline.setDavguseqty((Utility.nvlBigDecimal(prline.getDavguseqty(), "0")));
		prline.setSstock((Utility.nvlBigDecimal(prline.getSstock(), "0")));

		prline.setNewavguseqty(prline.getOriavguseqty().add(prline.getDavguseqty()));
		prline.setNewsstock(prline.getOrisstock().add(prline.getSstock()));
		prline.setOriminlevel(prline.getOriavguseqty().multiply(
				prline.getDeliverytime().divide(new BigDecimal("30"), 2,BigDecimal.ROUND_HALF_UP)
				.add(prMcParameter.getMcBuyerWorkTime()))
				.add(prline.getOrisstock()));
		prline.setMinlevel(prline.getDavguseqty().multiply(
				prline.getDeliverytime().divide(new BigDecimal("30"), 2,BigDecimal.ROUND_HALF_UP)
				.add(prMcParameter.getMcBuyerWorkTime()))
				.add(prline.getSstock()));
		prline.setNewminlevel(prline.getOriminlevel().add( // 四捨五入取整數
				prline.getMinlevel()).setScale(0, BigDecimal.ROUND_HALF_UP));
		prline.setLinecost(prline.getUnitcost().multiply(prline.getMinlevel()));
		setMultipleAvghdqtyThree(prline,prline.getNewavguseqty());
		
	}

	@Override
	public void r1Control(Prline prline) {
		prline.setNewminlevel(prline.getOriminlevel().add(prline.getMinlevel()));
		prline.setLinecost(prline.getUnitcost().multiply(prline.getMinlevel()));
		setMultipleAvghdqtyThree(prline,prline.getNewminlevel());
	}
	
	@Override
	public void setMultipleAvghdqtyThree(Prline prline,BigDecimal minlevel) {
		if(prline.getAvghdqtyThree()!=null &&
			prline.getAvghdqtyThree().compareTo(new BigDecimal("0"))==1){
			prline.setMultipleAvghdqtyThree(minlevel.divide(
			prline.getAvghdqtyThree(),2, BigDecimal.ROUND_HALF_UP));
		}else{
			prline.setMultipleAvghdqtyThree(new BigDecimal("0"));
		}
	}

	@Override
	public void setStoreCategory(Prline prline) {
		if (prline.getStoreCategory().equals(SystemConfig.ZERS)) {
			ClassstructureSubinventoryBO bo = SpringContextUtil.getBean(ClassstructureSubinventoryBO.class);
			ClassstructureSubinventory c = Utility.nvlEntity(
					bo.getSubinventor(prline.getClassstructureid(),prline.getSiteid()),
					ClassstructureSubinventory.class);
			prline.setStoreroom(c.getSubinventory());
		}
	}

	@Override
	public void setPrice(Pr pr,Prline prline,Item item,ItemAttribute itemAttribute) {
		FunctionBO functionBO=SpringContextUtil.getBean(FunctionBO.class);
		prline.setUnitcost(functionBO.getUnicost(item.getItemnum()));
		prline.setOriunitcost(prline.getUnitcost());
	}
	@Override
	public void setInventory(Prline prline) {
		InventoryBO inventoryBO = SpringContextUtil.getBean(InventoryBO.class);
		Inventory inventory = (Inventory) Utility.nvlEntity(
				inventoryBO.getInventory(prline.getItemnum(),
						prline.getItemsetid(), prline.getSiteid(),
						prline.getStoreroom()), Inventory.class);
		prline.setCategory(inventory.getCategory()); // 庫存分類
		prline.setMccommand(inventory.getMccommand()); // 庫存分類變更說明
		prline.setCmommcontrol(inventory.getCmommcontrol()); //控管模式
		
	}
	@Override
	public void setPrControlConfig(Prline prline) {
		if(StringUtils.isBlank(prline.getCmommcontrol())){
			PrControlConfigBO bo = SpringContextUtil.getBean(PrControlConfigBO.class);
			PrControlConfig prControlConfig = (PrControlConfig) Utility.nvlEntity(
			bo.getPrControlConfigBySiteId(prline.getItemnum(),
						prline.getSiteid()), PrControlConfig.class);
			prline.setCmommcontrol(prControlConfig.getControlType()); //控管模式
		}
		
	}
	@Override
	public void setInvbalances(Pr pr,Prline prline) {
		InvbalancesBO invbalancesBO=SpringContextUtil.getBean(InvbalancesBO.class);
		Invbalances invbalances = Utility.nvlEntity(invbalancesBO.getInvbalance(
				prline.getItemnum(),prline.getMDept(), prline.getStoreroom(),prline.getSiteid()),Invbalances.class);

		boolean controlFlag=Utility.equalsOR(pr.getPrtype(), PrType.R1CONTROL,PrType.R2CONTROL);
		
		if (Utility.equalsOR(pr.getPrtype(), PrType.R1CONTROL,PrType.R1PMREQ)) {
			this.setMatusetransHalf(pr, prline, invbalances,controlFlag);
		}else if (Utility.equalsOR(pr.getPrtype(), PrType.R2CONTROL,PrType.R2PMREQ)) {
			this.setbsstrkhost(pr, prline, invbalances, controlFlag);
		}
	}
	@Override
	public void setMatusetransHalf(Pr pr,Prline prline,Invbalances invbalances,boolean controlFlag) {
		MatusetransHalfBO matusetransHalfBO=SpringContextUtil.getBean(MatusetransHalfBO.class);
		FunctionBO functionBO=SpringContextUtil.getBean(FunctionBO.class);
		
		MatusetransHalf matusetransHalf=Utility.nvlEntity(
			matusetransHalfBO.getMatusetransHalf(prline.getItemnum(), 
			prline.getStoreroom(),pr.getMDept(),pr.getSiteid()),MatusetransHalf.class);
					
		prline.setHalfyearIssueCounter(Utility.nvlBigDecimal(
			matusetransHalf.getHalfyearIssueCounter(),"0"));
			
		if (controlFlag) {
			prline.setOriminlevel(Utility.nvlBigDecimal(invbalances.getMinlevel(), "0"));
			prline.setMaxhdqty(Utility.nvlBigDecimal(matusetransHalf.getMaxhdqty(),"0"));
			prline.setAvghdqty(Utility.nvlBigDecimal(matusetransHalf.getAvghdqty(),"0"));
			prline.setMaxhdqtyThree(Utility.nvlBigDecimal(matusetransHalf.getMaxhdqtyThree(),"0"));
			prline.setAvghdqtyThree(Utility.nvlBigDecimal(matusetransHalf.getAvghdqtyThree(),"0"));
			// ==============近半年耗用月次數[主+替] =============================
			prline.setSumofissuecounter(functionBO.getSumofcounter(prline.getItemnum(), pr.getMDept()));
		}
	}

	@Override
	public void setbsstrkhost(Pr pr, Prline prline, Invbalances invbalances,boolean controlFlag) {
		BsstrkhistBO bsstrkhistBO=SpringContextUtil.getBean(BsstrkhistBO.class);
		FunctionBO functionBO=SpringContextUtil.getBean(FunctionBO.class);
		Bsstrkhist bsstrkhist = (Bsstrkhist) Utility.nvlEntity(
				bsstrkhistBO.getBsstrkhist(prline.getItemnum(),
				pr.getMDept(), pr.getSiteid()),Bsstrkhist.class);
		prline.setHdqty(Utility.nvlBigDecimal(bsstrkhist.getHdqty(), "0"));
		prline.setUsecounter(Utility.nvlBigDecimal(bsstrkhist.getUsecounter(), "0"));
		prline.setMaxhdqty(Utility.nvlBigDecimal(bsstrkhist.getMaxhdqty(),"0"));
		prline.setAvghdqty(Utility.nvlBigDecimal(bsstrkhist.getAvghdqty(),"0"));
		prline.setHdqtyThree(Utility.nvlBigDecimal(bsstrkhist.getHdqtyThree(), "0"));
		prline.setMaxhdqtyThree(Utility.nvlBigDecimal(bsstrkhist.getMaxhdqtyThree(),"0"));
		prline.setAvghdqtyThree(Utility.nvlBigDecimal(bsstrkhist.getAvghdqtyThree(),"0"));
		if (controlFlag) {
			InventoryBO inventoryBO = SpringContextUtil.getBean(InventoryBO.class);
			Inventory inventory = (Inventory) Utility.nvlEntity(
					inventoryBO.getInventory(prline.getItemnum(),
							prline.getItemsetid(), prline.getSiteid(),
							prline.getStoreroom()), Inventory.class);
			//======================控管需求====================
			// 原平均月耗用量
			prline.setOriavguseqty(Utility.nvlBigDecimal(invbalances.getOriavguseqty(), "0"));
			// 原 最低安全存量
			prline.setOrisstock(Utility.nvlBigDecimal(invbalances.getSstock(),"0"));
			// 廠區設定值
			prline.setInvsstock(Utility.nvlBigDecimal(inventory.getSstock(),"0"));
			prline.setDavguseqty(new BigDecimal(0));
			prline.setSstock(new BigDecimal(0));
			// ==============近半年耗用月次數[主+替] =============================
			prline.setSumofusecounter(functionBO.getSumofcounter(prline.getItemnum(), pr.getMDept()));
			// =========================近半年部門耗用金額====================================
			prline.setHdcost(Utility.nvlBigDecimal(bsstrkhist.getHdcost(),"0"));
			prline.setMaxhdcost(Utility.nvlBigDecimal(bsstrkhist.getMaxhdcost(), "0"));
			prline.setAvghdcost(Utility.nvlBigDecimal(bsstrkhist.getAvghdcost(), "0"));
			prline.setHdcostThree(Utility.nvlBigDecimal(bsstrkhist.getHdcostThree(),"0"));
			prline.setMaxhdcostThree(Utility.nvlBigDecimal(bsstrkhist.getMaxhdcostThree(), "0"));
			prline.setAvghdcostThree(Utility.nvlBigDecimal(bsstrkhist.getAvghdcostThree(), "0"));
		}
		
	}


}
