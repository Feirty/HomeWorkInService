package com.hzu.feirty.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.hzu.feirty.dao.UserDaoImpl;
import com.hzu.feirty.entity.User;

public class DoGetUser extends HttpServlet {
	public DoGetUser() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();
		JSONObject array = new JSONObject();
		UserDaoImpl ndi = new UserDaoImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		if (action.equals("login")) {
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			List<User> list = new ArrayList<User>();
			list = ndi.Search(user, password);
			if (list.size() < 1) {
				array.put("code", "failure");
				array.put("msg", "用户名或密码不正确");
				array.put("data", "");
			} else {
				System.out.println("\n用户" + user+ "登陆成功" + "\ntime" + time );
				array.put("code", "success");
				array.put("msg", "登陆成功");
				array.put("data", list.get(0));
			}
		} else if (action.equals("save")) {
			String code=request.getParameter("identify_code");
			String username = request.getParameter("user");
			String password = request.getParameter("password");
			User users = new User(username, password);
			try {
				if (ndi.Save(users)) {
					array.put("code", "success");
					array.put("msg", "注册成功");
					System.out.println("\n用户" + username + "注册成功" + "\ntime:" + time);
				} else {
					array.put("code", "false");
					array.put("msg", "用户名已存在");
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
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}

}
