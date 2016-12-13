package com.ch.action;

import com.ch.util.ArrayUtils;

public class ActionFactory {

	public static BaseDevEnum getDev(byte[] head) {
		for (DevEnum dev : DevEnum.values()) {
			if (ArrayUtils.compare(dev.getHead(), head)) {
				return dev.getDev();
			}
		}
		return null;
	}

}
