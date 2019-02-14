package com.ttool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ttool.communication.CommandInterface;
import com.ttool.communication.ScreenInterface;
import com.ttool.util.Constant;

public class MainFrame1 extends JFrame implements ActionListener{
	ScreenFrame screenFrame;
	JTextField txtCmd;
	public MainFrame1() {
		this.setLocationRelativeTo(null);
		JPanel panel=new JPanel();
		JButton btn1=new JButton("开启命令服务");
		JButton btn2=new JButton("开启共享屏幕服务");
		JButton btn3=new JButton("停止命令服务");
		JButton btn4=new JButton("停止共享屏幕服务");
		txtCmd=new JTextField(20);
		JButton btn5=new JButton("发送命令");
		JButton btn6=new JButton("共享屏幕");
		JButton btn7=new JButton("停止共享屏幕");
		JButton btn8=new JButton("共享某学生屏幕");
		JButton btn9=new JButton("停止共享某学生屏幕");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		btn7.addActionListener(this);
		btn8.addActionListener(this);
		btn9.addActionListener(this);
		panel.add(btn1);
		panel.add(btn2);
		panel.add(btn3);
		panel.add(btn4);
		panel.add(txtCmd);
		panel.add(btn5);
		panel.add(btn6);
		panel.add(btn7);
		panel.add(btn8);
		panel.add(btn9);
		this.getContentPane().add(panel);
		this.setSize(300, 300);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("开启命令服务")) {
			CommandInterface.openCommandServer();
		}
		if(e.getActionCommand().equals("开启共享屏幕服务")) {
			ScreenInterface.openScreenServer();
		}
		if(e.getActionCommand().equals("停止命令服务")) {
			CommandInterface.closeCommandServer();
		}
		if(e.getActionCommand().equals("停止共享屏幕服务")) {
			ScreenInterface.closeScreenServer();
		}
		if(e.getActionCommand().equals("发送命令")) {
			byte cmd=Byte.parseByte(txtCmd.getText());
//			CommandInterface.sendCommand("1-zhangsan",cmd);
		}
		if(e.getActionCommand().equals("共享屏幕")) {
			ScreenInterface.sendScreen();
		}
		if(e.getActionCommand().equals("停止共享屏幕")) {
			ScreenInterface.stopSendScreen();
		}
		if(e.getActionCommand().equals("共享某学生屏幕")) {
			//先停止自己的屏幕共享
			ScreenInterface.stopSendScreen();
			ScreenInterface.displayScreen();
//			CommandInterface.sendCommand("1-zhangsan", Constant.COMMAND_SEND_STUDENT_SCREEN_REQUEST);
		}
		if(e.getActionCommand().equals("停止共享某学生屏幕")) {
			ScreenInterface.closeScreen();
//			CommandInterface.sendCommand("1-zhangsan", Constant.COMMAND_NOT_SEND_STUDENT_SCREEN_REQUEST);
		}
	}
}
