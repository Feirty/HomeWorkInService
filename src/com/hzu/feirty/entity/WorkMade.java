package com.hzu.feirty.entity;

public class WorkMade {
	private int id;
	private String work_name;
	private String work_content;
	private String arrange_time;
	private String teacher_name;
	
	public WorkMade(String workname,String content,String time,String teaname){
		this.work_name = workname;
		this.work_content = content;
		this.arrange_time = time;
		this.teacher_name = teaname;		
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
	public String getArrange_time() {
		return arrange_time;
	}
	public void setArrange_time(String arrange_time) {
		this.arrange_time = arrange_time;
	}
	public String getTeacher_name() {
		return teacher_name;
	}
	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

}
