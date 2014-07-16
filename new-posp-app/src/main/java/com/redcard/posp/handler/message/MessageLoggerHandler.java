package com.redcard.posp.handler.message;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationContentSpringProvider;
/**
 * 
 * 
 * 
 * @project posp_server
 * @description 
 * 消息日志处理器。
 * 在处理链的第一位
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-4-22
 */
public class MessageLoggerHandler implements IMessageHandler {
	private static Logger logger = LoggerFactory.getLogger(MessageLoggerHandler.class);

	public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {
		//logger.info("接收POS消息域值：\r\n"+msg.to8583FormatString());
		ApplicationContentSpringProvider.getInstance().getMessageService().saveTransactionMessage(msg);
	}

	public boolean isContinue() {
		return true;
	}

	public Map<String, String> getParam() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setParam(Map<String, String> param) {
		// TODO Auto-generated method stub
		
	}

	
}
