package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblMerchantPos;


public interface ITblMerchantPosDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblMerchantPos> findListByObj(TblMerchantPos tblMerchantPos);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblMerchantPos findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantPos> selectAllTblMerchantPos();
	
	public Long select_TblMerchantPossCount(TblMerchantPos tblMerchantPos);
	
	public void insert(TblMerchantPos tblMerchantPos);
	
	public void update(TblMerchantPos tblMerchantPos);
	
	public void updateKey(TblMerchantPos tblMerchantPos);
	
}

