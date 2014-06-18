package com.redcard.posp.manage.service;


import java.util.List;

import com.redcard.posp.manage.model.TblProxyHost;

public interface ITblProxyHostService {
	
	/**
	 * 新增
	 * @param  POJO对象
	 * @return String
	 */
	public String add(TblProxyHost tblProxyHost);
	
	
	/**
	 * 修改
	 * 
	 * @param  POJO对象
	 * @return String
	 */
	public String update(TblProxyHost tblProxyHost);
	
	
	
	/**
	 * 根据对象获取信息列表
	 * @param  POJO对象
	 * @return List<TblProxyHost>
	 */
	public List<TblProxyHost> getTblProxyHostListByObj(TblProxyHost tblProxyHost);
	
	/**
	 * 根据主键获取信息
	 * @param  POJO对象
	 * @return TblProxyHost
	 */
	public TblProxyHost getTblProxyHostByPk(String pk_Id);
	
	/**
	 * 查询所有
	 */
	public List<TblProxyHost> findAll();
	
	public void updateByMerchantNo(TblProxyHost tblProxyHost);
		
}
