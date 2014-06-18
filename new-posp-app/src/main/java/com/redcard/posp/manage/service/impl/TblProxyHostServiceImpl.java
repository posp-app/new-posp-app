package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblProxyHostDao;
import com.redcard.posp.manage.model.TblProxyHost;
import com.redcard.posp.manage.service.ITblProxyHostService;


public class TblProxyHostServiceImpl implements ITblProxyHostService{
	
	private ITblProxyHostDao tblProxyHostDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblProxyHostServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblProxyHost tblProxyHost) {
		// 保存申请信息
		tblProxyHostDao.insert(tblProxyHost);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblProxyHost tblProxyHost) {
    	// 保存申请信息
		tblProxyHostDao.update(tblProxyHost);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblProxyHost
	 */
	public List<TblProxyHost> getTblProxyHostListByObj(TblProxyHost tblProxyHost) {
		return tblProxyHostDao.findListByObj(tblProxyHost);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblProxyHost
	 */
	public TblProxyHost getTblProxyHostByPk(String pk_Id) {
		return tblProxyHostDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblProxyHost> findAll(){
		return tblProxyHostDao.selectAllTblProxyHost();
	}

	public ITblProxyHostDao getTblProxyHostDao() {
		return tblProxyHostDao;
	}

	public void setTblProxyHostDao(ITblProxyHostDao tblProxyHostDao) {
		this.tblProxyHostDao = tblProxyHostDao;
	}

	public void updateByMerchantNo(TblProxyHost tblProxyHost) {
		tblProxyHostDao.updateByMerchantNo(tblProxyHost);
	}
	
}
