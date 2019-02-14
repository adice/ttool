package com.ttool.communication.screen;

import java.net.InetSocketAddress;

import com.ttool.util.Constant;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class ScreenClient {

	private static ScreenClient screenClient;
	private EventLoopGroup group;
	private ChannelFuture channelFuture;
	
	private ScreenClient() {}
	
	public static ScreenClient getInstance() {
		if(screenClient==null) {
			screenClient=new ScreenClient();
		}
		return screenClient;
	}
	/**
	 * 连接Screen服务器
	 * @param ip
	 */
	public void connect(String ip) {
        try {
            Bootstrap bootStrap = new Bootstrap();
            group = new NioEventLoopGroup();
            bootStrap.group(group)
             .channel(NioSocketChannel.class)
             .remoteAddress(new InetSocketAddress(ip, Constant.COMMUNICATION_SCREEN_PORT))
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel socketChannel) throws Exception {
                	socketChannel.pipeline().addLast("decoder", new LengthFieldBasedFrameDecoder(65535*30,0,4,0,4));
                	socketChannel.pipeline().addLast("encoder", new LengthFieldPrepender(4, false));
 	                socketChannel.pipeline().addLast(new ScreenClientHandler());
                 }
             });
            channelFuture = bootStrap.connect().sync();
        }catch(InterruptedException e) {
        	e.printStackTrace();
        }
	}
	/**
	 * 断开Screen服务器
	 */
	public void disconnect() {
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
