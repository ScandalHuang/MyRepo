package mro.app.commonview.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.bo.ListAssetattributeBO;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.AssetattributeBO;
import mro.base.entity.Assetattribute;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.RowEditEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;


@ManagedBean(name="ListAssetattributeBean")
@ViewScoped
public class ListAssetattributeBean implements Serializable{
	private static final long serialVersionUID = 4837769171152209692L;
	private List<Assetattribute> listAssetattribute;
	private List<Assetattribute> filterlistAssetattribute;
    
	public boolean addButton=false;
	public boolean selectButton=false;
	
	private Map datatype;
	
	public ListAssetattributeBean(){
		
	}
	
	@PostConstruct
	public void init() {
		this.query();
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		datatype=bean.getParameterOption().get(ParameterType.ATTRIBUTE_DATA_TYPE);
	}
	public void query(){
		AssetattributeBO assetattributeBO = SpringContextUtil.getBean(AssetattributeBO.class);
		listAssetattribute=assetattributeBO.getList();
		filterlistAssetattribute=listAssetattribute;
	}
	public void onEditAttribute(RowEditEvent event) {
		ListAssetattributeBO listAssetattributeBO = SpringContextUtil.getBean(ListAssetattributeBO.class);
		GlobalGrowl message = new GlobalGrowl();	
		Assetattribute a=(Assetattribute)event.getObject();
		boolean vaildate=this.onvaildate(a,message); //空值驗證
		if(vaildate){
			listAssetattributeBO.updateAssetattribute(a);
			message.addInfoMessage("Update", "Update successful.");
			this.query();
		}
	}
	public void onAddAttribute() {
		ListAssetattributeBO listAssetattributeBO = SpringContextUtil.getBean(ListAssetattributeBO.class);
		GlobalGrowl message = new GlobalGrowl();
		Assetattribute addAssetattribute=listAssetattribute.get(0);
		boolean vaildate=this.onvaildate(addAssetattribute,message); //空值驗證
		
		if(vaildate){
			listAssetattributeBO.updateAssetattribute(addAssetattribute);
			addButton=false;
			message.addInfoMessage("Insert", "Insert successful.");
			this.query();
		}
	}
	public void onCancelAttribute() {
		listAssetattribute.remove(0);
		filterlistAssetattribute=listAssetattribute;
		addButton=false;
	}
	public void onAddList() {
		listAssetattribute.add(0,new Assetattribute());
		filterlistAssetattribute=listAssetattribute;
		addButton=true;
	}
	public boolean onvaildate(Assetattribute a,GlobalGrowl message){
		ListAssetattributeBO listAssetattributeBO = SpringContextUtil.getBean(ListAssetattributeBO.class);
		boolean vaildate=true;
		 
		if(StringUtils.isEmpty(a.getAssetattrid()))
		{message.addWarnMessage("Warn", "屬性不能為空值!"); vaildate=false;}
		if(!StringUtils.isEmpty(a.getAssetattrid()) && a.getAssetattributeid()==null
				&& listAssetattributeBO.getAssetattributeSize(a.getAssetattrid())>0)
		{message.addWarnMessage("Warn", "屬性已存在!"); vaildate=false;}
		
		return vaildate;
	}
	public void onSelect(){
		selectButton=true;
		filterlistAssetattribute=listAssetattribute;
	}
	public void onEdit(){
		selectButton=false;
		filterlistAssetattribute=listAssetattribute;
	}
//==========================================================================================
	
	public List<Assetattribute> getListAssetattribute() {
			return listAssetattribute;
	}

	public void setListAssetattribute(List<Assetattribute> listAssetattribute) {
		this.listAssetattribute = listAssetattribute;
	}

	public boolean isAddButton() {
		return addButton;
	}

	public void setAddButton(boolean addButton) {
		this.addButton = addButton;
	}

	public Map getDatatype() {
		return datatype;
	}

	public void setDatatype(Map datatype) {
		this.datatype = datatype;
	}

	public boolean isSelectButton() {
		return selectButton;
	}

	public void setSelectButton(boolean selectButton) {
		this.selectButton = selectButton;
	}

	public List<Assetattribute> getFilterlistAssetattribute() {
		return filterlistAssetattribute;
	}

	public void setFilterlistAssetattribute(
			List<Assetattribute> filterlistAssetattribute) {
		this.filterlistAssetattribute = filterlistAssetattribute;
	}

	
}
