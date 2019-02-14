package com.ttool.communication.command;

import java.net.InetSocketAddress;

import com.ttool.biz.entity.Student;
import com.ttool.biz.student.StudentServiceImpl;
import com.ttool.communication.ScreenInterface;
import com.ttool.communication.service.CommandChannelContainer;
import com.ttool.ui.MainFrame;
import com.ttool.util.Constant;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class CommandServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		System.out.println("command channelActive");
		String ip = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
		StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
		Student student = studentServiceImpl.findByIp(ip);
		CommandChannelContainer.add(student, ctx);
		MainFrame.getInstance().setStudents();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("command channelInactive");
		super.channelInactive(ctx);
		String ip = ((InetSocketAddress)ctx.channel().remoteAddress()).getAddress().getHostAddress();
		CommandChannelContainer.remove(ip);
		MainFrame.getInstance().setStudents();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("command channelRead");
		ByteBuf buf = (ByteBuf) msg;
		byte[] bytes=new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("command server--"+bytes.length+":"+bytes[0]);
        buf.release();
        if(bytes[0]==Constant.COMMAND_SEND_TEACHER_SCREEN_REQUEST) {
        	ScreenInterface.changeState(ctx, true);
        }
        if(bytes[0]==Constant.COMMAND_NOT_SEND_TEACHER_SCREEN_REQUEST) {
        	ScreenInterface.changeState(ctx, false);
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
