package com.redcard.posp.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public final class CommonUtil {

	private CommonUtil() {}
	
	/**
	 * 左补空格
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	public static String addLeftSpace(String value, int length) {
		if (value == null) {
			value = "";
		}
		if (value.length() > length) {
			return value.substring(value.length() - length, value.length());
		}
		char[] c = new char[length];
		System.arraycopy(value.toCharArray(), 0, c,
				0 + length - value.length(), value.length());
		for (int i = 0; i < length - value.length(); i++) {
			c[i] = ' ';
		}
		return new String(c);
	}

	/**
	 * 右补空格
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	public static String addRightSpace(String value, int length) {
		if (value == null) {
			value = "";
		}
		if (value.length() > length) {
			return value.substring(0, length - 1);
		}
		char[] c = new char[length];
		System.arraycopy(value.toCharArray(), 0, c, 0, value.length());
		for (int i = value.length(); i < c.length; i++) {
			c[i] = ' ';
		}
		return new String(c);
	}
	
	public static String addRightZero(String value, int length) {
		if (value == null) {
			value = "";
		}
		if (value.length() > length) {
			return value.substring(0, length - 1);
		}
		char[] c = new char[length];
		System.arraycopy(value.toCharArray(), 0, c, 0, value.length());
		for (int i = value.length(); i < c.length; i++) {
			c[i] = '0';
		}
		return new String(c);
	}

	/**
	 * 左填0
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	public static String addLeftZero(String s, int length) {
		// StringBuilder sb=new StringBuilder();
		int old = s.length();
		if (length > old) {
			char[] c = new char[length];
			char[] x = s.toCharArray();
			if (x.length > length) {
				throw new IllegalArgumentException(
						"Numeric value is larger than intended length: " + s
								+ " LEN " + length);
			}
			int lim = c.length - x.length;
			for (int i = 0; i < lim; i++) {
				c[i] = '0';
			}
			System.arraycopy(x, 0, c, lim, x.length);
			return new String(c);
		}
		return s.substring(0, length);

	}
	
	/**
	 * 生成len位随机数
	 * @param 	len
	 * @return	String
	 */
	public static String getRandom(int len){
		StringBuffer temp = new StringBuffer("");
		char[] tempChar = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		Random random = new Random();
		int count = 0;
		while(len > count){
			int i = random.nextInt(tempChar.length);
			if(i>=0 && i<tempChar.length){
				temp.append(tempChar[i]);
				count ++;
			}
		}
		return temp.toString();
	}

	/**
	 * 返回一个8个字符的当前时间yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	public static String getHour() {
		return new SimpleDateFormat("yyyyMMddHH").format(new Date());
	}

	public static String getSecond() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}
	
	public static String getFormatDateTime() {
		return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
	}
	
	public static String getCurrDate() {
		SimpleDateFormat datefmt = new SimpleDateFormat("MMdd");
		Calendar cal = Calendar.getInstance();
		return datefmt.format(cal.getTime());
	}
	
	public static String getCurrTime() {
		SimpleDateFormat datefmt = new SimpleDateFormat("HHmmss");
		Calendar cal = Calendar.getInstance();
		return datefmt.format(cal.getTime());
	}
	
	public static String getCurrDateTime() {
		SimpleDateFormat datefmt = new SimpleDateFormat("YYMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		return datefmt.format(cal.getTime());
	}

	/**
	 * 返回一个8个字符的时间yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String getDate(Date date) {
		return new SimpleDateFormat("yyyyMMdd").format(date);
	}

	/**
	 * 返回一个14个字符的当前时间yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getDateTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static String getDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	/**
	 * 返回一个14个字符的时间yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTime(Date date) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(date);
	}
	
	/**
	 * 时间字符串到时间的转化
	 * @param date
	 * @param formate
	 * @return
	 */
	public static Date stringToDate(String date, String formate) {
		Date d = null;
		try {
			DateFormat format = new SimpleDateFormat(formate);
			d = format.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	/**
	 * 格式化时间
	 * @param time
	 * @return
	 */
	public static String formateTime(String time) {
		return time.substring(0,2)+"点"+time.substring(2,4)+"分"+time.substring(4,time.length())+"秒";
	}
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String formateDate(String date) {
		return date.substring(0,4)+"年"+date.substring(4,6)+"月"+date.substring(6,date.length())+"日";
	}
	
	public static String b2kb(String count) {
		int c = Integer.valueOf(count);
		c = c/1024;
		return Integer.toString(c);
	}
	
	/**
	 * 银行卡加掩码，显示前后4位，其余*表示
	 * 注意，字符串长度必须是大于8，小于则返回原来字符（）
	 * @param cardNO
	 * @return
	 */
	public static String maskCardNO(String cardNO,int n) {
		if (cardNO == null || cardNO.length()<n*2) {
			return cardNO;
		}
		return cardNO.substring(0,n)+cardNO.substring(n,cardNO.length()-n).replaceAll("\\w", "*")+cardNO.substring(cardNO.length()-n);
	}
	public static void main(String[] a) {
		System.out.println(maskCardNO("1234123412341234",4));
	}
}
