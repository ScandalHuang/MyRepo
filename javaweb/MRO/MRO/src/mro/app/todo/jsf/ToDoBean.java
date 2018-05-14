package mro.app.todo.jsf;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.HrDeputyActiveVBO;
import mro.base.bo.MroApplySignListBO;
import mro.base.bo.SignProcessBO;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ToDoBean")
@ViewScoped
public class ToDoBean implements Serializable {
	private static final long serialVersionUID = 7992529520643790679L;
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	private Map inprgMap;
	private Map rejectMap;
	
	private int signTotal=0;
	private int rejectTotal=0;
	
	private transient MroApplySignListBO mroApplySignListBO;
	private transient SignProcessBO signProcessBO;
	private transient HrDeputyActiveVBO hrDeputyActiveVBO;
	
	public ToDoBean() {

	}

	@PostConstruct
	public void init() {
		mroApplySignListBO=SpringContextUtil.getBean(MroApplySignListBO.class);
		signProcessBO=SpringContextUtil.getBean(SignProcessBO.class);
		hrDeputyActiveVBO=SpringContextUtil.getBean(HrDeputyActiveVBO.class);
		List empNos=hrDeputyActiveVBO.getUnionEmp(loginInfoBean.getEmpNo());	//代理人+登錄者
		
		Map toDoMap=signProcessBO.getMap(true);
		inprgMap=getMap(toDoMap, mroApplySignListBO.getSignMap(SignStatus.INPRG,empNos));
		signTotal=setTotal(inprgMap,signTotal);

		rejectMap=getMap(toDoMap, mroApplySignListBO.getSignMap(SignStatus.REJECT, empNos));
		rejectTotal=setTotal(rejectMap,rejectTotal);	
	}
	public Map getMap(Map toDoMap,Map signMap){
		Map map=new LinkedHashMap();
		map.putAll(toDoMap);
		map.putAll(signMap);
		return map;
	}
	public int setTotal(Map map,int total){
		Iterator iter=map.entrySet().iterator();
		while (iter.hasNext()) {
		    Entry entry = (Entry) iter.next();
		    
		    total+=((int)entry.getValue());
		}
		return total;
	}
	

	// ============================================================================================


	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public Map getInprgMap() {
		return inprgMap;
	}

	public void setInprgMap(Map inprgMap) {
		this.inprgMap = inprgMap;
	}

	public Map getRejectMap() {
		return rejectMap;
	}

	public void setRejectMap(Map rejectMap) {
		this.rejectMap = rejectMap;
	}

	public int getSignTotal() {
		return signTotal;
	}

	public void setSignTotal(int signTotal) {
		this.signTotal = signTotal;
	}

	public int getRejectTotal() {
		return rejectTotal;
	}

	public void setRejectTotal(int rejectTotal) {
		this.rejectTotal = rejectTotal;
	}

}
