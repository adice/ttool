package com.ttool.communication.command;

import java.net.InetSocketAddress;

import com.ttool.util.Constant;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class CommandServer {
	
	private static CommandServer commandServer;
	private ServerBootstrap bootstrap;
	private NioEventLoopGroup group;
	private ChannelFuture channelFuture;
	private CommandServer() {}
	
	public static CommandServer getInstance() {
		if(commandServer==null) {
			commandServer=new CommandServer();
		}
		return commandServer;
	}
	
	public void init() {
		try {
			bootstrap=new ServerBootstrap();
			group = new NioEventLoopGroup();
			bootstrap.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(Constant.COMMUNICATION_COMMAND_PORT))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				public void initChannel(SocketChannel ch) throws Exception {
	                ch.pipeline().addLast(new CommandServerHandler());
	            }
			});
			channelFuture=bootstrap.bind().sync();
			System.out.println("command server started and listen on " 
					+ channelFuture.channel().localAddress());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
//			if(channelFuture!=null)
//				channelFuture.channel().closeFuture().sync();
			if(group!=null)
				group.shutdownGracefully().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
