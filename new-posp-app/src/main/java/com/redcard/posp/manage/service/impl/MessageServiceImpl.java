package com.redcard.posp.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.oscache.util.StringUtil;
import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.handler.DefaultMessageHandler;
import com.redcard.posp.manage.model.TblTransactionMessage;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.SupDataMessageConverter;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;
import com.redcard.posp.support.ApplicationContextInit;
import com.redcard.posp.support.ResultCode;

public class MessageServiceImpl {
	private static Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);


	public void saveTransactionMessage(Message message) {
		TblTransactionMessage tblTransactionMessage = new TblTransactionMessage();
		tblTransactionMessage.setFldAcquirInstitutionCode(message.getAcquiringCode());
		tblTransactionMessage.setFldAdditionalAmount(message.getBalanceAmount());
		//批次号
		tblTransactionMessage.setFldBatchNumber(message.getBatchNumber());
		tblTransactionMessage.setFldCardNo(ApplicationContentSpringProvider.doMaskCardNO(StringUtil.isEmpty(message.getAccount())?null:message.getAccount().substring(0,19)));
		//卡类型
		tblTransactionMessage.setFldCardType(message.getCardType());
		//tblTransactionMessage.setFldExpirationDate(message.getDateOfExpired());
		//tblTransactionMessage.setFldForwardInstitutionCode(message.getSendOrgCode());
		//tblTransactionMessage.setFldInstallmentAmount(message.getInstallmentAmount());
		//tblTransactionMessage.setFldInstallmentPeriod(message.getInstallmentPeriod());
		//票据号
		//tblTransactionMessage.setFldInvoiceNo(message.getInvoiceNo());
		//发卡银行简称
		//tblTransactionMessage.setFldIssueBank(message.getIssueBank());
		tblTransactionMessage.setFldLocalDate(CommonUtil.getDate());
		tblTransactionMessage.setFldLocalTime(CommonUtil.getCurrTime());
		tblTransactionMessage.setFldMerchantNo(message.getCardAcceptorIdentification());
		//操作员号
		//tblTransactionMessage.setFldOperateNo(message.getOperateNo());
		//原交易信息
		//tblTransactionMessage.setFldOriginalTransaction(message.get62Field());
		//原交易日期和时间
		//tblTransactionMessage.setFldOriginalTransactionDate(message.getOriginalTransactionDate());
		tblTransactionMessage.setFldId(ApplicationContentSpringProvider
				.getSystemSequence(ApplicationContent.MODEL_TRANSACTION_MESSAGE));
		tblTransactionMessage.setFldPacketType(message.getMSGType());
		tblTransactionMessage.setFldProcessCode(message.getTransactionCode());
		//tblTransactionMessage.setFldReferenceNumber(message.getRetrievalReferenceNumber());
		tblTransactionMessage.setFldResponseCode(message.getResponseCode());
		//服务点条件码
		tblTransactionMessage.setFldServiceEntryMode(message.getPointOfServiceEntryMode());
		tblTransactionMessage.setFldServiceConditionCode(message.getConditionCode());
		//系统来源
		tblTransactionMessage.setFldSystem(ApplicationContextInit.nodeName);
		tblTransactionMessage.setFldSystemDate(new Date());
		tblTransactionMessage.setFldSystemTraceNumber(message.getSystemSequence());
		tblTransactionMessage.setFldTerminalNo(message.getTerminalIdentification());
		tblTransactionMessage.setFldTransactionAmount(message.getTransactionMoney());
		tblTransactionMessage.setFldType(DefaultMessageHandler.msgTransactionType(message));
		ApplicationContentSpringProvider.getInstance()
				.getTransactionMessageService().add(tblTransactionMessage);
	}
	
	public void updateTransactionMessage(Message message) {
		TblTransactionMessage tblTransactionMessage = new TblTransactionMessage();
		//批次号
		tblTransactionMessage.setFldBatchNumber(message.getBatchNumber());
		
		//票据号
		tblTransactionMessage.setFldInvoiceNo(message.getInvoiceNo());
		tblTransactionMessage.setFldMerchantNo(message.getCardAcceptorIdentification());
		tblTransactionMessage.setFldResponseCode(message.getResponseCode());
		//tblTransactionMessage.setFldResponseCode(TypeConvert.bytes2HexString(message.getField()[39 - 1]));
		tblTransactionMessage.setFldSystemTraceNumber(message.getSystemSequence());
		tblTransactionMessage.setFldTerminalNo(message.getTerminalIdentification());
		tblTransactionMessage.setFldCardType(message.getCardType());
		//发卡银行简称
		tblTransactionMessage.setFldIssueBank(message.getIssueBank());
		tblTransactionMessage.setFldAcquirInstitutionCode(message.getAcquiringCode());
		tblTransactionMessage.setFldForwardInstitutionCode(message.getSendOrgCode());
		tblTransactionMessage.setFldReferenceNumber(message.getRetrievalReferenceNumber());
		tblTransactionMessage.setFldLocalDate(message.getSystemDate());
		tblTransactionMessage.setFldLocalTime(message.getSystemTime());
		int indicate = ApplicationContent.INDICATE_FAIL;
		if (ResultCode.RESULT_CODE_00.getCode().equals(tblTransactionMessage.getFldResponseCode())) {
			indicate = ApplicationContent.INDICATE_SUCCESS;
		}
		// 如果是冲正，设置冲正标记
		if (ApplicationContent.MSG_TYPE_REVERSAL_RESP.equals(message
				.getMSGType())) {
			tblTransactionMessage.setFldReversalIndicate(indicate);
			tblTransactionMessage.setFldOriginalId(updateAndfindOriginalId(
					ApplicationContent.INDICATE_REVERSAL, indicate,
					tblTransactionMessage));
			tblTransactionMessage
					.setFldReversalIndicate(ApplicationContent.INDICATE_FAIL);
		}
		// 如果是撤销，设置撤销标记
		// 所以撤销操作，处理码都是200000
		if (ApplicationContent.MSG_TYPE_VOID_RESP.equals(message
				.getMSGType())) {
			tblTransactionMessage.setFldRevokeIndicate(indicate);
			tblTransactionMessage.setFldOriginalId(updateAndfindOriginalId(
					ApplicationContent.INDICATE_REVOKE, indicate,
					tblTransactionMessage));
			tblTransactionMessage
					.setFldRevokeIndicate(ApplicationContent.INDICATE_FAIL);
		}

		ApplicationContentSpringProvider.getInstance().getTransactionMessageService()
			.update(tblTransactionMessage);
	}
	
	/**
	 * 更新原交易，并且返回原交易ID
	 * @param indicateType
	 * @param result
	 * @param terminal
	 * @param type
	 * @param systemTraceNumber
	 * @param datetime
	 * @return
	 */
	public String updateAndfindOriginalId(String indicateType,int result,TblTransactionMessage tblTransactionMessage) {
		return "-1";//ApplicationContentSpringProvider.getInstance().getTransactionMessageService()
				//.updateAndfindOrginalId(indicateType,result,tblTransactionMessage);
	}
	
	/**
	 * 获取当天要撤销或冲正的原交易
	 * @param msg
	 * @return
	 */
	public TblTransactionMessage findOrginal(Message msg){
		TblTransactionMessage tm = new TblTransactionMessage();
		tm.setFldLocalDate(CommonUtil.getDate());
		tm.setFldTerminalNo(msg.getTerminalIdentification());
		tm.setFldMerchantNo(msg.getCardAcceptorIdentification());
		tm.setFldBatchNumber(msg.getBatchNumber());
		tm.setFldReferenceNumber(msg.getRetrievalReferenceNumber());
		List<TblTransactionMessage> tmList = ApplicationContentSpringProvider.getInstance().getTransactionMessageService()
				.getTblTransactionMessageListByObj(tm);
		if (tmList!=null && tmList.size()>0) {
			System.out.println("====>>>"+msg.to8583FormatString());
			logger.error("=====>>>>> 找到原交易;查询条件：localDate=["+tm.getFldLocalDate()+"];terminalNo=["+tm.getFldTerminalNo()
					+"];merchantNo=["+tm.getFldMerchantNo()+"];batchNumber=["+tm.getFldBatchNumber()
					+"];ReferenceNumber=["+tm.getFldReferenceNumber()+"]");
			return tmList.get(0);
		}
		logger.error("找不到原交易;查询条件：localDate=["+msg.getSystemDate()+"];terminalNo=["+msg.getTerminalIdentification()
				+"];merchantNo=["+msg.getCardAcceptorIdentification()+"];batchNumber=["+msg.getBatchNumber()
				+"];systemTraceNumber=["+msg.getOriginalSystemSequence()+"]");
		return null;
	}
	
	public TblTransactionMessage findSelf(Message msg){
		TblTransactionMessage tm = new TblTransactionMessage();
		tm.setFldLocalDate(msg.getSystemDate());
		tm.setFldTerminalNo(msg.getTerminalIdentification());
		tm.setFldMerchantNo(msg.getCardAcceptorIdentification());
		//tm.setFldBatchNumber(msg.getBatchNumber());
		tm.setFldSystemTraceNumber(msg.getSystemSequence());
		List<TblTransactionMessage> tmList = ApplicationContentSpringProvider.getInstance().getTransactionMessageService()
				.getTblTransactionMessageListByObj(tm);
		if (tmList!=null && tmList.size()>0) {
			/*logger.info("========================>>>>>>>>>>>>>>>>>>>>>>找到本交易;查询条件：localDate=["+msg.getSystemDate()+"];terminalNo=["+msg.getTerminalIdentification()
					+"];merchantNo=["+msg.getCardAcceptorIdentification()+"];batchNumber=["+msg.getBatchNumber()
					+"];systemTraceNumber=["+msg.getSystemSequence()+"]");*/
			return tmList.get(0);
		}
		logger.error("数据库找不到本交易;查询条件：localDate=["+msg.getSystemDate()+"];terminalNo=["+msg.getTerminalIdentification()
				+"];merchantNo=["+msg.getCardAcceptorIdentification()+"];batchNumber=["+msg.getBatchNumber()
				+"];systemTraceNumber=["+msg.getSystemSequence()+"]");
		return null;
	}

}
