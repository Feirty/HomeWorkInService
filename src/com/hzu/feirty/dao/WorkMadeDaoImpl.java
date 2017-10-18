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
			pstmt = conn.prepareStatement("insert into makework(work_name,work_content,start_time,end_time,teacher_name,course_name)values(?,?,?,?,?,?)");
			pstmt.setString(1, workmade.getWork_name());
			pstmt.setString(2, workmade.getWork_content());
			pstmt.setTimestamp(3, workmade.getStart_time());
			pstmt.setTimestamp(4, workmade.getEnd_time());
			pstmt.setString(5, workmade.getTeacher_name());
			pstmt.setString(6, workmade.getCourse_name());
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
			workMade.setTeacher_name(rs.getString(6));
			workMade.setWork_name(rs.getString(1));
			workMade.setCourse_name(rs.getString(3));
			workMade.setWork_number(rs.getString(4));
			workMade.setWork_content(rs.getString(2));
			List.add(workMade);			
		}
		return List;
	}
	public List<WorkMade> QueryWorkC(String teacher_name,String course_name) throws SQLException{
		conn = this.getConnection();
		List<WorkMade> List=new ArrayList<WorkMade>();
		pstmt = conn.prepareStatement("select * from makework where teacher_name='"+teacher_name+"'and course_name='"+course_name+"'");
		rs = pstmt.executeQuery();
		while(rs.next()){
			WorkMade workMade = new WorkMade();
			workMade.setTeacher_name(rs.getString(6));
			workMade.setWork_name(rs.getString(1));
			workMade.setCourse_name(rs.getString(3));
			workMade.setWork_number(rs.getString(4));
			workMade.setWork_content(rs.getString(2));
			List.add(workMade);			
		}
		return List;
	}
}
