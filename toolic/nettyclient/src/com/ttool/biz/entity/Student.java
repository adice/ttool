package com.ttool.biz.entity;

public class Student {
	private String id;
	private String name;
	private String password;
	private String gender;
	private int stuGrade;
	private int stuClass;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getStuGrade() {
		return stuGrade;
	}
	public void setStuGrade(int stuGrade) {
		this.stuGrade = stuGrade;
	}
	public int getStuClass() {
		return stuClass;
	}
	public void setStuClass(int stuClass) {
		this.stuClass = stuClass;
	}
}
