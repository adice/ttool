package com.ttool.biz.teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ttool.biz.entity.Teacher;
import com.ttool.biz.util.DBUtil;

public class TeacherDaoImpl {
	
	public Teacher findByIdAndPwd(String id, String pwd) {
		Connection con=null;
		Teacher teacher=null;
		try {
			con=DBUtil.getCon();
			PreparedStatement pstm=con.prepareStatement("select * from teacher where id=? and password=?");
			pstm.setString(1, id);
			pstm.setString(2, pwd);
			ResultSet rs=pstm.executeQuery();
			if(rs.next()) {
				teacher=new Teacher();
				teacher.setId(rs.getString(1));
				teacher.setName(rs.getString(2));
				teacher.setPassword(rs.getString(3));
				teacher.setPositionId(rs.getInt(4));
				teacher.setStatus(rs.getInt(5));
			}
			return teacher;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			DBUtil.closeCon(con);
		}
	}

}
