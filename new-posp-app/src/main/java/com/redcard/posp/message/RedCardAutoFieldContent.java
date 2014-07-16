package com.redcard.posp.message;

import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.handler.secret.redcard.SecretKeyFactory;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.support.IDBuilder;

public class RedCardAutoFieldContent implements IAutoContent {

	public String getField(int number,String terminalNo) throws Exception{
		if (11 == number) {
			return IDBuilder.getInstance().getSystemSequence();
		}
		if (12 == number) {
			//本地交易时间
			return CommonUtil.getCurrTime();
		}
		if (13 == number) {
			//本地交易日期
			return CommonUtil.getDate();
		}
		if (15 == number) {
			//清分日期
			return CommonUtil.getCurrDate();
		}
		if (37 == number) {
			//参考号，想用时间代替着。
			return CommonUtil.getCurrDateTime();
		}
		if (60 ==number) {
			TblMerchantPos pos = ManageCacheService.findPos(terminalNo);
			return SecretKeyFactory.createMacKey(pos.getFldMasterKey());
		}
		if (61 == number) {
			TblMerchantPos pos = ManageCacheService.findPos(terminalNo);
			return SecretKeyFactory.createPinKey(pos.getFldMasterKey());
		}
		if (62 == number) {
			//当前的批次号+1
			TblMerchantPos pos = ManageCacheService.findPos(terminalNo);
			String currentBatchNo = pos.getFldBatchNo();
			int iCurrentBatchNo = Integer.parseInt(currentBatchNo)+1;
			if (iCurrentBatchNo>999999) {
				iCurrentBatchNo = 1;
			}
			String nextBatchNo = CommonUtil.addLeftZero(Integer.toString(iCurrentBatchNo), 6);
			/*String result = CommonUtil.addLeftZero(Integer.toString(nextBatchNo.length()), 4)+
					nextBatchNo;*/
			return nextBatchNo;
		}
		if (63 == number) {
			return SecretKeyFactory.createDownload63FieldValue();
		}
		throw new Exception("该域不能自动生成");
	}/*
	
	public static Key createKey(String keyType) {
		Key k = new Key();
		k.setKeyType(keyType);
		if (Key.KEY_TYPE_ENCRYPT.equals(keyType)) {
			k.setKeyIndex(1);
			k.setTag(Key.KEY_TAG_97);
		} else if (Key.KEY_TYPE_MAC.equals(keyType)){
			k.setKeyIndex(1);
			k.setTag(Key.KEY_TAG_99);
		}else if (Key.KEY_TYPE_PIN.equals(keyType)){
			k.setKeyIndex(1);
			k.setTag(Key.KEY_TAG_98);
		}else if (Key.KEY_TYPE_TERMINAL_MASTER.equals(keyType)){
			k.setKeyIndex(0);
			k.setTag(Key.KEY_TAG_96);
		}
		if (ApplicationContextInit.secretLength ==32) {
			k.setSubFieldLength(51);
		} else if (ApplicationContextInit.secretLength ==16) {
			k.setSubFieldLength(35);
		}
		//主密钥1和主密钥2异或得到主密钥
		byte[] masterKeyOne = TypeConvert.hexStringToByte(ApplicationContextInit.masterKeyOne);
		byte[] masterKeyTwo = TypeConvert.hexStringToByte(ApplicationContextInit.masterKeyTwo);
		int length = masterKeyOne.length>masterKeyTwo.length?masterKeyOne.length:masterKeyTwo.length;
		//byte[] masterKey = SecurityUtil.doXor(masterKeyOne, masterKeyTwo, length);
		String masterKey = ApplicationContextInit.terminalMasterKey;
		// 随机生成密钥明文
		String secretKey = CommonUtil.getRandom(ApplicationContextInit.secretLength);
		String target = MacUtil.DES_3(secretKey.substring(0,16), masterKey, 0)+MacUtil.DES_3(secretKey.substring(16), masterKey, 0);
		k.setKey(target);
		String checkValueStr = ApplicationContextInit.checkValueKey;
		String checkValueKey = MacUtil.DES_3(checkValueStr,secretKey,  0);
		k.setKeyCheckValue(checkValueKey.substring(0,16));
		System.out.println("masterKey="+masterKey);
		System.out.println("tag="+k.getTag());
		System.out.println("subFieldLength="+k.getSubFieldLength());
		System.out.println("keyIndex="+k.getKeyIndex());
		System.out.println("key="+k.getKey());
		System.out.println("keyType="+k.getKeyType());

		System.out.println("secretKey="+secretKey);
		System.out.println("keyCheckValue="+k.getKeyCheckValue());
		System.out.println("-----------------------------------------");
		
		return k;
	}*/

}
