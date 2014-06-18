package com.redcard.posp.manage.service.impl;

import java.util.Date;

import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.manage.model.TblTransactionMessage;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;

public class MessageServiceImpl {


	public void saveTransactionMessage(Message message) {
		TblTransactionMessage tblTransactionMessage = new TblTransactionMessage();
		tblTransactionMessage.setFldAcquirInstitutionCode(message.getAcquiringCode());
		tblTransactionMessage.setFldAdditionalAmount(message.getBalanceAmount());
		//批次号
		tblTransactionMessage.setFldBatchNumber(message.getBatchNumber());
		tblTransactionMessage.setFldCardNo(ApplicationContentSpringProvider.doMaskCardNO(message.getAccount()));
		//卡类型
		tblTransactionMessage.setFldCardType(message.getCardType());
		tblTransactionMessage.setFldExpirationDate(message.getDateOfExpired());
		tblTransactionMessage.setFldForwardInstitutionCode(message.getSendOrgCode());
		tblTransactionMessage.setFldInstallmentAmount(message.getInstallmentAmount());
		tblTransactionMessage.setFldInstallmentPeriod(message.getInstallmentPeriod());
		//票据号
		tblTransactionMessage.setFldInvoiceNo(message.getInvoiceNo());
		//发卡银行简称
		tblTransactionMessage.setFldIssueBank(message.getIssueBank());
		tblTransactionMessage.setFldLocalDate(message.getSystemDate());
		tblTransactionMessage.setFldLocalTime(message.getSystemTime());
		tblTransactionMessage.setFldMerchantNo(message.getCardAcceptorIdentification());
		//操作员号
		tblTransactionMessage.setFldOperateNo(message.getOperateNo());
		//原交易信息
		tblTransactionMessage.setFldOriginalTransaction(message.get62Field());
		//原交易日期和时间
		tblTransactionMessage.setFldOriginalTransactionDate(message.getOriginalTransactionDate());
		tblTransactionMessage.setFldId(ApplicationContentSpringProvider
				.getSystemSequence(ApplicationContent.MODEL_TRANSACTION_MESSAGE));
		tblTransactionMessage.setFldPacketType(message.getMSGType());
		tblTransactionMessage.setFldProcessCode(message.getTransactionCode());
		tblTransactionMessage.setFldReferenceNumber(message.getRetrievalReferenceNumber());
		tblTransactionMessage.setFldResponseCode(message.getResponseCode());
		//服务点条件码
		tblTransactionMessage.setFldServiceConditionCode(message.getConditionCode());
		tblTransactionMessage.setFldServiceEntryMode(message.getPointOfServiceEntryMode());
		tblTransactionMessage.setFldServiceConditionCode(message.getConditionCode());
		//系统来源
		tblTransactionMessage.setFldSystem("--");
		tblTransactionMessage.setFldSystemDate(new Date());
		tblTransactionMessage.setFldSystemTraceNumber(message.getSystemSequence());
		tblTransactionMessage.setFldTerminalNo(message.getTerminalIdentification());
		tblTransactionMessage.setFldTransactionAmount(message.getTransactionMoney());
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
		//tblTransactionMessage.setFldResponseCode(message.getResponseCode());
		tblTransactionMessage.setFldResponseCode(TypeConvert.bytes2HexString(message.getField()[39 - 1]));
		tblTransactionMessage.setFldSystemTraceNumber(message.getSystemSequence());
		tblTransactionMessage.setFldTerminalNo(message.getTerminalIdentification());
		tblTransactionMessage.setFldCardType(message.getCardType());
		//发卡银行简称
		tblTransactionMessage.setFldIssueBank(message.getIssueBank());
		tblTransactionMessage.setFldAcquirInstitutionCode(message.getAcquiringCode());
		tblTransactionMessage.setFldForwardInstitutionCode(message.getSendOrgCode());
		tblTransactionMessage.setFldReferenceNumber(message.getRetrievalReferenceNumber());
		ApplicationContentSpringProvider.getInstance().getTransactionMessageService()
			.update(tblTransactionMessage);
		//如果是签到操作，保存pinKey,和macKey
		if (message.getMSGType().equals(ApplicationContent.MSG_TYPE_SIGN_ON_RESP)) {
			
		}
	}

}
