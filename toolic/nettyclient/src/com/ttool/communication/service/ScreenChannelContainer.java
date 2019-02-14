package com.ttool.communication.service;

import io.netty.channel.ChannelHandlerContext;

public class ScreenChannelContainer {
	private static ChannelHandlerContext ctx;

	public static ChannelHandlerContext getCtx() {
		return ctx;
	}

	public static void setCtx(ChannelHandlerContext ctx) {
		ScreenChannelContainer.ctx = ctx;
	}
	
}
