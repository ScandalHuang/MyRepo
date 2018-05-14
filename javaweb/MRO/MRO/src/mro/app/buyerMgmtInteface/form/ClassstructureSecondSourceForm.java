package mro.app.buyerMgmtInteface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.entity.ClassstructureSecondSource;

public class ClassstructureSecondSourceForm implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<ClassstructureSecondSource> list;
	private ClassstructureSecondSource[] deleteList;
	private String sClassstructureid;
	private Map option;
	
	public ClassstructureSecondSourceForm(){
		intial();
	}
	
	public void intial(){
		list=new ArrayList<>();
		deleteList=null;

	}

	public List<ClassstructureSecondSource> getList() {
		return list;
	}

	public void setList(List<ClassstructureSecondSource> list) {
		this.list = list;
	}

	public ClassstructureSecondSource[] getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(ClassstructureSecondSource[] deleteList) {
		this.deleteList = deleteList;
	}

	public String getsClassstructureid() {
		return sClassstructureid;
	}

	public void setsClassstructureid(String sClassstructureid) {
		this.sClassstructureid = sClassstructureid;
	}

	public Map getOption() {
		return option;
	}

	public void setOption(Map option) {
		this.option = option;
	}
}
