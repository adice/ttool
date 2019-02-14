package com.ttool.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageByteUtil {

	public static byte[] image2Byte(BufferedImage img) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "png", baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	public static BufferedImage byte2Image(byte[] bytes) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		try {
			return ImageIO.read(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
