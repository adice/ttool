package com.ttool.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.ttool.biz.entity.ActualClass;

public class ChooseClassActionListener implements ActionListener {

	private LoginFrame frame;
	private ActualClass ac;
	
	public ChooseClassActionListener(LoginFrame frame, ActualClass ac) {
		this.frame = frame;
		this.ac=ac;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.chooseClass(this.ac);
	}

}
