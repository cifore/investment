package com.csi.sbs.investment.business.dao;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.investment.business.base.BaseDao;
import com.csi.sbs.investment.business.entity.StockInvestmentEntity;


@Mapper
public interface StockInvestmentDao<T> extends BaseDao<T> {
	
	public int closeAccount(StockInvestmentEntity ase);
	
	public void accountOldDateHandle(StockInvestmentEntity se);
}