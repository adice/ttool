package com.ttool.biz.teacher;

import com.ttool.biz.entity.Teacher;

public class TeacherServiceImpl {

	public Teacher login(String id, String pwd) {
		TeacherDaoImpl teacherDaoImpl=new TeacherDaoImpl();
		return teacherDaoImpl.findByIdAndPwd(id, pwd);
	}
}
