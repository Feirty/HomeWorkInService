package com.hzu.feirty.entity;

public class Teacher {
	private Integer id;
	private String user_teacher;
	private String mail_name;
	private String mail_pwd;
	private String school;
	private String peasonmail;
	private String nickname;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nikename) {
		this.nickname = nikename;
	}
	public Teacher(){
		super();
	}
	public Teacher(String teaher,String mailname,String pwd,String school ,String peasonmail,String nickname){
		this.user_teacher=teaher;
		this.mail_name= mailname;
		this.mail_pwd=pwd;
		this.school=school;
		this.peasonmail=peasonmail;
		this.nickname= nickname;
	}
	
	public String getPeasonmail() {
		return peasonmail;
	}
	public void setPeasonmail(String peasonmail) {
		this.peasonmail = peasonmail;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public Teacher(String user,String name,String pwd){
		this.user_teacher = user;
		this.mail_name = name;
		this.mail_pwd = pwd;	
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUser_teacher() {
		return user_teacher;
	}
	public void setUser_teacher(String user_teacher) {
		this.user_teacher = user_teacher;
	}
	public String getMail_name() {
		return mail_name;
	}
	public void setMail_name(String mail_name) {
		this.mail_name = mail_name;
	}
	public String getMail_pwd() {
		return mail_pwd;
	}
	public void setMail_pwd(String mail_pwd) {
		this.mail_pwd = mail_pwd;
	}

}
