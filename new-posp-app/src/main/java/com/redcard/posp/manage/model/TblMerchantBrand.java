package com.redcard.posp.manage.model;

import java.io.Serializable;





public class TblMerchantBrand  implements Serializable{
	private static final long serialVersionUID = 2041817027L;
	
	
	//columns START
	/** 变量 fldBrandCode . */
	private String fldBrandCode;
	/** 变量 fldFullName . */
	private String fldFullName;
	/** 变量 fldType . */
	private String fldType;
	/** 变量 fldShortName . */
	private String fldShortName;
	/** 变量 fldStatus . */
	private Integer fldStatus;
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
	* TblMerchantBrand 的构造函数
	*/
	public TblMerchantBrand() {
	}
	/**
	* TblMerchantBrand 的构造函数
	*/
	public TblMerchantBrand(
		String fldBrandCode
	) {
		this.fldBrandCode = fldBrandCode;
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
	public String getFldType() {
		return fldType;
	}
	public void setFldType(String fldType) {
		this.fldType = fldType;
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


}

