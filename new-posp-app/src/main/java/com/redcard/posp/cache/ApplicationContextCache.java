package com.redcard.posp.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.socket.ClientSocketChannelFactory;

import com.redcard.posp.message.MessageFormat;
import com.redcard.posp.message.TransFormat;
import com.redcard.posp.support.ApplicationException;
import com.redcard.posp.support.OutMessageConfig;
import com.redcard.posp.support.ResultCode;

public class ApplicationContextCache {

	/**
	 * 系统各类消息格式
	 */
	public static Map<String,MessageFormat> applicationMessageFormat = new HashMap<String,MessageFormat>();
	public static Map<String,List<TransFormat>> applicationTransFormat = new HashMap<String,List<TransFormat>>();
	
	public static MessageFormat inputMessageFormat = null;
	
	public static List<TransFormat> inputTrans = new ArrayList<TransFormat>();
	
	/*public static MessageFormat outputMesssageFormat = null;
	
	public static List<TransFormat> outputTrans = new ArrayList<TransFormat>();*/
	
	public static Map<OutMessageConfig,MessageFormat> allOutputMessageFormat = new Hashtable<OutMessageConfig,MessageFormat>();
	public static Map<OutMessageConfig,List<TransFormat>> allOutputTrans = new Hashtable<OutMessageConfig,List<TransFormat>>();
	
	public static ClientSocketChannelFactory clientSocketChannelFactory;
	
	public static boolean tranLock;
	
	
	
	public static TransFormat getTranFromInput(String messageType,String proccess){
		return getTran(messageType,proccess,inputTrans);
	}
	
	public static TransFormat getTranFromOutput(String messageType,String proccess,String name) 
		throws ApplicationException{
		List<TransFormat> outputTrans = allOutputTrans.get(name);
		if (outputTrans == null) {
			throw new ApplicationException(ResultCode.RESULT_CODE_94);
		}
		return getTran(messageType,proccess,outputTrans);
	}
	
	private static TransFormat getTran(String messageType,String proccess,List<TransFormat> trans){
		for (TransFormat tr:trans) {
			if (tr.getType().equals(messageType)&&tr.getProcess().equals(proccess)) {
				return tr;
			}
		}
		return null;
	}
}
