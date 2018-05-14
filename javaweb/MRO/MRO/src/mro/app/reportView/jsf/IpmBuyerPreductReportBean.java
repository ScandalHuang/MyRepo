package mro.app.reportView.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.reportView.bo.IpmBuyerPreductReportBo;
import mro.app.reportView.form.IpmBuyerPreductReportForm;

import com.inx.commons.util.SpringContextUtil;

@ManagedBean(name = "IpmBuyerPreductReportBean")
@ViewScoped
public class IpmBuyerPreductReportBean implements Serializable {
	private static final long serialVersionUID = -4124485488108831629L;
	private IpmBuyerPreductReportForm form;
	private transient IpmBuyerPreductReportBo ipmBuyerPreductReportBo;
	
	
	public IpmBuyerPreductReportBean() {

	}

	@PostConstruct
	public void init() {
		ipmBuyerPreductReportBo=SpringContextUtil.getBean(IpmBuyerPreductReportBo.class);
		form =new IpmBuyerPreductReportForm();
		form.setParameter();
	}
	
	public void search() {
		form.getList().clear();
		form.setList(ipmBuyerPreductReportBo.getList(form));
	}
	// ==========================================================================================

	public IpmBuyerPreductReportForm getForm() {
		return form;
	}

	public void setForm(IpmBuyerPreductReportForm form) {
		this.form = form;
	}

	public IpmBuyerPreductReportBo getIpmBuyerPreductReportBo() {
		return ipmBuyerPreductReportBo;
	}

	public void setIpmBuyerPreductReportBo(
			IpmBuyerPreductReportBo ipmBuyerPreductReportBo) {
		this.ipmBuyerPreductReportBo = ipmBuyerPreductReportBo;
	}

}
