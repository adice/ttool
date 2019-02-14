package com.ttool;

import java.awt.EventQueue;

import com.ttool.ui.LoginFrame;

public class ApplicationRun {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				LoginFrame frame = new LoginFrame();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});

	}

}
