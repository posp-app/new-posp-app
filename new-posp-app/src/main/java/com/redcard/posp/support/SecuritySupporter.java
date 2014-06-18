package com.redcard.posp.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.common.DecodeUtil;
import com.redcard.posp.common.MacUtil;
import com.redcard.posp.common.TypeConvert;

public class SecuritySupporter {
	private static Logger logger = LoggerFactory.getLogger(SecuritySupporter.class);

	/**
	 * pinKey 交换
	 * @param orgPin
	 * @param orgPinKey
	 * @param orgMasterKey
	 * @param targetPinKey
	 * @param targetMasterKey
	 * @return
	 */
	public static String transferredPin(String orgPin,String orgPinKey,String orgMasterKey,
			String targetPinKey,String targetMasterKey) {
		String orgWorkPin = MacUtil.DES_3(orgPinKey.substring(0,16), orgMasterKey, 1)
				+MacUtil.DES_3(orgPinKey.substring(16), orgMasterKey, 1);
		String pinBlock = MacUtil.DES_3(orgPin, orgWorkPin, 1);
		String targetWorkPin = MacUtil.DES_3(targetPinKey.substring(0,16), targetMasterKey, 1)
				+MacUtil.DES_3(targetPinKey.substring(16), targetMasterKey, 1);
		String result = MacUtil.DES_3(pinBlock, targetWorkPin, 0);
		logger.debug("POS上送PIN=["+orgPin+"]");
		logger.debug("POS的PinKey=["+orgPinKey+"]");
		logger.debug("POS的masterKey=["+orgMasterKey+"]");
		logger.debug("POS的PIN工作密钥=["+orgWorkPin+"]");
		logger.debug("POS上送的P-BLOCK=["+pinBlock+"]");
		logger.debug("上联的PinKey=["+targetPinKey+"]");
		logger.debug("上联的masterKey=["+targetMasterKey+"]");
		logger.debug("上联的PIN工作密钥=["+targetWorkPin+"]");
		logger.debug("最终的PIN=["+result+"]");
		return result;
		//return "86FCA77DD074C987";
	}
	
	/**
	 * MAC 交换
	 * @param mab
	 * @param orgMacKey
	 * @param orgMasterKey
	 * @param targetMacKey
	 * @param targetMasterKey
	 * @return
	 */
	public static String transferredMac(byte[] mab,String targetMacKey,String targetMasterKey) {
		
		String targetWorkMac = MacUtil.DES_3(targetMacKey.substring(0,16), targetMasterKey, 1)
				+MacUtil.DES_3(targetMacKey.substring(16), targetMasterKey, 1);
		String mabString = TypeConvert.bytes2HexString(mab);
		logger.debug("上联MacKey="+targetMacKey);
		logger.debug("上联MasterKey="+targetMasterKey);
		logger.debug("MAB="+mabString);
		logger.debug("上联工作密钥="+targetWorkMac);
		String mac = MacUtil.Mac_919(targetWorkMac, null, mabString);
		logger.debug("最终MAC="+mac);
		return mac;
	}
	
	/**
	 * Track 交换
	 * @param orgTrack
	 * @param orgMasterKey
	 * @param targetMasterKey
	 * @return
	 */
	public static String transferredTrack(String orgTrack,String orgEncryptKey,String orgMasterKey,
			String targetEncryptKey,String targetMasterKey){
		String orgWorkTrack = MacUtil.DES_3(orgEncryptKey.substring(0,16), orgMasterKey, 1)
				+MacUtil.DES_3(orgEncryptKey.substring(16), orgMasterKey, 1);
		String targetWorkTrack = MacUtil.DES_3(targetEncryptKey.substring(0,16), targetMasterKey, 1)
				+MacUtil.DES_3(targetEncryptKey.substring(16), targetMasterKey, 1);
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		String temp = orgTrack;
		int i = 0;
		while (orgTrack.length()>=(i+1)*16) {
			//System.out.println("temp="+temp);
			temp = orgTrack.substring(i*16,(i+1)*16);
			String track = MacUtil.DES_3(temp, orgWorkTrack, 1);
			sb2.append(track);
			String targetTrack = MacUtil.DES_3(track, targetWorkTrack, 0);
			sb.append(targetTrack);
			//temp = orgTrack.substring((i+1)*16);
			i++;
		}
		//sb.append(MacUtil.DES_3("0000000000000000", targetWorkTrack, 0));
		//sb.append(MacUtil.DES_3("0000000000000000", targetWorkTrack, 0));
		logger.debug("磁道工作密钥："+targetWorkTrack);
		logger.debug("磁道信息："+sb2.toString());
		logger.debug("磁道密文："+sb.toString());
		String result = sb.toString();
		return TypeConvert.bytes2HexString(DecodeUtil.str2Bcd(Integer.toString(result.length()/2)))+result;
	}
}
