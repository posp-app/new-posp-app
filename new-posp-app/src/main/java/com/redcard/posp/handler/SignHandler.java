package com.redcard.posp.handler;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.MessageFactory;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;

public class SignHandler extends SimpleChannelUpstreamHandler {
	
	private static Logger logger = LoggerFactory.getLogger(SignHandler.class);

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		/*ChannelBuffer cb = (ChannelBuffer) e.getMessage();
		logger.info("接收到快钱消息, bytes=[" + ChannelBuffers.hexDump(cb)+"]");
		Message msg = MessageFactory.getMessage(cb.array(), ApplicationContent.MESSAGE_IO_O);
		logger.info("接收到快钱消息,域值：\r\n"+msg.to8583FormatString());
		ApplicationContentSpringProvider.getInstance().getMessageService().updateTransactionMessage(msg);
		if (ApplicationContent.MSG_TYPE_SIGN_ON_RESP.equals(msg.getMSGType())) {
			//签到，更新pinKey 和macKey 
			//@Todo 这里校验checkValue
			TblProxyHost tph = new TblProxyHost();
			tph.setFldPinKey(msg.getPinKey());
			tph.setFldMacKey(msg.getMacKey());
			tph.setFldEncryptKey(msg.getEncryptKey());
			tph.setFldTerminalNo(msg.getSendOrgCode());
			tph.setFldMerchantNo(msg.getCardAcceptorIdentification());
			tph.setFldOrgCode(msg.getAcquiringCode());
			ApplicationContentSpringProvider.getInstance().getProxyHostService().updateByMerchantNo(tph);
			ManageCacheService.updateProxyHostKey(tph);
			
		} else {
			
		}*/
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		e.getCause().printStackTrace();
		//logger.debug("exception caught:"+e.getCause().printStackTrace());
	}
}
