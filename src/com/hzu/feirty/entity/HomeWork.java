package com.hzu.feirty.entity;

import java.sql.Timestamp;

public class HomeWork {
	private String id;
	private String stu_id;
	private String file_name;
	private String file_size;
	private Timestamp file_time;
	private int file_number;
	private String course_name;
	private String teacher_name;
	private String submit_state;
	private String receive_state;
	public HomeWork(){
		super();
	}
	public HomeWork(String stu_id,String teacher,String course_name){
		this.stu_id =stu_id;
		this.teacher_name = teacher;
		this.course_name = course_name;
	}
	public HomeWork(int work_number,String teacher,String course_name){
		this.file_number =work_number;
		this.teacher_name = teacher;
		this.course_name = course_name;
	}
	public HomeWork(String stu_id,String course_name,int work_number){
		this.file_number =work_number;
		this.stu_id =stu_id;
		this.course_name = course_name;
	}
	public HomeWork(String stu_id,String teacher,String course_name,int file_number,String submit_state){
		this.stu_id =stu_id;
		this.file_number = file_number; 
		this.teacher_name = teacher;
		this.course_name = course_name;
		this.submit_state =submit_state;
	}
	
	public HomeWork(String number, String filename, String filesize,
			Timestamp sendtime, String name, String course, int work_int) {
		// TODO Auto-generated constructor stub
		this.stu_id =number;
		this.file_name =filename;
		this.file_size =filesize;
		this.file_time = sendtime;
		this.teacher_name = name;
		this.course_name =course;
		this.file_number = work_int;
	}
	public String getSubmit_state() {
		return submit_state;
	}
	public void setSubmit_state(String submit_state) {
		this.submit_state = submit_state;
	}
	public String getReceive_state() {
		return receive_state;
	}
	public void setReceive_state(String receive_state) {
		this.receive_state = receive_state;
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
	public Timestamp getFile_time() {
		return file_time;
	}
	public void setFile_time(Timestamp file_time) {
		this.file_time = file_time;
	}
	
	public int getFile_number() {
		return file_number;
	}
	public void setFile_number(int file_number) {
		this.file_number = file_number;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}
	public String  getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
