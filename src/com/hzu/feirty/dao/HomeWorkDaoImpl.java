package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.hzu.feirty.entity.HomeWork;

public class HomeWorkDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	/**
	 * 作业信息记录表
	 * @param homework
	 * @return
	 * @throws SQLException 
	 */
	public boolean inSert(HomeWork homework) throws SQLException{
		conn = this.getConnection();
		if(true){    //!isExist(homework.getStu_id())
			try {
				pstmt = conn.prepareStatement("insert into homework(stu_id,file_name,file_size,file_time,course_name,file_number)values(?,?,?,?,?,?)");
				pstmt.setString(1, homework.getStu_id());
				pstmt.setString(2, homework.getFile_name());
				pstmt.setString(3, homework.getFile_size());
				pstmt.setTimestamp(4, homework.getFile_time());
				pstmt.setString(5, homework.getCourse_name());
				pstmt.setInt(6, homework.getFile_number());
				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();			
			}finally {
				this.closeAll(null, pstmt, conn);
			}			
		}
		return false;
	}
	/*
	 * 清除已有学生数据
	 */
	public void deleteDate(String stu_id,String course) throws SQLException{
		conn = this.getConnection();
		if(isExist(stu_id, course)){
			pstmt = conn.prepareStatement("delete  from homework where stu_id = '"+stu_id+"' and" +
					" course_name='"+course+"'");
			pstmt.executeUpdate();
		}
	}
	/*
	 * 查询学生数据是否存在by 学生id，课程
	 * 
	 */
	public boolean isExist(String stu_id,String course) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from homework where stu_id='" + stu_id + "' and" +
				" course_name='"+course+"'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	
	/*
	 * 查询学生数据是否存在bymail_id
	 * 
	 */
	public boolean isExistMailid(String id,String stu_number,String course) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from homework where id='" + id +"'" +
				"and stu_id = '"+stu_number+"' and course_name = '"+course+"'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
	/*
	 * 查询所有的作业数据
	 * @return list<HomeWork>
	 * 
	 */
	@SuppressWarnings("finally")
	public List<HomeWork> QueryAll() throws SQLException{
		conn = this.getConnection();
		List<HomeWork> homeworklist = new ArrayList<HomeWork>();
		try {
			pstmt = conn.prepareStatement("select * from homework");
			rs = pstmt.executeQuery();
			while(rs.next()){				
				HomeWork homework=new HomeWork();
				homework.setId(rs.getString(1));
				homework.setStu_id(rs.getString(2));
				homework.setFile_name(rs.getString(3));
				homework.setFile_size(rs.getString(4));
				homework.setFile_time(rs.getTimestamp(5));
				homework.setCourse_name(rs.getString(6));
				homework.setFile_number(rs.getInt(7));
				homeworklist.add(homework);
				}
			
		} catch (Exception e) {
			homeworklist.add(null);
			return homeworklist;
		}finally {
			this.closeAll(null, pstmt, conn);
			return homeworklist;
		}			
	}
	/*
	 * 查询收取作业信息表是否为空
	 * @return boolean
	 * 
	 */
	public boolean queryIsNull(){
		conn = this.getConnection();
		try{
			pstmt = conn.prepareStatement("select * from homework");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		}finally {
			this.closeAll(null, pstmt, conn);
		}		
	}
	
	public Date queryTime(){
		conn = this.getConnection();
		try{
			pstmt = conn.prepareStatement("select file_time from homework where id=(select max(id) from homework)");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getDate("file_time");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			this.closeAll(null, pstmt, conn);
		}
		return null;		
	}
}
