package com.ch.server;

import java.net.InetSocketAddress;

import com.ch.handle.RevicMessageHandle;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * 服务类
 * @author kangyanxiang
 * 2016年12月12日 下午9:43:57
 */
public class PollServer extends DefaultServer {

	/**
	 * 实际处理类
	 */
	RevicMessageHandle messageHandle;

	public void start() throws InterruptedException {

		ServerBootstrap bootstrap = new ServerBootstrap();//启动辅助类

		//io线程和工作线
		NioEventLoopGroup group = new NioEventLoopGroup(bossthreads);

		NioEventLoopGroup workGroup = new NioEventLoopGroup(workthreads);

		bootstrap.group(group, workGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.localAddress(new InetSocketAddress(port));//绑定端口
		bootstrap.option(ChannelOption.SO_BACKLOG, sobacklog);
		bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

		try {
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO)).addLast(new RevicMessageHandle());
				}
			});
			ChannelFuture f = bootstrap.bind().sync();//实际绑定操作

			logger.debug(PollServer.class.getName() + " 服务器启动完成" + f.channel().localAddress());

			f.channel().closeFuture().sync();//等待服务器关
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully().sync();//优雅关闭
			workGroup.shutdownGracefully().sync();
		}
	}

}