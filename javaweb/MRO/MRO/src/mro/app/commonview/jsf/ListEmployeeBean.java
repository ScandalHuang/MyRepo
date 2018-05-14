package mro.app.commonview.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.base.bo.PersonBO;
import mro.base.entity.Person;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListEmployeeBean")
@ViewScoped
public class ListEmployeeBean implements Serializable {
	private static final long serialVersionUID = 351942817927453849L;
	private List<Person> employeeList;
	private String condition;
	
    private Object object;
    private String updateView;
    
	private boolean searchButton;
	private boolean activeStatus;  //是否只顯示生效人員
	
	private transient PersonBO personBO;

	public ListEmployeeBean() {

	}

	@PostConstruct
	public void init() {
		personBO = SpringContextUtil.getBean(PersonBO.class);
		searchButton = true;
		activeStatus = false;
		employeeList = new ArrayList<>();
	}

	public void onSearch() {
		GlobalGrowl message = new GlobalGrowl();
		if (StringUtils.isNotBlank(condition)) {
			employeeList = personBO.getPersonList(condition,activeStatus);
		} else {
			message.addWarnMessage("Warn", "請先填寫搜索條件!");
		}
	}

	public void onDepotSearch(String deptCode) {
		employeeList = personBO.getPersonList(deptCode,activeStatus);
		searchButton = false;
	}
	public void save(Person person) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException{
			//=========================setClassstructureuid=======================================
			Method method=object.getClass().getMethod("setEmployee", Person.class);
			method.invoke(object, person);
			//=========================更新view=======================================
			if(StringUtils.isNotBlank(updateView)){ 
				RequestContext context = RequestContext.getCurrentInstance();  
				context.update(updateView);
			}
	}

	// ============================================================================================

	public List<Person> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Person> employeeList) {
		this.employeeList = employeeList;
	}

	public boolean isSearchButton() {
		return searchButton;
	}

	public void setSearchButton(boolean searchButton) {
		this.searchButton = searchButton;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getUpdateView() {
		return updateView;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}

}
