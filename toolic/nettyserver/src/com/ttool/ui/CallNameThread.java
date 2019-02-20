package com.ttool.ui;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CallNameThread implements Runnable {

	private boolean begin = false;
	private int randomNum = 0;
	private Random random;
	private List<JLabel> lblList;

	public CallNameThread(List<JLabel> lblList) {
		random = new Random();
		begin = true;
		this.lblList = lblList;
	}

	@Override
	public void run() {
		while (begin) {
			if (lblList != null && lblList.size() > 0) {
				lblList.get(randomNum).setIcon(new ImageIcon("image/yckz.png"));
				randomNum = random.nextInt(lblList.size());
				lblList.get(randomNum).setIcon(new ImageIcon("image/left.png"));
			} else {
				break;
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean isBegin() {
		return begin;
	}

	public void setBegin(boolean begin) {
		this.begin = begin;
	}

	public int getRandomNum() {
		return randomNum;
	}

	public void setRandomNum(int randomNum) {
		this.randomNum = randomNum;
	}
}
