package mro.app.signProcess.bo;

import java.util.Date;

import mro.app.signProcess.form.SignProcessForm;
import mro.base.entity.SignProcessList;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;


@Component
@Scope("prototype")
public class SignProcessAppBo {
	
	private CommonDAO commonDAO;
	
	@Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
    	commonDAO=new CommonDAO(sessionFactory);
    }
	@Transactional(readOnly=false)
	public void onSave(SignProcessForm signProcessForm,String empno){
		Date date=new Date(System.currentTimeMillis());

		if(signProcessForm.getSignProcess().getProcessId()==null){  //第一次儲存
			signProcessForm.getSignProcess().setCreateBy(empno);  //申請人
			signProcessForm.getSignProcess().setCreateDate(date);  //申請時間
		}
		
		signProcessForm.getSignProcess().setChangeBy(empno); //異動人
		signProcessForm.getSignProcess().setChangeDate(date); //異動時間
		
		for(SignProcessList s:signProcessForm.getDeleteSignProcessList()){
			commonDAO.delete(s);
		}
		
		commonDAO.insertUpdate(signProcessForm.getSignProcess());
		for(SignProcessList s:signProcessForm.getSignProcessList()){
			s.setSignProcess(signProcessForm.getSignProcess());
			if(s.getSignSequence().intValue()==1){
				s.setSignEmpNo(null);
				s.setSignCategory(null);
			}else{
				if (!s.getSignCategory().equals("APPOINT")){
					s.setSignEmpNo(null);
					s.setSignDeptNo(null);
				}
				if (!s.getSignCategory().equals("CONTINUE")){
					s.setSignLevel(null);
				}
				if (!s.getSignCategory().equals("FROM_SYSTEM") && !s.getSignCategory().equals("VALIDATE")){
					s.setSignValidateFunction(null);
				}
				if (!s.getSignCategory().equals("VALIDATE")){
					s.setSignValidateFalse(null);
					s.setSignValidateTrue(null);
				}
			}
			commonDAO.insertUpdate(s);
		}
	}
}
