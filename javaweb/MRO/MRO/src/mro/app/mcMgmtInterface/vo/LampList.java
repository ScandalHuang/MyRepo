package mro.app.mcMgmtInterface.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import mro.base.entity.LampControlHeader;
import mro.base.entity.LampControlLine;
import mro.base.entity.MroOrgFacility;

public class LampList implements Serializable {
	private static final long serialVersionUID = 6606895396757160298L;

	private LampControlHeader header;
	private MroOrgFacility mroOrgFacility;
	private List<LampControlLine> listLampControlLine;
	private List<LampControlLine> oriListLampControlLine;
	

	public LampList(LampControlHeader header,int range,List<WeekDateInterval> weekInterval ){
		listLampControlLine=new LinkedList<LampControlLine>();
		oriListLampControlLine=new LinkedList<LampControlLine>();
		this.header=header;
		
		for(int i=0;i<range;i++){
			LampControlLine lamp=new LampControlLine();
			lamp.setLampControlHeader(header);
			lamp.setControlDateStart(weekInterval.get(i).getDateStart());
			lamp.setControlDateEnd(weekInterval.get(i).getDateEnd());
			lamp.setNeedQty(new BigDecimal("0"));
			lamp.setMcQty(new BigDecimal("0"));
			lamp.setMatuseQty(new BigDecimal("0"));
			listLampControlLine.add(lamp);
		}
		this.copyLineList();
	}
	
	public void copyLineList(){
		oriListLampControlLine.clear();
		for(LampControlLine l:listLampControlLine){
			LampControlLine lamp=new LampControlLine();
			BeanUtils.copyProperties(l, lamp);
			oriListLampControlLine.add(lamp);
		}
	}
	public List<LampControlLine> getListLampControlLine() {
		return listLampControlLine;
	}

	public void setListLampControlLine(List<LampControlLine> listLampControlLine) {
		this.listLampControlLine = listLampControlLine;
	}

	public LampControlHeader getHeader() {
		return header;
	}

	public void setHeader(LampControlHeader header) {
		this.header = header;
	}

	public MroOrgFacility getMroOrgFacility() {
		return mroOrgFacility;
	}

	public void setMroOrgFacility(MroOrgFacility mroOrgFacility) {
		this.mroOrgFacility = mroOrgFacility;
	}

	public List<LampControlLine> getOriListLampControlLine() {
		return oriListLampControlLine;
	}

	public void setOriListLampControlLine(
			List<LampControlLine> oriListLampControlLine) {
		this.oriListLampControlLine = oriListLampControlLine;
	}
	
}
