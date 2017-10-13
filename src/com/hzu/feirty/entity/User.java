package com.hzu.feirty.entity;

public class User {
	private Integer id;
	private String username;
	private String userpwd;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String type;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public User( String username, String password){
		this.username= username;
		this.userpwd = password;
	}
	public User(){
		super();
	}

}
