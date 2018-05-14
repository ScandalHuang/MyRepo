package mro.app.signHr.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.base.bo.HrEmpBO;
import mro.base.entity.HrEmp;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.inx.commons.util.SpringContextUtil;


@ManagedBean(name="SignHrBean")
@ViewScoped
public class SignHrBean implements Serializable{
	private static final long serialVersionUID = 1391027059502174903L;
	private List<HrEmp> hrEmpList;
	private String condition;

    private Object object;
    private String updateView;
    private transient HrEmpBO hrEmpBO;
	
	public SignHrBean(){
		
	}
	
	@PostConstruct
	public void init() {
		hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		hrEmpList=new ArrayList<>();
	}
	
	public void onSearch(){
		hrEmpList=hrEmpBO.getList(condition);
	}
	public void save(HrEmp hrEmp) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException{
			//=========================setClassstructureuid=======================================
			Method method=object.getClass().getMethod("setEmployee",hrEmp.getClass());
			method.invoke(object, hrEmp);
			//=========================更新view=======================================
			if(StringUtils.isNotBlank(updateView)){ 
				RequestContext context = RequestContext.getCurrentInstance();  
				context.update(updateView);
			}
	}
	public void clearEmployee() 
		throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException{
		//=========================setClassstructureuid=======================================
		HrEmp hrEmp=new HrEmp();
		Method method=object.getClass().getMethod("setEmployee",hrEmp.getClass());
		method.invoke(object,hrEmp);
		//=========================更新view=======================================
		if(StringUtils.isNotBlank(updateView)){ 
			RequestContext context = RequestContext.getCurrentInstance();  
			context.update(updateView);
		}
	}
//============================================================================================

	public List<HrEmp> getHrEmpList() {
		return hrEmpList;
	}

	public void setHrEmpList(List<HrEmp> hrEmpList) {
		this.hrEmpList = hrEmpList;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
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
