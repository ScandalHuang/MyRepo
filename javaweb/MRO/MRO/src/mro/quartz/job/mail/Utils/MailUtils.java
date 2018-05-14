package mro.quartz.job.mail.Utils;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mro.app.util.MessageSender;
import mro.base.System.config.SystemConfig;
import mro.quartz.job.mail.vo.MailVO;

import org.apache.commons.lang3.StringUtils;

import com.inx.commons.util.Utility;

public class MailUtils {
	public static void sendMail(MailVO mailVO, File file) {
		try {
			// =====================正式機在寄信通知=================================
			if (Utility.validateHostName(SystemConfig.PRODUCTION_MAP)) {
				Transport.send(setMail(mailVO, file));
			}
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("error.unexpected");
		}
	}

	public static void sendMailShowException(MailVO mailVO, File file)
			throws MessagingException, UnknownHostException {
		// =====================正式機在寄信通知=================================
		if (Utility.validateHostName(SystemConfig.PRODUCTION_MAP)) {
			Transport.send(setMail(mailVO, file));
		}
	}

	public static MimeMessage setMail(MailVO mailVO, File file)
			throws MessagingException {
		MessageSender messageSender=new MessageSender();
		String mailhost = messageSender.getMailhost();
		String from = StringUtils.isBlank(mailVO.getMailFrom()) ? messageSender.getFrom()
				: mailVO.getMailFrom();
		StringBuffer body = new StringBuffer();
		body.append(mailVO.getContent() + "<br>");

		Properties props = System.getProperties();
		if (mailhost != null) {
			props.put("mail.smtp.host", mailhost);
		}
		Session session = Session.getInstance(props, null);
		session.setDebug(false);
		MimeMessage msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(from));

		Map map = new HashMap<>();
		for (String mailTo : mailVO.getMailTo()) {
			if (map.get(mailTo) == null) {
				msg.addRecipients(Message.RecipientType.TO, mailTo);
				map.put(mailTo, mailTo);
			}
		}
		map.clear();
		for (String mailCC : mailVO.getMailCC()) {
			if (map.get(mailCC) == null) {
				System.out.println("mailcc:" + mailCC);
				msg.addRecipients(Message.RecipientType.CC, mailCC);
				map.put(mailCC, mailCC);
			}
		}
		map.clear();
		for (String mailBCC : mailVO.getMailBCC()) {
			if (map.get(mailBCC) == null) {
				System.out.println("mailBCC:" + mailBCC);
				msg.addRecipients(Message.RecipientType.BCC, mailBCC);
				map.put(mailBCC, mailBCC);
			}
		}
		msg.setSubject(mailVO.getSubject(), "big5");

		// 把所有MimeBodyPart加進Multipart，所以如果有第二個附加檔，應該要有另一MultiBodyPart
		Multipart mp = new MimeMultipart();
		// 設定本文區的內容
		MimeBodyPart mbp1 = new MimeBodyPart();
		mbp1.setContent(body.toString(), "text/html; charset=utf-8");
		mp.addBodyPart(mbp1);
		// 設定附加檔
		if (file != null) {
			MimeBodyPart mbp2 = new MimeBodyPart();
			FileDataSource fds = new FileDataSource(file);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(file.getName()); // 附加檔在mail接收時，出現的檔案名稱
			mp.addBodyPart(mbp2);
		}
		msg.setContent(mp);

		msg.setSentDate(new Date());

		return msg;
	}

	public static List<String> mailToList(String mailTo) {
		MessageSender messageSender=new MessageSender();
		List list = new ArrayList<String>();
		if (StringUtils.isNotBlank(mailTo)) {
			for (String m : mailTo.split(";")) {
				if (StringUtils.isNotBlank(m)) {
					if (m.indexOf("@") == -1) {
						list.add(m + messageSender.getDomain());
					} else {
						list.add(m);
					}
				}
			}
		}
		return list;
	}
}
