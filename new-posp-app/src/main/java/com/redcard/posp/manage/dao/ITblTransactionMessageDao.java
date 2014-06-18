package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblTransactionMessage;


public interface ITblTransactionMessageDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblTransactionMessage> findListByObj(TblTransactionMessage tblTransactionMessage);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblTransactionMessage findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblTransactionMessage> selectAllTblTransactionMessage();
	
	public Long select_TblTransactionMessagesCount(TblTransactionMessage tblTransactionMessage);
	
	public void insert(TblTransactionMessage tblTransactionMessage);
	
	public void update(TblTransactionMessage tblTransactionMessage);
	
}

