package com.redcard.posp.support;

public class ApplicationContent {
	
	
	public static final String MESSAGE_HANDLER_NAME_LOGGER = "MESSAGE_HANDLER_NAME_LOGGER";
	public static final String MESSAGE_HANDLER_NAME_VALIDITY = "MESSAGE_HANDLER_NAME_VALIDITY";
    public static final String MESSAGE_HANDLER_NAME_VALIDITY_SIGN = "MESSAGE_HANDLER_NAME_VALIDITY_SIGN";
    public static final String MESSAGE_HANDLER_NAME_REPLACE = "MESSAGE_HANDLER_NAME_REPLACE";
	
	public static final String HANDLER_POSP_IN_BOUND = "HANDLER_POSP_IN_BOUND";
	public static final String HANDLER_POSP_OUT_BOUND = "HANDLER_POSP_OUT_BOUND";

	public static final int MESSAGE_IO_I = 1;
	public static final int MESSAGE_IO_O = 2;
	
	public static final String MSG_TYPE_SIGN_ON_REQ = "0800";
	
	public static final String MSG_TYPE_SIGN_ON_RESP = "0810";
	
	public static final String MSG_TYPE_BALANCE_REQ = "0150";

	public static final String MSG_TYPE_BALANCE_RESP = "0160";

	public static final String MSG_TYPE_SALE_REQ = "0200";

	public static final String MSG_TYPE_SALE_RESP = "0210";

	public static final String MSG_TYPE_VOID_REQ = "0400";

	public static final String MSG_TYPE_VOID_RESP = "0410";

	public static final String MSG_TYPE_SETTLEMENT_REQ = "0500";

	public static final String MSG_TYPE_SETTLEMENT_RESP = "0510";

	public static final String MSG_TYPE_REVERSAL_REQ = "0420";

	public static final String MSG_TYPE_REVERSAL_RESP = "0430";
	
	public static final String MSG_PROCESS_CODE_910000 = "910000";
	public static final String MSG_PROCESS_CODE_900000 = "900000";
	public static final String MSG_PROCESS_CODE_310000 = "310000";
	public static final String MSG_PROCESS_CODE_000000 = "000000";
	public static final String MSG_PROCESS_CODE_920000 = "920000";
	
	public static final String SYSTEM_TRANSACTION_TYPE_00 = "00";
	public static final String SYSTEM_TRANSACTION_TYPE_10 = "10";
	public static final String SYSTEM_TRANSACTION_TYPE_11 = "11";
	public static final String SYSTEM_TRANSACTION_TYPE_20 = "20";
	public static final String SYSTEM_TRANSACTION_TYPE_30 = "30";
	public static final String SYSTEM_TRANSACTION_TYPE_40 = "40";
	public static final String SYSTEM_TRANSACTION_TYPE_41 = "41";
	public static final String SYSTEM_TRANSACTION_TYPE_50 = "50";
	public static final String SYSTEM_TRANSACTION_TYPE_51 = "51";
	public static final String SYSTEM_TRANSACTION_TYPE_90 = "90";
	public static final String SYSTEM_TRANSACTION_TYPE_91 = "91";
	public static final String SYSTEM_TRANSACTION_TYPE_92 = "92";
	
	public static final String MSG_TYPE_SUP_DATA_SALE = "01";
	public static final String MSG_TYPE_SUP_DATA_REVERSAL = "41";
	public static final String MSG_TYPE_SUP_DATA_BALANCE = "11";
	public static final String MSG_TYPE_SUP_DATA_VOID = "31";
	
	
	public static final String MODEL_TRANSACTION_MESSAGE = "TM_";
	
	public static final int STATUS_USING = 0;
	public static final int STATUS_STOPED = 1;
	
	public static final boolean TRAN_LOCK_UP = true;
	public static final boolean TRAN_LOCK_DOWN = false;
	
	public static final int INDICATE_SUCCESS = 1;
	public static final int INDICATE_FAIL = 0;
	
	public static final String INDICATE_REVOKE = "INDICATE_REVOKE";
	public static final String INDICATE_REVERSAL = "INDICATE_REVERSAL";
	public static final String INDICATE_CANCEL = "INDICATE_CANCEL";
}
