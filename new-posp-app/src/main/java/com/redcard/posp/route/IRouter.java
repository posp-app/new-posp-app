package com.redcard.posp.route;

import com.redcard.posp.message.Message;
import com.redcard.posp.support.Result;

public interface IRouter {

	public Result route(Message message);
	
}
