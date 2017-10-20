package com.hzu.feirty.contorl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Store;

import com.hzu.feirty.dao.ConstructionDaoImpl;
import com.hzu.feirty.dao.CourseDaoImpl;
import com.hzu.feirty.dao.EmailDaoImpl;
import com.hzu.feirty.dao.HomeWorkDaoImpl;
import com.hzu.feirty.dao.StudentDaoImpl;
import com.hzu.feirty.dao.TeacherDaoImpl;
import com.hzu.feirty.entity.Course;
import com.hzu.feirty.entity.Email;
import com.hzu.feirty.entity.HomeWork;
import com.hzu.feirty.entity.Student;
import com.hzu.feirty.entity.Teacher;
import com.hzu.feirty.utils.ConnUtil;
import com.hzu.feirty.utils.GetFileSize;
import com.hzu.feirty.utils.IOUtil;
import com.sun.corba.se.impl.javax.rmi.CORBA.StubDelegateImpl;
import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.mail.internet.MimeMessage;


public class MailReceive {
	private String dateformat="yy-MM-dd HH:mm";//默认的日前显示格式
	public static List<Email>  getAllMail(String name) throws Exception{
		List<String> tealist = new ArrayList<String>();
		List<Email> mailList = new ArrayList<Email>();
		StudentDaoImpl stuDao = new StudentDaoImpl();
		Student student = new Student();
		student = stuDao.Search(name);
		tealist = stuDao.QueryTeacher(name);
		for(int b=0;b<tealist.size();b++){
			String number="";
			String mailname = new TeacherDaoImpl().find2(tealist.get(b)).getMail_name();
			String pwd = new TeacherDaoImpl().find2(tealist.get(b)).getMail_pwd();
			Store store =ConnUtil.login("pop.qq.com",mailname,pwd);
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
		    int mailCount = folder.getMessageCount();
	        if (mailCount == 0) {
	            folder.close(true);
	            store.close();
	            continue;
	        } else {
	            // 取得所有的邮件
	            Message[] messages = folder.getMessages();
	             for (int i = 0; i < messages.length; i++) {
	            	 Email mail = new Email();
	             	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);	        
	             	String str=pmm.getSubject();  
	             	String regex = "[0-9]{13}"; //正则表达式  
	             	Pattern pattern = Pattern.compile(regex);   
	             	Matcher m = pattern.matcher(str);
	             	if(m.find()){ 
	             	    number =m.group();
	             	}else{
	             		number ="";
	             	}
	             	if(!number.equals("")){
	             	 	String course = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
	                 	if(new StudentDaoImpl().isExit(student.getTeacher(),number,course)){
	                 	String sub = pmm.getSubject();
	                 	String subject = sub.substring(sub.indexOf("]")+1);
	                	mail.setFrom(pmm.getFrom());
	                	System.out.println("发件人："+pmm.getFrom());
	                	mail.setSubject(subject);
	                	System.out.println("主题："+subject);
	                	mail.setCourse(course);
	                	pmm.getMailContent((Part)messages[i]);
	                	mail.setContent(pmm.getBodyText());
	                	System.out.println("正文："+pmm.getBodyText());
	                	mail.setAttachmentname(pmm.getFilename((Part)messages[i]));
	                	System.out.println("附件："+pmm.getFilename((Part)messages[i]));
	                	pmm.setDateFormat("yy.MM.dd-HH:mm");
	                	mail.setSentdata(pmm.getSentDate());
	                	System.out.println("发送时间："+pmm.getSentDate());
	                    //PraseMimeMessage reciveMail = new PraseMimeMessage((MimeMessage) messages[i]);
	                	EmailDaoImpl eDaoImpl =new EmailDaoImpl();
	                	eDaoImpl.Insert(mail);
	                    mailList.add(mail);// 添加到邮件列表中
	                 	}        
	             	}          
	            }	           
	        }			
		}
		return mailList;
	}
	public static List<Email>  getAllMailByTeacher(String name) throws Exception{
		List<Email> mailList = new ArrayList<Email>();
		TeacherDaoImpl tDao = new TeacherDaoImpl();
		Teacher teacher = tDao.find2(name);
		String number="";
		String mail_name =teacher.getMail_name();
		String mail_pwd = teacher.getMail_pwd();
		Store store =ConnUtil.login("pop.qq.com",mail_name,mail_pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	    int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return null;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();
             for (int i = 0; i < messages.length; i++) {
            	Email mail = new Email();
             	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
             	//pmm.getSubject().contains("[作业]") ||pmm.getSubject().contains("[布置作业]"
             	String str=pmm.getSubject();  
             	String regex = "[0-9]{13}"; //正则表达式  
             	Pattern pattern = Pattern.compile(regex);   
             	Matcher m = pattern.matcher(str);
             	if(m.find()){ 
             	    number =m.group();
             	}else{
             		number ="";
             	}           
                 	if(!number.equals("")){
                 		String course = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                 		if(new StudentDaoImpl().isExit(name,number,course)){
                 			String sub = pmm.getSubject();
                         	String subject = sub.substring(sub.indexOf("]")+1);
                        	mail.setFrom(pmm.getFrom());
                        	System.out.println(pmm.getFrom());
                        	mail.setSubject(subject);
                        	System.out.println("主题："+subject);
                        	mail.setCourse(course);
                        	pmm.getMailContent((Part)messages[i]);
                        	mail.setContent(pmm.getBodyText());
                        	System.out.println(pmm.getBodyText());
                        	pmm.setDateFormat("yy.MM.dd-HH:mm");
                        	mail.setSentdata(pmm.getSentDate());
                        	mail.setMessageID(pmm.getMessageId());
                        	mail.setAttachmentname(pmm.getFilename((MimeMessage)messages[i]));
                        	EmailDaoImpl eDaoImpl =new EmailDaoImpl();
                        	eDaoImpl.Insert(mail);
                            mailList.add(mail);// 添加到邮件列表中
                 			
                 		}
             	
                 	}        	            	
             	
            }
            return mailList;
        }		
	}
	
	public static List<Email>  getAllMailByCourse(String name,String course) throws Exception{
		List<Email> mailList = new ArrayList<Email>();
		TeacherDaoImpl tDao = new TeacherDaoImpl();
		Teacher teacher = tDao.find2(name);
		String number="";
		String mail_name =teacher.getMail_name();
		String mail_pwd = teacher.getMail_pwd();
		Store store =ConnUtil.login("pop.qq.com",mail_name,mail_pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	    int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return null;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();
             for (int i = 0; i < messages.length; i++) {
            	Email mail = new Email();
             	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
             	//pmm.getSubject().contains("[作业]") ||pmm.getSubject().contains("[布置作业]"
             	String str=pmm.getSubject();  
             	String regex = "[0-9]{13}"; //正则表达式  
             	Pattern pattern = Pattern.compile(regex);   
             	Matcher m = pattern.matcher(str);
             	if(m.find()){ 
             	    number =m.group();
             	}else{
             		number ="";
             	}
             	
             	if(!number.equals("")){
             		String course1 = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
             		if(course.equals(course1)&&new StudentDaoImpl().isExit(name,number,course)){
             			String sub = pmm.getSubject();
                     	String subject = sub.substring(sub.indexOf("]")+1);
                    	mail.setFrom(pmm.getFrom());
                    	System.out.println(pmm.getFrom());
                    	mail.setSubject(subject);
                    	System.out.println("主题："+subject);
                    	mail.setCourse(course);
                    	pmm.getMailContent((Part)messages[i]);
                    	mail.setContent(pmm.getBodyText());
                    	System.out.println(pmm.getBodyText());
                    	pmm.setDateFormat("yy.MM.dd-HH:mm");
                    	mail.setSentdata(pmm.getSentDate());
                    	mail.setMessageID(pmm.getMessageId());
                    	mail.setAttachmentname(pmm.getFilename((MimeMessage)messages[i]));
                    	EmailDaoImpl eDaoImpl =new EmailDaoImpl();
                    	eDaoImpl.Insert(mail);
                        mailList.add(mail);// 添加到邮件列表中                     		
             		}        		
             	}
            }
            return mailList;
        }		
	}
	
	public static List<Email>  getAllReceiveWorkS(String name) throws Exception{
		List<Email> mailList = new ArrayList<Email>();
		List<String> tealist = new ArrayList<String>();
		StudentDaoImpl stuDao = new StudentDaoImpl();
		Student student = new Student();
		student = stuDao.Search(name);
		tealist = stuDao.QueryTeacher(name);
		for(int b=0;b<tealist.size();b++){
			String mailname = new TeacherDaoImpl().find2(tealist.get(b)).getMail_name();
			String pwd = new TeacherDaoImpl().find2(tealist.get(b)).getMail_pwd();
			Store store =ConnUtil.login("pop.qq.com",mailname,pwd);
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
		    int mailCount = folder.getMessageCount();
	        if (mailCount == 0) {
	            folder.close(true);
	            store.close();
	            continue;
	        } else {
	            // 取得所有的邮件
	            Message[] messages = folder.getMessages();
	             for (int i = 0; i < messages.length; i++) {
	            	Email mail = new Email();
	             	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
	             	String str=pmm.getSubject();  
	             	if(pmm.getSubject().contains("[课程:")){
	             		String course = str.substring(str.indexOf(":") + 1, str.indexOf("]"));
	             		String sub = pmm.getSubject();
	             		String subject = sub.substring(sub.indexOf("]")+1);
		            	mail.setSubject(subject);
		            	System.out.println(pmm.getSubject());
		            	pmm.getMailContent((Part)messages[i]);
		            	mail.setContent(pmm.getBodyText());
		            	System.out.println(pmm.getBodyText());
		            	mail.setCourse(course);
		            	pmm.setDateFormat("yy.MM.dd-HH:mm");
		            	mail.setSentdata(pmm.getSentDate());
		            	mail.setMessageID(pmm.getMessageId());
		            	//EmailDaoImpl eDaoImpl =new EmailDaoImpl();
		            	//eDaoImpl.Insert(mail);
		                mailList.add(mail);// 添加到邮件列表中
	             	}
	            }	         
	        }		
		}
		return mailList;
	}
	
	public static List<Email>  getAllReceiveWorkT(String name) throws Exception{
		List<Email> mailList = new ArrayList<Email>();
		TeacherDaoImpl tDao = new TeacherDaoImpl();
		Teacher teacher = tDao.find2(name);
		String mail_name =teacher.getMail_name();
		String mail_pwd = teacher.getMail_pwd();
		Store store =ConnUtil.login("pop.qq.com",mail_name,mail_pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	    int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return null;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();
             for (int i = 0; i < messages.length; i++) {
            	Email mail = new Email();
             	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);         
             	String str=pmm.getSubject();  
             	if(pmm.getSubject().contains("[课程:")){
             		String course = str.substring(str.indexOf(":") + 1, str.indexOf("]"));
             		String sub = pmm.getSubject();
             		String subject = sub.substring(sub.indexOf("]")+1);
	            	mail.setSubject(subject);
	            	System.out.println(pmm.getSubject());
	            	pmm.getMailContent((Part)messages[i]);        
	            	mail.setContent(pmm.getBodyText());
	            	mail.setCourse(course);
	            	System.out.println(pmm.getBodyText());
	            	pmm.setDateFormat("yy.MM.dd-HH:mm");
	            	mail.setSentdata(pmm.getSentDate());          
	            	EmailDaoImpl eDaoImpl =new EmailDaoImpl();
	            	//eDaoImpl.Insert(mail);
	                mailList.add(mail);// 添加到邮件列表中
             	}
            }
            return mailList;
        }		
	}

	public static int  getAllMailByTeacher2(String name,String course,String docsPath) throws Exception{
		List<Email> mailList = new ArrayList<Email>();
		TeacherDaoImpl tDao = new TeacherDaoImpl();
		String number="";
		Teacher teacher = tDao.find2(name);
		String mail_name =teacher.getMail_name();
		String mail_pwd = teacher.getMail_pwd();
		Store store =ConnUtil.login("pop.qq.com",mail_name,mail_pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	    int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return -1;
        } else {
            // 取得所有的邮件
        	int i=0;  
            Message[] messages = folder.getMessages();
            int shu = 0;
            for(i = 0; i < messages.length; i++){
            	ConstructionDaoImpl conDao = new ConstructionDaoImpl();   
            	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
            	if(conDao.queryIsNull()||conDao.queryTime(name).before(pmm.getSentDate2())){           
                	Email mail = new Email();               
                	String str=pmm.getSubject();  
                	String regex = "[0-9]{13}"; //正则表达式  
                	Pattern pattern = Pattern.compile(regex);   
                	Matcher m = pattern.matcher(str);
                	if(m.find()){ 
                	    number =m.group();
                	}else{
                		number ="";
                	}
                	if(!number.equals("")){
                		String course1 = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                 		if(course.equals(course1)&&new StudentDaoImpl().isExit(name,number,course)){                	
	                		if(pmm.isContainAttach((Part)messages[i])){
	                			File file=new File(docsPath);
	                			if(!file.exists()){
	                				file.mkdirs();
	                			}
	                			//清除上次作业的记录
	                			new HomeWorkDaoImpl().deleteDate(number, course1);
	                			//得到发送时间
	                			Date sd =pmm.getSentDate2();
	                			String mail_id = pmm.getMessageId();
	                			java.sql.Timestamp sendtime = new java.sql.Timestamp(sd.getTime());
	                			pmm.setAttachPath(file.toString());
	                			pmm.saveAttachMent((Part)messages[i]);
	                			//得到文件名
	                			String filename =pmm.getFilename();
	                			File path = new File(file.toString()+"\\"+filename);
	                			//计算作业文件大小
	                			GetFileSize getFileSize = new GetFileSize();
	                			String filesize =getFileSize.FormetFileSize(getFileSize.getFileSizes(path));         			           		
	                			//将作业得学号、文件名、文件大小、发送时间存入homework表中
	                			HomeWorkDaoImpl HomeWorkDaoImpl = new HomeWorkDaoImpl();
	                			HomeWork homework =new HomeWork(mail_id,number,filename,filesize,sendtime,course,1);
	                			HomeWorkDaoImpl.inSert(homework);
	                			shu++;
	                		}
                 		}             		
                	}
                    mailList.add(mail);// 添加到邮件列表中            	
            		}
            	}
            folder.close(true);
            store.close();
            return shu;
            }      
	}
	public static boolean  getAllMailByNumber(String name) throws Exception{
		Store store =ConnUtil.login("pop.qq.com",new TeacherDaoImpl().find2(name).getMail_name(),new TeacherDaoImpl().find2(name).getMail_pwd());
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		int a = 0;
	    int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return false;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();
             for (int i = 0; i < messages.length; i++) {
            	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
            	if(pmm.getSubject().contains("[学号]")){
            		if(pmm.isContainAttach((Part)messages[i])){
            			String course_name = pmm.getSubject().substring(4);
            			File file=new File("c:\\tmp\\");
            			if(!file.exists()){
            				file.mkdirs();
            			}
            			pmm.setAttachPath(file.toString());
            			pmm.saveAttachMent((Part)messages[i]);
            			int numbers =IOUtil.Txt("c:\\tmp\\"+pmm.getSubject()+".txt",course_name,name);
            			Course course = new Course();
            			course.setName(course_name);
            			course.setStu_number(numbers);
            			course.setTea_name(name);
            			new CourseDaoImpl().inSert(course);
            			a++;
            		}
            	}           
            }if(a>0){
            	 return true;
             }else{
            	 return false;
             }
        }
	}
	public static boolean  updateNumber(String name) throws Exception{
		Store store =ConnUtil.login("pop.qq.com",new TeacherDaoImpl().find2(name).getMail_name(),new TeacherDaoImpl().find2(name).getMail_pwd());
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		int a =0;
	    int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return false;
        } else {
            // 取得所有的邮件
            Message[] messages = folder.getMessages();
             for (int i = 0; i < messages.length; i++) {
            	//Email mail = new Email();
            	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);
            	if(pmm.getSubject().contains("[更新][学号]")){
            		if(pmm.isContainAttach((Part)messages[i])){
            			String course_name = pmm.getSubject().substring(8);
            			if(new CourseDaoImpl().find(course_name, name)){
            				File file=new File("c:\\tmp\\");
                			if(!file.exists()){
                				file.mkdirs();
                			}
                			pmm.setAttachPath(file.toString());
                			pmm.saveAttachMent((Part)messages[i]);
                			if(new StudentDaoImpl().delete(name,course_name)){
                				int numbers =IOUtil.Txt("c:\\tmp\\"+pmm.getSubject()+".txt",course_name,name);
                    			Course course = new Course();
                    			course.setName(course_name);
                    			course.setStu_number(numbers);
                    			course.setTea_name(name);
                    			new CourseDaoImpl().update(course);
                    			a++;
                			}                  
            			}            			            			        		
            		}if(a>0){
                   	 return true;
                    }else{
                   	 return false;
                    }	
            	}             
            }          
        }
		return false;			
	}
	
	/*
	 * 学生查询作业是否已经收取
	 */
	public boolean queryWorked(String name,String course1) throws Exception{
		StudentDaoImpl stuDao = new StudentDaoImpl();
		Student student = new Student();
		student = stuDao.Search(name);
		boolean ty = false;
		String number="";
		String mailname = new TeacherDaoImpl().find2(student.getTeacher()).getMail_name();
		String pwd = new TeacherDaoImpl().find2(student.getTeacher()).getMail_pwd();
		Store store =ConnUtil.login("pop.qq.com",mailname,pwd);
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
	    int mailCount = folder.getMessageCount();
        if (mailCount == 0) {
            folder.close(true);
            store.close();
            return false;
        } else {
            Message[] messages = folder.getMessages();
             for (int i = 0; i < messages.length; i++) {           
             	PraseMimeMessage pmm = new PraseMimeMessage((MimeMessage)messages[i]);          
             	String str=pmm.getSubject();  
             	String regex = "[0-9]{13}"; //正则表达式  
             	Pattern pattern = Pattern.compile(regex);   
             	Matcher m = pattern.matcher(str);
             	if(m.find()){ 
             	    number =m.group();
             	}else{
             		number ="";
             	}
             	if(!number.equals("")){
             	 	String course = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                 	if(new StudentDaoImpl().isExit(student.getTeacher(),number,course)&& course.equals(course1)){
                 		String id = pmm.getMessageId();
                 		if(new HomeWorkDaoImpl().isExistMailid(id,student.getNumber(),course)){
                 			ty=true;               		
                 		}         
                 	}        
             	}          
            }
            return ty;
        }		
	}
}
