package mro.app.alitem.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.alitem.bo.ItemListALItemBo;
import mro.app.alitem.vo.ItemListALItemVO;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ItemListALItemBean")
@ViewScoped
public class ItemListALItemBean implements Serializable {
	
	private static final long serialVersionUID = 1525223099275867613L;
	
	private List<ItemListALItemVO> itemAltitemList;
	private ItemListALItemVO itemListALItem;
	private String itemnum;
	private String itemnumDescription;
	private String altitemnum;
	private String altItemnumDescription;
	private String selectItemCategory;
	
	private Map itemCategoryOption;


	public ItemListALItemBean() {

	}

	@PostConstruct
	public void init() {
		itemAltitemList = new ArrayList();
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		itemCategoryOption=bean.getParameterOption().get(ParameterType.ITEM_CATEGORY);

	}

	public void onRowSelect(SelectEvent event) {
		itemListALItem = (ItemListALItemVO) event.getObject();
	}

	public void onSearch() {
		ItemListALItemBo itemListALItemBo = SpringContextUtil
				.getBean(ItemListALItemBo.class);
		itemAltitemList = itemListALItemBo.getItemAltitemList(
				selectItemCategory, itemnum, itemnumDescription, altitemnum,
				altItemnumDescription);

	}
	// ============================================================================================

	public List<ItemListALItemVO> getItemAltitemList() {
		return itemAltitemList;
	}

	public void setItemAltitemList(List<ItemListALItemVO> itemAltitemList) {
		this.itemAltitemList = itemAltitemList;
	}

	public ItemListALItemVO getItemListALItem() {
		return itemListALItem;
	}

	public void setItemListALItem(ItemListALItemVO itemListALItem) {
		this.itemListALItem = itemListALItem;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getItemnumDescription() {
		return itemnumDescription;
	}

	public void setItemnumDescription(String itemnumDescription) {
		this.itemnumDescription = itemnumDescription;
	}

	public String getAltitemnum() {
		return altitemnum;
	}

	public void setAltitemnum(String altitemnum) {
		this.altitemnum = altitemnum;
	}

	public String getAltItemnumDescription() {
		return altItemnumDescription;
	}

	public void setAltItemnumDescription(String altItemnumDescription) {
		this.altItemnumDescription = altItemnumDescription;
	}

	public String getSelectItemCategory() {
		return selectItemCategory;
	}

	public void setSelectItemCategory(String selectItemCategory) {
		this.selectItemCategory = selectItemCategory;
	}

	public Map getItemCategoryOption() {
		return itemCategoryOption;
	}

	public void setItemCategoryOption(Map itemCategoryOption) {
		this.itemCategoryOption = itemCategoryOption;
	}	
	
}
