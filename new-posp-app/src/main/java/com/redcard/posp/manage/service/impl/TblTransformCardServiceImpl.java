package com.redcard.posp.manage.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblTransformCardDao;
import com.redcard.posp.manage.model.TblTransformCard;
import com.redcard.posp.manage.service.ITblTransformCardService;


public class TblTransformCardServiceImpl implements ITblTransformCardService{
	
	private ITblTransformCardDao tblTransformCardDao;
	
	
	final static Logger logger = LoggerFactory.getLogger(TblTransformCardServiceImpl.class);

	/**
	 * 新增
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblTransformCard tblTransformCard) {
		// 保存申请信息
		tblTransformCardDao.insert(tblTransformCard);
		return null;
	}
	
	/**
	 * 新增修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblTransformCard tblTransformCard) {
    	// 保存申请信息
		tblTransformCardDao.update(tblTransformCard);
		return null;
	}
	
	
	/**
	 * 根据主键对象获取信息
	 * 
	 * @param  POJO对象
	 * @return TblTransformCard
	 */
	public List<TblTransformCard> getTblTransformCardListByObj(TblTransformCard tblTransformCard) {
		return tblTransformCardDao.findListByObj(tblTransformCard);
	}
	
	/**
	 * 根据主键获取信息
	 * 
	 * @param  POJO对象
	 * @return TblTransformCard
	 */
	public TblTransformCard getTblTransformCardByPk(String pk_Id) {
		return tblTransformCardDao.findByPKId(pk_Id);
	}
	
	/**
	 * 查询所有
	 */
	public List<TblTransformCard> findAll(){
		return tblTransformCardDao.selectAllTblTransformCard();
	}

	public ITblTransformCardDao getTblTransformCardDao() {
		return tblTransformCardDao;
	}

	public void setTblTransformCardDao(ITblTransformCardDao tblTransformCardDao) {
		this.tblTransformCardDao = tblTransformCardDao;
	}
	
}
