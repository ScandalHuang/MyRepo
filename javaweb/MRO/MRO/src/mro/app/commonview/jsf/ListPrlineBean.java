package mro.app.commonview.jsf;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.applyItem.Utils.ApplyPrUtils;
import mro.app.commonview.services.Impl.PrlineImpl;
import mro.base.entity.Item;
import mro.base.entity.MroOrgFacilityEq;
import mro.base.entity.Prline;
import mro.base.entity.VwNewvendorcodeEpmall;
import mro.form.PrForm;
import mro.form.PrLineForm;
import mro.viewForm.PrLineView;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "ListPrlineBean")
@ViewScoped
public class ListPrlineBean{
	private PrLineForm prLineForm;
	private PrLineView prLineView;  //prline view
	private PrlineImpl prlineImpl;
    
	public ListPrlineBean() {
		this.init();
	}

	@PostConstruct
	public void init() {
		prlineImpl=new PrlineImpl();
		prLineForm = new PrLineForm();
		prLineView=new PrLineView();
	}
	
	private void setPrlineView(){
		prLineView.setView(prLineForm.getPrForm(), prLineForm.getPrline());  //
	}
	
	/** 新增 PRLINE 料號 */
	public void addPrLine(long maxPrlineNum, PrForm prForm) {  //
		this.init();
		prLineForm.addPrLine(maxPrlineNum, prForm);
		this.setPrlineView();
	}
	/** 更新 & VIEW PRLINE 料號 */
	public void onPrLine(Prline p, boolean e, PrForm prForm) {  //
		this.init();
		prLineForm.onPrLine(p, e, prForm);
		this.setPrlineView();
	}
	//總金額&控管資訊
	public void newminlevel(){
		prlineImpl.newminlevel(prLineForm.getPrForm().getPr(), prLineForm.getPrline());
	}
	
	public void save() {   // 儲存
		GlobalGrowl message = new GlobalGrowl();
		StringBuffer warnMessage = new StringBuffer();
		warnMessage = ApplyPrUtils.ListPrlineValidate(prLineForm.getPrline(), warnMessage);
		// end of added
		if (warnMessage.length() == 0) { 
			this.newminlevel();//新建議月用量 & 預估總價 & 需求量
			prLineForm.preSave(); //儲存前更新
			//=========================setClassstructureuid=======================================
			try {
				Method method = prLineForm.getObject().getClass().getMethod(PrLineForm.prlineMethod,
						prLineForm.getPrline().getClass(),prLineForm.getActionType().getClass());
				method.invoke(prLineForm.getObject(), prLineForm.getPrline(),prLineForm.getActionType());
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			} 
			//=========================更新view=======================================
			if(StringUtils.isNotBlank(prLineForm.getUpdateView())){ 
				RequestContext context = RequestContext.getCurrentInstance();  
				context.update(prLineForm.getUpdateView());
			}
		} else {
			message.addErrorMessage("Error", warnMessage.toString());
		}
	}
	public void setSelectItem(Item item) { // 取得料號
		prlineImpl.setPrline(prLineForm.getPrForm().getPr(),prLineForm.getPrline(),item);
		prLineForm.setItemForm();
		this.setPrlineView();
	}
	
	/** 更新共應商 */
	public void setCompany(VwNewvendorcodeEpmall vwNewvendorcodeEpmall) {
		prLineForm.getPrline().setVendor(vwNewvendorcodeEpmall==null?
				null:vwNewvendorcodeEpmall.getNvcid().toString());
	}
	/** 更新設備編號 */
	public void setMroOrgFacilityEq(MroOrgFacilityEq mroOrgFacilityEq) {
		prLineForm.getPrline().setEqId(mroOrgFacilityEq.getEqId());
	}

	// ============================================================================================

	public PrLineForm getPrLineForm() {
		return prLineForm;
	}

	public void setPrLineForm(PrLineForm prLineForm) {
		this.prLineForm = prLineForm;
	}

	public PrLineView getPrLineView() {
		return prLineView;
	}

	public void setPrLineView(PrLineView prLineView) {
		this.prLineView = prLineView;
	}
	
	
}
