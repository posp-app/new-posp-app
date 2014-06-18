package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantPosDao;
import com.redcard.posp.manage.model.TblMerchantPos;
import com.redcard.posp.manage.service.ITblMerchantPosService;


public class TblMerchantPosServiceImpl implements ITblMerchantPosService{
	
	private ITblMerchantPosDao tblMerchantPosDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantPosServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantPos tblMerchantPos) {
		// 保存申请信息
		tblMerchantPosDao.insert(tblMerchantPos);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantPos tblMerchantPos) {
    	// 保存申请信息
		tblMerchantPosDao.update(tblMerchantPos);
		return null;
	}
	public void updateKey(TblMerchantPos tblMerchantPos){
		tblMerchantPosDao.updateKey(tblMerchantPos);
	}
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantPos
	 */
	public List<TblMerchantPos> getTblMerchantPosListByObj(TblMerchantPos tblMerchantPos) {
		return tblMerchantPosDao.findListByObj(tblMerchantPos);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantPos
	 */
	public TblMerchantPos getTblMerchantPosByPk(String pk_Id) {
		return tblMerchantPosDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantPos> findAll(){
		return tblMerchantPosDao.selectAllTblMerchantPos();
	}

	public ITblMerchantPosDao getTblMerchantPosDao() {
		return tblMerchantPosDao;
	}

	public void setTblMerchantPosDao(ITblMerchantPosDao tblMerchantPosDao) {
		this.tblMerchantPosDao = tblMerchantPosDao;
	}
	
}
