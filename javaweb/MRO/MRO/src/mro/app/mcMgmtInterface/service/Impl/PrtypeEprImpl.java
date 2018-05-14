package mro.app.mcMgmtInterface.service.Impl;

import mro.app.mcMgmtInterface.form.PrtypeEprForm;
import mro.app.mcMgmtInterface.service.PrtypeEprInterface;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.PrType;
import mro.base.bo.PrtypeEprBO;
import mro.base.entity.Person;
import mro.base.entity.PrtypeEpr;
import mro.base.loginInfo.jsf.LoginInfoBean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.SpringContextUtil;

public class PrtypeEprImpl implements PrtypeEprInterface {

	private  PrtypeEprBO bo = SpringContextUtil.getBean(PrtypeEprBO.class);

	@Override
	public void setParameter(PrtypeEprForm form) {
		form.setPrtypeOption(PrType.getTransPrTypeOption());
		form.setEprRequestedby2TypeO(ParameterType.EPR_REQUESTEDBY2.getOption());
		LoginInfoBean bean=JsfContextUtil.getBean(LoginInfoBean.class.getSimpleName());
		form.setLocationSites(bean.getUserLSMap().values());
		
	}
	@Override
	public  void onSelect(PrtypeEprForm form) {
		String locationSite=form.getPrtypeEpr().getLocationSite();
		String prtype=form.getPrtypeEpr().getPrtype();
		PrtypeEpr prtypeEpr=form.getList().stream()
		.filter(l->l.getLocationSite().equals(locationSite) && l.getPrtype().equals(prtype))
		.findAny().orElse(new PrtypeEpr(prtype,locationSite));
		BeanUtils.copyProperties(prtypeEpr, form.getPrtypeEpr());
		
	}
	@Override
	public void onDelete(PrtypeEprForm form,PrtypeEpr prtypeEpr,GlobalGrowl msg){
		bo.delete(prtypeEpr);
		this.query(form);
		if(msg!=null)  msg.addInfoMessage("Delete", "Delete successful.");
	}

	@Override
	public  void onSave(PrtypeEprForm form,Person person,GlobalGrowl msg) {
		if(!this.validation(form.getPrtypeEpr(), msg)) return; //驗證錯誤就不往下走
		bo.update(form.getPrtypeEpr(),person);
		this.query(form);
		if(msg!=null)  msg.addInfoMessage("Save", "Save successful.");
	}
	
	@Override
	public  void query(PrtypeEprForm form) {
		form.inital(); //清除報表跟欄位
		form.setList(bo.getList(form.getLocationSites()));
		
	}
	@Override
	public boolean validation(PrtypeEpr prtypeEpr, GlobalGrowl msg) {
		StringBuffer message=new StringBuffer();
		if(StringUtils.isBlank(prtypeEpr.getLocationSite())){
			message.append("LOCATION_SITE 必須選取!<BR/>");
		}
		if(StringUtils.isBlank(prtypeEpr.getPrtype())){
			message.append("PRTYPE 必須選取!<BR/>");
		}
		if(StringUtils.isBlank(prtypeEpr.getEprRequestedby2Type())){
			message.append("拋轉請購人TYPE: 必須選取!<BR/>");
		}
		if(message.length()>0){
			if(msg!=null) msg.addErrorMessage("Error!", message.toString());
			return false;
		}
		return true;
	}
}
