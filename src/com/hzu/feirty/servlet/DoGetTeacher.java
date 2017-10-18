package com.hzu.feirty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.Store;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hzu.feirty.contorl.MailReceive;
import com.hzu.feirty.contorl.MailSenter;
import com.hzu.feirty.dao.StudentDaoImpl;
import com.hzu.feirty.dao.TeacherDaoImpl;
import com.hzu.feirty.entity.Email;
import com.hzu.feirty.entity.Teacher;
import com.hzu.feirty.utils.ConnUtil;
import com.sun.org.apache.bcel.internal.generic.POP;

public class DoGetTeacher extends HttpServlet {
	private List<Email> maillist;
	
	public DoGetTeacher() {
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
		PrintWriter out = response.getWriter();
		JSONObject array = new JSONObject();
		if(action.equals("SAVESET")){
			String name = request.getParameter("name");
			String school = request.getParameter("school");
			String workmail = request.getParameter("workmail");
			String mailpwd = request.getParameter("pwd");
			String user = request.getParameter("user");
			String peasonmail = request.getParameter("peasonmail");
			try {
				Teacher teacher = new Teacher(name,workmail,mailpwd,school,peasonmail,user);
				new TeacherDaoImpl().add(teacher);
		        //MailReceive.getAllMailByNumber(user)  
				array.put("code", "success");
				array.put("msg", "发送成功");
				System.out.println("教师信息收集完成");													
			} catch (Exception e) {
				e.printStackTrace();
				array.put("code", "false");
				array.put("msg", "发送失败");
				System.out.println("收集异常失败");
			}	
		}else if(action.equals("queryMyTeacher")){
			JSONArray arrays = new JSONArray();	
			String user = request.getParameter("user");
			try {
				List<String> tealist = new StudentDaoImpl().QueryTeacher(user);
				if(!tealist.equals(null)){
					for(int i=0;i<tealist.size();i++){
						JSONObject object = new JSONObject();
						object.put("teacher", tealist.get(i));
						arrays.add(object);
					}
					array.put("teachers", arrays.toString());
					array.put("code", "success");					
				}else{
					array.put("code", "queryMyTeacherNull");
				}
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
