package com.redcard.posp.manage.model;

import java.io.Serializable;





public class TblMerchantPos  implements Serializable{
	private static final long serialVersionUID = 2090521593L;
	
	
	//columns START
	/** 变量 fldTerminalNo . */
	private String fldTerminalNo;
	/** 变量 fldMerchantCode . */
	private String fldMerchantCode;
	/** 变量 fldSelfNo . */
	private String fldSelfNo;
	/** 变量 fldName . */
	private String fldName;
	/** 变量 fldStatus . */
	private Integer fldStatus;
	/** 变量 fldType . */
	private Integer fldType;
	/** 变量 fldContacter . */
	private String fldContacter;
	/** 变量 fldPhone . */
	private String fldPhone;
	/** 变量 fldAddress . */
	private String fldAddress;
	/** 变量 fldPinKey . */
	private String fldPinKey;
	/** 变量 fldMacKey . */
	private String fldMacKey;
	/** 变量 fldMacFlag . */
	private Integer fldMacFlag;
	private String fldEncryptKey;
	/** 变量 fldPinKey . */
	private String fldMasterKey;
	/** 变量 fldMacKey . */
	private String fldCPUKey;
	/** 变量 fldSystem . */
	private Integer fldSystem;
	/** 变量 fldOperateUserNo . */
	private String fldOperateUserNo;
	/** 变量 fldOperateDate . */
	private java.util.Date fldOperateDate;
	/** 变量 fldCreateUserNo . */
	private String fldCreateUserNo;
	/** 变量 fldCreateDate . */
	private java.util.Date fldCreateDate;
	//columns END

	/**
	* TblMerchantPos 的构造函数
	*/
	public TblMerchantPos() {
	}
	/**
	* TblMerchantPos 的构造函数
	*/
	public TblMerchantPos(
		String fldTerminalNo
	) {
		this.fldTerminalNo = fldTerminalNo;
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
	public String getFldSelfNo() {
		return fldSelfNo;
	}
	public void setFldSelfNo(String fldSelfNo) {
		this.fldSelfNo = fldSelfNo;
	}
	public String getFldName() {
		return fldName;
	}
	public void setFldName(String fldName) {
		this.fldName = fldName;
	}
	public Integer getFldStatus() {
		return fldStatus;
	}
	public void setFldStatus(Integer fldStatus) {
		this.fldStatus = fldStatus;
	}
	public Integer getFldType() {
		return fldType;
	}
	public void setFldType(Integer fldType) {
		this.fldType = fldType;
	}
	public String getFldContacter() {
		return fldContacter;
	}
	public void setFldContacter(String fldContacter) {
		this.fldContacter = fldContacter;
	}
	public String getFldPhone() {
		return fldPhone;
	}
	public void setFldPhone(String fldPhone) {
		this.fldPhone = fldPhone;
	}
	public String getFldAddress() {
		return fldAddress;
	}
	public void setFldAddress(String fldAddress) {
		this.fldAddress = fldAddress;
	}
	public String getFldPinKey() {
		return fldPinKey;
	}
	public void setFldPinKey(String fldPinKey) {
		this.fldPinKey = fldPinKey;
	}
	public String getFldMacKey() {
		return fldMacKey;
	}
	public void setFldMacKey(String fldMacKey) {
		this.fldMacKey = fldMacKey;
	}
	public Integer getFldMacFlag() {
		return fldMacFlag;
	}
	public void setFldMacFlag(Integer fldMacFlag) {
		this.fldMacFlag = fldMacFlag;
	}
	public Integer getFldSystem() {
		return fldSystem;
	}
	public void setFldSystem(Integer fldSystem) {
		this.fldSystem = fldSystem;
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
	public String getFldCreateUserNo() {
		return fldCreateUserNo;
	}
	public void setFldCreateUserNo(String fldCreateUserNo) {
		this.fldCreateUserNo = fldCreateUserNo;
	}
	public java.util.Date getFldCreateDate() {
		return fldCreateDate;
	}
	public void setFldCreateDate(java.util.Date fldCreateDate) {
		this.fldCreateDate = fldCreateDate;
	}
	public String getFldEncryptKey() {
		return fldEncryptKey;
	}
	public void setFldEncryptKey(String fldEncryptKey) {
		this.fldEncryptKey = fldEncryptKey;
	}
	public String getFldMasterKey() {
		return fldMasterKey;
	}
	public void setFldMasterKey(String fldMasterKey) {
		this.fldMasterKey = fldMasterKey;
	}
	public String getFldCPUKey() {
		return fldCPUKey;
	}
	public void setFldCPUKey(String fldCPUKey) {
		this.fldCPUKey = fldCPUKey;
	}


}

