package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblMerchant;

public interface ITblMerchantService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchant tblMerchant);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchant tblMerchant);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblMerchant>
	 */
	public List<TblMerchant> getTblMerchantListByObj(TblMerchant tblMerchant);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblMerchant
	 */
	public TblMerchant getTblMerchantByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblMerchant> findAll();
		
}
