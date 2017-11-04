package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hzu.feirty.entity.Construction;
import com.hzu.feirty.entity.Course;
import com.hzu.feirty.entity.WorkMade;

public class WorkMadeDaoImpl extends BaseDaoImpl {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	/**
	 * 发布作业信息记录表
	 * @param homework
	 * @return
	 */
	public boolean inSert(WorkMade workmade){
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("insert into makework(work_name,work_content,start_time,teacher_name,course_name,work_number,students,receive_state)values(?,?,?,?,?,?,?,?)");
			pstmt.setString(1, workmade.getWork_name());
			pstmt.setString(2, workmade.getWork_content());
			pstmt.setTimestamp(3, workmade.getStart_time());
			pstmt.setString(4, workmade.getTeacher_name());
			pstmt.setString(5, workmade.getCourse_name());
			pstmt.setInt(6, workmade.getWork_times());
			pstmt.setInt(7, workmade.getStudents());
			pstmt.setString(8, workmade.getReceive_state());
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
	public List<WorkMade> QueryWorkT(String teacher_name) throws SQLException{
		conn = this.getConnection();
		List<WorkMade> List=new ArrayList<WorkMade>();
		pstmt = conn.prepareStatement("select * from makework where teacher_name='"+teacher_name+"'");
		rs = pstmt.executeQuery();
		while(rs.next()){
			WorkMade workMade = new WorkMade();
			workMade.setWork_name(rs.getString(2));
			workMade.setWork_content(rs.getString(3));
			workMade.setCourse_name(rs.getString(4));
			workMade.setWork_times(rs.getInt(5));
			workMade.setSubmitteds(rs.getInt(6));
			workMade.setStudents(rs.getInt(7));
			workMade.setStart_time(rs.getTimestamp(8));
			workMade.setTeacher_name(rs.getString(9));
			workMade.setReceive_state(rs.getString(10));
			workMade.setUpdate_time(rs.getTimestamp(11));	
			List.add(workMade);			
		}
		this.closeAll(rs, pstmt, conn);
		return List;
	}
	public List<WorkMade> QueryWorkC(String teacher_name,String course_name) throws SQLException{
		conn = this.getConnection();
		List<WorkMade> List=new ArrayList<WorkMade>();
		pstmt = conn.prepareStatement("select * from makework where teacher_name='"+teacher_name+"'and course_name='"+course_name+"'");
		rs = pstmt.executeQuery();
		while(rs.next()){
			WorkMade workMade = new WorkMade();
			workMade.setWork_name(rs.getString(2));
			workMade.setWork_content(rs.getString(3));
			workMade.setCourse_name(rs.getString(4));
			workMade.setWork_times(rs.getInt(5));
			workMade.setSubmitteds(rs.getInt(6));
			workMade.setStudents(rs.getInt(7));
			workMade.setStart_time(rs.getTimestamp(8));
			workMade.setTeacher_name(rs.getString(9));
			workMade.setReceive_state(rs.getString(10));
			workMade.setUpdate_time(rs.getTimestamp(11));		
			List.add(workMade);	
		}
		this.closeAll(rs, pstmt, conn);
		return List;
	}
	//查找
	public String queryState(String name,String tea_name,int work_number) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select receive_state from makework where course_name='" + name + "'and teacher_name='"+tea_name+"'" +
				"and work_number='"+work_number+"'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			String state = rs.getString("receive_state");
			this.closeAll(rs, pstmt, conn);
			return state;
		}else {
			return "未知";
		}
	}
	public boolean changeSate(WorkMade workmade) throws SQLException {
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("update makework set receive_state=? where teacher_name='"
							+ workmade.getTeacher_name()+"'and course_name='"+workmade.getCourse_name()+"' and work_number='"+workmade.getWork_times()+"'");
			pstmt.setString(1,workmade.getReceive_state());
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

	public boolean initSate(WorkMade workmade) throws SQLException {
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("update makework set update_time=?,submit_state=? where teacher_name='"
							+ workmade.getTeacher_name() + "'and course_name='"+workmade.getCourse_name()+"'");
			pstmt.setInt(1, workmade.getWork_times());
			pstmt.setString(2,workmade.getReceive_state());
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
	public boolean changeUpdateTimeAndSbmitteds(WorkMade workmade) throws SQLException {
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("update makework set update_time=?,submitteds=? where teacher_name='"
							+ workmade.getTeacher_name() + "'and course_name='"+workmade.getCourse_name()+"' and work_number='"+workmade.getWork_times()+"'");
			pstmt.setTimestamp(1,workmade.getUpdate_time());
			pstmt.setInt(2, workmade.getSubmitteds());
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
}
