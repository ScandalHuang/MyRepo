package mro.app.vendor.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.base.bo.VwNewvendorcodeEpmallBO;
import mro.base.entity.VwNewvendorcodeEpmall;

import com.inx.commons.util.SpringContextUtil;


@ManagedBean(name="ListVendorBean")
@ViewScoped
public class ListVendorBean implements Serializable{
	private static final long serialVersionUID = -139672535228184783L;

	private List<VwNewvendorcodeEpmall> vwNewvendorcodeEpmallList;
	
	private String vatRegistration;
	private String name;
	
	private transient VwNewvendorcodeEpmallBO vwNewvendorcodeEpmallBO;
	
	public ListVendorBean(){
		
	}
	
	@PostConstruct
	public void init() {
		vwNewvendorcodeEpmallBO = SpringContextUtil.getBean(VwNewvendorcodeEpmallBO.class);
		vwNewvendorcodeEpmallList=new ArrayList();
	}
	public void onSearch(){
		vwNewvendorcodeEpmallList=vwNewvendorcodeEpmallBO.getList(vatRegistration,name);
	}

//============================================================================================
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
	public List<VwNewvendorcodeEpmall> getVwNewvendorcodeEpmallList() {
		return vwNewvendorcodeEpmallList;
	}

	public void setVwNewvendorcodeEpmallList(
			List<VwNewvendorcodeEpmall> vwNewvendorcodeEpmallList) {
		this.vwNewvendorcodeEpmallList = vwNewvendorcodeEpmallList;
	}
}
