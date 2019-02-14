package com.ttool.biz.actualclass;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ttool.biz.entity.ActualClass;
import com.ttool.biz.entity.CourseTime;
import com.ttool.biz.entity.CourseTimeDetail;
import com.ttool.util.Constant;

public class ActualClassServiceImpl {

	/**
	 * 获取当前课程
	 * 
	 * @return
	 */
	public List<ActualClass> getTodayClasses() {
		// 查询本学期某老师所有课程班级
		ActualClassDaoImpl actualClassDaoImpl = new ActualClassDaoImpl();
		List<ActualClass> list = actualClassDaoImpl.findByTeacherId(Constant.teacher.getId(), Constant.academicYear,
				Constant.semester);
		// 计算当前周数
		String years[] = Constant.startDate.split("-");
		LocalDate start = LocalDate.of(Integer.parseInt(years[0]), Integer.parseInt(years[1]),
				Integer.parseInt(years[2]));
		LocalDate nowDate = LocalDate.now();
		int weeks = (int) (nowDate.toEpochDay() - start.toEpochDay());
		if (weeks % 7 == 0) {
			weeks = weeks / 7;
		} else {
			weeks = weeks / 7 + 1;
		}
		Constant.weeks = weeks;
		// 获取当前是周几
		int weekday = nowDate.getDayOfWeek().getValue();
		// 找出教师当天的授课班级
		List<ActualClass> todayClasses = new ArrayList<ActualClass>();
		for (ActualClass tempClass : list) {
			boolean isBreak = false;
			List<CourseTime> tempList = json2CourseTime(tempClass.getCourseTime());
			for (CourseTime temp : tempList) {
				if (temp.getWeek().equals(String.valueOf(weeks))) {
					for (CourseTimeDetail ctd : temp.getWeekdays()) {
						if (ctd.getWeekday().equals(String.valueOf(weekday))) {
							todayClasses.add(tempClass);
							isBreak = true;
							break;
						}
					}
				}
				if (isBreak)
					break;
			}
		}
		return todayClasses;
	}

	public List<CourseTime> json2CourseTime(String json) {
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<CourseTime>>() {
		}.getType());
	}

	public void editTeacherIp(int classId, String ip) {
		ActualClassDaoImpl actualClassDaoImpl = new ActualClassDaoImpl();
		actualClassDaoImpl.updateIpbyActualClass(classId, ip);
	}

}
