package com.ttool.communication.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ttool.biz.entity.Student;

import io.netty.channel.ChannelHandlerContext;

public class CommandChannelContainer {
	
	private static ConcurrentHashMap<Student, ChannelHandlerContext> container=new ConcurrentHashMap<Student	, ChannelHandlerContext>();

	public static void add(Student student, ChannelHandlerContext ctx) {
		container.put(student, ctx);
	}
	
	public static void remove(Student student) {
		container.remove(student);
	}
	
	public static void remove(String ip) {
		for(Student stu:container.keySet()) {
			if(ip.equals(stu.getLastLoginIp())) {
				container.remove(stu);
			}
		}
	}
	
	public static ChannelHandlerContext get(Student student) {
		return container.get(student);
	}
	
	public static Map<Student, ChannelHandlerContext> getContainer(){
		return container;
	}
	
}
