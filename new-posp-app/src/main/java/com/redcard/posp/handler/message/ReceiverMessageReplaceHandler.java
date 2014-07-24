package com.redcard.posp.handler.message;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.handler.DefaultMessageHandler;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.model.TblTransactionMessage;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;
import com.redcard.posp.support.ResultCode;

/**
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @project posp_server
 * @description 接收上联posp系统的消息，替换为终端对应域
 * @date 2014-5-1
 */
public class ReceiverMessageReplaceHandler implements IMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(ReceiverMessageReplaceHandler.class);

    private boolean isContinue = true;

    private Map<String, Object> param = null;

    public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {
        if (ResultCode.RESULT_CODE_00.getCode().equals(msg.getResponseCode())) {
            TblMerchantPos merchantPos = ManageCacheService.findPos(msg.getTerminalIdentification());
            if (merchantPos == null) {
                byte[] allBytes = msg.toMessgeBytes();
                ChannelBuffer retCB = ChannelBuffers.dynamicBuffer();
                retCB.writeBytes(allBytes);
                inBoundChannel.write(retCB);
                isContinue = false;
                return;
            } else {
                if (ApplicationContent.MSG_TYPE_SALE_RESP.equals(msg.getMSGType())) {
                    //查原来交易。将原交易金额负给4域
                    TblTransactionMessage tm = ApplicationContentSpringProvider.getInstance()
                            .getMessageService().findSelf(msg);
                    if (tm != null) {
                        msg.setBCDField(4, tm.getFldTransactionAmount());
                    }
                }

                if (ApplicationContent.MSG_TYPE_REVERSAL_RESP.equals(msg.getMSGType())
                        || ApplicationContent.MSG_TYPE_SALE_RESP.equals(msg.getMSGType())
                        || ApplicationContent.MSG_TYPE_SALE_RESP.equals(msg.getMSGType())) {
                    msg.setUnicertainAscField(62, merchantPos.getFldBatchNo());
                }

                String targetMac = DefaultMessageHandler.getMAC(msg);
                msg.setBCDField(64, targetMac);


            }
        }
    }

    public boolean isContinue() {
        return isContinue;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        if (this.param != null) {
            this.param.putAll(param);
        } else {
            this.param = param;
        }
    }

}
