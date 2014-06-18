package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblTrade;


public interface ITblTradeDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblTrade> findListByObj(TblTrade tblTrade);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblTrade findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblTrade> selectAllTblTrade();
	
	public Long select_TblTradesCount(TblTrade tblTrade);
	
	public void insert(TblTrade tblTrade);
	
	public void update(TblTrade tblTrade);
	
}

