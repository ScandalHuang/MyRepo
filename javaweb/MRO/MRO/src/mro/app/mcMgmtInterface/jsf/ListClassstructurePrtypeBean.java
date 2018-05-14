package mro.app.mcMgmtInterface.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.mcMgmtInterface.bo.ListClassstructurePrtypeBO;
import mro.app.mcMgmtInterface.utils.ListClassstructurePrtypeUtils;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.entity.ClassstructurePrtype;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ListClassstructurePrtypeBean")
@ViewScoped
public class ListClassstructurePrtypeBean implements Serializable {
	
	private static final long serialVersionUID = 1917736500613972634L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private List<ClassstructurePrtype> listClassstructurePrtype;
	private Map classstructureOption;
	private Map prtypeOption;
	private Map itemCategoryOption;
	private String selectitemCategory;
	private String selectPrtype;
	private String selectClassstructureid;
	private boolean editButton = false; // 編輯

	public ListClassstructurePrtypeBean() {

	}

	@PostConstruct
	public void init() {
		inital();SystemConfigBean bean=JsfContextUtil.getBean(SystemConfigBean.class.getSimpleName());
		if(loginInfoBean.isMcRole() || loginInfoBean.isAdminRole()){ //物管管理介面
			prtypeOption=PrType.getALLPrTypeOption();
			editButton=true;
		}else{
			prtypeOption=PrType.getApplyPrTypeOption();
		}
		itemCategoryOption=bean.getParameterOption().get(ParameterType.ITEM_CATEGORY);
	}

	// =========================================inital===============================================================
	public void inital() { // 開立新單
	}

	public void query() {
		ListClassstructurePrtypeBO listClassstructurePrtypeBO = SpringContextUtil
				.getBean(ListClassstructurePrtypeBO.class);
		if (StringUtils.isBlank(selectPrtype)) {
			listClassstructurePrtype = new ArrayList<>();
			classstructureOption = null;
		} else {
			listClassstructurePrtype = listClassstructurePrtypeBO
					.getClassstructurePrtypeList(new String[] { "cp.prtype",
							"cp.classstructureid" }, selectPrtype,
							selectitemCategory);
			classstructureOption = listClassstructurePrtypeBO
					.getClassstructure(selectPrtype, selectitemCategory);
		}
	}

	public void updatePr(String action) {
		GlobalGrowl message = new GlobalGrowl();
		ListClassstructurePrtypeBO listClassstructurePrtypeBO = SpringContextUtil
				.getBean(ListClassstructurePrtypeBO.class);
		if (ListClassstructurePrtypeUtils.validate(selectPrtype,
				selectClassstructureid, message)) {
			if (action.equals("Delete")) {
				listClassstructurePrtypeBO.deleteClassstructurePrtypeList(
						new String[] { "prtype", "classstructureid" },
						selectPrtype, selectClassstructureid);
			} else if (action.equals("Add")) {
				listClassstructurePrtypeBO.updateClassstructurePrtype(
						selectPrtype, selectClassstructureid,
						loginInfoBean.getEmpNo());
			}
			message.addInfoMessage(action, action + " successful!");
			this.query();
		}
	}

	// ===================================================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public List<ClassstructurePrtype> getListClassstructurePrtype() {
		return listClassstructurePrtype;
	}

	public void setListClassstructurePrtype(
			List<ClassstructurePrtype> listClassstructurePrtype) {
		this.listClassstructurePrtype = listClassstructurePrtype;
	}

	public Map getClassstructureOption() {
		return classstructureOption;
	}

	public void setClassstructureOption(Map classstructureOption) {
		this.classstructureOption = classstructureOption;
	}

	public String getSelectPrtype() {
		return selectPrtype;
	}

	public void setSelectPrtype(String selectPrtype) {
		this.selectPrtype = selectPrtype;
	}

	public String getSelectClassstructureid() {
		return selectClassstructureid;
	}

	public void setSelectClassstructureid(String selectClassstructureid) {
		this.selectClassstructureid = selectClassstructureid;
	}

	public Map getPrtypeOption() {
		return prtypeOption;
	}

	public void setPrtypeOption(Map prtypeOption) {
		this.prtypeOption = prtypeOption;
	}

	public String getSelectitemCategory() {
		return selectitemCategory;
	}

	public void setSelectitemCategory(String selectitemCategory) {
		this.selectitemCategory = selectitemCategory;
	}

	public boolean isEditButton() {
		return editButton;
	}

	public void setEditButton(boolean editButton) {
		this.editButton = editButton;
	}

	public Map getItemCategoryOption() {
		return itemCategoryOption;
	}

	public void setItemCategoryOption(Map itemCategoryOption) {
		this.itemCategoryOption = itemCategoryOption;
	}

}
