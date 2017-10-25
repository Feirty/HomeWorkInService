package com.hzu.feirty.entity;

import java.sql.Date;
import java.sql.Timestamp;

public class Construction {
	private String teacher_name;
	private String course_name;
	private long number;
	private String zipname;
	private String zipsize;
	private Timestamp time;
	private String work_made_number;
	
	public Construction(String teacher_name,long number,String zipname,String zipsize,String course_name,Timestamp time){
		this.teacher_name = teacher_name;
		this.course_name = course_name;
		this.number= number;
		this.zipname = zipname;
		this.zipsize = zipsize;
		this.time = time;
		
	}
	
	public String getWork_made_number() {
		return work_made_number;
	}
	public void setWork_made_number(String work_made_number) {
		this.work_made_number = work_made_number;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getZipname() {
		return zipname;
	}
	public void setZipname(String zipname) {
		this.zipname = zipname;
	}
	public String getZipsize() {
		return zipsize;
	}
	public void setZipsize(String zipsize) {
		this.zipsize = zipsize;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}

}
