package com.hzu.feirty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.mail.Store;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hzu.feirty.contorl.MailReceive;
import com.hzu.feirty.dao.SchoolDaoImpl;
import com.hzu.feirty.dao.StudentDaoImpl;
import com.hzu.feirty.dao.TeacherDaoImpl;
import com.hzu.feirty.dao.UserDaoImpl;
import com.hzu.feirty.entity.Student;
import com.hzu.feirty.entity.Teacher;
import com.hzu.feirty.entity.User;
import com.hzu.feirty.utils.ConnUtil;

public class DoGetStudent extends HttpServlet {
	private String mail;
	private String pwd;
	public DoGetStudent() {
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
		String username = request.getParameter("user");
		PrintWriter out = response.getWriter();
		JSONObject array = new JSONObject();
		if(action.equals("SAVESET")){
			String teacher = request.getParameter("teacher");
			String school = request.getParameter("school");
			String number = request.getParameter("number");
			String peasonmail = request.getParameter("mail");	
			TeacherDaoImpl teaDao = new TeacherDaoImpl();
			StudentDaoImpl stuDao = new StudentDaoImpl();
			try {
				boolean a = stuDao.find(number);				
				boolean b = teaDao.find(teacher);
				//判断学号和老师姓名存在
				if(a&&b){
					//更新学生的信息
					Student stu= new Student();
					stu.setNumber(number);
					stu.setName(username);
					stu.setTeacher(teacher);
					stu.setMail(peasonmail);
					stu.setSchool(school);
					if(stuDao.update(stu)){
						try {
							array.put("code", "success");
							array.put("msg", "发送成功");
							array.put("mail",mail);
							array.put("pwd", pwd);
							array.put("data", "");
							System.out.println("发送成功！");
						} catch (Exception e) {
							e.printStackTrace();
							array.put("code", "succ");
							array.put("msg", "发送失败");
							array.put("data", "");
							System.out.println("发送失败！");
						}						
					}else{
						array.put("code", "false");
						array.put("msg", "插入异常");
						System.out.println("插入异常！");
						}
					}else{
						array.put("code", "succ");
						array.put("msg", "失败");
						array.put("data", "");
						System.out.println("发送失败！");	
					}
			}catch (SQLException e1) {		
					e1.printStackTrace();
				}		
		} else if(action.equals("queryStudent")){
			JSONArray arrays = new JSONArray();	
			String course = request.getParameter("course");		
			List<String> list = new StudentDaoImpl().QueryStudent(username,course);
			if(!list.equals(null)){
				for(int i=0;i<list.size();i++){
					JSONObject object = new JSONObject();
					object.put("school", list.get(i));
					arrays.add(object);
				}
				array.put("schools", arrays.toString());
				array.put("code", "success");					
			}else{
				array.put("code", "queryStudentNull");
			}
					 						
		}
		out.print(array);
		out.flush();
		out.close();
	}

}
