package com.hzu.feirty.contorl;
import  com.hzu.feirty.entity.Email;
import com.sun.mail.util.MailSSLSocketFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class MailSenter {

    private String host;
    private String username;
    private String password;

    // private static final String SMTPHOST = "smtphost";
    // private static final String USERNAME = "username";
    // private static final String PASSWORD = "password";
    // private static MailSenter instance;

    // public static MailSenter getInstance(EditMailActivity context) {
    // if (instance == null) {
    // instance = new MailSenter(PreferencesUtil.getSharedStringData(context,
    // SMTPHOST), PreferencesUtil.getSharedStringData(context, USERNAME),
    // PreferencesUtil.getSharedStringData(context, PASSWORD));
    // }
    // return instance;
    // }

    /**
     * 构造方法
     *
     * @param host 邮箱服务器
     * @param username 邮箱用户全名。例如：test@qq.com
     * @param password 邮箱用户密码
     */
    public MailSenter(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    /**
     * 发送邮件
     * @param hostname 
     * @param mailTo 收件人email地址
     * @param mailSubject 邮件标题
     * @param mailBody 邮件正文
     * @throws Exception
     */
    public void send(String hostname,String mailTo, String mailSubject, String mailBody) throws Exception {
    	// Create the attachment
    	EmailAttachment attachment = new EmailAttachment();
    	//附件地址
    	//attachment.setPath(attachments);
    	attachment.setDisposition(EmailAttachment.ATTACHMENT);
    	//附件说明
    	//attachment.setDescription("Picture of John");
    	//附件名称
    	//这里需要加上文件的后缀名
        //Integer a = attachments.indexOf("\\");
       // String aftera = attachments.substring(a+5);
    	//attachment.setName(MimeUtility.encodeText(aftera));
    	// Create the email message
    	MultiPartEmail email = new MultiPartEmail();
    	//邮件服务器
    	email.setHostName(hostname);
    	//端口号
    	email.setSmtpPort(25);
    	//用户名、密码
    	email.setAuthenticator(new DefaultAuthenticator(username, password));
    	//email.setSSLOnConnect(true);
    	email.setSSL(true);
    	try {
    	//收件人
    	email.addTo(mailTo, "老师");
    	//发件人
    	email.setFrom(username, "布置作业");
    	//标题
    	email.setSubject(mailSubject);
    	//内容
    	email.setMsg(mailBody);
    	// add the attachment
    	//email.attach(null);
    	// send the email
    	email.send();
    	} catch (EmailException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    	}
    }

    /**
     * 发送邮件
     *
     * @param email 邮件对象
     * @throws Exception
     */
    public void send(Email email) throws Exception {
        //send(email.getTo(), email.getSubject(), email.getContent(), email.getAttachments(), null);
    }

    /**
     * 发送邮件
     *
     * @param mailTo 收件人email地址
     * @param mailSubject 邮件标题
     * @param mailBody 邮件正文
     * @param attachments 附件
     * @param personalName 邮件显示的发送人名称
     * @throws Exception
     */
    public void send(String mailTo, String mailSubject, String mailBody, String attachments,
                     String personalName) throws Exception {   	
    	// TODO Auto-generated method stub
    	// Create the attachment
    	EmailAttachment attachment = new EmailAttachment();
    	//附件地址
    	attachment.setPath(attachments);
    	attachment.setDisposition(EmailAttachment.ATTACHMENT);
    	//附件说明
    	//attachment.setDescription("Picture of John");
    	//附件名称
    	//这里需要加上文件的后缀名
        Integer a = attachments.indexOf("\\");
        String aftera = attachments.substring(a+5);
    	attachment.setName(MimeUtility.encodeText(aftera));
    	// Create the email message
    	MultiPartEmail email = new MultiPartEmail();
    	//邮件服务器
    	email.setHostName("smtp.qq.com");
    	//端口号
    	email.setSmtpPort(25);
    	//用户名、密码
    	email.setAuthenticator(new DefaultAuthenticator(username, password));
    	//email.setSSLOnConnect(true);
    	email.setSSL(true);
    	try {
    	//收件人
    	email.addTo(mailTo, "老师");
    	//发件人
    	email.setFrom(username, personalName);
    	//标题
    	email.setSubject(mailSubject);
    	//内容
    	email.setMsg(mailBody);
    	// add the attachment
    	email.attach(attachment);
    	// send the email
    	email.send();
    	} catch (EmailException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    	}
        //try {
        	/*Properties props = new Properties();
        	// 开启debug调试
        	props.setProperty("mail.debug", "true");
        	// 发送服务器需要身份验证
        	props.setProperty("mail.smtp.auth", "true");
        	// 设置邮件服务器主机名
        	props.setProperty("mail.host", "smtp.qq.com");
        	// 发送邮件协议名称
        	props.setProperty("mail.transport.protocol", "smtp");
        	MailSSLSocketFactory sf = new MailSSLSocketFactory();
        	sf.setTrustAllHosts(true);
        	props.put("mail.smtp.ssl.enable", "true");
        	props.put("mail.smtp.ssl.socketFactory", sf);
        	Session session = Session.getInstance(props);
        	//邮件内容部分
        	Message msg = new MimeMessage(session);
        	msg.setSubject(mailSubject);
        	StringBuilder builder = new StringBuilder();
        	builder.append(mailBody);
        	msg.setText(builder.toString());
        	//邮件发送者
        	msg.setFrom(new InternetAddress(username));
            Multipart mpRoot = new MimeMultipart();
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mailBody, "text/html;charset=gb2312");
            mpRoot.addBodyPart(mimeBodyPart);
            for (int i = 0; i < attachments.size(); i++) {
                MimeBodyPart mpAttachments = new MimeBodyPart();
                mpAttachments.setDataHandler(new DataHandler(new FileDataSource(attachments.get(i))));
                mpAttachments.setFileName(MimeUtility.encodeText(attachments.get(i).substring
                        (attachments.get(i).lastIndexOf("/") + 1)));
                mpRoot.addBodyPart(mpAttachments);
            }
            msg.setContent(mpRoot);
        	//发送邮件
        	Transport transport = session.getTransport();
        	transport.connect("smtp.qq.com", username, password);
        	transport.sendMessage(msg, new Address[] { new InternetAddress(mailTo) });
        	transport.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }*/
    }
   

    /**
     * 用来进行服务器对用户的认证
     */
    public class Email_Autherticator extends Authenticator {
        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

}
