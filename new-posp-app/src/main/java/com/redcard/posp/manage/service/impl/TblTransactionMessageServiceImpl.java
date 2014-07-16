package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblTransactionMessageDao;
import com.redcard.posp.manage.model.TblTransactionMessage;
import com.redcard.posp.manage.service.ITblTransactionMessageService;
import com.redcard.posp.support.ApplicationContent;


public class TblTransactionMessageServiceImpl implements ITblTransactionMessageService{
	
	private ITblTransactionMessageDao tblTransactionMessageDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblTransactionMessageServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblTransactionMessage tblTransactionMessage) {
		// 保存申请信息
		tblTransactionMessageDao.insert(tblTransactionMessage);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblTransactionMessage tblTransactionMessage) {
    	// 保存申请信息
		tblTransactionMessageDao.update(tblTransactionMessage);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblTransactionMessage
	 */
	public List<TblTransactionMessage> getTblTransactionMessageListByObj(TblTransactionMessage tblTransactionMessage) {
		return tblTransactionMessageDao.findListByObj(tblTransactionMessage);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblTransactionMessage
	 */
	public TblTransactionMessage getTblTransactionMessageByPk(String pk_Id) {
		return tblTransactionMessageDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblTransactionMessage> findAll(){
		return tblTransactionMessageDao.selectAllTblTransactionMessage();
	}

	public ITblTransactionMessageDao getTblTransactionMessageDao() {
		return tblTransactionMessageDao;
	}

	public void setTblTransactionMessageDao(
			ITblTransactionMessageDao tblTransactionMessageDao) {
		this.tblTransactionMessageDao = tblTransactionMessageDao;
	}


	public String updateAndfindOrginalId(String indicateType, int result,
			TblTransactionMessage tblTransactionMessage) {
		//先找出消息本身。然后通过原来保存有的原交易流水和原交易时间找到要冲正、撤销的交易。
		TblTransactionMessage tm = new TblTransactionMessage();
		tm.setFldTerminalNo(tblTransactionMessage.getFldTerminalNo());
		tm.setFldSystemTraceNumber(tblTransactionMessage.getFldSystemTraceNumber());
		tm.setFldMerchantNo(tblTransactionMessage.getFldMerchantNo());
		tm.setFldBatchNumber(tblTransactionMessage.getFldBatchNumber());
		tm.setFldLocalDate(tblTransactionMessage.getFldLocalDate());
		
		List<TblTransactionMessage> tList = tblTransactionMessageDao.findListByObj(tm);
		if (tList==null || tList.size()<1) {
			logger.error("找不到关联交易[terminal="+tblTransactionMessage.getFldTerminalNo()+"];[systemTranceNumber="+tblTransactionMessage.getFldSystemTraceNumber()+"]");
			return "-1";
		} else if(tList.size()>1) {
			//因为可能引发多次冲正，或撤销、或退货。所以可能大于1条记录
			//logger.error("找到多条关联交易[terminal="+tblTransactionMessage.getFldTerminalNo()+"];[systemTranceNumber="+tblTransactionMessage.getFldSystemTraceNumber()+"]");
			//return "-1";
		}
		tm = tList.get(0);
		//通过原交易流水和原交易时间找要冲正、撤销的交易
		TblTransactionMessage orgTm = new TblTransactionMessage();
		orgTm.setFldTerminalNo(tblTransactionMessage.getFldTerminalNo());
		orgTm.setFldMerchantNo(tblTransactionMessage.getFldMerchantNo());
		orgTm.setFldSystemTraceNumber(tm.getFldOriginalTransaction());
		orgTm.setFldLocalDate(tm.getFldLocalDate());
		logger.info("找到要冲正或撤销的原交易[terminal="+orgTm.getFldTerminalNo()+"];merchantNo=["+orgTm.getFldMerchantNo()+"];[systemTranceNumber="+orgTm.getFldSystemTraceNumber()+"];localdate=["+orgTm.getFldLocalDate()+"]");
		//orgTm.setFldLocalTime(tm.getFldOriginalTransactionDate().substring(4));
		List<TblTransactionMessage> tmList= tblTransactionMessageDao.findListByObj(orgTm);
		if (ApplicationContent.INDICATE_REVOKE.equals(indicateType)) {
			orgTm.setFldRevokeIndicate(result);
		} else if (ApplicationContent.INDICATE_CANCEL.equals(indicateType)) {
			orgTm.setFldCancelIndicate(result);
		} else if (ApplicationContent.INDICATE_REVERSAL.equals(indicateType)) {
			orgTm.setFldReversalIndicate(result);
		} 
		if (tmList!=null && tmList.size()>0) {
			orgTm.setFldId(tmList.get(0).getFldId());
			tblTransactionMessageDao.updateById(orgTm);
			return tmList.get(0).getFldId();
		}
		return null;
	}
}
