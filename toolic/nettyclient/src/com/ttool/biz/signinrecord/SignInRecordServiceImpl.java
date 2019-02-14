package com.ttool.biz.signinrecord;

import com.ttool.biz.entity.SignInRecord;

public class SignInRecordServiceImpl {

	public void checkIn(SignInRecord signInRecord) {
		SignInRecordDaoImpl signInRecordDaoImpl = new SignInRecordDaoImpl();
		signInRecordDaoImpl.saveSignInRecord(signInRecord);
	}
}
