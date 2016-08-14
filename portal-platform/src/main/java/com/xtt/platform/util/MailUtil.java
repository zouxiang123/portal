package com.xtt.platform.util;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.xtt.platform.util.config.PropertiesUtil;

/**
 * 发送邮件工具类
 * @author zhuss
 *
 */
public class MailUtil {
    
    private static Logger log = LoggerFactory.getLogger(MailUtil.class);

	/**
	 * 发送HTML文件
	 * @return
	 */
	public static void sendHTMLMail(String toEmail,String subject,String text){
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();

        try
        {
            Properties props = PropertiesUtil.loadJarProperties("/config/config.properties");
    		// 设定mail server
    		senderImpl.setHost(props.get("mail.host").toString());
    		senderImpl.setPort(Integer.parseInt(props.get("mail.port").toString()));
    		
    		// 建立邮件消息,发送简单邮件和html邮件的区别，
    		//MimeMessage是处理JavaMail邮件常用的顺手组件之一。它可以让你摆脱繁复的javax.mail.internetAPI类
    		MimeMessage mailMessage = senderImpl.createMimeMessage();
    		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
			//寄件人
			messageHelper.setFrom(props.get("mail.from").toString());
			String[] toMails = toEmail.split(","); 
			if (toMails.length > 1) {
                InternetAddress[] ias = new InternetAddress[toMails.length];
			    for (int i = 0; i < toMails.length; i++)
                {
			        ias[i] = new InternetAddress(toMails[i]);
                }
	            messageHelper.setTo(ias);
			} else {
                messageHelper.setTo(toEmail);
			}
			messageHelper.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(text, "text/html; charset=UTF-8");
			mainPart.addBodyPart(html);
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);
			
			Properties prop = new Properties();
    		senderImpl.setUsername(props.get("mail.username").toString()); // 根据自己的情况,设置username
    		senderImpl.setPassword(props.get("mail.password").toString()); // 根据自己的情况, 设置password
    		prop.put("mail.smtp.auth", props.get("mail.smtp.auth").toString()); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
    		prop.put("mail.smtp.timeout", props.get("mail.smtp.timeout").toString());
    		prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    		senderImpl.setJavaMailProperties(prop);
    		// 发送邮件
    		senderImpl.send(mailMessage);
        } catch (Exception e)
        {
            log.error("邮件发送失败:\ntoEmail=" + toEmail + "\nsubject" + subject + "\ntext" + text);
            log.error(CommonUtil.getExceptionMessage(e));
            return;
        } 
	}
	
	public static void main(String[] args)
    {
        sendHTMLMail("41254534@qq.com", "11", "fsdfsdfds");
    }
}
