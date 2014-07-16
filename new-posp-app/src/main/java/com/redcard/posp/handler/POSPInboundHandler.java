package com.redcard.posp.handler;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.handler.message.MessageLoggerHandler;
import com.redcard.posp.handler.message.MessageReplaceHandler;
import com.redcard.posp.handler.message.MessageValidityHandler;
import com.redcard.posp.message.IMessageConverter;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.MessageFactory;
import com.redcard.posp.message.SupDataMessageConverter;
import com.redcard.posp.route.ChannelFactory;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationException;
import com.redcard.posp.support.ApplicationKey;
import com.redcard.posp.support.ResultCode;

public class POSPInboundHandler extends SimpleChannelUpstreamHandler {
	
	private static Logger logger = LoggerFactory.getLogger(POSPInboundHandler.class);

	private volatile Channel outboundChannel;
	
	public POSPInboundHandler(){
		//this.cf = cf;
	}
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		logger.debug("连接通道打开，来自于："+ctx.getPipeline().getChannel().getLocalAddress());
		
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		final Channel inBoundChannel = ctx.getChannel();
		final ChannelBuffer cb = (ChannelBuffer) e.getMessage();
		logger.info("接收POS到数据包，Bytes=["+ChannelBuffers.hexDump(cb)+"]");
		if(!DefaultMessageHandler.checkDate(cb.array())){
			logger.error("收到的报文是非法报文，直接丢弃。");
			return;
		}
		Message msg = null;
		try {
			msg = MessageFactory.getInputMessage(cb.array());
			DefaultMessageHandler handler = new DefaultMessageHandler();
			handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_LOGGER, new MessageLoggerHandler());
			handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_VALIDITY, new MessageValidityHandler());
			handler.addHandler(ApplicationContent.MESSAGE_HANDLER_NAME_REPLACE, new MessageReplaceHandler());
			handler.handler(msg, inBoundChannel, cb);
			Map<String,String> param = handler.getParam();
			/**
			 * 测试用的
			 */
			/*Map<String,String> param = new Hashtable<String,String>();
			param.put(ApplicationKey.IP, "10.0.0.91");
			param.put(ApplicationKey.PORT, "91");
			param.put(ApplicationKey.PROTOCOL_TYPE, "");*/
			if (!handler.isContinue() ||param.isEmpty()) {
				//DefaultMessageHandler.returnOrgMessage(msg, inBoundChannel, ResultCode.RESULT_CODE_93.getCode());
				return;
			}
			/**
			 * 测试用
			 */
			//param.put(ApplicationKey.PROTOCOL_TYPE,ApplicationKey.PROTOCOL_TYPE_SUPDATA);
			final String messageProtocal = param.get(ApplicationKey.PROTOCOL_TYPE);
			if (ApplicationKey.PROTOCOL_TYPE_SUPDATA.equals(messageProtocal)) {
				//这里从本读消息转换为目标消息。就是要送出去的渠道消息
				IMessageConverter converter = new SupDataMessageConverter();
				msg = converter.input2output(msg);
			}
			final Message targetMSG = msg;
			final ChannelFuture f = ChannelFactory.createChannel(param,inBoundChannel);
			f.addListener(new ChannelFutureListener() {
				public void operationComplete(ChannelFuture future)
						throws Exception {
					if (future.isSuccess()) {
						// Connection attempt succeeded:
						// Begin to accept incoming traffic.
						inBoundChannel.setReadable(true);
						ChannelBuffer outCB = ChannelBuffers.dynamicBuffer();
						//throw new Exception("-----------------------");
						if (ApplicationKey.PROTOCOL_TYPE_SUPDATA.equals(messageProtocal)) {
							outCB.writeBytes(targetMSG.toSupDataMessgeBytes());
							logger.info("发送到上联（商银通）渠道  Bytes=["+TypeConvert.bytes2HexString(targetMSG.toSupDataMessgeBytes())+"]");
						} else {
							outCB.writeBytes(targetMSG.toMessgeBytes());
							logger.info("发送到上联渠道  Bytes=["+TypeConvert.bytes2HexString(targetMSG.toMessgeBytes())+"]");
						}
						logger.info("发送到渠道  域值：\r\n"+targetMSG.to8583FormatString());
						f.getChannel().write(outCB);
					} else {
						// Close the connection if the connection attempt has
						// failed.
						inBoundChannel.close();
					}
					if (future.getCause()!=null) {
						logger.error("网络异常;异常信息["+future.getCause().getMessage()+"]");
						//超时
						DefaultMessageHandler.returnOrgMessage(targetMSG,inBoundChannel,ResultCode.RESULT_CODE_92.getCode());
					}
				}
			});
		}catch (ApplicationException ae) {
			//处理返回错误代码。
			DefaultMessageHandler.returnOrgMessage(msg,inBoundChannel,ae.getCode());
			logger.error("交易被拦截;返回码["+ae.getCode()+"];返回信息["+ae.getMessage()+"]");
			//ae.printStackTrace();
		}catch (Exception exc) {
			exc.printStackTrace();
			logger.error("消息解析，或路由发送途中出错;["+exc.getMessage()+"]");
		}
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		if (outboundChannel != null) {
			closeOnFlush(outboundChannel);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		logger.debug("exception caught:"+e.getCause().getMessage());
		//e.getCause().printStackTrace();
		closeOnFlush(e.getChannel());
	}
	

	/**
	 * Closes the specified channel after all queued write requests are flushed.
	 */
	public static void closeOnFlush(Channel ch) {
		if (ch.isConnected()) {
			ch.write(ChannelBuffers.EMPTY_BUFFER).addListener(
					ChannelFutureListener.CLOSE);
		}
	}
}
