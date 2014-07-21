package com.redcard.posp.handler;

import com.redcard.posp.common.MacUtil;
import com.redcard.posp.support.ApplicationKey;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.MessageFactory;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SignHandler extends SimpleChannelUpstreamHandler {

    private static Logger logger = LoggerFactory.getLogger(SignHandler.class);

    private AtomicBoolean atomicBoolean = null;

    public AtomicBoolean getAtomicBoolean() {
        return atomicBoolean;
    }

    public void setAtomicBoolean(AtomicBoolean atomicBoolean) {
        this.atomicBoolean = atomicBoolean;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
        ChannelBuffer cb = (ChannelBuffer) e.getMessage();
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.getChannel().getRemoteAddress();
        logger.info("接收到来自[" + socketAddress + "]签到消息, bytes=[" + ChannelBuffers.hexDump(cb) + "]");
        Message msg = MessageFactory.getInputMessage(cb.array());
        logger.info("接收到签到消息,域值：\r\n" + msg.to8583FormatString());
        if (ApplicationContent.MSG_TYPE_SIGN_ON_RESP.equals(msg.getMSGType())) {
        	String ipAddress = socketAddress.toString();
        	//不能使用socketAddress.getHostName(),不知道为什么会等待5秒左右
        	String ip = ipAddress.split(":")[0].substring(1);
        	Integer port = Integer.valueOf(ipAddress.split(":")[1]);
            TblProxyHost queryObject = new TblProxyHost();
            queryObject.setFldHostPort(port);
            queryObject.setFldHostIp(ip);
            queryObject.setFldProtocolType(null);
            logger.info("444444444444444444444");

            List<TblProxyHost> tblProxyHostList = ApplicationContentSpringProvider.getInstance().getProxyHostService().getTblProxyHostListByObj(queryObject);

            
            if (tblProxyHostList != null && tblProxyHostList.size() > 0) {
                //签到，更新pinKey 和macKey
                TblProxyHost tblProxyHost = tblProxyHostList.get(0);
                if(StringUtils.isBlank(tblProxyHost.getFldHostMasterKey())){
                    logger.error("转发主机["+socketAddress+"] 的机构密钥为空");
                    return;
                }

                if(tblProxyHost.getFldHostMasterKey().length()!=16 && tblProxyHost.getFldHostMasterKey().length()!=32){
                    logger.error("机构密钥长度错误，必须为16或32字节，当前长度为"+tblProxyHost.getFldHostMasterKey().length());
                    return;
                }

                String pinKey = null;
                String macKey = null;

                if(tblProxyHost.getFldHostMasterKey().length()==16){
                    pinKey = MacUtil.DES_1(msg.getPinKey(),tblProxyHost.getFldHostMasterKey() , 1);
                    macKey = MacUtil.DES_1(msg.getMacKey(),tblProxyHost.getFldHostMasterKey() , 1);
                }else{
                    pinKey = MacUtil.DES_3(msg.getPinKey(), tblProxyHost.getFldHostMasterKey(), 1);
                    macKey = MacUtil.DES_3(msg.getMacKey(), tblProxyHost.getFldHostMasterKey(), 1);
                }

                TblProxyHost save = new TblProxyHost();
                save.setFldHostCode(tblProxyHost.getFldHostCode());
                save.setFldPinKey(pinKey);
                save.setFldMacKey(macKey);
                save.setFldSignDate(new Date());
                save.setFldOperateDate(new Date());
                ApplicationContentSpringProvider.getInstance().getProxyHostService().update(save);
                //更新缓存中的转发主机的key
                ManageCacheService.updateProxyHostKeyByCode(save);
                getAtomicBoolean().set(true);
            }
        } else {
            logger.error("转发主机["+socketAddress+"]签到失败");
        }

        e.getChannel().close();
        e.getChannel().disconnect();
    }

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
        POSPInboundHandler.closeOnFlush(e.getChannel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
            throws Exception {
        logger.error(e.getCause().getMessage());
        e.getCause().printStackTrace();
        POSPInboundHandler.closeOnFlush(e.getChannel());
    }
}
