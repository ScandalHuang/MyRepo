package mro.app.item.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.form.ItemForm;

@ManagedBean(name = "AItemChangeBean")
@ViewScoped
public class AItemChangeBean implements Serializable {
	private ItemForm itemForm;
	private ItemForm preItemForm;
	
	public AItemChangeBean() {

	}

	@PostConstruct
	public void init() {
		itemForm=new ItemForm();
		preItemForm=new  ItemForm();
	}

	public ItemForm getItemForm() {
		return itemForm;
	}

	public void setItemForm(ItemForm itemForm) {
		this.itemForm = itemForm;
	}

	public ItemForm getPreItemForm() {
		return preItemForm;
	}

	public void setPreItemForm(ItemForm preItemForm) {
		this.preItemForm = preItemForm;
	}

}
