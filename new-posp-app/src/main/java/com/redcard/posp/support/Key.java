package com.redcard.posp.support;

import java.io.Serializable;

public class Key implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9133702815418990945L;
	public static final String KEY_TYPE_ENCRYPT = "03";
	public static final String KEY_TYPE_PIN = "02";
	public static final String KEY_TYPE_MAC = "01";
	public static final String KEY_TYPE_TERMINAL_MASTER = "00";
	public static final String KEY_TAG_96 = "96";
	public static final String KEY_TAG_97 = "97";
	public static final String KEY_TAG_98 = "98";
	public static final String KEY_TAG_99 = "99";
	private String tag;
	private int subFieldLength;
	private int keyIndex;
	private String key;
	private String keyType;
	private String keyCheckValue;
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getSubFieldLength() {
		return subFieldLength;
	}
	public void setSubFieldLength(int subFieldLength) {
		this.subFieldLength = subFieldLength;
	}
	public int getKeyIndex() {
		return keyIndex;
	}
	public void setKeyIndex(int keyIndex) {
		this.keyIndex = keyIndex;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKeyType() {
		return keyType;
	}
	public void setKeyType(String keyType) {
		this.keyType = keyType;
	}
	public String getKeyCheckValue() {
		return keyCheckValue;
	}
	public void setKeyCheckValue(String keyCheckValue) {
		this.keyCheckValue = keyCheckValue;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.tag);
		sb.append(this.subFieldLength);
		sb.append(this.keyIndex);
		sb.append(this.key);
		sb.append(this.keyType);
		sb.append(this.keyCheckValue);
		return sb.toString();
	}

}
