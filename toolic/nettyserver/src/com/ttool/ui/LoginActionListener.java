package com.ttool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginActionListener implements ActionListener {
	
	private LoginFrame loginFrame;
	public LoginActionListener(LoginFrame loginFrame) {
		this.loginFrame=loginFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("登  录".equals(e.getActionCommand())){
			loginFrame.login();
		}
	}

}
