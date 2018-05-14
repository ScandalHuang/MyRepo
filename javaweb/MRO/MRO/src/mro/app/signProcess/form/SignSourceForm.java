package mro.app.signProcess.form;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import mro.base.entity.SignSourceCategory;
import mro.base.entity.SignSourceSystem;

public class SignSourceForm implements Serializable {
	private static final long serialVersionUID = 8939543523626725629L;
	private SignSourceSystem signSourceSystem;
	private List<SignSourceSystem> listSignSourceSystem;
	private List<SignSourceCategory> listSignSourceCategory;
	private List<SignSourceCategory> deleteSignSourceCategoryList;
	
	private int activeIndex;//  page
	
	public SignSourceForm(){
		
	}
	
	public void inital(){
		signSourceSystem=null;
		listSignSourceCategory=new LinkedList<>();
		deleteSignSourceCategoryList=new LinkedList<>();
		activeIndex=0;
	}
	
	public void onNew(){
		this.inital();
		signSourceSystem=new SignSourceSystem();
		signSourceSystem.setDelted("0");
		activeIndex=1;
	}

	public SignSourceSystem getSignSourceSystem() {
		return signSourceSystem;
	}

	public void setSignSourceSystem(SignSourceSystem signSourceSystem) {
		this.signSourceSystem = signSourceSystem;
	}

	public List<SignSourceSystem> getListSignSourceSystem() {
		return listSignSourceSystem;
	}

	public void setListSignSourceSystem(List<SignSourceSystem> listSignSourceSystem) {
		this.listSignSourceSystem = listSignSourceSystem;
	}

	public List<SignSourceCategory> getListSignSourceCategory() {
		return listSignSourceCategory;
	}

	public void setListSignSourceCategory(
			List<SignSourceCategory> listSignSourceCategory) {
		this.listSignSourceCategory = listSignSourceCategory;
	}

	public int getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(int activeIndex) {
		this.activeIndex = activeIndex;
	}

	public List<SignSourceCategory> getDeleteSignSourceCategoryList() {
		return deleteSignSourceCategoryList;
	}

	public void setDeleteSignSourceCategoryList(
			List<SignSourceCategory> deleteSignSourceCategoryList) {
		this.deleteSignSourceCategoryList = deleteSignSourceCategoryList;
	}
	
}
