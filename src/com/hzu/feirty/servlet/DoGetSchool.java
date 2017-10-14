package com.hzu.feirty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hzu.feirty.dao.CourseDaoImpl;
import com.hzu.feirty.dao.SchoolDaoImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DoGetSchool extends HttpServlet {

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
		if(action.equals("querySchool")){
			JSONArray arrays = new JSONArray();	
			try {
				List<String> list = new SchoolDaoImpl().QuerySchool();
				if(!list.equals(null)){
					for(int i=0;i<list.size();i++){
						JSONObject object = new JSONObject();
						object.put("school", list.get(i));
						arrays.add(object);
					}
					array.put("schools", arrays.toString());
					array.put("code", "success");					
				}else{
					array.put("code", "querySchoolNull");
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
