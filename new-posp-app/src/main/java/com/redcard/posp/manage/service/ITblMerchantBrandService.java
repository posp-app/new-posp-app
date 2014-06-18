package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblMerchantBrand;

public interface ITblMerchantBrandService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantBrand tblMerchantBrand);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantBrand tblMerchantBrand);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblMerchantBrand>
	 */
	public List<TblMerchantBrand> getTblMerchantBrandListByObj(TblMerchantBrand tblMerchantBrand);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblMerchantBrand
	 */
	public TblMerchantBrand getTblMerchantBrandByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantBrand> findAll();
		
}
