package mro.app.bbs.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.Attachment;
import mro.base.entity.Bulletinboard;
import mro.base.entity.BulletinboardSite;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;

public class BbsForm implements Serializable{
	
	private static final long serialVersionUID = 9071958641145402664L;
	
	private List<Bulletinboard> bbsList;
	private List<Bulletinboard> filterbbsList;
	private List<BulletinboardSite> bulletinboardSites;//Bulletinboard對應的site
	private LocationSiteMap[] sLocationSiteMap; //Bulletinboard選取的site
	private Map bbsMapList;  //List 依type分類
	public Map postNameMap;;
	private Map<BigDecimal, Boolean> bbsTimeMap;  //new image
	private Bulletinboard bulletinboard;
	private Person loginPerson;
	public Map dowloadFileMap;
	private List<Attachment> listAttachment;//附檔
	
	private Map typeOption;
	private List<String> typeList;
	
	public BbsForm(){
		
	}
	public void bbsIntial(){
		bulletinboard=new Bulletinboard();
		bbsList=new ArrayList<>();
		bulletinboardSites=new ArrayList<>();
		filterbbsList=new ArrayList<>();
		bbsMapList=null;
		postNameMap=null;
		bbsTimeMap=new HashMap<BigDecimal, Boolean>();
		listAttachment = new ArrayList<Attachment>();
		dowloadFileMap = FileCategory.getDownloadFileMap();
	}
	public void onNew(LocationSiteMap locationSiteMap){
		bulletinboard=new Bulletinboard();
		sLocationSiteMap=null;
		sLocationSiteMap=new LocationSiteMap[]{locationSiteMap};
	}
	public void setSLocationSiteMap(List<BulletinboardSite> bulletinboardSites){
		sLocationSiteMap=null;
		if(CollectionUtils.isEmpty(bulletinboardSites)) return;
		List<LocationSiteMap> locationSiteMaps=bulletinboardSites.stream()
			.map(l->l.getLocationSiteMap()).filter(l->l!=null).collect(Collectors.toList());
		sLocationSiteMap=new LocationSiteMap[locationSiteMaps.size()];
		sLocationSiteMap=locationSiteMaps.toArray(sLocationSiteMap);
	}
	
//=============================================================================================
	public List<Bulletinboard> getBbsList() {
		return bbsList;
	}
	public void setBbsList(List<Bulletinboard> bbsList) {
		this.bbsList = bbsList;
	}
	public List<Bulletinboard> getFilterbbsList() {
		return filterbbsList;
	}
	public void setFilterbbsList(List<Bulletinboard> filterbbsList) {
		this.filterbbsList = filterbbsList;
	}
	public Bulletinboard getBulletinboard() {
		return bulletinboard;
	}
	public void setBulletinboard(Bulletinboard bulletinboard) {
		this.bulletinboard = bulletinboard;
	}
	public Person getLoginPerson() {
		return loginPerson;
	}
	public void setLoginPerson(Person loginPerson) {
		this.loginPerson = loginPerson;
	}
	public Map getDowloadFileMap() {
		return dowloadFileMap;
	}
	public void setDowloadFileMap(Map dowloadFileMap) {
		this.dowloadFileMap = dowloadFileMap;
	}
	public List<Attachment> getListAttachment() {
		return listAttachment;
	}
	public void setListAttachment(List<Attachment> listAttachment) {
		this.listAttachment = listAttachment;
	}
	public Map getTypeOption() {
		return typeOption;
	}
	public void setTypeOption(Map typeOption) {
		this.typeOption = typeOption;
	}
	public List<String> getTypeList() {
		return typeList;
	}
	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}
	public Map getBbsMapList() {
		return bbsMapList;
	}
	public void setBbsMapList(Map bbsMapList) {
		this.bbsMapList = bbsMapList;
	}
	public Map<BigDecimal, Boolean> getBbsTimeMap() {
		return bbsTimeMap;
	}
	public void setBbsTimeMap(Map<BigDecimal, Boolean> bbsTimeMap) {
		this.bbsTimeMap = bbsTimeMap;
	}
	public Map getPostNameMap() {
		return postNameMap;
	}
	public void setPostNameMap(Map postNameMap) {
		this.postNameMap = postNameMap;
	}
	public List<BulletinboardSite> getBulletinboardSites() {
		return bulletinboardSites;
	}
	public void setBulletinboardSites(List<BulletinboardSite> bulletinboardSites) {
		this.bulletinboardSites = bulletinboardSites;
	}
	public LocationSiteMap[] getsLocationSiteMap() {
		return sLocationSiteMap;
	}
	public void setsLocationSiteMap(LocationSiteMap[] sLocationSiteMap) {
		this.sLocationSiteMap = sLocationSiteMap;
	}
	
}
