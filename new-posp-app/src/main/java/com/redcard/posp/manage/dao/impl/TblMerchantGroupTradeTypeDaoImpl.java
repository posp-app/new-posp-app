package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantGroupTradeTypeDao;
import com.redcard.posp.manage.model.TblMerchantGroupTradeType;


public class TblMerchantGroupTradeTypeDaoImpl extends AbstractiBatisDAO implements ITblMerchantGroupTradeTypeDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantGroupTradeTypeDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblMerchantGroupTradeType> findListByObj(TblMerchantGroupTradeType tblMerchantGroupTradeType){
		String sql = "findListByObj" + "_"+"TblMerchantGroupTradeType";
		return getSqlSession().selectList(sql, tblMerchantGroupTradeType);
	}
	
	/**
	 * 
	 */
	public  TblMerchantGroupTradeType findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblMerchantGroupTradeType";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantGroupTradeType> selectAllTblMerchantGroupTradeType(){
		String sql = "selectAll" + "TblMerchantGroupTradeType";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblMerchantGroupTradeTypesCount(TblMerchantGroupTradeType tblMerchantGroupTradeType){
		String sql="select_"+"TblMerchantGroupTradeType"+"sCount";
		return getSqlSession().selectOne(sql,tblMerchantGroupTradeType);
	}
	
	public void insert(TblMerchantGroupTradeType tblMerchantGroupTradeType){
		this.getSqlSession().insert("insert_"+"TblMerchantGroupTradeType",
					tblMerchantGroupTradeType);
	}
	
	public void update(TblMerchantGroupTradeType tblMerchantGroupTradeType){
		this.getSqlSession().update("update_"+"TblMerchantGroupTradeType",
					tblMerchantGroupTradeType);
	}
	
}
