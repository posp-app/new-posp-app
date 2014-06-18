package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblMerchantTransformHostLinkDao;
import com.redcard.posp.manage.model.TblMerchantTransformHostLink;


public class TblMerchantTransformHostLinkDaoImpl extends AbstractiBatisDAO implements ITblMerchantTransformHostLinkDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblMerchantTransformHostLinkDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblMerchantTransformHostLink> findListByObj(TblMerchantTransformHostLink tblMerchantTransformHostLink){
		String sql = "findListByObj" + "_"+"TblMerchantTransformHostLink";
		return getSqlSession().selectList(sql, tblMerchantTransformHostLink);
	}
	
	/**
	 * 
	 */
	public  TblMerchantTransformHostLink findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblMerchantTransformHostLink";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblMerchantTransformHostLink> selectAllTblMerchantTransformHostLink(){
		String sql = "selectAll" + "TblMerchantTransformHostLink";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblMerchantTransformHostLinksCount(TblMerchantTransformHostLink tblMerchantTransformHostLink){
		String sql="select_"+"TblMerchantTransformHostLink"+"sCount";
		return getSqlSession().selectOne(sql,tblMerchantTransformHostLink);
	}
	
	public void insert(TblMerchantTransformHostLink tblMerchantTransformHostLink){
		this.getSqlSession().insert("insert_"+"TblMerchantTransformHostLink",
					tblMerchantTransformHostLink);
	}
	
	public void update(TblMerchantTransformHostLink tblMerchantTransformHostLink){
		this.getSqlSession().update("update_"+"TblMerchantTransformHostLink",
					tblMerchantTransformHostLink);
	}
	
}
