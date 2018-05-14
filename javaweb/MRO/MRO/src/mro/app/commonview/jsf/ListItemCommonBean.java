package mro.app.commonview.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.base.System.config.basicType.ItemStatusType;
import mro.base.System.config.basicType.LocationSiteActionType;
import mro.base.bo.ClassstructureBO;
import mro.base.bo.ItemBO;
import mro.base.entity.Item;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name="ListItemCommonBean")
@ViewScoped
public class ListItemCommonBean implements Serializable{
	private static final long serialVersionUID = -8439092524356340574L;
	private Map classstructureMap;
	private Map classstructureOption;
	private List<Item> listItem;
	
	//=================搜索============================
	private String classstructureId;  //條件
	private String itemnumSearch;  //料號
	private String description;  //品名
	private boolean secondSourceType; //2nd seconde source
	private Map<String,String> statusMap;  //可以選取的Status
	//================Location site=========================
	private String locationSite;
	private LocationSiteActionType actionType;  //區域審核機制
	private Map<String,String> itemSiteMap;  //區域可選取料號map.get()=Y
	//===================更新view======================
    private Object object;
    private String updateView;

	private transient ClassstructureBO classstructureBO;
	private transient ItemBO itemBO;
	
	public ListItemCommonBean(){
		
	}
	
	@PostConstruct
	public void  init(){
		listItem = new LinkedList<Item>();
		statusMap = new HashMap<String,String>();
		itemSiteMap = new HashMap<String,String>();
		classstructureBO=SpringContextUtil.getBean(ClassstructureBO.class);
		itemBO=SpringContextUtil.getBean(ItemBO.class);
	}
	public void setClassstructure(String classstructureid,String prtype,
			ItemStatusType itemStatusType,boolean secondSourceType){
		classstructureMap = classstructureBO.getClassstructureMap(classstructureid, prtype);
		classstructureOption =Utility.swapMap(classstructureMap);
		this.classstructureId=classstructureid;
		this.statusMap = Utility.listToMap(itemStatusType.getValue());
		this.secondSourceType=secondSourceType;
		this.clearListItem();
	}
	

	
	public void clearListItem(){
		listItem = new LinkedList<Item>();
	}
	public void onSearch(){
		GlobalGrowl message = new GlobalGrowl();
		if(itemnumSearch.length()<5 && StringUtils.isBlank(classstructureId) 
				&& StringUtils.isBlank(description) ){
			message.addWarnMessage("WARN!", "請選擇類別、品名或料號至少5碼!");
		}else {
			listItem=itemBO.getItemList(classstructureId, itemnumSearch, description, 
					classstructureOption,secondSourceType);
			this.setItemSiteMap();  //區域可選取料號map.get()=Y
		}
	}
	
	//===========================LocationSite=====================================================
	public void setLocationSiteInfo(String locationSite,LocationSiteActionType actionType){
		this.locationSite = locationSite;
		this.actionType = actionType;
	}
	
	public void setItemSiteMap(){
		itemSiteMap.clear();
		if(StringUtils.isNotBlank(locationSite) && actionType!=null){
			List<Item> itemSiteList=itemBO.getItemList(classstructureId, itemnumSearch, 
					description, classstructureOption, locationSite, actionType,secondSourceType);
			//===============List<Object> to map<itemnum,Y>
			for(Object i:itemSiteList){  
				itemSiteMap.put(((Item)i).getItemnum(), "Y");
			}
		}
	}
	
	//===================================reflect save=========================================================
	public void save(Item item) throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, InvocationTargetException{
			//=========================setClassstructureuid=======================================
			Method method=object.getClass().getMethod("setSelectItem", item.getClass());
			method.invoke(object, item);
			//=========================更新view=======================================
			if(StringUtils.isNotBlank(updateView)){ 
				for(String v:updateView.split(" ")){
					RequestContext context = RequestContext.getCurrentInstance();  
					context.update(v.trim());
				}
			}
	}
//========================================================================
	public void setClassstructure(ItemStatusType itemStatusType){
		this.setClassstructure(null, null, itemStatusType,false);
	}
	public void setClassstructure(String classstructureid,String prtype,ItemStatusType itemStatusType){
		this.setClassstructure(classstructureid, prtype, itemStatusType,false);
	}
	public void setClassstructure(String classstructureid,ItemStatusType itemStatusType,boolean secondSourceType){
		this.setClassstructure(classstructureid, null, itemStatusType,secondSourceType);
	}
	public List<Item> getListItem() {
		return listItem;
	}
	public Map getClassstructureMap() {
		return classstructureMap;
	}
	public String getClassstructureId() {
		return classstructureId;
	}

	public void setClassstructureId(String classstructureId) {
		this.classstructureId = classstructureId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public Map getClassstructureOption() {
		return classstructureOption;
	}

	public String getItemnumSearch() {
		return itemnumSearch;
	}

	public void setItemnumSearch(String itemnumSearch) {
		this.itemnumSearch = itemnumSearch;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getUpdateView() {
		return updateView;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}

	public Map getStatusMap() {
		return statusMap;
	}
	public LocationSiteActionType getActionType() {
		return actionType;
	}

	public String getLocationSite() {
		return locationSite;
	}

	public void setLocationSite(String locationSite) {
		this.locationSite = locationSite;
	}

	public Map<String, String> getItemSiteMap() {
		return itemSiteMap;
	}

	public void setItemSiteMap(Map<String, String> itemSiteMap) {
		this.itemSiteMap = itemSiteMap;
	}

	
}
