package mro.app.overview.jsf;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.overview.form.ItemOverviewForm;
import mro.app.reportView.jsf.R1HalfUseListBean;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.jsf.SystemConfigBean;

import org.primefaces.json.JSONObject;

import com.inx.commons.util.Utility;


@ManagedBean(name="ListItemOverviewBean")
@ViewScoped
public class ListItemOverviewBean implements Serializable{
	
	@ManagedProperty(value = "#{SystemConfigBean}")
	private SystemConfigBean systemConfigBean;
	@ManagedProperty(value = "#{R1InvbalancesListBean}")
	private R1InvbalancesListBean r1InvbalancesListBean;
	@ManagedProperty(value = "#{R2InvbalancesListBean}")
	private R2InvbalancesListBean r2InvbalancesListBean;
	@ManagedProperty(value = "#{ZErpUnProcessListBean}")
	private ZErpUnProcessListBean zErpUnProcessListBean;
	@ManagedProperty(value = "#{ZMroOpenPoListBean}")
	private ZMroOpenPoListBean zMroOpenPoListBean;
	@ManagedProperty(value = "#{R1HalfUseListBean}")
	private R1HalfUseListBean r1HalfUseListBean;
	
    ItemOverviewForm itemOverviewForm;

    
	public ListItemOverviewBean(){
		
	}
	
	@PostConstruct
	public void init() {
		itemOverviewForm=new ItemOverviewForm();
	}
	public void onSearch(String itemnum,String itemCategory){
		String reportName="";
		if(Utility.equalsOR(itemCategory,ItemType.R2,ItemType.R94)){
			r2InvbalancesListBean.setItemnum(itemnum);
			r2InvbalancesListBean.setSearchFlag(false);
			r2InvbalancesListBean.search();
		}else if(itemCategory.equals(ItemType.R1.toString())){
			r1InvbalancesListBean.setItemnum(itemnum);
			r1InvbalancesListBean.setSearchFlag(false);
			r1InvbalancesListBean.search();
		}
	}
	public void onSearch5(String itemnum){
		zErpUnProcessListBean.setItemnum(itemnum);
		zErpUnProcessListBean.setSearchFlag(false);
		zErpUnProcessListBean.search();
	}
	public void onSearch6(String itemnum){
		zMroOpenPoListBean.setItemnum(itemnum);
		zMroOpenPoListBean.setSearchFlag(false);
		zMroOpenPoListBean.search();
	}
	//================ListPrLine參考資料====================================
	public void onSearchPrline(String itemnum,String itemCategory){
		itemOverviewForm.setItemtype(itemCategory);
		onSearch(itemnum,itemCategory);
		onSearch5(itemnum);
		onSearch6(itemnum);
	}
	//================ListPrLine參考資料====================================
	public void onHalfyearIssueCounter(String itemnum, String deptCode) {
		r1HalfUseListBean.setItemnum(itemnum);
		r1HalfUseListBean.setDeptNo(deptCode);
		r1HalfUseListBean.setSearchFlag(false);
		r1HalfUseListBean.search();
	}
	public JSONObject getJson(Map map){
		JSONObject json = new JSONObject(map);
		return json;
	}
//============================================================================================

	public ItemOverviewForm getItemOverviewForm() {
		return itemOverviewForm;
	}

	public void setItemOverviewForm(ItemOverviewForm itemOverviewForm) {
		this.itemOverviewForm = itemOverviewForm;
	}

	public SystemConfigBean getSystemConfigBean() {
		return systemConfigBean;
	}

	public void setSystemConfigBean(SystemConfigBean systemConfigBean) {
		this.systemConfigBean = systemConfigBean;
	}


	public R1InvbalancesListBean getR1InvbalancesListBean() {
		return r1InvbalancesListBean;
	}

	public void setR1InvbalancesListBean(R1InvbalancesListBean r1InvbalancesListBean) {
		this.r1InvbalancesListBean = r1InvbalancesListBean;
	}

	public R2InvbalancesListBean getR2InvbalancesListBean() {
		return r2InvbalancesListBean;
	}

	public void setR2InvbalancesListBean(R2InvbalancesListBean r2InvbalancesListBean) {
		this.r2InvbalancesListBean = r2InvbalancesListBean;
	}

	public ZErpUnProcessListBean getzErpUnProcessListBean() {
		return zErpUnProcessListBean;
	}

	public void setzErpUnProcessListBean(ZErpUnProcessListBean zErpUnProcessListBean) {
		this.zErpUnProcessListBean = zErpUnProcessListBean;
	}

	public ZMroOpenPoListBean getzMroOpenPoListBean() {
		return zMroOpenPoListBean;
	}

	public void setzMroOpenPoListBean(ZMroOpenPoListBean zMroOpenPoListBean) {
		this.zMroOpenPoListBean = zMroOpenPoListBean;
	}

	public R1HalfUseListBean getR1HalfUseListBean() {
		return r1HalfUseListBean;
	}

	public void setR1HalfUseListBean(R1HalfUseListBean r1HalfUseListBean) {
		this.r1HalfUseListBean = r1HalfUseListBean;
	}
	
}
