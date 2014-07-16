package com.redcard.posp.manage.service;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.cache.ApplicationContextCache;
import com.redcard.posp.common.DecodeUtil;
import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.handler.SignHandler;
import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.MessageConverter;
import com.redcard.posp.message.MessageFactory;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;
import com.redcard.posp.support.ApplicationContextInit;
import com.redcard.posp.support.IDBuilder;


/**
 * 
 * 
 * 
 * @project posp_server
 * @description 
 * 签到处理类。用于对各平台做签到处理。
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-4-24
 */
public class SignService {
	
	private static Logger logger = LoggerFactory.getLogger(SignService.class);


	private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static final long PERIOD_DAY = 24*60*60*1000;
	
	public static void startSignService() {
		/*Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MILLISECOND,(int)PERIOD_DAY);
		calendar.set(Calendar.HOUR_OF_DAY,ApplicationContextSets.HOUR_RUN);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		long first = calendar.getTime().getTime();
		long current = new Date().getTime();
		long runTime = 1000*3;
		if (ApplicationContextSets.RUN_RIGHT_NOW == 1) {
			scheduler.scheduleAtFixedRate(new ReportDailyJob(),runTime,PERIOD_DAY,TimeUnit.MILLISECONDS);
		} else {
			scheduler.scheduleAtFixedRate(new ReportDailyJob(),first-current,PERIOD_DAY,TimeUnit.MILLISECONDS);
		}
		//log.info("庆忌者，其状若人，其长四寸，衣黄衣，冠黄冠，戴黄盖，乘小马，好急驰。");
		//log.info("以其名呼之，可使千里一日反报。此涸泽之精也。");
		logger.info("POSP签到服务启动完成。");*/
	}
	
	/*public static void signAll() {
		List<TblProxyHost> allHost = ApplicationContentSpringProvider.getInstance()
				.getProxyHostService().findAll();
		for (TblProxyHost h:allHost) {
			final Message m = createSignMessage(h.getFldOrgCode(),h.getFldTerminalNo(),h.getFldMerchantNo());
			if (m!=null) {
				ClientBootstrap cb = new ClientBootstrap(ApplicationContextCache.clientSocketChannelFactory);
				cb.getPipeline()
				.addLast(ApplicationContent.HANDLER_POSP_OUT_BOUND, new SignHandler());
				final ChannelFuture f = cb.connect(new InetSocketAddress(h.getFldHostIp(),h.getFldHostPort()));
				System.out.println("host-ip="+h.getFldHostIp()+";host-port="+h.getFldHostPort());
				f.addListener(new ChannelFutureListener() {
					public void operationComplete(ChannelFuture future)
							throws Exception {
						if (future.isSuccess()) {
							ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
							cb.writeBytes(m.toMessgeBytes());
							System.out.println(TypeConvert.bytes2HexString(m.toMessgeBytes()));
							f.getChannel().write(cb);
						} else {
						}
					}
				});
			}
		}
	}*/
	/**
	 * 
	 * @param acquiringCode 受理机构代码  （机构号）
	 * @param sendOrgCode 发送机构代码（终端号）
	 * @param merchantCode 商户号
	 * @return
	 */
	public static Message createSignMessage(String acquiringCode,String sendOrgCode
			,String merchantCode){
		/*try {
			Message m = MessageConverter.create(null,null,ApplicationContextCache.outputMesssageFormat,
					ApplicationContextCache.getTranFromOutput(ApplicationContent.MSG_TYPE_SIGN_ON_REQ,
							ApplicationContent.MSG_PROCESS_CODE_910000));
			m = MessageFactory.createMessageHead(m, ApplicationContextCache.outputMesssageFormat);
			//m.setBCDField(1, "0800");
			//m.setHead(TypeConvert.hexStringToByte("000060000900000100"));
			//m.setBCDField(1, value);
			//m.setBCDField(4, "990000");
			//m.setBCDField(12, "00001");
			//m.setBCDField(25, "009");
			m.setBCDField(32, acquiringCode.length()+acquiringCode);
			m.setBCDField(33, sendOrgCode.length()+sendOrgCode);
			m.setASCField(42, merchantCode);
			m.setBCDField(61, "0009"+DecodeUtil.bcd2Str((IDBuilder.getInstance().getBatchSequence()
					+ApplicationContextInit.NET_CODE).getBytes()));
			//System.out.println(TypeConvert.bytes2HexString(m.toMessgeBytes()));
			return m;
			
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return null;
	}
}
