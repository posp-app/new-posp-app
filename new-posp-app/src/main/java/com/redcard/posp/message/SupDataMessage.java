package com.redcard.posp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.common.SecurityUtil;
import com.redcard.posp.common.TypeConvert;

public class SupDataMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7295199751452937025L;
	private static Logger log = LoggerFactory.getLogger(SupDataMessage.class);

	
	public SupDataMessage(byte[] buf, MessageFormat format) throws Exception {
		this.buf = buf;
		this.format = format;
		int fieldCount = this.format.getFields().size();
		field = new byte[fieldCount][];
		bitMap = new byte[fieldCount/8];
		int startPostion = 0;
		position = startPostion;
		analyzeMessage();
	}

	private void analyzeMessage() throws Exception{
		int i = 0;
		for (FormatMetadata fm:format.getFields()) {
			field[i] = new byte[fm.getLength()];
			System.arraycopy(buf, position, field[i], 0, field[i].length);
			log.debug("field[" + (i + 1) + "]="
					+ TypeConvert.bytes2HexString(field[i]));
			position = position+field[i].length;
			SecurityUtil.setBinaryOne(bitMap, i+1);
			i++;
		}
	}
	
	public String getTrack2(){
		return getFieldValue(4);
	}
	
	public String getTrack3() {
		return getFieldValue(5);
	}
	
	public String getTransactionMoney(){
		return getFieldValue(6);
	}
	
	public String getBalanceAmount(){
		return getFieldValue(7);
	}
	
	public String getSystemSequence(){
		return getFieldValue(8);
	}
	public String getOriginalSystemSequence(){
		return getFieldValue(9);
	}
	
	public String getRetrievalReferenceNumber() {
		return getFieldValue(10);
	}
	
	public String getAuthorizationResponseCode() {
		return getFieldValue(11);
	}
	public String getResponseCode() {
		return getFieldValue(12);
	}
	public String getResponseDesc() {
		return getFieldValue(13);
	}
	public String getTerminalIdentification() {
		return getFieldValue(14);
	}
	public String getCardAcceptorIdentification() {
		return getFieldValue(15);
	}
	public String getBatchNumber() {
		return getFieldValue(16);
	}
	public String getPID() {
		return getFieldValue(17);
	}
	public String getTerminalNumber() {
		return getFieldValue(18);
	}
	public String getOperateNo() {
		return getFieldValue(19);
	}
	public String getOperatePassword() {
		return getFieldValue(20);
	}
	public String getOperateNewPassword() {
		return getFieldValue(21);
	}
	public String getCardBank() {
		return getFieldValue(22);
	}
	public String getCardBankName() {
		return getFieldValue(23);
	}
	public String getBatSettle() {
		return getFieldValue(24);
	}
	public String getTranCount() {
		return getFieldValue(25);
	}
	public String getTranAmount() {
		return getFieldValue(26);
	}
	public String getOriginalTransactionDate() {
		return getFieldValue(27);
	}
	public String getOriginalMessageType() {
		return getFieldValue(28);
	}
	public String getSystemDate() {
		return getFieldValue(29);
	}
	public String getSystemTime() {
		return getFieldValue(30);
	}
	public String getDateOfExpired() {
		return getFieldValue(31);
	}
	public String getAdditionalData() {
		return getFieldValue(32);
	}
}
