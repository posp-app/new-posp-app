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

import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.handler.message.ReceiverMessageLoggerHandler;
import com.redcard.posp.handler.message.ReceiverMessageReplaceHandler;
import com.redcard.posp.handler.message.ReceiverMessageValidityHandler;
import com.redcard.posp.message.IMessageConverter;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.MessageFactory;
import com.redcard.posp.message.SupDataMessageConverter;
import com.redcard.posp.support.ApplicationContent;

public class SupDataOutboundHandler extends SimpleChannelUpstreamHandler {
	private static Logger logger = LoggerFactory.getLogger(ShareOutboundHandler.class);

	private final Channel inboundChannel;
	
	private String messageID = "";

	public SupDataOutboundHandler(Channel inboundChannel) {
		this.inboundChannel = inboundChannel;
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		ChannelBuffer cb = (ChannelBuffer) e.getMessage();
		logger.info("接收到商银通平台消息, bytes=[" + ChannelBuffers.hexDump(cb)+"]");
		//Message msg = MessageFactory.getMessage(cb.array(), ApplicationContent.MESSAGE_IO_O);
		Message supDataMessage = MessageFactory.getSupDataMessage(cb.array());
		IMessageConverter converter = new SupDataMessageConverter();
		Message inputMessage = converter.output2input(supDataMessage);
		DefaultMessageHandler handler = new DefaultMessageHandler();
		handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_LOGGER, new ReceiverMessageLoggerHandler());
		handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_VALIDITY, new ReceiverMessageValidityHandler());
		handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_REPLACE, new ReceiverMessageReplaceHandler());
		handler.handler(inputMessage, inboundChannel, cb);
		if (!handler.isContinue()) {
			return;
		}
		byte[] allBytes = inputMessage.toMessgeBytes();
		logger.debug("返回给POS的数据包;Bytes=["+TypeConvert.bytes2HexString(allBytes)+"]");
		logger.debug("返回给POS的8583格式数据："+inputMessage.to8583FormatString());
		ChannelBuffer retCB = ChannelBuffers.dynamicBuffer();
		retCB.writeBytes(allBytes);
		inboundChannel.write(retCB);
		
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
