package com.redcard.posp.support;

import java.io.Serializable;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 166054625135150096L;

	private String code;
	
	private String message;
	
	private Object object;
	
	public Result(String code,String message,Object object) {
		this.code = code;
		this.message = message;
		this.object = object;
	}
	
	public Result(ResultCode returnCode) {
		this.code = returnCode.getCode();
		this.message = returnCode.getMessage();
	}
	
	public void setResultCode(ResultCode returnCode) {
		this.code = returnCode.getCode();
		this.message = returnCode.getMessage();
	}
	
	public void setValue(String code,String message,Object object){
		this.code = code;
		this.message = message;
		this.object = object;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
