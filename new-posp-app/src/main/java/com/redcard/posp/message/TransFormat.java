package com.redcard.posp.message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransFormat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1487931950574982638L;
	
	private String name;
	
	private String type;
	
	private String process;
	
	private List<FormatMetadata> fields = new ArrayList<FormatMetadata>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FormatMetadata> getFields() {
		return fields;
	}

	public void setFields(List<FormatMetadata> fields) {
		this.fields = fields;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

}
