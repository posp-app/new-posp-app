package com.redcard.posp.support;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.manage.service.ITblCardService;
import com.redcard.posp.manage.service.ITblMerchantGroupService;
import com.redcard.posp.manage.service.ITblMerchantGroupTradeTypeService;
import com.redcard.posp.manage.service.ITblMerchantPosService;
import com.redcard.posp.manage.service.ITblMerchantService;
import com.redcard.posp.manage.service.ITblMerchantTransformHostLinkService;
import com.redcard.posp.manage.service.ITblProxyHostService;
import com.redcard.posp.manage.service.ITblTransactionMessageService;
import com.redcard.posp.manage.service.ITblTransformCardService;
import com.redcard.posp.manage.service.impl.MessageServiceImpl;

public class ApplicationContentSpringProvider {

	private ApplicationContext applicationContext;

	private static ApplicationContentSpringProvider provider = new ApplicationContentSpringProvider();

	private static Object lock = new Object();
	
	private static final String SPRING_CONTEXT = "applicationContext.xml";

	private ApplicationContentSpringProvider() {
		init();
	}

	private void init() throws ExceptionInInitializerError {
		applicationContext = new FileSystemXmlApplicationContext(ApplicationContextInit.MESSAGE_FORMAT_PATH + "\\"
				+ SPRING_CONTEXT);
	}

	public static ApplicationContentSpringProvider getInstance() {
		if (provider != null) {
			return provider;
		}
		synchronized (lock) {
			if (provider == null) {
				provider = new ApplicationContentSpringProvider();
			}
		}
		return provider;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}
	
	public ITblTransactionMessageService getTransactionMessageService() {
		return (ITblTransactionMessageService)getBean("tblTransactionMessageService");
	}
	public ITblMerchantGroupService getMerchantGroupService() {
		return (ITblMerchantGroupService)getBean("tblMerchantGroupService");
	}
	public ITblMerchantGroupTradeTypeService getMerchantGroupTradeTypeService() {
		return (ITblMerchantGroupTradeTypeService)getBean("tblMerchantGroupTradeTypeService");
	}
	public ITblMerchantTransformHostLinkService getMerchantTransformHostLinkService() {
		return (ITblMerchantTransformHostLinkService)getBean("tblMerchantTransformHostLinkService");
	}
	public ITblMerchantService getMerchantService() {
		return (ITblMerchantService)getBean("tblMerchantService");
	}
	public ITblMerchantPosService getMerchantPosService() {
		return (ITblMerchantPosService)getBean("tblMerchantPosService");
	}
	public ITblProxyHostService getProxyHostService() {
		return (ITblProxyHostService)getBean("tblProxyHostService");
	}
	public ITblTransformCardService getTransforCardService() {
		return (ITblTransformCardService)getBean("tblTransformCardService");
	}
	public ITblCardService getCardService() {
		return (ITblCardService)getBean("tblCardService");
	}
	public MessageServiceImpl getMessageService() {
		return (MessageServiceImpl)getBean("messageService");
	}
	
	public static synchronized String getSystemSequence(String type) {
		return type+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}
	
	public static String doMaskCardNO(String cardNO) {
		if (ApplicationContextInit.needMaskCardNO.equals(ApplicationKey.USE_Y)) {
			return CommonUtil.maskCardNO(cardNO, ApplicationContextInit.maskLength);
		}
		return cardNO;
	}
}
