package mro.app.commonview.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import mro.app.commonview.bo.FileUploadBO;
import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.UploadInterfaces;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.ReflectUtils;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class FileUploadExceImpl implements FileUploadExcelInterfaces {

	@Override
	public void message(List entities) {
		GlobalGrowl message = new GlobalGrowl();
		if (Utility.isNotEmpty(entities)) {
			message.addInfoMessage("Upload", "成功上傳" + entities.size() + "筆!");
		} else {
			message.addErrorMessage("Error", "Upload File error!");
		}
	}
	
	@Override
	public boolean message(GlobalGrowl globalGrowl,String msg) {
		if(globalGrowl!=null && StringUtils.isNotBlank(msg)){
			globalGrowl.addErrorMessage("Error", msg);
			return false;
		}
		return true;
	}

	@Override
	public boolean uploadExcel(Object entity, File file, List<String> keyList,
			boolean deleteFlag,boolean message) {
		return this.uploadExcel(entity, file, keyList, deleteFlag, message,null);
	}

	
	@Override
	public boolean uploadExcel(Object entity, File file, List<String> keyList,
			boolean deleteFlag,boolean message, Class clazz) {
		List list=this.uploadExcelToList(
				this.converList(entity, file, keyList,message?new GlobalGrowl():null), keyList, deleteFlag,
				clazz==null?UploadImpl.class:clazz);
		this.message(list);
		return Utility.isNotEmpty(list);
	}
	
	@Override
	public boolean uploadExcel(List entities, List<String> keyList,
			boolean deleteFlag,boolean message, Class clazz) {
		List list=this.uploadExcelToList(entities, keyList, deleteFlag,
				clazz==null?UploadImpl.class:clazz);
		this.message(list);
		return Utility.isNotEmpty(list);
	}
	
	@Override
	public List uploadExcelToList(Object entity, File file,List<String> keyList, 
			boolean deleteFlag,boolean message, Class clazz) {
		List list=this.uploadExcelToList(this.converList(entity, file, keyList,message?new GlobalGrowl():null),
				keyList, deleteFlag, clazz==null?UploadImpl.class:clazz);
		this.message(list);
		return list;
	}

	@Override
	public List uploadExcelToList(List entities, List<String> keyList,
			boolean deleteFlag, Class clazz) {
		if (Utility.isNotEmpty(entities)) {
			Map<String, String> entityMap = ReflectUtils.getEntityMap(entities
					.get(0).getClass());
			FileUploadBO fileUploadBO = SpringContextUtil
					.getBean(FileUploadBO.class);
			// ===================================取得實作upload的類別==========================
			try {
				UploadInterfaces uploadI = (UploadInterfaces) clazz.newInstance();
				//================== 執行儲存前的預先作業 function======================
				uploadI.preFunction(entities);
				// ==============================insert作業=====================================
				if (Utility.isNotEmpty(entities)) {
					if (uploadI.assignFunctionFlag()) { // 指定method
						uploadI.assignFunction(entities);
					} else {
						fileUploadBO.updateExcel(entities, keyList, deleteFlag,
								entityMap);
					}
				}
				// ==============================額外作業=====================================
				if (uploadI.validate(entities)) {
					uploadI.excute(entities);
				}
				uploadI.destroyed(entities);

			} catch (Exception e) {
				throw new RuntimeException("UploadInterfaces error!" + e.getMessage());

			}
		}
		return entities;
	}

	@Override
	public Object convertValue(HSSFCell cell, String column,
			Map<String, Class> entityType) {
		Class oriType = entityType.get(column);
		//因為setParameter(null)時,hibernate不會把條件過濾,所以回傳空值可以減少sql的語法
		if(cell==null  || StringUtils.isBlank(cell.toString())){
			if(oriType.equals(String.class)){
				return "";
			}
			return null;
		}
		if (oriType.equals(Date.class)) {
			return cell.getDateCellValue();
		}else{
			cell.setCellType(Cell.CELL_TYPE_STRING);
			if (oriType.equals(BigDecimal.class)) {
				return new BigDecimal(cell.getStringCellValue());
			}else{
				return cell.getStringCellValue();
			}
				
		}
	}

	@Override
	public List converList(Object entity, File file, List<String> keyList,
			GlobalGrowl globalGrowl) {
		Map<String, String> entityMap = entity!=null?ReflectUtils.getEntityMap(entity.getClass()):null;
		Map<String, Class> entityType = entity!=null?ReflectUtils.getEntityType(entity.getClass()):null;
		List<Object> entities = new ArrayList<Object>();
		if (file != null) {
			try {
				InputStream inputStream = new FileInputStream(file);
				HSSFWorkbook wb = new HSSFWorkbook(inputStream);
				HSSFSheet sheet = wb.getSheetAt(0);
				List<String> columnList = new ArrayList<String>();
				int rowNum = 0; // row num
				int columnLength = sheet.getRow(0).getPhysicalNumberOfCells(); // header
																				// length
				while (sheet.getRow(rowNum) != null) {
					HSSFRow row = sheet.getRow(rowNum);
					Object newEntity = entity != null ? entity.getClass().newInstance() : new LinkedHashMap(); // 新組成的 entity
					if (entity != null)
						PropertyUtils.copyProperties(newEntity, entity);
					for (int columnNum = 0; columnNum < columnLength; columnNum++) {
						HSSFCell cell = row.getCell(columnNum);
						if (rowNum == 0) { // set header
							// header為null就代表header結束
							if (StringUtils.isBlank(cell.getStringCellValue())) {
								break;
							}

							String header = cell.getStringCellValue().toUpperCase().trim();
							if (header.indexOf("(") != -1) {
								columnList.add(header.substring(0,header.indexOf("(")));
							} else {
								columnList.add(header);
							}
						} else {
							if (entity != null) {
								// ====setvalue===================
								String header = entityMap.get(columnList.get(columnNum));
								if (StringUtils.isNotBlank(header)) {
									Object value = convertValue(cell,columnList.get(columnNum),entityType);
									ReflectUtils.setFieldValue(newEntity,header, value);
								}
							} else {
								((Map) newEntity).put(columnList.get(columnNum),ObjectUtils.toString(cell));
							}
						}
					}
					if (rowNum > 0 && newEntity != null) { // set value
						entities.add(newEntity);
					}
					rowNum++;
				}
				inputStream.close();

				// 出現除複key值的資料與key有缺
				if (CollectionUtils.isNotEmpty(entities)) {
					StringBuffer error = new StringBuffer();
					error.append(this.validatDuplicate(entities, keyList,entityMap));
					error.append(this.validateKey(entities, keyList, entityMap));
					if (error.length() > 0) {
						this.message(globalGrowl, error.toString());
						return null;
					}
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return entities;
	}
	@Override
	public String validatDuplicate(List entities, List<String> keyList,Map<String, String> entityMap) {
		StringBuffer error=new StringBuffer();
		if(CollectionUtils.isNotEmpty(keyList)){
			Map map=new HashMap();
			entities.stream().map(l->keyList.stream()
					.map(k->{
						Object value;
						if(l instanceof Map) value=((Map)l).get(k);
						else value=ReflectUtils.getFieldValue(l, entityMap.get(k));
						
						if(value==null || value instanceof String || value instanceof BigDecimal){
							return ObjectUtils.toString(value);
						}else {
							Map<String,String> r=ReflectUtils.getEntityMap(value.getClass());
							return ObjectUtils.toString(ReflectUtils.getFieldValue(value,r.get(k)));
						}
					}).collect(Collectors.joining(",")))
			.forEach(l->{
				if(map.get(l)!=null) error.append(l+" 數值出現重複!<br/>");
				map.put(l, l);
			});
		}
		return error.toString();
	}

	@Override
	public String validateKey(List entities, List<String> keyList,Map<String, String> entityMap) {
		StringBuffer error=new StringBuffer();
		if(CollectionUtils.isNotEmpty(keyList)){
			entities.stream().forEach(l->{
				keyList.stream().forEach(k->{
					Object value;
					if(l instanceof Map) value=((Map)l).get(k);
					else value=ReflectUtils.getFieldValue(l, entityMap.get(k));
					if(StringUtils.isBlank(ObjectUtils.toString(value))){
						error.append("第"+(entities.indexOf(l)+1)+"列缺少key值!<br/>");
						return;
					}
				});
			});
		}
		return error.toString();
	}
}
