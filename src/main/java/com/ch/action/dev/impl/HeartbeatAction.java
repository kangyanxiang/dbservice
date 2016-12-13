package com.ch.action.dev.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ch.action.BaseAction;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelPromise;

/**
 * 心跳包
 * @author kangyanxiang
 * 2016年12月12日 下午9:41:20
 */
@Component
public class HeartbeatAction implements BaseAction {

	private final Logger logger = Logger.getLogger(getClass());

	public void dealData(ChannelHandlerContext ctx, ByteBuf buf) {
		buf.setShort(4, 0X6771);

		ctx.writeAndFlush(buf, new DefaultChannelPromise(ctx.channel()) {
			@Override
			public boolean trySuccess() {
				logger.debug("发送" + this.channel() + "成功");
				return super.trySuccess();
			}
		});
	}

}
