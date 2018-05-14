package mro.app.buyerMgmtInteface.service.Impl;

import mro.app.buyerMgmtInteface.bo.ListAlnvalueCommonBO;
import mro.app.buyerMgmtInteface.form.AlnvalueCommonForm;
import mro.app.buyerMgmtInteface.service.ListAlnvalueCommonInterface;
import mro.base.entity.Assetattribute;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;

public class ListAlnvalueCommonImpl implements ListAlnvalueCommonInterface {

	@Override
	public AlnvalueCommonForm onSearch(AlnvalueCommonForm alnvalueCommonForm) {
		ListAlnvalueCommonBO listAlnvalueCommonBO = SpringContextUtil.getBean(ListAlnvalueCommonBO.class);
		alnvalueCommonForm.intial();
		if(StringUtils.isBlank(alnvalueCommonForm.getItemType())){
			alnvalueCommonForm.setAssetattribute(null);
		}else{
			Assetattribute assetattribute=Utility.nvlEntity(
					alnvalueCommonForm.getAssetattribute(), Assetattribute.class);
			alnvalueCommonForm.setListAlndomainCommon(listAlnvalueCommonBO.getAlndomainCommonList(
					new String[]{"itemtype","assetattrid"}, 
					alnvalueCommonForm.getItemType(),assetattribute.getAssetattrid()));
		}
		return alnvalueCommonForm;
	}

	@Override
	public AlnvalueCommonForm onDelete(AlnvalueCommonForm alnvalueCommonForm) {
		ListAlnvalueCommonBO listAlnvalueCommonBO = SpringContextUtil.getBean(ListAlnvalueCommonBO.class);
		listAlnvalueCommonBO.delete(alnvalueCommonForm.getDeleteAlndomainCommon());
		return onSearch(alnvalueCommonForm);
	}

}
