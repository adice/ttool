package com.ttool.communication.screen;

import com.ttool.communication.service.ScreenChannelContainer;
import com.ttool.screencapture.ScreenCapture;
import com.ttool.util.ImageByteUtil;

import io.netty.buffer.Unpooled;

/**
 * 截屏并向服务器端发送的线程
 * @author adi
 *
 */
public class ScreenCaptureThread implements Runnable {
	
	private boolean state = true;

	public void stopSendScreen() {
		state = false;
	}

	@Override
	public void run() {
		while(state) {
			try {
				byte[] bytes=null;
				bytes=ImageByteUtil.image2Byte(ScreenCapture.getScreen());
				ScreenChannelContainer.getCtx().writeAndFlush(Unpooled.copiedBuffer(bytes));
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
