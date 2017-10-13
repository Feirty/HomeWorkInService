package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.hzu.feirty.entity.Email;

public class EmailDaoImpl extends BaseDaoImpl{
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	
	/**
	 * 信息记录表
	 * @param homework
	 * @return
	 */
	public boolean Insert(Email email){
		conn = this.getConnection();
		try {
			pstmt = conn.prepareStatement("insert into email(mail_from,mail_subject,mail_sentdata,mail_content,mail_messageid,mail_attachment_name)values(?,?,?,?,?,?)");
			pstmt.setString(1,email.getFrom());
			pstmt.setString(2,email.getSubject());
			pstmt.setString(3,email.getSentdata());
			pstmt.setString(4,email.getContent());
			pstmt.setString(5,email.getMessageID());
			pstmt.setString(6,email.getAttachmentname());
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
	 * 查询收取作业信息表是否为空
	 * @return boolean
	 * 
	 */
	public boolean queryIsNull(){
		conn = this.getConnection();
		try{
			pstmt = conn.prepareStatement("select * from email");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}finally {
			this.closeAll(null, pstmt, conn);
		}
		return true;		
	}
	
	public Date queryTime(){
		conn = this.getConnection();
		try{
			pstmt = conn.prepareStatement("select mail_sentdata from email where id=(select max(id) from email)");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getDate("mail_sentdata");
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
	

}
