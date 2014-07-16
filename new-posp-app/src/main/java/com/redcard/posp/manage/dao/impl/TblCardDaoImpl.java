package com.redcard.posp.manage.dao.impl;

import java.util.List;

import com.redcard.posp.manage.dao.ITblCardDao;
import com.redcard.posp.manage.model.TblCard;

public class TblCardDaoImpl extends AbstractiBatisDAO implements ITblCardDao {

	public TblCard findByCardNo(TblCard card) {
		String sql = "selectTblCard";
		List<TblCard>  cList = getSqlSession().selectList(sql, card);
		return (cList!=null&&cList.size()>0)?cList.get(0):null;
	}

}
