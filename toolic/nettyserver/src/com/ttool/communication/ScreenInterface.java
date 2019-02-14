package com.ttool.communication;

import java.net.InetSocketAddress;
import java.util.Map.Entry;

import com.ttool.communication.screen.ScreenCaptureThread;
import com.ttool.communication.screen.ScreenServer;
import com.ttool.communication.service.ScreenChannelContainer;
import com.ttool.ui.ScreenFrame;

import io.netty.channel.ChannelHandlerContext;

public class ScreenInterface {

	private static ScreenServer screenServer=null;
	private static Thread thread=null;
	private static ScreenCaptureThread screenCaptureThread=null; 
	
	public static void openScreenServer() {
		screenServer=ScreenServer.getInstance();
		screenServer.init();
	}
	
	public static void closeScreenServer() {
		screenServer.close();
	}
	
	public static void sendScreen() {
		screenCaptureThread = new ScreenCaptureThread();
		thread = new Thread(screenCaptureThread);
		thread.start();
	}

	public static void stopSendScreen() {
		if(screenCaptureThread!=null) {
			screenCaptureThread.stopSendScreen();
			screenCaptureThread=null;
		}
		if(thread!=null) {
			thread.interrupt();
			thread=null;
		}
	}
	
	public static void changeState(ChannelHandlerContext ctx, boolean isSend) {
		String remoteHost = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
		for (Entry<ChannelHandlerContext,Boolean> entry : ScreenChannelContainer.getContexts().entrySet()) {
			String screenHost = ((InetSocketAddress)entry.getKey().channel().remoteAddress()).getAddress().getHostAddress();
			if(remoteHost.equals(screenHost))
				ScreenChannelContainer.change(entry.getKey(), isSend);
		}
	}
	public static void displayScreen() {
		ScreenFrame.getInstance();
	}
	
	public static void closeScreen() {
		ScreenFrame.close();
	}
}
