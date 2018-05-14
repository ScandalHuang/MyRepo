package mro.app.aItemSimple.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mro.base.entity.AItemSimple;

public class AItemSimpleForm implements Serializable{
	
	private static final long serialVersionUID = 490775923976777094L;
	
	private List<AItemSimple> listAItemSimple;
	private String oriitemnum;
	private String applyNumFlag;
	private String changeby;
	private String moqMpqFlag;
	private String assetattridFlag;
	
	
	public AItemSimpleForm(){
	}
	public void intial(){
		listAItemSimple=new ArrayList<>();
		oriitemnum="";
		applyNumFlag="";
		changeby="";
		moqMpqFlag="";
		assetattridFlag="";
	}
	
	public List<AItemSimple> getListAItemSimple() {
		return listAItemSimple;
	}
	public void setListAItemSimple(List<AItemSimple> listAItemSimple) {
		this.listAItemSimple = listAItemSimple;
	}
	public String getOriitemnum() {
		return oriitemnum;
	}
	public void setOriitemnum(String oriitemnum) {
		this.oriitemnum = oriitemnum;
	}
	public String getChangeby() {
		return changeby;
	}
	public void setChangeby(String changeby) {
		this.changeby = changeby;
	}
	public String getMoqMpqFlag() {
		return moqMpqFlag;
	}
	public void setMoqMpqFlag(String moqMpqFlag) {
		this.moqMpqFlag = moqMpqFlag;
	}
	public String getApplyNumFlag() {
		return applyNumFlag;
	}
	public void setApplyNumFlag(String applyNumFlag) {
		this.applyNumFlag = applyNumFlag;
	}
	public String getAssetattridFlag() {
		return assetattridFlag;
	}
	public void setAssetattridFlag(String assetattridFlag) {
		this.assetattridFlag = assetattridFlag;
	}
	
}
