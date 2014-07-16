package com.redcard.posp.manage.service.impl;

import com.redcard.posp.manage.dao.ITblCardDao;
import com.redcard.posp.manage.model.TblCard;
import com.redcard.posp.manage.service.ITblCardService;

public class TblCardServiceImpl implements ITblCardService {
	
	private ITblCardDao tblCardDao;

	public TblCard findByCardNo(TblCard card) {
		return this.getTblCardDao().findByCardNo(card);
	}

	public ITblCardDao getTblCardDao() {
		return tblCardDao;
	}

	public void setTblCardDao(ITblCardDao tblCardDao) {
		this.tblCardDao = tblCardDao;
	}

}
