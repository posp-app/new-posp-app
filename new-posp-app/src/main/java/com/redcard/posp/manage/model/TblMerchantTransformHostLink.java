package com.redcard.posp.manage.model;

import java.io.Serializable;





public class TblMerchantTransformHostLink  implements Serializable{
	private static final long serialVersionUID = 2090760724L;
	
	
	//columns START
	/** 变量 fldId . */
	private Integer fldId;
	/** 变量 fldMerchantGroupCode . */
	private String fldMerchantGroupCode;
	/** 变量 fldTransformHostCode . */
	private String fldTransformHostCode;
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
	* TblMerchantTransformHostLink 的构造函数
	*/
	public TblMerchantTransformHostLink() {
	}
	/**
	* TblMerchantTransformHostLink 的构造函数
	*/
	public TblMerchantTransformHostLink(
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
	public String getFldTransformHostCode() {
		return fldTransformHostCode;
	}
	public void setFldTransformHostCode(String fldTransformHostCode) {
		this.fldTransformHostCode = fldTransformHostCode;
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

