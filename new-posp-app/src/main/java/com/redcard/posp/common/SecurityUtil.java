package com.redcard.posp.common;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

import com.redcard.posp.support.ApplicationContextInit;

/**
 * @说明：史建敏提供的加密解密类。原文件Secu.java
 * 此文件仅仅修改了其类名，和包路径。
 * @功能: 数据安全类
 * @生产者: 史建敏
 * @生产时间: 2005-9-7
 */
public class SecurityUtil {
	
	
	private static final String Algorithm_DESede = "DESede";
	private static final String Algorithm_DES = "DES";

	/**
	 * @函数功能: 加密
	 * @输入参数: 密钥
	 * @输入参数: 需要加密的数据
	 * @输出结果: 加密后的数据
	 */
	public static byte[] Encrypt(byte[] skey, byte[] sContents,String transformation) {
		
		try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(skey, Algorithm_DES);

            //加密
            Cipher c1 = Cipher.getInstance(Algorithm_DES);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(sContents);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
		/*byte[] cipherByte = null;

		// 添加新安全算法,如果用JCE就要把它添加进去
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		try {
			// 生成密钥
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(skey);
			Key key = keyFactory.generateSecret(keySpec);

			// 加密
			Cipher c1 = Cipher.getInstance(transformation);
			c1.init(Cipher.ENCRYPT_MODE, key);
			cipherByte = c1.doFinal(sContents);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException:" + e.getMessage());
		} catch (NoSuchPaddingException e) {
			System.out.println("NoSuchPaddingException:" + e.getMessage());
		} catch (InvalidKeyException e) {
			System.out.println("InvalidKeyException:" + e.getMessage());
		} catch (InvalidKeySpecException e) {
			System.out.println("InvalidKeySpecException:" + e.getMessage());
		} catch (IllegalStateException e) {
			System.out.println("IllegalStateException:" + e.getMessage());
		} catch (IllegalBlockSizeException e) {
			System.out.println("IllegalBlockSizeException:" + e.getMessage());
		} catch (BadPaddingException e) {
			System.out.println("BadPaddingException:" + e.getMessage());
		}

		return cipherByte;*/
	}

	/**
	 * @函数功能: 解密
	 * @输入参数: 密钥
	 * @输入参数: 需要解密的数据
	 * @输出结果: 解密后的数据
	 */
	public static byte[] Decrypt(byte[] skey, byte[] cipherByte,String transformation) {
		byte[] clearByte = null;

		// 添加新安全算法,如果用JCE就要把它添加进去
		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		try {
			// 生成密钥
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec keySpec = new DESKeySpec(skey);
			Key key = keyFactory.generateSecret(keySpec);

			// 解密
			Cipher c1 = Cipher.getInstance(transformation);
			c1.init(Cipher.DECRYPT_MODE, key);
			clearByte = c1.doFinal(cipherByte);

			//SecretKey desKey = new SecretKeySpec(skey,"DESede");
            /*String algorithm = "DESede/ECB/NoPadding";  
            SecretKey desKey = new SecretKeySpec(skey, algorithm);  
            Cipher tcipher = Cipher.getInstance(algorithm);  
            tcipher.init(Cipher.DECRYPT_MODE, desKey);  
			Cipher c1 = Cipher.getInstance("DESede/ECB/NoPadding");
			c1.init(Cipher.DECRYPT_MODE, desKey);
			clearByte = c1.doFinal(cipherByte);*/

		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException:" + e.getMessage());
		} catch (NoSuchPaddingException e) {
			System.out.println("NoSuchPaddingException:" + e.getMessage());
		} catch (InvalidKeyException e) {
			System.out.println("InvalidKeyException:" + e.getMessage());
		} catch (InvalidKeySpecException e) {
			System.out.println("InvalidKeySpecException:" + e.getMessage());
		} catch (IllegalStateException e) {
			System.out.println("IllegalStateException:" + e.getMessage());
		} catch (IllegalBlockSizeException e) {
			System.out.println("IllegalBlockSizeException:" + e.getMessage());
		} catch (BadPaddingException e) {
			System.out.println("BadPaddingException:" + e.getMessage());
		}

		return clearByte;
	}

	/**
	 * @函数功能: ASC码串转为BCD码
	 * @输入参数: ASC码串
	 * @输出结果: BCD码
	 */
	public static byte[] asc2Bcd(byte[] asc) {

		// 数据的长度
		int len = asc.length / 2;

		// 转换后的BCD码
		byte bcd[] = new byte[(1 + asc.length) / 2];

		int sH, sL, i;

		for (i = 0; i < len; i++) {
			if ((asc[2 * i] >= 'a') && (asc[2 * i] <= 'f')) {
				sH = asc[2 * i] - 'a' + 10;
			} else if ((asc[2 * i] >= 'A') && (asc[2 * i] <= 'F')) {
				sH = asc[2 * i] - 'A' + 10;
			} else {
				sH = asc[2 * i] & 0x0f;
			}

			if ((asc[2 * i + 1] >= 'a') && (asc[2 * i + 1] <= 'f')) {
				sL = asc[2 * i + 1] - 'a' + 10;
			} else if ((asc[2 * i + 1] >= 'A') && (asc[2 * i + 1] <= 'F')) {
				sL = asc[2 * i + 1] - 'A' + 10;
			} else {
				sL = asc[2 * i + 1] & 0x0f;
			}

			bcd[i] = (byte) ((sH << 4) + sL);
		}
		if (asc.length % 2 != 0) {
			if ((asc[2 * i] >= 'a') && (asc[2 * i] <= 'f')) {
				sH = asc[2 * i] - 'a' + 10;
			} else if ((asc[2 * i] >= 'A') && (asc[2 * i] <= 'F')) {
				sH = asc[2 * i] - 'A' + 10;
			} else {
				sH = asc[2 * i] & 0x0f;
			}
			bcd[i] = (byte) (sH << 4);
		}
		return bcd;
	}

	/**
	 * @函数功能: BCD码串转化为ASC码串
	 * @输入参数: BCD码
	 * @输出结果: ASC码串
	 */
	public static byte[] bcd2Asc(byte[] bcd) {
		byte asc[] = new byte[bcd.length * 2];
		int val;

		for (int i = 0; i < bcd.length; i++) {
			val = ((bcd[i] & 0xf0) >> 4) & 0x0f;
			asc[i * 2] = (byte) (val > 9 ? val + 'A' - 10 : val + '0');

			val = bcd[i] & 0x0f;
			asc[i * 2 + 1] = (byte) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return asc;
	}

	/**
	 * @函数功能: 异或操作
	 * @输入参数: 源串
	 * @输入参数: 长度
	 * @输出结果: 异或的串值
	 */
	public static byte[] doXor(byte[] src1, byte[] src2, int num) {
		for (int i = 0; i < num; i++) {
			src1[i] ^= src2[i];
		}
		return src1;
	}

	/**
	 * @函数功能: 生成X99标准MAC
	 * @输入参数: 主密钥(明文)
	 * @输入参数: MAC密钥(密文)
	 * @输入参数: MAB源串
	 * @输出结果: MAC值
	 */
	public static byte[] genX99MAC(String strMastKey, String strMACKey,
			byte[] byteMab) {
		int size = 0;
		byte byteBuf[] = new byte[8];
		byte byteTmp[] = new byte[8];
		byte[] byteMAC = null;

		byte[] byteKey = strMastKey.getBytes();//asc2Bcd(strMastKey.getBytes());
		byte[] byteEncrypt = asc2Bcd(strMACKey.getBytes());
		byte[] byteWorkKey = Decrypt(byteKey, byteEncrypt,ApplicationContextInit.transformation);

		int i = 0;
		while (byteMab.length > size) {
			if ((byteMab.length - size) <= 8) {
				System.arraycopy(byteMab, size, byteTmp, 0, byteMab.length
						- size);
				doXor(byteBuf, byteTmp, byteMab.length - size);
				byteMAC = Encrypt(byteWorkKey, byteBuf,ApplicationContextInit.transformation);
				//System.out.println("第"+i+"(final)次运算结果[data1="+TypeConvert.bytes2HexString(byteBuf)+";data2="+TypeConvert.bytes2HexString(byteTmp)+"]");
				break;
			}
			System.arraycopy(byteMab, size, byteTmp, 0, 8);
			//System.out.println("byteMab:"+TypeConvert.bytes2HexString(byteMab));
			doXor(byteBuf, byteTmp, 8);
			byteMAC = Encrypt(byteWorkKey, byteBuf,ApplicationContextInit.transformation);

			// System.arraycopy(byteBuf, 0, byteMAC, 0, 8);
			System.arraycopy(byteMAC, 0, byteBuf, 0, 8);
			//System.out.println("第"+i+"次运算结果[data1="+TypeConvert.bytes2HexString(byteBuf)+";data2="+TypeConvert.bytes2HexString(byteTmp)+"]");

			size += 8;
			i++;
		}
		//return byteMAC;
		return bcd2Asc(byteMAC);
	}

	/**
	 * @函数功能: 生成X98标准PIN密码
	 * @输入参数: 卡号(主密钥(明文))
	 * @输入参数: 主密钥(明文)(密码键盘中的那个主密钥)
	 * @输入参数: PIN密钥(密文)(签到时，域62:自定义域（密钥信息）中的PIN密钥)
	 * @输入参数: 明文密码(信用卡密码)
	 * @输出结果: 密文PIN
	 *//*
	public static byte[] genX98Pin(String strCardNo, String strMastKey,
			String strPINKey, String clearPin) {
		try {
			System.out.println("卡号:"+strCardNo);
			System.out.println("主密钥(明文):"+strMastKey);
			System.out.println("PIN密钥(密文):"+strPINKey);
			System.out.println("明文密码:"+clearPin);
			// 密钥
			byte[] byteKey = asc2Bcd(strMastKey.getBytes());
			// 需要解密的数据
			byte[] byteEncrypt = asc2Bcd(strPINKey.getBytes());
			// 解密
			byte[] byteWorkKey = Decrypt(byteKey, byteEncrypt);

			String strCardBuf = "0000"
					+ strCardNo.substring(strCardNo.length() - 13, strCardNo
							.length() - 1);

			byte[] byteCard = asc2Bcd(strCardBuf.getBytes());
			String strPinBuf = "0"
					+ Integer.toString(clearPin.length())
					+ clearPin
					+ "FFFFFFFFFFFFFFFF".substring(0, 16 - 2 - clearPin
							.length());
			// 需要加密的数据
			byte[] bytePin = asc2Bcd(strPinBuf.getBytes());
			// 异或操作
			doXor(bytePin, byteCard, 8);

			// 加密
			byte[] byteEncPin = Encrypt(byteWorkKey, bytePin);

			// 修改时间 20070725 开始
			// return bcd2Asc(byteEncPin);

			return byteEncPin;
			// 修改时间 20070725 结束
		} catch (Exception ex) {
			return "123456".getBytes();
		}
	}

	*//**
	 * @函数功能: 生成银联标准MAC（软加密，也就是不用密码键盘来加密）
	 * @输入参数: 主密钥(明文)(密码键盘中的那个主密钥)
	 * @输入参数: MAC密钥(密文)(签到时，域62:自定义域（密钥信息）中的MAC密钥)
	 * @输入参数: MAB源串(整个包文(从MSGID到MAC域之前;包括MSGID但不包括MAC域))
	 * @输出结果: MAC值
	 */
	public static byte[] genCUPMAC(String strMastKey, String strMACKey,
			byte[] byteMab) {
		int size = 0;
		byte byteBuf[] = new byte[8];
		byte byteTmp[] = new byte[8];
		byte[] byteMAC = null;
		byte[] byteSrc = null;
		
		int i = 0;
		while (byteMab.length > size) {
			if ((byteMab.length - size) <= 8) {
				for (int n=0;n<8;n++){
					byteTmp[n] = 0x00;
				} 
				System.arraycopy(byteMab, size, byteTmp, 0, byteMab.length
						- size);
				//System.out.print("第"+i+"(final)次运算数据[data1="+TypeConvert.bytes2HexString(byteBuf)+";data2="+TypeConvert.bytes2HexString(byteTmp)+"]");
				doXor(byteBuf, byteTmp, byteMab.length - size);
				//System.out.println("异或运算后结果："+TypeConvert.bytes2HexString(byteBuf));
				break;
			}
			System.arraycopy(byteMab, size, byteTmp, 0, 8);
			//System.out.print("第"+i+"次运算数据[data1="+TypeConvert.bytes2HexString(byteBuf)+";data2="+TypeConvert.bytes2HexString(byteTmp)+"]");
			doXor(byteBuf, byteTmp, 8);
			//System.out.println("异或运算后结果："+TypeConvert.bytes2HexString(byteBuf));
			size += 8;
			i++;
		}
		//将异或运算后的最后8个字节转换成16个HEXDECIMAL；
		String resultBlock = TypeConvert.bytes2HexString(byteBuf);
		//System.out.println("--------------------------------------------");
		//System.out.println("MAB xor 运算最终结果（8字节）："+resultBlock);
		//取8个字节用MAK加密
		byte bf[] = new byte[8];
		byte bt[] = new byte[8];
		System.arraycopy(resultBlock.getBytes(),0, bf, 0, 8);
		System.arraycopy(resultBlock.getBytes(),8, bt, 0, 8);
		//System.out.println("前8字节 hexdecimal ="+TypeConvert.bytes2HexString(bf));
		byte[] byteKey = null;
		try {
			byteKey = (new BASE64Decoder()).decodeBuffer(strMastKey);
		} catch (IOException e) {
			e.printStackTrace();
		}//
		byteKey = TypeConvert.hexStringToByte(strMastKey);
		byte[] byteEncrypt = TypeConvert.hexStringToByte(strMACKey);//CashUtil.macKey;//TypeConvert.hexStringToByte(strMACKey);//strMACKey.getBytes();//asc2Bcd(strMACKey.getBytes());
		//System.out.println("mast key (hex)["+TypeConvert.bytes2HexString((byteKey))+"]");
		//System.out.println("mac key (hex)["+TypeConvert.bytes2HexString((byteEncrypt))+"]");
		
		byte[] byteWorkKey = Decrypt(byteKey, byteEncrypt,ApplicationContextInit.transformation);
		//System.out.println("根据 mastKey和macKey解密得到工作密钥(hex)["+TypeConvert.bytes2HexString(byteWorkKey)+"]");
		byte[] encBlock1 =  Encrypt(byteWorkKey, bf,ApplicationContextInit.transformation);
		//System.out.println("取前8个字节用MAK加密后的结果："+TypeConvert.bytes2HexString(encBlock1));
		//将加密后的结果与后8字节异或
		//System.out.println("后8字节 hexdecimal ="+TypeConvert.bytes2HexString(bt));
		doXor(encBlock1,bt,8);
		//System.out.println("MAK加密后与后8字节异或结果："+TypeConvert.bytes2HexString(encBlock1));
		byte[] encBlock2 = Encrypt(byteWorkKey,encBlock1,ApplicationContextInit.transformation);
		//System.out.println("异或后在进行一次单倍长密钥算法结果："+TypeConvert.bytes2HexString(encBlock2));
		resultBlock = TypeConvert.bytes2HexString(encBlock2);
		//System.out.println("转换成16个HEXDECIMAL的结果："+TypeConvert.bytes2HexString(resultBlock.getBytes()));
		byte[] result = new byte[8];
		System.arraycopy(resultBlock.getBytes(), 0, result, 0, 8);
		//System.out.println("最后返回的结果："+TypeConvert.bytes2HexString(result));
		return result;
		//byteSrc = bcd2Asc(byteBuf);
		
		//byteMAC = genX99MAC(strMastKey, strMACKey, byteSrc);
		//String mac = DecodeUtil.bcd2Str(byteMAC);

		//byte[] byteResult = new byte[8];
		//System.arraycopy(byteMAC, 0, byteResult, 0, 8);
		//return byteResult;
	}
	
	/**
	 * 将128位位元表的某一位置为1
	 * @param bitMap 原始位元表
	 * @return 位元表
	 */
	public static byte[] setBinaryOne(byte[] bitMap,int position) {
		return setBinary(bitMap,position,1);
	}
	
	/**
	 * 将128位位元表的某一位置为0
	 * @param bitMap 原始位元表
	 * @return 位元表
	 */
	public static byte[] setBinaryZero(byte[] bitMap,int position) {
		return setBinary(bitMap,position,0);
	}
	
	private static byte[] setBinary(byte[] bitMap,int position,int value){
		if (position==1) {
			return bitMap;
		}
		if (position<1 || position>128) {
			throw new IllegalArgumentException("position error");
		}
		if (bitMap.length!=8) {
			throw new IllegalArgumentException("bitMap error");
		}
		int p = position/8;
		int q = 8-position%8;
		if (position%8==0) {
			p = p-1;
			q = 0;
		}
		byte c = bitMap[p];
		if (value == 1) {
			byte temp = (byte)0x01;
			c = (byte)((temp << (q)) | c);
		} else if (value == 0 ){
			byte temp = (byte)0xFE;
			c = (byte)((temp << (q)) & c);
		}
		//System.out.print(p+"-");
		//System.out.println(q);
		bitMap[p] = c;
		return bitMap;
	}
	
	/**
	 * 128位位元表的某一个位置是否为1
	 * @param bitMap
	 * @param position 位置
	 * @return true 表示为1，false表示不为1
	 */
	public static boolean getBinaryOne(byte[] bitMap,int position) {
		if (position<0 || position>128) {
			throw new IllegalArgumentException("position error");
		}
		if (bitMap.length!=8) {
			throw new IllegalArgumentException("bitMap error");
		}
		int p = position/8;
		int q = 8-position%8;
		if (position%8==0) {
			p = p-1;
			q = 0;
		}
		byte c = bitMap[p];
		byte temp = (byte)0x01;
		c = (byte)((temp << (q)) & c);
		if (c==0x00) {
			return false;
		}
		return true;
	}
	
	public static String getDesPassword(String desPassword) {
		//
		StringBuilder sb = new StringBuilder();
		StringBuilder lastChar = new StringBuilder();
		String head = desPassword.substring(0, 2);
		desPassword = desPassword.substring(2,desPassword.length());
		String[] str = desPassword.split("=");
		for (int i=0;i<str.length-1;i++) {
			lastChar.append(str[i].substring(0, 2));
			str[i] = str[i].substring(2,str[i].length());
		}
		DesEncrypt des = new DesEncrypt();
		des.getKey(lastChar.toString()+head);
		for (String s:str) {
			sb.append(des.getDesString(s+"="));
		}
		//System.out.println("password is:"+sb.toString());
		return sb.toString();
	}
}
