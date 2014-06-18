package com.redcard.posp.support.command;

import com.opensymphony.oscache.util.StringUtil;
import com.redcard.posp.support.ApplicationContextInit;
import com.redcard.posp.support.Result;
import com.redcard.posp.support.ResultCode;

public class DefaultCommandHandler implements ICommandHandler {

	/**
	 * 把所有的指令做相同处理。
	 */
	public String handler(String cmd) {
		/*//卡规则
		if (CMD_C.equals(cmd)) {
			return null;
		}
		//集团商户发送变动（包括集团商户对应的转发主机变化）
		if (CMD_G.equals(cmd)) {
			return null;
		}
		//商户发生变动
		if (CMD_M.equals(cmd)) {
			return null;
		}
		//终端发生变化
		if (CMD_P.equals(cmd)) {
			return null;
		}*/
		if (StringUtil.isEmpty(cmd)) {
			return ResultCode.POSP_RESULT_CODE_9997.getCode();
		}
		try {
			Result r = ApplicationContextInit.loadCache();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultCode.POSP_RESULT_CODE_9999.getCode();
		}
		return null;
	}

}
