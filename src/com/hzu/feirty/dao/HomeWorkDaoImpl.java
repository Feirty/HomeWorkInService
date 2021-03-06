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
	public boolean updateData(HomeWork homework) throws SQLException{
		conn = this.getConnection();
		if(true){    //!isExist(homework.getStu_id())
			try {
				pstmt = conn.prepareStatement("update homework set file_name=?,file_size=?,file_time=? where stu_id='"+homework.getStu_id()+"' AND" +
						" where course_name='"+homework.getCourse_name()+"' and where file_number='"+homework.getFile_number()+"'");
				pstmt.setString(1, homework.getFile_name());
				pstmt.setString(2, homework.getFile_size());
				pstmt.setTimestamp(3, homework.getFile_time());
				pstmt.setInt(4, homework.getFile_number());
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
	
	
	public boolean save(HomeWork homework) throws SQLException{
		conn = this.getConnection();
		if(!isExist(homework.getStu_id(),homework.getCourse_name())){    
			try {
				pstmt = conn.prepareStatement("insert into homework(stu_id,course_name,teacher_name,file_number,submit_state)values(?,?,?,?,?)");
				pstmt.setString(1, homework.getStu_id());	
				pstmt.setString(2, homework.getCourse_name());
				pstmt.setString(3, homework.getTeacher_name());
				pstmt.setInt(4, homework.getFile_number());
				pstmt.setString(5, homework.getSubmit_state());
				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();			
			}finally {
				this.closeAll(null, pstmt, conn);
			}			
		}
		return false;
	}
	
	public boolean updateTimes(HomeWork homework) throws SQLException{
		conn = this.getConnection();
		if(true){    
			try {
				pstmt = conn.prepareStatement("update homework set file_number=?,submit_state=? where teacher_name='"+homework.getTeacher_name()+"' and course='"+homework.getCourse_name()+"'");
				pstmt.setInt(1, homework.getFile_number());
				pstmt.setString(2, "未提交");
				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();			
			}finally {
				this.closeAll(null, pstmt, conn);
			}			
		}
		return false;
	}
	
	public boolean updateName(HomeWork homework) throws SQLException{
		conn = this.getConnection();
		if(!isExist(homework.getStu_id(),homework.getCourse_name())){    
			try {
				pstmt = conn.prepareStatement("update homework set id=? where stu_id='"+homework.getStu_id()+"' and course='"+homework.getCourse_name()+"'");
				pstmt.setString(1, homework.getId());	
				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();			
			}finally {
				this.closeAll(null, pstmt, conn);
			}			
		}
		return false;
	}
	public boolean updateSumbitSate(HomeWork homework) throws SQLException{
		conn = this.getConnection();
		if(!isExist(homework.getStu_id(),homework.getCourse_name())){    
			try {
				pstmt = conn.prepareStatement("update homework set submit_state=? where id='"+homework.getId()+"' and course='"+homework.getCourse_name()+"' and file_number='"+homework.getFile_number()+"'");
				pstmt.setString(1, homework.getSubmit_state());	
				pstmt.executeUpdate();
				return true;
			} catch (SQLException e) {
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
	 * 查询所有的作业数据,参数：学生id，课程号
	 * @return list<HomeWork>
	 * 
	 */
	@SuppressWarnings("finally")
	public List<HomeWork> QueryAll(String stu_id,String course_name) throws SQLException{
		conn = this.getConnection();
		List<HomeWork> homeworklist = new ArrayList<HomeWork>();
		try {
			pstmt = conn.prepareStatement("select * from homework where stu_id = '"+stu_id+"'" +
					"and course_name = '"+course_name+"'");
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
	 * 查询所有的作业数据,参数：学生id，课程号
	 * @return list<HomeWork>
	 * 
	 */
	@SuppressWarnings("finally")
	public List<HomeWork> QueryAll2(String teacher_name,String course_name) throws SQLException{
		conn = this.getConnection();
		List<HomeWork> homeworklist = new ArrayList<HomeWork>();
		try {
			pstmt = conn.prepareStatement("select * from homework where teacher_name = '"+teacher_name+"'" +
					"and course_name = '"+course_name+"'");
			rs = pstmt.executeQuery();
			while(rs.next()){				
				HomeWork homework=new HomeWork();
				homework.setId(rs.getString(1));
				homework.setStu_id(rs.getString(2));
				homework.setFile_name(rs.getString(3));
				homework.setFile_size(rs.getString(4));
				homework.setFile_time(rs.getTimestamp(5));
				homework.setCourse_name(rs.getString(6));
				homework.setTeacher_name(rs.getString(7));
				homework.setFile_number(rs.getInt(8));			
				homeworklist.add(homework);
				}
		} catch (Exception e) {		
			return null;
		}finally {
			this.closeAll(null, pstmt, conn);
			return null;
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

	public boolean querySubmit_state(HomeWork homeWork){
		conn = this.getConnection();
		try{
			pstmt = conn.prepareStatement("select submit_state from homework where stu_id='"+homeWork.getStu_id()+"' " +
					"and course_name='"+homeWork.getCourse_name()+"' and file_number='"+homeWork.getFile_number()+"'");
			rs = pstmt.executeQuery();
			if(rs.next()){
				String a =rs.getString("submit_state");
				if(a.equals("已提交")){
					return true;		
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			this.closeAll(null, pstmt, conn);
		}
		return false;		
	}
}
