package com.redcard.posp.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.handler.message.ReceiverMessageLoggerHandler;
import com.redcard.posp.handler.message.ReceiverMessageReplaceHandler;
import com.redcard.posp.handler.message.ReceiverMessageValidityHandler;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.MessageFactory;
import com.redcard.posp.support.ApplicationContent;

public class BonusOutboundHandler extends SimpleChannelUpstreamHandler {
	private static Logger logger = LoggerFactory.getLogger(ShareOutboundHandler.class);

	private final Channel inboundChannel;
	
	private String messageID = "";

	public BonusOutboundHandler(Channel inboundChannel) {
		this.inboundChannel = inboundChannel;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		ChannelBuffer cb = (ChannelBuffer) e.getMessage();
		logger.info("接收到赢点平台消息, bytes=[" + ChannelBuffers.hexDump(cb)+"]");
		//Message msg = MessageFactory.getMessage(cb.array(), ApplicationContent.MESSAGE_IO_O);
		Message wpMessage = MessageFactory.getOutputMessage(cb.array());
		
		DefaultMessageHandler handler = new DefaultMessageHandler();
		handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_LOGGER, new ReceiverMessageLoggerHandler());
		handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_VALIDITY, new ReceiverMessageValidityHandler());
		handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_REPLACE, new ReceiverMessageReplaceHandler());
		handler.handler(wpMessage, inboundChannel, cb);
		if (!handler.isContinue()) {
			return;
		}
		Message inputMessage = 
		byte[] allBytes = inputMessage.toMessgeBytes();
		ChannelBuffer retCB = ChannelBuffers.dynamicBuffer();
		retCB.writeBytes(allBytes);
		inboundChannel.write(retCB);
		//inboundChannel.write(cb);
		
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		POSPInboundHandler.closeOnFlush(inboundChannel);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		POSPInboundHandler.closeOnFlush(e.getChannel());
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
}
