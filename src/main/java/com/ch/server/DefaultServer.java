package com.ch.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

public abstract class DefaultServer {

	protected Logger logger = Logger.getLogger(getClass());

	protected int sobacklog;

	protected int port;

	protected int bossthreads;

	protected int dbthreads;

	protected int workthreads;

	protected int readtimeout;

	/**
	 * 数据库操作线
	 */
	protected ExecutorService dboptGroup = Executors.newFixedThreadPool(8);

	public int getSobacklog() {
		return sobacklog;
	}

	public void setSobacklog(int sobacklog) {
		this.sobacklog = sobacklog;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getBossthreads() {
		return bossthreads;
	}

	public void setBossthreads(int bossthreads) {
		this.bossthreads = bossthreads;
	}

	public int getDbthreads() {
		return dbthreads;
	}

	public void setDbthreads(int dbthreads) {
		this.dbthreads = dbthreads;
		dboptGroup = Executors.newFixedThreadPool(dbthreads);
	}

	public int getWorkthreads() {
		return workthreads;
	}

	public void setWorkthreads(int workthreads) {
		this.workthreads = workthreads;
	}

	public int getReadtimeout() {
		return readtimeout;
	}

	public void setReadtimeout(int readtimeout) {
		this.readtimeout = readtimeout;
	}

	public ExecutorService getDboptGroup() {
		return dboptGroup;
	}

	public void setDboptGroup(ExecutorService dboptGroup) {
		this.dboptGroup = dboptGroup;
	}

}
