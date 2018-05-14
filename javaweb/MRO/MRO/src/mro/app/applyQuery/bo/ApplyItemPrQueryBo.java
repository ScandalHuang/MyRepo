package mro.app.applyQuery.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import mro.app.applyQuery.dao.ApplyItemPrQuerynDao;
import mro.app.commonview.services.Impl.PrlineImpl;
import mro.base.System.config.basicType.PrType;
import mro.base.System.config.basicType.SignStatus;
import mro.base.entity.Person;
import mro.base.entity.Pr;
import mro.base.entity.Prline;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.util.hibernate.ActiveFlag;

@Component
@Scope("prototype")
public class ApplyItemPrQueryBo {

	private ApplyItemPrQuerynDao applyItemPrQuerynDao;
	
	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
    public void initDAO(SessionFactory sessionFactory) {
		applyItemPrQuerynDao=new ApplyItemPrQuerynDao(sessionFactory);
    }
	
    @Transactional(readOnly=false)
    public String mroPrToErp(Person person,Pr[] prs){
    	StringBuffer str=new StringBuffer();
    	for(Pr p:prs){
    		boolean reorder=false;
    		if(p.getPrtype().equals(PrType.R1REORDER.name()) || 
    				p.getPrtype().equals(PrType.R2REORDER.name())){
    			reorder=true;
    		}
    		PrType prType=PrType.findValue(p.getPrtype());
    		int i=applyItemPrQuerynDao.mroPrToErp(person, p.getPrnum(),
    				prType.getPrtypeName(),prType.getDeliveryDay(),reorder);
    		if(i>0){
    			updateEPFlag(p);
    			applyItemPrQuerynDao.updatePrLine(p.getPrnum());
    			str.append(p.getPrnum()+"傳送至成功!<br />");
    		}else{
    			str.append(p.getPrnum()+"傳送至失敗!<br />");
    		}
    	}
    	
    	return str.toString();
    }

	@Transactional(readOnly = true)
	public List getApplyPrlineList(Pr[] prs,SignStatus status){
		StringBuffer str=new StringBuffer();
		if(prs!=null){
			str.append("and ( p.prnum='-1' ");
			for(Pr p:prs){
				str.append(" or p.prnum='"+p.getPrnum()+"'");
			}
			str.append(") ");
		}
			str.append(" and prl.LINE_CLOSED_CODE='"+status+"'");
		return applyItemPrQuerynDao.getApplyPrlineList(str.toString());
	}
    @Transactional(readOnly=false)
    public void updateEPFlag(Pr pr){
    	pr.setTransferFlag(ActiveFlag.Y.name());
    	pr.setTransferDate(new Date(System.currentTimeMillis()));
    	applyItemPrQuerynDao.insertUpdate(pr);
    }
    @Transactional(readOnly=false)
    public void deletePrline(Prline prline){
    	applyItemPrQuerynDao.delete(prline);
    }
    @Transactional(readOnly=false)
    public void updatePrline(Pr pr,Prline prline){
    	PrlineImpl prlineImpl=new PrlineImpl();
    	BigDecimal oriLineCost=prline.getLinecost();
    	prlineImpl.newminlevel(pr,prline);
		pr.setTotalbasecost2(pr.getTotalbasecost2().subtract(
				oriLineCost.subtract(prline.getLinecost())
				));
		applyItemPrQuerynDao.insertUpdate(pr);
		applyItemPrQuerynDao.insertUpdate(prline);
    }
    
    @Transactional(readOnly=false)
	public void updateEPFlag(Pr[] prs){
    	Stream.of(prs).forEach(p->{
    		this.updateEPFlag(p);
    	});
    }
    
    @Transactional(readOnly=false)
	public void updatePr(Pr pr,List<Prline> prlines,boolean transferFlag){
		pr.setTransferFlag(transferFlag?null:ActiveFlag.Y.name());
		pr.setTransferDate(null);
		applyItemPrQuerynDao.insertUpdate(pr);
		prlines.stream().forEach(l->{
			l.setEpInterfaceId(null);
			l.setIepInterfaceId(null);
			l.setErpPo(null);
			l.setErpBuyerReturnNote(null);
			l.setErpPoBuyerEmpNo(null);
			l.setErpPoline(null);
			l.setErpPoLineNum(null);
			l.setErpPoStatus(null);
			l.setErpPrStatus(null);
			l.setErpPrline(null);
			l.setErpPrnum(null);
			l.setPoVendorId(null);
			l.setPoPromisedDate(null);
			l.setErpPrLineNum(null);
			l.setEpmallCreateDate(null);
			l.setErpPrBuyerEmpNo(null);
			l.setErpPrCancelDate(null);
			l.setEpNum(null);
			l.setTransferMsg(null);
			l.setErpPrStatusDescription(null);
		});
		applyItemPrQuerynDao.insertUpdate(prlines);
		
	}
}
