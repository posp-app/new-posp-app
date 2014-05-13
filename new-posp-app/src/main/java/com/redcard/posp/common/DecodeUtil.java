package com.redcard.posp.common;


/**
 * @功能: ASC与BCD码的转化
 * @生产者: 史建敏
 * @生产时间: 2006-3-23
 */
public class DecodeUtil {

	/**
	 * @函数功能: 10进制串转为BCD码
	 * @输入参数: 10进制串
	 * @输出结果: BCD码
	 */
	public static byte[] str2Bcd(String asc) {

		// 原数据的长度
		int len = asc.length();
		int mod = len % 2;

		/*
		 * if (mod != 0) { asc = asc + "0"; len = asc.length(); }
		 */

		if (mod != 0) {
			asc = "0" + asc;
			len = asc.length();
		}

		// 原数据
		byte bOriginalData[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		// 将字符串数据转换成字节数据
		bOriginalData = asc.getBytes();

		// 转换后的BCD码
		byte bBCD[] = new byte[len];

		int sH, sL;

		for (int p = 0; p < asc.length() / 2; p++) {

			if ((bOriginalData[2 * p] >= 'a') && (bOriginalData[2 * p] <= 'f')) {
				sH = bOriginalData[2 * p] - 'a' + 10;
			} else if ((bOriginalData[2 * p] >= 'A')
					&& (bOriginalData[2 * p] <= 'F')) {
				sH = bOriginalData[2 * p] - 'A' + 10;
			} else {
				sH = bOriginalData[2 * p] & 0x0f;
			}

			if ((bOriginalData[2 * p + 1] >= 'a')
					&& (bOriginalData[2 * p + 1] <= 'f')) {
				sL = bOriginalData[2 * p + 1] - 'a' + 10;
			} else if ((bOriginalData[2 * p + 1] >= 'A')
					&& (bOriginalData[2 * p + 1] <= 'F')) {
				sL = bOriginalData[2 * p + 1] - 'A' + 10;
			} else {
				sL = bOriginalData[2 * p + 1] & 0x0f;
			}

			bBCD[p] = (byte) ((sH << 4) + sL);
		}
		return bBCD;
	}
	
	/**
	 * 10进制串转为BCD码
	 * @param asc 10进制串
	 * @param zeroType  补0类型true右补0
	 * @return BCD码
	 */
	public static byte[] str2BcdRightZero(String asc,boolean zeroType) {
		// 原数据的长度
		int len = asc.length();
		int mod = len % 2;
		
		if (zeroType) {
			if (mod != 0) { 
				asc = asc + "0"; len = asc.length(); 
			}
		} else {
			if (mod != 0) {
				asc = "0" + asc;
				len = asc.length();
			}
		}
		// 原数据
		byte bOriginalData[] = new byte[len];
		if (len >= 2) {
			len = len / 2;
		}

		// 将字符串数据转换成字节数据
		bOriginalData = asc.getBytes();

		// 转换后的BCD码
		byte bBCD[] = new byte[len];

		int sH, sL;

		for (int p = 0; p < asc.length() / 2; p++) {

			if ((bOriginalData[2 * p] >= 'a') && (bOriginalData[2 * p] <= 'f')) {
				sH = bOriginalData[2 * p] - 'a' + 10;
			} else if ((bOriginalData[2 * p] >= 'A')
					&& (bOriginalData[2 * p] <= 'F')) {
				sH = bOriginalData[2 * p] - 'A' + 10;
			} else {
				sH = bOriginalData[2 * p] & 0x0f;
			}

			if ((bOriginalData[2 * p + 1] >= 'a')
					&& (bOriginalData[2 * p + 1] <= 'f')) {
				sL = bOriginalData[2 * p + 1] - 'a' + 10;
			} else if ((bOriginalData[2 * p + 1] >= 'A')
					&& (bOriginalData[2 * p + 1] <= 'F')) {
				sL = bOriginalData[2 * p + 1] - 'A' + 10;
			} else {
				sL = bOriginalData[2 * p + 1] & 0x0f;
			}

			bBCD[p] = (byte) ((sH << 4) + sL);
		}
		return bBCD;
	}

	/**
	 * @函数功能: BCD码串转化为字符串
	 * @输入参数: BCD码
	 * @输出结果: 10进制串
	 */
	public static String bcd2Str(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}
	
	public static String getAsc(String asc) {
		StringBuilder sb = new StringBuilder();
		for (char c:asc.toCharArray()){
			sb.append((int)c);
		}
		return sb.toString();
	}
	public static String asc2String(String asc) {
		StringBuilder sb = new StringBuilder();
		for (char c:asc.toCharArray()){
			sb.append(c);
		}
		return sb.toString();
	} 
	
	/*public static byte[] getPINDate(String account,String password) {
		//byte[] a = MessageConfig.getInstance().getPinKey().getBytes();
		byte[] asc = SecurityUtil.genX98Pin(account, MessageConfig.getInstance().getMastKey(), DecodeUtil.bcd2Str(CacheUtil.getInstance().getPinKey()), password);
		
		return asc;
	}*/
	
	public static int getBCDLengthFromASC(int ascLength) {
		if (ascLength==0) {
			return 0;
		}
		if (ascLength%2!=0) {
			return ascLength/2+1;
		} else {
			return ascLength/2;
		}
	}

}
