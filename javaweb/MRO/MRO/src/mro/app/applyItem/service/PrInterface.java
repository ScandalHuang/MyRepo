package mro.app.applyItem.service;

import java.math.BigDecimal;

import com.inx.commons.jsf.GlobalGrowl;

import mro.base.System.config.basicType.ActionType;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;
import mro.form.PrForm;
import mro.viewForm.PrView;

public interface PrInterface {
	public PrForm selectApply(PrForm prForm,Pr pr);
	public void closePrLine(PrForm prForm,PrView prView,Prline prline,String loginEmpNO); // close prline
	public void combinePrLine(PrForm prForm,PrView prView,Prline prline,String loginEmpNO); // close prline
	public void reclosePrLine(PrForm prForm,PrView prView,Prline prline); // reclose prline
	public void recombinePrLine(PrForm prForm,PrView prView,Prline prline);
	public PrForm closePrLine(PrForm prForm,PrView prView,String loginEmpNO); // close prline
	public PrForm combinePrLine(PrForm prForm,PrView prView,String loginEmpNO); // combine prline
	public PrForm deletePrLine(PrForm prForm); // 刪除prline(費用重新計算)
	public PrForm onEmployeeClear(PrForm prForm,Long type);
	public PrForm setEmployee(PrForm prForm,Person person);
	public PrForm setSelectPrline(PrForm prForm,Prline prline,ActionType actionType);
	public BigDecimal gettotalcost(PrForm prForm);
	//===================================Action========================================
	public boolean onApplySave(PrForm prForm,ActionType actionType,Person person,GlobalGrowl message);  // 儲存申請單
	public void onCan(PrForm prForm); // 取消申請單
	public void onDelete(PrForm prForm); // 刪除申請單
	public boolean onApplyProcess(PrForm prForm,ActionType actionType,Person person,GlobalGrowl message);  // 儲存申請單
	//==================================參數===============================
	public void setPrlineList(PrForm prForm);
	public void setParameter(PrForm prForm);
	public void setReasonFlag(PrForm prForm);
	//===================================File============================================
	public void setDownLoadFile(PrForm prForm);
	//===================================pr 相關人員姓名============================================
	public void setPrNameMap(PrForm prForm);
	//===================================ProceessId========================================
	public int getPrSignProcessId(Pr pr);
	public PrForm onSignPreView(PrForm prForm);
	//===================================機台代 碼==========================================
	public boolean getMroOrgFacilityEqSize(Pr pr,LocationSiteMap locationSiteMap);
	//eqid=null 清空prline的eqid資料
	public void updaetMroOrgFacilityEq(PrForm prForm);
	//==================================簽核參數===============================
	public String getSignParameter(PrForm prForm);
	public String getSignParameter(PrForm prForm,String warnMessage);
}
