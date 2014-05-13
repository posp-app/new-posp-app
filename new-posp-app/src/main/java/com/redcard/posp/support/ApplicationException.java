package com.redcard.posp.support;

import org.springframework.util.Assert;

public class ApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8852979806203041741L;

	private String code;
	
	public ApplicationException(final String code) {
		initException(code);
	}
	
	public ApplicationException(final String code, final String msg) {
		super(msg);
		initException(code);
	}

	public ApplicationException(final String code, final Throwable throwable) {
		super(throwable);
		initException(code);
	}
	
	public ApplicationException(ResultCode resultCode) {
		this(resultCode.getCode(),resultCode.getMessage());
	}

	public final String getCode() {
		final Throwable cause = this.getCause();
		if (cause != null && (cause instanceof ApplicationException)) {
			return ((ApplicationException) cause).getCode();
		}
		return this.code;
	}
	
	
	private void initException(final String code) {
		Assert.hasLength(code, "The exception code cannot be blank");
		this.code = code;
	}

	@Override
	public String toString() {
		return this.getCode();
	}

}
