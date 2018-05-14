package mro.base.sso;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import SessionCount.ServletContextListenerImpl;

import com.inx.base.SsoConstant;
import com.inx.base.bo.MenuHrEmpBO;
import com.inx.base.entity.HrEmp;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.utils.JsonInxUtils;
import com.inx.commons.utils.ServletInxUtils;
import com.inx.ias.sso.filter.ContextPathByPassChecker;
import com.inx.ias.sso.filter.SsoEmployeeTicket;
import com.inx.ias.sso.filter.SsoLoginTicket;
import com.inx.ias.sso.filter.SsoTicket;
import com.inx.ias.sso.filter.TicketUtils;
import com.inx.ias.sso.filter.impl.BaseSsoTicket;
import com.inx.login.auth.bean.LoginAuthResultBean;
import com.inx.web.servlet.filter.ReturnModel;
import com.inx.web.servlet.filter.WebFilterException;
import com.inx.web.servlet.filter.annotation.WebFilterExecutor;
import com.inx.web.servlet.filter.executor.impl.SpringFilterExecutor;
import com.inx.web.servlet.filter.impl.BaseReturnModel;

/**
 *
 * @author Administrator
 */
@WebFilterExecutor
public class LoginFilterSSO extends SpringFilterExecutor {
	
	@Autowired
	private ContextPathByPassChecker contextPathByPassChecker;
	
    public ReturnModel executeFilter(ServletRequest servletRequest, ServletResponse servletResponse) throws WebFilterException,
	IOException, ServletException {
    	
		boolean checkByPassList = contextPathByPassChecker.byPassFilter(servletRequest);
		if (checkByPassList) {
			return new BaseReturnModel();
		}
		
    	HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        LoginAuthResultBean authBean = (LoginAuthResultBean) session.getAttribute(LoginAuthResultBean.getSessionKey());
        
        if (authBean == null || !authBean.isAuthPass()) {
        	try {
	        	String jsonSsoTicket = ServletInxUtils.getCookieValue(req, SsoConstant.ATTR_IAS_SSO_TICKET);
	        	if(StringUtils.isBlank(jsonSsoTicket)){
	        		return goon(req);
	        	}
	        	SsoTicket ssoTicket =JsonInxUtils.readAsObject(jsonSsoTicket, BaseSsoTicket.class);
	        	SsoLoginTicket ssoLoginTicket = TicketUtils.convertLoginTicket(ssoTicket);
	        	SsoEmployeeTicket ssoEmployeeTicket = TicketUtils.convertEmployeeTicket(ssoLoginTicket);
	        	

	            LoginAuthResultBean rtnBean=nonAuthorize(ssoEmployeeTicket.getUserId(),req); //不驗證
	            
	            rtnBean.setFowardUrl(authBean ==null ? "" : authBean.getFowardUrl());
	            session.setAttribute(LoginAuthResultBean.getSessionKey(), rtnBean);
        	} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return new BaseReturnModel();
    }
    
    private LoginAuthResultBean nonAuthorize(String user,HttpServletRequest req) {  //不驗證
    	HttpSession session = req.getSession();
        MenuHrEmpBO menuHrEmpBO = SpringContextUtil.getBean(MenuHrEmpBO.class);
        boolean b=false;
        if(StringUtils.isNotBlank(user)){
	        HrEmp hrEmp = menuHrEmpBO.getHrEmp(user,"");
	        if (hrEmp != null) {
	            b=true;
	        }
        }
        
        if(b){
        	session.setAttribute("user", user);
        	ServletContextListenerImpl.loginCreated(req);
        	return new LoginAuthResultBean(user, true, null);
        }else{
        	return new LoginAuthResultBean(user, false, null);
        }
    }
    private BaseReturnModel goon(HttpServletRequest req ){
    	BaseReturnModel baseReturnModel=new BaseReturnModel();
    	baseReturnModel.setType(ReturnModel.TYPE_REDIRECT);
    	baseReturnModel.put(ReturnModel.ATTR_REDIRECT_URL, req.getContextPath()+"/index.xhtml");
    	return baseReturnModel;
    }
}
