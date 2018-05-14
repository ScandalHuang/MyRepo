package mro.base.bo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mro.base.dao.OracleFunctionDAO;
import mro.base.entity.Item;
import mro.base.entity.Prline;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.inx.commons.dao.FunctionDAO;
import com.inx.commons.util.Utility;

@Component
public class FunctionBO {

	private FunctionDAO functionDAO;
	private OracleFunctionDAO oracleFunctionDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		oracleFunctionDAO = new OracleFunctionDAO(sessionFactory);
		functionDAO = new FunctionDAO(sessionFactory);
	}
	@Transactional(readOnly=true)
    public BigDecimal getSquence(String seqName) {
    			
	   return functionDAO.getSquence(seqName);
	}
	@Transactional(readOnly = true)
	public void callProcedure(String procedure) {
		functionDAO.callProcedure(procedure);
	}
	@Transactional(readOnly = true)
	public BigDecimal getSumofcounter(String itemnum, String deptcode) {
		BigDecimal value=oracleFunctionDAO.getSumofcounter(itemnum, deptcode);
		return value==null?new BigDecimal("0"):value;
	}
	@Transactional(readOnly = true)
	public BigDecimal getUnicost(String itemnum) {
		return oracleFunctionDAO.getUnicost(itemnum);
	}
	
	@Transactional(readOnly = true)
	public String getDeptUp(String deptcode, int deptLevel) {
		return StringUtils.defaultString(oracleFunctionDAO.getDeptUp(deptcode, deptLevel));
	}
	
	@Transactional(readOnly = true)
	public List<Item> validateSumofUseCounterByPrline(List<Prline> prlines, String deptcode) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashMap();
		param.put("DEPTCODE", deptcode);
		for(int i=0;i<prlines.size();i++){
			if(condition.length()>0){condition.append(" or ");}
			condition.append("(itemnum=:p"+i+" and GET_SUMOFCONTER (:p"+i+", :DEPTCODE)<>:s"+i+")");
			param.put("p"+i, prlines.get(i).getItemnum());
			param.put("s"+i, Utility.nvlBigDecimal(prlines.get(i).getSumofusecounter(),"0"));
		}
		return oracleFunctionDAO.validateSumofcounter(condition.toString(), param);
	}
	@Transactional(readOnly = true)
	public List<Item> validateSumofIssueCounterByPrline(List<Prline> prlines, String deptcode) {
		StringBuffer condition=new StringBuffer();
		Map param=new HashMap();
		param.put("DEPTCODE", deptcode);
		for(int i=0;i<prlines.size();i++){
			if(condition.length()>0){condition.append(" or ");}
			condition.append("(itemnum=:p"+i+" and GET_SUMOFCONTER (:p"+i+", :DEPTCODE)<>:s"+i+")");
			param.put("p"+i, prlines.get(i).getItemnum());
			param.put("s"+i, Utility.nvlBigDecimal(prlines.get(i).getSumofissuecounter(),"0"));
		}
		return oracleFunctionDAO.validateSumofcounter(condition.toString(), param);
	}
}
