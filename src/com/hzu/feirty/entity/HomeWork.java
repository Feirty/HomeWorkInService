package com.hzu.feirty.entity;

import java.sql.Timestamp;

public class HomeWork {
	private String id;
	private String stu_id;
	private String file_name;
	private String file_size;
	private Timestamp file_time;
	private String course_name;
	private int file_number;
	public HomeWork(){
		super();
	}
	public HomeWork(String id,String stu_id,String file_name,String file_size,Timestamp file_time,String course_name,int file_number){
		this.stu_id =stu_id;
		this.file_name =file_name;
		this.file_size =file_size;
		this.file_time = file_time;	
		this.file_number = file_number; 
		this.course_name = course_name;
	}
	
	public int getFile_number() {
		return file_number;
	}
	public void setFile_number(int file_number) {
		this.file_number = file_number;
	}
	
	public Timestamp getFile_time() {
		return file_time;
	}
	public void setFile_time(Timestamp file_time) {
		this.file_time = file_time;
	}
	public String  getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_size() {
		return file_size;
	}
	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

}
