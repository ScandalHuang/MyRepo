package mro.app.commonview.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.SelectEvent;

import mro.base.bo.HrDeputyActiveVBO;
import mro.base.bo.PersonBO;
import mro.base.entity.Person;
import mro.base.entity.view.HrDeputyActiveV;
import mro.utility.JsfContextUtils;

import com.inx.commons.util.SpringContextUtil;


@ManagedBean(name="PersonDetailBean")
@ViewScoped
public class PersonDetailBean implements Serializable{
	private static final long serialVersionUID = -6030177060942982201L;
	private Person person;
	private HrDeputyActiveV hrDeputyActiveV;
	private transient PersonBO personBO;
	private transient HrDeputyActiveVBO hrDeputyActiveVBO;
	
	
	public PersonDetailBean(){
		
	}
	
	@PostConstruct
	public void init() {
		personBO=SpringContextUtil.getBean(PersonBO.class);
		hrDeputyActiveVBO=SpringContextUtil.getBean(HrDeputyActiveVBO.class);
	}
	

	
	public void onRowSelect(SelectEvent event) { 
		this.updateView((Person) event.getObject());
    }  
	
	public void onView(String empNo){
		this.updateView(personBO.getPerson(empNo));
	}
	
	private void updateView(Person person){
		this.person=person;
		hrDeputyActiveV=hrDeputyActiveVBO.getHrDeputyActiveV(person.getPersonId());
		JsfContextUtils.executeView("PF('PDetailDialog').show();");
		JsfContextUtils.updateView("PDetailForm"); //更新上傳檔案頁面
	}
	
	//============================================================================================
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public HrDeputyActiveV getHrDeputyActiveV() {
		return hrDeputyActiveV;
	}

	public void setHrDeputyActiveV(HrDeputyActiveV hrDeputyActiveV) {
		this.hrDeputyActiveV = hrDeputyActiveV;
	}


}
