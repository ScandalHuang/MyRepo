package mro.quartz.job.mail.service.Impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mro.base.bo.MailConfigBO;
import mro.base.entity.MailConfig;
import mro.quartz.job.mail.Utils.MailUtils;
import mro.quartz.job.mail.bo.MailAlertListBo;
import mro.quartz.job.mail.service.MailAlertListInterface;
import mro.quartz.job.mail.vo.MailVO;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.SpringContextUtil;

public class MailAlertListImpl implements MailAlertListInterface {

	@Override
	public void sendMailMain(String appId) {
		MailConfigBO bo=SpringContextUtil.getBean(MailConfigBO.class);
		MailConfig mailConfig=bo.getMailConfig(appId);
		if(mailConfig==null) return;
		
		MailAlertListBo mailAlertListBo=SpringContextUtil.getBean(MailAlertListBo.class);
		List<Map> list=mailAlertListBo.getSQLList(mailConfig.getSelectStatement());
		if(list!=null && list.size()>0){
			if(mailConfig.getPerLineFlag().equals("Y")){  //by line send mail
				for(Map map:list){
						MailUtils.sendMail(setMailVOByLine(mailConfig, map),null);
				}
			}else if(mailConfig.getPerLineFlag().equals("N")){  //send maill ALL
	//			if(StringUtils.isNotBlank(mailAlert.getGroupValue())){
					Map<String,MailVO> groupMap=setMailVOByLineGroup(mailConfig, list);
					Iterator iter= (Iterator) groupMap.entrySet().iterator();
					while(iter.hasNext()){
						Entry entry=(Entry) iter.next();
						MailUtils.sendMail((MailVO) entry.getValue(),null);
					}
	//			}else{
	//				MailUtils.sendMail(setMailVOByAll(mailAlert, list));
	//			}
			}
		}
		
	}

	@Override
	public MailVO setMailVOByAll(MailConfig mailConfig, List<Map> list) {
		String contentTable="<br><br>";
		MailVO mailVO=new MailVO();
		if(list.size()>0){
			contentTable=contentTable+"<table "
					+ "style=border-collapse: collapse;border: 1px solid black;>";
		}
		int i=0;
		for(Map map:list){
			//================================表頭=================================
			if(i==0){
				Iterator iterColumn=(Iterator) map.entrySet().iterator();
				contentTable=contentTable+"<tr bgcolor=#f0e68c>";
				while (iterColumn.hasNext()) { 
					Entry entry=(Entry) iterColumn.next();
					contentTable=contentTable+"<td style=border: 1px solid black;>"+entry.getKey()+"</td>";
				}
				contentTable=contentTable+"</tr>";
			}
			
			//===============================表身===================================
			contentTable=contentTable+"<tr>";
			Iterator iter=(Iterator) map.entrySet().iterator();
			while (iter.hasNext()) { 
				Entry entry=(Entry) iter.next();
				String value=entry.getValue()!=null?entry.getValue().toString():"";
				contentTable=contentTable+"<td style=border: 1px solid black;>"+value+"</td>";
			}
			contentTable=contentTable+"</tr>";
			
			i++;
		}		
		if(list.size()>0){
			contentTable=contentTable+"</table>";
		}
		
		mailVO.setSubject(mailConfig.getAlertSubject());
		mailVO.setContent("<pre>"+mailConfig.getAlertHeader()+"</pre>"+contentTable);
		mailVO.setMailTo(MailUtils.mailToList(mailConfig.getNotifyEmail()));
		mailVO.setMailCC(MailUtils.mailToList(mailConfig.getCcEmail()));

		return mailVO;
	}
	@Override
	public Map setMailVOByLineGroup(MailConfig mailConfig,List<Map> list) {
		Map<String,MailVO> groupMap=new HashMap<>();
		for(Map map:list){
			String value="";  //群組資料
			Iterator iter= (Iterator) map.entrySet().iterator();
			while(iter.hasNext()){
				Entry entry=(Entry) iter.next();
				value+=replace(mailConfig.getGroupValue(), entry);
			}
			MailVO mailVO=setMailVOByLine(mailConfig, map);
			if(groupMap.get(value)!=null){
				mailVO=mergeMailVO(mailVO,groupMap.get(value));
			}
			groupMap.put(value, mailVO);
		}
		
		return groupMap;
	}
	
	@Override
	public MailVO setMailVOByLine(MailConfig mailConfig,Map map) {
		MailVO mailVO=new MailVO();
		mailVO.setSubject(mailConfig.getAlertSubject());
		mailVO.setContent(mailConfig.getAlertHeader());
		String to=mailConfig.getNotifyEmail();
		String cc=mailConfig.getCcEmail();
		
		Iterator iter=(Iterator) map.entrySet().iterator();
		while (iter.hasNext()) { 
			Entry entry=(Entry) iter.next();
			mailVO.setSubject(replace(mailVO.getSubject(), entry));
			mailVO.setContent(replace(mailVO.getContent(), entry));
			to=replace(to, entry);
			cc=replace(cc, entry);
		}
		mailVO.setContent(mailVO.getContent());
		mailVO.setMailTo(MailUtils.mailToList(to));
		mailVO.setMailCC(MailUtils.mailToList(cc));
		return mailVO;
	}
	@Override
	public MailVO mergeMailVO(MailVO s_mailVO,MailVO o_mailVO) {
		o_mailVO.getMailTo().addAll(s_mailVO.getMailTo());
		o_mailVO.getMailCC().addAll(s_mailVO.getMailCC());
		
		//=======================內文 reapeat====================================
		if(s_mailVO.getContent().indexOf("<TBODY>")!=-1 &&
		   s_mailVO.getContent().indexOf("</TBODY>")!=-1){
			String content=o_mailVO.getContent().substring(0,o_mailVO.getContent().lastIndexOf("</TBODY>"));
			content+=s_mailVO.getContent().substring(
					s_mailVO.getContent().indexOf("<TBODY>")+8,
					s_mailVO.getContent().indexOf("</TBODY>"));
			content+=o_mailVO.getContent().substring(o_mailVO.getContent().lastIndexOf("</TBODY>"));
			o_mailVO.setContent(content);
		}
		return o_mailVO;
	}
	@Override
	public String replace(String mail,Entry entry) {
		String mailTemp=mail;
		if(StringUtils.isNotBlank(mail)){
			mailTemp=mail.toUpperCase();
			String key=":"+entry.getKey().toString().toUpperCase();
			String value=entry.getValue()!=null?entry.getValue().toString():"";
			if(mailTemp.toUpperCase().indexOf(key)!=-1){
				mailTemp=mailTemp.toUpperCase().replaceAll(key, value);
			}
		}
		return mailTemp;
	}
}
