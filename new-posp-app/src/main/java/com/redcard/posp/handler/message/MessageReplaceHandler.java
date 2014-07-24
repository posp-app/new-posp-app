package com.redcard.posp.handler.message;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import com.opensymphony.oscache.util.StringUtil;
import com.redcard.posp.common.MacUtil;
import com.redcard.posp.handler.DefaultMessageHandler;
import com.redcard.posp.handler.secret.redcard.SecretKeyFactory;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationKey;

/**
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @project posp_server
 * @description 消息替换处理器。
 * 用于将pos收到的消息，替换快钱对应的mac
 * @date 2014-4-26
 */
public class MessageReplaceHandler implements IMessageHandler {

    private boolean isContinue = true;

    private Map<String, Object> param = null;

    public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {
        TblMerchantPos merchantPos = ManageCacheService.findPos(msg.getTerminalIdentification());
        if (merchantPos == null) {
            isContinue = false;
            return;
        }
        // 替换pin ，52域 个人识别码
        if (!StringUtil.isEmpty(msg.getPINDate())) {
            String pinKey = (String) param.get(ApplicationKey.PIN_KEY);
            if (pinKey == null || pinKey.length() == 0) {
                msg.setBCDField(52, SecretKeyFactory.getPin(merchantPos.getFldPinKey(), merchantPos.getFldMasterKey(),
                        msg.getPINDate(), msg.getAccount()));
            } else {
                msg.setBCDField(52, SecretKeyFactory.transferredPin(merchantPos.getFldPinKey(), merchantPos.getFldMasterKey(),
                        pinKey, msg.getPINDate()));
            }
        }
        //替换mac 64域
        if (!StringUtil.isEmpty(msg.getMAC())) {
            String macKey = (String) param.get(ApplicationKey.MAC_KEY);
            if (macKey == null || macKey.length() == 0) {
                msg.setBCDField(64, "");
            } else {
                String mabString = DefaultMessageHandler.getMAB(msg);
                String mac = MacUtil.redCardMac(macKey, null, mabString);
                msg.setBCDField(64, mac);
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
