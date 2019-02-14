package com.ttool.communication.screen;

import java.awt.image.BufferedImage;

import com.ttool.communication.service.ScreenChannelContainer;
import com.ttool.ui.MainFrame;
import com.ttool.util.ImageByteUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class ScreenClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes=new byte[buf.readableBytes()];
        buf.readBytes(bytes);
		BufferedImage img=ImageByteUtil.byte2Image(bytes);
		MainFrame.showImg(img);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("channelActive");
		super.channelActive(ctx);
		ScreenChannelContainer.setCtx(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
		cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}

}
