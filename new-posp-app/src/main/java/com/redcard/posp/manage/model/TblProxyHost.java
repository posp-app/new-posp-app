package com.redcard.posp.manage.model;

import java.io.Serializable;
import java.util.Date;

import com.redcard.posp.support.ApplicationKey;





public class TblProxyHost  implements Serializable{
	private static final long serialVersionUID = 2094364480L;
	
	
	//columns START
	/** 变量 fldHostCode . */
	private String fldHostCode;
	/** 变量 fldHostName . */
	private String fldHostName;
	/** 变量 fldHostIp . */
	private String fldHostIp;
	/** 变量 fldHostPort . */
	private Integer fldHostPort;
	/** 变量 fldStatus . */
	private Integer fldStatus;
	/** 变量 fldOrgCode . */
	private String fldOrgCode;
	/** 变量 fldPinKey . */
	private String fldPinKey;
	/** 变量 fldMacKey . */
	private String fldMacKey;
	private String fldEncryptKey;
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

    private Date fldSignDate;

    private String fldHostMasterKey;

    private Integer fldSignMode = ApplicationKey.PROXY_SIGN_MODE_NONE;

    //columns END
	
	private String fldProtocolType = ApplicationKey.PROTOCOL_TYPE_SHARE;

	public String getFldProtocolType() {
		return fldProtocolType;
	}
	public void setFldProtocolType(String fldProtocolType) {
		this.fldProtocolType = fldProtocolType;
	}

    public Integer getFldSignMode() {
        return fldSignMode;
    }

    public void setFldSignMode(Integer fldSignMode) {
        this.fldSignMode = fldSignMode;
    }

    /**
	* TblProxyHost 的构造函数
	*/
	public TblProxyHost() {
	}
	/**
	* TblProxyHost 的构造函数
	*/
	public TblProxyHost(
		String fldHostCode
	) {
		this.fldHostCode = fldHostCode;
	}
	public String getFldHostCode() {
		return fldHostCode;
	}
	public void setFldHostCode(String fldHostCode) {
		this.fldHostCode = fldHostCode;
	}
	public String getFldHostName() {
		return fldHostName;
	}
	public void setFldHostName(String fldHostName) {
		this.fldHostName = fldHostName;
	}
	public String getFldHostIp() {
		return fldHostIp;
	}
	public void setFldHostIp(String fldHostIp) {
		this.fldHostIp = fldHostIp;
	}
	public Integer getFldHostPort() {
		return fldHostPort;
	}
	public void setFldHostPort(Integer fldHostPort) {
		this.fldHostPort = fldHostPort;
	}
	public Integer getFldStatus() {
		return fldStatus;
	}
	public void setFldStatus(Integer fldStatus) {
		this.fldStatus = fldStatus;
	}
	public String getFldOrgCode() {
		return fldOrgCode;
	}
	public void setFldOrgCode(String fldOrgCode) {
		this.fldOrgCode = fldOrgCode;
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
	public String getFldEncryptKey() {
		return fldEncryptKey;
	}
	public void setFldEncryptKey(String fldEncryptKey) {
		this.fldEncryptKey = fldEncryptKey;
	}

    public Date getFldSignDate() {
        return fldSignDate;
    }

    public void setFldSignDate(Date fldSignDate) {
        this.fldSignDate = fldSignDate;
    }

    public String getFldHostMasterKey() {
        return fldHostMasterKey;
    }

    public void setFldHostMasterKey(String fldHostMasterKey) {
        this.fldHostMasterKey = fldHostMasterKey;
    }
}

