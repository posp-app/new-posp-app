package com.redcard.posp.message;

public interface IMessageConverter {
	public Message input2output(Message input) throws Exception;

	public Message output2input(Message output) throws Exception;
}
