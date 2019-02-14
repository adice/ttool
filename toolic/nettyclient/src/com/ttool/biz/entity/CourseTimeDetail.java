package com.ttool.biz.entity;

import java.util.ArrayList;
import java.util.List;

public class CourseTimeDetail {
	private String weekday;
	private List<String> chapter=new ArrayList<String>(0);
	public String getWeekday() {
		return weekday;
	}
	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	public List<String> getChapter() {
		return chapter;
	}
	public void setChapter(List<String> chapter) {
		this.chapter = chapter;
	}
	
}
