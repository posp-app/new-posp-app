package com.redcard.posp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.cache.ApplicationContextCache;
import com.redcard.posp.common.DecodeUtil;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationException;
import com.redcard.posp.support.ApplicationKey;
import com.redcard.posp.support.ResultCode;

public class MessageFactory {
    private static Logger logger = LoggerFactory.getLogger(MessageFactory.class);

    public static Message getInputMessage(byte[] buf) throws Exception {
        return new Message(buf, ApplicationContextCache.inputMessageFormat);
    }

    public static Message getSupDataMessage(byte[] buf) throws Exception {
        return new SupDataMessage(buf, ApplicationContextCache.applicationMessageFormat.get(ApplicationKey.PROTOCOL_TYPE_SUPDATA));
    }

    /**
     * @param inputMessage 源消息
     * @param io           目的消息类型（确定目的消息格式）
     * @param type         消息类型
     * @return 目的消息
     * @throws Exception
     */
    public static Message createInputMessage(Message inputMessage, int io, String type, String process) throws Exception {
        MessageFormat targetMessageFormat = null;
        TransFormat targetTransFormat = null;
        if (io == ApplicationContent.MESSAGE_IO_I) {
            targetMessageFormat = ApplicationContextCache.inputMessageFormat;
            targetTransFormat = ApplicationContextCache.getTranFromInput(type, process);
        }
//		}
        if (targetTransFormat == null) {
            logger.error("未定义交易。");
            throw new ApplicationException(ResultCode.RESULT_CODE_95);
        }
        Message result = null;
        result = MessageConverter.create(targetMessageFormat, inputMessage, targetMessageFormat, targetTransFormat);
        return result;
    }

    public static Message createMessageHead(Message message, MessageFormat messageFormat) {
        byte[] head = message.head;
        int position = 0;
        for (FormatMetadata fm : messageFormat.getHead()) {
            if (fm.getName().equals(ApplicationKey.PACKAGE_LENGTH)) {

            } else {
                String value = fm.getDefaultValue();
                if (fm.getFormat().equals(ApplicationKey.FORMAT_BCD)) {
                    System.arraycopy(DecodeUtil.str2Bcd(value), 0, head, position, fm.getLength());
                } else if (fm.getFormat().equals(ApplicationKey.FORMAT_ASCII)) {
                    System.arraycopy(value.getBytes(), 0, head, position, fm.getLength());
                } else {
                    //throw new Exception("未知消息头格式。");
                }
            }
            position = position + fm.getLength();
        }
        return message;
    }

    public static FormatMetadata findInputMessageFormat(int io, int number) {
        MessageFormat fieldMessageFormat = null;
        if (io == ApplicationContent.MESSAGE_IO_I) {
            fieldMessageFormat = ApplicationContextCache.inputMessageFormat;
        }
        return fieldMessageFormat.getFields().get(number - 1);
    }
}
