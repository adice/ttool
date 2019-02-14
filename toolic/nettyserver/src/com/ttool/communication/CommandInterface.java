package com.ttool.communication;

import com.ttool.biz.entity.Student;
import com.ttool.communication.command.CommandServer;
import com.ttool.communication.service.CommandChannelContainer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class CommandInterface {
	
	private static CommandServer commandServer=null;
	
	public static void openCommandServer() {
		commandServer=CommandServer.getInstance();
		commandServer.init();
	}
	
	public static void closeCommandServer() {
		commandServer.close();
	}
	
	public static void sendCommand(Student key, byte cmd) {
		ChannelHandlerContext ctx=CommandChannelContainer.get(key);
		ByteBuf encoded = ctx.alloc().buffer(1);
		encoded.writeByte(cmd);
		ctx.writeAndFlush(encoded);
	}
	
	public static void sendCommand(ChannelHandlerContext ctx, byte cmd) {
		ByteBuf encoded = ctx.alloc().buffer(1);
		encoded.writeByte(cmd);
		ctx.writeAndFlush(encoded);
	}
	
}
