package com.ttool.biz.entity;

import java.util.Date;

public class SignInRecord {
	private int id;
	private int classId;
	private String stuId;
	private String stuIp;
	private Date signInTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public String getStuId() {
		return stuId;
	}
	public void setStuId(String stuId) {
		this.stuId = stuId;
	}
	public String getStuIp() {
		return stuIp;
	}
	public void setStuIp(String stuIp) {
		this.stuIp = stuIp;
	}
	public Date getSignInTime() {
		return signInTime;
	}
	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}
}
