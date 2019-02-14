package com.ttool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBarActionListener implements ActionListener {
	private MainFrame mainFrame;

	public ToolBarActionListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if("查看屏幕".equals(e.getActionCommand())) {
			this.mainFrame.connectAndDisplayScreen();
		}
		if("停止查看屏幕".equals(e.getActionCommand())) {
			this.mainFrame.stopDisplayScreen();
		}
		if("人脸签到".equals(e.getActionCommand())) {
			this.mainFrame.faceRecord();
		}
	}

}
