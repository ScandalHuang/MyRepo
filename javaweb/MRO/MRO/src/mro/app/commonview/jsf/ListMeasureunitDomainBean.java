package mro.app.commonview.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.bo.ListMeasureunitDomainBO;
import mro.base.bo.MeasureunitDomainBO;
import mro.base.entity.MeasureunitDomain;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.RowEditEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name="ListMeasureunitDomainBean")
@ViewScoped
public class ListMeasureunitDomainBean implements Serializable{
	private static final long serialVersionUID = 3957802582035456122L;
	private List<MeasureunitDomain> listMeasureunitDomain;
	private String domainid;
	
	public boolean addButton=false;
	
	public ListMeasureunitDomainBean(){
		
	}
	
	@PostConstruct
	public void init() {
		
	}
	public void onSearch(){
		addButton=false;
		MeasureunitDomainBO measureunitDomainBO = SpringContextUtil.getBean(MeasureunitDomainBO.class);
		listMeasureunitDomain=measureunitDomainBO.getList(domainid,false);
	}
	public void onEdit(RowEditEvent event) {
		ListMeasureunitDomainBO listMeasureunitDomainBO = SpringContextUtil.getBean(ListMeasureunitDomainBO.class);
		GlobalGrowl message = new GlobalGrowl();	
		MeasureunitDomain m=(MeasureunitDomain)event.getObject();

		boolean vaildate=this.onvaildate(m,message); //空值驗證
		if(vaildate){
			listMeasureunitDomainBO.updateMeasureunitDomain(m);
			message.addInfoMessage("Update", "Update successful.");
		}
	}
	public void onAdd() {
		ListMeasureunitDomainBO listMeasureunitDomainBO = SpringContextUtil.getBean(ListMeasureunitDomainBO.class);
		GlobalGrowl message = new GlobalGrowl();
		MeasureunitDomain addMeasureunitDomain=listMeasureunitDomain.get(0);
		boolean vaildate=this.onvaildate(addMeasureunitDomain,message); //空值驗證
		
		if(vaildate){
			listMeasureunitDomainBO.updateMeasureunitDomain(addMeasureunitDomain);
			addButton=false;
			message.addInfoMessage("Insert", "Insert successful.");
		}
	}
	public void onCancel() {
		listMeasureunitDomain.remove(0);
			addButton=false;
	}
	public void onAddList() {
		MeasureunitDomain m =new MeasureunitDomain();
		m.setDomainid(domainid);
		listMeasureunitDomain.add(0,m);
		addButton=true;
	}
	public boolean onvaildate(MeasureunitDomain m,GlobalGrowl message){
		ListMeasureunitDomainBO listMeasureunitDomainBO = SpringContextUtil.getBean(ListMeasureunitDomainBO.class);
		boolean vaildate=true;
		
		if(StringUtils.isEmpty(m.getDescription()))
		{message.addWarnMessage("Warn", "單位不能為空值!"); vaildate=false;}
		else if(listMeasureunitDomainBO.getMeasureunitDomainCount(m).compareTo(new BigDecimal("0"))==1){
			message.addWarnMessage("Warn", "單位重複!"); vaildate=false;
		}
		return vaildate;
	}
	//==========================================================================================
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

	public List<MeasureunitDomain> getListMeasureunitDomain() {
		return listMeasureunitDomain;
	}

	public void setListMeasureunitDomain(
			List<MeasureunitDomain> listMeasureunitDomain) {
		this.listMeasureunitDomain = listMeasureunitDomain;
	}
	
}
