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
	
	private Map<String,Object> param = null;

	public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {

	}

	public boolean isContinue() {
		return isContinue;
	}
	
	public Map<String, Object> getParam() {
		return param;
	}
	
	public void setParam(Map<String, Object> param) {
		if (this.param!=null) {
			this.param.putAll(param);
		} else {
			this.param = param;
		}
	}
}
