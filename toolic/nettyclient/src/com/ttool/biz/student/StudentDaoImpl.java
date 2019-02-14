package com.ttool.biz.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import com.ttool.biz.entity.Student;
import com.ttool.biz.util.DBUtil;

public class StudentDaoImpl {
	public Student findByIdAndPwd(String id, String pwd) {
		Connection con = null;
		Student student = null;
		try {
			con = DBUtil.getCon();
			PreparedStatement pstm = con.prepareStatement("select * from student where id=? and password=?");
			pstm.setString(1, id);
			pstm.setString(2, pwd);
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				student = new Student();
				student.setId(rs.getString(1));
				student.setName(rs.getString(2));
				student.setPassword(rs.getString(3));
				student.setGender(rs.getString(4));
				student.setStuGrade(rs.getInt(5));
				student.setStuClass(rs.getInt(6));
			}
			return student;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeCon(con);
		}
	}

	public void updateIpAndTime(String id, String ip) {
		Connection con = null;
		try {
			con = DBUtil.getCon();
			PreparedStatement pstm = con.prepareStatement("update student set lastLoginIp = ?, lastLoginTime = ? where id=?");
			pstm.setString(1, ip);
			pstm.setString(2, new Date().toLocaleString());
			pstm.setString(3, id);
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeCon(con);
		}
	}
}
