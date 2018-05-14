package mro.app.mcMgmtInterface.service.Impl;

import mro.app.mcMgmtInterface.form.PrControlConfigForm;
import mro.app.mcMgmtInterface.service.PrControlConfigInterface;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.ParameterType;
import mro.base.bo.PrControlConfigBO;
import mro.base.entity.Person;
import mro.base.entity.PrControlConfig;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.hibernate.ActiveFlag;

public class PrControlConfigImpl implements PrControlConfigInterface{

	private  PrControlConfigBO bo = SpringContextUtil.getBean(PrControlConfigBO.class);

	@Override
	public void setParameter(PrControlConfigForm form) {
		form.setGroupOption(ParameterType.ITEM_CATEGORY.getOption());
		form.setControlOption(ParameterType.CONTROL_TYPE.getOption());
		LoginInfoBean bean=JsfContextUtil.getBean(LoginInfoBean.class.getSimpleName());
		form.setLocationSites(bean.getUserLSMap().values());
	}
	@Override
	public  void onSelect(PrControlConfigForm form) {
		String locationSite=form.getPrControlConfig().getLocationSite();
		String commoditygroup=form.getPrControlConfig().getCommoditygroup();
		PrControlConfig prControlConfig=form.getList().stream()
		.filter(l->l.getLocationSite().equals(locationSite) && l.getCommoditygroup().equals(commoditygroup))
		.findAny().orElse(new PrControlConfig(commoditygroup,locationSite));
		BeanUtils.copyProperties(prControlConfig, form.getPrControlConfig());
		
	}
	@Override
	public void onDelete(PrControlConfigForm form,PrControlConfig prControlConfig,GlobalGrowl msg){
		bo.delete(prControlConfig);
		this.query(form);
		if(msg!=null)  msg.addInfoMessage("Delete", "Delete successful.");
	}

	@Override
	public  void onSave(PrControlConfigForm form,Person person,GlobalGrowl msg) {
		if(!this.validation(form.getPrControlConfig(), msg)) return; //驗證錯誤就不往下走
		bo.update(form.getPrControlConfig(),person);
		this.query(form);
		if(msg!=null)  msg.addInfoMessage("Save", "Save successful.");
	}
	
	@Override
	public  void query(PrControlConfigForm form) {
		form.inital(); //清除報表跟欄位
		form.setList(bo.getList(form.getLocationSites()));
		
	}
	@Override
	public boolean validation(PrControlConfig prControlConfig, GlobalGrowl msg) {
		StringBuffer message=new StringBuffer();
		if(StringUtils.isBlank(prControlConfig.getLocationSite())){
			message.append("LOCATION_SITE 必須選取!<BR/>");
		}
		if(StringUtils.isBlank(prControlConfig.getCommoditygroup())){
			message.append("大分類必須選取!<BR/>");
		}
		if(StringUtils.isBlank(prControlConfig.getSstockFlag())){
			message.append("SSOTCK多廠區修改: 必須選取!<BR/>");
		}else if(prControlConfig.getCommoditygroup().equals(ItemType.R1.name()) &&
				prControlConfig.getSstockFlag().equals(ActiveFlag.Y.name())){
			message.append("SSOTCK多廠區修改目前只開放R2!<BR/>");
		}
		if(message.length()>0){
			if(msg!=null) msg.addErrorMessage("Error!", message.toString());
			return false;
		}
		return true;
	}
}
