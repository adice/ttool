package com.ttool.communication.screen;

import java.net.InetSocketAddress;

import com.ttool.util.Constant;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class ScreenServer {
	
	private static ScreenServer screenServer;
	private NioEventLoopGroup group;
	private ChannelFuture channelFuture;
	
	private ScreenServer() {}
	
	public static ScreenServer getInstance() {
		if(screenServer==null) {
			screenServer=new ScreenServer();
		}
		return screenServer;
	}
	
	public void init() {
		try {
			ServerBootstrap bootstrap=new ServerBootstrap();
			group = new NioEventLoopGroup();
			bootstrap.group(group)
			.channel(NioServerSocketChannel.class)
			.localAddress(new InetSocketAddress(Constant.COMMUNICATION_SCREEN_PORT))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("decoder", new LengthFieldBasedFrameDecoder(65535*30,0,4,0,4));
					ch.pipeline().addLast("encoder", new LengthFieldPrepender(4, false));
	                ch.pipeline().addLast(new ScreenServerHandler());
	            }
			});
			channelFuture=bootstrap.bind().sync();
			System.out.println(this.getClass().getName()
					+ " started and listen on "
					+ channelFuture.channel().localAddress());
		}catch (InterruptedException e) {
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
