package mro.viewForm;

import java.io.Serializable;

import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.entity.Pr;
import mro.base.entity.PrControlConfig;
import mro.base.entity.Prline;
import mro.form.PrForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.JsfContextUtil;
import com.inx.commons.util.Utility;
import com.inx.commons.util.hibernate.ActiveFlag;

public class PrLineView implements Serializable{

	private static final long serialVersionUID = 5182162093083514764L;
	private boolean photo;   //圖片連結
	private boolean altItemnum;  //替代料號
	private boolean requireDate;  //需求日期
	private boolean eqId;  //使用設備
	private boolean packageUnit;//包裝單位
	private boolean transferQuantity;//轉換數量
	private boolean halfyearIssueCounter;//近半年領用月次
	private boolean mccommand;//庫存分類變更說明
	private boolean maxidledays;//最大呆滯天數
	private boolean otherFile; // 依分類所對應之製成工務負責人一欄表
	private boolean costInfo;  //價格資訊
	private boolean erpInfo;  //erp pr po 資訊

	private boolean pmreqqtyT1;  //需求量(無小數點)
	private boolean pmreqqtyT2;  //需求量(有小數點)
	private boolean controlInfoT1;  //控管資訊(R1)
	private boolean controlInfoT2;  //控管資訊(R2)
	private boolean vendor; //指定供應商
	private boolean useInfo; //耗用資訊
	private boolean sstock; //廠區設定值
	
	//=============必填符號================
	private boolean requireDatNullMark; //需求日期必填
	
	public PrLineView(){
		this.inital();
	}
	public void inital(){
		photo=false;
		altItemnum=false;
		requireDate=false;
		eqId=false;
		packageUnit=false;
		transferQuantity=false;
		halfyearIssueCounter=false;
		mccommand=false;
		maxidledays=false;
		otherFile=false;
		costInfo=false;
		pmreqqtyT1=false;
		pmreqqtyT2=false;
		erpInfo=false;
		controlInfoT1=false;
		controlInfoT2=false;
		vendor=false;
		useInfo=false;
		sstock=false;
	}
	
	public void setView(PrForm prForm,Prline prline){
		this.inital();
		Pr pr=prForm.getPr();
		photo=Utility.equalsOR(pr.getPrtype(), PrType.R2PMREQ.name(),PrType.R2CONTROL.name());
		altItemnum=Utility.equals(pr.getPrtype(), PrType.R2CONTROL.name());
		
		if(!Utility.equalsOR(pr.getPrtype(),PrType.R1CONTROL.name(),PrType.R2CONTROL.name())){
			requireDate=true;
			requireDatNullMark=Utility.equals(pr.getPrtype(),PrType.R2PMREQ.name());
		}
		
		if(prForm.isEqFlag() || StringUtils.isNotBlank(prline.getEqId())){
			eqId=true;
		}
		
		packageUnit=!Utility.equals(pr.getPrtype(),PrType.R1CONTROL.name());
		if(packageUnit && StringUtils.isNotBlank(prline.getPackageUnit())){
			transferQuantity=true;
		}
		halfyearIssueCounter=Utility.equalsOR(pr.getPrtype(),PrType.R1PMREQ.name(),PrType.R1CONTROL.name());
		mccommand=Utility.equalsOR(pr.getPrtype(), PrType.R2PMREQ,PrType.R2CONTROL.name());
		maxidledays=Utility.equalsOR(pr.getPrtype(), PrType.R2PMREQ.name(),PrType.R2CONTROL.name());
		otherFile=Utility.equals(pr.getPrtype(),PrType.R2CONTROL.name());
		costInfo=Utility.equalsOR(pr.getPrtype(), PrType.R1PMREQ.name(),PrType.R1CONTROL.name());
		if(!Utility.equalsOR(pr.getPrtype(),PrType.R1CONTROL.name(),PrType.R2CONTROL.name())){
			pmreqqtyT1=!Utility.equalsOR(pr.getPrtype(),PrType.R1PMREQ.name(),PrType.R1REORDER.name());
			pmreqqtyT2=!pmreqqtyT1;
		}
		if(!Utility.equalsAND(pr.getPrtype(), PrType.R1CONTROL.name(),PrType.R2CONTROL.name()) &&
			Utility.equals(pr.getStatus(), SignStatus.APPR)){
			erpInfo=true;
		}
		controlInfoT1=Utility.equals(pr.getPrtype(), PrType.R1CONTROL.name());
		controlInfoT2=Utility.equals(pr.getPrtype(), PrType.R2CONTROL.name());
		vendor=Utility.equals(pr.getPrtype(), PrType.R2PMREQ.name());
		useInfo=Utility.equals(pr.getPrtype(), PrType.R2PMREQ.name());
		if(controlInfoT2){
			if(prline.getInvsstock() !=null && prline.getOrisstock().compareTo(prline.getInvsstock())==0){
				sstock=true;
			}
			String sstockFlag=prForm.getPrControlConfig()!=null?
					prForm.getPrControlConfig().getSstockFlag():ActiveFlag.N.name();
			if(sstockFlag.equals(ActiveFlag.Y.name())){
				sstock=true;
			}
				
		}
	}
	public boolean isPhoto() {
		return photo;
	}
	public boolean isErpInfo() {
		return erpInfo;
	}
	public boolean isAltItemnum() {
		return altItemnum;
	}
	public boolean isRequireDate() {
		return requireDate;
	}
	public boolean isRequireDatNullMark() {
		return requireDatNullMark;
	}
	public boolean isEqId() {
		return eqId;
	}
	public boolean isPackageUnit() {
		return packageUnit;
	}
	public boolean isTransferQuantity() {
		return transferQuantity;
	}
	public boolean isHalfyearIssueCounter() {
		return halfyearIssueCounter;
	}
	public boolean isMccommand() {
		return mccommand;
	}
	public boolean isMaxidledays() {
		return maxidledays;
	}
	public boolean isOtherFile() {
		return otherFile;
	}
	public boolean isCostInfo() {
		return costInfo;
	}
	public boolean isPmreqqtyT1() {
		return pmreqqtyT1;
	}
	public boolean isPmreqqtyT2() {
		return pmreqqtyT2;
	}
	public boolean isControlInfoT1() {
		return controlInfoT1;
	}
	public boolean isControlInfoT2() {
		return controlInfoT2;
	}
	public boolean isVendor() {
		return vendor;
	}
	public boolean isUseInfo() {
		return useInfo;
	}
	public boolean isSstock() {
		return sstock;
	}
	
}
