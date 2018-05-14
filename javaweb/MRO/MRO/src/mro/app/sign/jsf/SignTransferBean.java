package mro.app.sign.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.signTask.jsf.SignTaskBean;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.MroApplySignListBO;
import mro.base.bo.SignSourceCategoryBO;
import mro.base.entity.Person;
import mro.base.entity.view.MroApplySignList;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.base.workflow.utils.WorkflowActionUtils;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "SignTransferBean")
@ViewScoped
public class SignTransferBean implements Serializable {
	private static final long serialVersionUID = -8545518899694201786L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;
	
	private Map applyCategory; // 申請單種類
	private Map applyCategoryMap; // 申請單種類
	private String selectCategory;// 選取申請單種累
	private List<MroApplySignList> listMroApplySignList;// 待簽核清單
	private MroApplySignList[] mroApplySignList;  //選取簽核申請單
	
	private Person signListEmployeeVO;
	private Person fowardListEmployeeVO;
	private String applyNum; //申請單號
	private long employeeType;
	private String signComment;
	
	private String signHistoryUrl; //簽核歷程網址
	private transient SignSourceCategoryBO signSourceCategoryBO;
	private transient MroApplySignListBO mroApplySignListBO;

	public SignTransferBean() {
	}

	@PostConstruct
	public void init() {
		signSourceCategoryBO=SpringContextUtil.getBean(SignSourceCategoryBO.class);
		mroApplySignListBO=SpringContextUtil.getBean(MroApplySignListBO.class);
		applyCategoryMap=signSourceCategoryBO.getMap(SystemConfig.SYSTEMNAME);
		applyCategory=Utility.swapMap(applyCategoryMap);
		signListEmployeeVO=new Person();
		fowardListEmployeeVO=new Person();
		this.initial();
	}
	public void signHistory(BigDecimal taskId){
		//=============================簽核歷程=========================================================
		signHistoryUrl=WorkflowActionUtils.onSignHistory(taskId);
	}
	public void initial(){
		mroApplySignList=null;
		listMroApplySignList=new ArrayList<MroApplySignList>();
	}
	public void Search() { //搜尋待簽核申請單
		this.initial();
		GlobalGrowl message = new GlobalGrowl();
		if(StringUtils.isNotBlank(signListEmployeeVO.getPersonId()) || 
		   StringUtils.isNotBlank(applyNum)){
		SignTaskBean signTaskBean = new SignTaskBean();
		List<BigDecimal> signTaskId = signTaskBean.getWaitSignTask(
				signListEmployeeVO.getPersonId(),applyNum,SignStatus.INPRG,
				SystemConfig.SYSTEMNAME, selectCategory,false);
			if(Utility.isNotEmpty(signTaskId)){
				listMroApplySignList = mroApplySignListBO.getList(SignStatus.INPRG, signTaskId);
			}
		}else{
			message.addWarnMessage("Warn", "請先選擇待簽核主管或申請單號!");
		}
	}

	public void onTransfer(){
		GlobalGrowl message = new GlobalGrowl();
		if(StringUtils.isBlank(signListEmployeeVO.getPersonId()) && 
				   StringUtils.isBlank(applyNum)){
			message.addWarnMessage("Warn", "請先選擇待簽核主管或申請單號!");
		} else 	if(StringUtils.isBlank(fowardListEmployeeVO.getPersonId())){
			message.addWarnMessage("Warn", "請先選擇轉呈主管!!");
		} else 	if(StringUtils.isBlank(signComment)){
			message.addWarnMessage("Warn", "請填寫轉呈原因 !!");
		} else 	if(!Utility.isNotEmpty(mroApplySignList)){
			message.addWarnMessage("Warn", "請先選擇待簽核申請單 !!");
		} else 	if(signListEmployeeVO.getPersonId()!=null &&
				signListEmployeeVO.getPersonId().equals(fowardListEmployeeVO.getPersonId())){
			message.addWarnMessage("Warn", "待簽核主管 不能與轉呈主管相同，請重新選取!!");
		}else {
			for(MroApplySignList s:mroApplySignList){
				if(WorkflowActionUtils.onTransferByAdmin(
						s.getTaskId(),s.getApplyNum()
						,loginInfoBean.getEmpNo()
						, signComment, fowardListEmployeeVO.getPersonId(),message)){
					message.addInfoMessage("轉呈成功", "申請單號："+s.getApplyNum()+" 轉呈成功!");
				}
			}
			this.Search();
		}
	}
	public void setEmployee(Person person) {//選取員工
			if (employeeType == 1) {
				signListEmployeeVO =person;
			} else if (employeeType == 2) {
				fowardListEmployeeVO = person;
			}
	}

	// =========================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public Map getApplyCategory() {
		return applyCategory;
	}

	public void setApplyCategory(Map applyCategory) {
		this.applyCategory = applyCategory;
	}

	public Map getApplyCategoryMap() {
		return applyCategoryMap;
	}

	public void setApplyCategoryMap(Map applyCategoryMap) {
		this.applyCategoryMap = applyCategoryMap;
	}

	public String getSelectCategory() {
		return selectCategory;
	}

	public void setSelectCategory(String selectCategory) {
		this.selectCategory = selectCategory;
	}

	public Person getSignListEmployeeVO() {
		return signListEmployeeVO;
	}

	public void setSignListEmployeeVO(Person signListEmployeeVO) {
		this.signListEmployeeVO = signListEmployeeVO;
	}

	public Person getFowardListEmployeeVO() {
		return fowardListEmployeeVO;
	}

	public void setFowardListEmployeeVO(Person fowardListEmployeeVO) {
		this.fowardListEmployeeVO = fowardListEmployeeVO;
	}

	public String getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(String applyNum) {
		this.applyNum = applyNum;
	}

	public long getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(long employeeType) {
		this.employeeType = employeeType;
	}

	public String getSignComment() {
		return signComment;
	}

	public void setSignComment(String signComment) {
		this.signComment = signComment;
	}

	public String getSignHistoryUrl() {
		return signHistoryUrl;
	}

	public void setSignHistoryUrl(String signHistoryUrl) {
		this.signHistoryUrl = signHistoryUrl;
	}

	public List<MroApplySignList> getListMroApplySignList() {
		return listMroApplySignList;
	}

	public void setListMroApplySignList(List<MroApplySignList> listMroApplySignList) {
		this.listMroApplySignList = listMroApplySignList;
	}

	public MroApplySignList[] getMroApplySignList() {
		return mroApplySignList;
	}

	public void setMroApplySignList(MroApplySignList[] mroApplySignList) {
		this.mroApplySignList = mroApplySignList;
	}

}
