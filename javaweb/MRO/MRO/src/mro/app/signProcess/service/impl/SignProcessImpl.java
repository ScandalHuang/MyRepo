package mro.app.signProcess.service.impl;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import mro.app.signProcess.Utils.SignProcessUtils;
import mro.app.signProcess.bo.SignProcessAppBo;
import mro.app.signProcess.form.SignProcessForm;
import mro.app.signProcess.service.SignProcessInterface;
import mro.app.signTask.service.ActorInterface;
import mro.app.signTask.service.ValidateInterface;
import mro.base.bo.HrEmpBO;
import mro.base.bo.SignProcessBO;
import mro.base.bo.SignProcessListBO;
import mro.base.bo.SignSourceCategoryBO;
import mro.base.entity.HrEmp;
import mro.base.entity.SignProcess;
import mro.base.entity.SignProcessList;
import mro.base.parameter.bo.SignParameterBO;

import org.apache.commons.collections.map.HashedMap;
import org.reflections.Reflections;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class SignProcessImpl implements SignProcessInterface {
	private transient HrEmpBO hrEmpBO;
	private transient SignProcessBO signProcessBO;
	private transient SignProcessListBO signProcessListBO;
	
	public SignProcessImpl(){
		hrEmpBO=SpringContextUtil.getBean(HrEmpBO.class);
		signProcessBO=SpringContextUtil.getBean(SignProcessBO.class);
		signProcessListBO=SpringContextUtil.getBean(SignProcessListBO.class);
	}
	@Override
	public SignProcessForm setMethodMap(SignProcessForm signProcessForm) {
		signProcessForm.setMethodMap(new HashedMap());
		//===============================驗證節點=======================================
		signProcessForm.getMethodMap().put("VALIDATE", getMethodMap(ValidateInterface.class));
		//===============================系統來源=======================================
		signProcessForm.getMethodMap().put("FROM_SYSTEM", getMethodMap(ActorInterface.class));
		
		
		return signProcessForm;
	}

	@Override
	public <T> Map getMethodMap(Class clazz) {
		Map methodMap=new LinkedHashMap<>();
		Reflections reflections = new Reflections("mro.app");   
		Set<Class<? extends T>> clazzs=reflections.getSubTypesOf(clazz);
		for(Class c:clazzs){
			methodMap.put(c.getSimpleName(),c.getName());
		}
		return methodMap;
	}

	@Override
	public SignProcessForm setParameter(SignProcessForm signProcessForm) {
		SignParameterBO signParameterBO=SpringContextUtil.getBean(SignParameterBO.class);
		signProcessForm.setSignLevel(signParameterBO.getParameterMenu("SIGN_LEVEL"));
		signProcessForm.setSignCategory(signParameterBO.getParameterMenu("SIGN_CATEGORY"));
		signProcessForm.setSignSourseSystem(signParameterBO.getSignSourceSystemList());
		signProcessForm=setMethodMap(signProcessForm);
		return signProcessForm;
	}

	@Override
	public SignProcessForm onSystemChange(SignProcessForm signProcessForm) {
		SignSourceCategoryBO signSourceCategoryBO=SpringContextUtil.getBean(SignSourceCategoryBO.class);
		if(signProcessForm.getSignProcess()!=null){
			signProcessForm.setSignSourseCategory(Utility.swapMap(
					signSourceCategoryBO.getMap(signProcessForm.getSignProcess().getSourceSystem())));
		}
		return signProcessForm;
	}

	@Override
	public SignProcessForm setEmployee(SignProcessForm signProcessForm,
			HrEmp hremp) {

		if(hremp!=null){
			for(SignProcessList s:signProcessForm.getSignProcessList()){
				if(s.getSignSequence().intValue()==signProcessForm.getSignSequence()){
					s.setSignEmpNo(hremp.getEmpNo());
					s.setSignDeptNo(hremp.getDeptNo());
				}
			}
		}
		return signProcessForm;
	}

	@Override
	public SignProcessForm selectSignProcess(SignProcessForm signProcessForm,
			SignProcess signProcess) {
		SignProcessAppBo signProcessBo=SpringContextUtil.getBean(SignProcessAppBo.class);
		signProcessForm.setSignProcess(signProcess);
		signProcessForm.setCreateName(hrEmpBO.getHrEmp(signProcessForm.getSignProcess().getCreateBy()).getName());
		signProcessForm.setChangeName(hrEmpBO.getHrEmp(signProcessForm.getSignProcess().getChangeBy()).getName());
		signProcessForm.setSignProcessList(signProcessListBO.getList(signProcessForm.getSignProcess().getProcessId()));
		this.onSystemChange(signProcessForm);
		signProcessForm.setActiveIndex(1);
		this.setSignSequenceMap(signProcessForm);
		return signProcessForm;
	}

	@Override
	public SignProcessForm deleteSignProcessList(SignProcessForm signProcessForm) {
		for(SignProcessList s:signProcessForm.getsDeleteSignProcessList()){
			signProcessForm.getSignProcessList().remove(s);
			signProcessForm.getDeleteSignProcessList().add(s);
		}
		int i=1;
		for(SignProcessList s:signProcessForm.getSignProcessList()){
			s.setSignSequence(new BigDecimal(i));
			i++;
		}
		signProcessForm.setsDeleteSignProcessList(null);
		this.setSignSequenceMap(signProcessForm);
		return signProcessForm;
	}

	@Override
	public SignProcessForm addSignProcessList(SignProcessForm signProcessForm) {
		BigDecimal newSequence=new BigDecimal(signProcessForm.getSignProcessList().size()+1);
		SignProcessList temp_signProcessList=new SignProcessList();
		temp_signProcessList.setSignSequence(newSequence);
		temp_signProcessList.setSignPrice(new BigDecimal(0));
		signProcessForm.getSignProcessList().add(temp_signProcessList);
		this.setSignSequenceMap(signProcessForm);
		return signProcessForm;
	}

	@Override
	public SignProcessForm onChangeSequence(SignProcessForm signProcessForm,SignProcessList s,String type) {
		int ori_index=signProcessForm.getSignProcessList().indexOf(s);
		int new_index = ori_index-1;
		if(type.equals("DOWN")){new_index=ori_index+1;}
		
		SignProcessList newSignProcessList=signProcessForm.getSignProcessList().get(new_index);
		//============================對調=====================================
		BigDecimal index=newSignProcessList.getSignSequence();
		newSignProcessList.setSignSequence(s.getSignSequence());
		s.setSignSequence(index);
		//============================存放入list==================================
		signProcessForm.getSignProcessList().set(new_index, s);
		signProcessForm.getSignProcessList().set(ori_index, newSignProcessList);
		return signProcessForm;
	}

	@Override
	public void onSave(SignProcessForm signProcessForm,String empno) {
		GlobalGrowl message = new GlobalGrowl();
		if(SignProcessUtils.validate(signProcessForm, message)){
			SignProcessAppBo signProcessBo=SpringContextUtil.getBean(SignProcessAppBo.class);
			signProcessBo.onSave(signProcessForm, empno);
			SignProcess signProcess=signProcessForm.getSignProcess();
			this.mainQuery(signProcessForm,0);
			
			this.selectSignProcess(signProcessForm,signProcess);
			message.addInfoMessage("Save", "Save successful.");
		}
		signProcessForm.setActiveIndex(1);
	}

	@Override
	public SignProcessForm mainQuery(SignProcessForm signProcessForm, int Index) {
		signProcessForm.inital();
		signProcessForm.setSignProcessQuery(signProcessBO.getList(false));
		signProcessForm.setActiveIndex(Index);
		return signProcessForm;
	}

	@Override
	public void setSignSequenceMap(SignProcessForm signProcessForm) {
		signProcessForm.initalSignSequenceMap();
		for(SignProcessList s:signProcessForm.getSignProcessList()){
			signProcessForm.getSignSequenceMap().put(s.getSignSequence(), s.getSignSequence());
		}
		
	}

}
