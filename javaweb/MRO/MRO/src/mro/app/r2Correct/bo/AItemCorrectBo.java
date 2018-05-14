package mro.app.r2Correct.bo;

import java.util.List;
import java.util.Map;

import mro.app.applyItem.bo.ApplyItemBo;
import mro.app.applyItem.service.ItemInterface;
import mro.app.applyItem.service.impl.ItemImpl;
import mro.app.r2Correct.dao.AItemCorrectDao;
import mro.app.r2Correct.vo.AItemCorrect;
import mro.base.entity.AItem;
import mro.form.ItemForm;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.SpringContextUtil;

@Component
@Scope("prototype")
public class AItemCorrectBo {

	private AItemCorrectDao aItemCorrectDao;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		aItemCorrectDao = new AItemCorrectDao(sessionFactory);
	}

	@Transactional(readOnly = false)
	public void update(List<AItemCorrect> aItemCorrects) {
		for (AItemCorrect aItemCorrect : aItemCorrects) {
			StringBuffer errorMsg = new StringBuffer();
			if (StringUtils.isNotBlank(aItemCorrect.getDelete())
					&& aItemCorrect.getDelete().toUpperCase().equals("Y")) {
				errorMsg.append(deletAItem(aItemCorrect)); // 刪除申請單
			} else {
				errorMsg.append(updateChangeBy(aItemCorrect)); // 更新異動人員
				errorMsg.append(updateOrganizationCode(aItemCorrect)); // 更新申請廠區
				errorMsg.append(updateClassstructureid(aItemCorrect)); // 更新類別結構
			}
			if(!aItemCorrect.isExecuteFlag()){
				errorMsg.append("未執行任何處理程序!"); //未執行任何處理程序
			}
			aItemCorrect.setErrorMsg(errorMsg.toString().replaceAll("!",
					"!<br />"));
			aItemCorrect.setUpdateFlag(StringUtils.isNotBlank(errorMsg) ? false
					: true);
		}
	}

	@Transactional(readOnly = false)
	public String updateChangeBy(AItemCorrect aItemCorrect) {
		Map param = new HashedMap();
		if (StringUtils.isNotBlank(aItemCorrect.getChangeby())) {
			param.put("itemnum", aItemCorrect.getItemnum());
			param.put("changeby", aItemCorrect.getChangeby());
			if (aItemCorrectDao.updateChangeby(param) == 0) {
				return "新異動人員更新失敗!";
			}
			aItemCorrect.setExecuteFlag(true);  //曾經處理過
		}
		return "";
	}
	
	@Transactional(readOnly = false)
	public String updateOrganizationCode(AItemCorrect aItemCorrect) {
		Map param = new HashedMap();
		if (StringUtils.isNotBlank(aItemCorrect.getOrganizationCode())) {
			param.put("itemnum", aItemCorrect.getItemnum());
			param.put("organizationCode", aItemCorrect.getOrganizationCode().toUpperCase());
			if (aItemCorrectDao.updateOrganizationCode(param) == 0) {
				return "新申請廠區更新失敗!";
			}
			aItemCorrect.setExecuteFlag(true);  //曾經處理過
		}
		return "";
	}
	@Transactional(readOnly = false)
	public String updateClassstructureid(AItemCorrect aItemCorrect) {
		Map param = new HashedMap();
		ItemInterface itemImpl = new ItemImpl();
		if (StringUtils.isNotBlank(aItemCorrect.getClassstructureid())) {
			ItemForm itemForm = new ItemForm();
			itemImpl.setClassstructurePhase(itemForm,
					aItemCorrect.getClassstructureid());
			param.put("itemnum", aItemCorrect.getItemnum());
			param.put("classstructureid", aItemCorrect.getClassstructureid());
			param.put(
					"commoditygroup",
					itemForm.getsStructs1().substring(0,
							itemForm.getsStructs1().indexOf(" (")));
			param.put(
					"commodity",
					itemForm.getsStructs2().substring(0,
							itemForm.getsStructs2().indexOf(" (")));
			if (aItemCorrectDao.updateClassstructureid(param) > 0) {
				aItemCorrectDao.updateItemMapping(param);
			} else {
				return "新類別結構更新失敗!";
			}
			aItemCorrect.setExecuteFlag(true);  //曾經處理過

		}
		return "";
	}

	@Transactional(readOnly = false)
	public String deletAItem(AItemCorrect aItemCorrect) {
		Map param = new HashedMap();
		if (StringUtils.isNotBlank(aItemCorrect.getDelete())
				&& aItemCorrect.getDelete().toUpperCase().equals("Y")) {
			param.put("itemnum", aItemCorrect.getItemnum());
			AItem aItem = aItemCorrectDao.getAIteList(param);
			if (aItem != null) {
				ApplyItemBo applyItemBo = SpringContextUtil
						.getBean(ApplyItemBo.class);
				aItemCorrectDao.deleteItemMapping(param);
				applyItemBo.onApplyDelete(aItem);
			} else {
				return "刪除申請單失敗!";
			}
			aItemCorrect.setExecuteFlag(true);  //曾經處理過
		}
		return "";
	}
}
