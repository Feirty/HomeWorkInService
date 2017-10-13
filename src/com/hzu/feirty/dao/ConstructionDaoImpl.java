package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.hzu.feirty.entity.Construction;
import com.hzu.feirty.entity.HomeWork;
import com.sun.org.apache.xpath.internal.operations.And;

public class ConstructionDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	/**
	 * 作业附件信息记录表
	 * @param homework
	 * @return
	 */
	public boolean inSert(Construction construction){
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("insert into construction(teacher_name,number,zipname,zipsize,time)values(?,?,?,?,?)");
			pstmt.setString(1, construction.getTeacher_name());
			pstmt.setLong(2, construction.getNumber());
			pstmt.setString(3, construction.getZipname());
			pstmt.setString(4, construction.getZipsize());
			pstmt.setTimestamp(5, construction.getTime());
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
	/*
	 * 查询收取作业的最新时间
	 * @return Date
	 * 
	 */
	public Date queryTime(String name){
		conn = this.getConnection();
		try{
			pstmt = conn.prepareStatement("select time from construction where id=(select max(id) from construction where teacher_name='"+name+"')");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getTimestamp("time");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			this.closeAll(null, pstmt, conn);
		}
		return null;		
	}
	
	/*
	 * 查询收取作业信息表是否为空
	 * @return boolean
	 * 
	 */
	public boolean queryIsNull(){
		conn = this.getConnection();
		try{
			pstmt = conn.prepareStatement("select * from construction");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			this.closeAll(null, pstmt, conn);
		}		
	}
}
