package mro.app.commonview.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.bo.ListAlndomainBO;
import mro.base.System.config.SystemConfig;
import mro.base.entity.Alndomain;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.RowEditEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name="ListAlndomainBean")
@ViewScoped
public class ListAlndomainBean implements Serializable{
	private static final long serialVersionUID = 5648814749851215042L;
	private List<Alndomain> listAlndomain;
	private String domainid;
	
	public boolean addButton=false;
	
	public ListAlndomainBean(){
		
	}
	
	@PostConstruct
	public void init() {
		
	}
	public void onSearch(){
		addButton=false;
		ListAlndomainBO listAlndomainBO = SpringContextUtil.getBean(ListAlndomainBO.class);
		listAlndomain=listAlndomainBO.getAlndomainList(domainid);
	}
	public void onEdit(RowEditEvent event) {
		ListAlndomainBO listAlndomainBO = SpringContextUtil.getBean(ListAlndomainBO.class);
		GlobalGrowl message = new GlobalGrowl();	
		Alndomain a=(Alndomain)event.getObject();
		boolean vaildate=this.onvaildate(a,message); //驗證
		if(vaildate){ //驗證通過
			listAlndomainBO.updateAlndomain(a);
			message.addInfoMessage("Update", "Update successful.");
		}else{
			this.onSearch();
		}
	}
	public void onAdd() {
		ListAlndomainBO listAlndomainBO = SpringContextUtil.getBean(ListAlndomainBO.class);
		GlobalGrowl message = new GlobalGrowl();
		Alndomain addAlndomain=listAlndomain.get(0);
		boolean vaildate=this.onvaildate(addAlndomain,message); //驗證
		
		if(vaildate){
			listAlndomainBO.updateAlndomain(addAlndomain);
			addButton=false;
			message.addInfoMessage("Insert", "Insert successful.");
		}
	}
	public void onDelete(Alndomain alndomain) {
		ListAlndomainBO listAlndomainBO = SpringContextUtil.getBean(ListAlndomainBO.class);
		GlobalGrowl message = new GlobalGrowl();
		String asset=alndomain.getDomainid().substring(alndomain.getDomainid().length() - 4, alndomain.getDomainid().length());
		if(SystemConfig.defaultNonEditSpec().get(asset)!=null){
			listAlndomainBO.deleteAlndomain(alndomain);
			message.addInfoMessage("Delete", "Delete successful.");
		}else{
			message.addErrorMessage("Error","此屬性不得刪除!");
		}
	}
	public void onCancel() {
			listAlndomain.remove(0);
			addButton=false;
	}
	public void onAddList() {
		Alndomain a =new Alndomain();
		a.setDomainid(domainid);
		listAlndomain.add(0,a);
		addButton=true;
	}
	public boolean onvaildate(Alndomain a,GlobalGrowl message){
		ListAlndomainBO listAlndomainBO = SpringContextUtil.getBean(ListAlndomainBO.class);
		boolean vaildate=true;
		
		if(StringUtils.isEmpty(a.getDescription()))
		{message.addWarnMessage("Warn", "屬性敘述不能為空值!"); vaildate=false;}
		else if(listAlndomainBO.getAlndomainCount(a,false).compareTo(new BigDecimal("0"))==1){ ///所有清單都必須比較
			message.addWarnMessage("Warn", "敘述重複!"); vaildate=false;
		}
		
		return vaildate;
	}
	//==========================================================================================

	public List<Alndomain> getListAlndomain() {
		return listAlndomain;
	}

	public void setListAlndomain(List<Alndomain> listAlndomain) {
		this.listAlndomain = listAlndomain;
	}

	public String getDomainid() {
		return domainid;
	}

	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}

	public boolean isAddButton() {
		return addButton;
	}

	public void setAddButton(boolean addButton) {
		this.addButton = addButton;
	}
	
}
