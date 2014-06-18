package com.redcard.posp.message;

import java.io.Serializable;

public class FormatMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7367417113045962116L;
	private String name;
	
	private int length;
	
	private String use;
	
	private int number;
			
	private String format;
	
	private String llFormat;
	
	private String varFormat;
	
	private String charset;
	
	private String direction;
	
	private String sourceType;
	
	private String request;
	
	private String defaultValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLlFormat() {
		return llFormat;
	}

	public void setLlFormat(String llFormat) {
		this.llFormat = llFormat;
	}

	public String getVarFormat() {
		return varFormat;
	}

	public void setVarFormat(String varFormat) {
		this.varFormat = varFormat;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
