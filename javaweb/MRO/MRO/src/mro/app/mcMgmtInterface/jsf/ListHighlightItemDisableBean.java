package mro.app.mcMgmtInterface.jsf;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.bo.ListItemCommonBO;
import mro.base.bo.ItemControlConfigBO;
import mro.base.entity.Item;
import mro.base.entity.ItemControlConfig;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListHighlightItemDisableBean")
@ViewScoped
public class ListHighlightItemDisableBean {
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private List<ItemControlConfig> list; // 禁止控管Highlight料號
	private List<ItemControlConfig> listFilter; // 禁止控管Highlight料號Filter
	private ItemControlConfig itemControlConfig; // 新增的料號
	private transient ItemControlConfigBO itemControlConfigBO;
	private transient ListItemCommonBO listItemCommonBO;

	public ListHighlightItemDisableBean() {
		itemControlConfigBO = SpringContextUtil
				.getBean(ItemControlConfigBO.class);
		listItemCommonBO = new SpringContextUtil()
				.getBean(ListItemCommonBO.class);
	}

	@PostConstruct
	public void init() {
		this.getDisableHighlightList();
	}

	// ======================取得禁止控管Highlight料號================================
	public void getDisableHighlightList() {
		list = itemControlConfigBO.getList(null,null,"Y");
		listFilter = list;
		itemControlConfig = new ItemControlConfig();
	}

	// ======================更新禁止控管Highlight料號================================
	public void update(String action) {
		GlobalGrowl message = new GlobalGrowl();
		Item item = listItemCommonBO.getItem(itemControlConfig.getItemnum());
		if (item == null) {
			message.addErrorMessage("Error",
					"料號" + itemControlConfig.getItemnum() + "不存在!");
		} else {
			itemControlConfig.setItem(item);
			itemControlConfigBO.save(itemControlConfig,action,"highlightDisableFlag",loginInfoBean.getPerson());
			this.getDisableHighlightList();
			message.addInfoMessage("Success", "料號" + item.getItemnum()
					+ "更新成功!");
		}
	}

	// ==========================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public List<ItemControlConfig> getList() {
		return list;
	}

	public void setList(List<ItemControlConfig> list) {
		this.list = list;
	}

	public List<ItemControlConfig> getListFilter() {
		return listFilter;
	}

	public void setListFilter(List<ItemControlConfig> listFilter) {
		this.listFilter = listFilter;
	}

	public ItemControlConfig getItemControlConfig() {
		return itemControlConfig;
	}

	public void setItemControlConfig(ItemControlConfig itemControlConfig) {
		this.itemControlConfig = itemControlConfig;
	}

}
