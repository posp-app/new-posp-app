package com.redcard.posp.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.netty.channel.socket.ClientSocketChannelFactory;

import com.pay.posp.message.MessageFormat;
import com.pay.posp.message.TransFormat;

public class ApplicationContextCache {

	/**
	 * 系统各类消息格式
	 */
	public static Map<String,MessageFormat> applicationMessageFormat = new HashMap<String,MessageFormat>();
	public static Map<String,List<TransFormat>> applicationTransFormat = new HashMap<String,List<TransFormat>>();
	
	public static MessageFormat inputMessageFormat = null;
	
	public static List<TransFormat> inputTrans = new ArrayList<TransFormat>();
	
	public static MessageFormat outputMesssageFormat = null;
	
	public static List<TransFormat> outputTrans = new ArrayList<TransFormat>();
	
	public static ClientSocketChannelFactory clientSocketChannelFactory;
	
	public static boolean tranLock;
	
	
	
	public static TransFormat getTranFromInput(String messageType){
		return getTran(messageType,inputTrans);
	}
	
	public static TransFormat getTranFromOutput(String messageType) {
		return getTran(messageType,outputTrans);
	}
	
	private static TransFormat getTran(String messageType,List<TransFormat> trans){
		for (TransFormat tr:trans) {
			if (tr.getType().equals(messageType)) {
				return tr;
			}
		}
		return null;
	}
}
