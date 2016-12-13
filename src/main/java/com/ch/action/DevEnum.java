package com.ch.action;

import com.ch.action.dev.DEVBean;

public enum DevEnum {

	// 0X55AA0100
	DEV(new byte[] { 85, -86, 1, 0 }, 0XAA55, DEVBean.MINE),

	// X55AA0100(0X55AA0100, 0XAA55, DEVBean.class),

	;

	private byte[] head;

	private int end;

	private BaseDevEnum dev;

	private DevEnum(byte[] head, int end, BaseDevEnum dev) {
		this.head = head;
		this.end = end;
		this.dev = dev;
	}

	public byte[] getHead() {
		return head;
	}

	public int getEnd() {
		return end;
	}

	public BaseDevEnum getDev() {
		return dev;
	}

}
