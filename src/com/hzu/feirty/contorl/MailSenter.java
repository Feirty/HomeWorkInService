package com.hzu.feirty.contorl;
import  com.hzu.feirty.entity.Email;
import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

public class MailSenter {

    private String host;
    private String username;
    private String password;
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
    	email.setFrom(username, "发布作业");
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
    }
}
