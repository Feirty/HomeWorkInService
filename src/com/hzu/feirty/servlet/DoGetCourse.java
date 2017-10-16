package com.hzu.feirty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzu.feirty.contorl.MailReceive;
import com.hzu.feirty.dao.CourseDaoImpl;
import com.hzu.feirty.entity.Course;
import com.hzu.feirty.entity.Email;
import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DoGetCourse extends HttpServlet {
	private List<Course> list;
	private List<Email> maillist;
	public DoGetCourse() {
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
		if(action.equals("findcourse")){
			JSONArray arrays = new JSONArray();	
			try {
				list = new CourseDaoImpl().QueryByTeacher(user);
				for(int i=0;i<list.size();i++){
					JSONObject object = new JSONObject();
					object.put("course", list.get(i).getName());
					object.put("students",list.get(i).getStu_number());
					object.put("works", list.get(i).getWorks());
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
		}else if(action.equals("findwork")){
			JSONArray arrays = new JSONArray();	
			String course = request.getParameter("course");
			try {
				maillist = MailReceive.getAllMailByCourse(user,course);
				CourseDaoImpl courseDaoImpl = new CourseDaoImpl();
				int stu_number= courseDaoImpl.findNumber(user,course);
				array.put("code", "success");
				array.put("number", ""+stu_number);
				array.put("msg", "11");
				int a=1;
				for (int i = 0; i < maillist.size(); i++) {
					JSONObject object = new JSONObject();
					object.put("id", ""+a);
					object.put("from", maillist.get(i).getFrom());
					object.put("subject", maillist.get(i).getSubject());
					object.put("content", maillist.get(i).getContent());
					object.put("time", maillist.get(i).getSentdata());
					object.put("course", maillist.get(i).getCourse());
					object.put("attachment", maillist.get(i).getAttachmentname());
					arrays.add(object);
					a++;
				}
				array.put("data", arrays.toString());
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}else if(action.equals("queryCourse")){
			JSONArray arrays = new JSONArray();	
			try {
				List<String> list = new CourseDaoImpl().QueryCourse();
				if(!list.equals(null)){
					for(int i=0;i<list.size();i++){
						JSONObject object = new JSONObject();
						object.put("course", list.get(i));
						arrays.add(object);
					}
					array.put("courses", arrays.toString());
					array.put("code", "success");
					System.out.println("响应课程请求");
										
				}else{
					array.put("code", "queryCourseNull");
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
