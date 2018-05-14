package mro.base.workflow.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name="WorkFlowBean")
@ViewScoped
public class WorkFlowBean implements Serializable{
	
	public WorkFlowBean(){
		
	}
	
	@PostConstruct
	public void init() {

	}

}
