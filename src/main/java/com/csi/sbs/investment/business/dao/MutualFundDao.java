package com.csi.sbs.investment.business.dao;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.investment.business.base.BaseDao;
import com.csi.sbs.investment.business.entity.MutualFundEntity;

@Mapper
public interface MutualFundDao<T> extends BaseDao<T> {
	
	
	public int closeAccount(MutualFundEntity ase);
}