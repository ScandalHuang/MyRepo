package mro.base.loginFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mro.base.System.config.SystemConfig;

import com.inx.commons.util.Utility;
import com.inx.login.auth.service.LoginAttributeInterface;

public class LoginAttribute implements LoginAttributeInterface {
	
	public LoginAttribute(){
		
	}
	@Override
	public List<String> getAuthorizedURL() {
		// TODO Auto-generated method stub
		return new ArrayList<String>(){{
		add("/faces/signView/signPreView.xhtml"); ////預覽簽核
		add("/faces/signView/signHistoryMain.xhtml"); ////簽核歷程
		add("/faces/reportList/reportList.xhtml");//Report
		add("/test.xhtml");//測試頁面
		}};
	}

	@Override
	public long getMaxLogin() {
		// TODO Auto-generated method stub
		return 200;
	}

	@Override
	public List<String> getNonFilterURL() {
		// TODO Auto-generated method stub
		return new ArrayList<String>(){{
			add("/faces/CommonView/imageView.xhtml");//料號圖片預覽
			add("/error.xhtml");//錯誤
			}};
	}

	@Override
	public List<String> getNonMaxLoginAccount() {
		// TODO Auto-generated method stub
		return new ArrayList<String>(){{
			add("HONGJIE.WU");
			}};
	}
	@Override
	public List<String> deputyLoginGroup() {
		// TODO Auto-generated method stub
		return Arrays.asList("MC","Admin");
	}
	@Override
	public boolean validateLogin() {
		// TODO Auto-generated method stub
		return Utility.validateHostName(SystemConfig.PRODUCTION_MAP);
	}
	@Override
	public List<String> defaultGroup() {
		// TODO Auto-generated method stub
		return Arrays.asList("Guest");
	}
}
