package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblTradeDao;
import com.redcard.posp.manage.model.TblTrade;


public class TblTradeDaoImpl extends AbstractiBatisDAO implements ITblTradeDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblTradeDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblTrade> findListByObj(TblTrade tblTrade){
		String sql = "findListByObj" + "_"+"TblTrade";
		return getSqlSession().selectList(sql, tblTrade);
	}
	
	/**
	 * 
	 */
	public  TblTrade findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblTrade";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblTrade> selectAllTblTrade(){
		String sql = "selectAll" + "TblTrade";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblTradesCount(TblTrade tblTrade){
		String sql="select_"+"TblTrade"+"sCount";
		return getSqlSession().selectOne(sql,tblTrade);
	}
	
	public void insert(TblTrade tblTrade){
		this.getSqlSession().insert("insert_"+"TblTrade",
					tblTrade);
	}
	
	public void update(TblTrade tblTrade){
		this.getSqlSession().update("update_"+"TblTrade",
					tblTrade);
	}
	
}
