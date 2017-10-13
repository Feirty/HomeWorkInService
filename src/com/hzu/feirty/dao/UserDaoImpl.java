package com.hzu.feirty.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hzu.feirty.entity.User;

public class UserDaoImpl extends BaseDaoImpl {
	List<User> list = new ArrayList<User>();
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection conn = null;

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	public List<User> Search() {
		conn = this.getConnection();
		try {

			pstmt = conn.prepareStatement("select * from user");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				User content = new User();
				content.setId(rs.getInt("id"));
				content.setUsername(rs.getString("username"));
				content.setUserpwd(rs.getString("password"));
				list.add(content);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;
	}
	
	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	public String SearchType(String name) {
		conn = this.getConnection();
		try {

			pstmt = conn.prepareStatement("select type from user where username='"+name+"'");			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString("type");
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
	 * 查询某用户是否存在，根据usename
	 *
	 * @return
	 */
	public User Search(String username) {
		conn = this.getConnection();
		User content = null;
		try {

			pstmt = conn.prepareStatement("select * from user where username='"
					+ username + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new User();
				content.setId(rs.getInt("id"));
				content.setUsername(rs.getString("username"));
				content.setUserpwd(rs.getString("password"));
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
	 * 查询某用户是否存在，根据用户名跟密码
	 * 
	 * @return
	 */
	public List<User> Search(String username, String password) {
		conn = this.getConnection();
		User content = null;
		try {

			pstmt = conn.prepareStatement("select * from user where username='"
					+ username + "'and password='" + password + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				content = new User();
				content.setId(rs.getInt("id"));
				content.setUsername(rs.getString("username"));
				content.setUserpwd(rs.getString("password"));
				list.add(content);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			this.closeAll(rs, pstmt, conn);

		}
		return list;

	}

	/**
	 * 添加用户数据
	 * 
	 * @param news
	 * @throws SQLException 
	 */
	public boolean Save(User user) throws SQLException {
		conn = this.getConnection();
		if(!find(user.getUsername())){
		try {
			pstmt = conn
					.prepareStatement("insert into user(username,password,type)values(?,?,?)");
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getUserpwd());
			pstmt.setString(3, "0");
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
		}else{
			return false;
		}
	}

	/**
	 * 用户修改密码或者其他信息
	 * 
	 * @param news
	 */
	public boolean update_message(User user) {
		conn = this.getConnection();
		try {
			pstmt = conn
					.prepareStatement("update user set password=? where username='"
							+ user.getUsername() + "'");

			pstmt.setString(1, user.getUserpwd());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}
	
	/**
	 * 用户修改type
	 * 
	 * @param news
	 */
	public boolean updateType(String type,String name) {
		conn = this.getConnection();
		try {
			pstmt = conn
					.prepareStatement("update user set type=? where username='"
							+ name + "'");
			pstmt.setString(1, type);
			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			this.closeAll(null, pstmt, conn);
		}
	}
	
	//查找
	public boolean find(String name) throws SQLException{
		conn = this.getConnection();
		pstmt = conn.prepareStatement("select * from user where username='" + name + "'");
		rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
	}	
}

