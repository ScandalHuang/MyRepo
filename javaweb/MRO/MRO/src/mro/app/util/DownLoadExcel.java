package mro.app.util;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import mro.utility.vo.ColumnModel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

public class  DownLoadExcel  {
	private int pageCount=50000;
	
	public DownLoadExcel(){
		
	}
	// ===================================down load excel==================================================
	public void postProcessXLS(List list) throws Exception{
			List<ColumnModel> columnsTemp = new ArrayList<ColumnModel>();
			if(list.size()>0){
				columnsTemp.clear(); 
				Map m=(Map) list.get(0);
				for ( Object key : m.keySet() ) {
					columnsTemp.add(new ColumnModel(key.toString(),key.toString()));  
				}
			}
			
			SimpleDateFormat formatterTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy/MM/dd");
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			HSSFCellStyle style = wb.createCellStyle();
			style.setFont(font);
			
			
			int totalPage=(int) Math.ceil(((double)list.size()/(double)pageCount));
			for(int i=0;i<totalPage;i++){
				HSSFSheet sheet = wb.createSheet("sheet"+i);
				HSSFRow header = sheet.createRow(0);
				
				//===============================header=============================================
				for(int h=0;h<columnsTemp.size();h++){
					Cell cell = header.createCell(h);
					cell.setCellStyle(style);
					ColumnModel c=columnsTemp.get(h);
					cell.setCellValue(c.getHeader());
				}
				//===============================line===============================================
				int limit=pageCount*(i+1);
				limit=limit>list.size()?list.size():limit;
				
				for(int p=i*pageCount;p<limit;p++){				
					Map map=(Map) list.get(p);
					HSSFRow line = sheet.createRow(p+1);
					for(int h=0;h<columnsTemp.size();h++){
						ColumnModel c=columnsTemp.get(h);
						Cell cell = line.createCell(h);
						Object obj=map.get(c.getHeader());
						if(obj==null){
							cell.setCellValue("");
						}else if(obj instanceof java.sql.Date){
							Date date=new Date(((Date)obj).getTime());
							if(date.getHours()==0 && date.getMinutes()==0 && date.getSeconds()==0){
								cell.setCellValue(formatterDate.format(date));
							}else{
								cell.setCellValue(formatterTime.format(date));
							}
						}else if(obj instanceof java.math.BigDecimal){
								cell.setCellValue(((BigDecimal)obj).toString());
						}else{
							Method method=Cell.class.getMethod("setCellValue", obj.getClass());
							method.invoke(cell, obj);
						}
	
					}
				}
			}
		   
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext external = context.getExternalContext();
			external.responseReset();
	//		external.setResponseContentType("application/vnd.ms-excel");
			external.setResponseHeader(
					"Content-Disposition",
					"attachment; filename=\""
							+ URLEncoder.encode(System.currentTimeMillis()+".xls", "UTF-8")
									.replace("+", "%20") + "\"");
			OutputStream output = external.getResponseOutputStream(); 
			wb.write(output);
			output.close();output.flush();
			context.responseComplete();
	}
	
	//=============================================================================================
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	
}
