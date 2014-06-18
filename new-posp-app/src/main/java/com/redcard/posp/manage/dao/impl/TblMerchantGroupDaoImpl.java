package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantGroupDao;
import com.redcard.posp.manage.model.TblMerchantGroup;


public class TblMerchantGroupDaoImpl extends AbstractiBatisDAO implements ITblMerchantGroupDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantGroupDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblMerchantGroup> findListByObj(TblMerchantGroup tblMerchantGroup){
		String sql = "findListByObj" + "_"+"TblMerchantGroup";
		return getSqlSession().selectList(sql, tblMerchantGroup);
	}
	
	/**
	 * 
	 */
	public  TblMerchantGroup findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblMerchantGroup";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantGroup> selectAllTblMerchantGroup(){
		String sql = "selectAll" + "TblMerchantGroup";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblMerchantGroupsCount(TblMerchantGroup tblMerchantGroup){
		String sql="select_"+"TblMerchantGroup"+"sCount";
		return getSqlSession().selectOne(sql,tblMerchantGroup);
	}
	
	public void insert(TblMerchantGroup tblMerchantGroup){
		this.getSqlSession().insert("insert_"+"TblMerchantGroup",
					tblMerchantGroup);
	}
	
	public void update(TblMerchantGroup tblMerchantGroup){
		this.getSqlSession().update("update_"+"TblMerchantGroup",
					tblMerchantGroup);
	}
	
}
