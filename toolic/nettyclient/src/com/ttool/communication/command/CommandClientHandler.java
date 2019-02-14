package com.ttool.communication.command;

import io.netty.channel.ChannelHandler.Sharable;

import com.ttool.communication.CommandInterface;
import com.ttool.communication.ScreenInterface;
import com.ttool.communication.service.CommandChannelContainer;
import com.ttool.ui.MainFrame;
import com.ttool.util.Constant;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class CommandClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("command channelActive");
		//激活时指定服务器端的ctx
		CommandChannelContainer.setCtx(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("command channelInactive");
		super.channelInactive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes=new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("command--"+bytes.length+":"+bytes[0]);
        buf.release();
        if(bytes[0]==Constant.COMMAND_NOT_SEND_TEACHER_SCREEN_REQUEST) {
        	MainFrame.stopDisplayScreen();
        }
        if(bytes[0]==Constant.COMMAND_SEND_STUDENT_SCREEN_REQUEST) {
        	//停止接收教师端截屏
        	CommandInterface.sendCommand(Constant.COMMAND_NOT_SEND_TEACHER_SCREEN_REQUEST);
			MainFrame.stopDisplayScreen();
			//启动线程，向教师端发送截屏
			ScreenInterface.sendScreen();
			//TODO 将共享教师屏幕按钮置灰，禁止再接收教师屏幕
        }
        if(bytes[0]==Constant.COMMAND_NOT_SEND_STUDENT_SCREEN_REQUEST) {
        	ScreenInterface.stopSendScreen();
        	//TODO 将共享教师屏幕按钮置成可用，可以再接收教师屏幕
        }
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("command channelReadComplete");
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("command channelRegistered");
		super.channelRegistered(ctx);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		System.out.println("command channelUnregistered");
		super.channelUnregistered(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("command exceptionCaught");
		cause.printStackTrace();
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}
}
