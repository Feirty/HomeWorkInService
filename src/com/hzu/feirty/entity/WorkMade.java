package com.hzu.feirty.entity;

import java.sql.Timestamp;

public class WorkMade {
	private int id;
	private String work_name;
	private String work_content;
	private Timestamp start_time;
	private Timestamp end_time;
	private String teacher_name;
	private String course_name;
	private String work_number;
	
	public WorkMade(){
		super();
	}
	public WorkMade(String workname,String content,String course_name,Timestamp start_time,Timestamp end_time,String teaname,String course){
		this.work_name = workname;
		this.work_content = content;
		this.end_time = end_time;
		this.teacher_name = teaname;
		this.start_time = start_time;
		this.course_name = course;
	}
	
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public String getWork_number() {
		return work_number;
	}
	public void setWork_number(String work_number) {
		this.work_number = work_number;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWork_name() {
		return work_name;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public String getWork_content() {
		return work_content;
	}
	public void setWork_content(String work_content) {
		this.work_content = work_content;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	
	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

}
