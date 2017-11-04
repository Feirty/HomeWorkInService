package com.hzu.feirty.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadFile extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		this.doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//this.doGet(request,response);
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
	    //获得请求文件名 
	    String filename1 = request.getParameter("path"); //"大学物理第1次作业"
	    String filename  = new String(filename1.getBytes("ISO-8859-1"),"UTF-8"); 
	    //String filename = "大学物理第1次作业";
	    System.out.println(filename); 	     
	    //设置文件MIME类型 
	    response.setContentType(getServletContext().getMimeType(filename)); 
	    //设置Content-Disposition 
	    response.setHeader("Content-Disposition", "attachment;filename="+filename); 
	    //读取目标文件，通过response将目标文件写到客户端 
	    //获取目标文件的绝对路径 
	    String fullFileName = getServletContext().getRealPath(filename); 
	    //System.out.println(fullFileName); 
	    //读取文件 
	    InputStream in = new FileInputStream(fullFileName); 
	    OutputStream out = response.getOutputStream();    
	    //写文件 
	    int b; 
	    while((b=in.read())!= -1) 
	    { 
	      out.write(b); 
	    } 
	     
	    in.close(); 
	    out.close(); 
	  } 

}
