package com.redcard.posp.manage.model;

import java.io.Serializable;





public class TblMerchantGroupTradeType  implements Serializable{
	private static final long serialVersionUID = 2068356995L;
	
	
	//columns START
	/** 变量 fldId . */
	private Integer fldId;
	/** 变量 fldMerchantGroupCode . */
	private String fldMerchantGroupCode;
	/** 变量 fldTradeType . */
	private String fldTradeType;
	/** 变量 fldTradeName . */
	private String fldTradeName;
	/** 变量 fldCreateUserNo . */
	private String fldCreateUserNo;
	/** 变量 fldCreateDate . */
	private java.util.Date fldCreateDate;
	/** 变量 fldOperateUserNo . */
	private String fldOperateUserNo;
	/** 变量 fldOperateDate . */
	private java.util.Date fldOperateDate;
	//columns END

	/**
	* TblMerchantGroupTradeType 的构造函数
	*/
	public TblMerchantGroupTradeType() {
	}
	/**
	* TblMerchantGroupTradeType 的构造函数
	*/
	public TblMerchantGroupTradeType(
		Integer fldId
	) {
		this.fldId = fldId;
	}
	public Integer getFldId() {
		return fldId;
	}
	public void setFldId(Integer fldId) {
		this.fldId = fldId;
	}
	public String getFldMerchantGroupCode() {
		return fldMerchantGroupCode;
	}
	public void setFldMerchantGroupCode(String fldMerchantGroupCode) {
		this.fldMerchantGroupCode = fldMerchantGroupCode;
	}
	public String getFldTradeType() {
		return fldTradeType;
	}
	public void setFldTradeType(String fldTradeType) {
		this.fldTradeType = fldTradeType;
	}
	public String getFldTradeName() {
		return fldTradeName;
	}
	public void setFldTradeName(String fldTradeName) {
		this.fldTradeName = fldTradeName;
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

