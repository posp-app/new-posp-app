package com.redcard.posp.manage.model;

import java.io.Serializable;





public class TblTrade  implements Serializable{
	private static final long serialVersionUID = 2098837001L;
	
	
	//columns START
	/** 变量 fldId . */
	private String fldId;
	/** 变量 fldTradeDate . */
	private java.util.Date fldTradeDate;
	/** 变量 fldCardNo . */
	private String fldCardNo;
	/** 变量 fldTerminalNo . */
	private String fldTerminalNo;
	/** 变量 fldMerchantCode . */
	private String fldMerchantCode;
	/** 变量 fldMerchantName . */
	private String fldMerchantName;
	/** 变量 fldTerminalName . */
	private String fldTerminalName;
	/** 变量 fldPosSerial . */
	private String fldPosSerial;
	/** 变量 fldBatchNumber . */
	private String fldBatchNumber;
	/** 变量 fldType . */
	private String fldType;
	/** 变量 fldAmount . */
	private java.math.BigDecimal fldAmount;
	/** 变量 fldAfterAmount . */
	private java.math.BigDecimal fldAfterAmount;
	/** 变量 fldPayMoney . */
	private java.math.BigDecimal fldPayMoney;
	/** 变量 fldResponseCode . */
	private String fldResponseCode;
	/** 变量 fldSystemDate . */
	private String fldSystemDate;
	/** 变量 fldSystemTime . */
	private String fldSystemTime;
	/** 变量 fldSettleFlag . */
	private Integer fldSettleFlag;
	/** 变量 fldResultFlag . */
	private Integer fldResultFlag;
	/** 变量 fldSettleDate . */
	private java.util.Date fldSettleDate;
	/** 变量 fldSystem . */
	private Integer fldSystem;
	/** 变量 fldDayCutDate . */
	private java.util.Date fldDayCutDate;
	/** 变量 fldOperateUserNo . */
	private String fldOperateUserNo;
	/** 变量 fldOperateDate . */
	private java.util.Date fldOperateDate;
	//columns END

	/**
	* TblTrade 的构造函数
	*/
	public TblTrade() {
	}
	/**
	* TblTrade 的构造函数
	*/
	public TblTrade(
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
	public java.util.Date getFldTradeDate() {
		return fldTradeDate;
	}
	public void setFldTradeDate(java.util.Date fldTradeDate) {
		this.fldTradeDate = fldTradeDate;
	}
	public String getFldCardNo() {
		return fldCardNo;
	}
	public void setFldCardNo(String fldCardNo) {
		this.fldCardNo = fldCardNo;
	}
	public String getFldTerminalNo() {
		return fldTerminalNo;
	}
	public void setFldTerminalNo(String fldTerminalNo) {
		this.fldTerminalNo = fldTerminalNo;
	}
	public String getFldMerchantCode() {
		return fldMerchantCode;
	}
	public void setFldMerchantCode(String fldMerchantCode) {
		this.fldMerchantCode = fldMerchantCode;
	}
	public String getFldMerchantName() {
		return fldMerchantName;
	}
	public void setFldMerchantName(String fldMerchantName) {
		this.fldMerchantName = fldMerchantName;
	}
	public String getFldTerminalName() {
		return fldTerminalName;
	}
	public void setFldTerminalName(String fldTerminalName) {
		this.fldTerminalName = fldTerminalName;
	}
	public String getFldPosSerial() {
		return fldPosSerial;
	}
	public void setFldPosSerial(String fldPosSerial) {
		this.fldPosSerial = fldPosSerial;
	}
	public String getFldBatchNumber() {
		return fldBatchNumber;
	}
	public void setFldBatchNumber(String fldBatchNumber) {
		this.fldBatchNumber = fldBatchNumber;
	}
	public String getFldType() {
		return fldType;
	}
	public void setFldType(String fldType) {
		this.fldType = fldType;
	}
	public java.math.BigDecimal getFldAmount() {
		return fldAmount;
	}
	public void setFldAmount(java.math.BigDecimal fldAmount) {
		this.fldAmount = fldAmount;
	}
	public java.math.BigDecimal getFldAfterAmount() {
		return fldAfterAmount;
	}
	public void setFldAfterAmount(java.math.BigDecimal fldAfterAmount) {
		this.fldAfterAmount = fldAfterAmount;
	}
	public java.math.BigDecimal getFldPayMoney() {
		return fldPayMoney;
	}
	public void setFldPayMoney(java.math.BigDecimal fldPayMoney) {
		this.fldPayMoney = fldPayMoney;
	}
	public String getFldResponseCode() {
		return fldResponseCode;
	}
	public void setFldResponseCode(String fldResponseCode) {
		this.fldResponseCode = fldResponseCode;
	}
	public String getFldSystemDate() {
		return fldSystemDate;
	}
	public void setFldSystemDate(String fldSystemDate) {
		this.fldSystemDate = fldSystemDate;
	}
	public String getFldSystemTime() {
		return fldSystemTime;
	}
	public void setFldSystemTime(String fldSystemTime) {
		this.fldSystemTime = fldSystemTime;
	}
	public Integer getFldSettleFlag() {
		return fldSettleFlag;
	}
	public void setFldSettleFlag(Integer fldSettleFlag) {
		this.fldSettleFlag = fldSettleFlag;
	}
	public Integer getFldResultFlag() {
		return fldResultFlag;
	}
	public void setFldResultFlag(Integer fldResultFlag) {
		this.fldResultFlag = fldResultFlag;
	}
	public java.util.Date getFldSettleDate() {
		return fldSettleDate;
	}
	public void setFldSettleDate(java.util.Date fldSettleDate) {
		this.fldSettleDate = fldSettleDate;
	}
	public Integer getFldSystem() {
		return fldSystem;
	}
	public void setFldSystem(Integer fldSystem) {
		this.fldSystem = fldSystem;
	}
	public java.util.Date getFldDayCutDate() {
		return fldDayCutDate;
	}
	public void setFldDayCutDate(java.util.Date fldDayCutDate) {
		this.fldDayCutDate = fldDayCutDate;
	}
	public String getFldOperateUserNo() {
		return fldOperateUserNo;
	}
	public void setFldOperateUserNo(String fldOperateUserNo) {
		this.fldOperateUserNo = fldOperateUserNo;
	}
	public java.util.Date getFldOperateDate() {
		return fldOperateDate;
	}
	public void setFldOperateDate(java.util.Date fldOperateDate) {
		this.fldOperateDate = fldOperateDate;
	}


}

