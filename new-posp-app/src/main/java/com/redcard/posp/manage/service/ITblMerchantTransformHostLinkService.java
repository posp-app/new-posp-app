package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblMerchantTransformHostLink;

public interface ITblMerchantTransformHostLinkService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblMerchantTransformHostLink tblMerchantTransformHostLink);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblMerchantTransformHostLink tblMerchantTransformHostLink);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblMerchantTransformHostLink>
	 */
	public List<TblMerchantTransformHostLink> getTblMerchantTransformHostLinkListByObj(TblMerchantTransformHostLink tblMerchantTransformHostLink);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblMerchantTransformHostLink
	 */
	public TblMerchantTransformHostLink getTblMerchantTransformHostLinkByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblMerchantTransformHostLink> findAll();
		
}
