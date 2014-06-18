package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantGroupDao;
import com.redcard.posp.manage.model.TblMerchantGroup;
import com.redcard.posp.manage.service.ITblMerchantGroupService;


public class TblMerchantGroupServiceImpl implements ITblMerchantGroupService{
	
	private ITblMerchantGroupDao tblMerchantGroupDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantGroupServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantGroup tblMerchantGroup) {
		// 保存申请信息
		tblMerchantGroupDao.insert(tblMerchantGroup);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantGroup tblMerchantGroup) {
    	// 保存申请信息
		tblMerchantGroupDao.update(tblMerchantGroup);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantGroup
	 */
	public List<TblMerchantGroup> getTblMerchantGroupListByObj(TblMerchantGroup tblMerchantGroup) {
		return tblMerchantGroupDao.findListByObj(tblMerchantGroup);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantGroup
	 */
	public TblMerchantGroup getTblMerchantGroupByPk(String pk_Id) {
		return tblMerchantGroupDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantGroup> findAll(){
		return tblMerchantGroupDao.selectAllTblMerchantGroup();
	}

	public ITblMerchantGroupDao getTblMerchantGroupDao() {
		return tblMerchantGroupDao;
	}

	public void setTblMerchantGroupDao(ITblMerchantGroupDao tblMerchantGroupDao) {
		this.tblMerchantGroupDao = tblMerchantGroupDao;
	}
	
}
