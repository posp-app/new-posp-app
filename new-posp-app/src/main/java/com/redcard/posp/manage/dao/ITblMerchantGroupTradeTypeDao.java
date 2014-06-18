package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblMerchantGroupTradeType;


public interface ITblMerchantGroupTradeTypeDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblMerchantGroupTradeType> findListByObj(TblMerchantGroupTradeType tblMerchantGroupTradeType);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblMerchantGroupTradeType findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantGroupTradeType> selectAllTblMerchantGroupTradeType();
	
	public Long select_TblMerchantGroupTradeTypesCount(TblMerchantGroupTradeType tblMerchantGroupTradeType);
	
	public void insert(TblMerchantGroupTradeType tblMerchantGroupTradeType);
	
	public void update(TblMerchantGroupTradeType tblMerchantGroupTradeType);
	
}

