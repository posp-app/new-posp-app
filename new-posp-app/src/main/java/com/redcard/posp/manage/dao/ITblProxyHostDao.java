package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblProxyHost;


public interface ITblProxyHostDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblProxyHost> findListByObj(TblProxyHost tblProxyHost);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblProxyHost findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblProxyHost> selectAllTblProxyHost();
	
	public Long select_TblProxyHostsCount(TblProxyHost tblProxyHost);
	
	public void insert(TblProxyHost tblProxyHost);
	
	public void update(TblProxyHost tblProxyHost);
	
	public void updateByMerchantNo(TblProxyHost tblProxyHost);
}

