package mro.app.mcMgmtInterface.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.inx.commons.util.SpringContextUtil;

import mro.app.mcMgmtInterface.vo.LampList;
import mro.app.mcMgmtInterface.vo.WeekDateInterval;
import mro.base.bo.MroOrgFacilityBO;
import mro.base.entity.LampControlHeader;
import mro.base.entity.LampControlLine;
import mro.base.entity.MroOrgFacility;
import mro.base.entity.Person;

public class LampControlForm implements Serializable{
	private static final long serialVersionUID = -1880888400000791074L;
	private List<LampList> list;
	List<MroOrgFacility> mroOrgFacilitys;
	private int weekRage;   //顯示幾週
	private List<WeekDateInterval> weekInterval;  //每週的起日與節束日
	
	//==============search========================
	private String activeFlag;
	private String itemnum;
	private String organizationCode;
	private String deptCode;
	private Date   createDate;
	
	public LampControlForm(Person person,int weekRage){
		this.organizationCode=person.getOrganizationCode();
		this.deptCode=person.getDeptCode();
		this.createDate=new Date();
		this.weekRage=weekRage;
		this.intial();
		this.setWeekDateInterval();
	}
	
	public void intial(){
		list=new ArrayList<LampList>();
		mroOrgFacilitys=new ArrayList<MroOrgFacility>();
		weekInterval=new ArrayList<WeekDateInterval>();
	}
	
	private void setWeekDateInterval(){
		for(int i=0;i<this.weekRage;i++){
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(createDate);
	        calendar.add(Calendar.DAY_OF_YEAR,i*7);
			weekInterval.add(new WeekDateInterval(new Date(calendar.getTimeInMillis())));
		}
	}
	/*
	 * 新增headers到list的第一筆
	 */
	public void addList(LampControlHeader headers){
			LampList lampList=new LampList(headers,weekRage,weekInterval);
			int mIndex=mroOrgFacilitys.indexOf(
					new MroOrgFacility(headers.getDeptCode(),headers.getOrganizationCode()));
			lampList.setMroOrgFacility(mroOrgFacilitys.get(mIndex));
			list.add(0,lampList);
	}
	/*
	 * 設定list
	 */
	public void setList(List<LampControlHeader> headers){
		this.setWeekDateInterval();
		//=========================================================
		MroOrgFacilityBO mroOrgFacilityBO=SpringContextUtil.getBean(MroOrgFacilityBO.class);
		mroOrgFacilitys=mroOrgFacilityBO.getListByLamp(headers);
		//=========================================================
		for(LampControlHeader h:headers){
			LampList lampList=new LampList(h,weekRage,weekInterval);
			int mIndex=mroOrgFacilitys.indexOf(
					new MroOrgFacility(h.getDeptCode(),h.getOrganizationCode()));
			lampList.setMroOrgFacility(mroOrgFacilitys.get(mIndex));
			if(h.getLampControlLines()!=null){
				for(LampControlLine l:h.getLampControlLines()){
					lampList.getListLampControlLine().set(getWeekListIndex(l, createDate),l);
				}
			}
			lampList.copyLineList();  //複製一份原始line
			list.add(lampList);
		}
	}
	
	
	private int getWeekListIndex(LampControlLine lamp,Date date){
		long d1 = new java.sql.Date(date.getYear(), date.getMonth(), date.getDate()).getTime();
	    long d2 = new java.sql.Date(lamp.getControlDateEnd().getYear(), 
	    		lamp.getControlDateEnd().getMonth(), lamp.getControlDateEnd().getDate()).getTime();
        long time = 1000*3600*24; //A day in milliseconds
        return (int) (((d2-d1)/time)/7);
	}
	
	public String getItemnum() {
		return itemnum;
	}

	public void setItemnum(String itemnum) {
		this.itemnum = itemnum;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public List<LampList> getList() {
		return list;
	}

	public int getWeekRage() {
		return weekRage;
	}

	public void setWeekRage(int weekRage) {
		this.weekRage = weekRage;
	}

	public List<WeekDateInterval> getWeekInterval() {
		return weekInterval;
	}

	public void setWeekInterval(List<WeekDateInterval> weekInterval) {
		this.weekInterval = weekInterval;
	}

	public List<MroOrgFacility> getMroOrgFacilitys() {
		return mroOrgFacilitys;
	}

	public void setMroOrgFacilitys(List<MroOrgFacility> mroOrgFacilitys) {
		this.mroOrgFacilitys = mroOrgFacilitys;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}
