package com.ttool.communication.screen;

import java.util.Map.Entry;

import com.ttool.communication.service.ScreenChannelContainer;
import com.ttool.screencapture.ScreenCapture;
import com.ttool.util.ImageByteUtil;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class ScreenCaptureThread implements Runnable {

	private boolean state = true;

	public void stopSendScreen() {
		state = false;
	}

	@Override
	public void run() {
		while (state) {
			try {
				byte[] bytes = ImageByteUtil.image2Byte(ScreenCapture.getScreen());
				for (Entry<ChannelHandlerContext,Boolean> entry : ScreenChannelContainer.getContexts().entrySet()) {
					if(entry.getValue())
						entry.getKey().writeAndFlush(Unpooled.copiedBuffer(bytes));
				}
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
	}

}
