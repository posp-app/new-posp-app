package com.redcard.posp.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.ClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.posp.cache.ApplicationContextCache;
import com.pay.posp.support.ApplicationContextInit;

public class Bootstrap {
	private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		logger.info("Usage - java com.pay.posp.server.Bootstrap <port>");
		logger.info("Usage - java -jar  posp.jar <port>");
        ApplicationContextInit.getInstance();
		int port = ApplicationContextInit.receiverPort;
		if (args.length>1) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.exit(1);
			}
		}
		Executor executor = Executors.newCachedThreadPool();
		ServerBootstrap serverBootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                		executor,
                		executor));


        
		ApplicationContextCache.clientSocketChannelFactory =
                new NioClientSocketChannelFactory(executor, executor);
        

        // Set up the pipeline factory.
		/*serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new POSPServerHandler());
            }
        });*/
        serverBootstrap.setPipelineFactory(new POSPPipelineFactory());
        
        // Bind and start to accept incoming connections.
		serverBootstrap.bind(new InetSocketAddress(port));
        logger.info("启动接收端�?端口="+port+"�?成功");
        logger.info("-------->> posp right here <<-------- \n");
	}
}