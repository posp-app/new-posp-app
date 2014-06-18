package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblMerchantPos;

public interface ITblMerchantPosService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantPos tblMerchantPos);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantPos tblMerchantPos);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblMerchantPos>
	 */
	public List<TblMerchantPos> getTblMerchantPosListByObj(TblMerchantPos tblMerchantPos);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblMerchantPos
	 */
	public TblMerchantPos getTblMerchantPosByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantPos> findAll();
	
	public void updateKey(TblMerchantPos tblMerchantPos);
		
}
