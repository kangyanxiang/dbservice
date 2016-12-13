package com.ch.action.dev.impl;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ch.action.BaseAction;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * 阀门
 * @author kangyanxiang
 * 2016年12月12日 下午9:41:37
 */
@Component
public class ValveAction implements BaseAction {

	private final Logger logger = Logger.getLogger(getClass());

	public void dealData(ChannelHandlerContext ctx, ByteBuf buf) {
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
		String clientIp = insocket.getAddress().getHostAddress();
		logger.debug(clientIp + "阀门下发" + (buf.getByte(12) == 0 ? "成功" : "失败"));
	}

}
