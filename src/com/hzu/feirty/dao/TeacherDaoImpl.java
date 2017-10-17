package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzu.feirty.entity.Teacher;
import com.sun.org.apache.regexp.internal.recompile;


public class TeacherDaoImpl extends BaseDaoImpl {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	//添加用户
	 public boolean add(Teacher user) throws Exception{
		 conn = this.getConnection();
		 try {
			 String sql = "insert into teacher(user_teacher,mail_name,mail_pwd,peasonmail,school,nickname) values (?,?,?,?,?,?) ";
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1,user.getUser_teacher());
			 pstmt.setString(2,user.getMail_name());
			 pstmt.setString(3,user.getMail_pwd());
			 pstmt.setString(4,user.getPeasonmail());
			 pstmt.setString(5,user.getSchool());
			 pstmt.setString(6,user.getNickname());
			 pstmt.executeUpdate();
			 return true;
		 }catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
		 finally {this.closeAll(null, pstmt, conn);}
	   }
	 
	//查找
	public boolean find(String name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from teacher where user_teacher='" + name + "'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 查找老师的邮箱账号和密码
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public Teacher find2(String name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from teacher where nickname='" + name + "'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			Teacher tea =new Teacher();
			tea.setMail_name(rs.getString("mail_name"));
			tea.setMail_pwd(rs.getString("mail_pwd"));
			tea.setPeasonmail(rs.getString("peasonmail"));
			return tea;
		}else {
			return null;
		}
	}		
}
