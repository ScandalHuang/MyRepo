package mro.base.System.config.jsf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import mro.app.commonview.bo.FileUploadBO;
import mro.app.commonview.bo.ListItemCommonBO;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.ItemType;
import mro.base.System.config.basicType.ParameterType;
import mro.base.System.config.basicType.PrLineType;
import mro.base.System.config.entity.ParameterMap;
import mro.base.bo.LocationMapBO;
import mro.base.bo.LocationSiteMapBO;
import mro.base.bo.ParameterBO;
import mro.base.entity.Attachment;
import mro.base.entity.LocationMap;
import mro.base.entity.LocationSiteMap;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

@ManagedBean(name = "SystemConfigBean")
@ApplicationScoped
public class SystemConfigBean{
	private String materialR1=ItemType.R1.toString();
	private String materialR2=ItemType.R2.toString();
	private String materialR3=ItemType.R3.toString();
	private String materialR94=ItemType.R94.toString();
	private long filetDelay=500;
	
	//========================廠區===============================
	private Map<String,String> lOption;  //value=organization
	private Map<String,String> lSIdOption;  //value=siteid
	private Map<String,LocationMap> locationMap;  //key=siteid+orgCode value=LocationMap;
	private Map<String,String> lSiteOption;  //locatoinSite Option
	
	private Map<String,String> parameterMap;  // parameter map 
	private Map<ParameterType,Map> parameterOption;  //parameter option
	private Map<String,FileCategory> fileMap;
	private Map prlineStatusMap;
	private Map msdsReportMap;
	private Map defaultNonEditSpec;
	private Map siteMap;
	private boolean productionValidate;
	private Map locationMapALL;// SITEID & OrganizationCode 對應的名稱
	
	private String aln=SystemConfig.aln;
	private String numeric=SystemConfig.numeric;
	
	private DefaultStreamedContent photo;
	
	public SystemConfigBean(){
	}
	
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		ParameterBO parameterBO =SpringContextUtil.getBean(ParameterBO.class);
		LocationMapBO locationMapBO = SpringContextUtil.getBean(LocationMapBO.class);
		ListItemCommonBO listItemCommonBO =SpringContextUtil.getBean(ListItemCommonBO.class);
		LocationSiteMapBO locationSiteMapBO=SpringContextUtil.getBean(LocationSiteMapBO.class);
		this.setParameter(parameterBO.getParameterAllMap());
		fileMap=FileCategory.getFileMap();
		//===================location & locatoin site mapping=====================
		List<LocationMap> locations=locationMapBO.getLocationMapList(""); //有locationSite
		lSIdOption = locationMapBO.getLocationMapOptionBySite(locations);
		lOption = locationMapBO.getLocationMapOptionByOCode(locations);
		lSiteOption=locationSiteMapBO.getMap(locationSiteMapBO.getLocationSiteMapList());
		
		List<LocationMap> locationAll=locationMapBO.getLocationAllList();//全部
		locationMap=locationAll.stream().filter(l->l.getSiteId()!=null).collect(Collectors.toMap(LocationMap::getSiteId,l->l));
		locationMap.putAll(locationAll.stream().filter(l->l.getOrganizationCode()!=null).collect(Collectors.toMap(LocationMap::getOrganizationCode,l->l)));
		locationMapALL=locationMapBO.getLocationMapAll(locationAll);
		//=====================================================================
		prlineStatusMap=PrLineType.getMap();
		msdsReportMap=SystemConfig.msdsReportMap();
		defaultNonEditSpec=SystemConfig.defaultNonEditSpec();
		siteMap=listItemCommonBO.getSiteMap();
		productionValidate=Utility.validateHostName(SystemConfig.PRODUCTION_MAP);
	}
	//==================圖片下載===================================================
	public DefaultStreamedContent getPhoto() { 
		String fileId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fileId");
		if(StringUtils.isNotBlank(fileId)){
			FileUploadBO fileUploadBO = SpringContextUtil.getBean(FileUploadBO.class);
			Attachment attachment=fileUploadBO.getAttachment(fileId);
			if(attachment!=null){
				File file = new File(attachment.getFilePath());
				if (file.exists()) {
				    try {
				    	return new DefaultStreamedContent(new FileInputStream(file));

					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				file = new File(attachment.getFilePath());
			}
		}
		return new DefaultStreamedContent();
	}
	public void setParameter(Map<ParameterType,ParameterMap> parameterAllMap){
		parameterMap=new HashMap<String,String>();
		parameterOption=new HashMap<ParameterType,Map>();
		Iterator iterator=parameterAllMap.entrySet().iterator();
		while(iterator.hasNext()){
			Map.Entry entry=(Entry) iterator.next();
			ParameterMap parameterMap=(ParameterMap) entry.getValue();
			parameterOption.put((ParameterType)entry.getKey(),parameterMap.getOption());
			this.parameterMap.putAll(parameterMap.getMap());
		}
	}
	public void postProcessXLS(Object document)
    {   
       HSSFWorkbook wb = (HSSFWorkbook) document;
       HSSFSheet sheet = wb.getSheetAt(0);
       HSSFRow header  = sheet.getRow(0);
       HSSFCellStyle cellStyle = wb.createCellStyle();
       for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) 
       {
          HSSFCell cell = header.getCell(i);
          cell.setCellStyle(cellStyle);
          cell.setCellValue(cell.getStringCellValue().replace("<BR/>", "\r\n"));
       }
    }
	/** 取得LOCATION_MAP LIST */
	public List<LocationMap> getLocationMaps(Object locationSite){
		if(StringUtils.isBlank(ObjectUtils.toString(locationSite))) return null;
		Set<LocationMap> locationMaps=locationMap.values().stream()
				.filter(l->{
					if(locationSite instanceof String){
						if(locationSite.equals("失效區域") && l.getLocationSiteMap()==null)
							return true;
						if(l.getLocationSiteMap()!=null)
							return l.getLocationSiteMap().getLocationSite().equals(locationSite.toString());
					}else if(locationSite instanceof LocationSiteMap){
						if(l.getLocationSiteMap()!=null)
							return l.getLocationSiteMap().getLocationSite().equals(((LocationSiteMap)locationSite).getLocationSite());
					}
					return false;
				}).sorted((f1, f2) ->f1.getOrganizationName().compareTo(f2.getOrganizationName()))
				.collect(Collectors.toSet());
		return locationMaps.stream()
				.sorted((f1, f2) ->f1.getOrganizationName().compareTo(f2.getOrganizationName()))
				.collect(Collectors.toList());
	}
	//=============================================================================
	

	public Map getPrlineStatusMap() {
		return prlineStatusMap;
	}

	public Map getMsdsReportMap() {
		return msdsReportMap;
	}

	public Map getDefaultNonEditSpec() {
		return defaultNonEditSpec;
	}

	public Map getSiteMap() {
		return siteMap;
	}

	public boolean isProductionValidate() {
		return productionValidate;
	}


	public String getMaterialR2() {
		return materialR2;
	}

	public String getMaterialR1() {
		return materialR1;
	}

	public Map getLocationMapALL() {
		return locationMapALL;
	}

	public String getMaterialR94() {
		return materialR94;
	}


	public String getAln() {
		return aln;
	}

	public String getNumeric() {
		return numeric;
	}

	public String getMaterialR3() {
		return materialR3;
	}

	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	public Map<ParameterType, Map> getParameterOption() {
		return parameterOption;
	}

	public Map<String, FileCategory> getFileMap() {
		return fileMap;
	}

	public long getFiletDelay() {
		return filetDelay;
	}

	public Map<String, String> getlOption() {
		return lOption;
	}

	public Map<String, String> getlSIdOption() {
		return lSIdOption;
	}

	public Map<String, LocationMap> getLocationMap() {
		return locationMap;
	}

	public Map<String, String> getlSiteOption() {
		return lSiteOption;
	}

}

