package com.redcard.posp.manage.model;

import java.io.Serializable;





public class TblTransformCard  implements Serializable{
	private static final long serialVersionUID = 2119388044L;
	
	
	//columns START
	/** 变量 fldId . */
	private Integer fldId;
	/** 变量 fldHostCode . */
	private String fldHostCode;
	/** 变量 fldIssuer . */
	private String fldIssuer;
	/** 变量 fldCardRule . */
	private String fldCardRule;
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
	* TblTransformCard 的构造函数
	*/
	public TblTransformCard() {
	}
	/**
	* TblTransformCard 的构造函数
	*/
	public TblTransformCard(
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
	public String getFldHostCode() {
		return fldHostCode;
	}
	public void setFldHostCode(String fldHostCode) {
		this.fldHostCode = fldHostCode;
	}
	public String getFldIssuer() {
		return fldIssuer;
	}
	public void setFldIssuer(String fldIssuer) {
		this.fldIssuer = fldIssuer;
	}
	public String getFldCardRule() {
		return fldCardRule;
	}
	public void setFldCardRule(String fldCardRule) {
		this.fldCardRule = fldCardRule;
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

