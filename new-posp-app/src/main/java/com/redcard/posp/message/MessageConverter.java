package com.redcard.posp.message;

import com.redcard.posp.cache.ApplicationContextCache;
import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.common.DecodeUtil;
import com.redcard.posp.common.SecurityUtil;
import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationKey;


/**
 * 
 * 
 * 
 * @project posp_server
 * @description 
 * 消息转换器，用于将接收到的类8583报文转换到发送的类8583报文。
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-3-31
 */
public class MessageConverter implements IMessageConverter{

	public Message input2output(Message input) throws Exception{
		return convert(input,ApplicationContent.MESSAGE_IO_I);
	}
	
	public Message output2input(Message output) throws Exception {
		return convert(output,ApplicationContent.MESSAGE_IO_O);
	}
	/**
	 * 消息转换函数
	 * 根据消息来源转换到目标消息。
	 * 消息来源为input则转换成output，
	 * 消息来源为output则转换为input
	 * @param msg 源消息
	 * @param direction 源消息来源方向 
	 * @return 目标消息
	 */
	private Message convert(Message msg,int direction) throws Exception{
		String msgType = msg.getMSGType();
		String proccess = msg.getTransactionCode();
		TransFormat inputTran = ApplicationContextCache.getTranFromInput(msgType,proccess);
        //@TODO 不知道为什么需要传3个参数
		TransFormat outputTran = ApplicationContextCache.getTranFromOutput(msgType,proccess,null);
		MessageFormat inputMessageFormat = ApplicationContextCache.inputMessageFormat;
        //@TODO 静态变量已经创建，但不知道有没有用
		MessageFormat outputMessageFormat = ApplicationContextCache.outputMesssageFormat;
		if (inputTran == null) {
			throw new Exception("消息转换失败，接收端(input)没有找到对应消息类型【"+msgType+"】的定义");
		}
		if (outputTran == null) {
			throw new Exception("消息转换失败，发送端(output)没有找到对应消息类型【"+msgType+"】的定义");
		}
		if (direction == ApplicationContent.MESSAGE_IO_I) {
			//构造消息的消息头head
			int headLength = 0;
			//int allPackageLength = 0;
			for (FormatMetadata fm:outputMessageFormat.getHead()) {
				headLength = headLength+fm.getLength();
			}
			//allPackageLength = allPackageLength+headLength;
			//allPackageLength = allPackageLength+outputMessageFormat.getMap().getLength();
			Message result = new Message(outputMessageFormat);
			for (FormatMetadata fm:outputTran.getFields()){
				//System.out.println("------------:"+fm.getNumber());
				//获取域值，然后赋给result对应的field
					//必填值
				if (fm.getSourceType().equals(ApplicationKey.SOURCE_TYPE_D)) {
					// 域值来之于定义值
					FormatMetadata omf = outputMessageFormat.getFields().get(fm.getNumber()-1);
					if (omf.getUse().equals(ApplicationKey.USE_N)) {
						throw new Exception("消息转换错误：目标消息【"+omf.getNumber()+"】域未启用");
					}
					if (omf.getFormat().equals(ApplicationKey.FORMAT_BCD)||
							omf.getFormat().equals(ApplicationKey.FORMAT_B64)) { 
						result.field[fm.getNumber()-1] = DecodeUtil.str2Bcd(fm.getDefaultValue());
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
					} else if(omf.getFormat().equals(ApplicationKey.FORMAT_ASCII)) { 
						result.field[fm.getNumber()-1] = fm.getDefaultValue().getBytes();
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
					} else if(omf.getFormat().startsWith(ApplicationKey.LL)) {
						String varFormat = omf.getVarFormat();
						int length = fm.getDefaultValue().length();
						if (varFormat.equals(ApplicationKey.FORMAT_BCD)) { 
							//length = fm.getDefaultValue().length();
							result.field[fm.getNumber()-1] = DecodeUtil.str2Bcd(fm.getDefaultValue());
							SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
						} else if (varFormat.equals(ApplicationKey.FORMAT_B64) ||
								varFormat.equals(ApplicationKey.FORMAT_Z)) { 
							//length = fm.getDefaultValue().length();
							result.field[fm.getNumber()-1] = TypeConvert.hexStringToByte(fm.getDefaultValue());
							SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
						} else if(varFormat.equals(ApplicationKey.FORMAT_ASCII)) { 
							result.field[fm.getNumber()-1] = fm.getDefaultValue().getBytes();
							SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
							//length = fm.getDefaultValue().length()/2;
						} else {
							throw new Exception("消息转换错误：不定长【"+omf.getNumber()+"】域转换出错，未知域格式");
						}
						int lLength = 2;
						if (omf.getFormat().equals(ApplicationKey.FORMAT_LLVAR)) {
							lLength = 2;
						} else if (omf.getFormat().equals(ApplicationKey.FORMAT_LLLVAR)) {
							lLength = 3;
						}
						byte[] temp = new byte[lLength+result.field[fm.getNumber()-1].length];
						String sLength = CommonUtil.addLeftZero(Integer.valueOf(length).toString(),lLength);
						byte[] bLength = DecodeUtil.str2Bcd(sLength);
						System.arraycopy(bLength, 0, temp, 0,bLength.length);
						System.arraycopy(result.field[fm.getNumber()-1], 0, temp, bLength.length,result.field[fm.getNumber()-1].length);
						result.field[fm.getNumber()-1] = temp;
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
					} else {
						throw new Exception("消息转换错误：【"+omf.getNumber()+"】域转换出错，未知域格式");
					}
				} else if (fm.getSourceType().equals(
						ApplicationKey.SOURCE_TYPE_C)) {
					// 域值来自于转换源消息。
					FormatMetadata omf = outputMessageFormat.getFields().get(fm.getNumber()-1);
					FormatMetadata imf = inputMessageFormat.getFields().get(fm.getNumber()-1);
					if (omf.getFormat().equals(imf.getFormat())&& omf.getLength()==omf.getLength()) {
						if (msg.field[fm.getNumber()-1] == null) {
							if (fm.getRequest().equals(ApplicationKey.REQUEST_M)) {
								throw new Exception("消息转换错误：【"+omf.getNumber()+"】域转换出错，源消息无对应交易");
							} else if (fm.getRequest().equals(ApplicationKey.REQUEST_C)) {
								continue;
							}  else {
							}
						}
						result.field[fm.getNumber()-1] = msg.field[fm.getNumber()-1];
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
					} else {
						//处理格式和长度不一致的情况。
						
					}
				} else if (fm.getSourceType().equals(
						ApplicationKey.SOURCE_TYPE_P)) {
					// 域值来自于系统
					throw new Exception("消息转换错误：【"+fm.getNumber()+"】域转换出错，域值来自于系统。此功能暂时未实现。");
				} else {
					throw new Exception("消息转换错误：【"+fm.getNumber()+"】域转换出错，未知域值来源定义");
				}
			}
			int fieldByteLength = 0;
			for (byte[] b:result.field){
				if (b == null) {
					continue;
				}
				fieldByteLength = fieldByteLength+b.length;
			}
			int allPackageLength = headLength+fieldByteLength+result.bitMap.length;
			byte[] buf = new byte[allPackageLength];
			int position = 0;
			for (FormatMetadata fm:outputMessageFormat.getHead()) {
				if (fm.getName().equals(ApplicationKey.PACKAGE_LENGTH)) {
					System.arraycopy(DecodeUtil.str2Bcd(CommonUtil.addLeftZero(Integer.toString(allPackageLength),fm.getLength()*2)),
							0, buf, position,fm.getLength());
					//position = position+fm.getLength();
				} else {
					String value = fm.getDefaultValue();
					if (fm.getFormat().equals(ApplicationKey.FORMAT_BCD)) {
						System.arraycopy(DecodeUtil.str2Bcd(value), 0, buf, position,fm.getLength());
					} else if (fm.getFormat().equals(ApplicationKey.FORMAT_ASCII)) {
						System.arraycopy(value.getBytes(), 0, buf, position,fm.getLength());
					} else {
						throw new Exception("未知消息头格式。");
					}
				}
				position = position+fm.getLength();
			}
			int i = 0;
			for (byte[] f:result.field) {
				if (f == null) {
					continue;
				}
				System.arraycopy(f, 0, buf, position,f.length);
				position = position+f.length;
				if (i==1){
					//加上bitmap
					System.arraycopy(result.bitMap, 0, buf, position,result.bitMap.length);
					position = position+result.bitMap.length;
				}
				i++;
			}
			//System.out.println("hex=["+TypeConvert.bytes2HexString(buf)+"]");
			result.buf = buf;
			return result;
		} else if (direction == ApplicationContent.MESSAGE_IO_O) {
			
		}
		throw new Exception("消息来源未知。");
	}
	
	public static Message create(MessageFormat orgMessageFormat,Message orgMessage,MessageFormat targetMessageFormat,TransFormat targetTransFormat) 
			throws Exception{

		Message result = new Message(targetMessageFormat);
		//构造消息的消息头head
		int headLength = 0;
		//int allPackageLength = 0;
		for (FormatMetadata fm:targetMessageFormat.getHead()) {
			headLength = headLength+fm.getLength();
		}
		//allPackageLength = allPackageLength+headLength;
		//allPackageLength = allPackageLength+outputMessageFormat.getMap().getLength();
		for (FormatMetadata fm:targetTransFormat.getFields()){
			//System.out.println("------------:"+fm.getNumber());
			//获取域值，然后赋给result对应的field
				//必填值
			if (fm.getSourceType().equals(ApplicationKey.SOURCE_TYPE_D)) {
				// 域值来之于定义值
				FormatMetadata omf = targetMessageFormat.getFields().get(fm.getNumber()-1);
				if (omf.getUse().equals(ApplicationKey.USE_N)) {
					throw new Exception("消息转换错误：目标消息【"+omf.getNumber()+"】域未启用");
				}
				if (omf.getFormat().equals(ApplicationKey.FORMAT_BCD)||
						omf.getFormat().equals(ApplicationKey.FORMAT_B64)) { 
					result.field[fm.getNumber()-1] = DecodeUtil.str2Bcd(fm.getDefaultValue());
					SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
				} else if(omf.getFormat().equals(ApplicationKey.FORMAT_ASCII)) { 
					result.field[fm.getNumber()-1] = fm.getDefaultValue().getBytes();
					SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
				} else if(omf.getFormat().startsWith(ApplicationKey.LL)) {
					String varFormat = omf.getVarFormat();
					int length = fm.getDefaultValue().length();
					if (varFormat.equals(ApplicationKey.FORMAT_BCD)) { 
						//length = fm.getDefaultValue().length();
						result.field[fm.getNumber()-1] = DecodeUtil.str2Bcd(fm.getDefaultValue());
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
					} else if (varFormat.equals(ApplicationKey.FORMAT_B64) ||
							varFormat.equals(ApplicationKey.FORMAT_Z)) { 
						//length = fm.getDefaultValue().length();
						result.field[fm.getNumber()-1] = TypeConvert.hexStringToByte(fm.getDefaultValue());
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
					} else if(varFormat.equals(ApplicationKey.FORMAT_ASCII)) { 
						result.field[fm.getNumber()-1] = fm.getDefaultValue().getBytes();
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
						//length = fm.getDefaultValue().length()/2;
					} else {
						throw new Exception("消息转换错误：不定长【"+omf.getNumber()+"】域转换出错，未知域格式");
					}
					int lLength = 2;
					if (omf.getFormat().equals(ApplicationKey.FORMAT_LLVAR)) {
						lLength = 2;
					} else if (omf.getFormat().equals(ApplicationKey.FORMAT_LLLVAR)) {
						lLength = 3;
					}
					byte[] temp = new byte[lLength+result.field[fm.getNumber()-1].length];
					String sLength = CommonUtil.addLeftZero(Integer.valueOf(length).toString(),lLength);
					byte[] bLength = DecodeUtil.str2Bcd(sLength);
					System.arraycopy(bLength, 0, temp, 0,bLength.length);
					System.arraycopy(result.field[fm.getNumber()-1], 0, temp, bLength.length,result.field[fm.getNumber()-1].length);
					result.field[fm.getNumber()-1] = temp;
					SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
				} else {
					throw new Exception("消息转换错误：【"+omf.getNumber()+"】域转换出错，未知域格式");
				}
			} else if (fm.getSourceType().equals(
					ApplicationKey.SOURCE_TYPE_C)) {
				// 域值来自于转换源消息。
				FormatMetadata omf = targetMessageFormat.getFields().get(fm.getNumber()-1);
				FormatMetadata imf = orgMessageFormat.getFields().get(fm.getNumber()-1);
				if (omf.getFormat().equals(imf.getFormat())&& omf.getLength()==omf.getLength()) {
					if (orgMessage.field[fm.getNumber()-1] == null) {
						if (fm.getRequest().equals(ApplicationKey.REQUEST_M)) {
							throw new Exception("消息转换错误：【"+omf.getNumber()+"】域转换出错，源消息无对应交易");
						} else if (fm.getRequest().equals(ApplicationKey.REQUEST_C)) {
							continue;
						}  else {
						}
					}
					result.field[fm.getNumber()-1] = orgMessage.field[fm.getNumber()-1];
					SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
				} else {
					//处理格式和长度不一致的情况。
					throw new Exception("消息转换错误：【"+fm.getNumber()+"】域转换出错，不支持域值长度不一致的情况");
				}
			} else if (fm.getSourceType().equals(
					ApplicationKey.SOURCE_TYPE_P)) {
				//当前位置，来源是非预定义和源交易，域值还不能确定。
				// 域值来自于系统
				//throw new Exception("消息转换错误：【"+fm.getNumber()+"】域转换出错，域值来自于系统。此功能暂时未实现。");
				IAutoContent autoContent = new RedCardAutoFieldContent();
				String fieldValue = null;
				try {
					fieldValue = autoContent.getField(fm.getNumber(),orgMessage.getTerminalIdentification());
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
				FormatMetadata omf = targetMessageFormat.getFields().get(fm.getNumber()-1);
				if (omf.getUse().equals(ApplicationKey.USE_N)) {
					throw new Exception("消息转换错误：目标消息【"+omf.getNumber()+"】域未启用");
				}
				if (omf.getFormat().equals(ApplicationKey.FORMAT_BCD)||
						omf.getFormat().equals(ApplicationKey.FORMAT_B64)) { 
					result.field[fm.getNumber()-1] = DecodeUtil.str2Bcd(fieldValue);
					SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
				} else if(omf.getFormat().equals(ApplicationKey.FORMAT_ASCII)) { 
					result.field[fm.getNumber()-1] = fieldValue.getBytes();
					SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
				} else if(omf.getFormat().startsWith(ApplicationKey.LL)) {
					String varFormat = omf.getVarFormat();
					int length = fieldValue.length();
					if (varFormat.equals(ApplicationKey.FORMAT_BCD)) { 
						//length = fm.getDefaultValue().length();
						result.field[fm.getNumber()-1] = DecodeUtil.str2Bcd(fieldValue);
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
					} else if (varFormat.equals(ApplicationKey.FORMAT_B64) ||
							varFormat.equals(ApplicationKey.FORMAT_Z)) { 
						//length = fm.getDefaultValue().length();
						result.field[fm.getNumber()-1] = TypeConvert.hexStringToByte(fieldValue);
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
					} else if(varFormat.equals(ApplicationKey.FORMAT_ASCII)) { 
						result.field[fm.getNumber()-1] = fieldValue.getBytes();
						SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
						//length = fm.getDefaultValue().length()/2;
					} else {
						throw new Exception("消息转换错误：不定长【"+omf.getNumber()+"】域转换出错，未知域格式");
					}
					int lLength = 2;
					if (omf.getFormat().equals(ApplicationKey.FORMAT_LLVAR)) {
						lLength = 2;
					} else if (omf.getFormat().equals(ApplicationKey.FORMAT_LLLVAR)) {
						lLength = 3;
					}
					String sLength = CommonUtil.addLeftZero(Integer.valueOf(length).toString(),lLength);
					byte[] bLength = DecodeUtil.str2Bcd(sLength);
					byte[] temp = new byte[bLength.length+result.field[fm.getNumber()-1].length];
					System.arraycopy(bLength, 0, temp, 0,bLength.length);
					System.arraycopy(result.field[fm.getNumber()-1], 0, temp, bLength.length,result.field[fm.getNumber()-1].length);
					result.field[fm.getNumber()-1] = temp;
					SecurityUtil.setBinaryOne(result.bitMap,fm.getNumber());
				} else {
					throw new Exception("消息转换错误：【"+omf.getNumber()+"】域转换出错，未知域格式");
				}
			} else {
				throw new Exception("消息转换错误：【"+fm.getNumber()+"】域转换出错，未知域值来源定义");
			}
		}
		int fieldByteLength = 0;
		for (byte[] b:result.field){
			if (b == null) {
				continue;
			}
			fieldByteLength = fieldByteLength+b.length;
		}
		int allPackageLength = headLength+fieldByteLength+result.bitMap.length;
		byte[] buf = new byte[allPackageLength];
		int position = 0;
		byte[] head = new byte[headLength];
		for (FormatMetadata fm:targetMessageFormat.getHead()) {
			if (fm.getName().equals(ApplicationKey.PACKAGE_LENGTH)) {
				System.arraycopy(DecodeUtil.str2Bcd(CommonUtil.addLeftZero(Integer.toString(allPackageLength),fm.getLength()*2)),
						0, buf, position,fm.getLength());
				System.arraycopy(DecodeUtil.str2Bcd(CommonUtil.addLeftZero(Integer.toString(allPackageLength),fm.getLength()*2)),
						0, head, position,fm.getLength());
				//position = position+fm.getLength();
			} else {
				String value = fm.getDefaultValue();
				if (fm.getFormat().equals(ApplicationKey.FORMAT_BCD)) {
					System.arraycopy(DecodeUtil.str2Bcd(value), 0, buf, position,fm.getLength());
					System.arraycopy(DecodeUtil.str2Bcd(value), 0, head, position,fm.getLength());
				} else if (fm.getFormat().equals(ApplicationKey.FORMAT_ASCII)) {
					System.arraycopy(value.getBytes(), 0, buf, position,fm.getLength());
					System.arraycopy(value.getBytes(), 0, head, position,fm.getLength());
				} else {
					throw new Exception("未知消息头格式。");
				}
			}
			position = position+fm.getLength();
		}
		result.head = head;
		int i = 0;
		for (byte[] f:result.field) {
			if (f == null) {
				continue;
			}
			System.arraycopy(f, 0, buf, position,f.length);
			position = position+f.length;
			if (i==1){
				//加上bitmap
				System.arraycopy(result.bitMap, 0, buf, position,result.bitMap.length);
				position = position+result.bitMap.length;
			}
			i++;
		}
		//System.out.println("hex=["+TypeConvert.bytes2HexString(buf)+"]");
		result.buf = buf;
		/*byte[] head = new byte[headLength];
		for (FormatMetadata fm:targetMessageFormat.getHead()) {
			
			
		}*/
		return result;
	}
}
