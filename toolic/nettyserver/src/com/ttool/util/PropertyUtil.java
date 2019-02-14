package com.ttool.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {
	public static void readConfig() {
		Properties prop = new Properties();
		InputStream in=null;
		try {
			in = new BufferedInputStream (new FileInputStream("config/config.properties"));
			prop.load(in);
			Constant.academicYear = prop.getProperty("academicYear");
			Constant.semester = prop.getProperty("semester");
			Constant.startDate = prop.getProperty("startDate");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(in!=null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void writeConfig(String academicYear, String semester, String startDate) {
		Properties prop = new Properties();
		FileOutputStream os=null;
		try {
			os = new FileOutputStream("config/config.properties");
			prop.setProperty("academicYear", academicYear);
			prop.setProperty("semester", semester);
			prop.setProperty("startDate", startDate);
			prop.store(os, "config file");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(os!=null)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
