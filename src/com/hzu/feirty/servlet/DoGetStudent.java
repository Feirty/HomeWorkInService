package com.hzu.feirty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Store;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hzu.feirty.contorl.MailReceive;
import com.hzu.feirty.dao.CourseDaoImpl;
import com.hzu.feirty.dao.SchoolDaoImpl;
import com.hzu.feirty.dao.StudentDaoImpl;
import com.hzu.feirty.dao.TeacherDaoImpl;
import com.hzu.feirty.dao.UserDaoImpl;
import com.hzu.feirty.entity.Course;
import com.hzu.feirty.entity.Student;
import com.hzu.feirty.entity.Teacher;
import com.hzu.feirty.entity.User;
import com.hzu.feirty.utils.ConnUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class DoGetStudent extends HttpServlet {
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
			String course = request.getParameter("course");
			String peasonmail = request.getParameter("mail");		
			StudentDaoImpl stuDao = new StudentDaoImpl();
			try {				
				if(stuDao.find(number,teacher,course)){
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
							System.out.println("--学生信息设置成功--");
						} catch (Exception e) {
							e.printStackTrace();
							array.put("code", "false");
							System.out.println("--学生信息设置失败--");
						}						
					}else{
						array.put("code", "false");
						System.out.println("插入异常！");
						}
					}else{
						array.put("code", "succ");
						System.out.println("--学生信息设置失败--");	
					}
			}catch (SQLException e1) {		
					e1.printStackTrace();
				}		
		} else if(action.equals("findstudent")){
			JSONArray arrays = new JSONArray();	
			String course = request.getParameter("course");		
			List<Student> list = new StudentDaoImpl().QueryStudent(username,course);
			if(!list.equals(null)){
				for(int i=0;i<list.size();i++){
					JSONObject object = new JSONObject();
					object.put("number", list.get(i).getNumber());
					arrays.add(object);
				}
				array.put("student", arrays.toString());
				array.put("code", "success");
				System.out.println("学生信息发送成功");
			}else{
				array.put("code", "queryStudentNull");
			}			 						
		}else if(action.equals("checkhomework")){
			String course = request.getParameter("course");
			try {
				if(new MailReceive().queryWorked(username, course)){
					array.put("code", "success");
					System.out.println("作业已收取");					
				}else{
					array.put("code", "false");
					System.out.println("作业未收取");
				}
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}else if(action.equals("findcourse")){
			JSONArray arrays = new JSONArray();	
			List<String> list = new ArrayList<String>();
			try {
				list = new StudentDaoImpl().QueryCourse(username);
				for(int i=0;i<list.size();i++){
					JSONObject object = new JSONObject();
					object.put("course", list.get(i));
					arrays.add(object);
				}
				array.put("course", arrays.toString());
				array.put("code", "success");
				array.put("msg", "11");
				System.out.println("课程发送成功");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		out.print(array);
		out.flush();
		out.close();
	}

}
