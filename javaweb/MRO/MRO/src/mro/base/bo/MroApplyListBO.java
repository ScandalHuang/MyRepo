package mro.base.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mro.base.System.config.basicType.ItemStatusType;
import mro.base.entity.view.MroApplyList;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.hibernate.RestrictionsUtils;

@Component
public class MroApplyListBO {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}
	
	@Transactional(readOnly = true)
	public MroApplyList getMroApplyList(String applyNum,BigDecimal taskId,
			ItemStatusType itemStatusType){
		List criterions = new ArrayList();
		criterions.add(RestrictionsUtils.eq("applyNum", applyNum));
		criterions.add(RestrictionsUtils.eq("taskId", taskId));
		criterions.add(RestrictionsUtils.in("status", itemStatusType.getValue()));
		
		return (MroApplyList) commonDAO.uniQuery(MroApplyList.class, null, criterions);
	}
}