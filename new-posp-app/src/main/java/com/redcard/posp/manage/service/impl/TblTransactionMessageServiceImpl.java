package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblTransactionMessageDao;
import com.redcard.posp.manage.model.TblTransactionMessage;
import com.redcard.posp.manage.service.ITblTransactionMessageService;


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
	
}
