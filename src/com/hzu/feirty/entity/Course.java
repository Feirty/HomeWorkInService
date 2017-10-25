package com.hzu.feirty.entity;

public class Course {
	private String name;
	private int stu_number;
	private String school;
	private String tea_name;
	private String state;
	private int works;
	
	public  Course(){
		super();
	}
	public Course(String course,String teacher,int work_number,String state){
		this.name = course;
		this.tea_name =teacher;
		this.works = work_number;
		this.state =state;
	}
	
	public int getWorks() {
		return works;
	}
	public void setWorks(int works) {
		this.works = works;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getStu_number() {
		return stu_number;
	}
	public void setStu_number(int stu_number) {
		this.stu_number = stu_number;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getTea_name() {
		return tea_name;
	}
	public void setTea_name(String tea_name) {
		this.tea_name = tea_name;
	}

}
