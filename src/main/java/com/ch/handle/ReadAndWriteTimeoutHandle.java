package com.ch.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutException;

/**
 * 只有读写都是一定时间空闲才会关闭
 * 
 * @author tzj 2016-10-18 上午10:14:52
 */
public class ReadAndWriteTimeoutHandle extends IdleStateHandler {

	boolean wirteTimeOut = false;
	boolean readTimeOut = false;

	private boolean closed;

	public ReadAndWriteTimeoutHandle(int timeout) {
		super(timeout, timeout, timeout);
	}

	public ReadAndWriteTimeoutHandle(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
		super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
	}

	@Override
	protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
		super.channelIdle(ctx, evt);

		boolean closeit = false;
		switch (evt.state()) {
		case READER_IDLE:
			readTimeOut = true;
			closeit = wirteTimeOut;
			break;
		case WRITER_IDLE:
			wirteTimeOut = true;
			closeit = readTimeOut;
			break;
		default:
			break;
		}

		if (closeit && !closed) {
			ctx.fireExceptionCaught(ReadTimeoutException.INSTANCE);
			ctx.close();
			closed = true;
		}
	}

}
