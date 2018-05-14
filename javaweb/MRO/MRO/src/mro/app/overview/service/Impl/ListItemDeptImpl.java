package mro.app.overview.service.Impl;

import mro.app.commonview.bo.ListItemCommonBO;
import mro.app.overview.bo.ListItemDeptBO;
import mro.app.overview.form.ItemDeptForm;
import mro.app.overview.service.ListItemDeptInterface;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class ListItemDeptImpl implements ListItemDeptInterface {

	@Override
	public ItemDeptForm onSearch(ItemDeptForm itemDeptForm) {
		ListItemDeptBO listItemDeptBO = SpringContextUtil.getBean(ListItemDeptBO.class);
		itemDeptForm.intial();
		itemDeptForm.setListItemDept(listItemDeptBO.getItemDeptList(
				new String[]{"deptcode"}, 
				itemDeptForm.getDeptCode()));
		return itemDeptForm;
	}

	@Override
	public ItemDeptForm onDelete(ItemDeptForm itemDeptForm){
		ListItemDeptBO listItemDeptBO = SpringContextUtil.getBean(ListItemDeptBO.class);
		listItemDeptBO.delete(itemDeptForm.getDeleteItemDept());
		return onSearch(itemDeptForm);
	}


	@Override
	public ItemDeptForm onInsert(String empNO, String deptCode,
			ItemDeptForm itemDeptForm) {
		ListItemDeptBO listItemDeptBO = SpringContextUtil.getBean(ListItemDeptBO.class);
		listItemDeptBO.insert(empNO, deptCode, itemDeptForm.getItemnum());
		return onSearch(itemDeptForm);
	}

	@Override
	public String validate(ItemDeptForm itemDeptForm) {
		StringBuffer warnMessage=new StringBuffer();
		ListItemCommonBO listItemCommonBO=SpringContextUtil.getBean(ListItemCommonBO.class);
		if (StringUtils.isBlank(itemDeptForm.getItemnum())){
			warnMessage.append("請填寫料號!<br />");
		}else if(listItemCommonBO.getItem(itemDeptForm.getItemnum())==null){
			warnMessage.append(itemDeptForm.getItemnum()+"此料號不存在!<br />");
		}
		return warnMessage.toString();
	}

}
