package com.hzu.feirty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.hzu.feirty.contorl.MailSenter;
import com.hzu.feirty.dao.TeacherDaoImpl;
import com.hzu.feirty.dao.UserDaoImpl;
import com.hzu.feirty.entity.Teacher;

public class DoGetType extends HttpServlet {
	public DoGetType(){
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
		if(action.equals("istype")){
			try {
				String type=new UserDaoImpl().SearchType(user);
				if(type.equals("teacher")){
					array.put("code", "teacher");
					array.put("msg", "验证成功");
					array.put("data", "");
					System.out.println("验证成功！");		
				}else if(type.equals("student")){
					array.put("code", "student");
					array.put("msg", "验证成功");
					array.put("data", "");
					System.out.println("验证成功！");		
				}else{
					array.put("code", "false");
					array.put("msg", "验证失败");
					array.put("data", "");
					System.out.println("验证失败！");
				}		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array.put("code", "error");
				array.put("msg", "未知错误");
				array.put("data", "");
				System.out.println("未知错误！");
			}
		}else if(action.equals("settype2")){
			try {
				String type = request.getParameter("type");
				boolean a =new UserDaoImpl().updateType(type, user);
				if(a){
					array.put("code", "success");
					array.put("msg", "设置身份成功");
					array.put("data", "");
					System.out.println("设置身份成功");	
				}else{
					array.put("code", "false");
					array.put("msg", "身份设置失败");
					array.put("data", "");
					System.out.println("身份设置失败");
				}		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array.put("code", "error");
				array.put("msg", "未知错误");
				array.put("data", "");
				System.out.println("未知错误！");
			}
		}
		else if(action.equals("settype")){
			try {
				String type=new UserDaoImpl().SearchType(user);
				if(type.equals("teacher")||type.equals("student")||type.equals("student")){
					array.put("code", "success");
					array.put("msg", "已选择身份");
					array.put("data", "");
					System.out.println("已选择身份");	
				}else{
					array.put("code", "false");
					array.put("msg", "请选择身份");
					array.put("data", "");
					System.out.println("请选择身份");
				}		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				array.put("code", "error");
				array.put("msg", "未知错误");
				array.put("data", "");
				System.out.println("未知错误！");
			}
		}
		out.print(array);
		out.flush();
		out.close();
	}

}
