package mro.base.loginInfo.jsf;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.LocationMapBO;
import mro.base.bo.PersonBO;
import mro.base.bo.UserLocationSiteMapBO;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.loginInfo.utils.LoginInfoUtils;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;
import com.inx.menu.jsf.bean.UserBean;

@ManagedBean(name = "LoginInfoBean")
@SessionScoped
public class LoginInfoBean implements Serializable {
	private static final long serialVersionUID = 759868361600367496L;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	private Person person;
	private LocationMap locationMap;
	private Map roleMap;
	private boolean mcRole;
	private boolean fsmcRole;
	private boolean adminRole;

	private String userId;
	private String empNo;
	private String deptNo;
	private String userName;

	private Map lOptionByGroup; //person Map<name,org>
	private Map lSiteOptionByGroup; //person Map<name,siteid>
	private Map<String,LocationSiteMap> userLSMap; //user locationsite 權限
	private List<LocationMap> userLMs; //user locationsite 權限 對應的 locationMap 

	private transient LocationMapBO locationMapBO;
	private transient PersonBO personBO;

	public LoginInfoBean() {

	}

	@PostConstruct
	public void init() {
		locationMapBO = SpringContextUtil.getBean(LocationMapBO.class);
		personBO = SpringContextUtil.getBean(PersonBO.class);
		person = personBO.getPerson(userBean.getEmpNo());
		locationMap = Utility.nvlEntity(locationMapBO
				.getLocationMapByOcode(person.getOrganizationCode()),
				LocationMap.class);
		userId = userBean.getUserId();
		empNo = person.getPersonId();
		deptNo = person.getDeptCode();
		userName = person.getDisplayName();
		roleMap = LoginInfoUtils.getRoleMap(userBean.getEmpNo());
		adminRole = LoginInfoUtils.getRoleValidate(roleMap, "Admin");
		mcRole = LoginInfoUtils.getRoleValidate(roleMap, "MC");
		fsmcRole = LoginInfoUtils.getRoleValidate(roleMap, "FSMC");
	}

	public String getPersonName(String empno) {
		if (StringUtils.isBlank(empno)) {
			return null;
		}
		Person person = personBO.getPerson(empno);
		return person != null ? person.getDisplayName() : "";
	}

	public Map getlSiteOptionByGroup() {
		if (lSiteOptionByGroup == null) {
			lSiteOptionByGroup = locationMapBO
					.getLocationMapOptionBySite(locationMapBO
							.getLocationMapList(locationMap.getLocationSiteMap()));
		}
		return lSiteOptionByGroup;
	}

	public Map getlOptionByGroup() {
		if (lOptionByGroup == null) {
			lOptionByGroup = locationMapBO
					.getLocationMapOptionByOCode(locationMapBO
							.getLocationMapList(locationMap.getLocationSiteMap()));
		}
		return lOptionByGroup;
	}

	public Map getUserLSMap() {
		if (userLSMap == null) {
			UserLocationSiteMapBO bo=SpringContextUtil.getBean(UserLocationSiteMapBO.class);
			userLSMap=new LinkedHashMap<>();
			bo.getList(empNo).forEach(l->{
				userLSMap.put(l.getLocationSiteMap().getLocationSite(), l.getLocationSiteMap());
			});
			LocationSiteMap lSite=locationMap.getLocationSiteMap();
			if(lSite!=null)userLSMap.put(lSite.getLocationSite(), lSite);
		}
		return userLSMap;
	}

	/** 取得user locationSite對應的locationMap */
	public List<LocationMap> getUserLMs() {
		if (userLMs == null) {
			SystemConfigBean bean = JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
			userLMs=new LinkedList<LocationMap>();
			this.getUserLSMap().values().forEach(l->userLMs.addAll(bean.getLocationMaps(l)));
			userLMs.sort(((f1, f2) ->f1.getOrganizationName().compareTo(f2.getOrganizationName())));
		}
		return userLMs;
	}

	/** 驗證siteid是不是在UserLSMap */
	public boolean  validateUserLSMap(String siteId){
		SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		LocationMap locationMap=bean.getLocationMap().get(siteId);
		if(locationMap!=null){
			if(userLSMap.get(locationMap.getLocationSiteMap().getLocationSite())!=null)
				return true;
		}
		return false;
	}

	// ============================================================================================

	public UserBean getUserBean() {
		return userBean;
	}

	public Person getPerson() {
		return person;
	}

	public LocationMap getLocationMap() {
		return locationMap;
	}

	public boolean isMcRole() {
		return mcRole;
	}

	public boolean isAdminRole() {
		return adminRole;
	}

	public String getUserId() {
		return userId;
	}

	public String getEmpNo() {
		return empNo;
	}

	public String getDeptNo() {
		return deptNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setLocationMap(LocationMap locationMap) {
		this.locationMap = locationMap;
	}

	public void setMcRole(boolean mcRole) {
		this.mcRole = mcRole;
	}

	public void setAdminRole(boolean adminRole) {
		this.adminRole = adminRole;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setlOptionByGroup(Map lOptionByGroup) {
		this.lOptionByGroup = lOptionByGroup;
	}

	public void setlSiteOptionByGroup(Map lSiteOptionByGroup) {
		this.lSiteOptionByGroup = lSiteOptionByGroup;
	}

	public void setUserLSMap(Map<String, LocationSiteMap> userLSMap) {
		this.userLSMap = userLSMap;
	}

	public void setUserLMs(List<LocationMap> userLMs) {
		this.userLMs = userLMs;
	}

	public boolean isFsmcRole() {
		return fsmcRole;
	}

	public void setFsmcRole(boolean fsmcRole) {
		this.fsmcRole = fsmcRole;
	}
	
}
