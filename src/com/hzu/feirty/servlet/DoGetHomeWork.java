package com.hzu.feirty.servlet;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Part;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzu.feirty.contorl.MailReceive;
import com.hzu.feirty.contorl.MailSenter;
import com.hzu.feirty.contorl.MailToZip;
import com.hzu.feirty.contorl.PraseMimeMessage;
import com.hzu.feirty.dao.ConstructionDaoImpl;
import com.hzu.feirty.dao.CourseDaoImpl;
import com.hzu.feirty.dao.HomeWorkDaoImpl;
import com.hzu.feirty.dao.StudentDaoImpl;
import com.hzu.feirty.dao.TeacherDaoImpl;
import com.hzu.feirty.dao.UserDaoImpl;
import com.hzu.feirty.dao.WorkMadeDaoImpl;
import com.hzu.feirty.entity.Construction;
import com.hzu.feirty.entity.Course;
import com.hzu.feirty.entity.Email;
import com.hzu.feirty.entity.HomeWork;
import com.hzu.feirty.entity.Student;
import com.hzu.feirty.entity.Teacher;
import com.hzu.feirty.entity.WorkMade;
import com.hzu.feirty.utils.GetFileSize;
import com.hzu.feirty.utils.ExportExcel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DoGetHomeWork extends HttpServlet {
	ArrayList<String> attachments= new ArrayList<String>();
	private List<Email> maillist;
	private List<WorkMade> workList;
	//ArrayList<String> attachments;
	public DoGetHomeWork() {
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		String user = request.getParameter("user");
		PrintWriter out = response.getWriter();
		JSONObject array = new JSONObject();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(action.equals("send")){
			//String end_timeStr = request.getParameter("endtime");
			String course = request.getParameter("course");
			String subject1 =request.getParameter("title");
			String subject = "[课程:"+course+"]"+subject1;
			String content = request.getParameter("content");
			String work_number = subject1.substring(subject1.indexOf("-") + 1, subject1.indexOf(":"));
			try {
				Teacher teacher =new TeacherDaoImpl().find2(user);							
				String starttimestr =sdf.format(new Date());
				java.util.Date da2 = sdf.parse(starttimestr);
				java.sql.Timestamp start_time = new java.sql.Timestamp(da2.getTime());
				int number = Integer.parseInt(work_number);
				int stu_number= new CourseDaoImpl().findNumber(user,course);
				//保存作业内容
				WorkMade workmode = new WorkMade(subject1,content,course,number,start_time,user,course,stu_number,"未收取");				
				new WorkMadeDaoImpl().inSert(workmode);
				//发送作业内容
				MailSenter mailsend =new MailSenter("smtp.qq.com",teacher.getMail_name(), teacher.getMail_pwd());
				mailsend.send("smtp.qq.com",teacher.getPeasonmail(),subject,content);				
				array.put("code", "success");
				array.put("msg", "--任务发布成功--");
				array.put("data", "");
				System.out.println("--"+course+"任务发布成功--");						
			} catch (Exception e) {
				e.printStackTrace();
				array.put("code", "succ");
				array.put("msg", "--"+course+"发布作业失败");
				array.put("data", "");
				System.out.println("--"+course+"任务发布失败--");
			}
		} else if(action.equals("receivework")){
			JSONArray arrays = new JSONArray();		
			try {
				String str =new UserDaoImpl().SearchType(user);				
                //老师部分，任务内容接收
				if(str.equals("teacher")){				
					maillist = MailReceive.getAllReceiveWorkT(user);
					CourseDaoImpl courseDaoImpl = new CourseDaoImpl();		
					for (int i = 0; i < maillist.size(); i++) {
						JSONObject object = new JSONObject();
						int stu_number= courseDaoImpl.findNumber(user,maillist.get(i).getCourse());
						object.put("course", maillist.get(i).getCourse());
						object.put("title", maillist.get(i).getSubject());
						object.put("content", maillist.get(i).getContent());
						object.put("submitted", maillist.get(i).getWork_number());
						object.put("students",""+stu_number);
						object.put("update_time",""+maillist.get(i).getUpdate_time());
						object.put("issue_time", maillist.get(i).getSentdata());
						object.put("receive_state", maillist.get(i).getReceive_state());
						object.put("update_time",maillist.get(i).getUpdate_time());
						arrays.add(object);
					}
					array.put("data", arrays.toString());
					array.put("code", "success");
					System.out.println("------教师身份，任务接收成功-----");
				}				
                //学生部分：任务内容接收
				else if(str.equals("student")){
					List<String> courselist = new StudentDaoImpl().QueryCourse(user);
					for(int a =0;a<courselist.size();a++){
						List<Email> mList = new ArrayList<Email>();
						mList = MailReceive.getAllReceiveWorkS(user,courselist.get(a));
						CourseDaoImpl courseDaoImpl = new CourseDaoImpl();
						StudentDaoImpl stuDao = new StudentDaoImpl();
						Student student = stuDao.Search(user,courselist.get(a));			
						for (int i = 0; i < mList.size(); i++) {					
							JSONObject object = new JSONObject();
							int stu_number= courseDaoImpl.findNumber(student.getTeacher(),mList.get(i).getCourse());				
							object.put("course", mList.get(i).getCourse());
							object.put("title", mList.get(i).getSubject());
							object.put("content", mList.get(i).getContent());
							object.put("submitted", mList.get(i).getWork_number());
							object.put("students",""+stu_number);
							object.put("issue_time", mList.get(i).getSentdata());
							object.put("submit_state", mList.get(i).getSubmit_state());
							object.put("receive_state", mList.get(i).getReceive_state());
							object.put("update_time",mList.get(i).getUpdate_time());
							arrays.add(object);												
						}	
					}				
					array.put("data", arrays.toString());
					array.put("code", "success");			
					System.out.println("---学生身份:任务接收成功---");
				}else{
					array.put("code", "false");					
				}		
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}else if(action.equals("first_receive")){
			JSONArray arrays = new JSONArray();		
			try {
				String str =new UserDaoImpl().SearchType(user);				
                //老师部分，任务内容接收
				if(str.equals("teacher")){				
					workList = new WorkMadeDaoImpl().QueryWorkT(user);
					for (int i = 0; i < workList.size(); i++) {
						JSONObject object = new JSONObject();
						object.put("course", workList.get(i).getCourse_name());
						object.put("title", workList.get(i).getWork_name());
						object.put("content", workList.get(i).getWork_content());
						object.put("submitted", workList.get(i).getSubmitteds());
						object.put("students",workList.get(i).getStudents());
						String issue_time =sdf.format(workList.get(i).getStart_time());
						String update_time = sdf.format(workList.get(i).getUpdate_time());					
						object.put("receive_state", workList.get(i).getReceive_state());
						object.put("issue_time",issue_time);
						object.put("update_time",update_time);
						arrays.add(object);
					}
					array.put("data", arrays.toString());
					array.put("code", "success");
					System.out.println("------教师身份，任务接收成功-----");
				}				
                //学生部分：任务内容接收
				else if(str.equals("student")){
					List<String> courselist = new StudentDaoImpl().QueryCourse(user);
					for(int a =0;a<courselist.size();a++){
						//List<Email> mList = new ArrayList<Email>();
						String tea_name =new StudentDaoImpl().QueryTeacher_name(user, courselist.get(a));
						workList = new WorkMadeDaoImpl().QueryWorkC(tea_name,courselist.get(a));
						//CourseDaoImpl courseDaoImpl = new CourseDaoImpl();
						//StudentDaoImpl stuDao = new StudentDaoImpl();
						//Student student = stuDao.Search(user,courselist.get(a));			
						for (int i = 0; i < workList.size(); i++) {					
							JSONObject object = new JSONObject();
							object.put("course", workList.get(i).getCourse_name());
							object.put("title", workList.get(i).getWork_name());
							object.put("content", workList.get(i).getWork_content());
							object.put("submitted", workList.get(i).getSubmitteds());
							object.put("students",workList.get(i).getStudents());
							String issue_time =sdf.format(workList.get(i).getStart_time());
							String update_time = sdf.format(workList.get(i).getUpdate_time());					
							object.put("receive_state", workList.get(i).getReceive_state());
							object.put("issue_time",issue_time);
							object.put("update_time",update_time);
							String stu_id = new StudentDaoImpl().QueryNumber(user, workList.get(i).getCourse_name());
							HomeWork homeWork = new HomeWork(stu_id,workList.get(i).getCourse_name(),workList.get(i).getWork_times());  
		             		if(new HomeWorkDaoImpl().querySubmit_state(homeWork)){
		             			object.put("submit_state","你的作业已提交");       
		             		}else{
		             			object.put("submit_state","你的作业未提交");  
		             		}   
							arrays.add(object);												
						}	
					}				
					array.put("data", arrays.toString());
					array.put("code", "success");			
					System.out.println("---学生身份:任务接收成功---");
				}else{
					array.put("code", "false");					
				}		
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}else if(action.equals("receive")){
			JSONArray arrays = new JSONArray();		
			try {
				String str =new UserDaoImpl().SearchType(user);				
                //老师部分，作业邮件内容接收
				if(str.equals("teacher")){
					//这个函数改！
					maillist = MailReceive.getAllMail(user);
					CourseDaoImpl courseDaoImpl = new CourseDaoImpl();
					int a=1;
					for (int i = 0; i < maillist.size(); i++) {
						JSONObject object = new JSONObject();
						object.put("id", ""+a);
						object.put("from", maillist.get(i).getFrom());
						object.put("subject", maillist.get(i).getSubject());
						object.put("content", maillist.get(i).getContent());
						object.put("time", maillist.get(i).getSentdata());
						object.put("course", maillist.get(i).getCourse());
						int stu_number= courseDaoImpl.findNumber(user,maillist.get(i).getCourse());
						object.put("stu_number",""+stu_number);
						object.put("attachment", maillist.get(i).getAttachmentname());
						arrays.add(object);
						a++;
					}
					array.put("data", arrays.toString());
					array.put("code", "success");
					System.out.println("---教师身份:作业接收成功---");
				}				
                //学生部分：作业邮件内容接收
				else if(str.equals("student")){
					maillist = MailReceive.getAllMail(user);
					StudentDaoImpl stuDao = new StudentDaoImpl();
					Student student = new Student();
					student = stuDao.Search(user,"");
					CourseDaoImpl courseDaoImpl = new CourseDaoImpl();		
					int a=1;
					for (int i = 0; i < maillist.size(); i++) {
						if(student.getCourse().equals(maillist.get(i).getCourse())){
							JSONObject object = new JSONObject();
							object.put("id", ""+a);
							object.put("from", maillist.get(i).getFrom());
							object.put("subject", maillist.get(i).getSubject());
							object.put("content", maillist.get(i).getContent());
							object.put("time", maillist.get(i).getSentdata());
							object.put("course", maillist.get(i).getCourse());
							int stu_number= courseDaoImpl.findNumber(user,maillist.get(i).getCourse());
							object.put("stu_number",""+stu_number);
							object.put("attachment", maillist.get(i).getAttachmentname());
							arrays.add(object);					
						}						
					}
					array.put("data", arrays.toString());
					array.put("code", "success");		
					System.out.println("---学生身份:作业接收成功---");							
				}else{
					array.put("code", "false");
					array.put("msg", "11");						
				}		
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		/*
		 * 添加课程功能
		 * 关于课程、学号
		 */
		else if(action.equals("COURSE")){
			try {
				array.put("code", "success");
				array.put("msg", "学号添加成功");
				array.put("data", "");
				String Path = request.getSession().getServletContext().getRealPath("student");
				if(MailReceive.getAllMailByNumber(user,Path)){
					System.out.println("学号添加成功");					
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array.put("code", "suc");
				array.put("msg", "学号添加失败");
				array.put("data", "");
				System.out.println("学号添加失败");
			}			
		}else if(action.equals("UPDATECOURSE")){
			try {
				array.put("code", "success");
				array.put("msg", "学号添加成功");
				array.put("data", "");
				String Path = request.getSession().getServletContext().getRealPath("student");
				if(MailReceive.updateNumber(user,Path)){
					System.out.println("课程更新成功");					
				}				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array.put("code", "suc");
				array.put("msg", "课程更新失败");
				array.put("data", "");
				System.out.println("课程更新失败");
			}			
		}
		/*
		 * 教师接收作业功能
		 * 关于附件下载
		 * 附件打包
		 * 附件邮件发送
		 */
		else if(action.equals("RECEIVEHOMEWORK")){
			try {
				String course = request.getParameter("course");
				String workString = request.getParameter("work_number");
				int work_number = Integer.parseInt(workString);
				String type=new UserDaoImpl().SearchType(user);
				if(type.equals("teacher")){
					Teacher teacher = new TeacherDaoImpl().find2(user);
					if(!teacher.getPeasonmail().equals("")){
						String separator = System.getProperty("file.separator");
						DateFormat dateFormat = new SimpleDateFormat("yyMMddhhmm");
						String date =dateFormat.format(new Date());						
						//作业匹配下载				
						String docsPath = request.getSession().getServletContext().getRealPath(course+"/"+"第"+workString+"次作业");
						String downloadPath= request.getSession().getServletContext().getRealPath("/DownloadFile?"+"path="+course+"/"+"作业汇总"+"/第"+workString+"次作业"+".zip");
						//  i为作业的数量
						int i = MailReceive.getAllMailByTeacher2(user,course,workString,docsPath);
						if(i>0){							
							String worknumber=String.valueOf(i);					
							String imagesPath = request.getSession().getServletContext().getRealPath("images");
							//(new ExportExcel()).test(imagesPath, docsPath,user,course);
							String zipsPath = request.getSession().getServletContext().getRealPath(course+"/"+"作业汇总");
							File sourceFilePath=new File(docsPath);
							File zipFilePath=new File(zipsPath);
							if(!zipFilePath.exists()){
								zipFilePath.mkdirs();
                			}								
							//作业压缩zip
							MailToZip.mailToZip(sourceFilePath.toString(), zipFilePath.toString(), course+"第"+workString+"次作业");
							System.out.println("-----作业压缩成功-----");
							File zippath = new File(zipFilePath.toString()+separator+course+"第"+workString+"次作业"+".zip");
							long zipSize =new GetFileSize().getFileSizes(zippath);
							GetFileSize g = new GetFileSize();
	            			long filenumber= g.getlist(sourceFilePath);
							System.out.println("-----作业文件大小:"+zipSize+"-----");
							if(zipSize<8366808){
								String attachments = zippath.toString();
								String mailname = teacher.getMail_name();
								String mailpwd = teacher.getMail_pwd();
								String peason_mail = teacher.getPeasonmail();					
								 // 作业压缩文件发送 
								MailSenter mailsend =new MailSenter("smtp.qq.com", mailname, mailpwd);					
								mailsend.send(peason_mail,course+"第"+workString+"次作业","请及时查收",attachments,"收发作业系统");
								System.out.println("-----作业发送成功-----");
								GetFileSize getFileSize = new GetFileSize();
		            			String zip_size =getFileSize.FormetFileSize(getFileSize.getFileSizes(zippath));
		            			String zip_name = course+"第"+workString+"次作业";		                			            			
		            			String datestring =sdf.format(new Date());
		            			java.util.Date da = sdf.parse(datestring);
		            			java.sql.Timestamp sendtime = new java.sql.Timestamp(da.getTime());
		            			ConstructionDaoImpl constructionDaoImpl = new ConstructionDaoImpl();
		            			//统计作业的提交情况
		            			Construction construction = new Construction(user, filenumber, zip_name, zip_size,course,sendtime);
		            			constructionDaoImpl.inSert(construction);
		            			//修改课程作业状态
		            			WorkMade workmade =  new WorkMade(user,course,work_number,"已收取");
		        				new WorkMadeDaoImpl().changeSate(workmade);		
		            			array.put("code", "success");
								array.put("msg", "作业打包发送成功");
								array.put("zip_name",zip_name);
								array.put("amount", worknumber);
								array.put("zip_size", zip_size);
								array.put("send_time", date);
							}else{
								GetFileSize getFileSize = new GetFileSize();
		            			String zip_size =getFileSize.FormetFileSize(getFileSize.getFileSizes(zippath));
		            			String zip_name = course+"第"+workString+"次作业";		            		         			            			
		            			String datestring =sdf.format(new Date());
		            			java.util.Date da = sdf.parse(datestring);
		            			java.sql.Timestamp sendtime = new java.sql.Timestamp(da.getTime());
		            			ConstructionDaoImpl constructionDaoImpl = new ConstructionDaoImpl();
		            			//统计作业的提交情况
		            			Construction construction = new Construction(user, filenumber, zip_name, zip_size,course,sendtime);
		            			constructionDaoImpl.inSert(construction);
		            			//修改课程作业状态
		            			WorkMade workmade =  new WorkMade(user,course,work_number,"已收取");
		        				new WorkMadeDaoImpl().changeSate(workmade);		
		            			array.put("code", "SizeIsTOBig");
								array.put("msg", "作业打包发送成功");
								array.put("zip_name",zip_name);
								array.put("url",downloadPath.toString());
								array.put("amount", worknumber);
								array.put("zip_size", zip_size);
								array.put("send_time", date);						
							}	            			
						}else{
							array.put("msg", "没有待收取的作业");
							array.put("code", "nonew");
						}
													
					}else{
						array.put("code", "nomail");
						array.put("msg", "nomail");					
					}				
				}else{
					array.put("code", "noidentry");
					array.put("msg", "noidentry");				
				}
			} catch (Exception e) {
				e.printStackTrace();
				array.put("code", "false");
				array.put("msg", "收作业失败");
				System.out.println("收作业失败");
			}
		}
		out.print(array);
		out.flush();
		out.close();
	}
  
}
