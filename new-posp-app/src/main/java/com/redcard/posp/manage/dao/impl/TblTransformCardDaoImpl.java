package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblTransformCardDao;
import com.redcard.posp.manage.model.TblTransformCard;


public class TblTransformCardDaoImpl extends AbstractiBatisDAO implements ITblTransformCardDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblTransformCardDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblTransformCard> findListByObj(TblTransformCard tblTransformCard){
		String sql = "findListByObj" + "_"+"TblTransformCard";
		return getSqlSession().selectList(sql, tblTransformCard);
	}
	
	/**
	 * 
	 */
	public  TblTransformCard findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblTransformCard";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblTransformCard> selectAllTblTransformCard(){
		String sql = "selectAll" + "TblTransformCard";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblTransformCardsCount(TblTransformCard tblTransformCard){
		String sql="select_"+"TblTransformCard"+"sCount";
		return getSqlSession().selectOne(sql,tblTransformCard);
	}
	
	public void insert(TblTransformCard tblTransformCard){
		this.getSqlSession().insert("insert_"+"TblTransformCard",
					tblTransformCard);
	}
	
	public void update(TblTransformCard tblTransformCard){
		this.getSqlSession().update("update_"+"TblTransformCard",
					tblTransformCard);
	}
	
}
