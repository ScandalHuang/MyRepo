package mro.app.applyItem.Utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.app.commonview.bo.FileUploadBO;
import mro.base.System.config.basicType.ClassstructureApplyType;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.basicType.SignStatus;
import mro.base.bo.AlndomainBO;
import mro.base.bo.ClassstructureLineApplyBO;
import mro.base.entity.Alndomain;
import mro.base.entity.ClassstructureHeaderApply;
import mro.base.entity.ClassstructureLineApply;
import mro.form.ClassstructureForm;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ApplyClassstructureUtils {

	public static String validate(ClassstructureForm classstructureForm) {
		FileUploadBO fileUploadBO = SpringContextUtil.getBean(FileUploadBO.class);
		ClassstructureHeaderApply classstructureHeaderApply = classstructureForm
				.getClassstructureHeaderApply();
		StringBuffer warnMessage = new StringBuffer();
		boolean lineValidate=true;  //line的驗證是否通過
		warnMessage = warnMessage.append(vaildateHeader(classstructureHeaderApply));

		if (StringUtils.isBlank(classstructureHeaderApply.getHeaderRemark())) // 用途說明
		{
			warnMessage.append("用途說明必須填寫!<br />");
		}
		
		if (fileUploadBO.getAttachmentList(classstructureHeaderApply.getApplyHeaderNum(),
				FileCategory.CLASSSTRUCTURE_HEADER_ATTACHMENT, "").size() == 0) {
			warnMessage.append("附件 必須上傳!<br />");
		}
		if (classstructureForm.getListClassstructureLineApply().size() == 0) {
			warnMessage.append("請選擇執行異動的屬性清單!!<br />");
		} else {			
			for (ClassstructureLineApply l : classstructureForm
					.getListClassstructureLineApply()) {
				if (StringUtils.isBlank(l.getDomainid())) {
					warnMessage.append("項目" + l.getLineNum() + ":屬性必須選取!<br />");
					lineValidate=false;
				}

				if (StringUtils.isBlank(l.getDescription())) {
					warnMessage.append("項目" + l.getLineNum() + ":清單敘述必須不能為空值!<br />");
					lineValidate=false;
				}
			}
		}
		if(lineValidate){
			//屬性編號+清單敘述是否有正在簽核中
			warnMessage=ApplyClassstructureUtils.validateline(
					classstructureHeaderApply.getApplyHeaderId(), 
					classstructureForm.getListClassstructureLineApply(), warnMessage);
			
			//屬性編號+清單敘述是否有重複
			warnMessage=ApplyClassstructureUtils.validateDuplicate(
					classstructureForm.getListClassstructureLineApply(), warnMessage);
			
			if(Utility.equals(classstructureHeaderApply.getAction(), 
					ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ADD)){
				//屬性編號+清單敘述是否  存在ALNDOMAIN
				warnMessage=ApplyClassstructureUtils.validateAlndomain(
						classstructureForm.getListClassstructureLineApply(),null, warnMessage);
			}else if(Utility.equals(classstructureHeaderApply.getAction(), 
					ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_ACTIVE)){
				//屬性編號+清單敘述是否  生效ALNDOMAIN
				warnMessage=ApplyClassstructureUtils.validateAlndomain(
						classstructureForm.getListClassstructureLineApply(),true, warnMessage);
			}else if(Utility.equals(classstructureHeaderApply.getAction(), 
					ClassstructureApplyType.CLASSSTRUCTURE_ALNDOMAIN_INACTIVE)){
				//屬性編號+清單敘述是否  失效ALNDOMAIN
				warnMessage=ApplyClassstructureUtils.validateAlndomain(
						classstructureForm.getListClassstructureLineApply(),false, warnMessage);
			}
		}
		return warnMessage.toString();

	}

	public static String vaildateHeader(
			ClassstructureHeaderApply classstructureHeaderApply) {
		StringBuffer warnMessage = new StringBuffer();
		if (StringUtils
				.isBlank(classstructureHeaderApply.getClassstructureid())) // 必須先選擇類別結構
		{
			warnMessage.append("請先選擇類別結構!<br />");
		}
		if (StringUtils.isBlank(classstructureHeaderApply.getAction())) // 必須先選擇ACTION 
		{
			warnMessage.append("請先選擇ACTION !<br />");
		}
		if (StringUtils.isBlank(classstructureHeaderApply.getOrganizationCode())) // 必須先選擇廠區
		{
			warnMessage.append("[廠區]必須選取!<br />");
		}

		return warnMessage.toString();

	}
	 /*
	  * 屬性編號+清單敘述是否有正在簽核中
	  */
	public static StringBuffer validateline(BigDecimal applyHeaderId,
			List<ClassstructureLineApply> validateList, StringBuffer warnMessage){
		ClassstructureLineApplyBO classstructureLineApplyBO = SpringContextUtil.getBean(
				ClassstructureLineApplyBO.class);
		if(Utility.isNotEmpty(validateList)){
			List<ClassstructureLineApply> list=classstructureLineApplyBO.getValidateList(
					applyHeaderId,SignStatus.INPRG,validateList);
			for(ClassstructureLineApply c:list){
				warnMessage.append("屬性編號" + c.getAssetattrid() + ",清單敘述 "
						+ c.getDescription() + " 正在簽核中!<br />");
			}
		}
		
		return warnMessage;
	}
	
	 /*
	  * 屬性編號+清單敘述是否有重複
	  */
	public static StringBuffer validateDuplicate(List<ClassstructureLineApply> validateList, 
			StringBuffer warnMessage) {
		Map map = new HashedMap();
		for(ClassstructureLineApply c : validateList){
			String key=c.getDomainid() + c.getDescription();
			if (map.get(key) != null) {
				warnMessage.append("屬性編號" + c.getAssetattrid() + ",清單敘述 "
						+ c.getDescription() + " 重複新增!<br />");
			}else{
				map.put(key,key);
			}
		}
		return warnMessage;
	}
	 /*
	  * 屬性編號+清單敘述是否  生效/失效/存在ALNDOMAIN
	  * 存在：inactive=NULL ；生效：inactive=true ；失效：inactive=false 
	  */
	public static StringBuffer validateAlndomain(List<ClassstructureLineApply> validateList,
			Boolean inactive,StringBuffer warnMessage) {
		if(Utility.isNotEmpty(validateList)){
			AlndomainBO alndomainBO=SpringContextUtil.getBean(AlndomainBO.class);
			Map message=new HashMap<Boolean, String>(){{ 
				put(null,"已經存在");put(true,"已生效");put(false,"未生效");
			}};
			
			for(Alndomain a:alndomainBO.getAlndomain(validateList,inactive)){
				warnMessage.append("屬性編號" + a.getDomainid().substring(
						a.getDomainid().length() - 4,a.getDomainid().length()) + ",清單敘述 "
						+ a.getDescription() + " "+message.get(inactive)+"!<br />");
			}
		}
		return warnMessage;
	}
}
