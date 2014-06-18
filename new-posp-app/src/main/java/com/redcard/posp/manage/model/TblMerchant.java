package com.redcard.posp.manage.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;






public class TblMerchant  implements Serializable{
	private static final long serialVersionUID = 263766272L;
	
	
	//columns START
	/** 变量 fldCode . */
	private String fldCode;
	/** 变量 fldGroupCode . */
	private String fldGroupCode;
	/** 变量 fldBrandCode . */
	private String fldBrandCode;
	/** 变量 fldFullName . */
	private String fldFullName;
	/** 变量 fldShortName . */
	private String fldShortName;
	/** 变量 fldStatus . */
	private Integer fldStatus;
	/** 变量 fldSettleType . */
	private Integer fldSettleType;
	/** 变量 fldIndustryType . */
	private String fldIndustryType;
	/** 变量 fldAddress . */
	private String fldAddress;
	/** 变量 fldPostcode . */
	private String fldPostcode;
	/** 变量 fldContacter . */
	private String fldContacter;
	/** 变量 fldPhone . */
	private String fldPhone;
	/** 变量 fldMobiles . */
	private String fldMobiles;
	/** 变量 fldEmails . */
	private String fldEmails;
	/** 变量 fldFax . */
	private String fldFax;
	/** 变量 fldPinKey . */
	private String fldPinKey;
	/** 变量 fldMacKey . */
	private String fldMacKey;
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
	
	
	private List<TblMerchantPos> posList = new ArrayList<TblMerchantPos>();;

	/**
	* TblMerchant 的构造函数
	*/
	public TblMerchant() {
	}
	/**
	* TblMerchant 的构造函数
	*/
	public TblMerchant(
		String fldCode
	) {
		this.fldCode = fldCode;
	}
	public String getFldCode() {
		return fldCode;
	}
	public void setFldCode(String fldCode) {
		this.fldCode = fldCode;
	}
	public String getFldGroupCode() {
		return fldGroupCode;
	}
	public void setFldGroupCode(String fldGroupCode) {
		this.fldGroupCode = fldGroupCode;
	}
	public String getFldBrandCode() {
		return fldBrandCode;
	}
	public void setFldBrandCode(String fldBrandCode) {
		this.fldBrandCode = fldBrandCode;
	}
	public String getFldFullName() {
		return fldFullName;
	}
	public void setFldFullName(String fldFullName) {
		this.fldFullName = fldFullName;
	}
	public String getFldShortName() {
		return fldShortName;
	}
	public void setFldShortName(String fldShortName) {
		this.fldShortName = fldShortName;
	}
	public Integer getFldStatus() {
		return fldStatus;
	}
	public void setFldStatus(Integer fldStatus) {
		this.fldStatus = fldStatus;
	}
	public Integer getFldSettleType() {
		return fldSettleType;
	}
	public void setFldSettleType(Integer fldSettleType) {
		this.fldSettleType = fldSettleType;
	}
	public String getFldIndustryType() {
		return fldIndustryType;
	}
	public void setFldIndustryType(String fldIndustryType) {
		this.fldIndustryType = fldIndustryType;
	}
	public String getFldAddress() {
		return fldAddress;
	}
	public void setFldAddress(String fldAddress) {
		this.fldAddress = fldAddress;
	}
	public String getFldPostcode() {
		return fldPostcode;
	}
	public void setFldPostcode(String fldPostcode) {
		this.fldPostcode = fldPostcode;
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
	public String getFldMobiles() {
		return fldMobiles;
	}
	public void setFldMobiles(String fldMobiles) {
		this.fldMobiles = fldMobiles;
	}
	public String getFldEmails() {
		return fldEmails;
	}
	public void setFldEmails(String fldEmails) {
		this.fldEmails = fldEmails;
	}
	public String getFldFax() {
		return fldFax;
	}
	public void setFldFax(String fldFax) {
		this.fldFax = fldFax;
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
	public List<TblMerchantPos> getPosList() {
		return posList;
	}
	public void setPosList(List<TblMerchantPos> posList) {
		this.posList = posList;
	}


}

