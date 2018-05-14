package mro.app.mcMgmtInterface.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mro.app.applyItem.dao.ApplyItemPrDao;
import mro.app.mcMgmtInterface.dao.ApplyInactiveDeptMinLevelDAO;
import mro.app.util.SystemUtils;
import mro.base.bo.LongdescriptionBO;
import mro.base.entity.Invbalances;
import mro.base.entity.InvbalancesInactiveRecode;
import mro.base.entity.Inventory;
import mro.base.entity.Longdescription;

import org.hibernate.SessionFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.SpringContextUtil;


@Component
@Scope("prototype")
public class ApplyInactiveDeptMinLevelBo {

	private ApplyInactiveDeptMinLevelDAO applyInactiveDeptMinLevelDAO;
	private CommonDAO commonDAO;
	private ApplyItemPrDao applyItemPrDao;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
		applyInactiveDeptMinLevelDAO=new ApplyInactiveDeptMinLevelDAO(sessionFactory);
		applyItemPrDao=new ApplyItemPrDao(sessionFactory);
		
	}
	
	@Transactional(readOnly=true)
	public List getInactiveDeptList(){
		return applyInactiveDeptMinLevelDAO.getInactiveDeptList();
	}
	
	@Transactional(readOnly=false)
	public void setInvbalances(Invbalances invbalances,String empno,
			BigDecimal newMinLevel,BigDecimal newSstock,BigDecimal newOriavguseqty,
			String inactiveRemark){
		LongdescriptionBO longdescriptionBO=SpringContextUtil.getBean(LongdescriptionBO.class);
		BigDecimal oldMinLevel=invbalances.getMinlevel();
		BigDecimal oldSstock=invbalances.getSstock();
		BigDecimal oldOriavguseqty=invbalances.getOriavguseqty();
		
		InvbalancesInactiveRecode iir=new InvbalancesInactiveRecode();
//		invbalances.setLastrequestedby(empno);  //下修不更新控管人
//		invbalances.setLastrequestedby2(empno); //下修不更新控管人
		invbalances.setMinlevel(newMinLevel);
		invbalances.setSstock(newSstock);
		invbalances.setOriavguseqty(newOriavguseqty);
		BeanUtils.copyProperties(invbalances,iir);
		iir.setLastupdateDate(new Date(System.currentTimeMillis()));
		iir.setLastupdateBy(empno);
		iir.setOldMinlevel(oldMinLevel);
		iir.setNewMinlevel(newMinLevel);
		iir.setRemark(inactiveRemark);
		iir.setOldSstock(oldSstock);
		iir.setNewSstock(newSstock);
		iir.setOldOriavguseqty(oldOriavguseqty);
		iir.setNewOriavguseqty(newOriavguseqty);
		iir.setLastrequestedby2(invbalances.getLastrequestedby2());
		applyInactiveDeptMinLevelDAO.insertUpdate(iir);
		applyInactiveDeptMinLevelDAO.insertUpdate(invbalances);
		//================================Longdescription=====================================================
		Longdescription longdescription=longdescriptionBO.getLongdescription(invbalances.getInvbalancesid());
		if(longdescription==null){longdescription=new Longdescription();}
		longdescription.setCreateDate(new Date(System.currentTimeMillis()));
		longdescription.setLdkey(invbalances.getInvbalancesid());
		longdescription.setLdownertable("INVBALANCES");
		longdescription.setLdownercol("REMARK");
		longdescription.setLangcode("ZH");
		longdescription.setLdtext(iir.getRemark());
		commonDAO.insertUpdate(longdescription);
		
		if(oldSstock!=null && oldSstock.compareTo(newSstock)==1){  //若有最低安全時，必須歸0
			SystemUtils systemUtils=new SystemUtils(
					new String[]{"itemnum","location"});
			StringBuffer condition=systemUtils.getConditions(
					invbalances.getItemnum(),invbalances.getLocation());
			Inventory invenotry=applyInactiveDeptMinLevelDAO.getInventory(condition.toString());
			invenotry.setSstock(newSstock);
			applyInactiveDeptMinLevelDAO.insertUpdate(invenotry);
		}
	}
}
