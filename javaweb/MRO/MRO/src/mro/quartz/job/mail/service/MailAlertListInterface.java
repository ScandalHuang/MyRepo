package mro.quartz.job.mail.service;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mro.base.entity.MailConfig;
import mro.quartz.job.mail.vo.MailVO;

public interface MailAlertListInterface {
	
	public void sendMailMain(String appId)
			throws UnknownHostException;

	public MailVO setMailVOByLine(MailConfig mailConfig, Map map);

	public MailVO setMailVOByAll(MailConfig mailConfig, List<Map> list);

	public String replace(String mail, Entry entry);

	public Map setMailVOByLineGroup(MailConfig mailConfig, List<Map> map);

	public MailVO mergeMailVO(MailVO o_mailVO, MailVO s_mailVO);
}
