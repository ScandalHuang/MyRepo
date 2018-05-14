package mro.app.commonview.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import mro.app.commonview.services.FileUploadInterfaces;
import mro.app.commonview.services.Impl.FileUploadImpl;
import mro.base.System.config.basicType.FileCategory;
import mro.base.entity.Attachment;
import mro.form.FileUploadForm;

import org.apache.commons.lang.StringUtils;

@ManagedBean(name = "ImageViewBean")
@ViewScoped
public class ImageViewBean implements Serializable {
	private static final long serialVersionUID = -1901723880401810750L;
	private FileUploadForm fileUploadForm;

	public ImageViewBean() {

	}

	@PostConstruct
	public void init() {
		fileUploadForm =new FileUploadForm();
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		if(StringUtils.isNotBlank(paramMap.get("keyId"))){
			fileUploadForm.setListAttachment(getAttachmentList(
					paramMap.get("keyId"), FileCategory.ITEM_PHOTO));
		}
	}

	public void inital() { // 初始化
		fileUploadForm.intial();
	}
	
	public List<Attachment> getAttachmentList(String keyid,FileCategory category){
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		return fileUploadInterfaces.getAttachmentList(keyid, category);
	}
	// 檔案下載
	public void download(Attachment attachment) throws Exception {
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		fileUploadInterfaces.download(attachment);
	}

	// =======================================================================

	public FileUploadForm getFileUploadForm() {
		return fileUploadForm;
	}

	public void setFileUploadForm(FileUploadForm fileUploadForm) {
		this.fileUploadForm = fileUploadForm;
	}


}
