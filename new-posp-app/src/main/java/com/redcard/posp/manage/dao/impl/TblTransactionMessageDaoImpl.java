package com.redcard.posp.manage.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redcard.posp.manage.dao.ITblTransactionMessageDao;
import com.redcard.posp.manage.model.TblTransactionMessage;


public class TblTransactionMessageDaoImpl extends AbstractiBatisDAO implements ITblTransactionMessageDao  {
	
	final static Logger logger = LoggerFactory.getLogger(TblTransactionMessageDaoImpl.class);
	
	/**
	 * 根据自定义的对象查询,返回对象集合
	 */
	public List<TblTransactionMessage> findListByObj(TblTransactionMessage tblTransactionMessage){
		String sql = "findListByObj" + "_"+"TblTransactionMessage";
		return getSqlSession().selectList(sql, tblTransactionMessage);
	}
	
	/**
	 * 
	 */
	public  TblTransactionMessage findByPKId(@Param("pk_Id") String pk_Id)
	{
		String sql = "findByPKId" + "_"+"TblTransactionMessage";
		return getSqlSession().selectOne(sql, pk_Id);
	}
	
	/**
	 * 查询所有集合
	 */
	public List<TblTransactionMessage> selectAllTblTransactionMessage(){
		String sql = "selectAll" + "TblTransactionMessage";
		return getSqlSession().selectList(sql);
	}
	
	/**
	 * 统计在某个条件下的数量
	 * @param bossCnlProperties
	 * @return
	 */
	public Long select_TblTransactionMessagesCount(TblTransactionMessage tblTransactionMessage){
		String sql="select_"+"TblTransactionMessage"+"sCount";
		return getSqlSession().selectOne(sql,tblTransactionMessage);
	}
	
	public void insert(TblTransactionMessage tblTransactionMessage){
		this.getSqlSession().insert("insert_"+"TblTransactionMessage",
					tblTransactionMessage);
	}
	
	public void update(TblTransactionMessage tblTransactionMessage){
		this.getSqlSession().update("update_"+"TblTransactionMessage",
					tblTransactionMessage);
	}
	
}
