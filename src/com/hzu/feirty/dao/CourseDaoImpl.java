package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.hzu.feirty.entity.Course;
import com.sun.org.apache.regexp.internal.recompile;

public class CourseDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	List<Course> list = null;
	
	
	public boolean inSert(Course course) throws SQLException{
		conn = this.getConnection();
		if(!find(course.getName(),course.getTea_name())){
			try {
				pstmt = conn.prepareStatement("insert into course(name,stu_number,tea_name,works_number)values(?,?,?,?)");
				pstmt.setString(1, course.getName());
				pstmt.setInt(2,course.getStu_number());
				pstmt.setString(3,course.getTea_name());
				pstmt.setInt(4, 0);
				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}finally {
				this.closeAll(null, pstmt, conn);
			}				
		}else{
			return false;
		}		
	}
	//更新
	public boolean update(Course course) throws SQLException {
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("update course set stu_number=? where tea_name='"
							+ course.getTea_name() + "'and name='"+course.getName()+"'");
			pstmt.setInt(1, course.getStu_number());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			this.closeAll(null, pstmt, conn);
		}
	}
	//查找
	public boolean find(String name,String tea_name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from course where name='" + name + "'and tea_name='"+tea_name+"'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	//查找
	public boolean find2(String name,String tea_name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from course where name='" + name + "'and tea_name='"+tea_name+"'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	//查询课程发布作业的次数
	public boolean queryWorks_number(String teacher_name,String course_name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select works_number from course where tea_name = " +
				"'"+teacher_name+"' and name = '"+course_name+"'");
		rs = pstmt.executeQuery();
		if(rs.next()){
			int old_number = rs.getInt("works_number");
			old_number++;
			conn.prepareStatement("update course set works_number = '"+old_number+"' where tea_name = " +
					"'"+teacher_name+"' and name = '"+course_name+"'");
			return true;
		}
		return false;
	}
				
	/*
	 * 查找学生的总人数
	 * 
	 */
	public int findNumber(String tea_name,String course) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select stu_number from course where tea_name='" + tea_name + "' and name='"+course+"'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("stu_number");
		}else {
			return -1;
		}
	}
	public List<String> QueryCourse() throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select name from course");
		rs = pstmt.executeQuery();
		List<String> list = new ArrayList<String>();
		while(rs.next()){		 
			   list.add(rs.getString("name"));
		}
		return list;
	}
	
	public List<Course> QueryByTeacher(String teacher) throws SQLException{
		conn = this.getConnection();
		List<Course> List=new ArrayList<Course>();
		pstmt = conn.prepareStatement("select * from course where tea_name ='"+teacher+"'");
		rs = pstmt.executeQuery();
		while(rs.next()){
			   Course course = new Course();
			   course.setName(rs.getString(2));  
			   course.setStu_number(rs.getInt(3));
			   course.setWorks(rs.getInt(4));
			   List.add(course);
		}
		return List;
	}
}
