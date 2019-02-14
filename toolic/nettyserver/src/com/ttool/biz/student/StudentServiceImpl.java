package com.ttool.biz.student;

import com.ttool.biz.entity.Student;

public class StudentServiceImpl {
	
	public Student findByIp(String ip) {
		StudentDaoImpl studentDaoImpl = new StudentDaoImpl();
		return studentDaoImpl.findByIp(ip);
	}

}
