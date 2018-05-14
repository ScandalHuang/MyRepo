package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.bo.ListItemCommonBO;
import mro.app.mcMgmtInterface.bo.ListItemDisableBO;
import mro.base.entity.Item;
import mro.base.entity.ItemDisableLog;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListItemDisableBean")
@ViewScoped
public class ListItemDisableBean implements Serializable {
	private static final long serialVersionUID = 2269495058471582039L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private List<ItemDisableLog> listItemDisableLog;
	private String itemnum;
	private String remark;
	private String disableFlag;
	private Item item;
	public ListItemDisableBean() {

	}

	@PostConstruct
	public void init() {
		listItemDisableLog=new LinkedList<>();
	}
	public void search() {
		GlobalGrowl message = new GlobalGrowl();
		ListItemDisableBO listItemDisableBO = SpringContextUtil
				.getBean(ListItemDisableBO.class);
		ListItemCommonBO listItemCommonBO=SpringContextUtil.getBean(ListItemCommonBO.class);
		item=listItemCommonBO.getItem(itemnum);
		if(item!=null){
			listItemDisableLog=listItemDisableBO.getItemDisableLogList(
					new String[]{"itemid"}, item.getItemid());
		}else{
			message.addWarnMessage("WARM!", "料號"+itemnum+"不存在!");
		}
	}
	public void update(){
		GlobalGrowl message = new GlobalGrowl();
		ListItemCommonBO listItemCommonBO=SpringContextUtil.getBean(ListItemCommonBO.class);
		ListItemDisableBO listItemDisableBO = SpringContextUtil.getBean(ListItemDisableBO.class);
		item=listItemCommonBO.getItem(itemnum);
		if(item!=null){
			listItemDisableBO.update(item, remark,disableFlag,loginInfoBean.getEmpNo());
				search();
			message.addInfoMessage("Submit", "Submit successful.");
		}else{
			message.addWarnMessage("WARM!", "料號"+itemnum+"不存在!");
		}
	}
	// ==========================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public List<ItemDisableLog> getListItemDisableLog() {
		return listItemDisableLog;
	}

	public void setListItemDisableLog(List<ItemDisableLog> listItemDisableLog) {
		this.listItemDisableLog = listItemDisableLog;
	}

	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDisableFlag() {
		return disableFlag;
	}

	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
