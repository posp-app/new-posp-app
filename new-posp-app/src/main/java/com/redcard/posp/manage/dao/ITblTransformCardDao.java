package com.redcard.posp.manage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.redcard.posp.manage.model.TblTransformCard;


public interface ITblTransformCardDao {

	/**
	 * 根据自定义的对象查询,返回满足条件集合
	 */
	public List<TblTransformCard> findListByObj(TblTransformCard tblTransformCard);
	/**
	 * 根据主键查询
	 * @param pk_Id
	 * @return
	 */
	public  TblTransformCard findByPKId(@Param("pk_Id") String pk_Id);
	
	/**
	 * 查询所有集合
	 */
	public List<TblTransformCard> selectAllTblTransformCard();
	
	public Long select_TblTransformCardsCount(TblTransformCard tblTransformCard);
	
	public void insert(TblTransformCard tblTransformCard);
	
	public void update(TblTransformCard tblTransformCard);
	
}

