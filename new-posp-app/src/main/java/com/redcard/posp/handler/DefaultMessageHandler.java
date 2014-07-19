package com.redcard.posp.handler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.common.MacUtil;
import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.handler.message.IMessageHandler;
import com.redcard.posp.handler.secret.redcard.SecretKeyFactory;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;

/**
 * 
 * 
 * @project falcon_server
 * @description
 *          <p>
 *          8583 包处理器
 *          </p>
 *          这个处理器做8583包处理的父处理器。<br>
 *          她可能会包含以下多种子处理器。<br>
 *          1、数据包格式验证处理器。<br>
 *          2、数据正确性验证处理器。<br>
 *          3、响应数据生成处理器。<br>
 *          4、日记处理器。<br>
 *          5、数据包路由处理器。<br>
 *          6、数据包请求转发处理器。<br>
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date Dec 30, 2011
 */
public class DefaultMessageHandler implements IMessageHandler{

	private static Logger logger = LoggerFactory.getLogger(DefaultMessageHandler.class);

	private Map<String,IMessageHandler> handlers = new LinkedHashMap<String,IMessageHandler>();
	
	private boolean isContinue = true;
	
	private Map<String,Object> param = new HashMap<String,Object>();

	
	public void addHandler(String name,IMessageHandler handler){
		handlers.put(name,handler);
	}
	
	public void handler(Message msg,Channel inBoundChannel,ChannelBuffer cb) {
		for (IMessageHandler h:handlers.values()) {
			h.setParam(param);
			h.handler(msg, inBoundChannel, cb);
			if (h.getParam()!=null) {
				param.putAll(h.getParam());
			}
			if (!h.isContinue()) {
				isContinue = false;
				return;
			}
		}
	}
	public boolean isContinue() {
		return isContinue;
	}
	

	
	public static void returnOrgMessage(Message msg,Channel inBoundChannel,String responseCode) {
		if (msg !=null) {
			String type = msg.getMSGType();
			type = type.substring(0,2)+(Integer.parseInt(type.substring(2))+10);
			msg.setBCDField(1, type);
			msg.setASCField(39, responseCode);
			//重新计算mac
			if (!type.equals(ApplicationContent.MSG_TYPE_SIGN_ON_RESP)) {
				msg.setBCDField(64, getMAC(msg));
			}
			//System.out.println("response code="+TypeConvert.bytes2HexString(msg.getField()[39 - 1]));
			ApplicationContentSpringProvider.getInstance().getMessageService().updateTransactionMessage(msg);
			ChannelBuffer outCB = ChannelBuffers.dynamicBuffer();
			outCB.writeBytes(msg.toMessgeBytes());
			logger.debug("返回给POS的数据包;Bytes=["+TypeConvert.bytes2HexString(msg.toMessgeBytes())+"]");
			logger.debug("返回给POS的8583格式数据："+msg.to8583FormatString());
			inBoundChannel.write(outCB);
		}
	}
	
	public static String getMAB(Message msg) {
		int mabLength = msg.getBitMap().length;
		int i = 0;
		for (byte[] bs:msg.getField()) {
			if (i==0||i==63 ||bs ==null) {
				i++;
				continue;
			}
			mabLength = mabLength+bs.length;
			i++;
		}
		
		//int mabLength = msg.buf.length-9-8;
		byte[] mab = new byte[mabLength];
		int pos = 0;
		i = 0;
		System.arraycopy(msg.getBitMap(), 0, mab, pos, msg.getBitMap().length);
		pos = pos + msg.getBitMap().length;
		for (byte[] bs:msg.getField()) {
			if (i==0||i==63 ||bs ==null) {
				i++;
				continue;
			}
			System.arraycopy(bs, 0, mab, pos, bs.length);
			pos = pos+bs.length;
			i++;
		}
		//System.arraycopy(msg.buf, 9, mab, 0, mabLength);
		msg.mab = mab;
		String mabString = TypeConvert.bytes2HexString(mab);
		logger.debug("MAB=["+mabString+"]");;
		return mabString;
	}
	
	/**
	 * 校验MAC是否正确
	 * @param msg
	 * @return
	 */
	public static boolean checkMAC(Message msg) {
		String mac = getMAC(msg);
		return (!mac.substring(0, 16).equals(msg.getMAC().substring(0, 16)));
	}
	
	/**
	 * 计算MAC
	 * @param msg
	 * @return
	 */
	public static String getMAC(Message msg) {
		String mabString = DefaultMessageHandler.getMAB(msg);
		TblMerchantPos pos = ManageCacheService.findPos(msg.getTerminalIdentification());
		String workMac = SecretKeyFactory.getDesWorkKey(pos.getFldMacKey(),pos.getFldMasterKey());
		String mac = MacUtil.redCardMac(workMac, null, mabString);
		return mac;
	}
	
	/**
	 * 校验数据的合法性
	 * @return
	 */
	public static boolean checkDate(byte[] date) {
		byte[] length = new byte[2];
		//取出消息长度两字节
		System.arraycopy(date, 0, length, 0,2);
		int iLength = TypeConvert.byte2short(length);
		//System.out.println(iLength+"="+(date.length));
		if (iLength>(date.length)){
			//logger.error("收到的是非法包文。");
			return false;
		} else {
			return true;
		}
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
	
	/**
	 * 确定是那种数据字典的交易类型
	 * 消费 10 
		消费撤销 11
		冲正 30 
		签到 90
		下载主密钥 91 
		余额查询 92

	 * @param msg
	 * @return
	 */
	public static String msgTransactionType(Message msg) {
		if ((ApplicationContent.MSG_TYPE_SALE_REQ.equals(msg.getMSGType())||
				ApplicationContent.MSG_TYPE_SALE_RESP.equals(msg.getMSGType()))&&
				ApplicationContent.MSG_PROCESS_CODE_000000.equals(msg.getTransactionCode())){
			return ApplicationContent.SYSTEM_TRANSACTION_TYPE_10;
		}
		if ((ApplicationContent.MSG_TYPE_VOID_REQ.equals(msg.getMSGType())||
				ApplicationContent.MSG_TYPE_VOID_RESP.equals(msg.getMSGType()))){
			return ApplicationContent.SYSTEM_TRANSACTION_TYPE_11;
		}
		if ((ApplicationContent.MSG_TYPE_REVERSAL_REQ.equals(msg.getMSGType())||
				ApplicationContent.MSG_TYPE_REVERSAL_RESP.equals(msg.getMSGType()))){
			return ApplicationContent.SYSTEM_TRANSACTION_TYPE_30;
		}
		if ((ApplicationContent.MSG_TYPE_SIGN_ON_REQ.equals(msg.getMSGType())||
				ApplicationContent.MSG_TYPE_SIGN_ON_RESP.equals(msg.getMSGType()))&&
				ApplicationContent.MSG_PROCESS_CODE_910000.equals(msg.getTransactionCode())){
			return ApplicationContent.SYSTEM_TRANSACTION_TYPE_90;
		}
		if ((ApplicationContent.MSG_TYPE_SIGN_ON_REQ.equals(msg.getMSGType())||
				ApplicationContent.MSG_TYPE_SIGN_ON_RESP.equals(msg.getMSGType()))&&
				ApplicationContent.MSG_PROCESS_CODE_900000.equals(msg.getTransactionCode())){
			return ApplicationContent.SYSTEM_TRANSACTION_TYPE_91;
		}
		if ((ApplicationContent.MSG_TYPE_BALANCE_REQ.equals(msg.getMSGType())||
				ApplicationContent.MSG_TYPE_BALANCE_RESP.equals(msg.getMSGType()))&&
				ApplicationContent.MSG_PROCESS_CODE_310000.equals(msg.getTransactionCode())){
			return ApplicationContent.SYSTEM_TRANSACTION_TYPE_92;
		}
		return "00";
	}
}
