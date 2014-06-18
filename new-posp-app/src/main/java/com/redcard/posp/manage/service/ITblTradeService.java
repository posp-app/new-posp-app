package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblTrade;

public interface ITblTradeService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblTrade tblTrade);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblTrade tblTrade);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblTrade>
	 */
	public List<TblTrade> getTblTradeListByObj(TblTrade tblTrade);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblTrade
	 */
	public TblTrade getTblTradeByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblTrade> findAll();
		
}
