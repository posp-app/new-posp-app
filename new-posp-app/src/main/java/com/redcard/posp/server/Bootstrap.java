package com.redcard.posp.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.cache.ApplicationContextCache;
import com.redcard.posp.support.ApplicationContextInit;

public class Bootstrap {
    private static Logger logger = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * @param args
     */
    public static void main(String[] args) {

        logger.info("Usage - java com.redcard.posp.server.Bootstrap <port> <workerCount> ");
        logger.info("Usage - java -jar  new-posp-app.jar <port> <workerCount>");
        ApplicationContextInit.getInstance();
        int port = ApplicationContextInit.receiverPort;
        int workerCount = ApplicationContextInit.workerCount;
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.exit(1);
            }
        }

        if (args.length > 2) {
            try {
                workerCount = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.exit(1);
            }
        }
        Executor executor = Executors.newCachedThreadPool();
        ServerBootstrap serverBootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        executor,
                        executor, workerCount));


        ApplicationContextCache.clientSocketChannelFactory =
                new NioClientSocketChannelFactory(executor, executor, workerCount);

        serverBootstrap.setPipelineFactory(new POSPPipelineFactory());

        // Bind and start to accept incoming connections.
        serverBootstrap.bind(new InetSocketAddress(port));
        logger.info("启动接收端端口=[" + port + "]成功");
        logger.info("-------->> posp right here <<-------- \n");
    }
}