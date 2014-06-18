package com.redcard.posp.support;

import java.io.Serializable;

public class OutMessageConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6991702178414835774L;
	
	private String name;
	
	private String className;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
