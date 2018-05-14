package mro.app.signProcess.Utils;

import mro.app.signProcess.form.SignSourceForm;
import mro.base.entity.SignSourceCategory;
import mro.base.entity.SignSourceSystem;

import org.apache.commons.lang3.StringUtils;

public class SignSourceUtils {
	public static String validate(SignSourceForm signSourceForm) {
		StringBuffer warnMessage = new StringBuffer();
		SignSourceSystem signSourceSystem=signSourceForm.getSignSourceSystem();
		if (StringUtils.isBlank(signSourceSystem.getSourceSystemKey())){ // 系統名稱
			warnMessage.append("系統名稱必須填寫!<br />");}
		if (StringUtils.isBlank(signSourceSystem.getDelted())){ // 狀態
			warnMessage.append("狀態必須選取!<br />");}
		
		int i=1;
		for(SignSourceCategory s:signSourceForm.getListSignSourceCategory()){
			if (StringUtils.isBlank(s.getSourceCategoryKey())){ // 類別KEY值
				warnMessage.append("項目 "+i+" 類別KEY值必須填寫!<br />");}
			if (StringUtils.isBlank(s.getDescription())){ // 類別描述
				warnMessage.append("項目 "+i+" 類別描述必須填寫!<br />");}
			if (StringUtils.isBlank(s.getDelted())){ // 狀態
				warnMessage.append("項目 "+i+" 狀態必須選取!<br />");}
			i++;
		}
		return warnMessage.toString();
	}

}
