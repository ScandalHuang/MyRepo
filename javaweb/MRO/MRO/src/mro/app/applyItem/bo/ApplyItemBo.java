package mro.app.applyItem.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mro.app.applyItem.Utils.ApplyUtils;
import mro.app.applyItem.dao.ApplyItemDao;
import mro.app.applyItem.vo.ListAInvvendorVO;
import mro.app.applyItem.vo.ListAItemspecVO;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.AInvvendor;
import mro.base.entity.AItem;
import mro.base.entity.AItemAttribute;
import mro.base.entity.AItemSecondItemnum;
import mro.base.entity.AItemspec;
import mro.base.entity.ClassstructureItemSign;
import mro.base.entity.ClassstructureItemchangeSign;
import mro.base.entity.Item;
import mro.base.entity.ItemMapping;
import mro.form.ItemForm;
import mro.utility.ValidationUtils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.Utility;

@Component
@Scope("prototype")
public class ApplyItemBo {

	private ApplyItemDao applyItemDao;
	
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	applyItemDao=new ApplyItemDao(sessionFactory);
    }
	//=====2013/6/21料號產生使用=====
	@Transactional(readOnly = false)
	public void updateErrorCode(BigDecimal EAUDITTRANSID,String errorCode){
		applyItemDao.updateErrorCode(EAUDITTRANSID, errorCode);
	}
	//=====2013/6/21料號產生使用=====
	@Transactional(readOnly = true)
	public List<AItem> getAItemListTemp(String name,SignStatus status) {

		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(name)) {
			condition.append("and EAUDITUSERNAME = '" + name + "' ");
		}
		condition.append("and ORIITEMNUM is null ");
		condition.append("and STATUS = '" + status + "' ");
		
		return applyItemDao.getAItemList(condition.toString());
	}
	
	/* 產生料號規格異動清單
	 * hongjie.wu
	 * 2013/11/4
	 * */
	@Transactional(readOnly = true)
	public List<AItem> getAItemListBySpecTemp(String materialGroup,SignStatus status) {

		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(materialGroup)) {
			condition.append("and CLASSSTRUCTUREID in (" + materialGroup + ") ");
		}
		condition.append("and ORIITEMNUM is not null ");
		condition.append("and STATUS = '" + status + "' ");
		
		return applyItemDao.getAItemList(condition.toString());
	}
	
	@Transactional(readOnly = true)
	public AItem getAItem(BigDecimal EAUDITTRANSID) {

		StringBuffer condition = new StringBuffer();
		condition.append("and EAUDITTRANSID = '" + EAUDITTRANSID + "' ");
		
		return applyItemDao.getAItem(condition.toString());
	}
	@Transactional(readOnly = true)
	public List<AItem> getAItemList(String name) {

		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotBlank(name)) {
			condition.append("and EAUDITUSERNAME = '" + name + "' ");
		}
		condition.append("and STATUS != '" + SignStatus.APPR + "' ");
		condition.append("and ORIITEMNUM is null ");
//		condition.append("and CLASSSTRUCTUREID NOT like 'R2%'  ");
		
		return applyItemDao.getAItemList(condition.toString());
	}

	@Transactional(readOnly = false)
	public void onApplyDelete(AItem aItem) {
		ApplyUtils.deleteFile(aItem.getItemnum(),null);
		applyItemDao.deleteAInvvedor(aItem.getEaudittransid());
		applyItemDao.deleteAItemSecondItemnum(aItem.getEaudittransid());
		applyItemDao.deleteAItemspec(aItem.getItemnum());
		applyItemDao.deleteAItemAttribute(aItem.getEaudittransid().toString());
		applyItemDao.delete(aItem);
	}

	@Transactional(readOnly = true)
	public List<Item> getValidItemList(String decription,Item item,ItemMapping itemMapping) {
		Map param=new HashMap();
		StringBuffer condition = new StringBuffer();
			condition.append("and VALIDATE_DESCRIPTION = replace(:VALIDATE_DESCRIPTION,' ','') ");
			param.put("VALIDATE_DESCRIPTION",decription);
		if (item!=null && item.getItemid()!=null) {
			condition.append("and itemid!=:ITEMID ");
			param.put("ITEMID",item.getItemid());
		}
		if (itemMapping!=null && StringUtils.isNotBlank(itemMapping.getOldMatnr())) {
			condition.append("and itemnum!=:OLD_MATNR ");
			param.put("OLD_MATNR",itemMapping.getOldMatnr());
		}

		condition.append("and STATUS!='"+SignStatus.STOPUSE+"'"); //20150212 驗證品名不驗證失效料號
		
		return applyItemDao.getItemList(condition.toString(),param);
	}
	@Transactional(readOnly = true)
	public int getItemCount(BigDecimal itemid) {
		BigDecimal b=new BigDecimal(0);
		StringBuffer condition = new StringBuffer();
		if (itemid!=null) {
			condition.append("and itemid='"+itemid+"'");}
			condition.append("and STATUS!='"+SignStatus.ACTIVE+"'");
			b = applyItemDao.getItemCount(condition.toString());
		
		return b.intValue();
	}
	@Transactional(readOnly = true)
	public List<AItem> getVaildateAItemList(String decription) {
		StringBuffer condition = new StringBuffer();
		Map param=new HashMap();
			condition.append("and VALIDATE_DESCRIPTION = replace(:VALIDATE_DESCRIPTION,' ','') ");
			condition.append("and STATUS='"+SignStatus.INPRG+"'");
			param.put("VALIDATE_DESCRIPTION",decription);
		
		return applyItemDao.getAItemList(condition.toString(),param);
	}
	@Transactional(readOnly = false)
	public void onApplySave(ItemForm itemForm, BigDecimal Eaudittransid,String itemnum,String action) {
		if (itemForm.getaItem().getEaudittransid() == null) {
			itemForm.getaItem().setEaudittransid(Eaudittransid);
			itemForm.getaItem().setItemnum(itemnum);
			itemForm.getaItem().setCreateDate(itemForm.getaItem().getEaudittimestamp());
		}else{  //驗證申請單是否在流程中
			ValidationUtils.validateStatus(itemForm.getaItem().getItemnum(), 
					itemForm.getaItem().getTaskId(),ItemStatusType.TYPE_PROCESS_AIS);
		}
		if(action.equals("submit")){
			itemForm.getaItem().setEaudittimestamp(new Date(System.currentTimeMillis()));
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
			// l.setStatus(aItem.getStatus());
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

	@Transactional(readOnly = true)
	public AItemspec getAItemspec(int eaudittransid, String assetattrid) {

		StringBuffer condition = new StringBuffer();

		condition.append("and EAUDITTRANSID = " + eaudittransid + " ");
		condition.append("and ASSETATTRID = '" + assetattrid + "' ");

		return applyItemDao.getAItemspec(condition.toString());
	}
	
	@Transactional(readOnly = true)
	public AItemAttribute getAItemAttribute(int eaudittransid) {

		StringBuffer condition = new StringBuffer();

		condition.append("and EAUDITTRANSID = " + eaudittransid + " ");
		
		AItemAttribute a=applyItemDao.getAItemAttribute(condition.toString());

		return a==null?new AItemAttribute():a;
	}
	@Transactional(readOnly = true)
	public List<ListAInvvendorVO> getListAInvvendorVO(int eaudittransid) {

		StringBuffer condition = new StringBuffer();

		condition.append("and a.EAUDITTRANSID = " + eaudittransid + " ");

		return applyItemDao.getListAInvvendorVO(condition.toString());
	}
	@Transactional(readOnly = true)
	public List<AItemSecondItemnum> getListAItemSecondItemnum(int eaudittransid) {

		StringBuffer condition = new StringBuffer();

		condition.append("and EAUDITTRANSID = " + eaudittransid + " ");

		return applyItemDao.getListAItemSecondItemnum(condition.toString());
	}
	@Transactional(readOnly = true)
	public List<ListAItemspecVO> getListAItemspecVOList(BigDecimal eaudittransid,BigDecimal preEaudittransid) {
		StringBuffer condition = new StringBuffer();
		StringBuffer preCondition = new StringBuffer();
		if(eaudittransid!=null){
			condition.append("and a.EAUDITTRANSID = " + eaudittransid + "  ");
		}
		if(preEaudittransid!=null){
			preCondition.append(",(select nvl(alnvalue,NUMVALUE) "
					+ "from A_ITEMSPEC where EAUDITTRANSID =" + preEaudittransid + " "
				+ "and assetattrid=a.assetattrid) old_value,"
				+ "(select MEASUREUNITID from A_ITEMSPEC where EAUDITTRANSID =" + preEaudittransid + " "
				+ "and assetattrid=a.assetattrid) old_MEASUREUNITID ");
		}else{
			preCondition.append(",'' old_value,'' old_MEASUREUNITID ");
		}
		return applyItemDao.getListAItemspecVOList(condition.toString(),preCondition.toString());
	}
	@Transactional(readOnly = true)
	public ClassstructureItemSign getClassstructureItemSign(String classstructureuid,String organizationCode){
		return applyItemDao.getClassstructureItemSign(classstructureuid,organizationCode);
	}
	@Transactional(readOnly = true)
	public ClassstructureItemchangeSign getClassstructureItemchangeSign(String classstructureuid,String organizationCode){
		return applyItemDao.getClassstructureItemchangeSign(classstructureuid,organizationCode);
	}
	@Transactional(readOnly = true)
	public List getClassstructureItemchangeSignGroup(String classstructureuid,String column,String itemnum){
		return applyItemDao.getClassstructureItemchangeSignGroup(classstructureuid, column,itemnum);
	}
	@Transactional(readOnly = true)
	public List<ClassstructureItemSign> getClassstructureItemSign(String classstructureuid){
		return applyItemDao.getClassstructureItemSign(classstructureuid);
	}
	@Transactional(readOnly = true)
	public BigDecimal getRate(ItemForm itemForm){
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(itemForm.getaItemAttribute().getOriCurrency())){
			condition.append("and FROM_CURRENCY = '" + itemForm.getaItemAttribute().getOriCurrency() + "' ");
			condition.append("and TO_CURRENCY = '" + itemForm.getaItemAttribute().getCurrencyCode() + "' ");
		}
		BigDecimal value=new BigDecimal("1");
		if(StringUtils.isBlank(itemForm.getaItemAttribute().getOriCurrency())){
			value=new BigDecimal("0");
		}else if(itemForm.getaItemAttribute().getOriCurrency().compareTo(
				itemForm.getaItemAttribute().getCurrencyCode())!=0){
			value=applyItemDao.getRate(condition.toString());
		}
		return value==null?new BigDecimal(0):value;
	}
	@Transactional(readOnly=true)
    public String getItemspecValue(String assetattrid,String eaudittransid) {
		StringBuffer condition = new StringBuffer();
		condition.append(" and a.assetattrid='"+assetattrid+"'");
		ListAItemspecVO l=	applyItemDao.getListAItemspecVO(condition.toString(),eaudittransid);
		String temp = "";
		if(l==null) {return temp;}
		// ==================規格====================
		if (l.getDatatype().equals(SystemConfig.numeric)) {
			temp=l.getNumvalue()!=null?l.getNumvalue().toString():"";
		} else if (l.getDatatype().equals(SystemConfig.aln)) {
			temp=l.getAlnvalue();
		}
	   return temp;
	}	
	
	@Transactional(readOnly = true)
	public List getR2AItemCount(AItem aItem, String[] R2ItemSpecNonDuplicate,
			String alnvalue,List<ListAItemspecVO> listAItemspecVOs) {
		Map param=new HashMap();
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotBlank(aItem.getOriitemnum())){
			condition.append("and a.oriitemnum !=:ORIITEMNUM ");
			param.put("ORIITEMNUM",aItem.getOriitemnum());
		}
		if(StringUtils.isNotBlank(alnvalue)){
			condition.append("and replace(b.alnvalue,' ','')=:ALNVALUE ");
			param.put("ALNVALUE",alnvalue.replaceAll(" ", ""));
		}
		if(R2ItemSpecNonDuplicate!=null && R2ItemSpecNonDuplicate.length>0){
			condition.append("and b.ASSETATTRID in (:ASSETATTRID)");
			param.put("ASSETATTRID",R2ItemSpecNonDuplicate);
		}
		if(Utility.isNotEmpty(listAItemspecVOs)){
			for(int i=0;i<listAItemspecVOs.size();i++){
				ListAItemspecVO l=listAItemspecVOs.get(i);
					condition.append("and EXISTS ( SELECT 1 FROM A_ITEMSPEC WHERE  EAUDITTRANSID=A.EAUDITTRANSID ");
					condition.append("AND ASSETATTRID=:METERIAL_NAME"+i+" ");
					condition.append("AND ALNVALUE=:METERIAL_ALNVALUE"+i+") ");
					param.put("METERIAL_NAME"+i,l.getAssetattrid());
					param.put("METERIAL_ALNVALUE"+i,ObjectUtils.toString(
							l.getDatatype().equals(SystemConfig.aln)?l.getAlnvalue():l.getNumvalue()));
			}
		}
		return applyItemDao.getR2AItemCount(aItem, condition.toString(),param);
	}

	@Transactional(readOnly = true)
	public List getR2ItemCount(AItem aItem, String[] R2ItemSpecNonDuplicate,
			String alnvalue,List<ListAItemspecVO> listAItemspecVOs) {
		StringBuffer condition = new StringBuffer();
		Map<String, Object> param=new HashMap();
		if(StringUtils.isNotBlank(aItem.getOriitemnum())){
			condition.append("and B.itemnum !=:ORIITEMNUM ");
			param.put("ORIITEMNUM",aItem.getOriitemnum());
		}
		if(StringUtils.isNotBlank(alnvalue)){
			condition.append("and replace(b.alnvalue,' ','')=:ALNVALUE ");
			param.put("ALNVALUE",alnvalue.replaceAll(" ", ""));
		}
		if(R2ItemSpecNonDuplicate!=null && R2ItemSpecNonDuplicate.length>0){
			condition.append("and b.ASSETATTRID in (:ASSETATTRID)");
			param.put("ASSETATTRID",R2ItemSpecNonDuplicate);
		}
		if(Utility.isNotEmpty(listAItemspecVOs)){
			for(int i=0;i<listAItemspecVOs.size();i++){
				ListAItemspecVO l=listAItemspecVOs.get(i);
					condition.append("and EXISTS ( SELECT 1 FROM ITEMSPEC WHERE ITEMID=B.ITEMID ");
					condition.append("AND ASSETATTRID=:METERIAL_NAME"+i+" ");
					condition.append("AND ALNVALUE=:METERIAL_ALNVALUE"+i+") ");
					param.put("METERIAL_NAME"+i,l.getAssetattrid());
					param.put("METERIAL_ALNVALUE"+i,ObjectUtils.toString(
						l.getDatatype().equals(SystemConfig.aln)?l.getAlnvalue():l.getNumvalue()));
			}
		}
		return applyItemDao.getR2ItemCount(aItem, condition.toString(),param);
	}
	
	@Transactional(readOnly = true)
	public BigDecimal getItemMappingCount(BigDecimal eaudittransid,BigDecimal itemId) {
		return applyItemDao.getItemMappingCount(eaudittransid,itemId);
	}
	
	@Transactional(readOnly=true)
    public Map getRateCurrencyOption(String toCurrency){ 
    	List list=applyItemDao.getRateCurrency(toCurrency);
		Map map= new LinkedHashMap();
		for(Object o : list){
			map.put(o, o);
		}
		map.put(toCurrency, toCurrency);
		
		return map;
    }
}
