package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblMerchantTransformHostLink;


public interface ITblMerchantTransformHostLinkDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblMerchantTransformHostLink> findListByObj(TblMerchantTransformHostLink tblMerchantTransformHostLink);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblMerchantTransformHostLink findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantTransformHostLink> selectAllTblMerchantTransformHostLink();
	
	public Long select_TblMerchantTransformHostLinksCount(TblMerchantTransformHostLink tblMerchantTransformHostLink);
	
	public void insert(TblMerchantTransformHostLink tblMerchantTransformHostLink);
	
	public void update(TblMerchantTransformHostLink tblMerchantTransformHostLink);
	
}

