package mro.app.signView.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mro.base.bo.MroSignTaskListVBO;
import mro.base.entity.view.MroSignTaskListV;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "SignHistoryBean")
@ViewScoped
public class SignHistoryBean implements Serializable {
	private static final long serialVersionUID = -8072525319121576268L;
	private List<MroSignTaskListV> signHistoryList;
	private List<MroSignTaskListV> signToDoList;	
	private transient MroSignTaskListVBO mroSignTaskListVBO;

	public SignHistoryBean() {

	}

	@PostConstruct
	public void init() {
		mroSignTaskListVBO=SpringContextUtil.getBean(MroSignTaskListVBO.class);
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext()
				.getRequestParameterMap();

		String taskId = paramMap.get("ID"); // REPORT ID

		if (StringUtils.isNotBlank(taskId)) {
			signHistoryList =  mroSignTaskListVBO.getListByTaskId(new BigDecimal(taskId),null);
			signToDoList =  mroSignTaskListVBO.getListBySource(signHistoryList, "Y");
		}
	}
	// ===================================================================================================


	public List<MroSignTaskListV> getSignHistoryList() {
		return signHistoryList;
	}

	public void setSignHistoryList(List<MroSignTaskListV> signHistoryList) {
		this.signHistoryList = signHistoryList;
	}

	public List<MroSignTaskListV> getSignToDoList() {
		return signToDoList;
	}

	public void setSignToDoList(List<MroSignTaskListV> signToDoList) {
		this.signToDoList = signToDoList;
	}

}
