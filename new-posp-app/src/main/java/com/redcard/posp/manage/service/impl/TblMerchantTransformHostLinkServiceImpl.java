package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantTransformHostLinkDao;
import com.redcard.posp.manage.model.TblMerchantTransformHostLink;
import com.redcard.posp.manage.service.ITblMerchantTransformHostLinkService;


public class TblMerchantTransformHostLinkServiceImpl implements ITblMerchantTransformHostLinkService{
	
	private ITblMerchantTransformHostLinkDao tblMerchantTransformHostLinkDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantTransformHostLinkServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantTransformHostLink tblMerchantTransformHostLink) {
		// 保存申请信息
		tblMerchantTransformHostLinkDao.insert(tblMerchantTransformHostLink);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantTransformHostLink tblMerchantTransformHostLink) {
    	// 保存申请信息
		tblMerchantTransformHostLinkDao.update(tblMerchantTransformHostLink);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantTransformHostLink
	 */
	public List<TblMerchantTransformHostLink> getTblMerchantTransformHostLinkListByObj(TblMerchantTransformHostLink tblMerchantTransformHostLink) {
		return tblMerchantTransformHostLinkDao.findListByObj(tblMerchantTransformHostLink);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblMerchantTransformHostLink
	 */
	public TblMerchantTransformHostLink getTblMerchantTransformHostLinkByPk(String pk_Id) {
		return tblMerchantTransformHostLinkDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantTransformHostLink> findAll(){
		return tblMerchantTransformHostLinkDao.selectAllTblMerchantTransformHostLink();
	}

	public ITblMerchantTransformHostLinkDao getTblMerchantTransformHostLinkDao() {
		return tblMerchantTransformHostLinkDao;
	}

	public void setTblMerchantTransformHostLinkDao(
			ITblMerchantTransformHostLinkDao tblMerchantTransformHostLinkDao) {
		this.tblMerchantTransformHostLinkDao = tblMerchantTransformHostLinkDao;
	}
	
}
