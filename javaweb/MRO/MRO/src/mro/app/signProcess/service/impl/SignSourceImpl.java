package mro.app.signProcess.service.impl;

import mro.app.signProcess.Utils.SignSourceUtils;
import mro.app.signProcess.bo.SignSourceBo;
import mro.app.signProcess.form.SignSourceForm;
import mro.app.signProcess.service.SignSourceInterface;
import mro.base.bo.SignSourceCategoryBO;
import mro.base.bo.SignSourceSystemBO;
import mro.base.entity.SignSourceCategory;
import mro.base.entity.SignSourceSystem;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;

public class SignSourceImpl implements SignSourceInterface {
	private transient SignSourceSystemBO signSourceSystemBO;
	private transient SignSourceCategoryBO signSourceCategoryBO;
	
	public SignSourceImpl(){
		signSourceSystemBO=SpringContextUtil.getBean(SignSourceSystemBO.class);
		signSourceCategoryBO=SpringContextUtil.getBean(SignSourceCategoryBO.class);
	}
	
	@Override
	public void mainQuery(SignSourceForm signSourceForm, int Index) {
		signSourceForm.inital();
		signSourceForm.setListSignSourceSystem(signSourceSystemBO.getList());
		signSourceForm.setActiveIndex(Index);
	}

	@Override
	public void onSelect(SignSourceForm signSourceForm,
			SignSourceSystem signSourceSystem) {
		signSourceForm.onNew();
		signSourceForm.setSignSourceSystem(signSourceSystem);
		signSourceForm.setListSignSourceCategory(signSourceCategoryBO.getList(signSourceSystem));
		
	}

	@Override
	public void onSave(SignSourceForm signSourceForm) {
		SignSourceBo signSourceBo=SpringContextUtil.getBean(SignSourceBo.class);
		GlobalGrowl message = new GlobalGrowl();
		String errorMessage=SignSourceUtils.validate(signSourceForm);
		if(errorMessage.length()==0){
			signSourceBo.onSave(signSourceForm);
			SignSourceSystem signSourceSystem=signSourceForm.getSignSourceSystem();
			this.mainQuery(signSourceForm,0);
			this.onSelect(signSourceForm,signSourceSystem);
			message.addInfoMessage("Save", "Save successful.");
		}else{
			message.addErrorMessage("Error", errorMessage);
		}
		signSourceForm.setActiveIndex(1);
	}

	@Override
	public void addLine(SignSourceForm signSourceForm) {
		SignSourceCategory signSourceCategory=new SignSourceCategory();
		signSourceCategory.setDelted("0");
		signSourceForm.getListSignSourceCategory().add(signSourceCategory);
	}

	@Override
	public void deleteLine(SignSourceForm signSourceForm,SignSourceCategory signSourceCategory) {
			signSourceForm.getListSignSourceCategory().remove(signSourceCategory);
			signSourceForm.getDeleteSignSourceCategoryList().add(signSourceCategory);
		
	}

}
