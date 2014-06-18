package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblMerchantGroupTradeType;

public interface ITblMerchantGroupTradeTypeService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantGroupTradeType tblMerchantGroupTradeType);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantGroupTradeType tblMerchantGroupTradeType);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblMerchantGroupTradeType>
	 */
	public List<TblMerchantGroupTradeType> getTblMerchantGroupTradeTypeListByObj(TblMerchantGroupTradeType tblMerchantGroupTradeType);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblMerchantGroupTradeType
	 */
	public TblMerchantGroupTradeType getTblMerchantGroupTradeTypeByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantGroupTradeType> findAll();
		
}
