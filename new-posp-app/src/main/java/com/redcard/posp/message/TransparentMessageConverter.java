package com.redcard.posp.message;

/**
 * 
 * 
 * 
 * @project posp_server
 * @description 
 * 透传，输入报文直接返回成输出报文。
 * 主要是为了保存接口的一致性
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-4-4
 */
public class TransparentMessageConverter implements IMessageConverter {

	public Message input2output(Message input) throws Exception {
		return input;
	}

	public Message output2input(Message output) throws Exception {
		return output;
	}

}
