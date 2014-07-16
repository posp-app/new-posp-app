package com.redcard.posp.message;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.oscache.util.StringUtil;
import com.redcard.posp.cache.ApplicationContextCache;
import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.common.DecodeUtil;
import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.manage.model.TblTransactionMessage;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;
import com.redcard.posp.support.ApplicationContextInit;
import com.redcard.posp.support.ApplicationKey;

/**
 * 
 * @author posp
 *
 */
public class SupDataMessageConverter implements IMessageConverter {
	private static Logger logger = LoggerFactory.getLogger(SupDataMessageConverter.class);

	private static void setLengthField(Message m,int fieldNumber,String value,int length) {
		byte[] t = new byte[length];
		System.arraycopy(value.getBytes(), 0, 
				t, 0, value.getBytes().length-1);
		m.setByteField(fieldNumber, t);
	}
	public static String getSupDataMessageType(String messageType) {
		if (ApplicationContent.MSG_TYPE_SALE_REQ.equals(messageType)
				||ApplicationContent.MSG_TYPE_SALE_RESP.equals(messageType)){
			return ApplicationContent.MSG_TYPE_SUP_DATA_SALE;
		}
		if (ApplicationContent.MSG_TYPE_BALANCE_REQ.equals(messageType)
				||ApplicationContent.MSG_TYPE_BALANCE_RESP.equals(messageType)){
			return ApplicationContent.MSG_TYPE_SUP_DATA_BALANCE;
		}
		if (ApplicationContent.MSG_TYPE_REVERSAL_REQ.equals(messageType)
				||ApplicationContent.MSG_TYPE_REVERSAL_RESP.equals(messageType)){
			return ApplicationContent.MSG_TYPE_SUP_DATA_REVERSAL;
		}
		if (ApplicationContent.MSG_TYPE_VOID_REQ.equals(messageType)
				||ApplicationContent.MSG_TYPE_VOID_RESP.equals(messageType)){
			return ApplicationContent.MSG_TYPE_SUP_DATA_VOID;
		}
		logger.error("商银通只支持消费、消费撤销、查询、冲正操作，不支持将["+messageType+"]转换成商银通的消息");
		return "";
	}
	/**
	 * pos 报文到商银通报文
	 */
	public Message input2output(Message input) throws Exception {
		Message m = createSupDataMessage(ApplicationContextCache.applicationMessageFormat.get(ApplicationKey.PROTOCOL_TYPE_SUPDATA));
		m.setASCField(1, getSupDataMessageType(input.getMSGType()));
		if (!StringUtil.isEmpty(input.getAccount())){
			setLengthField(m,2,input.getAccount(),19);
		}
		if (!StringUtil.isEmpty(input.getPINDate())){
			byte[] t = new byte[8];
			System.arraycopy(DecodeUtil.str2Bcd(input.getPINDate().substring(0,12)), 0, 
					t, 0, DecodeUtil.str2Bcd(input.getPINDate().substring(0,12)).length);
			m.setByteField(3, t);
		}
		if (!StringUtil.isEmpty(input.getTrack2())){
			setLengthField(m,4,input.getTrack2().replace("D", "="),40);
		}
		if (!StringUtil.isEmpty(input.getTrack3())){
			setLengthField(m,4,input.getTrack3().replace("D", "="),101);
		}
		if (!StringUtil.isEmpty(input.getTransactionMoney())){
			m.setASCField(6,input.getTransactionMoney());
		}
		m.setASCField(8, input.getSystemSequence());
		m.setASCField(14, input.getTerminalIdentification());
		m.setASCField(15, input.getCardAcceptorIdentification());
		m.setASCField(16, input.get62Field());
		m.setASCField(19, ApplicationContextInit.SUPDATA_OPERATOR);
		m.setASCField(20, ApplicationContextInit.SUPDATA_OPERATOR_PASSWORD);
		if (input.getMSGType().equals(ApplicationContent.MSG_TYPE_SALE_REQ)) {
		} else if (input.getMSGType().equals(ApplicationContent.MSG_TYPE_REVERSAL_REQ)){
			m.setASCField(8, "A"+input.getSystemSequence().substring(1));
			m.setASCField(9, input.getSystemSequence());
			//m.setASCField(27, input.getSystemDate());
			String type = "00";
			if (ApplicationContent.MSG_PROCESS_CODE_000000.equals(input.getTransactionCode())) {
				type = getSupDataMessageType(ApplicationContent.MSG_TYPE_SALE_REQ);
			} else {
				type = getSupDataMessageType(ApplicationContent.MSG_TYPE_VOID_REQ);
			}
			m.setASCField(28, type);
		} else if (input.getMSGType().equals(ApplicationContent.MSG_TYPE_BALANCE_REQ)){
		} else if (input.getMSGType().equals(ApplicationContent.MSG_TYPE_VOID_REQ)){
			m.setASCField(9, input.getOriginalSystemSequence());
			TblTransactionMessage tm = ApplicationContentSpringProvider.getInstance()
					.getMessageService().findOrginal(input);
			//通过原交易流水，去数据库中查原交易，找出原交易类型
			//m.setASCField(27, input.getOriginalTransactionDate());
			m.setASCField(28, getSupDataMessageType(ApplicationContent.MSG_TYPE_SALE_REQ));
			if (tm!=null) {
				m.setASCField(9, tm.getFldSystemTraceNumber());
				m.setASCField(27, tm.getFldLocalDate().substring(4,8)+tm.getFldLocalTime());			
			}
			
		}
		logger.info("convert to byte["+TypeConvert.bytes2HexString(m.toSupDataMessgeBytes())+"]");
		return m;
	}

	/**
	 * 商银通报文到pos报文
	 */
	public Message output2input(Message output) throws Exception {
		Message m = new Message(ApplicationContextCache.inputMessageFormat);
		SupDataMessage o = (SupDataMessage)output;
		if (!StringUtil.isEmpty(o.getAccount())) {
			m.setBCDField(2, "19"+o.getAccount()+"0");
		}
		m.setBCDField(11, o.getSystemSequence());
		m.setBCDField(12, o.getSystemTime());
		m.setBCDField(13, new SimpleDateFormat("yyyy").format(new Date())+o.getSystemDate());
		m.setASCField(39, o.getResponseCode());
		m.setASCField(41, o.getTerminalIdentification());
		m.setASCField(42, o.getCardAcceptorIdentification());
		if (!StringUtil.isEmpty(o.getBalanceAmount())) {
			String balanceAmount = "0"+o.getBalanceAmount();
			int length = balanceAmount.getBytes().length+2;
			byte[] t = new byte[length];
			System.arraycopy(DecodeUtil.str2Bcd(CommonUtil.addLeftZero(Integer.toString(balanceAmount.length()),4)), 0, t, 0, 2);
			System.arraycopy(balanceAmount.getBytes(), 0, t,2, balanceAmount.getBytes().length);
			//System.out.println("-------------["+TypeConvert.bytes2HexString(t)+"]----------------");
			m.setByteField(54, t);
		}
		if (!StringUtil.isEmpty(o.getBatchNumber())) {
			int length = o.getBatchNumber().getBytes().length+2;
			byte[] t = new byte[length];
			System.arraycopy(DecodeUtil.str2Bcd(CommonUtil.addLeftZero(Integer.toString(o.getBatchNumber().length()),4)), 0, t, 0, 2);
			System.arraycopy(o.getBatchNumber().getBytes(), 0, t,2, o.getBatchNumber().getBytes().length);
			m.setByteField(62, t);
		}
		m.setBCDField(64, "00000000");
		if (ApplicationContent.MSG_TYPE_SUP_DATA_SALE.equals(o.getMSGType())) {
			m.setBCDField(1, ApplicationContent.MSG_TYPE_SALE_RESP);
			m.setBCDField(3, ApplicationContent.MSG_PROCESS_CODE_000000);
			m.setASCField(37, o.getRetrievalReferenceNumber());
			m.setASCField(38, o.getAuthorizationResponseCode());
		} else if (ApplicationContent.MSG_TYPE_SUP_DATA_VOID.equals(o.getMSGType())) {
				m.setBCDField(1, ApplicationContent.MSG_TYPE_VOID_RESP);
				m.setBCDField(3, ApplicationContent.MSG_PROCESS_CODE_000000);
		} else if (ApplicationContent.MSG_TYPE_SUP_DATA_BALANCE.equals(o.getMSGType())) {
			m.setBCDField(1, ApplicationContent.MSG_TYPE_BALANCE_RESP);
			m.setBCDField(3, ApplicationContent.MSG_PROCESS_CODE_310000);
		} else if (ApplicationContent.MSG_TYPE_SUP_DATA_REVERSAL.equals(o.getMSGType())) {
			m.setBCDField(1, ApplicationContent.MSG_TYPE_REVERSAL_RESP);
			m.setBCDField(3, ApplicationContent.MSG_PROCESS_CODE_000000);
		}
		return m;
	}
	
	private Message createSupDataMessage(MessageFormat targetTransFormat) throws Exception {
		Message result = new Message(targetTransFormat);
		//注意 商银通是没有消息头
		//构造消息的消息头head
		int headLength = 0;
		//int allPackageLength = 0;
		for (FormatMetadata fm:targetTransFormat.getHead()) {
			headLength = headLength+fm.getLength();
		}
		for (FormatMetadata fm:targetTransFormat.fields){
			if (ApplicationKey.USE_Y.equals(fm.getUse())){
				result.field[fm.getNumber()-1] = new byte[fm.getLength()];
			}
		}
		return result;
	}

}
