package mro.app.commonview.jsf;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.mcMgmtInterface.form.MroOrgFacilityEqForm;
import mro.base.bo.MroOrgFacilityEqBO;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.MroOrgFacilityEq;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name="ListOrgFacilityEqCommonBean")
@ViewScoped
public class ListOrgFacilityEqCommonBean implements Serializable{ 
	private static final long serialVersionUID = -3448380123275395257L;

	private MroOrgFacilityEqForm mroOrgFacilityEqForm;
	
    private Object object;
    private String updateView;
		
	public ListOrgFacilityEqCommonBean(){
		
	}
	
	@PostConstruct
	public void init() {
		mroOrgFacilityEqForm=new MroOrgFacilityEqForm();
	}
	public void setProperty(LocationSiteMap locationSiteMap,String reason,String depetNo){
		mroOrgFacilityEqForm.setsLocationSiteMap(locationSiteMap);
		mroOrgFacilityEqForm.setSelectReason(reason);
		mroOrgFacilityEqForm.setSelectDeptNo(depetNo);
	}
	public void onSearch(){
		MroOrgFacilityEqBO bo = SpringContextUtil.getBean(MroOrgFacilityEqBO.class);
		mroOrgFacilityEqForm.intial();
		mroOrgFacilityEqForm.setListMroOrgFacilityEq(bo.getList(mroOrgFacilityEqForm));
		mroOrgFacilityEqForm.copyList();
		
	}
	
	
	public void save(MroOrgFacilityEq mroOrgFacilityEq) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, InvocationTargetException{
			//=========================setClassstructureuid=======================================
			Method method=object.getClass().getMethod("setMroOrgFacilityEq", 
					mroOrgFacilityEq.getClass());
			method.invoke(object, mroOrgFacilityEq);
			//=========================更新view=======================================
			if(StringUtils.isNotBlank(updateView)){ 
				RequestContext context = RequestContext.getCurrentInstance();  
				context.update(updateView);
			}
	}
	//=================================================================================================

	public MroOrgFacilityEqForm getMroOrgFacilityEqForm() {
		return mroOrgFacilityEqForm;
	}

	public void setMroOrgFacilityEqForm(MroOrgFacilityEqForm mroOrgFacilityEqForm) {
		this.mroOrgFacilityEqForm = mroOrgFacilityEqForm;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getUpdateView() {
		return updateView;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}

}
