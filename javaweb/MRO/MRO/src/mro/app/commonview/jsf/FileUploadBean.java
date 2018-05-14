package mro.app.commonview.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.FileUploadInterfaces;
import mro.app.commonview.services.Impl.FileUploadImpl;
import mro.base.System.config.SystemConfig;
import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.Attachment;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.FileUploadForm;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean(name = "FileUploadBean")
@ViewScoped
public class FileUploadBean implements Serializable {
	private static final long serialVersionUID = -9160876740805432509L;

	@ManagedProperty(value = "#{LoginInfoBean}")
	private LoginInfoBean loginInfoBean;

	private FileUploadForm fileUploadForm;
	private transient FileUploadInterfaces fileImpl;

	public FileUploadBean() {
	}

	@PostConstruct
	public void init() {
		fileUploadForm =new FileUploadForm();
		fileImpl=new FileUploadImpl();
	}

	public void inital() { // 初始化
		fileUploadForm.intial();
	}

	public void view(Object keyId,FileCategory category){
		fileImpl.onFileType(fileUploadForm,category, keyId);
	}
	
	public void onFileType(long filesize, long fileLimit, FileCategory category,
			Object keyId,Map dowloadFileMap,String updateView, boolean editButton) { // 文件上傳
		fileImpl.onFileType(fileUploadForm,filesize, fileLimit, category, keyId,
				dowloadFileMap,updateView, editButton);
	}

	public void handleFileUpload(FileUploadEvent event) throws Exception {
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		UploadedFile uploadFile = event.getFile();
		fileUploadInterfaces.handleFileUpload(fileUploadForm, uploadFile, loginInfoBean.getUserId());

	}
	
	public List<Attachment> getAttachmentList(String keyid,FileCategory category){
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		return fileUploadInterfaces.getAttachmentList(keyid, category);
	}

	// =========================檔案刪除==========================================
	public void fileDelete(Attachment attachment) {
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		fileUploadInterfaces.fileDelete(fileUploadForm,attachment);
	}

	// 檔案下載
	public void download(Attachment attachment) throws Exception {
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		fileUploadInterfaces.download(attachment);
	}

	public void copyFile(String keyId, String newItemNum, FileCategory category,
			boolean delete) throws Exception {
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		fileUploadInterfaces.copyFile(keyId, newItemNum, category, loginInfoBean.getUserId(), delete);

	}

	/*
	 * 料號規格異動清單File hongjie.wu 2013/11/4
	 */
	public void copyFileTemp(String userId, String keyId, String newItemNum,
			FileCategory category, boolean delete) throws Exception {
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		fileUploadInterfaces.copyFileTemp(loginInfoBean.getUserId(),keyId, newItemNum, category, delete);
	}
	public void onUpdatePage() throws Exception{
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		fileUploadInterfaces.onUpdatePage(fileUploadForm);  
	}	
	// =======================================================================

	public LoginInfoBean getLoginInfoBean() {
		return loginInfoBean;
	}

	public void setLoginInfoBean(LoginInfoBean loginInfoBean) {
		this.loginInfoBean = loginInfoBean;
	}

	public FileUploadForm getFileUploadForm() {
		return fileUploadForm;
	}

	public void setFileUploadForm(FileUploadForm fileUploadForm) {
		this.fileUploadForm = fileUploadForm;
	}


}
