package mro.app.bbs.bo;

import java.sql.Timestamp;
import java.util.Map;
import java.util.stream.Stream;






import mro.app.bbs.form.BbsForm;
import mro.app.commonview.services.FileUploadInterfaces;
import mro.app.commonview.services.Impl.FileUploadImpl;
import mro.base.System.config.basicType.FileCategory;
import mro.base.System.config.jsf.SystemConfigBean;
import mro.base.entity.BulletinboardSite;
import mro.base.entity.LocationSiteMap;
import mro.base.loginInfo.jsf.LoginInfoBean;
import mro.form.PrForm;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;






import com.inx.commons.dao.CommonDAO;
import com.inx.commons.util.JsfContextUtil;

@Component
@Scope("prototype")
public class BbsBo {

	private CommonDAO commonDAO;

	@Autowired
	@Qualifier(value = "DEFAULT_SESSION_FACTORY")
	public void initDAO(SessionFactory sessionFactory) {
		commonDAO = new CommonDAO(sessionFactory);
	}

	@Transactional(readOnly = false)
	public void update(BbsForm bbsForm) {
		bbsForm.getBulletinboard().setPostdate(new Timestamp(System.currentTimeMillis()));
		if(bbsForm.getBulletinboard().getBulletinboardid()==null){
			bbsForm.getBulletinboard().setPostby(bbsForm.getLoginPerson().getPersonId());
		}
		commonDAO.insertUpdate(bbsForm.getBulletinboard());
		//========刪除該USER預設有的SITE================
		LoginInfoBean bean=JsfContextUtil.getBean(LoginInfoBean.class.getSimpleName());
		Map<String,LocationSiteMap> userLSMap=bean.getUserLSMap();
		bbsForm.getBulletinboardSites().stream()
			.filter(l->userLSMap.get(l.getLocationSiteMap().getLocationSite())!=null)
			.forEach(l->commonDAO.delete(l));
		//========重新新增===============
		Stream.of(bbsForm.getsLocationSiteMap()).forEach(s->{
			commonDAO.insertUpdate(new BulletinboardSite(null,bbsForm.getBulletinboard(),s));
		});
		
	}

	@Transactional(readOnly = false)
	public void delete(BbsForm bbsForm) {
		FileUploadInterfaces fileUploadInterfaces=new FileUploadImpl();
		fileUploadInterfaces.fileDelete(
				bbsForm.getBulletinboard().getBulletinboardid().toString(),
				FileCategory.BBS_ATTACHMENT,"");
		commonDAO.delete(bbsForm.getBulletinboardSites());
		commonDAO.delete(bbsForm.getBulletinboard());
	}

}
