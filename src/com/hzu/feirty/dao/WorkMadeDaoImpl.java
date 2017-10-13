package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hzu.feirty.entity.Construction;
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
			pstmt = conn.prepareStatement("insert into makework(work_name,work_content,arrange_time,teacher_name)values(?,?,?,?)");
			pstmt.setString(1, workmade.getWork_name());
			pstmt.setString(2, workmade.getWork_content());
			pstmt.setString(3, workmade.getArrange_time());
			pstmt.setString(4, workmade.getTeacher_name());
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
