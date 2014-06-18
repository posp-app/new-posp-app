package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantPosDao;
import com.redcard.posp.manage.model.TblMerchantPos;


public class TblMerchantPosDaoImpl extends AbstractiBatisDAO implements ITblMerchantPosDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantPosDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblMerchantPos> findListByObj(TblMerchantPos tblMerchantPos){
		String sql = "findListByObj" + "_"+"TblMerchantPos";
		return getSqlSession().selectList(sql, tblMerchantPos);
	}
	
	/**
	 * 
	 */
	public  TblMerchantPos findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblMerchantPos";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantPos> selectAllTblMerchantPos(){
		String sql = "selectAll" + "TblMerchantPos";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblMerchantPossCount(TblMerchantPos tblMerchantPos){
		String sql="select_"+"TblMerchantPos"+"sCount";
		return getSqlSession().selectOne(sql,tblMerchantPos);
	}
	
	public void insert(TblMerchantPos tblMerchantPos){
		this.getSqlSession().insert("insert_"+"TblMerchantPos",
					tblMerchantPos);
	}
	
	public void update(TblMerchantPos tblMerchantPos){
		this.getSqlSession().update("update_"+"TblMerchantPos",
					tblMerchantPos);
	}
	
	public void updateKey(TblMerchantPos tblMerchantPos){
		this.getSqlSession().update("updateKey",
					tblMerchantPos);
	}
	
}
