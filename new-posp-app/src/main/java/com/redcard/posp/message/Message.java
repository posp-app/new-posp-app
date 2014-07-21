package com.redcard.posp.message;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.opensymphony.oscache.util.StringUtil;
import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.common.DecodeUtil;
import com.redcard.posp.common.SecurityUtil;
import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.support.ApplicationKey;
import com.redcard.posp.support.Key;

/**
 * 
 * 
 * @project posp_server
 * @description
 *          <p>
 *          类8583消息包
 *          </p>
 *          所有的各域长的类8583消息包，继承这个类。
 *          消息会根据传入的消息格式解析出各域的域值。
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9206502260245585634L;
	private static Logger log = LoggerFactory.getLogger(Message.class);

	protected byte[][] field;
	
	protected int i = 0;
	
	protected int position = 0;
	
	protected byte[] temp = null;
	
	protected  byte[] bitMap;
	
	protected byte[] head;
	
	protected int length = 0;
	
	public byte buf[];
	
	protected StringBuilder toString;
	
	//head里边定义包长度值的长度。
	protected int packageLength = 0;
	
	protected MessageFormat format;
	
	/**
	 * MAC 元数据
	 */
	public byte[] mab;
	
	public Message() throws Exception{
		
	}
	
	public Message(MessageFormat format) throws Exception {
		this.format = format;
		int fieldCount = this.format.getFields().size();
		field = new byte[fieldCount][];
		bitMap = new byte[fieldCount/8];
		int startPostion = 0;
		for (FormatMetadata fm:format.getHead()) {
			startPostion = startPostion+fm.getLength();
			if (fm.getName().equals(ApplicationKey.PACKAGE_LENGTH)){
				packageLength = fm.getLength();
			}
		}
		head = new byte[startPostion];
		position = startPostion;
	}
	
	public Message(byte buf[],MessageFormat format) throws Exception{
		this.buf = buf;
		this.format = format;
		int fieldCount = this.format.getFields().size();
		field = new byte[fieldCount][];
		bitMap = new byte[fieldCount/8];
		int startPostion = 0;
		for (FormatMetadata fm:format.getHead()) {
			startPostion = startPostion+fm.getLength();
			if (fm.getName().equals(ApplicationKey.PACKAGE_LENGTH)){
				packageLength = fm.getLength();
			}
		}
		head = new byte[startPostion];
		position = startPostion;
		analyzeMessage();
	}
	/**
	 * 解析消息
	 * @throws Exception
	 */
	private void analyzeMessage() throws Exception {
		//取出head
		System.arraycopy(buf, 0, head, 0,head.length);
		//取出bitMap
		System.arraycopy(buf, position+packageLength, bitMap, 0,bitMap.length);
		log.debug("bit map:"+TypeConvert.bytes2HexString(bitMap));
		//根据消息格式处理各个域
		for (FormatMetadata fm:format.getFields()) {
			if (i==0) {
				field[i] = new byte[fm.getLength()];
				System.arraycopy(buf, position, field[i], 0, field[i].length);
				log.debug("field[" + (i + 1) + "]="
						+ TypeConvert.bytes2HexString(field[i]));
				position = position + field[i].length;
				//i++;
			}
			if (i==1){
				//位置加上bit-map长度
				position = position+format.getMap().getLength();
			}
			//先判断本域是否启用
			if (fm.getUse().equals(ApplicationKey.USE_N)) {
				i++;
				continue;
			} else if (fm.getUse().equals(ApplicationKey.USE_Y)) {
				//根据format的不同调不同的处理方法
				String format = fm.getFormat();
				if (format.equals(ApplicationKey.FORMAT_ASCII)) {
					//定长域
					dealField(fm.getLength());
					continue;					
				} else if(format.equals(ApplicationKey.FORMAT_BCD)) {
					//定长域
					dealField(fm.getLength());
					continue;
				} else if(format.equals(ApplicationKey.FORMAT_LLVAR)) {
					//LLVAR 就是1个BCD码标识长度
					int length = 1;
					if (fm.getVarFormat().equals(ApplicationKey.FORMAT_BCD)) {
						dealUncertainField(length);
					} else if (fm.getVarFormat().equals(ApplicationKey.FORMAT_ASCII)) {
						dealUnicertainAscField(length);
					} else if (fm.getVarFormat().equals(ApplicationKey.FORMAT_Z)) {
						dealUnicertainAscField(length);
					} else {
						throw new Exception("定义的消息格式错误，未能识别的var-format值");
					}
					continue;
				} else if(format.equals(ApplicationKey.FORMAT_LLLVAR)) {
					//LLLVAR 就是2个BCD码标识长度
					int length = 2;
					if (fm.getVarFormat().equals(ApplicationKey.FORMAT_BCD)) {
						dealUncertainField(length);
					} else if (fm.getVarFormat().equals(ApplicationKey.FORMAT_ASCII)) {
						dealUnicertainAscField(length);
					} else if (fm.getVarFormat().equals(ApplicationKey.FORMAT_Z)) {
						dealUnicertainAscField(length);
					} else {
						throw new Exception("定义的消息格式错误，未能识别的var-format值");
					}
					continue;
				} else if(format.equals(ApplicationKey.FORMAT_B64)) {
					//定长域
					dealField(fm.getLength());
					continue;
				} 
			} else {
				throw new Exception("定义的消息格式错误，未能识别的use值");
			}
		}
	}
	
	/*protected void setBCDField(Integer position, String value) {
		byte[] temp = null;
		temp = DecodeUtil.str2Bcd(value);
		System.arraycopy(temp, 0, buf, position, temp.length);
		this.position = position + temp.length;
	}

	protected void setBCDFieldRightZero(Integer position, String value) {
		byte[] temp = null;
		temp = DecodeUtil.str2BcdRightZero(value, true);
		System.arraycopy(temp, 0, buf, position, temp.length);
		this.position = position + temp.length;
	}

	protected void setField(Integer position, byte[] value) {
		System.arraycopy(value, 0, buf, position, value.length);
		this.position = position + value.length;
	}*/
	
	public void setBCDField(int fieldNumber,String value) {
		byte[] temp = DecodeUtil.str2Bcd(value);
		this.field[fieldNumber-1] = temp;
		if (fieldNumber>1){
			if (!StringUtil.isEmpty(value)) {
				SecurityUtil.setBinaryOne(bitMap, fieldNumber);
			} else {
				SecurityUtil.setBinaryZero(bitMap, fieldNumber);
			}
		}
 	}
	public void setByteField(int fieldNumber,byte[] value) {
		this.field[fieldNumber-1] = value;
		if (fieldNumber>1){
			SecurityUtil.setBinaryOne(bitMap, fieldNumber);
		}
	}
	public void setASCField(int fieldNumber,String value) {
		byte[] temp = value.getBytes();
		this.field[fieldNumber-1] = temp;
		if (fieldNumber>1){
			SecurityUtil.setBinaryOne(bitMap, fieldNumber);
		}
	}

    public void setUnicertainAscField(int fieldNumber, String value) {
        FormatMetadata fm = this.format.getFields().get(fieldNumber - 1);

        if (fm.getUse().equals(ApplicationKey.USE_N)) {
            return;
        }

        if(fieldNumber<=1){
            return;
        }

        if (!fm.getFormat().startsWith(ApplicationKey.LL)) {
            return;
        }
        int lLength = 1;

        if (fm.getFormat().startsWith(ApplicationKey.LLL)) {
            lLength = 2;
        }

        byte[] bLength = DecodeUtil.str2Bcd(CommonUtil.addLeftZero(value.length()+"",lLength*2));
        byte[] temp = value.getBytes();
        byte[] buf = new byte[temp.length + bLength.length];
        System.arraycopy(bLength, 0, buf, 0, bLength.length);
        System.arraycopy(temp, 0, buf, bLength.length, temp.length);
        this.field[fieldNumber - 1] = buf;
        SecurityUtil.setBinaryOne(bitMap, fieldNumber);
    }

	/**
	 * 处理定长域
	 * 
	 * @param length
	 *            域长
	 */
	private void dealField(int length) {
		if (SecurityUtil.getBinaryOne(bitMap, i + 1)) {
			field[i] = new byte[length];
			System.arraycopy(buf, position, field[i], 0, length);
			log.debug("field["+(i+1)+"]="+TypeConvert.bytes2HexString(field[i]));
			position = position + length;
		}
		i++;
	}
	
	/**
	 * 处理bcd编码的不定长域。
	 * 
	 * @param length
	 *            前多少字节确定不定域长度。
	 */
	private void dealUncertainField(int length) {
		// System.out.println("uncertain field binary one is
		// ---->"+SecurityUtil.getBinaryOne(bitMap, i+1));
		// length = length/2+1;
		if (SecurityUtil.getBinaryOne(bitMap, i + 1)) {
			byte[] b = new byte[length];
			System.arraycopy(buf, position, b, 0, length);
			int l = Integer.valueOf(DecodeUtil.bcd2Str(b));
			if (l % 2 != 0) {
				l = l / 2 + 1;
			} else {
				l = l / 2;
			}
			field[i] = new byte[l + length];
			System.arraycopy(buf, position, field[i], 0, l + length);
			position = position + l + length;
			log.debug("field["+(i+1)+"]="+TypeConvert.bytes2HexString(field[i]));
		}
		i++;
	}
	
	/**
	 * 处理asc编码的不定长�域 不定长域的域长度是过前n位确定的。
	 * 
	 * @param length
	 *            前多少字节确定不定域长度。
	 */
	private void dealUnicertainAscField(int length) {
		if (SecurityUtil.getBinaryOne(bitMap, i + 1)) {
			byte[] b = new byte[length];
			System.arraycopy(buf, position, b, 0, length);
			;
			// int s = Integer.valueOf(DecodeUtil.bcd2Str(b));
			// System.out.println("s="+s);
			int l = Integer.valueOf(DecodeUtil.bcd2Str(b));
			/*
			 * if (l%2!=0) { l = l/2+1; } else { l = l/2; }
			 */
			field[i] = new byte[l + length];
			System.arraycopy(buf, position, field[i], 0, l + length);
			position = position + l + length;
			log.debug("field["+(i+1)+"]="+TypeConvert.bytes2HexString(field[i]));
		}
		i++;
	}
	
	
	
	///////////////////下面是各域(64域)的返回方法//////////////////////////////////////////////
	
	/**
	 * 获取消息类型
	 * 
	 * @return 消息类型
	 */
	public String getMSGType() {
		return getFieldValue(1);
	}

	/**
	 * 获取主账户 2域
	 * 
	 * @return 主账户
	 */
	public String getAccount() {
		return getFieldValue(2);
	}

	/**
	 * 获取交易代码 3 域
	 * 
	 * @return 交易代码
	 */
	public String getTransactionCode() {
		return getFieldValue(3);
	}

	/**
	 * 获取交易金额 4域
	 * 
	 * @return 交易金额
	 */
	public String getTransactionMoney() {
		return getFieldValue(4);
	}

	/**
	 * 获取受卡方系统跟踪号（终端流水号） 11域
	 * 
	 * @return 受卡方系统跟踪号
	 */
	public String getSystemSequence() {
		return getFieldValue(11);
	}

	/**
	 * 获取受卡方所在地时间 12域
	 * 
	 * @return 受卡方所在地时间 (hhmmss)
	 */
	public String getSystemTime() {
		return getFieldValue(12);
	}

	/**
	 * 获取受卡方所在地日期 13域
	 * 
	 * @return 受卡方所在地日期(YYYYMMDD)
	 */
	public String getSystemDate() {
		return getFieldValue(13);
	}

	/**
	 * 获取卡有效期 14域
	 * 
	 * @return 卡有效期(YYMM)
	 */
	public String getDateOfExpired() {
		return getFieldValue(14);
	}

	/**
	 * 获取清算日期 15域
	 * 
	 * @return 清算日期(MMDD)
	 */
	public String getDateOfSettlement() {
		return getFieldValue(15);
	}
	/**
	 * 分期期数 19域
	 * @return
	 */
	public String getInstallmentPeriod() {
		return getFieldValue(19);
	}
	
	/**
	 * 分期返回金额20域
	 * @return
	 */
	public String getInstallmentAmount() {
		return getFieldValue(20);
	}
	

	/**
	 * 获取服务点输入方式码（ 输入模式） 22域
	 * 
	 * @return 服务点输入方式码
	 */
	public String getPointOfServiceEntryMode() {
		return getFieldValue(22);
	}

	/**
	 * 获取卡序列号 23域
	 * 
	 * @return 
	 */
	public String getCardSequenceNumber() {
		return getFieldValue(23);
	}
	/**
	 * NII (24域)
	 * @return
	 */
	public String getNII() {
		return getFieldValue(24);
	}

	/**
	 * 获取服务点条件码 25域
	 * 
	 * @return 服务点条件码
	 */
	public String getConditionCode() {
		return getFieldValue(25);
	}

	/**
	 * 获取服务点PIN获取�码 26域
	 * 
	 * @return 服务点PIN获取�码
	 */
	public String getPinCaptureCode() {
		return getFieldValue(26);
	}

	/**
	 * 获取受理方标识码 32域
	 * 
	 * @return 受理方标识码
	 */
	public String getAcquiringCode() {
		return getFieldValue(32);
	}
	
	/**
	 * 发送机构代码33域
	 * @return
	 */
	public String getSendOrgCode() {
		return getFieldValue(33);
	}
	
	/**
	 * 扩展主帐号
	 * @return
	 */
	public String getExtendAccount() {
		return getFieldValue(34);
	}

	/**
	 * 获取2磁道数据 35域
	 * 
	 * @return 2磁道数据
	 */
	public String getTrack2() {
		return getFieldValue(35);
	}

	/**
	 * 获取3磁道数据 36域
	 * 
	 * @return 3磁道数据
	 */
	public String getTrack3() {
		return getFieldValue(36);
	}

	/**
	 * 获取检索参考号 37域
	 * 
	 * @return 检索参考号
	 */
	public String getRetrievalReferenceNumber() {
		return getFieldValue(37);
	}

	/**
	 * 获取授权标识应答�码 38域
	 * 
	 * @return 授权标识应答码
	 */
	public String getAuthorizationResponseCode() {
		return getFieldValue(38);
	}

	/**
	 * 获取应答�码 39域
	 * 
	 * @return 应答码
	 */
	public String getResponseCode() {
		return getFieldOrgString(39);
	}

	/**
	 * 获取受卡机终端标识码 41域
	 * 
	 * @return 受卡机终端标识码
	 */
	public String getTerminalIdentification() {
		return getFieldValue(41);
	}

	/**
	 * 获取受卡方标识码（商户号） 42域
	 * 
	 * @return 受卡方标识码
	 */
	public String getCardAcceptorIdentification() {
		return getFieldValue(42);
	}

	/**
	 * 获取附加响应数据 44域
	 * 
	 * @return 附加响应数据
	 */
	public String getAdditionalResponseData() {
		return getFieldValue(44);
	}
	
	/**
	 * TLV处理域46域
	 * @return
	 */
	public String getTLV() {
		return getFieldValue(46);
	}

	/**
	 * 获取附加数据-私有 48域
	 * 
	 * @return 附加数据-私有
	 */
	public String getAdditionalData() {
		return getFieldValue(48);
	}

	/**
	 * 获取交易货币代码 49域
	 * 
	 * @return 交易货币代码
	 */
	public String getCurrencyCodeOfTransaction() {
		return getFieldValue(49);
	}

	/**
	 * 获取个人标识码数据 52域
	 * 
	 * @return 个人标识码数据
	 */
	public String getPINDate() {
		return getFieldValue(52);
	}

	/**
	 * 结算商户号  53域
	 * 
	 * @return 结算商户号
	 */
	public String getControlInformation() {
		return getFieldValue(53);
	}

	/**
	 * 获取余额 54域
	 * 
	 * @return 余额
	 */
	public String getBalanceAmount() {
		return getFieldValue(54);
	}

	/**
	 * 获取IC卡数据域 55域
	 * 
	 * @return IC卡数据域
	 */
	public String getICRelatedData() {
		return getFieldValue(55);
	}
	/**
	 * TC结果、发卡行脚本结果、下装EMV参数相关数据及其它 56域
	 * @return
	 */
	public String getTCResult() {
		return getFieldValue(56);
	}
	
	/**
	 * reserved 57域
	 * @return
	 */
	public String getReserved() {
		return getFieldValue(57);
	}
	
	/**
	 * 获取PBOC交易信息 58域
	 * 
	 * @return IC卡数据域
	 */
	public String getPBOCElectronicData() {
		return getFieldValue(58);
	}
	/**
	 * 获取汇率信息 59域
	 * @return
	 */
	public String getExchangeRate() {
		return getFieldValue(59);
	}

	/**
	 * 获取订单编号60域
	 * 
	 * @return 订单编号60域
	 */
	public String get60Field() {
		return getFieldValue(60);
	}

	/**
	 * 获取原始信息域 61域
	 * 
	 * @return 原始信息域 61域
	 */
	public String getOriginalMessage() {
		return getFieldValue(61);
	}
	/**
	 * 批次号 62
	 * @return
	 */
	public String getBatchNumber() {
		String batchNumber = get62Field().trim();
		return batchNumber;
	}
	/**
	 * 操作员号 60.2
	 * @return
	 */
	public String getOperateNo() {
		String original = getOriginalMessage().trim();;
		if (original == null || original.equals("")||original.length()<9){
			return "";
		}
		return original.substring(6,9);		
	}
	/**
	 * 票据号 60.3
	 * @return
	 */
	public String getInvoiceNo() {
		String original = getOriginalMessage().trim();;
		if (original == null || original.equals("")||original.length()<15){
			return "";
		}
		return original.substring(9,15);			
	}
	/**
	 * 卡类型60.4
	 * @return
	 */
	public String getCardType() {
		String original = getOriginalMessage();
		if (original == null || original.equals("")||original.length()<17){
			return "";
		}
		return original.substring(15,17);	
	}
	/**
	 * 发卡机构简称 60.5
	 * @return
	 */
	public String getIssueBank() {
		String original = getOriginalMessage();
		if (original == null || original.equals("")||original.length()<21){
			return "";
		}
		return original.substring(17,21);		
	}

	/**
	 * 获取自定义62域
	 * 
	 * @return 自定义62域
	 */
	public String get62Field() {

		return getFieldValue(62).trim();
	}
	
	/**
	 * 信息类型码62.1域
	 * @return
	 */
	public String getMessageTypeCode() {
		String field62 = get62Field().trim();;
		if (field62 == null || field62.equals("")||field62.length()<4){
			return "";
		}
		return field62.substring(0,4);		
	}
	/**
	 * 系统跟踪号
	 * @return
	 */
	public String getOriginalSystemSequence() {
		String field62 = get62Field().trim();
		if (field62 == null || field62.equals("")||field62.length()<10){
			return "";
		}
		return field62.substring(4,10);		
	}
	/**
	 * 原交易日期和时间
	 * @return
	 */
	public String getOriginalTransactionDate() {
		String field62 = get62Field().trim();;
		if (field62 == null || field62.equals("")||field62.length()<20){
			return "";
		}
		return field62.substring(10,20);		
	}

	public byte[] get62FieldByte() {
		return this.field[61];
	}

	public String getPinKey() {
		return getFieldValue(61);
	}

	public String getMacKey() {
		return getFieldValue(60);
	}
	
	public String getEncryptKey() {
		Key k = getKey(Key.KEY_TYPE_ENCRYPT);
		return k==null?"":k.getKey();
	}

	/**
	 * 获取自定义63域 63域
	 * 
	 * @return 自定义63域
	 */
	public String get63Field() {
		return getFieldValue(63);
	}

	/**
	 * 获取mac 64域
	 * 
	 * @return mac
	 */
	public String getMAC() {
		return getFieldValue(64);
	}
	
	/**
	 * 从buf串中的bcd编码域中获取asc串
	 * 
	 * @param start
	 *            bcd 码在buf中的起始位置
	 * @param length
	 *            bcd 码的长度。
	 * @return
	 */
	protected String getFieldFromBCD(int field) {
		byte[] a = this.field[field - 1];
		if (a == null || a.length < 1) {
			return "";
		}
		return DecodeUtil.bcd2Str(a);
	}
	
	protected String getFieldFromByte(int field) {
		byte[] a = this.field[field - 1];
		if (a == null || a.length < 1) {
			return "";
		}
		return TypeConvert.bytes2HexString(a);
	}

	/**
	 * 从buf串中得asc编码域中获取asc串
	 * 
	 * @param start
	 * @param length
	 * @return
	 */
	protected String getFieldFromASC(String charset,int field) {
		byte[] a = this.field[field - 1];
		if (a == null || a.length < 1) {
			return "";
		}
		try {
			if (!StringUtil.isEmpty(charset)) {
				return new String(a,charset);
			} else {
				return new String(a);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new String(a);
	}
	
	protected String getUncertainFieldFromASC(int lLength,String charset,int field) {
		byte[] a = this.field[field - 1];
		if (a == null || a.length < 1) {
			return "";
		}
		byte[] l = new byte[lLength];
		System.arraycopy(a, 0, l, 0, l.length);
		int fieldLength = Integer.parseInt(DecodeUtil.bcd2Str(l));
		byte[] b = new byte[fieldLength];
		System.arraycopy(a, lLength, b, 0, fieldLength);
		String result = "";
		try {
			if (!StringUtil.isEmpty(charset)) {
				result = new String(b,charset);
			} else {
				result = new String(b);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = new String(b);
		}
		return DecodeUtil.bcd2Str(l)+result;
	}
	
	protected String getFieldValue(int field) {
		//从消息格式获取对应域是什么格式。
		FormatMetadata fm = this.format.getFields().get(field-1);
		if (fm.getUse().equals(ApplicationKey.USE_N)){
			return "";
		} else {
			String format = ApplicationKey.FORMAT_ASCII;
			int length = 0;
			if (fm.getFormat().startsWith(ApplicationKey.LL)) {
				format = fm.getVarFormat();
				length = 1;
				if (fm.getFormat().startsWith(ApplicationKey.LLL)) {
					length = 2;
				}
			} else {
				format = fm.getFormat();
			}
			String resultValue = "";
			if (format.equals(ApplicationKey.FORMAT_BCD)) {
				resultValue = getFieldFromBCD(field).trim();
			} else if (format.equals(ApplicationKey.FORMAT_Z)||
					format.equals(ApplicationKey.FORMAT_B64)) {
				resultValue = getFieldFromByte(field).trim();
			} else if (fm.getFormat().startsWith(ApplicationKey.LL)) {
				resultValue = getUncertainFieldFromASC(length,fm.getCharset(), field).trim();
			} else {
				resultValue = getFieldFromASC(fm.getCharset(),field).trim();
				//System.out.println("++++++++++++++++>>>>>>>>>>>"+resultValue);
			}
			if (fm.getFormat().startsWith(ApplicationKey.LL)) {
				if (resultValue.length()>length) {
					int dataLength = Integer.parseInt(resultValue.substring(0,length*2));
					if (format.equals(ApplicationKey.FORMAT_Z)){
						dataLength = dataLength*2;
					}
					//这里要处理左右靠的问题。
					if (fm.getDirection().equals(ApplicationKey.DIRECTION_RIGHT)) {
						resultValue = resultValue.substring(resultValue.length()-dataLength,resultValue.length());
					} else {
						resultValue = resultValue.substring(length*2,resultValue.length());
					}
				}
			}
			return resultValue;
		}
	}
	
	protected String getFieldOrgString(int field) {
		//从消息格式获取对应域是什么格式。
		FormatMetadata fm = this.format.getFields().get(field-1);
		if (fm.getUse().equals(ApplicationKey.USE_N)){
			return "";
		} else {
			String format = ApplicationKey.FORMAT_ASCII;
			boolean uncertain = false;
			if (fm.getFormat().startsWith(ApplicationKey.LL)) {
				format = fm.getVarFormat();
				uncertain = true;
			} else {
				format = fm.getFormat();
			}
			if (format.equals(ApplicationKey.FORMAT_BCD)) {
				return getFieldFromBCD(field).trim();
			} else if (format.equals(ApplicationKey.FORMAT_Z)||
					format.equals(ApplicationKey.FORMAT_B64)) {
				return getFieldFromByte(field).trim();
			} else {
				if (uncertain) {
					int lLength = 1;
					if (fm.getFormat().startsWith(ApplicationKey.LLL)) {
						lLength = 2;
					}
					return getUncertainFieldFromASC(lLength,fm.getCharset(),field);
				} else {
					return getFieldFromASC(fm.getCharset(),field);
				}
			}
		}
	}
	
	public byte[] toMessgeBytes() {
		int length = this.bitMap.length+this.head.length;
		for (byte[] f:this.field) {
			if (f!=null) {
				length = length+f.length;
			}
		}
		byte[] messageBytes = new byte[length];
		//String lengthStr = CommonUtil.addLeftZero(Integer.toString(length), 4);
		System.arraycopy(this.head, 0, messageBytes, 0, this.head.length);
		//两字节长度;-2是长度字节
		byte[] packetLength = TypeConvert.short2byte(length-2);
		//System.out.println(TypeConvert.bytes2HexString(packetLength));
		System.arraycopy(packetLength, 0, messageBytes, 0, 2);
		int position = this.head.length;
		int i = 1;
		for (byte[] f:this.field) {
			//遍历所以域，组装整个消息包
			if (f!=null) {
				System.arraycopy(f, 0, messageBytes, position, f.length);
				position = position + f.length;
			}
			if (i==1){
				System.arraycopy(this.bitMap, 0, messageBytes, position, this.bitMap.length);
				position = position + this.bitMap.length;
			} 
			i++;
		}
		return messageBytes;
	}
	
	public byte[] toSupDataMessgeBytes() {
		int length = 0;
		for (byte[] f:this.field) {
			if (f!=null) {
				length = length+f.length;
			}
		}
		byte[] messageBytes = new byte[length];
		int posi = 0;
		for (byte[] f:this.field) {
			//遍历所以域，组装整个消息包
			if (f!=null) {
				System.arraycopy(f, 0, messageBytes, posi, f.length);
				posi = posi + f.length;
			}
		}
		return messageBytes;
	}
	
	/*public void setFieldValue(int number,String value){
		
	}*/
	
	private Key getKey(String type){
		//List<Key> keyList = new ArrayList<Key>();
		String f48 = getFieldValue(48);
		String f = f48;
		while (f != null && f.length()>4) {
			Key k = new Key();
			k.setTag(f.substring(0, 2));
			k.setSubFieldLength(Integer.parseInt(f.substring(2, 4)));
			String temp = f.substring(4,4+k.getSubFieldLength());
			int keyLength = 16;
			if (k.getSubFieldLength() == 35) {
				//单倍长
				
			} else {
				//双倍长
				keyLength = 32;
			}
			k.setKeyIndex(Integer.parseInt(temp.substring(0,1)));
			k.setKey(temp.substring(1, 1+keyLength));
			k.setKeyType(temp.substring(1+keyLength,keyLength+3));
			k.setKeyCheckValue(temp.substring(keyLength+3,keyLength+19));
			f = f.substring(keyLength+23);
			if (type.equals(k.getKeyType())) {
				return k;
			}
			//keyList.add(k);
		}
		return null;
	}
	
	public String to8583FormatString() {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("bitMap=[" + TypeConvert.bytes2HexString(bitMap) + "]\r");
			for (int i = 1; i <= field.length; i++) {
				if (!StringUtils.isEmpty(getFieldOrgString(i))) {
					// System.out.println(i+":-------------["+TypeConvert.bytes2HexString(field[i-1])+"]----------------");
					sb.append("Field[" + i + "]=" + getFieldOrgString(i) + "\n");
				}
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public byte[][] getField() {
		return field;
	}

	public byte[] getBitMap() {
		return bitMap;
	}

	public void setBitMap(byte[] bitMap) {
		this.bitMap = bitMap;
	}

	public byte[] getHead() {
		return head;
	}

	public void setHead(byte[] head) {
		this.head = head;
	}
}
