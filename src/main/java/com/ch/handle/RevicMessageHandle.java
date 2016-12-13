package com.ch.handle;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.ch.action.ActionFactory;
import com.ch.action.BaseAction;
import com.ch.action.BaseDevEnum;
import com.ch.util.ArrayUtils;
import com.ch.util.SpringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 接受信息并保存数据库
 * @author kangyanxiang
 * 2016年12月12日 下午9:42:38
 */
@Sharable
public class RevicMessageHandle extends ChannelInboundHandlerAdapter {

	private Logger logger = Logger.getLogger(getClass());

	private BlockingQueue<Channel> channels;

	public RevicMessageHandle() {
		channels = new LinkedBlockingQueue<Channel>();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.debug(ctx.channel().remoteAddress() + "连接" + Thread.currentThread());
		channels.add(ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		closeChannel(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		
		byte[] req = new byte[12];
		// 包头
		req[0] = buf.getByte(0);
		req[1] = buf.getByte(1);
		req[2] = buf.getByte(2);
		req[3] = buf.getByte(3);

		// 心跳标识
		req[4] = buf.getByte(4);
		req[5] = buf.getByte(5);

		// 其他标识
		req[10] = buf.getByte(10);
		req[11] = buf.getByte(11);

		byte[] head = ArrayUtils.subArray(req, 0, 4);
		BaseDevEnum dev = ActionFactory.getDev(head);
		if (dev != null) {
			Class<? extends BaseAction> actionClz = dev.getActionClz(req);
			if (actionClz != null) {
				BaseAction action = SpringUtils.getBean(actionClz);
				action.dealData(ctx, buf);
			}
		}
	}

	/**
	 * 关闭通道
	 * @param ctx
	 * @author kangyanxiang 
	 * @date 2016年12月12日 下午9:43:10
	 */
	private void closeChannel(ChannelHandlerContext ctx) {
		logger.debug(ctx.channel().remoteAddress() + "断开");
		channels.remove(ctx.channel());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

}
