package com.redcard.posp.handler.message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.common.MacUtil;
import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationContextInit;

public class ReceiverMessageValidityHandler implements IMessageHandler {
		
	private static Logger logger = LoggerFactory.getLogger(ReceiverMessageValidityHandler.class);
	
	private boolean isContinue = true;

	public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {

	}

public boolean isContinue() {
	return isContinue;
}

public Map<String, String> getParam() {
	// TODO Auto-generated method stub
	return null;
}
}
