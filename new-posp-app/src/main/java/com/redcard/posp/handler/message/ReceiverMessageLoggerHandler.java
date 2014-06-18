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
 * 从快钱接收到的日志处理器。
 * 在处理链的第一位。
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-5-3
 */
public class ReceiverMessageLoggerHandler implements IMessageHandler{

	private static Logger logger = LoggerFactory.getLogger(ReceiverMessageLoggerHandler.class);
	public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {
		logger.info("接收到上联系统消息,域值：\r\n"+msg.to8583FormatString());
		ApplicationContentSpringProvider.getInstance().getMessageService().updateTransactionMessage(msg);
	}
	
	public boolean isContinue() {
		return true;
	}

	public Map<String, String> getParam() {
		// TODO Auto-generated method stub
		return null;
	}
}
