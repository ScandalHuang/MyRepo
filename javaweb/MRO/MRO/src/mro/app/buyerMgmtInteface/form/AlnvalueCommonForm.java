package mro.app.buyerMgmtInteface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mro.base.entity.AlndomainCommon;
import mro.base.entity.Assetattribute;

public class AlnvalueCommonForm implements Serializable{
	
	private static final long serialVersionUID = 7867251774282078880L;
	
	private List<AlndomainCommon> listAlndomainCommon;
	private AlndomainCommon[] deleteAlndomainCommon;
	private String itemType;
	private Assetattribute assetattribute;
	private Map itemCategoryOption;
	
	private List<Assetattribute> assetattributeList;
	
	public AlnvalueCommonForm(){
		intial();
	}
	
	public void intial(){
		listAlndomainCommon=new ArrayList<>();
		deleteAlndomainCommon=null;
	}

	public List<AlndomainCommon> getListAlndomainCommon() {
		return listAlndomainCommon;
	}

	public void setListAlndomainCommon(List<AlndomainCommon> listAlndomainCommon) {
		this.listAlndomainCommon = listAlndomainCommon;
	}

	public AlndomainCommon[] getDeleteAlndomainCommon() {
		return deleteAlndomainCommon;
	}

	public void setDeleteAlndomainCommon(AlndomainCommon[] deleteAlndomainCommon) {
		this.deleteAlndomainCommon = deleteAlndomainCommon;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public Assetattribute getAssetattribute() {
		return assetattribute;
	}

	public void setAssetattribute(Assetattribute assetattribute) {
		this.assetattribute = assetattribute;
	}

	public Map getItemCategoryOption() {
		return itemCategoryOption;
	}

	public void setItemCategoryOption(Map itemCategoryOption) {
		this.itemCategoryOption = itemCategoryOption;
	}

	public List<Assetattribute> getAssetattributeList() {
		return assetattributeList;
	}

	public void setAssetattributeList(List<Assetattribute> assetattributeList) {
		this.assetattributeList = assetattributeList;
	}
}
