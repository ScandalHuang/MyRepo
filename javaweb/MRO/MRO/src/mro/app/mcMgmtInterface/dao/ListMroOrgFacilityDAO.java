package mro.app.mcMgmtInterface.dao;

import org.hibernate.SessionFactory;

import com.inx.commons.dao.FactoryBaseDAO;


public class ListMroOrgFacilityDAO extends FactoryBaseDAO{
	public ListMroOrgFacilityDAO(SessionFactory sesssionFacoty){
		super.setSessionFactory(sesssionFacoty);
	}
	
	public void updateShortName (){
		String sql="update MRO_ORG_FACILITY "
				+ "set SHORT_NAME=(select SHORT_NAME from Hr_Org where dept_No=MRO_ORG_FACILITY.dept_No) "
				+ "where SHORT_NAME is null";
		modifyBySQL(sql);
	}
	
}
