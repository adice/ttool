package com.ttool.biz.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ttool.biz.entity.Student;
import com.ttool.biz.util.DBUtil;

public class StudentDaoImpl {
	/**
	 * 查询某IP登录的最后一个学生的学号和姓名
	 * @param ip
	 * @return
	 */
	public Student findByIp(String ip) {
		Connection con=null;
		Student student=null;
		try {
			con=DBUtil.getCon();
			PreparedStatement pstm=con.prepareStatement("select id,name,lastLoginIp from student where lastLoginIp=? order by lastLoginTime desc limit 0,1");
			pstm.setString(1, ip);
			ResultSet rs=pstm.executeQuery();
			if(rs.next()) {
				student=new Student();
				student.setId(rs.getString(1));
				student.setName(rs.getString(2));
				student.setLastLoginIp(rs.getString(3));
			}
			return student;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			DBUtil.closeCon(con);
		}
	}
}
