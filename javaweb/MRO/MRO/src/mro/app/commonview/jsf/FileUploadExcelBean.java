package mro.app.commonview.jsf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.FileType;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.inx.commons.jsf.GlobalGrowl;

@ManagedBean(name = "FileUploadExcelBean")
@ViewScoped
public class FileUploadExcelBean implements Serializable {
	private static final long serialVersionUID = 8189931592055032331L;
	private int sizeLimit;
	private String allowTypes;
	private long fileLimit;
	private File tempLoadFile;
	private String allowTypesText;
	private String fileName;
	
    private Object object;
    private String updateView;
	
	public FileUploadExcelBean() {

	}

	@PostConstruct
	public void init() {
		this.inital();
		fileLimit=1;
	}

	public void inital() { // 初始化
		fileLimit = 0;
	}

	public void excel(long filesize, long fileLimit) { // 文件上傳
		this.inital();
		sizeLimit = (int) (SystemConfig.ApplyFilesizeLimit * filesize);
		allowTypes = FileType.FileTypesExcel.getSubName().getValue();
		allowTypesText = FileType.FileTypesExcel.getValue();
		this.fileLimit = fileLimit;
		tempLoadFile=null;
		fileName="";
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		GlobalGrowl message = new GlobalGrowl();
		UploadedFile uploadFile = event.getFile();
		if (uploadFile.getSize() > sizeLimit) {
			message.addErrorMessage("Error", "Maximum file size allowed: "
					+ sizeLimit / 1024 / 1024 + " MB!");
		} else if (uploadFile != null) {
			uploadFile(uploadFile);
			message.addInfoMessage("Upload", "Upload successful!");
		}

	}
	// ========================檔案上傳===================================
	public void uploadFile(UploadedFile uploadFile){
		try {
			Date date=new Date();
			fileName=uploadFile.getFileName().substring(uploadFile.getFileName().lastIndexOf("\\")+1);
			tempLoadFile=File.createTempFile(fileName+date.getTime(), ".xls");
			FileOutputStream out = new FileOutputStream(tempLoadFile);
			this.readFile(uploadFile.getContents(), out);
			tempLoadFile.deleteOnExit();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	private void readFile(byte[] inbyte, OutputStream output)
			throws IOException {
		int off = 0;
		while (inbyte.length > off) {
			int i = 4096;
			int len = off + i;
			if (inbyte.length < len) {
				i = inbyte.length - off;
			}
			output.write(inbyte, off, i);
			output.flush();
			off += i;
		}
		if (output != null)
			output.close();
	}
	
	public void confirm() throws NoSuchMethodException, 
	SecurityException, IllegalAccessException, InvocationTargetException{
		if(tempLoadFile!=null){
			//=========================setClassstructureuid=======================================
			Method method=object.getClass().getMethod("uploadExcel", tempLoadFile.getClass());
			method.invoke(object, tempLoadFile);
			//=========================更新view=======================================
			if(StringUtils.isNotBlank(updateView)){ 
				RequestContext context = RequestContext.getCurrentInstance();  
				context.update(updateView);
			}
		}
	}
	// =======================================================================
	public int getSizeLimit() {
		return sizeLimit;
	}

	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

	public long getFileLimit() {
		return fileLimit;
	}

	public void setFileLimit(long fileLimit) {
		this.fileLimit = fileLimit;
	}

	public String getAllowTypes() {
		return allowTypes;
	}

	public void setAllowTypes(String allowTypes) {
		this.allowTypes = allowTypes;
	}

	public String getAllowTypesText() {
		return allowTypesText;
	}

	public void setAllowTypesText(String allowTypesText) {
		this.allowTypesText = allowTypesText;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getTempLoadFile() {
		return tempLoadFile;
	}

	public void setTempLoadFile(File tempLoadFile) {
		this.tempLoadFile = tempLoadFile;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getUpdateView() {
		return updateView;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}

}
