package mro.app.bbs.jsf;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.bbs.bo.BbsBo;
import mro.app.bbs.form.BbsForm;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.AttachmentBO;
import mro.base.bo.BulletinboardBO;
import mro.base.bo.BulletinboardSiteBO;
import mro.base.entity.Bulletinboard;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.SelectEvent;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "BbsBean")
@ViewScoped
public class BbsBean implements Serializable {

	private static final long serialVersionUID = -8803416944110841782L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	@ManagedProperty(value = "#{SystemConfigBean}")
	private SystemConfigBean systemConfigBean;

	private BbsForm bbsForm;

	private transient BulletinboardBO bulletinboardBO;
	private transient BulletinboardSiteBO bulletinboardSiteBO;
	private transient BbsBo bbsBo;

	public BbsBean() {

	}

	@PostConstruct
	public void init() {
		bulletinboardBO = SpringContextUtil.getBean(BulletinboardBO.class);
		bulletinboardSiteBO = SpringContextUtil.getBean(BulletinboardSiteBO.class);
		bbsBo = SpringContextUtil.getBean(BbsBo.class);
		bbsForm = new BbsForm();
		bbsForm.setLoginPerson(loginInfoBean.getPerson());
		bbsForm.setTypeOption(systemConfigBean.getParameterOption().get(
				ParameterType.BSS_TYPE));
		bbsForm.setTypeList(new ArrayList<String>(bbsForm.getTypeOption().keySet()));
		this.onSearch();
	}

	public void onNew() {
		bbsForm.onNew(loginInfoBean.getLocationMap().getLocationSiteMap());
	}

	public void onSearch() {
		bbsForm.bbsIntial();
		bbsForm.setBbsList(bulletinboardBO.getBulletinboard(
				loginInfoBean.isAdminRole()?null:loginInfoBean.getEmpNo()));
		bbsForm.setFilterbbsList(bbsForm.getBbsList());
	}

	public void update() {
		if (this.onValidate()) {
			GlobalGrowl msg = new GlobalGrowl();
			bbsBo.update(bbsForm);
			this.onSearch();
			msg.addInfoMessage("Update", "Update successful.");
		}
	}

	public void delete() {
		GlobalGrowl msg = new GlobalGrowl();
		bbsBo.delete(bbsForm);
		this.onSearch();
		msg.addInfoMessage("Delete", "Delete successful.");
	}

	public void onRowSelect(SelectEvent event) {
		bbsForm.setBulletinboard((Bulletinboard) event.getObject());
		bbsForm.setBulletinboardSites(bulletinboardSiteBO.getList(bbsForm.getBulletinboard()));
		bbsForm.setSLocationSiteMap(bbsForm.getBulletinboardSites()); //更新公怖欄對應的site選項
		this.getDownLoadFile();
	}

	public boolean onValidate() {
		GlobalGrowl msg = new GlobalGrowl();
		StringBuffer warn = new StringBuffer();
		if (StringUtils.isBlank(bbsForm.getBulletinboard().getSubject())) {
			warn.append("主題必須填寫!<br/>");
		}
		if (bbsForm.getBulletinboard().getExpiredate() == null) {
			warn.append("到期日必須填寫!<br/>");
		}
		if (loginInfoBean.getUserLSMap().values().size()!=systemConfigBean.getlSiteOption().values().size()&&
				bbsForm.getsLocationSiteMap().length==0) {
			warn.append("您的權限必須選取發佈廠區!<br/>");
		}
		if (warn.length() > 0) {
			msg.addErrorMessage("Error", warn.toString());
			return false;
		}
		return true;
	}

	public void getDownLoadFile() { // 取得下載檔案
		AttachmentBO attachmentBO = SpringContextUtil.getBean(AttachmentBO.class);
		bbsForm.getDowloadFileMap().putAll(attachmentBO.getMap(
			bbsForm.getBulletinboard().getBulletinboardid().toString(), 
			FileCategory.BBS_ATTACHMENT, true));
	}

	// ===============================================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public BbsForm getBbsForm() {
		return bbsForm;
	}

	public void setBbsForm(BbsForm bbsForm) {
		this.bbsForm = bbsForm;
	}

	public SystemConfigBean getSystemConfigBean() {
		return systemConfigBean;
	}

	public void setSystemConfigBean(SystemConfigBean systemConfigBean) {
		this.systemConfigBean = systemConfigBean;
	}

}
