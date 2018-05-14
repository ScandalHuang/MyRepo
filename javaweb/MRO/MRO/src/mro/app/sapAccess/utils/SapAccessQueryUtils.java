package mro.app.sapAccess.utils;

import mro.app.sapAccess.bo.SapAccessQueryBo;
import mro.base.bo.ItemSiteBO;
import mro.base.entity.ItemSite;
import mro.form.SapForm;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class SapAccessQueryUtils {

	public static String vaildate(SapForm sapForm){
		StringBuffer warnInfo=new StringBuffer();
		SapAccessQueryBo sapAccessQueryBo=SpringContextUtil.getBean(SapAccessQueryBo.class);
		ItemSiteBO itemSiteBO=SpringContextUtil.getBean(ItemSiteBO.class);
		if(StringUtils.isBlank(sapForm.getAddItemnum())){
			warnInfo.append("請選取料號!<br/>");
		}else if(StringUtils.isBlank(sapForm.getAddType())){
			warnInfo.append("請選取ACTION!<br/>");
		}else if(sapForm.getAddType().equals("bySite") && sapAccessQueryBo.getEnableItemList(
				new String[]{"a.itemnum","b.locationSite"},
				sapForm.getAddItemnum(),sapForm.getAddSite())==0){
			warnInfo.append("此料號在"+sapForm.getAddSite()+"未生效!<br/>");
		}else if(sapForm.getAddType().equals("byPlant")){
				ItemSite itemSite=itemSiteBO.getItemSiteByOrgCode(
						sapForm.getAddItemnum(),sapForm.getAddPlantCode());
				ItemSite nItemSite=itemSiteBO.getItemSiteByNonValueP(
						sapForm.getAddItemnum(),sapForm.getAddPlantCode());
				if(itemSite==null && nItemSite==null){
					warnInfo.append("此料號在"+sapForm.getAddPlantCode()+"未生效!");
				}
			}
		return warnInfo.toString();
	}
}
