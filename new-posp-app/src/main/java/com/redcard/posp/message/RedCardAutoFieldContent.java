package com.redcard.posp.message;

import com.redcard.posp.common.CommonUtil;
import com.redcard.posp.handler.secret.redcard.SecretKeyFactory;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.service.impl.ManageCacheService;
import com.redcard.posp.support.IDBuilder;
import org.apache.commons.lang3.StringUtils;

public class RedCardAutoFieldContent implements IAutoContent {

    public String getField(int number, String terminalNo) throws Exception {
        if (11 == number) {
            return IDBuilder.getInstance().getSystemSequence();
        }
        if (12 == number) {
            //本地交易时间
            return CommonUtil.getCurrTime();
        }
        if (13 == number) {
            //本地交易日期
            return CommonUtil.getDate();
        }
        if (15 == number) {
            //清分日期
            return CommonUtil.getCurrDate();
        }
        if (37 == number) {
            //参考号，想用时间代替着。
            return CommonUtil.getCurrDateTime();
        }
        if (60 == number) {
            TblMerchantPos pos = ManageCacheService.findPos(terminalNo);
            if (pos == null) {
                return "";
            }
            return SecretKeyFactory.createMacKey(pos.getFldMasterKey());
        }
        if (61 == number) {
            TblMerchantPos pos = ManageCacheService.findPos(terminalNo);
            if (pos == null) {
                return "";
            }
            return SecretKeyFactory.createPinKey(pos.getFldMasterKey());
        }
        if (62 == number) {
            //当前的批次号+1
            TblMerchantPos pos = ManageCacheService.findPos(terminalNo);
            if (pos == null || StringUtils.isBlank(pos.getFldBatchNo())) {
                return "000000";
            }
            return pos.getFldBatchNo();
        }
        if (63 == number) {
            return SecretKeyFactory.createDownload63FieldValue();
        }
        throw new Exception("该域不能自动生成");
    }

}
