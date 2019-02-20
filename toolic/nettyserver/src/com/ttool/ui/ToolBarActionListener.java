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
		if ("共享屏幕".equals(e.getActionCommand())) {
			this.mainFrame.sendScreen();
		}
		if ("停止共享屏幕".equals(e.getActionCommand())) {
			this.mainFrame.stopSendScreen();
		}
		if ("随机点名".equals(e.getActionCommand())) {
			this.mainFrame.randomCall();
		}
		if ("停".equals(e.getActionCommand())) {
			this.mainFrame.stopRandomCall();
		}
	}

}
