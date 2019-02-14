package com.ttool.communication.command;

import java.net.InetSocketAddress;

import com.ttool.util.Constant;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class CommandClient {

	private static CommandClient commandClient;
	private EventLoopGroup group;
	private ChannelFuture channelFuture;

	private CommandClient() {
	}

	public static CommandClient getInstance() {
		if (commandClient == null) {
			commandClient = new CommandClient();
		}
		return commandClient;
	}

	/**
	 * 连接命令服务器
	 * 
	 * @param ip
	 */
	public void connect(String ip) throws Exception {
		Bootstrap bootStrap = new Bootstrap();
		group = new NioEventLoopGroup();
		bootStrap.group(group).channel(NioSocketChannel.class)
				.remoteAddress(new InetSocketAddress(ip, Constant.COMMUNICATION_COMMAND_PORT))
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast(new CommandClientHandler());
					}
				});
		channelFuture = bootStrap.connect().sync();
	}

	/**
	 * 断开命令服务器
	 */
	public void disconnect() {
		try {
//			if(channelFuture!=null)
//				channelFuture.channel().closeFuture().sync();
			if (group != null)
				group.shutdownGracefully().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
