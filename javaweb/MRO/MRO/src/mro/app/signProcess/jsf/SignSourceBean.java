package mro.app.signProcess.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.signProcess.form.SignSourceForm;
import mro.app.signProcess.service.SignSourceInterface;
import mro.app.signProcess.service.impl.SignSourceImpl;
import mro.base.entity.SignSourceCategory;
import mro.base.entity.SignSourceSystem;

import org.primefaces.event.SelectEvent;
@ManagedBean(name = "SignSourceBean")
@ViewScoped
public class SignSourceBean implements Serializable {
	private static final long serialVersionUID = 1407387979109090373L;
	private SignSourceForm signSourceForm;

	
	@PostConstruct
	public void init() {
		signSourceForm= new SignSourceForm();
		this.mainQuery(0);
	}
	
	public void mainQuery(int index){  //搜索清單
		SignSourceInterface signSourceInterface=new SignSourceImpl();
		signSourceInterface.mainQuery(signSourceForm,index);
	}
//=========================================Action===================================================
	public void onNew(){
		signSourceForm.onNew();
	}
	public void onSave(){
		SignSourceInterface signSourceInterface=new SignSourceImpl();
		signSourceInterface.onSave(signSourceForm);
	}
	

//===================================actionListener==================================================
	public void onSelect(SelectEvent event){  //選取清單
		onSelect((SignSourceSystem) event.getObject());
	}
	
	public void onSelect(SignSourceSystem signSourceSystem){  //選取清單
		SignSourceInterface signSourceInterface=new SignSourceImpl();
		signSourceInterface.onSelect(signSourceForm, signSourceSystem);
	}
	
	public void addLine(){ 
		SignSourceInterface signSourceInterface=new SignSourceImpl();
		signSourceInterface.addLine(signSourceForm);
	}
	public void deleteLine(SignSourceCategory signSourceCategory){ 
		SignSourceInterface signSourceInterface=new SignSourceImpl();
		signSourceInterface.deleteLine(signSourceForm,signSourceCategory);
	}
//===================================================================================================


	public SignSourceForm getSignSourceForm() {
		return signSourceForm;
	}

	public void setSignSourceForm(SignSourceForm signSourceForm) {
		this.signSourceForm = signSourceForm;
	}
	
}
