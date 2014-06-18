package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblTransformCard;

public interface ITblTransformCardService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblTransformCard tblTransformCard);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblTransformCard tblTransformCard);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblTransformCard>
	 */
	public List<TblTransformCard> getTblTransformCardListByObj(TblTransformCard tblTransformCard);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblTransformCard
	 */
	public TblTransformCard getTblTransformCardByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblTransformCard> findAll();
		
}
