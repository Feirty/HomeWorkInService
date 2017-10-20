package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.hzu.feirty.entity.Student;

public class StudentDaoImpl extends BaseDaoImpl {
	List<Student> list = new ArrayList<Student>();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
   public StudentDaoImpl(){
		super();
	}
	/**
	 * 添加用户学号数据
	 * 
	 * @param news
	 * @throws SQLException 
	 */
	public boolean Save(Student user) throws SQLException {
		conn = this.getConnection();
		if(!find(user)){
		try {
			pstmt = conn.prepareStatement("insert into student(number,course,teacher)values(?,?,?)");
			pstmt.setString(1, user.getNumber());
			pstmt.setString(2, user.getCourse());
			pstmt.setString(3, user.getTeacher());
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
		return false;
	}
	//添加 老师name、邮箱
	public boolean update(Student user) throws SQLException {
		conn = this.getConnection();
		if(find(user)){
		try {
			pstmt = conn.prepareStatement("update student set name=?,teacher=?,mail=?,school=? where number='"
							+ user.getNumber() + "'");
			//pstmt.setString(1, user.getName());
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getTeacher());
			pstmt.setString(3, user.getMail());
			pstmt.setString(4, user.getSchool());
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
		return false;
	}
	
	/*
	 * 根据student对象查找
	 * 
	 */
	public boolean find(Student student) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from student where number='" + student.getNumber() +"'AND teacher='" + student.getTeacher() + "'AND course='"+student.getCourse()+"'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * 根据学号查找
	 * 
	 */
	public boolean find(String number,String teacher,String course) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from student where number='" + number + "' and teacher='"+teacher+"' and course='"+course+"'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 查询某用户是否存在，根据usename
	 *
	 * @return
	 */
	public Student Search(String name) {
		conn = this.getConnection();
		Student content = null;
		try {
			pstmt = conn.prepareStatement("select * from student where name='"
					+ name + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new Student();
				content.setId(rs.getInt("id"));
				content.setName(rs.getString("name"));
				content.setTeacher(rs.getString("teacher"));
				content.setCourse(rs.getString("course"));
				return content;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return null;

	}
	/**
	 * 查询某用户是否存在，根据teacher
	 *
	 * @return
	 */
	public boolean isNumber(String teacher,String number) {
		conn = this.getConnection();
		//Student content = null;
		try {
			pstmt = conn.prepareStatement("select * from student where teacher='"+ teacher + "'and number='"+number+"'");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return false;
	}
	
	/**
	 * 查询某用户是否存在，根据teacher
	 *
	 * @return
	 */
	public boolean isExit(String teacher,String number,String course) {
		conn = this.getConnection();
		//Student content = null;
		try {
			pstmt = conn.prepareStatement("select * from student where teacher='"+ teacher + "'and number='"+number+"' and course='"+course+"'");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return false;
	}
	
	public boolean isNumber2(String number) {
		conn = this.getConnection();
		//Student content = null;
		try {
			pstmt = conn.prepareStatement("select * from student where number='"+number+"'");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return false;
	}
	
	
	public boolean delete(String teacher,String course) throws Exception{
		 conn = this.getConnection();
		 int i =0;
		 try {
			pstmt = conn.prepareStatement("delete from student where teacher='"+teacher+"'and course='"+course+"'");
			i =pstmt.executeUpdate();
			return true;		
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
	}
	
	public List<String> QueryTeacher(String name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select teacher from student where name='"+name+"'");
		rs = pstmt.executeQuery();
		List<String> list = new ArrayList<String>();
		while(rs.next()){			 
			   list.add(rs.getString("teacher"));
		}
		return list;
	}
	
	public List<String> QueryCourse(String name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select course from student where name='"+name+"'");
		rs = pstmt.executeQuery();
		List<String> list = new ArrayList<String>();
		while(rs.next()){			 
			   list.add(rs.getString("course"));
		}
		return list;
	}
	
	/**
	 * 查询某用户是否存在，根据teacher
	 *
	 * @return
	 */
	public List<Student> QueryStudent(String teacher,String course) {
		conn = this.getConnection();
		//Student content = null;
		try {
			pstmt = conn.prepareStatement("select * from student where teacher='"+ teacher + "'and course='"+course+"'");
			rs = pstmt.executeQuery();
			List<Student> list = new ArrayList<Student>();
			while(rs.next()) {
				Student student = new Student();
				student.setName(rs.getString(2));
				student.setNumber(rs.getString(3));
				list.add(student);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);
		}
		return null;
	}
}
