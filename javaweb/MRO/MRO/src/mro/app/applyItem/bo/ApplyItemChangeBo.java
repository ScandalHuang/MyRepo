package mro.app.applyItem.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.applyItem.dao.ApplyItemChangeDao;
import mro.app.applyItem.dao.ApplyItemDao;
import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.applyItem.vo.ListAItemspecVO;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.AssetattributeBO;
import mro.base.bo.VwNewvendorcodeEpmallBO;
import mro.base.entity.AInvvendor;
import mro.base.entity.AItem;
import mro.base.entity.AItemAttribute;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.AItemspec;
import mro.base.entity.Assetattribute;
import mro.base.entity.Invvendor;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemSecondItemnum;
import mro.base.entity.Itemspec;
import mro.base.entity.VwNewvendorcodeEpmall;
import mro.form.ItemForm;
import mro.utility.ValidationUtils;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;

@Component
@Scope("prototype")
public class ApplyItemChangeBo {
	
	private ApplyItemChangeDao applyItemChangeDao;
	private ApplyItemDao applyItemDao;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		applyItemChangeDao = new ApplyItemChangeDao(sessionFactory);
		applyItemDao = new ApplyItemDao(sessionFactory); 
	}
	
	@Transactional(readOnly = true)
	public Map getBuyerSignMap() {
		Map map=new LinkedHashMap<>();
		List list=applyItemChangeDao.getBuyerSignList();
		for(Object o:list){
			map.put(o, o);
		}
		return map;
	}
	
	@Transactional(readOnly = false)
	public void onApplyDelete(AItem aItem) {
		int u=applyItemChangeDao.updateItem(aItem.getOriitemnum(),SignStatus.ACTIVE);
		if(u==1){//更新成功
			applyItemDao.deleteAInvvedor(aItem.getEaudittransid());
			applyItemDao.deleteAItemSecondItemnum(aItem.getEaudittransid());
			applyItemDao.deleteAItemspec(aItem.getItemnum());
			applyItemDao.deleteAItemAttribute(aItem.getEaudittransid().toString());
			applyItemDao.delete(aItem);
		}
	}
	@Transactional(readOnly = true)
	public AItemAttribute getItemAttributeToAItemAttribute(int itemid) {

		StringBuffer condition = new StringBuffer();

		condition.append("and ITEMID = " + itemid + " ");
		
		ItemAttribute i=applyItemChangeDao.getItemAttribute(condition.toString());
		AItemAttribute a= new AItemAttribute();
		if(i!=null) {BeanUtils.copyProperties(i,a);}
		return a;
	}
	@Transactional(readOnly = true)
	public BigDecimal getpreEaudittransid(BigDecimal itemid) {

		StringBuffer condition = new StringBuffer();

		condition.append("and ITEMID = " + itemid + " ");
		
		ItemAttribute i=applyItemChangeDao.getItemAttribute(condition.toString());
		return i.getEaudittransid();
	}
	@Transactional(readOnly=true)
    public List<AItem> getAItemList(String name) {
    	
		StringBuffer condition=new StringBuffer(); 
		condition.append("and EAUDITUSERNAME = '" + name + "' ");
		condition.append("and STATUS != '" + SignStatus.APPR + "' ");
		condition.append("and ORIITEMNUM is not null ");
	   return applyItemChangeDao.getAItemList(condition.toString());
	}
	
	/* 產生料號規格異動清單 by spec
	 * hongjie.wu
	 * 2013/11/4
	 * */
	@Transactional(readOnly=true)
    public List<AItem> getAItemListJoinSimple(String classstrctureID,String status,String name) {
    	
		StringBuffer condition=new StringBuffer(); 
		if (StringUtils.isNotBlank(name)) {
			condition.append("and A.EAUDITUSERNAME = '" + name + "' ");
		}	
		if (StringUtils.isNotBlank(classstrctureID)) {
			condition.append("and A.CLASSSTRUCTUREID = '" + classstrctureID + "' ");
		}
		if (StringUtils.isNotBlank(status)) {
			condition.append("and A.STATUS = '" + status + "' ");
		}else{
			condition.append("and A.STATUS in ('NEW','INPRG') ");
		}
		condition.append("AND B.APPLY_NUM IS not NULL ");
		condition.append("and A.ORIITEMNUM is not null ");
	   return applyItemChangeDao.getAItemListJoinSimple(condition.toString());
	}
	
	@Transactional(readOnly = true)
	public List<Item> getItemList(String deptId) {
 
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(deptId)) {
			condition.append("and itemnum in " +
					"(select itemnum from invbalances where binnum='"+deptId+"' )");
		}

		return applyItemChangeDao.getItemList(condition.toString());
	}
	@Transactional(readOnly = true)
	public int getChangeItemCount(BigDecimal itemid,Date createDate){
				return applyItemChangeDao.getChangeItemCount(itemid,createDate).intValue();
	}
	@Transactional(readOnly = true)
	public BigDecimal getSecondItemChangeCount(BigDecimal preEaudittransid,BigDecimal afterEaudittransid){
		return applyItemChangeDao.getSecondItemChangeCount(preEaudittransid,afterEaudittransid);
	}
	@Transactional(readOnly = true)
	public BigDecimal getItemVendorChangeCount(BigDecimal preEaudittransid,BigDecimal afterEaudittransid){
		return applyItemChangeDao.getItemVendorChangeCount(preEaudittransid,afterEaudittransid);
	}
	@Transactional(readOnly = true)
	public List<Object[]> getItemSpecChangeCount(BigDecimal preEaudittransid,BigDecimal afterEaudittransid){
		return applyItemChangeDao.getItemSpecChange(preEaudittransid,afterEaudittransid);
	}
	@Transactional(readOnly = true)
	public List<ListAInvvendorVO> getInvvendorToAInvvendorList(String itemid) {
		VwNewvendorcodeEpmallBO vwNewvendorcodeEpmallBO=SpringContextUtil.getBean(VwNewvendorcodeEpmallBO.class);
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(itemid)) {
			condition.append("and itemid ="+itemid);
		}
		condition.append(" and DISABLED=0 ");
		List<ListAInvvendorVO> ainv=new ArrayList();
		List<Invvendor> inv=applyItemChangeDao.getInvvendorList(condition.toString());
		
			for(Invvendor i:inv){
				ListAInvvendorVO l=new ListAInvvendorVO();
				BeanUtils.copyProperties(i,l);
				VwNewvendorcodeEpmall vwNewvendorcodeEpmall=vwNewvendorcodeEpmallBO.getListByNvcId(i.getVendor());
				if(vwNewvendorcodeEpmall!=null){
					l.setNewvendorname(vwNewvendorcodeEpmall.getNewvendorname());
					l.setRegistrationnum(vwNewvendorcodeEpmall.getRegistrationnum());
				}
				ainv.add(l);
			}
		return ainv;
	}
	@Transactional(readOnly = true)
	public List<AItemSecondItemnum> getItemSecondItemnumToAItemSecondItemnumList(String itemid) {
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(itemid)) {
			condition.append("and itemid ="+itemid);
		}
		List<AItemSecondItemnum> aSecondItemnum=new ArrayList();
		List<ItemSecondItemnum> secondItemnum=applyItemChangeDao.getItemSecondItemnumList(condition.toString());
		
			for(ItemSecondItemnum s:secondItemnum){
				AItemSecondItemnum a=new AItemSecondItemnum();
				BeanUtils.copyProperties(s,a);
				aSecondItemnum.add(a);
			}
		return aSecondItemnum;
	}
	@Transactional(readOnly = true)
	public List<ListAItemspecVO> getItemspecToItemspecList(String itemid,String classstructureid) {
		AssetattributeBO assetattributeBO=SpringContextUtil.getBean(AssetattributeBO.class);
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(itemid)) {
			condition.append("and itemid ="+itemid);
		}
		List<ListAItemspecVO> aspec=new ArrayList();
		List<Itemspec> spec=applyItemChangeDao.getItemspecList(condition.toString());
		Map<String,Assetattribute> assetattributeMap=assetattributeBO.getMap(
				classstructureid, Itemspec.class);
		for(Itemspec i:spec){
			ListAItemspecVO l=new ListAItemspecVO();
			BeanUtils.copyProperties(i,l);
			Assetattribute a=assetattributeMap.get(i.getAssetattrid());
			if(a!=null){
				l.setDescription(a.getDescription());
//				l.setDatatype(a.getDatatype());  //2014/07/21 dataType已經儲存在itemspec
				l.setDecimalplaces(a.getDecimalplaces());
			}
			aspec.add(l);
		}
		return aspec;
	}
	
	@Transactional(readOnly = false)
	public void onApplySave(ItemForm itemForm, BigDecimal Eaudittransid,String itemnum,String status) {
		int u=0;
		Date date=new Date(System.currentTimeMillis());
		if (itemForm.getaItem().getEaudittransid() == null) {
			itemForm.getaItem().setEaudittransid(Eaudittransid);
			itemForm.getaItem().setItemnum(itemnum);
			itemForm.getaItem().setCreateDate(date);
		}else{  //驗證申請單是否在流程中
			ValidationUtils.validateStatus(itemForm.getaItem().getItemnum(), 
					itemForm.getaItem().getTaskId(),ItemStatusType.TYPE_PROCESS_AIS);
		}
			
		if(status.equals("submit")){
			u=applyItemChangeDao.updateItem(itemForm.getaItem().getOriitemnum(),SignStatus.CHANGED);
		}
			// ==============================================================================
//			itemForm.getaItem().setDescription(ApplyUtils.getItemDescription(itemForm.getaItem(),itemForm.getListAItemspecVO()));// 品名
//			itemForm.getaItem().setLongDescription(ApplyUtils.getLongItemDescription(itemForm.getListAItemspecVO()));// 品名
//			itemForm.getaItem().setValidateDescription(ApplyUtils.getValidateDescription(itemForm.getListAItemspecVO()));//驗證品名
//			itemForm.getaItem().setIssueunit(itemForm.getaItem().getOrderunit()); // 採購單位==領料單位
			itemForm.getaItem().setEaudittimestamp(date);
			if(u==1){//異動成功
				itemForm.getaItem().setStatus(SignStatus.INPRG.toString());
			}
			applyItemDao.insertUpdate(itemForm.getaItem()); // AItem新增
			// ===============料號規格新增================================
			applyItemDao.deleteAItemspec(itemForm.getaItem().getItemnum()); // 規格清空
			for (ListAItemspecVO l : itemForm.getListAItemspecVO()) {
				l.setEaudittimestamp(itemForm.getaItem().getEaudittimestamp());
				l.setItemnum(itemForm.getaItem().getItemnum());
				l.setEaudittype(itemForm.getaItem().getEaudittype());
				l.setItemsetid(itemForm.getaItem().getItemsetid());
				l.setEauditusername(itemForm.getaItem().getEauditusername());
				// l.setStatus(itemForm.getaItem().getStatus());
				l.setChangeby(itemForm.getaItem().getChangeby());
				l.setChangedate(itemForm.getaItem().getEaudittimestamp());
				// ===================AitemSpec============================
				AItemspec aItemspec = new AItemspec();
				BeanUtils.copyProperties(l, aItemspec);
				aItemspec.setAItem(itemForm.getaItem());
				aItemspec.setAItemspecid(null);
				if(StringUtils.isBlank(l.getAlnvalue()) && l.getNumvalue()==null) {
					aItemspec.setMeasureunitid(null);
				}
				if(l.getDatatype().equals(SystemConfig.aln)){
					aItemspec.setNumvalue(null);
				}else if(l.getDatatype().equals(SystemConfig.numeric)){
					aItemspec.setAlnvalue(null);
				}
				applyItemDao.insertUpdate(aItemspec); // aItemspec新增
			}
	
			// ===============供應商新增================================
			applyItemDao.deleteAInvvedor(itemForm.getaItem().getEaudittransid()); // 供應商清空
			for (ListAInvvendorVO l : itemForm.getListListAInvvendorVO()) {
				l.setItemnum(itemForm.getaItem().getItemnum());
				l.setItemsetid(itemForm.getaItem().getItemsetid());
				AInvvendor i = new AInvvendor();
				BeanUtils.copyProperties(l, i);
				i.setAItem(itemForm.getaItem());
				i.setAInvvendorid(null);
				applyItemDao.insertUpdate(i);
			}
			// ===============替代料號新增================================
			applyItemDao.deleteAItemSecondItemnum(itemForm.getaItem().getEaudittransid()); // 替代料號清空
			for (AItemSecondItemnum a : itemForm.getListAItemSecondItemnum()) {
				AItemSecondItemnum aItemSecondItemnum=new AItemSecondItemnum();
				BeanUtils.copyProperties(a, aItemSecondItemnum);
				aItemSecondItemnum.setAItem(itemForm.getaItem());
				aItemSecondItemnum.setAItemSecondItemnumId(null);
				applyItemDao.insertUpdate(aItemSecondItemnum);
			}
			// ===========================申請單其他屬性===================================
			itemForm.getaItemAttribute().setAItem(itemForm.getaItem());
			applyItemDao.insertUpdate(itemForm.getaItemAttribute());
	}
	
	
}
