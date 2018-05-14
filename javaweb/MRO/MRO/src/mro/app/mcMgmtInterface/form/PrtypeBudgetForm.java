package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import mro.app.util.StringUtilsConvert;
import mro.base.entity.LocationSiteMap;
import mro.base.entity.PrtypeBudget;
import mro.utility.vo.DateOption;

public class PrtypeBudgetForm implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<PrtypeBudget> listPrtypeBudget;
	private PrtypeBudget[] deleteList;
	private String selectPrtype;
	private LocationSiteMap sLocationSiteMap; //選取的LOCATION_SITE
	private String selectBudgetType;
	private String year;
	private String month;
	private DateOption dateOption;
	private Map typeOption;
	
	public PrtypeBudgetForm(){
		intial();
		Calendar calendar = Calendar.getInstance();
		dateOption=new DateOption(3);
		year=String.valueOf(calendar.get(Calendar.YEAR));
		month=StringUtilsConvert.addZeroForNum(String.valueOf(((int)(calendar.get(Calendar.MONTH))+1)),2);
	}
	
	public void intial(){
		listPrtypeBudget=new ArrayList<>();
		deleteList=null;

	}

	public List<PrtypeBudget> getListPrtypeBudget() {
		return listPrtypeBudget;
	}

	public void setListPrtypeBudget(List<PrtypeBudget> listPrtypeBudget) {
		this.listPrtypeBudget = listPrtypeBudget;
	}

	public String getSelectPrtype() {
		return selectPrtype;
	}

	public void setSelectPrtype(String selectPrtype) {
		this.selectPrtype = selectPrtype;
	}

	public String getSelectBudgetType() {
		return selectBudgetType;
	}

	public void setSelectBudgetType(String selectBudgetType) {
		this.selectBudgetType = selectBudgetType;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public DateOption getDateOption() {
		return dateOption;
	}

	public void setDateOption(DateOption dateOption) {
		this.dateOption = dateOption;
	}

	public Map getTypeOption() {
		return typeOption;
	}

	public void setTypeOption(Map typeOption) {
		this.typeOption = typeOption;
	}

	public PrtypeBudget[] getDeleteList() {
		return deleteList;
	}

	public void setDeleteList(PrtypeBudget[] deleteList) {
		this.deleteList = deleteList;
	}

	public LocationSiteMap getsLocationSiteMap() {
		return sLocationSiteMap;
	}

	public void setsLocationSiteMap(LocationSiteMap sLocationSiteMap) {
		this.sLocationSiteMap = sLocationSiteMap;
	}

}
