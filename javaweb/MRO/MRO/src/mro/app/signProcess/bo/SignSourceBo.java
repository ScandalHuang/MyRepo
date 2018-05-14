package mro.app.signProcess.bo;

import java.util.Date;

import mro.app.signProcess.form.SignSourceForm;
import mro.base.entity.SignSourceCategory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;

@Component
@Scope("prototype")
public class SignSourceBo {
	private CommonDAO commonDAO;
	
	@Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
    }
	
	@Transactional(readOnly=false)
	public void onSave(SignSourceForm signSourceForm){
		signSourceForm.getSignSourceSystem().setSourceSystemValue(
				signSourceForm.getSignSourceSystem().getSourceSystemKey());
		signSourceForm.getSignSourceSystem().setLastUpdate(new Date(System.currentTimeMillis()));
		commonDAO.insertUpdate(signSourceForm.getSignSourceSystem());
		
		//==================================刪除===========================================
		for(SignSourceCategory s:signSourceForm.getDeleteSignSourceCategoryList()){
			if(s.getSourceCategoryId()!=null){
				commonDAO.delete(s);
			}
		}
		//===================================更新==========================================
		for(SignSourceCategory s:signSourceForm.getListSignSourceCategory()){
			s.setSignSourceSystem(signSourceForm.getSignSourceSystem());
			s.setSourceCategoryValue(s.getSourceCategoryKey());
			s.setLastUpdate(new Date(System.currentTimeMillis()));
			commonDAO.insertUpdate(s);
		}
	}
}
