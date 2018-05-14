package mro.app.reportView.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.form.InventoryStockReportForm;
import mro.app.reportView.viewType.InventoryStockReportType;
import mro.utility.ListUtility;
import mro.utility.ValidationUtils;
import mro.utility.vo.LinkedModel;

@ManagedBean(name = "InventoryStockReportBean")
@ViewScoped
public class InventoryStockReportBean implements Serializable {
	private static final long serialVersionUID = -6112916887378313851L;
	
	private InventoryStockReportForm form;
	
	
	public InventoryStockReportBean() {
		
	}

	@PostConstruct
	public void init() {
		form = new InventoryStockReportForm();
	}
	
	public void search() {
		if(ValidationUtils.validationCondition(form.getItemnum())){
			form.intial();
			this.searchType(InventoryStockReportType.STOCKS, form);
		}
	}
	
	public void searchType(InventoryStockReportType methodType,Object object) {
		form.setList(methodType.getList(object), methodType,true);
	}
	
	public void getNode(int node){
		LinkedModel linkedModel=form.getSLinked().get(node);
		form.setList(linkedModel.getList(), 
				(InventoryStockReportType)linkedModel.getType(),false);
		ListUtility.removeNodeLast(form.getSLinked(), node);
	}
	
	// ==========================================================================================

	public InventoryStockReportForm getForm() {
		return form;
	}

	public void setForm(InventoryStockReportForm form) {
		this.form = form;
	}

}
