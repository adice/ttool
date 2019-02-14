package com.ttool.communication;

import com.ttool.communication.screen.ScreenCaptureThread;
import com.ttool.communication.screen.ScreenClient;
import com.ttool.ui.ScreenFrame;
import com.ttool.util.Constant;

public class ScreenInterface {
	private static ScreenClient screenClient;
	private static Thread thread=null;
	private static ScreenCaptureThread screenCaptureThread=null;
	
	public static void connectScreenServer(String ip) {
		screenClient = ScreenClient.getInstance();
		screenClient.connect(ip);
	}

	public static void disconnectScreenServer() {
		CommandInterface.sendCommand(Constant.COMMAND_NOT_SEND_TEACHER_SCREEN_REQUEST);
		screenClient.disconnect();
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

}
