package com.redcard.posp.handler.message;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.common.MacUtil;
import com.redcard.posp.common.TypeConvert;
import com.redcard.posp.handler.DefaultMessageHandler;
import com.redcard.posp.handler.secret.redcard.SecretKeyFactory;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.FormatMetadata;
import com.redcard.posp.message.Message;
import com.redcard.posp.message.MessageFactory;
import com.redcard.posp.route.IRouter;
import com.redcard.posp.route.MessageRoute;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;
import com.redcard.posp.support.ApplicationKey;
import com.redcard.posp.support.Result;
import com.redcard.posp.support.ResultCode;

/**
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @project posp_server
 * @description 消息的合法性校验
 * 位于处理链的第二位
 * @date 2014-4-22
 */
public class MessageValidityHandler implements IMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(MessageValidityHandler.class);

    private boolean isContinue = true;

    private IRouter router = new MessageRoute();

    private Map<String, Object> param = null;

    @SuppressWarnings("unchecked")
    public void handler(Message msg, Channel inBoundChannel, ChannelBuffer cb) {

        Result result = router.route(msg);
        if (!ResultCode.RESULT_CODE_00.getCode().equals(result.getCode())) {
            DefaultMessageHandler.returnOrgMessage(msg, inBoundChannel, result.getCode());
            isContinue = false;
            return;
        }
        param = (Map<String, Object>) result.getObject();
        //下载主密钥
        if (msg.getMSGType().equals(ApplicationContent.MSG_TYPE_SIGN_ON_REQ) &&
                ApplicationContent.MSG_PROCESS_CODE_900000.equals(msg.getTransactionCode())) {
            downloadKey(msg, inBoundChannel, result);
            return;
        }

        //如果是签到，那么直接返回签到信息。
        if (msg.getMSGType().equals(ApplicationContent.MSG_TYPE_SIGN_ON_REQ) &&
                ApplicationContent.MSG_PROCESS_CODE_910000.equals(msg.getTransactionCode())) {
            signIn(msg, inBoundChannel, result);
            return;
        } else {
            String mac = DefaultMessageHandler.getMAC(msg);
            if (StringUtils.isBlank(mac) || !mac.substring(0, 16).equals(msg.getMAC().substring(0, 16))) {
                //mac校验错误，直接返回
                logger.info("MSG type=[" + msg.getMSGType() + "]交易MAC校验错误。计算MAC[" + mac + "]");
                result.setResultCode(ResultCode.RESULT_CODE_58);
                isContinue = false;
                DefaultMessageHandler.returnOrgMessage(msg, inBoundChannel, result.getCode());
                return;

            } else {
                logger.info("MSG type=[" + msg.getMSGType() + "]交易MAC校验成功。");
            }
        }
    }

    public boolean isContinue() {
        return isContinue;
    }

    public void downloadKey(Message msg, Channel inBoundChannel, Result result) {

        Message m = null;
        try {
            m = MessageFactory.createInputMessage(msg, ApplicationContent.MESSAGE_IO_I
                    , ApplicationContent.MSG_TYPE_SIGN_ON_RESP, ApplicationContent.MSG_PROCESS_CODE_900000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (m == null) {
            isContinue = false;
            return;
        }
        FormatMetadata formate = MessageFactory.findInputMessageFormat(ApplicationContent.MESSAGE_IO_I, 39);

        if (ApplicationKey.FORMAT_BCD.equals(formate.getFormat())) {
            m.setBCDField(39, result.getCode());
        } else {
            m.setASCField(39, result.getCode());
        }

        //将密钥信息保存到数据库以及内存
        if (ResultCode.RESULT_CODE_00.getCode().equals(result.getCode())) {
            TblMerchantPos pos = new TblMerchantPos();
            pos.setFldTerminalNo(msg.getTerminalIdentification());
            pos.setFldMerchantCode(msg.getCardAcceptorIdentification());
            pos.setFldMasterKey(m.get63Field().substring(4, 20));
            pos.setFldCPUKey(m.get63Field().substring(28, 60));
            ApplicationContentSpringProvider.getInstance().getMerchantPosService()
                    .updateKey(pos);
            TblMerchantPos p = ManageCacheService.findPos(pos.getFldTerminalNo());
            if (p != null) {
                p.setFldMasterKey(m.get63Field().substring(4, 20));
                p.setFldCPUKey(m.get63Field().substring(28, 60));
            }
        }
        byte[] allBytes = m.toMessgeBytes();
        logger.info("返回给POS消息域值：\r\n" + m.to8583FormatString());
        logger.debug("写入网络 bytes=[" + TypeConvert.bytes2HexString(allBytes) + "]");
        inBoundChannel.setReadable(true);
        ChannelBuffer retCB = ChannelBuffers.dynamicBuffer();
        retCB.writeBytes(allBytes);
        inBoundChannel.write(retCB);

        isContinue = false;
        return;
    }

    public void signIn(Message msg, Channel inBoundChannel, Result result) {

        Message m = null;
        try {
            m = MessageFactory.createInputMessage(msg, ApplicationContent.MESSAGE_IO_I
                    , ApplicationContent.MSG_TYPE_SIGN_ON_RESP, ApplicationContent.MSG_PROCESS_CODE_910000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (m == null) {
            isContinue = false;
            return;
        }
        FormatMetadata formate = MessageFactory.findInputMessageFormat(ApplicationContent.MESSAGE_IO_I, 39);

        if (ApplicationKey.FORMAT_BCD.equals(formate.getFormat())) {
            m.setBCDField(39, result.getCode());
        } else {
            m.setASCField(39, result.getCode());
        }
        //签到成功，将密钥信息保存到数据库以及内存
        if (ResultCode.RESULT_CODE_00.getCode().equals(result.getCode())) {
            TblMerchantPos pos = new TblMerchantPos();
            pos.setFldTerminalNo(msg.getTerminalIdentification());
            pos.setFldMerchantCode(msg.getCardAcceptorIdentification());
            pos.setFldMacKey(m.getMacKey());
            pos.setFldPinKey(m.getPinKey());
            ApplicationContentSpringProvider.getInstance().getMerchantPosService()
                    .updateKey(pos);
            TblMerchantPos p = ManageCacheService.findPos(pos.getFldTerminalNo());
            if (p != null) {
                p.setFldMacKey(m.getMacKey());
                p.setFldPinKey(m.getPinKey());
            }
        }

        byte[] allBytes = m.toMessgeBytes();
        logger.info("返回给POS消息域值：\r\n" + m.to8583FormatString());
        logger.debug("写入网络 bytes=[" + TypeConvert.bytes2HexString(allBytes) + "]");
        inBoundChannel.setReadable(true);
        ChannelBuffer retCB = ChannelBuffers.dynamicBuffer();
        retCB.writeBytes(allBytes);
        inBoundChannel.write(retCB);

        isContinue = false;
        return;

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
