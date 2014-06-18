package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblMerchantBrand;


public interface ITblMerchantBrandDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblMerchantBrand> findListByObj(TblMerchantBrand tblMerchantBrand);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblMerchantBrand findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantBrand> selectAllTblMerchantBrand();
	
	public Long select_TblMerchantBrandsCount(TblMerchantBrand tblMerchantBrand);
	
	public void insert(TblMerchantBrand tblMerchantBrand);
	
	public void update(TblMerchantBrand tblMerchantBrand);
	
}

