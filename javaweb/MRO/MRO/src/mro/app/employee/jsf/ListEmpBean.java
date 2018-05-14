package mro.app.employee.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import mro.base.bo.PersonBO;
import mro.base.entity.Person;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.SpringContextUtil;


@ManagedBean(name="ListEmpBean")
@ViewScoped
public class ListEmpBean implements Serializable{
	private static final long serialVersionUID = 331362146222719317L;
	private List<Person> employeeList;
	private Person person;
	private String empNo;
	private String empName;
	private String deptCode;
	private String supervisorNo;
	private String supervisorName;
	private transient PersonBO personBO;
	
	
	public ListEmpBean(){
		
	}
	
	@PostConstruct
	public void init() {
		personBO=SpringContextUtil.getBean(PersonBO.class);
		employeeList=new ArrayList();
	}
	
	public void onRowSelect(SelectEvent event) { 
		person=(Person) event.getObject();
    }  
	public void onSearch(){
		employeeList=personBO.getPersonList(empNo,empName,deptCode,supervisorNo,supervisorName);
	}
	
	public void listener(AjaxBehaviorEvent event) {
		System.out.println("I did something  "+event.getComponent().toString());
		onSearch();
	}

//============================================================================================

	public List<Person> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Person> employeeList) {
		this.employeeList = employeeList;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getSupervisorNo() {
		return supervisorNo;
	}

	public void setSupervisorNo(String supervisorNo) {
		this.supervisorNo = supervisorNo;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}


	
}
