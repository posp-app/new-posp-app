package com.redcard.posp.handler.secret.redcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.common.MacUtil;
import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.support.ApplicationContextInit;
import com.redcard.posp.support.SecuritySupporter;

public class SecretKeyFactory {
	private static Logger logger = LoggerFactory.getLogger(SecretKeyFactory.class);

	private static String masterKey = "D9F1E43AC27F6B5C";
	private static final String CHECK_VALUE_KEY = "0000000000000000";
	private static int secretLength16 = 16;
	private static int secretLength32 = 32;
	
	public static String getDesWorkKey(String key,String masterKey) {
		String masterWorkKey = MacUtil.DES_1(masterKey, SecretKeyFactory.masterKey, 1);
		return MacUtil.DES_1(key, masterWorkKey, 1);
	}
	
	public static String getPin(String pinKey,String masterKey,String pinData,String account) {
		String pinWorkKey = getDesWorkKey(pinKey,masterKey);
		String pinBlock = MacUtil.DES_1(pinData, pinWorkKey, 1);
		//logger.debug("POS上送的P-BLOCK=["+pinBlock+"]");
		String pan = account.substring(account.length()-14,account.length()-2);
		//logger.debug("POS上送的PAN=["+pan+"]");
		String pin = MacUtil.xOrString("0000"+pan,pinBlock).substring(2,8);
		//logger.debug("POS上送的PIN=["+pin+"]");
		byte[] ex = new byte[2];
		return TypeConvert.bytes2HexString(pin.getBytes())+TypeConvert.bytes2HexString(ex);
	}
	
	public static String transferredPin(String pinKey,String masterKey,
			String workPinKey,String pinData) {
		String pinWorkKey = getDesWorkKey(pinKey,masterKey);
		String pinBlock = MacUtil.DES_1(pinData, pinWorkKey, 1);
		//logger.debug("POS上送的P-BLOCK=["+pinBlock+"]");
		String result = MacUtil.DES_1(pinBlock, workPinKey, 0);
		System.out.println("target-pin="+result);
		return result;
	}
	
	public static String createDownloadKey(){
		//随机生成密钥明文
		StringBuilder sb = new StringBuilder();
		String secretKey = CommonUtil.getRandom(secretLength16);
		String target = MacUtil.DES_1(secretKey, masterKey, 0);
		String checkValueKey = MacUtil.DES_1(CHECK_VALUE_KEY, secretKey, 0); 
		sb.append(target+checkValueKey.substring(0,8));
//		secretKey = CommonUtil.getRandom(secretLength32);
		secretKey = ApplicationContextInit.keyCPU;//"25B0E912424D0F1C7997907D1BC116E8";
		target = MacUtil.DES_1(secretKey.substring(0,16), masterKey, 0)+MacUtil.DES_1(secretKey.substring(16), masterKey, 0);
		checkValueKey = MacUtil.DES_3(CHECK_VALUE_KEY,secretKey,  0);
		sb.append(target+checkValueKey.substring(0,8));
		return sb.toString();
	}
	
	public static String createDownload63FieldValue() {
		String value = createDownloadKey();
		String sLength = CommonUtil.addLeftZero(Integer.valueOf(value.length()/2).toString(),4);
		String v = sLength+value;
		return CommonUtil.addRightZero(v, 120);
	}
	
	public static String createMasterKey() {
		//随机生成密钥明文
		StringBuilder sb = new StringBuilder();
		String secretKey = CommonUtil.getRandom(secretLength16);
		String target = MacUtil.DES_1(secretKey, masterKey, 0);
		String checkValueKey = MacUtil.DES_1(CHECK_VALUE_KEY, secretKey, 0);
		sb.append(target + checkValueKey.substring(0, 8));
		return sb.toString();
	}
	
	public static String createCPUKey() {
		//随机生成密钥明文
		StringBuilder sb = new StringBuilder();
		String secretKey = CommonUtil.getRandom(secretLength32);
//		String secretKey = "25B0E912424D0F1C7997907D1BC116E8";
		String target = MacUtil.DES_1(secretKey.substring(0,16), masterKey, 0)+MacUtil.DES_1(secretKey.substring(16), masterKey, 0);
		String checkValueKey = MacUtil.DES_3(CHECK_VALUE_KEY,secretKey,  0);
		sb.append(target+checkValueKey.substring(0,8));
		return sb.toString();
	}
	
	public static String createMacKey(String masterKey) {
		return createKey(masterKey);
	}
	
	public static String createPinKey(String masterKey) {
		return createKey(masterKey);
	}
	
	private static String createKey(String masterKey){
		//随机生成密钥明文
		String secretKey = CommonUtil.getRandom(secretLength16);
		masterKey = MacUtil.DES_1(masterKey, SecretKeyFactory.masterKey, 1);
		String target = MacUtil.DES_1(secretKey, masterKey, 0);
		return target;
	}
}
