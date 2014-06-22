package com.redcard.posp.route;


import java.net.InetSocketAddress;
import java.util.Map;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.cache.ApplicationContextCache;
import com.redcard.posp.handler.SupDataOutboundHandler;
import com.redcard.posp.handler.ShareOutboundHandler;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationKey;

public class ChannelFactory {
	
	private static Logger logger = LoggerFactory.getLogger(ChannelFactory.class);

	

	/**
	 * 根据消息，得到要路由到哪个目标通道(地址)
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public  static ChannelFuture createChannel(Map<String,String> param,Channel inBoundChannel) 
			throws Exception {
		return createChannel(param.get(ApplicationKey.IP),
				Integer.parseInt(param.get(ApplicationKey.PORT)),
				param.get(ApplicationKey.PROTOCOL_TYPE),
				inBoundChannel);
		//return createChannel(ApplicationContextInit.upLinkServerIP,ApplicationContextInit.upLinkServerPort,inBoundChannel);
	}
	
	private static ChannelFuture createChannel(String ip,int port,String protocolType,final Channel inBoundChannel) 
		throws Exception {
		//ClientSocketChannelFactory cf;
		ClientBootstrap cb = new ClientBootstrap(ApplicationContextCache.clientSocketChannelFactory);
		logger.debug("获取目标通道：IP=["+ip+"];PORT=["+port+"];protocol=["+protocolType+"]");
		if (ApplicationKey.PROTOCOL_TYPE_SUPDATA.equals(protocolType)) {
			cb.getPipeline()
			.addLast(ApplicationContent.HANDLER_POSP_OUT_BOUND, new SupDataOutboundHandler(inBoundChannel));		
		} else {
			cb.getPipeline()
			.addLast(ApplicationContent.HANDLER_POSP_OUT_BOUND, new ShareOutboundHandler(inBoundChannel));
			
		}
		ChannelFuture f = cb.connect(new InetSocketAddress(ip,port));
		return f;
	}
}
