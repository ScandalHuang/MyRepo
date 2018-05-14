package mro.app.item.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.app.item.vo.ListItemBeanAItemspecVO;
import mro.app.item.vo.ListItemBeanInvvendorVO;
import mro.app.item.vo.ListItemBeanItemVO;
import mro.app.item.vo.ListItemBeanItemspecVO;
import mro.app.item.vo.ListItemBeanMatusetransVO;
import mro.app.item.vo.ListItemBeanSparepartVO;
import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.EamSnapshotBssO;
import mro.base.entity.EamSnapshotBssT;
import mro.base.entity.Item;
import mro.base.entity.ItemAttribute;
import mro.base.entity.ItemMapping;
import mro.base.entity.ItemSite;
import mro.base.entity.Matrectrans;
import mro.base.entity.ZZpoMroVendorItem;

public class ListItemForm implements Serializable {
	private static final long serialVersionUID = -7707105625310349948L;
	private List<ListItemBeanItemVO> listItemBeanItemVO;
	private ListItemBeanItemVO listItemBeanItem;
	private List<ListItemBeanItemspecVO> listItemBeanItemspecVO;
	private List<ListItemBeanAItemspecVO> listItemBeanAItemspecVO;
	private List<Item> altitemlist;
	private List listItemBeanInventoryVO;
	private List<EamSnapshotBssO> eamSnapshotBssO;
	private List<ListItemBeanInvvendorVO> listItemBeanInvvendorVO;
	private List listItemBeanPrlineVO;
	private List listItemBeanPrlineStatusVO;
	private List listItemBeanPolineStatusVO;
	private List<ListItemBeanSparepartVO> listItemBeanSparepartVO;
	private List<ListItemBeanMatusetransVO> listItemBeanMatusetransVO;
	private List<Matrectrans> listItemBeanMatrectransVO;
	private List<Matrectrans> listItemBeanPoMatrectransVO;
	private List<EamSnapshotBssT> eamSnapshotBssT;
	private List<ItemSite> listItemSite;
	private List<ItemSite> listItemSiteStop;
	private List<ItemMapping> listItemMapping;
	private List listZzpoMroVendorItem;
	private List<ZZpoMroVendorItem> listZZpoMroVendorItem;
	//======================================filter==============================================
	private List filterListItemBeanInventoryVO;
	private List<EamSnapshotBssO> filterEamSnapshotBssO;
	private List filterListItemBeanPrlineVO;
	private List filterListItemBeanPrlineStatusVO;
	private List filterListItemBeanPolineStatusVO;
	private List<ListItemBeanMatusetransVO> filterListItemBeanMatusetransVO;
	private List<Matrectrans> filterListItemBeanMatrectransVO;
	private List<Matrectrans> filterListItemBeanPoMatrectransVO;
	private List<EamSnapshotBssT> filterEamSnapshotBssT;
	
	private String itemnumtab1;
	private String itemnumtab2;
	private String itemnumtab3;
	private String itemnumtab4;
	private String itemnumtab5;
	private String itemnumtab6;
	private String itemnumtab7;
	private String itemnumtab8;
	
	private int activeIndex;
	private String itemnum;
	private String description;
	private String classstructureid;
	private String selectItemCategory;
	
	private ItemAttribute itemAttribute; // 其他屬性
	private Map dowloadFileMap;
	
	public ListItemForm(){
		this.inital();
	}
	public void inital(){
		dowloadFileMap =FileCategory.getDownloadFileMap();
		activeIndex=0;//供應商清單 page
		
		listItemBeanItem=null;
		listItemBeanItemspecVO=new ArrayList<>();
		listItemBeanAItemspecVO=new ArrayList<>();
		altitemlist=new ArrayList<>();
		listItemBeanInventoryVO=new ArrayList<>();
		eamSnapshotBssO=new ArrayList<>();
		listItemBeanInvvendorVO=new ArrayList<>();
		listItemBeanPrlineVO=new ArrayList<>();
		listItemBeanPrlineStatusVO=new ArrayList<>();
		listItemBeanPolineStatusVO=new ArrayList<>();
		listItemBeanSparepartVO=new ArrayList<>();
		listItemBeanMatusetransVO=new ArrayList<>();
		listItemBeanMatrectransVO=new ArrayList<>();
		listItemBeanPoMatrectransVO=new ArrayList<>();
		eamSnapshotBssT=new ArrayList<>();
		listItemSite=new ArrayList<>();
		listItemSiteStop=new ArrayList<>();
		listItemMapping=new ArrayList<ItemMapping>();
		listItemBeanPoMatrectransVO=new ArrayList<>();
		filterListItemBeanInventoryVO=new ArrayList<>();
		filterEamSnapshotBssO=new ArrayList<>();
		filterListItemBeanPrlineVO=new ArrayList<>();
		filterListItemBeanPrlineStatusVO=new ArrayList<>();
		filterListItemBeanPolineStatusVO=new ArrayList<>();
		filterListItemBeanMatusetransVO=new ArrayList<>();
		filterListItemBeanMatrectransVO=new ArrayList<>();
		filterListItemBeanPoMatrectransVO=new ArrayList<>();
		filterEamSnapshotBssT=new ArrayList<>();
		listItemSite=new ArrayList<>();
		itemnumtab1 = "";
		itemnumtab2 = "";
		itemnumtab3 = "";
		itemnumtab4 = "";
		itemnumtab5 = "";
		itemnumtab6 = "";
		itemnumtab7 = "";
		itemnumtab8 = "";
	}
	public void onCopyTab4(){
		filterListItemBeanInventoryVO=listItemBeanInventoryVO;
		filterEamSnapshotBssO=eamSnapshotBssO;
	}
	public void onCopyTab5(){
		filterListItemBeanPrlineVO=listItemBeanPrlineVO;
		filterListItemBeanPrlineStatusVO=listItemBeanPrlineStatusVO;
		filterListItemBeanPolineStatusVO=listItemBeanPolineStatusVO;
	}
	public void onCopyTab7(){
		filterListItemBeanMatusetransVO=listItemBeanMatusetransVO;
		filterListItemBeanMatrectransVO=listItemBeanMatrectransVO;
		filterListItemBeanPoMatrectransVO=listItemBeanPoMatrectransVO;
	}
	public void onCopyTab8(){
		filterEamSnapshotBssT=eamSnapshotBssT;
	}
	public List<ListItemBeanItemVO> getListItemBeanItemVO() {
		return listItemBeanItemVO;
	}
	public void setListItemBeanItemVO(List<ListItemBeanItemVO> listItemBeanItemVO) {
		this.listItemBeanItemVO = listItemBeanItemVO;
	}
	public ListItemBeanItemVO getListItemBeanItem() {
		return listItemBeanItem;
	}
	public void setListItemBeanItem(ListItemBeanItemVO listItemBeanItem) {
		this.listItemBeanItem = listItemBeanItem;
	}
	public List<ListItemBeanItemspecVO> getListItemBeanItemspecVO() {
		return listItemBeanItemspecVO;
	}
	public void setListItemBeanItemspecVO(
			List<ListItemBeanItemspecVO> listItemBeanItemspecVO) {
		this.listItemBeanItemspecVO = listItemBeanItemspecVO;
	}
	public List<ListItemBeanAItemspecVO> getListItemBeanAItemspecVO() {
		return listItemBeanAItemspecVO;
	}
	public void setListItemBeanAItemspecVO(
			List<ListItemBeanAItemspecVO> listItemBeanAItemspecVO) {
		this.listItemBeanAItemspecVO = listItemBeanAItemspecVO;
	}
	public List<Item> getAltitemlist() {
		return altitemlist;
	}
	public void setAltitemlist(List<Item> altitemlist) {
		this.altitemlist = altitemlist;
	}
	public List getListItemBeanInventoryVO() {
		return listItemBeanInventoryVO;
	}
	public void setListItemBeanInventoryVO(List listItemBeanInventoryVO) {
		this.listItemBeanInventoryVO = listItemBeanInventoryVO;
	}
	public List<EamSnapshotBssO> getEamSnapshotBssO() {
		return eamSnapshotBssO;
	}
	public void setEamSnapshotBssO(List<EamSnapshotBssO> eamSnapshotBssO) {
		this.eamSnapshotBssO = eamSnapshotBssO;
	}
	public List<ListItemBeanInvvendorVO> getListItemBeanInvvendorVO() {
		return listItemBeanInvvendorVO;
	}
	public void setListItemBeanInvvendorVO(
			List<ListItemBeanInvvendorVO> listItemBeanInvvendorVO) {
		this.listItemBeanInvvendorVO = listItemBeanInvvendorVO;
	}
	public List getListItemBeanPrlineVO() {
		return listItemBeanPrlineVO;
	}
	public void setListItemBeanPrlineVO(List listItemBeanPrlineVO) {
		this.listItemBeanPrlineVO = listItemBeanPrlineVO;
	}
	public List getListItemBeanPrlineStatusVO() {
		return listItemBeanPrlineStatusVO;
	}
	public void setListItemBeanPrlineStatusVO(List listItemBeanPrlineStatusVO) {
		this.listItemBeanPrlineStatusVO = listItemBeanPrlineStatusVO;
	}
	public List getListItemBeanPolineStatusVO() {
		return listItemBeanPolineStatusVO;
	}
	public void setListItemBeanPolineStatusVO(List listItemBeanPolineStatusVO) {
		this.listItemBeanPolineStatusVO = listItemBeanPolineStatusVO;
	}
	public List<ListItemBeanSparepartVO> getListItemBeanSparepartVO() {
		return listItemBeanSparepartVO;
	}
	public void setListItemBeanSparepartVO(
			List<ListItemBeanSparepartVO> listItemBeanSparepartVO) {
		this.listItemBeanSparepartVO = listItemBeanSparepartVO;
	}
	public List<ListItemBeanMatusetransVO> getListItemBeanMatusetransVO() {
		return listItemBeanMatusetransVO;
	}
	public void setListItemBeanMatusetransVO(
			List<ListItemBeanMatusetransVO> listItemBeanMatusetransVO) {
		this.listItemBeanMatusetransVO = listItemBeanMatusetransVO;
	}
	public List<Matrectrans> getListItemBeanMatrectransVO() {
		return listItemBeanMatrectransVO;
	}
	public void setListItemBeanMatrectransVO(
			List<Matrectrans> listItemBeanMatrectransVO) {
		this.listItemBeanMatrectransVO = listItemBeanMatrectransVO;
	}
	public List<Matrectrans> getListItemBeanPoMatrectransVO() {
		return listItemBeanPoMatrectransVO;
	}
	public void setListItemBeanPoMatrectransVO(
			List<Matrectrans> listItemBeanPoMatrectransVO) {
		this.listItemBeanPoMatrectransVO = listItemBeanPoMatrectransVO;
	}
	public List<EamSnapshotBssT> getEamSnapshotBssT() {
		return eamSnapshotBssT;
	}
	public void setEamSnapshotBssT(List<EamSnapshotBssT> eamSnapshotBssT) {
		this.eamSnapshotBssT = eamSnapshotBssT;
	}
	public List<ItemSite> getListItemSite() {
		return listItemSite;
	}
	public void setListItemSite(List<ItemSite> listItemSite) {
		this.listItemSite = listItemSite;
	}
	
	public List<ItemMapping> getListItemMapping() {
		return listItemMapping;
	}
	public void setListItemMapping(List<ItemMapping> listItemMapping) {
		this.listItemMapping = listItemMapping;
	}
	public List getListZzpoMroVendorItem() {
		return listZzpoMroVendorItem;
	}
	public void setListZzpoMroVendorItem(List listZzpoMroVendorItem) {
		this.listZzpoMroVendorItem = listZzpoMroVendorItem;
	}
	public List<ZZpoMroVendorItem> getListZZpoMroVendorItem() {
		return listZZpoMroVendorItem;
	}
	public void setListZZpoMroVendorItem(
			List<ZZpoMroVendorItem> listZZpoMroVendorItem) {
		this.listZZpoMroVendorItem = listZZpoMroVendorItem;
	}
	public List getFilterListItemBeanInventoryVO() {
		return filterListItemBeanInventoryVO;
	}
	public void setFilterListItemBeanInventoryVO(List filterListItemBeanInventoryVO) {
		this.filterListItemBeanInventoryVO = filterListItemBeanInventoryVO;
	}
	public List<EamSnapshotBssO> getFilterEamSnapshotBssO() {
		return filterEamSnapshotBssO;
	}
	public void setFilterEamSnapshotBssO(List<EamSnapshotBssO> filterEamSnapshotBssO) {
		this.filterEamSnapshotBssO = filterEamSnapshotBssO;
	}
	public List getFilterListItemBeanPrlineVO() {
		return filterListItemBeanPrlineVO;
	}
	public void setFilterListItemBeanPrlineVO(List filterListItemBeanPrlineVO) {
		this.filterListItemBeanPrlineVO = filterListItemBeanPrlineVO;
	}
	public List getFilterListItemBeanPrlineStatusVO() {
		return filterListItemBeanPrlineStatusVO;
	}
	public void setFilterListItemBeanPrlineStatusVO(
			List filterListItemBeanPrlineStatusVO) {
		this.filterListItemBeanPrlineStatusVO = filterListItemBeanPrlineStatusVO;
	}
	public List getFilterListItemBeanPolineStatusVO() {
		return filterListItemBeanPolineStatusVO;
	}
	public void setFilterListItemBeanPolineStatusVO(
			List filterListItemBeanPolineStatusVO) {
		this.filterListItemBeanPolineStatusVO = filterListItemBeanPolineStatusVO;
	}
	public List<ListItemBeanMatusetransVO> getFilterListItemBeanMatusetransVO() {
		return filterListItemBeanMatusetransVO;
	}
	public void setFilterListItemBeanMatusetransVO(
			List<ListItemBeanMatusetransVO> filterListItemBeanMatusetransVO) {
		this.filterListItemBeanMatusetransVO = filterListItemBeanMatusetransVO;
	}
	public List<Matrectrans> getFilterListItemBeanMatrectransVO() {
		return filterListItemBeanMatrectransVO;
	}
	public void setFilterListItemBeanMatrectransVO(
			List<Matrectrans> filterListItemBeanMatrectransVO) {
		this.filterListItemBeanMatrectransVO = filterListItemBeanMatrectransVO;
	}
	public List<Matrectrans> getFilterListItemBeanPoMatrectransVO() {
		return filterListItemBeanPoMatrectransVO;
	}
	public void setFilterListItemBeanPoMatrectransVO(
			List<Matrectrans> filterListItemBeanPoMatrectransVO) {
		this.filterListItemBeanPoMatrectransVO = filterListItemBeanPoMatrectransVO;
	}
	public List<EamSnapshotBssT> getFilterEamSnapshotBssT() {
		return filterEamSnapshotBssT;
	}
	public void setFilterEamSnapshotBssT(List<EamSnapshotBssT> filterEamSnapshotBssT) {
		this.filterEamSnapshotBssT = filterEamSnapshotBssT;
	}
	public int getActiveIndex() {
		return activeIndex;
	}
	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}
	public String getItemnum() {
		return itemnum;
	}
	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClassstructureid() {
		return classstructureid;
	}
	public void setClassstructureid(String classstructureid) {
		this.classstructureid = classstructureid;
	}
	public String getSelectItemCategory() {
		return selectItemCategory;
	}
	public void setSelectItemCategory(String selectItemCategory) {
		this.selectItemCategory = selectItemCategory;
	}
	public ItemAttribute getItemAttribute() {
		return itemAttribute;
	}
	public void setItemAttribute(ItemAttribute itemAttribute) {
		this.itemAttribute = itemAttribute;
	}
	public Map getDowloadFileMap() {
		return dowloadFileMap;
	}
	public void setDowloadFileMap(Map dowloadFileMap) {
		this.dowloadFileMap = dowloadFileMap;
	}
	public String getItemnumtab3() {
		return itemnumtab3;
	}
	public void setItemnumtab3(String itemnumtab3) {
		this.itemnumtab3 = itemnumtab3;
	}
	public String getItemnumtab4() {
		return itemnumtab4;
	}
	public void setItemnumtab4(String itemnumtab4) {
		this.itemnumtab4 = itemnumtab4;
	}
	public String getItemnumtab5() {
		return itemnumtab5;
	}
	public void setItemnumtab5(String itemnumtab5) {
		this.itemnumtab5 = itemnumtab5;
	}
	public String getItemnumtab6() {
		return itemnumtab6;
	}
	public void setItemnumtab6(String itemnumtab6) {
		this.itemnumtab6 = itemnumtab6;
	}
	public String getItemnumtab7() {
		return itemnumtab7;
	}
	public void setItemnumtab7(String itemnumtab7) {
		this.itemnumtab7 = itemnumtab7;
	}
	public String getItemnumtab8() {
		return itemnumtab8;
	}
	public void setItemnumtab8(String itemnumtab8) {
		this.itemnumtab8 = itemnumtab8;
	}
	public String getItemnumtab1() {
		return itemnumtab1;
	}
	public void setItemnumtab1(String itemnumtab1) {
		this.itemnumtab1 = itemnumtab1;
	}
	public String getItemnumtab2() {
		return itemnumtab2;
	}
	public void setItemnumtab2(String itemnumtab2) {
		this.itemnumtab2 = itemnumtab2;
	}
	public List<ItemSite> getListItemSiteStop() {
		return listItemSiteStop;
	}
	public void setListItemSiteStop(List<ItemSite> listItemSiteStop) {
		this.listItemSiteStop = listItemSiteStop;
	}
	
}
