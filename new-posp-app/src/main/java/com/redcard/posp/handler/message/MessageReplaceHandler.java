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
 * 
 * 
 * 
 * @project posp_server
 * @description 
 * 消息替换处理器。
 * 用于将pos收到的消息，替换快钱对应的mac 
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-4-26
 */
public class MessageReplaceHandler implements IMessageHandler{

	private boolean isContinue = true;
	
	private Map<String,String> param = null;
	
	public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {
		/*TblProxyHost host = ManageCacheService.findProxyHostByMerchantNo(msg.getCardAcceptorIdentification());
		if (host ==null) {
			isContinue = false;
			return;
		}*/
		TblMerchantPos merchantPos = ManageCacheService.findPos(msg.getTerminalIdentification());
		if (merchantPos ==null) {
			isContinue = false;
			return;
		}
		// 替换pin ，52域 个人识别码
		if (!StringUtil.isEmpty(msg.getPINDate())) {
			String pinKey = param.get(ApplicationKey.PIN_KEY);
			if (pinKey == null||pinKey.length()==0) {
				msg.setBCDField(52, SecretKeyFactory.getPin(merchantPos.getFldPinKey(),merchantPos.getFldMasterKey(),
						msg.getPINDate(),msg.getAccount()));
			} else {
				msg.setBCDField(52, SecretKeyFactory.transferredPin(merchantPos.getFldPinKey(),merchantPos.getFldMasterKey(),
						pinKey,msg.getPINDate()));
			}
		}
		//替换mac 64域
		if (!StringUtil.isEmpty(msg.getMAC())) {
			//String targetMac = SecuritySupporter.transferredMac(msg.mab,host.getFldMacKey(),ApplicationContextInit.targetMasterKey);
			String macKey = param.get(ApplicationKey.MAC_KEY);
			if (macKey ==null||macKey.length()==0) {
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

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		if (this.param!=null) {
			this.param.putAll(param);
		} else {
			this.param = param;
		}
	}

}
