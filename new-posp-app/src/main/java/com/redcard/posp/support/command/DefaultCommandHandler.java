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
