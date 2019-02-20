package com;

import javax.swing.JButton;

import com.ttool.ui.MainFrame;

public class Test {

	public static void main(String[] args) {
		JButton btn=new JButton();
		System.out.println(btn.getBackground());
		
		//测试读写properties
//		PropertyUtil.readConfig();
//		PropertyUtil.writeConfig("2018-2019","1","2019-1-9");
		
		//测试主界面
//		MainFrame.getInstance();
		
//		//测试课程安排的JSON格式
//		CourseTime ct1=new CourseTime();
//		ct1.setWeek("1");
//		CourseTimeDetail ctd1=new CourseTimeDetail();
//		ctd1.setWeekday("1");
//		ctd1.getChapter().add("1");
//		ctd1.getChapter().add("2");
//		ct1.getWeekdays().add(ctd1);
//		CourseTimeDetail ctd11=new CourseTimeDetail();
//		ctd11.setWeekday("2");
//		ctd11.getChapter().add("1");
//		ctd11.getChapter().add("2");
//		ct1.getWeekdays().add(ctd11);
//		
//		CourseTime ct2=new CourseTime();
//		ct2.setWeek("2");
//		CourseTimeDetail ctd2=new CourseTimeDetail();
//		ctd2.setWeekday("1");
//		ctd2.getChapter().add("1");
//		ctd2.getChapter().add("2");
//		ct2.getWeekdays().add(ctd1);
//		
//		List<CourseTime> list=new ArrayList<CourseTime>();
//		list.add(ct1);list.add(ct2);
//		
//		Gson gson=new Gson();
//		String ct=gson.toJson(list);
//		
//		try {
//			Connection con=DBUtil.getCon();
//			PreparedStatement pstm=con.prepareStatement("update actualclass set courseTime=?");
//			pstm.setString(1, ct);
//			pstm.executeUpdate();
//			con.close();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
	}

}
