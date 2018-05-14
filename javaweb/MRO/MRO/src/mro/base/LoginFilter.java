package mro.base;

import mro.base.loginFilter.LoginAttribute;

import com.inx.base.filter.MenuLoginFilterBase;
import com.inx.login.auth.service.LoginAttributeInterface;

/**
 *
 * @author Administrator
 */
public class LoginFilter extends MenuLoginFilterBase {

	@Override
	public LoginAttributeInterface getLoginAttributeBean() {
		// TODO Auto-generated method stub
		return new LoginAttribute();
	}

}
