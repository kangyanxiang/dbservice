package com.ch.action.dev;

import com.ch.action.BaseAction;
import com.ch.action.BaseDevEnum;
import com.ch.action.dev.impl.DataAction;
import com.ch.action.dev.impl.HeartbeatAction;
import com.ch.action.dev.impl.ValveAction;
import com.ch.util.ArrayUtils;

/**
 * 包头0X55AA0100事件
 * @author kangyanxiang
 * 2016年12月12日 下午9:39:30
 */
public enum DEVBean implements BaseDevEnum {

	MINE,

	// 0X8884 心跳
	HEARTBEAT(new byte[] { -120, -124 }, HeartbeatAction.class),
	// 0X7776 阀门
	VALVE(new byte[] { 119, 118 }, ValveAction.class),
	// 0X7776 阀门
	FLOW(new byte[] { 119, 118 }, ValveAction.class),

	// 0X000A
	DATA(new byte[] { 0, 10 }, DataAction.class),

	;

	private byte[] actionCode;

	private Class<? extends BaseAction> actionClz;

	private DEVBean() {
	}

	private DEVBean(byte[] actionCode, Class<? extends BaseAction> actionClz) {
		this.actionCode = actionCode;
		this.actionClz = actionClz;
	}

	@Override
	public Class<? extends BaseAction> getActionClz(byte[] array) {
		byte[] heart = ArrayUtils.subArray(array, 4, 2);
		if (ArrayUtils.compare(HEARTBEAT.actionCode, heart)) {
			return HEARTBEAT.actionClz;
		}
		byte[] tag = ArrayUtils.subArray(array, 10, 2);
		for (DEVBean bean : values()) {
			if (bean != HEARTBEAT && ArrayUtils.compare(bean.actionCode, tag)) {
				return bean.actionClz;
			}
		}
		return null;
	}

}
