package mro.base.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mro.base.System.config.basicType.ItemSiteTransferType;
import mro.base.entity.Item;
import mro.base.entity.ItemSiteTransferLog;
import mro.base.entity.SapPlantAttribute;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
public class ItemSiteTransferLogBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = false)
	public void update(List<SapPlantAttribute> sapPlantAttributes,Item item,
			String ApplyKeyId,String lastUpdatedBy,
			ItemSiteTransferType applyCategory,ItemSiteTransferType actionType){
		for (SapPlantAttribute sp : sapPlantAttributes) {
			ItemSiteTransferLog itemSiteTransferLog = new ItemSiteTransferLog();
			itemSiteTransferLog.setItemid(item.getItemid());
			itemSiteTransferLog.setItemnum(item.getItemnum());
			itemSiteTransferLog.setPlantCode(sp.getPlantCode());
			itemSiteTransferLog.setStorageLocation(sp.getStorageLocation());
			itemSiteTransferLog.setMaterialGroup(sp.getMaterialGroup());
			itemSiteTransferLog.setApplyCategory(new BigDecimal(applyCategory.getValue()));
			itemSiteTransferLog.setApplyKeyId(ApplyKeyId);
			itemSiteTransferLog.setCreateBy(lastUpdatedBy);
			itemSiteTransferLog.setCreateDate(new Date(System.currentTimeMillis()));
			itemSiteTransferLog.setActionType(actionType.getValue());// I:新增,U:更新,S:停止
			commonDAO.insertUpdate(itemSiteTransferLog);
		}
	}
}
