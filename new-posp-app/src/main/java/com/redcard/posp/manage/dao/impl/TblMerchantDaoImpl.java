package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantDao;
import com.redcard.posp.manage.model.TblMerchant;


public class TblMerchantDaoImpl extends AbstractiBatisDAO implements ITblMerchantDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblMerchant> findListByObj(TblMerchant tblMerchant){
		String sql = "findListByObj" + "_"+"TblMerchant";
		return getSqlSession().selectList(sql, tblMerchant);
	}
	
	/**
	 * 
	 */
	public  TblMerchant findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblMerchant";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchant> selectAllTblMerchant(){
		String sql = "selectAll" + "TblMerchant";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblMerchantsCount(TblMerchant tblMerchant){
		String sql="select_"+"TblMerchant"+"sCount";
		return getSqlSession().selectOne(sql,tblMerchant);
	}
	
	public void insert(TblMerchant tblMerchant){
		this.getSqlSession().insert("insert_"+"TblMerchant",
					tblMerchant);
	}
	
	public void update(TblMerchant tblMerchant){
		this.getSqlSession().update("update_"+"TblMerchant",
					tblMerchant);
	}
	
}
