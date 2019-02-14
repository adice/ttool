package com.ttool.communication.screen;

import java.awt.image.BufferedImage;
import java.util.Map.Entry;

import com.ttool.communication.service.ScreenChannelContainer;
import com.ttool.ui.ScreenFrame;
import com.ttool.util.ImageByteUtil;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class ScreenServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("channelActive");
		ScreenChannelContainer.add(ctx, false);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelInactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("channelRead");
		//接收并显示学生端截屏
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes=new byte[buf.readableBytes()];
		buf.readBytes(bytes);
		if(ScreenFrame.getFrame()!=null) {
			BufferedImage img=ImageByteUtil.byte2Image(bytes);
			ScreenFrame.showImg(img);
		}
		//转发到其他学生端
		for (Entry<ChannelHandlerContext,Boolean> entry : ScreenChannelContainer.getContexts().entrySet()) {
			if(entry.getValue())
				entry.getKey().writeAndFlush(Unpooled.copiedBuffer(bytes));
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelReadComplete");
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelUnregistered");
		super.channelUnregistered(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
		cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}

}
