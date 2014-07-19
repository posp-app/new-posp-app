package com.redcard.posp.handler.message;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;

import com.redcard.posp.message.Message;

/**
 * 
 * 
 * 
 * @project posp_server
 * @description 
 * 消息处理接口
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-4-22
 */
public interface IMessageHandler {

	public void handler(Message msg,Channel inBoundChannel,ChannelBuffer cb);
	
	public boolean isContinue();
	
	public Map<String,Object> getParam();
	
	public void setParam(Map<String,Object> param);
}
