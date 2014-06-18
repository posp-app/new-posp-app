package com.redcard.posp.manage.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;





public class TblMerchantGroup implements Serializable{
	private static final long serialVersionUID = 2062850653L;
	
	
	//columns START
	/** 变量 fldCode . */
	private String fldCode;
	/** 变量 fldFullName . */
	private String fldFullName;
	/** 变量 fldShortName . */
	private String fldShortName;
	/** 变量 fldAddress . */
	private String fldAddress;
	/** 变量 fldLinkMan . */
	private String fldLinkMan;
	/** 变量 fldLinkManPhone . */
	private String fldLinkManPhone;
	/** 变量 fldMobiles . */
	private String fldMobiles;
	/** 变量 fldEmails . */
	private String fldEmails;
	/** 变量 fldStatus . */
	private Integer fldStatus;
	/** 变量 fldSystem . */
	private Integer fldSystem;
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
	 * 存储集团支持的交易类型
	 */
	private List<TblMerchantGroupTradeType> tradeType = new ArrayList<TblMerchantGroupTradeType>();
	
	/**
	 * 存储属于集团的所有商户
	 */
	private List<TblMerchant> merchant = new ArrayList<TblMerchant>();;
	
	private List<TblMerchantTransformHostLink> host = new ArrayList<TblMerchantTransformHostLink>();;

	/**
	* TblMerchantGroup 的构造函数
	*/
	public TblMerchantGroup() {
	}
	/**
	* TblMerchantGroup 的构造函数
	*/
	public TblMerchantGroup(
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
	public String getFldAddress() {
		return fldAddress;
	}
	public void setFldAddress(String fldAddress) {
		this.fldAddress = fldAddress;
	}
	public String getFldLinkMan() {
		return fldLinkMan;
	}
	public void setFldLinkMan(String fldLinkMan) {
		this.fldLinkMan = fldLinkMan;
	}
	public String getFldLinkManPhone() {
		return fldLinkManPhone;
	}
	public void setFldLinkManPhone(String fldLinkManPhone) {
		this.fldLinkManPhone = fldLinkManPhone;
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
	public List<TblMerchantGroupTradeType> getTradeType() {
		return tradeType;
	}
	public void setTradeType(List<TblMerchantGroupTradeType> tradeType) {
		this.tradeType = tradeType;
	}
	public List<TblMerchant> getMerchant() {
		return merchant;
	}
	public void setMerchant(List<TblMerchant> merchant) {
		this.merchant = merchant;
	}
	public List<TblMerchantTransformHostLink> getHost() {
		return host;
	}
	public void setHost(List<TblMerchantTransformHostLink> host) {
		this.host = host;
	}


}

