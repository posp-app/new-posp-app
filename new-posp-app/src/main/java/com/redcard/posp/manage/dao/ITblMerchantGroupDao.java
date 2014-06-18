package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblMerchantGroup;


public interface ITblMerchantGroupDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblMerchantGroup> findListByObj(TblMerchantGroup tblMerchantGroup);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblMerchantGroup findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantGroup> selectAllTblMerchantGroup();
	
	public Long select_TblMerchantGroupsCount(TblMerchantGroup tblMerchantGroup);
	
	public void insert(TblMerchantGroup tblMerchantGroup);
	
	public void update(TblMerchantGroup tblMerchantGroup);
	
}

