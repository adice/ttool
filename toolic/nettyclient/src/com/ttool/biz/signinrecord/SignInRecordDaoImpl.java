package com.ttool.biz.signinrecord;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.ttool.biz.entity.SignInRecord;
import com.ttool.biz.util.DBUtil;

public class SignInRecordDaoImpl {
	public void saveSignInRecord(SignInRecord signInRecord) {
		Connection con = null;
		try {
			con = DBUtil.getCon();
			PreparedStatement pstm = con.prepareStatement("insert into signinrecord values(?,?,?,?)");
			pstm.setInt(1, signInRecord.getClassId());
			pstm.setString(2, signInRecord.getStuId());
			pstm.setString(3, signInRecord.getStuIp());
			pstm.setString(4, signInRecord.getSignInTime().toLocaleString());
			pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeCon(con);
		}
	}
}
