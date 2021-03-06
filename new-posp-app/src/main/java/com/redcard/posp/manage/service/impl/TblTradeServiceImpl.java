package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblTradeDao;
import com.redcard.posp.manage.model.TblTrade;
import com.redcard.posp.manage.service.ITblTradeService;


public class TblTradeServiceImpl implements ITblTradeService{
	
	private ITblTradeDao tblTradeDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblTradeServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblTrade tblTrade) {
		// 保存申请信息
		tblTradeDao.insert(tblTrade);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblTrade tblTrade) {
    	// 保存申请信息
		tblTradeDao.update(tblTrade);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblTrade
	 */
	public List<TblTrade> getTblTradeListByObj(TblTrade tblTrade) {
		return tblTradeDao.findListByObj(tblTrade);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblTrade
	 */
	public TblTrade getTblTradeByPk(String pk_Id) {
		return tblTradeDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblTrade> findAll(){
		return tblTradeDao.selectAllTblTrade();
	}

	public ITblTradeDao getTblTradeDao() {
		return tblTradeDao;
	}

	public void setTblTradeDao(ITblTradeDao tblTradeDao) {
		this.tblTradeDao = tblTradeDao;
	}
	
}
