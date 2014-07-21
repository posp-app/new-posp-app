package com.redcard.posp.handler.message;

import com.redcard.posp.cache.ApplicationContextCache;
import com.redcard.posp.handler.DefaultMessageHandler;
import com.redcard.posp.handler.SignHandler;
import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.MessageFactory;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;
import com.redcard.posp.support.ApplicationKey;
import com.redcard.posp.support.ResultCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.logging.resources.logging_es;

import java.net.InetSocketAddress;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @project posp_server
 * @description 签到处理器，用于针对机构签到的代理主机，在所有交易之前判断是否已经签到过，如果没有签到，则需要进行签到操作。
 * 在处理链的第3位
 * @date 2014-4-22
 */
public class MessageValiditySignHandler implements IMessageHandler {

    private static Logger logger = LoggerFactory.getLogger(MessageValiditySignHandler.class);

    private boolean isContinue = true;

    private Map<String, Object> param = null;

    public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {

        if (msg.getMSGType().equals(ApplicationContent.MSG_TYPE_SIGN_ON_REQ)){
            return;
        }

        String ip = (String) param.get(ApplicationKey.IP);
        Integer port = Integer.parseInt((String) param.get(ApplicationKey.PORT));

        logger.info("开始检查路由IP [" + param.get(ApplicationKey.IP) + "]:[" + param.get(ApplicationKey.PORT) + "] 是否已经签到");
        if (param.get(ApplicationKey.PROXY_MODE) != null
                && ApplicationKey.PROXY_SIGN_MODE_ORG.toString().equals(param.get(ApplicationKey.PROXY_MODE))) {

        	TblProxyHost queryObject = new TblProxyHost();
            queryObject.setFldHostPort(port);
            queryObject.setFldHostIp(ip);
            queryObject.setFldProtocolType(null);
            List<TblProxyHost> tblProxyHostList = ApplicationContentSpringProvider.getInstance().getProxyHostService().getTblProxyHostListByObj(queryObject);

            if (tblProxyHostList == null && tblProxyHostList.size() == 0) {
            	logger.info("路由IP [" + param.get(ApplicationKey.IP) + "]:[" + param.get(ApplicationKey.PORT) + "] 不存在 ");
                isContinue = false;
                DefaultMessageHandler.returnOrgMessage(msg, inBoundChannel, ResultCode.RESULT_CODE_60.getCode());
                return;
            }
        	
            Date latestSignDate = tblProxyHostList.get(0).getFldSignDate();
            String pinKey = tblProxyHostList.get(0).getFldPinKey();
            String macKey = tblProxyHostList.get(0).getFldMacKey();

            if (latestSignDate != null
                    && DateUtils.isSameDay(new Date(), latestSignDate)
                    && StringUtils.isNotBlank(pinKey)
                    && StringUtils.isNotBlank(macKey)) {
                logger.info("确认路由IP [" + param.get(ApplicationKey.IP) + "]:[" + param.get(ApplicationKey.PORT) + "] 已经签到, 签到时间 " + latestSignDate + ", PINKEY:[" + pinKey + "], MACKEY:[" + macKey + "]");
                return;
            }

            logger.info("路由尚未签到，开始签到");
            try {
                final Message m = MessageFactory.createInputMessage(msg, ApplicationContent.MESSAGE_IO_I
                        , ApplicationContent.MSG_TYPE_SIGN_ON_REQ, ApplicationContent.MSG_PROCESS_CODE_910000);
                ClientBootstrap clientBootstrap = new ClientBootstrap(ApplicationContextCache.clientSocketChannelFactory);
                clientBootstrap.setOption("connectTimeoutMillis", 5000);
                AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                SignHandler signHandler = new SignHandler();
                signHandler.setAtomicBoolean(atomicBoolean);
                clientBootstrap.getPipeline().addLast(ApplicationContent.HANDLER_POSP_OUT_BOUND, signHandler);

                final ChannelFuture f = clientBootstrap.connect(new InetSocketAddress(ip, port));
                f.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future)
                            throws Exception {
                        if (future.isSuccess()) {
                            ChannelBuffer cb = ChannelBuffers.dynamicBuffer();
                            logger.info("发送签到消息,域值：\r\n" + m.to8583FormatString());
                            cb.writeBytes(m.toMessgeBytes());
                            logger.info("发送签到消息, bytes=[" + ChannelBuffers.hexDump(cb) + "]");
                            f.getChannel().write(cb);
                        } else {

                        }
                    }
                });

                logger.error("==================");
                long start = System.currentTimeMillis();
                while ((System.currentTimeMillis()-start)<5000 && !atomicBoolean.get()) {
                    Thread.currentThread().sleep(10);
                }
                logger.error("==================");

                if(!atomicBoolean.get()){
                    isContinue = false;
                    DefaultMessageHandler.returnOrgMessage(msg, inBoundChannel, ResultCode.RESULT_CODE_93.getCode());
                    return;
                }

                param.put(ApplicationKey.PIN_KEY, tblProxyHostList.get(0).getFldPinKey());
                param.put(ApplicationKey.MAC_KEY, tblProxyHostList.get(0).getFldMacKey());
                param.put(ApplicationKey.PROXY_SIGN_DATE, tblProxyHostList.get(0).getFldSignDate());

                isContinue = true;
            } catch (Exception e) {
                e.printStackTrace();
                isContinue = false;
                DefaultMessageHandler.returnOrgMessage(msg, inBoundChannel, ResultCode.RESULT_CODE_90.getCode());
            }finally {
                logger.info("结束签到");
            }

        }

        logger.info("结束检查路由IP [" + param.get(ApplicationKey.IP) + "]:[" + param.get(ApplicationKey.PORT) + "]");

    }

    public boolean isContinue() {
        return isContinue;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        if (this.param != null) {
            this.param.putAll(param);
        } else {
            this.param = param;
        }
    }


}
