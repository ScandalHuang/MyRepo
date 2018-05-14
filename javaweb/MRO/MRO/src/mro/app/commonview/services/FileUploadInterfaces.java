package mro.app.commonview.services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.Attachment;
import mro.form.FileUploadForm;

import org.primefaces.model.UploadedFile;

public interface FileUploadInterfaces {
	
	public void onFileType(FileUploadForm fileUploadForm,FileCategory Category,Object keyId);
	
	public void onFileType(FileUploadForm fileUploadForm,
			long filesize, long fileLimit, FileCategory Category,Object keyId,
			Map dowloadFileMap,String updateView,boolean editButton);
	
	public void handleFileUpload(FileUploadForm fileUploadForm,UploadedFile uploadFile,String empNo) throws Exception;
	
	public void uploadFile(String keyId, String accountId, Date date,
			FileCategory category, byte[] inbyte, String FileName, boolean delete) throws Exception;
	
	public void fileDelete(String keyId, FileCategory fileCategory, String fileId);

	public void fileDelete(FileUploadForm fileUploadForm,Attachment attachment);
	
	public void readFile(byte[] inbyte, OutputStream output) throws IOException;
	
	public void download(Attachment attachment) throws Exception;
	
	public void copyFile(String keyId, String newItemNum, FileCategory category,String empNo,
			boolean delete) throws Exception ;
	
	public void copyFileTemp(String userId, String keyId, String newItemNum,
			FileCategory category, boolean delete) throws Exception;
	
	public List<Attachment> getAttachmentList(String keyid,FileCategory category);
	
	public void onUpdatePage(FileUploadForm fileUploadForm) throws Exception;

}
