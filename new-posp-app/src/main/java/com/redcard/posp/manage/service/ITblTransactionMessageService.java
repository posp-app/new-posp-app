package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblTransactionMessage;

public interface ITblTransactionMessageService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblTransactionMessage tblTransactionMessage);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblTransactionMessage tblTransactionMessage);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblTransactionMessage>
	 */
	public List<TblTransactionMessage> getTblTransactionMessageListByObj(TblTransactionMessage tblTransactionMessage);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblTransactionMessage
	 */
	public TblTransactionMessage getTblTransactionMessageByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblTransactionMessage> findAll();
		
}
