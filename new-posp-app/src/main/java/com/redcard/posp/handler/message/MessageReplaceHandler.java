package com.redcard.posp.handler.message;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import com.opensymphony.oscache.util.StringUtil;
import com.redcard.posp.handler.secret.redcard.SecretKeyFactory;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationContextInit;
import com.redcard.posp.support.SecuritySupporter;

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
			msg.setBCDField(52, SecretKeyFactory.getPin(merchantPos.getFldPinKey(),merchantPos.getFldMasterKey(),
					msg.getPINDate(),msg.getAccount()));
		}
		//替换mac 64域
		if (!StringUtil.isEmpty(msg.getMAC())) {
			//String targetMac = SecuritySupporter.transferredMac(msg.mab,host.getFldMacKey(),ApplicationContextInit.targetMasterKey);
			msg.setBCDField(64, "");
		}
	}

	public boolean isContinue() {
		return isContinue;
	}

	public Map<String, String> getParam() {
		// TODO Auto-generated method stub
		return null;
	}

}
