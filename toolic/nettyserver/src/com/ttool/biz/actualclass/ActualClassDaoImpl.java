package com.ttool.biz.actualclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ttool.biz.entity.ActualClass;
import com.ttool.biz.util.DBUtil;

public class ActualClassDaoImpl {

	/**
	 * 查询本学期所带班级的信息
	 * 
	 * @param teacherId
	 * @return
	 */
	public List<ActualClass> findByTeacherId(String teacherId, String academicYear, String semester) {
		Connection con = null;
		try {
			con = DBUtil.getCon();
			PreparedStatement pstm = con
					.prepareStatement("select * from actualclass where teacherId=? and academicYear=? and semester=?");
			pstm.setString(1, teacherId);
			pstm.setString(2, academicYear);
			pstm.setString(3, semester);
			ResultSet rs = pstm.executeQuery();
			List<ActualClass> list = new ArrayList<ActualClass>();
			while (rs.next()) {
				ActualClass ac = new ActualClass();
				ac.setId(rs.getInt(1));
				ac.setName(rs.getString(2));
				ac.setAcademicYear(rs.getString(3));
				ac.setSemester(rs.getString(4));
				ac.setTeacherId(rs.getString(5));
				ac.setTeacherIp(rs.getString(6));
				ac.setCourseTime(rs.getString(7));
				list.add(ac);
			}
			pstm.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DBUtil.closeCon(con);
		}
	}

	/**
	 * 老师登录后，修改老师在该班级的IP地址
	 * 
	 * @param classId
	 */
	public void updateIpbyActualClass(int classId, String ip) {
		Connection con = null;
		try {
			con = DBUtil.getCon();
			PreparedStatement pstm = con.prepareStatement("update actualclass set teacherIp=? where id=?");
			pstm.setString(1, ip);
			pstm.setInt(2, classId);
			pstm.executeUpdate();
			pstm.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeCon(con);
		}
	}
}
