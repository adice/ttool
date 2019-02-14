package com.ttool.biz.student;

import com.ttool.biz.entity.Student;

public class StudentServiceImpl {

	public Student login(String id, String pwd, String ip) {
		StudentDaoImpl studentDaoImpl=new StudentDaoImpl();
		Student student=studentDaoImpl.findByIdAndPwd(id, pwd);
		if(student!=null) {
			studentDaoImpl.updateIpAndTime(id, ip);
		}
		return student;
	}
}
