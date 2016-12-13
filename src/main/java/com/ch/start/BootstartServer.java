package com.ch.start;

import com.ch.server.PollServer;
import com.ch.util.SpringUtils;

public class BootstartServer {

	/**
	 * 主入口
	 * @param args
	 * @author kangyanxiang 
	 * @date 2016年12月12日 下午9:45:03
	 */
	public static void main(String[] args) {
		PollServer pollServer = SpringUtils.getBean(PollServer.class);
		try {
			pollServer.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
