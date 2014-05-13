package com.redcard.posp.server;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

import com.pay.posp.handler.POSPInboundHandler;
import com.pay.posp.support.ApplicationContent;

public class POSPPipelineFactory implements ChannelPipelineFactory {

	
	public POSPPipelineFactory(){
	}
	
	public ChannelPipeline getPipeline() throws Exception{
		ChannelPipeline p = pipeline();
		p.addLast(ApplicationContent.HANDLER_POSP_IN_BOUND,new POSPInboundHandler());
		return p;
	}
}
