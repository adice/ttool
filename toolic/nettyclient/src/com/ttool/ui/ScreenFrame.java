package com.ttool.ui;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreenFrame extends JFrame {
	private static ScreenFrame screenFrame;
	private static JPanel panel;
	private static JLabel lbl;
	private ScreenFrame() {
		panel = new JPanel();
		this.getContentPane().add(panel);
		lbl=new JLabel();
		panel.add(lbl);
		
		this.setSize(800,800);
		this.setVisible(true);
	}
	
	public static ScreenFrame getInstance() {
		if(ScreenFrame.screenFrame==null)
			ScreenFrame.screenFrame=new ScreenFrame();
		return ScreenFrame.screenFrame;
	}
	
	public static void close() {
		if(ScreenFrame.screenFrame!=null)
			ScreenFrame.screenFrame.dispose();
		ScreenFrame.screenFrame=null;
	}
	
	public static ScreenFrame getFrame() {
		return ScreenFrame.screenFrame;
	}
	
	public static void showImg(BufferedImage img) {
		lbl.setIcon(new ImageIcon(resize(img, panel.getWidth(), panel.getHeight())));
		lbl.repaint();
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, null);
		g.dispose();
		return dimg;
	}
}
