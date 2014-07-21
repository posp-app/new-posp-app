package com.redcard.posp.route;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.model.TblMerchant;
import com.redcard.posp.manage.model.TblMerchantGroup;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.Message;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationKey;
import com.redcard.posp.support.Result;
import com.redcard.posp.support.ResultCode;

/**
 * 
 * 消息路由
 * 
 * @project posp_server
 * @description 
 * 1、卡规则获取一个主机地址
 * 2、判断这个主机地址是否在这个商户对应的主机地址集合里边。
 * 3、在，继续交易，不在，返回错误。
 * 
 * 
 * 1、解析得到个域，得到商户号，终端号，交易得到卡号
 * 2、查商户状态，启用或停用，查终端状态，启用或停用
 * 3、商户号查集团编码，查询是否有交易种类
 * 4、集团编码获取转发主机编码集合
 * 5、卡规则获取一个主机
 * 6、判断卡主机是否在集团编码获取的转发主机里边。
 * @author cuijunrong(cuijunrong@hotmail.com)
 * @date 2014-4-10
 */
public class MessageRoute implements IRouter {

	private static Logger logger = LoggerFactory.getLogger(MessageRoute.class);

	public Result route(Message message){
		Result r = new Result(ResultCode.POSP_RESULT_CODE_9998);
		String merchant = message.getCardAcceptorIdentification();
		String terminalNO = message.getTerminalIdentification();
		String messageType = message.getMSGType();
		String process = message.getTransactionCode();
		TblMerchantGroup merchantGroup = ManageCacheService.findMerchantGroup(merchant);
		if (merchantGroup == null) {
			logger.error("找不到商户["+merchant+"]对应的商户集合(未知商户号)");
			r.setResultCode(ResultCode.RESULT_CODE_53);
			return r;
		}
		//查询商户和终端状态
		TblMerchant m = ManageCacheService.findMerchant(merchantGroup,merchant);
		if (m == null) {
			logger.error("找不到商户["+merchant+"]");
			r.setResultCode (ResultCode.RESULT_CODE_53);
			return r;
		}
		if (m.getFldStatus() != ApplicationContent.STATUS_USING) {
			logger.error("商户["+merchant+"]状态不是启用状态");
			r.setResultCode (ResultCode.RESULT_CODE_59);
			return r;
		}
		//如果是签到，不校验终端，因为没有终端域
		if (message.getMSGType().equals(ApplicationContent.MSG_TYPE_SIGN_ON_REQ)) {
			Map<String,String> para = new Hashtable<String,String>();
			para.put(ApplicationKey.MESSAGE_TYPE, message.getMSGType());
			r.setResultCode(ResultCode.RESULT_CODE_00);
			r.setObject(para);
			return r;
		}
		
		//@TODO 处理普天卡，长度为16位
        if(StringUtils.isEmpty(message.getAccount())){
            logger.error("卡号信息不能为空");
            r.setResultCode(ResultCode.RESULT_CODE_63);
            return r;
        }

        String cardNO = message.getAccount();

        if(message.getAccount().length()>19){
            cardNO = message.getAccount().substring(0,19);
        }
		TblMerchantPos posNO = ManageCacheService.findPos(m,terminalNO);
		if (posNO == null) {
			logger.error("商户["+m.getFldCode()+"]下没有终端["+terminalNO+"]");
			r.setResultCode(ResultCode.RESULT_CODE_54);
			return r;
		}
		if (posNO.getFldStatus() != ApplicationContent.STATUS_USING) {
			logger.error("该终端["+terminalNO+"]状态不是启用状态");
			r.setResultCode(ResultCode.RESULT_CODE_54);
			return r;
		}
		boolean allowTrade = ManageCacheService.allowTrade(merchantGroup,messageType,process);
		if (!allowTrade){
			logger.error("该商户["+merchant+"]不支持该类型的交易["+messageType+":"+process+"]");
			r.setResultCode(ResultCode.RESULT_CODE_79);
			return r;
		}
		TblProxyHost proxyHost = ManageCacheService.getCardHost(merchantGroup,m,cardNO);
		if (proxyHost == null) {
			logger.error("卡路由错误;找不到路由的主机;商户号["+merchant+"];卡号["+cardNO+"]");	
			r.setResultCode(ResultCode.RESULT_CODE_60);	
			return r;
		}
		Map<String,Object> para = new HashMap<String,Object>();
		para.put(ApplicationKey.IP, proxyHost.getFldHostIp());
		para.put(ApplicationKey.PORT, Integer.toString(proxyHost.getFldHostPort()));
		para.put(ApplicationKey.MESSAGE_TYPE, message.getMSGType());
		para.put(ApplicationKey.PROCCESS, message.getTransactionCode());
		para.put(ApplicationKey.PROTOCOL_TYPE, proxyHost.getFldProtocolType());
		para.put(ApplicationKey.PIN_KEY, proxyHost.getFldPinKey());
		para.put(ApplicationKey.MAC_KEY, proxyHost.getFldMacKey());
        para.put(ApplicationKey.PROXY_MODE,proxyHost.getFldSignMode().toString());
        para.put(ApplicationKey.PROXY_SIGN_DATE, proxyHost.getFldSignDate());
        para.put(ApplicationKey.HOST_MASTER_KEY, proxyHost.getFldHostMasterKey());
		r.setResultCode(ResultCode.RESULT_CODE_00);
		r.setObject(para);
		return r;
	}
}
