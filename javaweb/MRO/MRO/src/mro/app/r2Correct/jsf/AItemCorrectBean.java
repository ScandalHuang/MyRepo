package mro.app.r2Correct.jsf;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import mro.app.commonview.services.FileUploadExcelInterfaces;
import mro.app.commonview.services.Impl.FileUploadExceImpl;
import mro.app.r2Correct.service.impl.AItemCorrectmpl;
import mro.app.r2Correct.vo.AItemCorrect;

import com.inx.commons.jsf.GlobalGrowl;
import com.inx.commons.util.Utility;

@ManagedBean(name = "AItemCorrectBean")
@ViewScoped
public class AItemCorrectBean implements Serializable {
	private static final long serialVersionUID = 6345660159219984440L;
	private List<AItemCorrect> failList;
	
	public AItemCorrectBean() {

	}

	@PostConstruct
	public void init() {
		failList=new ArrayList<AItemCorrect>();
	}
	
	public void uploadExcel(File file) {
		this.init();
		FileUploadExcelInterfaces impl = new FileUploadExceImpl();
		this.setFailList(impl.uploadExcelToList(new AItemCorrect(),file,
				Arrays.asList("ITEMNUM"),false,true,AItemCorrectmpl.class),
				failList);
		if(Utility.isNotEmpty(failList)){
			GlobalGrowl message = new GlobalGrowl();
			message.addErrorMessage("更新失敗!!", "更新失敗 "+failList.size()+" 筆!");
		}
	}
	
	public void setFailList(List<AItemCorrect> sources,List<AItemCorrect> target){
		if(Utility.isNotEmpty(sources)){
			for(AItemCorrect a:sources){
				if(!a.isUpdateFlag()){
					target.add(a);
				}
			}
		}
	}

	//================================================================

	public List<AItemCorrect> getFailList() {
		return failList;
	}

	public void setFailList(List<AItemCorrect> failList) {
		this.failList = failList;
	}
	
}
