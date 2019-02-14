package com.ttool.biz.entity;

import java.util.ArrayList;
import java.util.List;

public class CourseTime {
	
	private String week;
	private List<CourseTimeDetail> weekdays=new ArrayList<CourseTimeDetail>(0);
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public List<CourseTimeDetail> getWeekdays() {
		return weekdays;
	}
	public void setWeekdays(List<CourseTimeDetail> weekdays) {
		this.weekdays = weekdays;
	}
}
