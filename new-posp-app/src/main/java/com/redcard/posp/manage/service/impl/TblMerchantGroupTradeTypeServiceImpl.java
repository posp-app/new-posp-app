package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantGroupTradeTypeDao;
import com.redcard.posp.manage.model.TblMerchantGroupTradeType;
import com.redcard.posp.manage.service.ITblMerchantGroupTradeTypeService;


public class TblMerchantGroupTradeTypeServiceImpl implements ITblMerchantGroupTradeTypeService{
	
	private ITblMerchantGroupTradeTypeDao tblMerchantGroupTradeTypeDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantGroupTradeTypeServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantGroupTradeType tblMerchantGroupTradeType) {
		// 保存申请信息
		tblMerchantGroupTradeTypeDao.insert(tblMerchantGroupTradeType);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantGroupTradeType tblMerchantGroupTradeType) {
    	// 保存申请信息
		tblMerchantGroupTradeTypeDao.update(tblMerchantGroupTradeType);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantGroupTradeType
	 */
	public List<TblMerchantGroupTradeType> getTblMerchantGroupTradeTypeListByObj(TblMerchantGroupTradeType tblMerchantGroupTradeType) {
		return tblMerchantGroupTradeTypeDao.findListByObj(tblMerchantGroupTradeType);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantGroupTradeType
	 */
	public TblMerchantGroupTradeType getTblMerchantGroupTradeTypeByPk(String pk_Id) {
		return tblMerchantGroupTradeTypeDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantGroupTradeType> findAll(){
		return tblMerchantGroupTradeTypeDao.selectAllTblMerchantGroupTradeType();
	}

	public ITblMerchantGroupTradeTypeDao getTblMerchantGroupTradeTypeDao() {
		return tblMerchantGroupTradeTypeDao;
	}

	public void setTblMerchantGroupTradeTypeDao(
			ITblMerchantGroupTradeTypeDao tblMerchantGroupTradeTypeDao) {
		this.tblMerchantGroupTradeTypeDao = tblMerchantGroupTradeTypeDao;
	}
	
}
