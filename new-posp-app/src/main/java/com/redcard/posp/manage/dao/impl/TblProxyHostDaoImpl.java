package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblProxyHostDao;
import com.redcard.posp.manage.model.TblProxyHost;


public class TblProxyHostDaoImpl extends AbstractiBatisDAO implements ITblProxyHostDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblProxyHostDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblProxyHost> findListByObj(TblProxyHost tblProxyHost){
		String sql = "findListByObj" + "_"+"TblProxyHost";
		return getSqlSession().selectList(sql, tblProxyHost);
	}
	
	/**
	 * 
	 */
	public  TblProxyHost findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblProxyHost";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblProxyHost> selectAllTblProxyHost(){
		String sql = "selectAll" + "TblProxyHost";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblProxyHostsCount(TblProxyHost tblProxyHost){
		String sql="select_"+"TblProxyHost"+"sCount";
		return getSqlSession().selectOne(sql,tblProxyHost);
	}
	
	public void insert(TblProxyHost tblProxyHost){
		this.getSqlSession().insert("insert_"+"TblProxyHost",
					tblProxyHost);
	}
	
	public void update(TblProxyHost tblProxyHost){
		this.getSqlSession().update("update_"+"TblProxyHost",
					tblProxyHost);
	}
	public void updateByMerchantNo(TblProxyHost tblProxyHost){
		this.getSqlSession().update("update_"+"ByMerchantNo",
				tblProxyHost);
	}
}
