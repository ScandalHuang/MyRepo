package mro.app.sapAccess.bo;

import java.text.SimpleDateFormat;
import java.util.List;

import mro.app.sapAccess.dao.SapAccessQueryDao;
import mro.app.util.SystemUtils;
import mro.base.System.config.basicType.ItemSiteTransferType;
import mro.base.bo.ItemBO;
import mro.base.bo.ItemSiteTransferLogBO;
import mro.base.bo.SapPlantAttributeBO;
import mro.base.entity.Item;
import mro.base.entity.ItemSiteTransferLog;
import mro.form.SapForm;

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
public class SapAccessQueryBo {

    private SapAccessQueryDao sapAccessQueryDao;
	private SapPlantAttributeBO sapPlantAttributeBO;
	private ItemSiteTransferLogBO itemSiteTransferLogBO;
	private ItemBO itemBO;
    
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	sapAccessQueryDao=new SapAccessQueryDao(sessionFactory);
		sapPlantAttributeBO=SpringContextUtil.getBean(SapPlantAttributeBO.class);
		itemSiteTransferLogBO=SpringContextUtil.getBean(ItemSiteTransferLogBO.class);
		itemBO=SpringContextUtil.getBean(ItemBO.class);
    }
    
	@Transactional(readOnly=true)
    public List<ItemSiteTransferLog> getItemSiteTransferLogList(SapForm sapForm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		StringBuffer str=new StringBuffer();
		if(StringUtils.isNotBlank(sapForm.getMaterial())){
			str.append("and  ITEMNUM like '"+sapForm.getMaterial().toUpperCase()+"%' ");
		}
		if(StringUtils.isNotBlank(sapForm.getApplyCategory())){
				str.append("and  APPLY_CATEGORY = '"+sapForm.getApplyCategory()+"' ");
		}
		if(StringUtils.isNotBlank(sapForm.getStatus())){
			if(sapForm.getStatus().equals("null")){
				str.append("and STATUS is null ");
			}else {
				str.append("and STATUS ='"+sapForm.getStatus()+"' ");
			}
		}
		
		if(sapForm.getBeginDate()!=null){
			str.append("and trunc(CREATE_DATE) >= to_date('"+sdf.format(sapForm.getBeginDate())+"','yyyy/mm/dd') ");}
		if(sapForm.getEndDate()!=null){
				str.append("and trunc(CREATE_DATE) <= to_date('"+sdf.format(sapForm.getEndDate())+"','yyyy/mm/dd') ");}		
	   return sapAccessQueryDao.getItemSiteTransferLogList(str.toString());
	}
		
	@Transactional(readOnly=true)
    public int getEnableItemList(String[] columnName,Object... object) {
		SystemUtils systemUtils=new SystemUtils("=",columnName);
		StringBuffer condition=systemUtils.getConditions(object);
	   return sapAccessQueryDao.getEnableItemList(condition.toString()).size();
	}
	@Transactional(readOnly=true)
    public List getSapLogPNList(SapForm sapForm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		StringBuffer str=new StringBuffer();
		if(StringUtils.isNotBlank(sapForm.getMaterial2())){
			str.append("and  a.material like '"+sapForm.getMaterial2().toUpperCase()+"%' ");
		}
		if(StringUtils.isNotBlank(sapForm.getStatus2())){
			str.append("and  a.type ='"+sapForm.getStatus2()+"' ");}
		
		if(StringUtils.isNotBlank(sapForm.getIn21())){
			str.append("and  nvl(b.in21,'N') ='"+sapForm.getIn21()+"' ");}
		
		if(sapForm.getBeginDate2()!=null){
			str.append("and trunc(a.ACCESS_TIME) >= to_date('"+sdf.format(sapForm.getBeginDate2())+"','yyyy/mm/dd') ");}
		if(sapForm.getEndDate2()!=null){
				str.append("and trunc(a.ACCESS_TIME) <= to_date('"+sdf.format(sapForm.getEndDate2())+"','yyyy/mm/dd') ");}		
	   return sapAccessQueryDao.getSapLogPNList(str.toString());
	}
	@Transactional(readOnly=true)
    public List getSapLogEqList(SapForm sapForm) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
		StringBuffer str=new StringBuffer();
		if(StringUtils.isNotBlank(sapForm.getMaterial4())){
			str.append("and  a.material like '"+sapForm.getMaterial4().toUpperCase()+"%' ");
		}
		if(StringUtils.isNotBlank(sapForm.getStatus4())){
			str.append("and  a.type ='"+sapForm.getStatus4()+"' ");}
		
		if(sapForm.getBeginDate4()!=null){
			str.append("and trunc(a.ACCESS_TIME) >= to_date('"+sdf.format(sapForm.getBeginDate4())+"','yyyy/mm/dd') ");}
		if(sapForm.getEndDate4()!=null){
				str.append("and trunc(a.ACCESS_TIME) <= to_date('"+sdf.format(sapForm.getEndDate4())+"','yyyy/mm/dd') ");}		
	   return sapAccessQueryDao.getSapLogEqList(str.toString());
	}
	@Transactional(readOnly=false)
    public void addItemSiteTransferLogByPlantCode(SapForm sapForm,String empNo){
		   Item item=itemBO.getItem(sapForm.getAddItemnum());
			// ===========================Item_site_transfer_log==========================
			itemSiteTransferLogBO.update(sapPlantAttributeBO.getListByPlant(
					sapForm.getAddPlantCode(), item.getClassstructureid()),
					item, null, empNo,
					ItemSiteTransferType.reTransfer, ItemSiteTransferType.update);
	}
	@Transactional(readOnly=false)
    public void addItemSiteTransferLogBySite(SapForm sapForm,String empNo){
	   Item item=itemBO.getItem(sapForm.getAddItemnum());
		// ===========================Item_site_transfer_log==========================
		itemSiteTransferLogBO.update(sapPlantAttributeBO.getListBySite(
				sapForm.getAddSite(), item.getClassstructureid(),true),
				item, null, empNo,
				ItemSiteTransferType.reTransfer, ItemSiteTransferType.update);
	}
}
