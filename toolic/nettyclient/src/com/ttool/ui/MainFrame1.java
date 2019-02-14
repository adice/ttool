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
	JTextField txtCmd;
	public MainFrame1() {
		JPanel panel=new JPanel();
		JButton btn1=new JButton("连接命令服务");
		JButton btn2=new JButton("连接共享屏幕服务");
		JButton btn3=new JButton("断开命令服务");
		JButton btn4=new JButton("断开共享屏幕服务");
		txtCmd=new JTextField(20);
		JButton btn5=new JButton("发送命令");
		JButton btn6=new JButton("显示共享屏幕");
		JButton btn7=new JButton("关闭共享屏幕");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		btn7.addActionListener(this);
		panel.add(btn1);
		panel.add(btn2);
		panel.add(btn3);
		panel.add(btn4);
		panel.add(txtCmd);
		panel.add(btn5);
		panel.add(btn6);
		panel.add(btn7);
		this.getContentPane().add(panel);
		this.setSize(300, 300);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("连接命令服务")) {
			try {
				CommandInterface.connectCommandServer(Constant.serverIp);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getActionCommand().equals("连接共享屏幕服务")) {
			ScreenInterface.connectScreenServer(Constant.serverIp);
		}
		if(e.getActionCommand().equals("断开命令服务")) {
			CommandInterface.disconnecCommandtServer();
		}
		if(e.getActionCommand().equals("断开共享屏幕服务")) {
			ScreenInterface.disconnectScreenServer();
		}
		if(e.getActionCommand().equals("发送命令")) {
			byte cmd=Byte.parseByte(txtCmd.getText());
			CommandInterface.sendCommand(cmd);
		}
		if(e.getActionCommand().equals("显示共享屏幕")) {
			CommandInterface.sendCommand(Constant.COMMAND_SEND_TEACHER_SCREEN_REQUEST);
		}
		if(e.getActionCommand().equals("关闭共享屏幕")) {
			CommandInterface.sendCommand(Constant.COMMAND_NOT_SEND_TEACHER_SCREEN_REQUEST);
		}
	}
	
}
