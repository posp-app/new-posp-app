package com.redcard.posp.manage.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.redcard.posp.manage.model.TblCard;
import com.redcard.posp.manage.model.TblMerchant;
import com.redcard.posp.manage.model.TblMerchantGroup;
import com.redcard.posp.manage.model.TblMerchantGroupTradeType;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.model.TblMerchantTransformHostLink;
import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.manage.model.TblTransformCard;
import com.redcard.posp.support.ApplicationContent;
import com.redcard.posp.support.ApplicationContentSpringProvider;
import com.redcard.posp.support.ApplicationContextInit;

public class ManageCacheService {

    public static List<TblMerchantGroup> merchantGroupList = new ArrayList<TblMerchantGroup>();

    public static List<TblTransformCard> cardHostList = new ArrayList<TblTransformCard>();

    /**
     * 转发主机
     */
    public static List<TblProxyHost> proxyHost = new ArrayList<TblProxyHost>();

    public static TblMerchantGroup findMerchantGroup(String merchant) {
        for (TblMerchantGroup mg : merchantGroupList) {
            for (TblMerchant m : mg.getMerchant()) {
                if (m.getFldCode().equals(merchant)) {
                    return mg;
                }
            }
        }
        return null;
    }

    /**
     * 根据商户号，查询出商户对象
     *
     * @param merchant
     * @return
     */
    public static TblMerchant findMerchant(TblMerchantGroup group, String merchant) {
        for (TblMerchant m : group.getMerchant()) {
            if (m.getFldCode().equals(merchant)) {
                return m;
            }
        }
        return null;
    }

    /**
     * 根据商户对象和终端号，查询出终端对象
     *
     * @param merchant
     * @param terminalNO
     * @return
     */
    public static TblMerchantPos findPos(TblMerchant merchant, String terminalNO) {
        for (TblMerchantPos mp : merchant.getPosList()) {
            if (mp.getFldTerminalNo().equals(terminalNO)) {
                return mp;
            }
        }
        return null;
    }

    public static TblMerchantPos findPos(String terminalNO) {
        for (TblMerchantGroup mg : merchantGroupList) {
            for (TblMerchant m : mg.getMerchant()) {
                for (TblMerchantPos mp : m.getPosList()) {
                    if (mp.getFldTerminalNo().equals(terminalNO)) {
                        return mp;
                    }
                }
            }
        }
        return null;
    }

    public static boolean allowTrade(TblMerchantGroup merchantGroup, String messageType, String process) {
        for (TblMerchantGroupTradeType mgtt : merchantGroup.getTradeType()) {
            if (ApplicationContent.MSG_TYPE_VOID_REQ.equals(messageType)
                    || ApplicationContent.MSG_TYPE_REVERSAL_REQ.equals(messageType)) {
                if (mgtt.getFldTradeType().equals(messageType)) {
                    return true;
                }
            } else {
                if (mgtt.getFldTradeType().equals(messageType + process)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param merchantGroup
     * @param merchant
     * @param cardNO
     * @return
     */
    public static TblProxyHost getCardHost(TblMerchantGroup merchantGroup, TblMerchant merchant, String cardNO) {
        TblTransformCard cardHost = null;
        String proxyHostCode = null;
        for (TblTransformCard tc : cardHostList) {
            if (cardRegMarch(cardNO, tc.getFldCardRule())) {
                cardHost = tc;
                proxyHostCode = cardHost.getFldHostCode();
                break;
            }
        }

        if (cardHost == null) {
            TblCard card = new TblCard();
            card.setFldCardNo(cardNO);
            TblCard t = ApplicationContentSpringProvider.getInstance().getCardService().findByCardNo(card);
            //检查tblcard表中是否有对应的卡片，如果没有，则使用默认的转发主机
            if (t != null) {
                TblProxyHost tph = new TblProxyHost();
                tph.setFldHostCode(t.getFldHostCode());
                tph.setFldProtocolType(null);
                List<TblProxyHost> tList = ApplicationContentSpringProvider.getInstance().getProxyHostService().getTblProxyHostListByObj(tph);
                if (tList != null && tList.size() > 0 && tList.get(0).getFldStatus() != ApplicationContent.STATUS_STOPED) {
                    proxyHostCode = tList.get(0).getFldHostCode();
                }
            }

            if (proxyHostCode == null) {
                TblProxyHost tph = new TblProxyHost();
                tph.setFldHostCode(ApplicationContextInit.defaultHostCode);
                tph.setFldProtocolType(null);
                List<TblProxyHost> tList = ApplicationContentSpringProvider.getInstance().getProxyHostService().getTblProxyHostListByObj(tph);
                if (tList != null && tList.size() > 0 && tList.get(0).getFldStatus() != ApplicationContent.STATUS_STOPED) {
                    proxyHostCode = tList.get(0).getFldHostCode();
                }
            }
        }

        //所有商户均要检测转发主机是否关联
        for (TblMerchantTransformHostLink mth : merchantGroup.getHost()) {
            if (mth.getFldTransformHostCode().equals(proxyHostCode)) {
                for (TblProxyHost host : proxyHost) {
                    if (host.getFldHostCode().equals(mth.getFldTransformHostCode())) {
                        return host.getFldStatus() == ApplicationContent.STATUS_STOPED ? null : host;
                    }
                }
            }
        }
        return null;
    }

    private static boolean cardRegMarch(String cardNO, String regEx) {
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(cardNO);
        return mat.find();
    }

    public static void updateProxyHostKeyByCode(TblProxyHost tblProxyHost) {
        for (TblProxyHost h : proxyHost) {
            if (h.getFldHostCode().equals(tblProxyHost.getFldHostCode())) {
                h.setFldMacKey(tblProxyHost.getFldMacKey());
                h.setFldPinKey(tblProxyHost.getFldPinKey());
                h.setFldSignDate(tblProxyHost.getFldSignDate());
            }
        }
    }

	/*public static TblProxyHost findProxyHostByMerchantNo(String merchantNo) {
        for (TblProxyHost h:proxyHost){
			//System.out.println("=======>>>>>>"+h.getFldMerchantNo());
			if (h.getFldMerchantNo().equals(merchantNo)) {
				return h;
			}
		}
		return null;
	}*/

}
