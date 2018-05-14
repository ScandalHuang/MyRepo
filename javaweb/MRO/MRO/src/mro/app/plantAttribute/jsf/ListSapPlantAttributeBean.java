package mro.app.plantAttribute.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.jsf.FileUploadExcelBean;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.plantAttribute.bo.ListSapPlantAttributeBO;
import mro.base.entity.SapPlantAttribute;
import mro.base.entity.SapProfitCenter;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.utility.vo.ColumnModel;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.ReflectUtils;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListSapPlantAttributeBean")
@ViewScoped
public class ListSapPlantAttributeBean implements Serializable {
	private static final long serialVersionUID = -3635274832263264245L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	@ManagedProperty(value = "#{FileUploadExcelBean}")
	private FileUploadExcelBean fileUploadExcelBean;
	
	private final List keys=Arrays.asList("PLANT_CODE","MATERIAL_GROUP","STORAGE_LOCATION");
	private List<SapPlantAttribute> listSapPlantAttribute;
	private SapPlantAttribute[] sapPlantAttributes;  //選取的Map
	private Map plantCodeMap;
	private Map materialGroupMap;
	private String selectPlantCode;
	private String selectMaterialGroup;
	private List<ColumnModel> columns; 
	
	private List<SapProfitCenter> sapProfitCenterList;
	private SapProfitCenter selectSapProfitCenter;
	private ListSapPlantAttributeBO listSapPlantAttributeBO;
	
	public ListSapPlantAttributeBean() {

	}

	@PostConstruct
	public void init() {
		listSapPlantAttribute=new LinkedList<>();
		selectSapProfitCenter=new SapProfitCenter();
		listSapPlantAttributeBO = SpringContextUtil.getBean(ListSapPlantAttributeBO.class);
		columns = new ArrayList<ColumnModel>();
		this.querySapProfitCenter();//取得profitCenterList
		this.setPrameter();
		this.setColumns();
	}
	public void setPrameter(){
		plantCodeMap = listSapPlantAttributeBO.getPlantCode();
		materialGroupMap = listSapPlantAttributeBO.getMaterialGroup();
		
	}
	public void onSelectAll(){
		sapPlantAttributes=(SapPlantAttribute[]) 
				ApplyPrUtils.onSelectAll(sapPlantAttributes,listSapPlantAttribute);
	}
	
	public void onDelete(){
		listSapPlantAttributeBO.onDelete(sapPlantAttributes);
		GlobalGrowl message = new GlobalGrowl();
		message.addInfoMessage("Delete", "Delete successful!");
		this.change();
	}
	
	public void change() {
		sapPlantAttributes=null;
		listSapPlantAttribute.clear();
		if(StringUtils.isNotBlank(selectPlantCode) || StringUtils.isNotBlank(selectMaterialGroup)){
			listSapPlantAttribute=listSapPlantAttributeBO.getSapPlantAttribute(selectPlantCode, selectMaterialGroup);
		}
	}
	
	public void getSapProfitCenter(){ //下拉plantCode query
		SapProfitCenter tempSapProfitCenter=listSapPlantAttributeBO.getSapProfitCenter(selectSapProfitCenter.getPlantCode());
		if(tempSapProfitCenter!=null){
			selectSapProfitCenter.setProfitCenter(tempSapProfitCenter.getProfitCenter());
		}else{
			selectSapProfitCenter.setProfitCenter("");
		}
	}
	public void querySapProfitCenter(){ //First query SAP_PROFIT_CENTER
			sapProfitCenterList=listSapPlantAttributeBO.getSapProfitCenterList();
	}
	public void updateSapProfitCenter(){ //更新SAP_PROFIT_CENTER
		GlobalGrowl message = new GlobalGrowl();
		if(StringUtils.isNotBlank(selectSapProfitCenter.getPlantCode())){
			listSapPlantAttributeBO.updateSapProfitCenter(selectSapProfitCenter,loginInfoBean.getEmpNo());
			selectSapProfitCenter=new SapProfitCenter();
			querySapProfitCenter();
			message.addInfoMessage("Upload", "Upload successful!");
		}else{
			message.addWarnMessage("Warn", "請選取PlantCode!");
		}
	}
	public void uploadExcel(File file) {
		FileUploadExcelInterfaces fileUploadExcelInterfaces = new FileUploadExceImpl();
		if (fileUploadExcelInterfaces.uploadExcel(new SapPlantAttribute(),file,
				keys,true,true) == true) {
			this.setPrameter();
			this.change();
		}
	}

	/** 取得column name */
	private void setColumns(){
		columns.clear(); 
		Map map=new LinkedHashMap();
		keys.forEach(k->map.put(k, null));
		map.putAll(ReflectUtils.getEntityMap(SapPlantAttribute.class));
		map.remove("ID");		
		map.forEach((k,v)->columns.add(new ColumnModel(v.toString(),k.toString())));
	}
	// ==========================================================================================

	public List getListSapPlantAttribute() {
		return listSapPlantAttribute;
	}

	public void setListSapPlantAttribute(
			List listSapPlantAttribute) {
		this.listSapPlantAttribute = listSapPlantAttribute;
	}

	public Map getPlantCodeMap() {
		return plantCodeMap;
	}

	public void setPlantCodeMap(Map plantCodeMap) {
		this.plantCodeMap = plantCodeMap;
	}

	public Map getMaterialGroupMap() {
		return materialGroupMap;
	}

	public void setMaterialGroupMap(Map materialGroupMap) {
		this.materialGroupMap = materialGroupMap;
	}

	public String getSelectPlantCode() {
		return selectPlantCode;
	}

	public void setSelectPlantCode(String selectPlantCode) {
		this.selectPlantCode = selectPlantCode;
	}

	public String getSelectMaterialGroup() {
		return selectMaterialGroup;
	}

	public void setSelectMaterialGroup(String selectMaterialGroup) {
		this.selectMaterialGroup = selectMaterialGroup;
	}

	public List<ColumnModel> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnModel> columns) {
		this.columns = columns;
	}

	public List<SapProfitCenter> getSapProfitCenterList() {
		return sapProfitCenterList;
	}

	public void setSapProfitCenterList(List<SapProfitCenter> sapProfitCenterList) {
		this.sapProfitCenterList = sapProfitCenterList;
	}

	public SapProfitCenter getSelectSapProfitCenter() {
		return selectSapProfitCenter;
	}

	public void setSelectSapProfitCenter(SapProfitCenter selectSapProfitCenter) {
		this.selectSapProfitCenter = selectSapProfitCenter;
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public FileUploadExcelBean getFileUploadExcelBean() {
		return fileUploadExcelBean;
	}

	public void setFileUploadExcelBean(FileUploadExcelBean fileUploadExcelBean) {
		this.fileUploadExcelBean = fileUploadExcelBean;
	}

	public SapPlantAttribute[] getSapPlantAttributes() {
		return sapPlantAttributes;
	}

	public void setSapPlantAttributes(SapPlantAttribute[] sapPlantAttributes) {
		this.sapPlantAttributes = sapPlantAttributes;
	}

}
