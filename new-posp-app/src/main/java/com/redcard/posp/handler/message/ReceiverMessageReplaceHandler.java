package com.redcard.posp.handler.message;

import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.redcard.posp.handler.DefaultMessageHandler;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.model.TblTransactionMessage;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;
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
	
	private Map<String,Object> param = null;
	
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
				if (ApplicationContent.MSG_TYPE_SALE_RESP.equals(msg.getMSGType())) {
					//查原来交易。将原交易金额负给4域
					TblTransactionMessage tm = ApplicationContentSpringProvider.getInstance()
							.getMessageService().findSelf(msg);
					if (tm!=null) {
						msg.setBCDField(4, tm.getFldTransactionAmount());
					}
				}
				String targetMac = DefaultMessageHandler.getMAC(msg);
				msg.setBCDField(64, targetMac);
				
				
			}
		} else {
			//msg.setASCField(37, "");
			//msg.setASCField(64, "");
			/*byte[] allBytes = msg.toMessgeBytes();
			ChannelBuffer retCB = ChannelBuffers.dynamicBuffer();
			retCB.writeBytes(allBytes);
			inBoundChannel.write(retCB);*/

			/*String type = msg.getMSGType();
			type = type.substring(0,2)+(Integer.parseInt(type.substring(2))-10);
			msg.setBCDField(1, type);
			DefaultMessageHandler.returnOrgMessage(msg,inBoundChannel,msg.getResponseCode());*/
			//@TODO fyh 如果为false的话，将不会返回错误信息给pos
//			isContinue = false;
			return;
		}
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
