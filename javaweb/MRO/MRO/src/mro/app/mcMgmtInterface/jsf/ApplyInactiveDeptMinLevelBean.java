package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.mcMgmtInterface.bo.ApplyInactiveDeptMinLevelBo;
import mro.base.bo.InvbalancesBO;
import mro.base.entity.Invbalances;
import mro.base.loginInfo.jsf.LoginInfoBean;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ApplyInactiveDeptMinLevelBean")
@ViewScoped
public class ApplyInactiveDeptMinLevelBean implements Serializable {
	private static final long serialVersionUID = -5295832605683531366L;
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	private List<Invbalances> invbalancesList;
	private Invbalances invbalances;  //選取的invbalance
	private String inactiveRemark;
	
	public ApplyInactiveDeptMinLevelBean() {

	}

	@PostConstruct
	public void init() {
		inital();
		query();
	}
	
	public void inital(){
		invbalances=new Invbalances();
	}
	
	public void query(){ //查詢失效部門控管資料
		InvbalancesBO invbalancesBO=SpringContextUtil.getBean(InvbalancesBO.class);
		LoginInfoBean bean=JsfContextUtil.getBean(LoginInfoBean.class.getSimpleName());
		invbalancesList=invbalancesBO.getInactiveDept(new ArrayList(bean.getUserLSMap().values()));
	}
	
	
	public void updateMinLevel(){ //更新控管量
		ApplyInactiveDeptMinLevelBo applyInactiveDeptMinLevelBo=SpringContextUtil.getBean(ApplyInactiveDeptMinLevelBo.class);
		GlobalGrowl message = new GlobalGrowl();
		applyInactiveDeptMinLevelBo.setInvbalances(invbalances,loginInfoBean.getEmpNo(),
				new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),inactiveRemark);
		message.addInfoMessage("Save", "Itemnum "+invbalances.getItemnum()+" save successful.");
		inital();
		query();
	}

	//============================================================================================
	public List<Invbalances> getInvbalancesList() {
		return invbalancesList;
	}

	public void setInvbalancesList(List<Invbalances> invbalancesList) {
		this.invbalancesList = invbalancesList;
	}

	public Invbalances getInvbalances() {
		return invbalances;
	}

	public void setInvbalances(Invbalances invbalances) {
		this.invbalances = invbalances;
	}

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public String getInactiveRemark() {
		return inactiveRemark;
	}

	public void setInactiveRemark(String inactiveRemark) {
		this.inactiveRemark = inactiveRemark;
	}
	
	
}
