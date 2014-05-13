package com.redcard.posp.support;

public class ApplicationContent {
	
	
	public static final String MESSAGE_HANDLER_NAME_LOGGER = "MESSAGE_HANDLER_NAME_LOGGER";
	public static final String MESSAGE_HANDLER_NAME_VALIDITY = "MESSAGE_HANDLER_NAME_VALIDITY";
	public static final String MESSAGE_HANDLER_NAME_REPLACE = "MESSAGE_HANDLER_NAME_REPLACE";
	
	public static final String HANDLER_POSP_IN_BOUND = "HANDLER_POSP_IN_BOUND";
	public static final String HANDLER_POSP_OUT_BOUND = "HANDLER_POSP_OUT_BOUND";

	public static final int MESSAGE_IO_I = 1;
	public static final int MESSAGE_IO_O = 2;
	
	public static final String MSG_TYPE_PRE_AUTHORIZATION_REQ = "0100";
	
	public static final String MSG_TYPE_PRE_AUTHORIZATION_RESP = "0110";
	
	public static final String MSG_TYPE_FINANCE_REQ = "0200";

	public static final String MSG_TYPE_FINANCE_RESP = "0210";

	public static final String MSG_TYPE_FINANCE_NOTICE_REQ = "0220";

	public static final String MSG_TYPE_FINANCE_NOTICE_RESP = "0230";

	public static final String MSG_TYPE_SIGN_IN_REQ = "0800";

	public static final String MSG_TYPE_SIGN_IN_RESP = "0810";

	public static final String MSG_TYPE_SIGN_OUT_REQ = "0820";

	public static final String MSG_TYPE_SIGN_OUT_RESP = "0830";

	public static final String MSG_TYPE_FLUSHES_REQ = "0400";

	public static final String MSG_TYPE_FLUSHES_RESP = "0410";
	
	
	public static final String MODEL_TRANSACTION_MESSAGE = "TM_";
	
	public static final int STATUS_USING = 0;
	public static final int STATUS_STOPED = 1;
	
	public static final boolean TRAN_LOCK_UP = true;
	public static final boolean TRAN_LOCK_DOWN = false;
}
