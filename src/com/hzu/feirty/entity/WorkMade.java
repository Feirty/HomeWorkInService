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
	private int work_times;
	private int submitteds;
	private int students;
	private String submit_state;
	private String receive_state;
	private Timestamp update_time;
	public WorkMade(){
		super();
	}
	public WorkMade(String workname,String content,String course_name,Integer work_times,Timestamp start_time,String teaname,String course,int students,String string){
		this.work_name = workname;
		this.work_content = content;
		this.work_times = work_times;
		this.teacher_name = teaname;
		this.start_time = start_time;
		this.course_name = course;
		this.students = students;
		this.receive_state = string;
		
	}
	
	public WorkMade(String user, String course, int number, String string) {
		// TODO Auto-generated constructor stub
		this.teacher_name = user;
		this.course_name =course;
		this.work_times =number;
		this.receive_state = string;
	}
	public WorkMade(String user, String course, int homework_time, int submitteds,Timestamp update_time) {
		// TODO Auto-generated constructor stub
		this.teacher_name = user;
		this.course_name =course;
		this.work_times =homework_time;
		this.submitteds = submitteds;
		this.update_time =update_time;
	}
	public WorkMade(String user, String course, int number, String string,Timestamp update_time) {
		// TODO Auto-generated constructor stub
		this.teacher_name = user;
		this.course_name =course;
		this.work_times =number;
		this.receive_state = string;
		this.update_time = update_time;
	}
	
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public int getSubmitteds() {
		return submitteds;
	}
	public void setSubmitteds(int submitteds) {
		this.submitteds = submitteds;
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
	public int getStudents() {
		return students;
	}
	public void setStudents(int students) {
		this.students = students;
	}
	
	public Timestamp getStart_time() {
		return start_time;
	}
	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}
	public int getWork_times() {
		return work_times;
	}
	public void setWork_times(int work_times) {
		this.work_times = work_times;
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
