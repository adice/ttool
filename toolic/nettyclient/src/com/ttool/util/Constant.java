package com.ttool.util;

import com.ttool.biz.entity.ActualClass;
import com.ttool.biz.entity.Student;

public class Constant {
	// 当前教师
	public static Student student;
	// 当天的授课班级
	public static ActualClass nowClass;
	// 当前学期第一周的日期
	public static String startDate;
	// 学年
	public static String academicYear;
	// 学期
	public static String semester;
	// 当前为第几周
	public static int weeks;

	// 服务器端IP
	public static String serverIp;

	public static final int COMMUNICATION_SCREEN_PORT = 8600;
	public static final int COMMUNICATION_COMMAND_PORT = 8603;

	public static final byte COMMAND_SHARE_SCREEN_REQUEST = 0x01;
	public static final byte COMMAND_SHARE_SCREEN_RESPONSE = 0x0f;
	// 发送教师屏幕请求\响应
	public static final byte COMMAND_SEND_TEACHER_SCREEN_REQUEST = 0x02;
	public static final byte COMMAND_SEND_TEACHER_SCREEN_RESPONSE = 0x0e;
	// 停止发送教师屏幕请求\响应
	public static final byte COMMAND_NOT_SEND_TEACHER_SCREEN_REQUEST = 0x03;
	public static final byte COMMAND_NOT_SEND_TEACHER_SCREEN_RESPONSE = 0x0d;
	// 发送学生屏幕请求\响应
	public static final byte COMMAND_SEND_STUDENT_SCREEN_REQUEST = 0x04;
	public static final byte COMMAND_SEND_STUDENT_SCREEN_RESPONSE = 0x0c;
	// 停止发送学生屏幕请求\响应
	public static final byte COMMAND_NOT_SEND_STUDENT_SCREEN_REQUEST = 0x05;
	public static final byte COMMAND_NOT_SEND_STUDENT_SCREEN_RESPONSE = 0x0b;
}
