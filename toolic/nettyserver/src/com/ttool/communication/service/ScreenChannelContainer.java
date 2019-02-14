package com.ttool.communication.service;

import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

public class ScreenChannelContainer {
	/**
	 * 保存与服务器连接的Channel，默认不发送屏幕
	 */
	private static ConcurrentHashMap<ChannelHandlerContext, Boolean> container = new ConcurrentHashMap<ChannelHandlerContext, Boolean>();

	public static void add(ChannelHandlerContext ctx, Boolean isSend) {
		container.put(ctx, isSend);
	}

	public static void remove(ChannelHandlerContext ctx) {
		container.remove(ctx);
	}
	
	public static void change(ChannelHandlerContext ctx, Boolean isSend) {
		container.put(ctx, isSend);
	}

	public static ConcurrentHashMap<ChannelHandlerContext, Boolean> getContexts() {
		return container;
	}

}
