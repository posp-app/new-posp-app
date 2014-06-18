package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantBrandDao;
import com.redcard.posp.manage.model.TblMerchantBrand;


public class TblMerchantBrandDaoImpl extends AbstractiBatisDAO implements ITblMerchantBrandDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantBrandDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblMerchantBrand> findListByObj(TblMerchantBrand tblMerchantBrand){
		String sql = "findListByObj" + "_"+"TblMerchantBrand";
		return getSqlSession().selectList(sql, tblMerchantBrand);
	}
	
	/**
	 * 
	 */
	public  TblMerchantBrand findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblMerchantBrand";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantBrand> selectAllTblMerchantBrand(){
		String sql = "selectAll" + "TblMerchantBrand";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblMerchantBrandsCount(TblMerchantBrand tblMerchantBrand){
		String sql="select_"+"TblMerchantBrand"+"sCount";
		return getSqlSession().selectOne(sql,tblMerchantBrand);
	}
	
	public void insert(TblMerchantBrand tblMerchantBrand){
		this.getSqlSession().insert("insert_"+"TblMerchantBrand",
					tblMerchantBrand);
	}
	
	public void update(TblMerchantBrand tblMerchantBrand){
		this.getSqlSession().update("update_"+"TblMerchantBrand",
					tblMerchantBrand);
	}
	
}
