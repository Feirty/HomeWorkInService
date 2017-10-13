package com.hzu.feirty.entity;

public class Student {
	private Integer id;
	private String name;
	private String number;
	private Integer worksnumber;
	private String mail;
	private String teacher;
	private String school;
	private String course;
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public Student(){
		super();
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getWorksnumber() {
		return worksnumber;
	}
	public void setWorksnumber(Integer worksnumber) {
		this.worksnumber = worksnumber;
	}

}
