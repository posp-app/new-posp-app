package com.redcard.posp.manage.model;

import java.io.Serializable;





public class TblTransactionMessage  implements Serializable{
	private static final long serialVersionUID = 2098989922L;
	
	
	//columns START
	/** 变量 fldId . */
	private String fldId;
	/** 变量 fldPacketType . */
	private String fldPacketType;
	/** 变量 fldCardNo . */
	private String fldCardNo;
	/** 变量 fldProcessCode . */
	private String fldProcessCode;
	/** 变量 fldTransactionAmount . */
	private String fldTransactionAmount;
	/** 变量 fldSystemTraceNumber . */
	private String fldSystemTraceNumber;
	/** 变量 fldLocalTime . */
	private String fldLocalTime;
	/** 变量 fldLocalDate . */
	private String fldLocalDate;
	/** 变量 fldExpirationDate . */
	private String fldExpirationDate;
	/** 变量 fldServiceEntryMode . */
	private String fldServiceEntryMode;
	/** 变量 fldServiceConditionCode . */
	private String fldServiceConditionCode;
	/** 变量 fldAcquirInstitutionCode . */
	private String fldAcquirInstitutionCode;
	/** 变量 fldForwardInstitutionCode . */
	private String fldForwardInstitutionCode;
	/** 变量 fldReferenceNumber . */
	private String fldReferenceNumber;
	/** 变量 fldResponseCode . */
	private String fldResponseCode;
	/** 变量 fldTerminalNo . */
	private String fldTerminalNo;
	/** 变量 fldMerchantNo . */
	private String fldMerchantNo;
	/** 变量 fldBatchNumber . */
	private String fldBatchNumber;
	/** 变量 fldAdditionalAmount . */
	private String fldAdditionalAmount;
	/** 变量 fldOperateNo . */
	private String fldOperateNo;
	/** 变量 fldInvoiceNo . */
	private String fldInvoiceNo;
	/** 变量 fldCardType . */
	private String fldCardType;
	/** 变量 fldIssueBank . */
	private String fldIssueBank;
	/** 变量 fldOriginalTransaction . */
	private String fldOriginalTransaction;
	/** 变量 fldOriginalTransactionDate . */
	private String fldOriginalTransactionDate;
	/** 变量 fldInstallmentPeriod . */
	private String fldInstallmentPeriod;
	/** 变量 fldInstallmentAmount . */
	private String fldInstallmentAmount;
	/** 变量 fldSystem . */
	private String fldSystem;
	/** 变量 fldSystemDate . */
	private java.util.Date fldSystemDate;
	//columns END

	/**
	* TblTransactionMessage 的构造函数
	*/
	public TblTransactionMessage() {
	}
	/**
	* TblTransactionMessage 的构造函数
	*/
	public TblTransactionMessage(
		String fldId
	) {
		this.fldId = fldId;
	}
	public String getFldId() {
		return fldId;
	}
	public void setFldId(String fldId) {
		this.fldId = fldId;
	}
	public String getFldPacketType() {
		return fldPacketType;
	}
	public void setFldPacketType(String fldPacketType) {
		this.fldPacketType = fldPacketType;
	}
	public String getFldCardNo() {
		return fldCardNo;
	}
	public void setFldCardNo(String fldCardNo) {
		this.fldCardNo = fldCardNo;
	}
	public String getFldProcessCode() {
		return fldProcessCode;
	}
	public void setFldProcessCode(String fldProcessCode) {
		this.fldProcessCode = fldProcessCode;
	}
	public String getFldTransactionAmount() {
		return fldTransactionAmount;
	}
	public void setFldTransactionAmount(String fldTransactionAmount) {
		this.fldTransactionAmount = fldTransactionAmount;
	}
	public String getFldSystemTraceNumber() {
		return fldSystemTraceNumber;
	}
	public void setFldSystemTraceNumber(String fldSystemTraceNumber) {
		this.fldSystemTraceNumber = fldSystemTraceNumber;
	}
	public String getFldLocalTime() {
		return fldLocalTime;
	}
	public void setFldLocalTime(String fldLocalTime) {
		this.fldLocalTime = fldLocalTime;
	}
	public String getFldLocalDate() {
		return fldLocalDate;
	}
	public void setFldLocalDate(String fldLocalDate) {
		this.fldLocalDate = fldLocalDate;
	}
	public String getFldExpirationDate() {
		return fldExpirationDate;
	}
	public void setFldExpirationDate(String fldExpirationDate) {
		this.fldExpirationDate = fldExpirationDate;
	}
	public String getFldServiceEntryMode() {
		return fldServiceEntryMode;
	}
	public void setFldServiceEntryMode(String fldServiceEntryMode) {
		this.fldServiceEntryMode = fldServiceEntryMode;
	}
	public String getFldServiceConditionCode() {
		return fldServiceConditionCode;
	}
	public void setFldServiceConditionCode(String fldServiceConditionCode) {
		this.fldServiceConditionCode = fldServiceConditionCode;
	}
	public String getFldAcquirInstitutionCode() {
		return fldAcquirInstitutionCode;
	}
	public void setFldAcquirInstitutionCode(String fldAcquirInstitutionCode) {
		this.fldAcquirInstitutionCode = fldAcquirInstitutionCode;
	}
	public String getFldForwardInstitutionCode() {
		return fldForwardInstitutionCode;
	}
	public void setFldForwardInstitutionCode(String fldForwardInstitutionCode) {
		this.fldForwardInstitutionCode = fldForwardInstitutionCode;
	}
	public String getFldReferenceNumber() {
		return fldReferenceNumber;
	}
	public void setFldReferenceNumber(String fldReferenceNumber) {
		this.fldReferenceNumber = fldReferenceNumber;
	}
	public String getFldResponseCode() {
		return fldResponseCode;
	}
	public void setFldResponseCode(String fldResponseCode) {
		this.fldResponseCode = fldResponseCode;
	}
	public String getFldTerminalNo() {
		return fldTerminalNo;
	}
	public void setFldTerminalNo(String fldTerminalNo) {
		this.fldTerminalNo = fldTerminalNo;
	}
	public String getFldMerchantNo() {
		return fldMerchantNo;
	}
	public void setFldMerchantNo(String fldMerchantNo) {
		this.fldMerchantNo = fldMerchantNo;
	}
	public String getFldBatchNumber() {
		return fldBatchNumber;
	}
	public void setFldBatchNumber(String fldBatchNumber) {
		this.fldBatchNumber = fldBatchNumber;
	}
	public String getFldAdditionalAmount() {
		return fldAdditionalAmount;
	}
	public void setFldAdditionalAmount(String fldAdditionalAmount) {
		this.fldAdditionalAmount = fldAdditionalAmount;
	}
	public String getFldOperateNo() {
		return fldOperateNo;
	}
	public void setFldOperateNo(String fldOperateNo) {
		this.fldOperateNo = fldOperateNo;
	}
	public String getFldInvoiceNo() {
		return fldInvoiceNo;
	}
	public void setFldInvoiceNo(String fldInvoiceNo) {
		this.fldInvoiceNo = fldInvoiceNo;
	}
	public String getFldCardType() {
		return fldCardType;
	}
	public void setFldCardType(String fldCardType) {
		this.fldCardType = fldCardType;
	}
	public String getFldIssueBank() {
		return fldIssueBank;
	}
	public void setFldIssueBank(String fldIssueBank) {
		this.fldIssueBank = fldIssueBank;
	}
	public String getFldOriginalTransaction() {
		return fldOriginalTransaction;
	}
	public void setFldOriginalTransaction(String fldOriginalTransaction) {
		this.fldOriginalTransaction = fldOriginalTransaction;
	}
	public String getFldOriginalTransactionDate() {
		return fldOriginalTransactionDate;
	}
	public void setFldOriginalTransactionDate(String fldOriginalTransactionDate) {
		this.fldOriginalTransactionDate = fldOriginalTransactionDate;
	}
	public String getFldInstallmentPeriod() {
		return fldInstallmentPeriod;
	}
	public void setFldInstallmentPeriod(String fldInstallmentPeriod) {
		this.fldInstallmentPeriod = fldInstallmentPeriod;
	}
	public String getFldInstallmentAmount() {
		return fldInstallmentAmount;
	}
	public void setFldInstallmentAmount(String fldInstallmentAmount) {
		this.fldInstallmentAmount = fldInstallmentAmount;
	}
	public String getFldSystem() {
		return fldSystem;
	}
	public void setFldSystem(String fldSystem) {
		this.fldSystem = fldSystem;
	}
	public java.util.Date getFldSystemDate() {
		return fldSystemDate;
	}
	public void setFldSystemDate(java.util.Date fldSystemDate) {
		this.fldSystemDate = fldSystemDate;
	}


}

