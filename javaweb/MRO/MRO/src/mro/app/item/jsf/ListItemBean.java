package mro.app.item.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.commonview.bo.FileUploadBO;
import mro.app.item.bo.ListItemBO;
import mro.app.item.form.ListItemForm;
import mro.app.item.vo.ListItemBeanItemVO;
import mro.base.bo.AttachmentBO;
import mro.base.bo.ItemMappingBO;
import mro.base.bo.ItemSiteBO;
import mro.base.bo.ZZpoMroVendorItemBO;
import mro.form.ItemForm;
import mro.utility.JsfContextUtils;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.ReflectUtils;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListItemBean")
@ViewScoped
public class ListItemBean implements Serializable {
	
	private ItemForm itemForm;
	private ListItemForm listItemForm;

	private transient ZZpoMroVendorItemBO zZpoMroVendorItemBO;
	private transient ListItemBO listItemBO;
	private transient ItemSiteBO itemSiteBO;
	private transient ItemMappingBO itemMappingBO;

	public ListItemBean() {

	}

	@PostConstruct
	public void init() {
		zZpoMroVendorItemBO = SpringContextUtil
				.getBean(ZZpoMroVendorItemBO.class);
		listItemBO = SpringContextUtil.getBean(ListItemBO.class);
		itemSiteBO = SpringContextUtil.getBean(ItemSiteBO.class);
		itemMappingBO = SpringContextUtil.getBean(ItemMappingBO.class);
		
		itemForm = new ItemForm();
		listItemForm = new ListItemForm();
		ItemInterface itemImpl=new ItemImpl();
		itemImpl.setParameter(itemForm);
	}

	public void inital() {
		itemForm.itemIntial();
		listItemForm.inital();
	}

	public void onSearch() {
		ListItemBO listItemBO = SpringContextUtil.getBean(ListItemBO.class);
		listItemForm.setListItemBeanItemVO(listItemBO.getItemList(
				listItemForm.getSelectItemCategory(),
				listItemForm.getItemnum(), listItemForm.getDescription(),
				listItemForm.getClassstructureid()));
	}

	public void onRowSelect(SelectEvent event) {
		this.inital();
		listItemForm
				.setListItemBeanItem((ListItemBeanItemVO) event.getObject());
		listItemForm.setActiveIndex(1);// 供應商明細 page
		listItemForm.setItemAttribute(listItemBO.getItemAttribute( // 其他屬性
				listItemForm.getListItemBeanItem().getItemid()));
		// ============================下載檔案===============================================
		this.getDownLoadFile();
		// ====================================================================================
		listItemForm.setAltitemlist(listItemBO.getAltitemList(listItemForm
				.getListItemBeanItem().getItemid())); // 可替代備品
		listItemForm.setListItemBeanInvvendorVO(listItemBO.getInvvendorList(
				listItemForm.getListItemBeanItem().getItemnum(), listItemForm
						.getListItemBeanItem().getItemsetid())); // 供應商

		listItemForm.setListItemSite(itemSiteBO.getItemSiteList(
				listItemForm.getListItemBeanItem().getItemnum(), true));  //生效區域
		listItemForm.setListItemSiteStop(itemSiteBO.getItemSiteList(
				listItemForm.getListItemBeanItem().getItemnum(), false));  //凍結區域
		
		listItemForm.setListItemMapping(itemMappingBO.getList(
				listItemForm.getListItemBeanItem().getItemnum()));
		listItemForm.setListZZpoMroVendorItem(zZpoMroVendorItemBO
				.getZZpoMroVendorItem(listItemForm.getListItemBeanItem()
						.getItemnum()));
		// ============================類別結構==================================================
		ItemInterface itemInterface = new ItemImpl();
		itemForm = itemInterface.setClassstructurePhase(itemForm,
				listItemForm.getListItemBeanItem().getClassstructureid());
	}

	public void onTabChange(TabChangeEvent event) {
		String tabId = event.getTab().getId();
		if (listItemForm.getListItemBeanItem()!=null && 
				StringUtils.isNotBlank(listItemForm.getListItemBeanItem().getItemnum())) {
			if(validateChangeTab(tabId)){  //判段是否有異動,若有異動即更新
				if (tabId.equals("tab3")) {
					this.onTab3();
				} else if (tabId.equals("tab4")) {
					this.onTab4();
					JsfContextUtils.executeView("PF('listItemBeanInventoryVO').clearFilters();");
				} else if (tabId.equals("tab5")) {
					this.onTab5();
					JsfContextUtils.executeView("PF('mc_history').clearFilters();");
					JsfContextUtils.executeView("PF('pr_status').clearFilters();");
					JsfContextUtils.executeView("PF('po_status').clearFilters();");
				} else if (tabId.equals("tab6")) {
					this.onTab6();
				} else if (tabId.equals("tab7")) {
					this.onTab7();
					JsfContextUtils.executeView("PF('matusetrans').clearFilters();");
					JsfContextUtils.executeView("PF('matrectrans').clearFilters();");
					JsfContextUtils.executeView("PF('listItemBeanPoMatrectransVO').clearFilters();");
				} else if (tabId.equals("tab8")) {
					this.onTab8();
					JsfContextUtils.executeView("PF('eam_snapshot_bss_t').clearFilters();");
				}
			}
		}

	}

	public void onTab3() {
		// 規格
		listItemForm.setListItemBeanItemspecVO(listItemBO
				.getItemSpecList(listItemForm.getListItemBeanItem()
						.getItemnum()));
		// 規格變更歷史
		listItemForm.setListItemBeanAItemspecVO(listItemBO
				.getAItemSpecList(listItemForm.getListItemBeanItem()
						.getItemid()));
	}

	public void onTab4() {
		// 各廠庫存
		listItemForm.setListItemBeanInventoryVO(listItemBO.getInventoryList(
				listItemForm.getListItemBeanItem().getItemnum(), listItemForm
						.getListItemBeanItem().getItemsetid()));
		// BSS 庫存資訊
		listItemForm.setEamSnapshotBssO(listItemBO
				.getEamSnapshotBssOList(listItemForm.getListItemBeanItem()
						.getItemnum()));
		listItemForm.onCopyTab4();
	}

	public void onTab5() {
		// 控管異動申請歷史
		listItemForm.setListItemBeanPrlineVO(listItemBO.getprLineList(
				listItemForm.getListItemBeanItem().getItemnum(), listItemForm
						.getListItemBeanItem().getItemsetid()));
		// 請購單狀況
		listItemForm.setListItemBeanPrlineStatusVO(listItemBO
				.getPrlineStatusList(listItemForm.getListItemBeanItem()
						.getItemnum()));
		// 訂單狀況
		listItemForm
				.setListItemBeanPolineStatusVO(listItemBO
						.getPolineList(listItemForm.getListItemBeanItem()
								.getItemnum()));
		listItemForm.onCopyTab5();

	}

	public void onTab6() {
		// 使用設備
		listItemForm.setListItemBeanSparepartVO(listItemBO.getSparepartList(
				listItemForm.getListItemBeanItem().getItemnum(), listItemForm
						.getListItemBeanItem().getItemsetid()));
	}

	public void onTab7() {
		// 領退料紀錄
		listItemForm.setListItemBeanMatusetransVO(listItemBO
				.getMatusetransList(listItemForm.getListItemBeanItem()
						.getItemnum(), listItemForm.getListItemBeanItem()
						.getItemsetid()));
		// 廠調紀錄
		listItemForm.setListItemBeanMatrectransVO(listItemBO
				.getMatrectransList(listItemForm.getListItemBeanItem()
						.getItemnum(), listItemForm.getListItemBeanItem()
						.getItemsetid(), "TRANSFER"));
		// PO入料紀錄
		listItemForm.setListItemBeanPoMatrectransVO(listItemBO
				.getMatrectransList(listItemForm.getListItemBeanItem()
						.getItemnum(), listItemForm.getListItemBeanItem()
						.getItemsetid(), "RECEIPT"));
		listItemForm.onCopyTab7();
	}

	public void onTab8() {
		// 耗用紀錄
		listItemForm.setEamSnapshotBssT(listItemBO
				.getEamSnapshotBssTList(listItemForm.getListItemBeanItem()
						.getItemnum()));

		listItemForm.onCopyTab8();
	}

	// ==========================================檔案===================================================================
	public void getDownLoadFile() { // 取得下載檔案
		AttachmentBO attachmentBO = SpringContextUtil.getBean(AttachmentBO.class);
		listItemForm.getDowloadFileMap().putAll(attachmentBO.getMap(
				listItemForm.getListItemBeanItem().getItemnum(), null, true));
	}

	//===========================判斷料號是否有異動===========================================
	public boolean validateChangeTab(String tabName){
		String tabItemnum=(String) ReflectUtils.getFieldValue(listItemForm, "itemnum"+tabName);
		String itemnum=listItemForm.getListItemBeanItem().getItemnum();
//		System.out.println(itemnum+","+tabItemnum+","+itemnum.equals(tabItemnum));
		if(!itemnum.equals(tabItemnum)){
			ReflectUtils.setFieldValue(listItemForm, "itemnum"+tabName, itemnum);
			return true;
		}
		return false;
	}
	// ============================================================================================

	public ItemForm getItemForm() {
		return itemForm;
	}

	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}
	public ListItemForm getListItemForm() {
		return listItemForm;
	}

	public void setListItemForm(ListItemForm listItemForm) {
		this.listItemForm = listItemForm;
	}

}
