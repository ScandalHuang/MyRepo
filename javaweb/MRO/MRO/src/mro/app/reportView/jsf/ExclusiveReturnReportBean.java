package mro.app.reportView.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.ExclusiveReturnReportBo;
import mro.app.reportView.form.ExclusiveReturnReportForm;
import mro.app.reportView.utils.ExclusiveReturnReportUtils;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "ExclusiveReturnReportBean")
@ViewScoped
public class ExclusiveReturnReportBean implements Serializable {
	private static final long serialVersionUID = -6112916887378313851L;
	
	private ExclusiveReturnReportForm exclusiveReturnReportForm;
	private transient ExclusiveReturnReportBo exclusiveReturnReportBo;
	
	
	public ExclusiveReturnReportBean() {

	}

	@PostConstruct
	public void init() {
		exclusiveReturnReportBo=SpringContextUtil.getBean(ExclusiveReturnReportBo.class);
		exclusiveReturnReportForm =new ExclusiveReturnReportForm();
	}
	
	public void search() {
		if(ExclusiveReturnReportUtils.validateSearch(exclusiveReturnReportForm)){
			exclusiveReturnReportForm.getList().clear();
			exclusiveReturnReportForm.setList(exclusiveReturnReportBo.getExclusiveReturnList(
					exclusiveReturnReportForm));
		}
	}
	public void searchMD() { //部級繳庫率
		if(ExclusiveReturnReportUtils.validateMDSearch(exclusiveReturnReportForm)){
			exclusiveReturnReportForm.getList2().clear();
			exclusiveReturnReportForm.setList2(exclusiveReturnReportBo.getExclusiveReturnMDList(
					exclusiveReturnReportForm));
		}
	}
	// ==========================================================================================

	public ExclusiveReturnReportForm getExclusiveReturnReportForm() {
		return exclusiveReturnReportForm;
	}

	public void setExclusiveReturnReportForm(
			ExclusiveReturnReportForm exclusiveReturnReportForm) {
		this.exclusiveReturnReportForm = exclusiveReturnReportForm;
	}
}
