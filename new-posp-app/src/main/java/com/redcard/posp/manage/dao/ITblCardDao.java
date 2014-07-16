package com.redcard.posp.manage.dao;

import com.redcard.posp.manage.model.TblCard;

public interface ITblCardDao {

	public TblCard findByCardNo(TblCard card);
}
