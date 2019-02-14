package com.ttool.screencapture;

import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ScreenCapture {
	/* 鼠标的图片 */
	private static BufferedImage cursor = null;
	private static Toolkit toolkit;
	private static Dimension dimension;
	private static Robot robot;
	private static Rectangle rec;
	static {
		try {
			// 当前屏幕大小
			toolkit = java.awt.Toolkit.getDefaultToolkit();
			dimension = toolkit.getScreenSize();
			rec = new Rectangle(dimension);
			// 创建Robot对象
			robot = new Robot();
			//读取鼠标的图片
			cursor = ImageIO.read(new File("image/cursor_1.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得屏幕截图。 返回一张图片BufferedImage。根据鼠标的位置，拼上一个鼠标
	 * 
	 * @return	返回一张带有鼠标的屏幕截图
	 * @throws Exception
	 */
	public static BufferedImage getScreen() throws Exception {
		// 获得鼠标的位置
		Point p = MouseInfo.getPointerInfo().getLocation();
		// 获得一个屏幕的截图
		BufferedImage image = robot.createScreenCapture(rec);
		// 将鼠标图片画在截图上
		image.createGraphics().drawImage(cursor, p.x, p.y, null);
		return image;
	}
}
