package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantDao;
import com.redcard.posp.manage.model.TblMerchant;
import com.redcard.posp.manage.service.ITblMerchantService;


public class TblMerchantServiceImpl implements ITblMerchantService{
	
	private ITblMerchantDao tblMerchantDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchant tblMerchant) {
		// 保存申请信息
		tblMerchantDao.insert(tblMerchant);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchant tblMerchant) {
    	// 保存申请信息
		tblMerchantDao.update(tblMerchant);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchant
	 */
	public List<TblMerchant> getTblMerchantListByObj(TblMerchant tblMerchant) {
		return tblMerchantDao.findListByObj(tblMerchant);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchant
	 */
	public TblMerchant getTblMerchantByPk(String pk_Id) {
		return tblMerchantDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblMerchant> findAll(){
		return tblMerchantDao.selectAllTblMerchant();
	}

	public ITblMerchantDao getTblMerchantDao() {
		return tblMerchantDao;
	}

	public void setTblMerchantDao(ITblMerchantDao tblMerchantDao) {
		this.tblMerchantDao = tblMerchantDao;
	}
	
}
