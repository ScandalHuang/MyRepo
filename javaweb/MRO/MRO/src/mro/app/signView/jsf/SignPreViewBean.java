package mro.app.signView.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mro.app.signTask.utils.SignTaskUtils;
import mro.app.signView.bo.SignPreViewBo;
import mro.base.entity.view.MroSignTaskListV;
import mro.base.workflow.utils.WorkflowActionUtils;

import org.apache.commons.lang3.ObjectUtils;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "SignPreViewBean")
@ViewScoped
public class SignPreViewBean implements Serializable {
	private static final long serialVersionUID = 2941430551319199705L;
	private List<MroSignTaskListV> signHistoryVOList;
	private String msg=null;
	
	public SignPreViewBean() {

	}

	@SuppressWarnings("rawtypes")
	@PostConstruct
	public void init()  {
		SignPreViewBo signPreViewBo=SpringContextUtil.getBean(SignPreViewBo.class);
		FacesContext context = FacesContext.getCurrentInstance();
		Map reqMap = context.getExternalContext().getRequestParameterMap();
		Map paramMap=SignTaskUtils.getSignParameter(ObjectUtils.toString(reqMap.get("param")));
		
		int processId = Integer.parseInt(ObjectUtils.toString(paramMap.get("processId"),"0"));
		String empno = ObjectUtils.toString(paramMap.get("empno")); 
		int price =  (int) Double.parseDouble(ObjectUtils.toString(paramMap.get("price"),"0"));
		String comment=ObjectUtils.toString(paramMap.get("comment")); //送審者簽核意見
		String[] counterEmp = ObjectUtils.toString(paramMap.get("counterEmp")).split(","); 
		msg=SignTaskUtils.vaildateSubmit(empno,processId);
		msg=msg+ObjectUtils.toString(paramMap.get("warnMessage")).replace("<br />", "");  //額外錯誤訊息
		if(msg.length()==0){
			signHistoryVOList=signPreViewBo.onSignPreView(processId, empno,comment, price, counterEmp,paramMap);
			//======================簽核清單check=============================================
			msg=SignTaskUtils.vaildateSignList(signHistoryVOList);
			if(msg.length()>0){
				signHistoryVOList=null;
			}
			//=====額外會簽(規格異動)=========================
//			signHistoryVOList=signPreViewBo.addExtraSigner(signHistoryVOList, extraEmp, extraDescription);
		}
	}
	
	
	// ===================================================================================================
	
	public List<MroSignTaskListV> getSignHistoryVOList() {
		return signHistoryVOList;
	}

	public void setSignHistoryVOList(List<MroSignTaskListV> signHistoryVOList) {
		this.signHistoryVOList = signHistoryVOList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	
}
