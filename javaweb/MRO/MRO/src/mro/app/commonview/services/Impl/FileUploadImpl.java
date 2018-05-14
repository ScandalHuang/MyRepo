package mro.app.commonview.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import mro.app.commonview.bo.FileUploadBO;
import mro.app.commonview.services.FileUploadInterfaces;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.Attachment;
import mro.form.FileUploadForm;
import mro.utility.JsfContextUtils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class FileUploadImpl implements FileUploadInterfaces{

	@Override
	public void onFileType(FileUploadForm fileUploadForm,
			FileCategory fileCategory, Object keyId) {
		this.onFileType(fileUploadForm, 0,0,fileCategory,keyId,null,null,false);
		
	}
	@Override
	public void onFileType(FileUploadForm fileUploadForm,
			long filesize, long fileLimit, FileCategory fileCategory,Object keyId,
			Map dowloadFileMap,String updateView, boolean editButton) {
		fileUploadForm.intial();
		if(fileCategory!=null){
			fileUploadForm.setAllowTypesText(fileCategory.getValue().getValue());
			fileUploadForm.setAllowTypes(fileCategory.getValue().getSubName().getValue());
		}
		fileUploadForm.setDowloadFileMap(dowloadFileMap);
		fileUploadForm.setUpdateView(updateView);
		fileUploadForm.setSizeLimit((int) (SystemConfig.ApplyFilesizeLimit * filesize));
		fileUploadForm.setFileLimit(fileLimit);
		fileUploadForm.setFileCategory(fileCategory);
		fileUploadForm.setKeyId(keyId);
		fileUploadForm.setEditButton(editButton);
		fileUploadForm.setListAttachment(getAttachmentList(ObjectUtils.toString(keyId),fileCategory));
		
		JsfContextUtils.executeView("PF('FileUploadDialog').show();");
		JsfContextUtils.updateView("fileUploadForm"); //更新上傳檔案頁面
	}
	


	@Override
	public void handleFileUpload(FileUploadForm fileUploadForm,UploadedFile uploadFile,String empNo) throws Exception {
		GlobalGrowl message = new GlobalGrowl();
		if (uploadFile.getSize() > fileUploadForm.getSizeLimit()) {
			message.addErrorMessage("Error", "Maximum file size allowed: "
					+ fileUploadForm.getSizeLimit() / 1024 / 1024 + " MB!");
		} else if (fileUploadForm.getListAttachment().size() >= fileUploadForm.getFileLimit()) {
			message.addErrorMessage("Error", "Maximum file limit allowed: "
					+ fileUploadForm.getFileLimit() + " !");
		} else if (fileUploadForm.getFileCategory()==null 
				|| StringUtils.isBlank(ObjectUtils.toString(fileUploadForm.getKeyId()))) {
			message.addErrorMessage("Error",
					"System Error,File Category or key  are Null!");
		} else if (uploadFile != null) {
			uploadFile(ObjectUtils.toString(fileUploadForm.getKeyId()), empNo,
					new Date(System.currentTimeMillis()), fileUploadForm.getFileCategory(),
					uploadFile.getContents(), uploadFile.getFileName(), false);
			fileUploadForm.setListAttachment(getAttachmentList(
					ObjectUtils.toString(fileUploadForm.getKeyId()),fileUploadForm.getFileCategory()));
			message.addInfoMessage("Upload", "Upload successful!");
		}

		
	}

	@Override
	public void uploadFile(String keyId, String accountId, Date date,
			FileCategory category, byte[] inbyte, String FileName, boolean delete) throws Exception {
		FileUploadBO fileUploadBO = SpringContextUtil
				.getBean(FileUploadBO.class);
		BigDecimal fileId = fileUploadBO.getFileId();

		// 先刪除檔案
		if (delete) {
			this.fileDelete(keyId, category, "");
		}

		int startIndex = FileName.lastIndexOf(46);
		int endIndex = FileName.length();
		String fileName = fileId.toString()
				+ FileName.substring(startIndex, endIndex);
		String filepath = SystemConfig.uploadpath_prodution + category + "\\" + fileName;
		
		if(!Utility.validateHostName(SystemConfig.PRODUCTION_MAP)){  //測試機
			filepath = SystemConfig.uploadpath_stagging + category + "\\" + fileName;
		}

		File saveFile = new File(filepath);
		if (!saveFile.getParentFile().exists()) {
			saveFile.getParentFile().mkdirs();
		}

		FileOutputStream out = new FileOutputStream(saveFile);
		this.readFile(inbyte, out);
		//===================================檔案不見 暫存=======================================
//		if(Utility.productionValidate()){  //正式機
//			
//			String filepath2 = SystemConfig.uploadpath_stagging + category + "\\" + fileName;
//			File saveFile2 = new File(filepath2);
//			if (!saveFile2.getParentFile().exists()) {
//				saveFile2.getParentFile().mkdirs();
//			}
//			FileOutputStream out2 = new FileOutputStream(saveFile2);
//			this.readFile(inbyte, out2);
//			
//			String filepath3 = "Z:\\MROFILE\\" + category + "\\" + fileName;
//			File saveFile3 = new File(filepath3);
//			if (!saveFile3.getParentFile().exists()) {
//				saveFile3.getParentFile().mkdirs();
//			}
//			FileOutputStream out3 = new FileOutputStream(saveFile3);
//			this.readFile(inbyte, out3);
//		}
		
		if(!saveFile.exists()){
			throw new RuntimeException("file not exists");
		}
		//=====================================================================================
		Attachment attachment = new Attachment();
		attachment.setFileId(fileId);
		attachment.setCreateDate(date);
		attachment.setCreateAccountId(accountId);
		attachment.setFileCategory(category.toString());
		attachment.setFileName(FilenameUtils.getName(FileName));
		attachment.setKeyId(keyId.toString());
		attachment.setFilePath(filepath);
		fileUploadBO.saveFile(attachment);
		
	}
	


	@Override
	public void fileDelete(String keyId, FileCategory fileCategory, String fileId) {
		FileUploadBO fileUploadBO = SpringContextUtil
				.getBean(FileUploadBO.class);
		List<Attachment> listAttachment = fileUploadBO.getAttachmentList(keyId,
				fileCategory, fileId);
		for (Attachment a : listAttachment) {
			File file = new File(a.getFilePath());
			if (file.exists()) {
				file.delete();
			}
		}
		fileUploadBO.deleteAttachmentList(keyId, fileCategory, fileId);
	}
	


	@Override
	public void readFile(byte[] inbyte, OutputStream output) {
		int off = 0;

		try {
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
		} catch (IOException e) {
			// e.printStackTrace();
		} finally {
			if (output != null)
				try { // ie8取消申請時必須關閉串流
					output.close();
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage());
				}
		}
	}

	
	
	@Override
	public List<Attachment> getAttachmentList(String keyid,FileCategory category) {
		FileUploadBO fileUploadBO = SpringContextUtil
				.getBean(FileUploadBO.class);
		return fileUploadBO.getAttachmentList(keyid, category, "");
	}



	@Override
	public void download(Attachment attachment) throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext external = context.getExternalContext();
		external.responseReset();
		// external.setResponseContentType("application/vnd.ms-excel");
		external.setResponseHeader(
				"Content-Disposition",
				"attachment; filename=\""
						+ URLEncoder.encode(attachment.getFileName(), "UTF-8")
								.replace("+", "%20") + "\"");
		OutputStream output = external.getResponseOutputStream();
		FileInputStream in = new FileInputStream(attachment.getFilePath());
		this.readFile(IOUtils.toByteArray(in), output);
		if (in != null)
			in.close();
		context.responseComplete();
	}



	@Override
	public void copyFile(String keyId, String newItemNum, FileCategory category,String empNo,
			boolean delete) throws Exception {

		FileUploadBO fileUploadBO = SpringContextUtil
				.getBean(FileUploadBO.class);
		List<Attachment> listAttachment = fileUploadBO.getAttachmentList(keyId,
				category, "");
		for (Attachment attachment : listAttachment) {
			File saveFile = new File(attachment.getFilePath());
			System.out.println("keyid:"+keyId+",newItemNum:"+newItemNum+",saveFile.exists():"+saveFile.exists());
			if (saveFile.exists()) {
				InputStream in = new FileInputStream(saveFile);
				uploadFile(newItemNum, empNo, new Date(
						System.currentTimeMillis()),
						FileCategory.valueOf(attachment.getFileCategory()), IOUtils.toByteArray(in),
						attachment.getFileName(), delete);
				in.close();
			}
		}
	}



	@Override
	public void copyFileTemp(String userId, String keyId, String newItemNum,
			FileCategory category, boolean delete) throws Exception {

		FileUploadBO fileUploadBO = SpringContextUtil
				.getBean(FileUploadBO.class);
		List<Attachment> listAttachment = fileUploadBO.getAttachmentList(keyId,
				category, "");
		for (Attachment attachment : listAttachment) {
			File saveFile = new File(attachment.getFilePath());
			if (saveFile.exists()) {
				InputStream in = new FileInputStream(saveFile);
				uploadFile(newItemNum, userId,
						new Date(System.currentTimeMillis()),
						FileCategory.valueOf(attachment.getFileCategory()), IOUtils.toByteArray(in),
						attachment.getFileName(), delete);
				in.close();
			}
		}
	}



	@Override
	public void fileDelete(FileUploadForm fileUploadForm,Attachment attachment) {
		GlobalGrowl message = new GlobalGrowl();
		FileUploadBO fileUploadBO = SpringContextUtil
				.getBean(FileUploadBO.class);
		fileUploadBO.deleteFile(attachment);
		File file = new File(attachment.getFilePath());
		if (file.exists()) {
			file.delete();
		}
		fileUploadForm.setListAttachment(getAttachmentList(
				ObjectUtils.toString(fileUploadForm.getKeyId()),fileUploadForm.getFileCategory()));
		message.addInfoMessage("Delete", "Delete successful!");
	}



	@Override
	public void onUpdatePage(FileUploadForm fileUploadForm) throws Exception {		
		if(fileUploadForm.getDowloadFileMap()!=null){
			if(fileUploadForm.getDowloadFileMap().get(fileUploadForm.getKeyId())!=null){
				fileUploadForm.getDowloadFileMap().put(fileUploadForm.getKeyId(),
						fileUploadForm.getListAttachment().size());
				
			}else if(fileUploadForm.getDowloadFileMap().get(fileUploadForm.getFileCategory())!=null){
				fileUploadForm.getDowloadFileMap().put(fileUploadForm.getFileCategory(),
						fileUploadForm.getListAttachment().size());
			}
		}
		
		if(StringUtils.isNotBlank(fileUploadForm.getUpdateView())){ 
			RequestContext context = RequestContext.getCurrentInstance();  
			context.update(fileUploadForm.getUpdateView());
		}
	}
}
