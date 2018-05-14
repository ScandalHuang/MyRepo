package mro.app.commonview.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.base.bo.VwNewvendorcodeEpmallBO;
import mro.base.entity.VwNewvendorcodeEpmall;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name="ListCompaniesBean")
@ViewScoped
public class ListCompaniesBean implements Serializable{ 
	private static final long serialVersionUID = -5829844626113951158L;

	private List<VwNewvendorcodeEpmall> listVwNewvendorcodeEpmall;
	
	private String vatRegistration;
	private String name;
	
    private Object object;
    private String updateView;

	private transient VwNewvendorcodeEpmallBO vwNewvendorcodeEpmallBO;
	
	private boolean searchButton=false;
		
	public ListCompaniesBean(){
		
	}
	
	@PostConstruct
	public void init() {
		vwNewvendorcodeEpmallBO=SpringContextUtil.getBean(VwNewvendorcodeEpmallBO.class);
		searchButton = true;
		listVwNewvendorcodeEpmall= new ArrayList<>();
	}
	public void onSearch(){
		listVwNewvendorcodeEpmall=vwNewvendorcodeEpmallBO.getList(vatRegistration,name);
	}
	
	public void onItemSearch(String itemnum){  //料號主檔供應商顯示
		listVwNewvendorcodeEpmall=vwNewvendorcodeEpmallBO.getListByItemnum(itemnum);
		searchButton = false;
	}
	
	public void save(VwNewvendorcodeEpmall vwNewvendorcodeEpmall) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException{
			//=========================setClassstructureuid=======================================
			Method method=object.getClass().getMethod("setCompany", 
					vwNewvendorcodeEpmall.getClass());
			method.invoke(object, vwNewvendorcodeEpmall);
			//=========================更新view=======================================
			if(StringUtils.isNotBlank(updateView)){ 
				RequestContext context = RequestContext.getCurrentInstance();  
				context.update(updateView);
			}
	}
	//================================Common Method====================================================
	public VwNewvendorcodeEpmall getSupplierHeader(String vendor){
			return vwNewvendorcodeEpmallBO.getListByNvcId(vendor);
	}
	public VwNewvendorcodeEpmall getSupplierHeaderVendorId(String vendorId){
		return vwNewvendorcodeEpmallBO.getListByOracleID(new BigDecimal(vendorId));
	}
	//=================================================================================================

	public String getVatRegistration() {
		return vatRegistration;
	}

	public void setVatRegistration(String vatRegistration) {
		this.vatRegistration = vatRegistration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VwNewvendorcodeEpmall> getListVwNewvendorcodeEpmall() {
		return listVwNewvendorcodeEpmall;
	}

	public void setListVwNewvendorcodeEpmall(
			List<VwNewvendorcodeEpmall> listVwNewvendorcodeEpmall) {
		this.listVwNewvendorcodeEpmall = listVwNewvendorcodeEpmall;
	}

	public boolean isSearchButton() {
		return searchButton;
	}

	public void setSearchButton(boolean searchButton) {
		this.searchButton = searchButton;
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

}
