package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantBrandDao;
import com.redcard.posp.manage.model.TblMerchantBrand;
import com.redcard.posp.manage.service.ITblMerchantBrandService;


public class TblMerchantBrandServiceImpl implements ITblMerchantBrandService{
	
	private ITblMerchantBrandDao tblMerchantBrandDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantBrandServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantBrand tblMerchantBrand) {
		// 保存申请信息
		tblMerchantBrandDao.insert(tblMerchantBrand);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantBrand tblMerchantBrand) {
    	// 保存申请信息
		tblMerchantBrandDao.update(tblMerchantBrand);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantBrand
	 */
	public List<TblMerchantBrand> getTblMerchantBrandListByObj(TblMerchantBrand tblMerchantBrand) {
		return tblMerchantBrandDao.findListByObj(tblMerchantBrand);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantBrand
	 */
	public TblMerchantBrand getTblMerchantBrandByPk(String pk_Id) {
		return tblMerchantBrandDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantBrand> findAll(){
		return tblMerchantBrandDao.selectAllTblMerchantBrand();
	}

	public ITblMerchantBrandDao getTblMerchantBrandDao() {
		return tblMerchantBrandDao;
	}

	public void setTblMerchantBrandDao(ITblMerchantBrandDao tblMerchantBrandDao) {
		this.tblMerchantBrandDao = tblMerchantBrandDao;
	}
	
}
