package com.redcard.posp.manage.model;

import java.io.Serializable;

public class TblCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7662628774703529054L;

	private String fldCardNo;
	private String fldHostCode;

	public String getFldCardNo() {
		return fldCardNo;
	}

	public void setFldCardNo(String fldCardNo) {
		this.fldCardNo = fldCardNo;
	}

	public String getFldHostCode() {
		return fldHostCode;
	}

	public void setFldHostCode(String fldHostCode) {
		this.fldHostCode = fldHostCode;
	}
}
