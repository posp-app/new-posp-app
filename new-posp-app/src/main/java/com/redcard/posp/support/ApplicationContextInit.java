package com.redcard.posp.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.cache.ApplicationContextCache;
import com.redcard.posp.manage.model.TblMerchant;
import com.redcard.posp.manage.model.TblMerchantGroup;
import com.redcard.posp.manage.model.TblMerchantGroupTradeType;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.model.TblMerchantTransformHostLink;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.message.FormatMetadata;
import com.redcard.posp.message.MessageFormat;
import com.redcard.posp.message.TransFormat;

public class ApplicationContextInit {

    private static Logger logger = LoggerFactory.getLogger(ApplicationContextInit.class);

    private static ApplicationContextInit instance;
    private static Object lock = new Object();

    //@TODO 运行时需要改为 resources
//	public static final String MESSAGE_FORMAT_PATH = "src\\main\\resources";
    public static final String MESSAGE_FORMAT_PATH = "resources";
    public static final String CONTEXT_FILE = "pospContext.xml";
    public static final String ID_CACHE_FILE = "IDBuilder.properties";

    public static String nodeName = "0000";

    public static int receiverPort = 8088;

    public static int workerCount = 100;

    public static String upLinkServerIP = "127.0.0.1";
    public static int upLinkServerPort = 7002;

    public static String needMaskCardNO = ApplicationKey.USE_N;
    public static int maskLength = 4;

    public static String masterKeyOne = "1111111111111111";
    public static String masterKeyTwo = "0000000000000000";
    public static int secretLength = 32;
    public static String transformation = "DES/CBC/NoPadding";
    public static String checkValueKey = "0000000000000000";
    public static String terminalMasterKey = "11111111111111112222222222222222";
    public static String targetMasterKey = "11111111111111112222222222222222";
    public static String NET_CODE = "001";

    public static String SUPDATA_OPERATOR = "111111";
    public static String SUPDATA_OPERATOR_PASSWORD = "123456";

    public static String defaultHostCode = "3";

    public static String keyCPU = "00000000000000000000000000000000";

    /**
     * 系统初始化
     */
    private ApplicationContextInit() {
        //载入消息格式
        Result r = loadMessageFormat();
        if (r.getCode().equals(ResultCode.POSP_RESULT_CODE_0000.getCode())) {
            logger.info("载入消息格式成功");
        } else {
            logger.error("载入消息格式失败;" + r.getMessage());
        }
        //系统配置(包括绑定接收消息和发送消息)载入
        r = systemLoad();
        if (r.getCode().equals(ResultCode.POSP_RESULT_CODE_0000.getCode())) {
            logger.info("系统配置(包括绑定接收消息和发送消息)载入成功");
        } else {
            logger.error("载入配置失败;" + r.getMessage());
        }
        r = loadSpringContext();
        if (r.getCode().equals(ResultCode.POSP_RESULT_CODE_0000.getCode())) {
            logger.info("Spring 环境  载入成功");
        } else {
            logger.error("Spring 环境  载入失败;" + r.getMessage());
        }
        try {
            r = loadCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (r.getCode().equals(ResultCode.POSP_RESULT_CODE_0000.getCode())) {
            logger.info("Cache 载入成功");
        } else {
            logger.error("Cache 载入失败;" + r.getMessage());
        }
    }

    public static ApplicationContextInit getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ApplicationContextInit();
                }
            }
        }
        return instance;
    }

    private Result loadMessageFormat() {
        Result r = new Result(ResultCode.POSP_RESULT_CODE_9998.getCode(),
                ResultCode.POSP_RESULT_CODE_9998.getMessage(), null);
        File path = new File(MESSAGE_FORMAT_PATH);
        logger.info("从目录获取消息格式文件，目录=[" + MESSAGE_FORMAT_PATH + "]");
        File[] fs = path.listFiles();
        if (fs == null || fs.length < 1) {
            logger.info("从目录找不到消息格式文件。");
            r.setValue(ResultCode.POSP_RESULT_CODE_9997.getCode(),
                    ResultCode.POSP_RESULT_CODE_9997.getMessage() + ";从目录找不到消息格式文件", null);
            return r;
        }
        for (int i = 0; i < fs.length; i++) {
            String fileName = fs[i].getName();
            if (fileName.endsWith(ApplicationKey.MESSAGE_FORMAT + ".xml")) {
                logger.info("开始载入消息格式文件[" + fileName + "]");
                InputStream in = null;
                SAXReader reader = new SAXReader();
                Document doc = null;
                try {
                    in = new FileInputStream(new File(MESSAGE_FORMAT_PATH + "\\" + fileName));
                    doc = reader.read(in);
                    Element docRoot = doc.getRootElement();
                    MessageFormat mf = new MessageFormat();
                    mf.setName(docRoot.attributeValue(ApplicationKey.NAME));
                    //head
                    Element ele = docRoot.element(ApplicationKey.HEAD);
                    List<FormatMetadata> heads = new ArrayList<FormatMetadata>();
                    mf.setHead(heads);
                    Element e = null;
                    Iterator ite = ele.elementIterator();
                    while (ite.hasNext()) {
                        e = (Element) ite.next();
                        FormatMetadata fm = new FormatMetadata();
                        fm.setName(e.getName());
                        fm.setLength(Integer.parseInt(e.attributeValue(ApplicationKey.LENGTH)));
                        fm.setFormat(e.attributeValue(ApplicationKey.FORMAT));
                        fm.setDefaultValue(e.attributeValue(ApplicationKey.DEFAULT_VALUE));
                        heads.add(fm);
                    }
                    // bit-map
                    e = docRoot.element(ApplicationKey.BIT_MAP);
                    FormatMetadata bm = new FormatMetadata();
                    bm.setName(e.getName());
                    bm.setLength(Integer.parseInt(e.attributeValue(ApplicationKey.LENGTH)));
                    bm.setFormat(e.attributeValue(ApplicationKey.FORMAT));
                    mf.setMap(bm);
                    List<FormatMetadata> fields = new ArrayList<FormatMetadata>();
                    //解析域
                    e = docRoot.element(ApplicationKey.FIELDS);
                    ite = e.elementIterator();
                    while (ite.hasNext()) {
                        e = (Element) ite.next();
                        FormatMetadata fm = new FormatMetadata();
                        fm.setNumber(Integer.parseInt(e.attributeValue(ApplicationKey.NUMBER)));
                        fm.setUse(e.attributeValue(ApplicationKey.USE));
                        String length = e.attributeValue(ApplicationKey.LENGTH);
                        if (length == null || length.equals("")) {
                            fm.setLength(0);
                        } else {
                            fm.setLength(Integer.parseInt(e.attributeValue(ApplicationKey.LENGTH)));
                        }
                        fm.setFormat(e.attributeValue(ApplicationKey.FORMAT));
                        fm.setLlFormat(e.attributeValue(ApplicationKey.LL_FORMAT));
                        fm.setVarFormat(e.attributeValue(ApplicationKey.VAR_FORMAT));
                        fm.setCharset(e.attributeValue(ApplicationKey.CHARSET));
                        fm.setDirection(e.attributeValue(ApplicationKey.DIRECTION));
                        fm.setName(e.getName() + "_" + fm.getNumber());
                        fields.add(fm);
                    }
                    mf.setFields(fields);

                    //载入各交易配置
                    //解析域
                    e = docRoot.element(ApplicationKey.TRANS);
                    List<TransFormat> transFormatList = new ArrayList<TransFormat>();
                    if (e != null) {
                        ite = e.elementIterator();
                        while (ite.hasNext()) {
                            e = (Element) ite.next();
                            TransFormat transFormat = new TransFormat();
                            transFormatList.add(transFormat);
                            transFormat.setType(e
                                    .attributeValue(ApplicationKey.TYPE));
                            transFormat.setProcess(e
                                    .attributeValue(ApplicationKey.PROCCESS));
                            Iterator ite2 = e.elementIterator();
                            while (ite2.hasNext()) {
                                e = (Element) ite2.next();
                                FormatMetadata fm = new FormatMetadata();
                                fm.setNumber(Integer.parseInt(e
                                        .attributeValue(ApplicationKey.NUMBER)));
                                fm.setSourceType(e
                                        .attributeValue(ApplicationKey.SOURCE_TYPE));
                                fm.setRequest(e
                                        .attributeValue(ApplicationKey.REQUEST));
                                fm.setDefaultValue(e
                                        .attributeValue(ApplicationKey.DEFAULT_VALUE));
                                transFormat.getFields().add(fm);
                            }
                        }
                    }
                    ApplicationContextCache.applicationTransFormat.put(mf.getName(), transFormatList);
                    ApplicationContextCache.applicationMessageFormat.put(mf.getName(), mf);
                } catch (FileNotFoundException e) {
                    logger.error("载入消息格式文件[" + fileName + "]失败。" + e.getMessage());
                    e.printStackTrace();
                    r.setValue(ResultCode.POSP_RESULT_CODE_9997.getCode(),
                            ResultCode.POSP_RESULT_CODE_9997.getMessage() + ";找不到文件", null);
                    return r;
                } catch (DocumentException e) {
                    logger.error("载入消息格式文件[" + fileName + "]失败。" + e.getMessage());
                    e.printStackTrace();
                    r.setValue(ResultCode.POSP_RESULT_CODE_9997.getCode(),
                            ResultCode.POSP_RESULT_CODE_9997.getMessage() + ";解析文件出错", null);
                    return r;
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                logger.info("载入消息格式文件[" + fileName + "]成功");
            }
        }
        if (ApplicationContextCache.applicationMessageFormat == null || ApplicationContextCache.applicationMessageFormat.size() < 1) {
            r.setValue(ResultCode.POSP_RESULT_CODE_9997.getCode(),
                    ResultCode.POSP_RESULT_CODE_9997.getMessage() + ";必须载入至少两个消息格式作为接收端和发送端消息", null);
            return r;
        }
        r.setValue(ResultCode.POSP_RESULT_CODE_0000.getCode(),
                ResultCode.POSP_RESULT_CODE_0000.getMessage(), null);
        return r;
    }

    private Result systemLoad() {
        Result r = new Result(ResultCode.POSP_RESULT_CODE_9998.getCode(),
                ResultCode.POSP_RESULT_CODE_9998.getMessage(), null);
        logger.info("从目录获取应用配置文件，目录=[" + MESSAGE_FORMAT_PATH + "]");
        InputStream in = null;
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            in = new FileInputStream(new File(MESSAGE_FORMAT_PATH + "\\"
                    + CONTEXT_FILE));
            doc = reader.read(in);
            Element docRoot = doc.getRootElement();
            Element ele = docRoot.element(ApplicationKey.INPUT_MESSAGE_FORMAT);
            String input = ele.attributeValue(ApplicationKey.NAME);
            ApplicationContextCache.inputMessageFormat = ApplicationContextCache.applicationMessageFormat.get(input);
            ApplicationContextCache.inputTrans = ApplicationContextCache.applicationTransFormat.get(input);
            if (ApplicationContextCache.inputMessageFormat == null) {
                r.setValue(ResultCode.POSP_RESULT_CODE_9999.getCode(),
                        ResultCode.POSP_RESULT_CODE_9999.getMessage() + ";找不到接收端消息格式", null);
                return r;
            }
            ele = docRoot.element(ApplicationKey.OUTS);
            Iterator ite = ele.elementIterator();
            while (ite.hasNext()) {
                ele = (Element) ite.next();
                String name = ele.attributeValue(ApplicationKey.NAME);
                String className = ele.attributeValue(ApplicationKey.CLASS_NAME);
                OutMessageConfig omc = new OutMessageConfig();
                omc.setName(name);
                omc.setClassName(className);
                ApplicationContextCache.allOutputMessageFormat.put(omc, ApplicationContextCache.applicationMessageFormat.get(name));
                ApplicationContextCache.allOutputTrans.put(omc, ApplicationContextCache.applicationTransFormat.get(name));
            }
            if (ApplicationContextCache.allOutputMessageFormat == null
                    || ApplicationContextCache.allOutputMessageFormat.size() < 1) {
                r.setValue(ResultCode.POSP_RESULT_CODE_9999.getCode(),
                        ResultCode.POSP_RESULT_CODE_9999.getMessage() + ";找不到发送端消息格式", null);
                return r;
            }
            ele = docRoot.element(ApplicationKey.SERVER);
            if (ele != null) {
                ApplicationContextInit.receiverPort = Integer.parseInt(ele.attributeValue(ApplicationKey.RECEIVER_PORT));
                ApplicationContextInit.needMaskCardNO = ele.attributeValue(ApplicationKey.MASK_CARD_NO);
            }
            ele = docRoot.element(ApplicationKey.NODE);
            if (ele != null) {
                ApplicationContextInit.nodeName = ele.attributeValue(ApplicationKey.NAME);
            }
            ele = docRoot.element(ApplicationKey.DEFAULT_HOST_CODE);
            if (ele != null) {
                ApplicationContextInit.defaultHostCode = ele.attributeValue(ApplicationKey.CODE);
            }
            ele = docRoot.element(ApplicationKey.KEY);
            if (ele != null) {
                ApplicationContextInit.keyCPU = ele.attributeValue(ApplicationKey.CPU);
            }
        } catch (FileNotFoundException e) {
            logger.error("载入应用配置文件[" + MESSAGE_FORMAT_PATH + "\\"
                    + CONTEXT_FILE + "]失败。" + e.getMessage());
            e.printStackTrace();
            r.setValue(ResultCode.POSP_RESULT_CODE_9997.getCode(),
                    ResultCode.POSP_RESULT_CODE_9997.getMessage() + ";找不到文件", null);
        } catch (DocumentException e) {
            logger.error("载入应用配置文件[" + MESSAGE_FORMAT_PATH + "\\"
                    + CONTEXT_FILE + "]失败。" + e.getMessage());
            e.printStackTrace();
            r.setValue(ResultCode.POSP_RESULT_CODE_9997.getCode(),
                    ResultCode.POSP_RESULT_CODE_9997.getMessage() + ";解析文件出错。", null);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //logger.info("载入应用配置文件[" + MESSAGE_FORMAT_PATH + "\\" + CONTEXT_FILE + "]成功");
        r.setValue(ResultCode.POSP_RESULT_CODE_0000.getCode(),
                ResultCode.POSP_RESULT_CODE_0000.getMessage(), null);
        return r;
    }

    private Result loadSpringContext() {
        Result r = new Result(ResultCode.POSP_RESULT_CODE_9998.getCode(),
                ResultCode.POSP_RESULT_CODE_9998.getMessage(), null);
        ApplicationContentSpringProvider.getInstance();
        r.setValue(ResultCode.POSP_RESULT_CODE_0000.getCode(),
                ResultCode.POSP_RESULT_CODE_0000.getMessage(), null);
        return r;
    }

    /**
     * 载入Cache
     *
     * @return
     */
    public static Result loadCache() throws Exception {
        Result r = new Result(ResultCode.POSP_RESULT_CODE_9998.getCode(),
                ResultCode.POSP_RESULT_CODE_9998.getMessage(), null);
        ManageCacheService.merchantGroupList.clear();
        ManageCacheService.merchantGroupList.addAll(ApplicationContentSpringProvider.getInstance()
                .getMerchantGroupService().findAll());
        for (TblMerchantGroup mg : ManageCacheService.merchantGroupList) {
            mg.getTradeType().clear();
            mg.getHost().clear();
            //商户集团对应的交易类型
            TblMerchantGroupTradeType merchantGroupTradeType = new TblMerchantGroupTradeType();
            merchantGroupTradeType.setFldMerchantGroupCode(mg.getFldCode());
            List<TblMerchantGroupTradeType> gtt = ApplicationContentSpringProvider.getInstance()
                    .getMerchantGroupTradeTypeService().getTblMerchantGroupTradeTypeListByObj(merchantGroupTradeType);
            mg.getTradeType().addAll(gtt);
            //商户集团的所有商户
            TblMerchant merchant = new TblMerchant();
            merchant.setFldGroupCode(mg.getFldCode());
            List<TblMerchant> m = ApplicationContentSpringProvider.getInstance()
                    .getMerchantService().getTblMerchantListByObj(merchant);
            mg.getMerchant().addAll(m);
            //商户下的所有终端
            for (TblMerchant tm : m) {
                TblMerchantPos mp = new TblMerchantPos();
                mp.setFldMerchantCode(tm.getFldCode());
                tm.getPosList().addAll(ApplicationContentSpringProvider.getInstance()
                        .getMerchantPosService().getTblMerchantPosListByObj(mp));
            }
            //商户集团的所有主机
            TblMerchantTransformHostLink hostLink = new TblMerchantTransformHostLink();
            hostLink.setFldMerchantGroupCode(mg.getFldCode());
            List<TblMerchantTransformHostLink> th = ApplicationContentSpringProvider.getInstance()
                    .getMerchantTransformHostLinkService().getTblMerchantTransformHostLinkListByObj(hostLink);
            mg.getHost().addAll(th);
        }
        ManageCacheService.cardHostList.clear();
        ManageCacheService.cardHostList.addAll(ApplicationContentSpringProvider.getInstance()
                .getTransforCardService().findAll());
        ManageCacheService.proxyHost.clear();
        ManageCacheService.proxyHost.addAll(ApplicationContentSpringProvider.getInstance()
                .getProxyHostService().findAll());
        if (ManageCacheService.merchantGroupList == null ||
                ManageCacheService.merchantGroupList.size() < 1) {
            throw new Exception("初始化失败，没有商户集合。");
        }
        r.setValue(ResultCode.POSP_RESULT_CODE_0000.getCode(),
                ResultCode.POSP_RESULT_CODE_0000.getMessage(), null);
        return r;
    }
}
