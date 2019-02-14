package com.ttool.communication;

import com.ttool.communication.command.CommandClient;
import com.ttool.communication.service.CommandChannelContainer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class CommandInterface {
	private static CommandClient commandClient;

	public static void connectCommandServer(String ip) throws Exception {
		commandClient = CommandClient.getInstance();
		commandClient.connect(ip);
	}

	public static void disconnecCommandtServer() {
		commandClient.disconnect();
	}

	public static void sendCommand(byte cmd) {
		ChannelHandlerContext ctx = CommandChannelContainer.getCtx();
		ByteBuf encoded = ctx.alloc().buffer(1);
		encoded.writeByte(cmd);
		ctx.writeAndFlush(encoded);
	}

}
