package com.redcard.posp.support.command;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandReceiver implements MessageListener{
	private static Logger logger = LoggerFactory.getLogger(CommandReceiver.class);

	private ICommandHandler handler = new DefaultCommandHandler();
	
	public void onMessage(Message message) {
		TextMessage m = (TextMessage)message;
		String cmd = null;
		try {
			cmd = m.getText();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		if (cmd == null) {
			return ;
		}
		logger.info("消息监听收到命令：["+cmd+"]");
		handler.handler(cmd);
	}
	
}
