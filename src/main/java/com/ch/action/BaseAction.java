package com.ch.action;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface BaseAction {

	void dealData(ChannelHandlerContext ctx, ByteBuf buf);

}
