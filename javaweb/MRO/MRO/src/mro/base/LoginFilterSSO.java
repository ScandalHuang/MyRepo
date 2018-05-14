package mro.base;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mro.base.System.config.SystemConfig;

import org.apache.commons.lang3.StringUtils;

import SessionCount.ServletContextListenerImpl;

import com.inx.base.bo.MenuHrEmpBO;
import com.inx.base.entity.HrEmp;
import com.inx.commons.util.SpringContextUtil;
import com.inx.commons.util.Utility;
import com.inx.login.auth.bean.LoginAuthResultBean;
import com.inx.login.auth.util.LDAPAuthorizor;

/**
 *
 * @author Administrator
 */
public class LoginFilterSSO implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        LoginAuthResultBean authBean = (LoginAuthResultBean) session.getAttribute(LoginAuthResultBean.getSessionKey());
        
        if (authBean == null || !authBean.isAuthPass()) {
            String user = req.getParameter("user");
            String password = req.getParameter("password");
            LoginAuthResultBean rtnBean; 
            if( ServletContextListenerImpl.getActiveSessions()>=200 &&  //=====線上人數驗證
            	StringUtils.isNotBlank(user) &&
            	StringUtils.isNotBlank(password) &&
				!ServletContextListenerImpl.validate(user)){
				request.setAttribute("activeSessionError", "目前線上人數已達上線200人，請稍候在登入!!");
				rtnBean = new LoginAuthResultBean(user, false, null);
			}else if(StringUtils.isNotBlank(user)){
	            if(Utility.validateHostName(SystemConfig.PRODUCTION_MAP)){
	            	rtnBean = authorize(user, password,req);  //LDAP
	            }else{
	            	rtnBean =nonAuthorize(user,req); //不驗證
	            }
			}else{
				rtnBean = new LoginAuthResultBean(user, false, null);
			}
            rtnBean.setFowardUrl(authBean ==null ? "" : authBean.getFowardUrl());
            session.setAttribute(LoginAuthResultBean.getSessionKey(), rtnBean);
        }
        
        chain.doFilter(req, response);
        
    }

    private LoginAuthResultBean authorize(String user, String password,HttpServletRequest req) {
    	HttpSession session = req.getSession();
        LoginAuthResultBean rtn ;
        LDAPAuthorizor authorizor = new LDAPAuthorizor();
        try {
            authorizor.authorize(user, password);
            rtn = new LoginAuthResultBean(user, true, null);
            session.setAttribute("user", user);
            ServletContextListenerImpl.loginCreated(req);
        }catch (Exception e) {
            rtn = new LoginAuthResultBean(user, false, null);
            req.setAttribute("exceptionMessage", e.getMessage());
        }
        return rtn;
    }

    @Override
    public void destroy() {
        
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

}
