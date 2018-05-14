package mro.app.bbs.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.bbs.form.BbsForm;
import mro.app.commonview.services.FileUploadInterfaces;
import mro.app.commonview.services.Impl.FileUploadImpl;
import mro.app.util.HrEmpUtils;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.bo.BulletinboardBO;
import mro.base.entity.Bulletinboard;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.primefaces.event.SelectEvent;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "BbsShowBean")
@ViewScoped
public class BbsShowBean implements Serializable {
	
	private static final long serialVersionUID = 7854890416745465425L;

	@ManagedProperty(value = "#{SystemConfigBean}")
	private SystemConfigBean systemConfigBean;
	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private BbsForm bbsForm;

	private transient BulletinboardBO bulletinboardBO;

	public BbsShowBean() {

	}

	@PostConstruct
	public void init() {
		bulletinboardBO = SpringContextUtil.getBean(BulletinboardBO.class);
		bbsForm = new BbsForm();
		bbsForm.setTypeList(new ArrayList<String>(systemConfigBean.getParameterOption().get(
				ParameterType.BSS_TYPE).values()) );
		this.onSearch();
	}

	public void onSearch() {
		bbsForm.bbsIntial();
		List<Bulletinboard> list=bulletinboardBO.getBulletinboard(
				true,new ArrayList(loginInfoBean.getUserLSMap().values()));
		Map<String,ArrayList<Bulletinboard>> map=new LinkedHashMap<>();
		List<String> actors = new ArrayList<String>();
		for(Bulletinboard b:list){
			if(map.get(b.getType())==null){
				map.put(b.getType(), new ArrayList<Bulletinboard>());
			}
			map.get(b.getType()).add(b);
			//===========更新日期小於7天的要顯示NEW圖片===============
			if((new Date().getTime()-b.getPostdate().getTime())<=7*24*3600*1000){
				bbsForm.getBbsTimeMap().put(b.getBulletinboardid(), true);
			}
			//===============postby=================================
			actors.add(b.getPostby());
		}
		bbsForm.setBbsMapList(map);
		bbsForm.setPostNameMap(HrEmpUtils.getMapList(actors));
	}

	public void onRowSelect(SelectEvent event) {
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		bbsForm.setBulletinboard((Bulletinboard) event.getObject());
		bbsForm.setListAttachment(fileUploadInterfaces.getAttachmentList(
				bbsForm.getBulletinboard().getBulletinboardid().toString(),
				FileCategory.BBS_ATTACHMENT));
	}

	// ============================================================================================

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

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

}
