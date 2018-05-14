package mro.app.mcMgmtInterface.bo;

import java.sql.Timestamp;
import java.util.Date;

import mro.app.mcMgmtInterface.vo.LampList;
import mro.base.entity.LampControlLine;
import mro.base.entity.LampControlLineLog;
import mro.base.entity.Person;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;



@Component
@Scope("prototype")
public class ListLampControlBO {
   
    private CommonDAO commonDAO;
    
    @Autowired
    @Qualifier(value="DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory){
    	commonDAO=new CommonDAO(sessionFactory);
    }
	@Transactional(readOnly = false)
	public void  update(LampList lampList,Person person){
		boolean change=false;
		for(int i=0;i<lampList.getListLampControlLine().size();i++){
			LampControlLine preLind=lampList.getOriListLampControlLine().get(i);
			LampControlLine newLind=lampList.getListLampControlLine().get(i);
			System.out.println(preLind.getNeedQty()+","+newLind.getNeedQty());
			if(preLind.getNeedQty().compareTo(newLind.getNeedQty())!=0 ||
					preLind.getNeedDate()!=newLind.getNeedDate()){
				Date date=new Timestamp(System.currentTimeMillis());
				if(newLind.getLineId()==null){
					newLind.setCreateBy(person.getPersonId());
					newLind.setCreateDate(date);
				}
				newLind.setLastUpdate(date);
				newLind.setLastUpdateBy(person.getPersonId());
				commonDAO.insertUpdate(newLind);
				
				LampControlLineLog log=new LampControlLineLog();
				BeanUtils.copyProperties(newLind,log);
				log.setLampControlLine(newLind);
				commonDAO.insertUpdate(log);
				change=true;
			}
		}
		if(change) lampList.copyLineList();
	}
}