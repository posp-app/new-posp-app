package com.redcard.posp.handler.message;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.redcard.posp.handler.DefaultMessageHandler;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ResultCode;

/**
 * 
 * 
 * 
 * @project posp_server
 * @description 
 * 接收上联posp系统的消息，替换为终端对应域
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-5-1
 */
public class ReceiverMessageReplaceHandler implements IMessageHandler {

	private boolean isContinue = true;
	
	public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {
		if (ResultCode.RESULT_CODE_00.getCode().equals(msg.getResponseCode())) {
			TblMerchantPos merchantPos = ManageCacheService.findPos(msg.getTerminalIdentification());
			if (merchantPos ==null) {
				byte[] allBytes = msg.toMessgeBytes();
				ChannelBuffer retCB = ChannelBuffers.dynamicBuffer();
				retCB.writeBytes(allBytes);
				inBoundChannel.write(retCB);
				isContinue = false;
				return;
			} else {
				//替换mac 64域//其他报文，首先做mac校验。
				/*int mabLength = msg.buf.length-9-8;
				byte[] mab = new byte[mabLength];
				System.arraycopy(msg.buf, 9, mab, 0, mabLength);
				msg.mab = mab;
				String mabString = TypeConvert.bytes2HexString(mab);
				TblMerchantPos pos = ManageCacheService.findPos(msg.getTerminalIdentification());
				String workMac = SecretKeyFactory.getDesWorkKey(pos.getFldMacKey(),pos.getFldMasterKey());
				String targetMac = MacUtil.redCardMac(workMac, null, mabString);*/
				String targetMac = DefaultMessageHandler.getMAC(msg);
				msg.setBCDField(64, targetMac);
				
			}
		} else {
			byte[] allBytes = msg.toMessgeBytes();
			ChannelBuffer retCB = ChannelBuffers.dynamicBuffer();
			retCB.writeBytes(allBytes);
			inBoundChannel.write(retCB);
			isContinue = false;
			return;
		}
	}

	public boolean isContinue() {
		return isContinue;
	}

	public Map<String, String> getParam() {
		// TODO Auto-generated method stub
		return null;
	}

}
