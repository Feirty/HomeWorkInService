
package com.hzu.feirty.utils;

import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

//连接邮件服务器邮件接收util类
public class ConnUtil {
    private static Store store = null;
    public static Store login(String host, String user, String password) {
        // 连接服务器
        Properties props =  System.getProperties();
        Session session = Session.getDefaultInstance(props);
        try {
            /**  QQ邮箱需要建立ssl连接 */
            props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.pop3.socketFactory.fallback", "false");
            props.setProperty("mail.pop3.starttls.enable","true");
            props.setProperty("mail.pop3.port", "995");
            props.setProperty("mail.pop3.socketFactory.port", "995");
            store = session.getStore("pop3");
            store.connect(host, user, password);
        }
        catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
        return store;
    }

    public static String getPOP3Host(String user) {
        if (user.contains("qq")) {
            return "pop.qq.com";
        }
		if (user.contains("163")) {
            return "pop.163.com";
        }
		if (user.contains("126")) {
            return "pop.126.com";
        }else
            return null;
        }
    
    public static String getSMTPHost(String user) {
        if (user.contains("qq")) {
            return "smtp.qq.com";
        } 
		if (user.contains("163")) {
            return "smtp.163.com";
        } 
		if (user.contains("126")) {
            return "smtp.126.com";
        } else {
            return null;
        }
    }

}
