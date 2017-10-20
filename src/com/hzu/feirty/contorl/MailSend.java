package com.hzu.feirty.contorl;
/** 
 * 使用JavaMail发送带附件的电子邮件示例 
 */  
import java.io.FileNotFoundException;
import java.io.IOException;  
import java.io.UnsupportedEncodingException;  
import java.util.Date;  
import java.util.Properties;  
import javax.activation.DataHandler;  
import javax.activation.FileDataSource;  
import javax.mail.MessagingException;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.Message.RecipientType;  
import javax.mail.internet.AddressException;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeBodyPart;  
import javax.mail.internet.MimeMessage;  
import javax.mail.internet.MimeMultipart;  
import javax.mail.internet.MimeUtility;  

public class MailSend {  
     private static Session getSession(String protocol){  
         Properties mailProps=new Properties();  
         mailProps.put("mail.smtp.auth", "true");//向SMTP服务器提交用户认证  
         mailProps.put("mail.transport.protocol", protocol);//指定发送邮件协议  
           
         //getInstance每次都会拿一个新的session,而getDefaultInstance拿的是同一个session  
         Session session=Session.getDefaultInstance(mailProps);  
         //session.setDebug(true);//调试模式  
         return session;  
     }  
      
      
     private static void sendEmail(MimeMessage message,String protocol) throws MessagingException{  
         String host="smtp.163.com";//连接发送方的SMTP服务器  
         String user="iwtxokhtd";//用户名  
         String password="123456";//密码  
        //从session中取mail.smtp.protocol指定协议的Transport  
         Transport transport=getSession(protocol).getTransport();  
         //建立与指定的SMTP服务器的连接  
         transport.connect(host, user, password);  
        //发给所有指定的收件人,若使用message.getAllRecipients()则还包括抄送和暗送的人  
         transport.sendMessage(message, message.getRecipients(RecipientType.TO));  
         //关闭连接  
         transport.close();  
           
         /** 
          * Transport的send静态方法包括了connect,saveChanges,sendMessage,close等一系列操作， 
          * 但它连接同一个SMTP服务器每发一封邮件给服务器都得重新建立连接和断开连接, 
          * 虽然使用较方便，但开销较大,不值得推荐。 
          */  
        // Transport.send(message, message.getRecipients(RecipientType.TO));  
     }  
       
      
     private static MimeMessage getTextMessage(Session session) throws AddressException,   
            MessagingException, UnsupportedEncodingException{  
          MimeMessage message=new MimeMessage(session);  
          String from="iwtxokhtd@163.com";//发送方邮件地址  
          String to="277515433@qq.com";//接收方邮件地址  
            
          String subject="从163发到QQ来的邮件";//邮件主题，注意是中文的  
            
          String content="你好吗";//cid为my1.jpg，下文会设置此cid  
          //String content="你好吗";
          message.setFrom(new InternetAddress(from));  
          message.setRecipient(RecipientType.TO, new InternetAddress(to));  
          message.setSubject(subject);  
          message.setSentDate(new Date());//发送时间 
          //message.setContent(content);
            
          MimeBodyPart picBodyPart=getPicBodyPart(content,"F://My头像.jpg");  
          MimeBodyPart attached1BodyPart=getAttachedBodyPart("C://tmp//作业附件20170703.zip");//注意附件名是中文的  
          //MimeBodyPart attached2BodyPart=getAttachedBodyPart("F://nginx英文.doc");  
            
          MimeMultipart mmp=new MimeMultipart("mixed");//MIME消息头组合类型是mixed(html+附件)  
          mmp.addBodyPart(picBodyPart);  
          mmp.addBodyPart(attached1BodyPart);  
          //mmp.addBodyPart(attached2BodyPart);  
            
          message.setContent(mmp);  
          message.saveChanges();  
            
          return message;  
        
     }  
       
     /** 
      * 处理文件名 
      * 此处是针对Window下的。 
      * @param filePath 
      * @return 
      */  
     private static String doHandlerFileName(String filePath){  
          String fileName=filePath;  
          if(null !=filePath && !"".equals(filePath)){  
           fileName=filePath.substring(filePath.lastIndexOf("//")+1);  
          }  
          return fileName;  
     }  
       
       
     private static MimeBodyPart getAttachedBodyPart(String filePath) throws MessagingException,  
            UnsupportedEncodingException{  
          MimeBodyPart attached=new MimeBodyPart();  
          FileDataSource fds=new FileDataSource(filePath);  
          attached.setDataHandler(new DataHandler(fds));  
          String fileName=doHandlerFileName(filePath);  
          attached.setFileName(MimeUtility.encodeWord(fileName));//处理附件文件的中文名问题  
          return attached;  
     }  
       
     /** 
      * 处理html加图片的类型(related) 
      * @param content 
      * @param picName 
      * @return 
      * @throws MessagingException 
      */  
     private static MimeBodyPart getPicBodyPart(String content,String picName) throws MessagingException{  
          MimeBodyPart contentPart=new MimeBodyPart();  
            
          MimeMultipart mmp=new MimeMultipart("related");//此处MIME消息头组合类型为related  
          MimeBodyPart contented=new MimeBodyPart();  
          contented.setContent(content,"text/html;charset=gb2312");//因正文内容中有中文  
            
          mmp.addBodyPart(contented);  
            
          /*MimeBodyPart picBodyPart=new MimeBodyPart();  
          FileDataSource fds=new FileDataSource(picName);  
          picBodyPart.setDataHandler(new DataHandler(fds));  
          picBodyPart.setContentID("my1.jpg");//设置contentId  
            
          mmp.addBodyPart(picBodyPart);  */
            
          contentPart.setContent(mmp);  
            
          return contentPart;  
     }  
       
       
     public static void main(String[] args) throws AddressException,   
            MessagingException, FileNotFoundException, IOException {  
         String protocol="smtp";  
         sendEmail(getTextMessage(getSession(protocol)),protocol);  
        
     }  
}