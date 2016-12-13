package com.ch.action.dev.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultChannelPromise;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ch.action.BaseAction;

/**
 * 数据方法
 * @author kangyanxiang
 * 2016年12月12日 下午9:40:28
 */
@Component
public class DataAction implements BaseAction {

	private final Logger logger = Logger.getLogger(getClass());

	@Override
	public void dealData(ChannelHandlerContext ctx, ByteBuf buf) {
		byte[] bs = new byte[] { 85, -86, 1, 0, 0, 0, 0, 0, 0, 0, -128, 10, 1, -86, 85 };
		bs[4] = buf.getByte(4);
		bs[5] = buf.getByte(5);
		bs[6] = buf.getByte(6);
		bs[7] = buf.getByte(7);
		bs[8] = buf.getByte(8);
		bs[9] = buf.getByte(9);

		ctx.writeAndFlush(ctx.alloc().buffer().writeBytes(bs), new DefaultChannelPromise(ctx.channel()) {
			@Override
			public boolean trySuccess() {
				logger.debug("数据确认返回" + this.channel() + "成功");
				return super.trySuccess();
			}
		});
	}

}
