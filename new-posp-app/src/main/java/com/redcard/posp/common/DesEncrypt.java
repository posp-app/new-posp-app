package com.redcard.posp.common;

import java.security.Key;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 系统名称：           信息发布系统
 * 子系统名称：          共通程序模块
 * 类：                DesEncrypt
 * 功能：		          加密解密
 * @author 		      yins
 * @version	 	      1.00
 * 作成日：		      2007/08/01
 * 修改履历：           2007/08/01  1.00 新规作成
 *                          
 */
public class DesEncrypt
{
		private Key key;
		
		/**
		 * 功能：			生成密钥
		 * @param		strKey - String
		 * @return		String
		 * @throws		无
		 */
		public void getKey(String strKey)
		{
			try{
				KeyGenerator _generator = KeyGenerator.getInstance("DES");
				_generator.init(new SecureRandom(strKey.getBytes()));
				this.key = _generator.generateKey();
				_generator=null;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		/**
		 * 功能：			加密输入的字符串
		 * @param		strMing - String  明文
		 * @return		String            密文
		 * @throws		无
		 */
		public String getEncString(String strMing)
		{
			byte[] byteMi = null;
			byte[] byteMing = null;
			String strMi = "";
			BASE64Encoder base64en = new BASE64Encoder();
			try
			{
				byteMing = strMing.getBytes("UTF8");
				byteMi = this.getEncCode(byteMing);
				strMi = base64en.encode(byteMi);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				base64en = null;
				byteMing = null;
				byteMi = null;
			}
			return strMi;
		}
		
		/**
		 * 功能：			将密文解密
		 * @param		strMing - String  密文
		 * @return		String            明文
		 * @throws		无
		 */
		public String getDesString(String strMi)
		{
			BASE64Decoder base64De = new BASE64Decoder();
			byte[] byteMing = null;
			byte[] byteMi = null;
			String strMing = "";
			try
			{
				byteMi = base64De.decodeBuffer(strMi);
				byteMing = this.getDesCode(byteMi);
				strMing = new String(byteMing, "UTF8");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				base64De = null;
				byteMing = null;
				byteMi = null;
			}
			return strMing;
		}
		
		/**
		 * 功能：			加密输入的字节数组
		 * @param		bytes - byte[]  明文
		 * @return		byte[]          密文
		 * @throws		无
		 */
		private byte[] getEncCode(byte[] byteS)
		{
			byte[] byteFina = null;
			Cipher cipher;
			try
			{
				cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				byteFina = cipher.doFinal(byteS);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				cipher = null;
			}
			return byteFina;
		}
		
		/**
		 * 功能：			将密文解密
		 * @param		bytes - byte[]  密文
		 * @return		byte[]          明文
		 * @throws		无
		 */
		private byte[] getDesCode(byte[] byteD)
		{
			Cipher cipher;
			byte[] byteFina=null;
			try{
				cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.DECRYPT_MODE, key);
				byteFina = cipher.doFinal(byteD);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				cipher=null;
			}
			return byteFina;
		
		}
		
		//测试函数
		public static void main(String[] args){
			DesEncrypt des=new DesEncrypt();//实例化一个对像
			des.getKey("123456");//生成密匙

			String strEnc = des.getEncString("单位组织去厦门旅游,早上起的太早,,那么早!");//加密字符串,返回String的密文
			System.out.println(strEnc);

			String strDes = des.getDesString(strEnc);//把String 类型的密文解密
			System.out.println(strDes);
		}

}
