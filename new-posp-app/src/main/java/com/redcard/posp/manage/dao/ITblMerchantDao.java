package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblMerchant;


public interface ITblMerchantDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblMerchant> findListByObj(TblMerchant tblMerchant);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblMerchant findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchant> selectAllTblMerchant();
	
	public Long select_TblMerchantsCount(TblMerchant tblMerchant);
	
	public void insert(TblMerchant tblMerchant);
	
	public void update(TblMerchant tblMerchant);
	
}

