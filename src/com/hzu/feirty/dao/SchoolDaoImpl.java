package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.hzu.feirty.entity.School;

public class SchoolDaoImpl extends BaseDaoImpl {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;
	List<School> list;
	
	public boolean inSert(String name) throws SQLException{
		conn = this.getConnection();
		if(!find(name)){
			try {
				pstmt = conn.prepareStatement("insert into school(name)values(?)");
				pstmt.setString(1, name);
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
	
	//查找
	public boolean find(String name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from school where name='" + name + "'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}
		/*
		 * 查找总数
		 * 
		 */
	public List Query() throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from school");
		rs = pstmt.executeQuery();
		while(rs.next()){
			   School school=new School();
			   school.setId(rs.getInt(1));
			   school.setName(rs.getString(2));  
			   list.add(school);
		}
		return list;
	}	

}
