package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblMerchantGroup;

public interface ITblMerchantGroupService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantGroup tblMerchantGroup);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantGroup tblMerchantGroup);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblMerchantGroup>
	 */
	public List<TblMerchantGroup> getTblMerchantGroupListByObj(TblMerchantGroup tblMerchantGroup);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblMerchantGroup
	 */
	public TblMerchantGroup getTblMerchantGroupByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantGroup> findAll();
		
}
