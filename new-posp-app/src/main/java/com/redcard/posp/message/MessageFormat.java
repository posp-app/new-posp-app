package com.redcard.posp.message;

import java.io.Serializable;
import java.util.List;

public class MessageFormat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 425906995042317507L;
	
	private String name;
	
	private List<FormatMetadata> head;
	
	private FormatMetadata map;
	
	public List<FormatMetadata> fields;


	public List<FormatMetadata> getHead() {
		return head;
	}


	public void setHead(List<FormatMetadata> head) {
		this.head = head;
	}


	public FormatMetadata getMap() {
		return map;
	}


	public void setMap(FormatMetadata map) {
		this.map = map;
	}


	public List<FormatMetadata> getFields() {
		return fields;
	}


	public void setFields(List<FormatMetadata> fields) {
		this.fields = fields;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

}
