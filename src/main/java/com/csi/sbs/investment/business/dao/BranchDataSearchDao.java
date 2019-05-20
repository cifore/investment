package com.csi.sbs.investment.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.csi.sbs.investment.business.base.BaseDao;
import com.csi.sbs.investment.business.entity.BranchDataSearchEntity;

@Mapper
public interface BranchDataSearchDao<T> extends BaseDao<T> {
	
	@SuppressWarnings("rawtypes")
	public List<Map> findMany(BranchDataSearchEntity bdse);

}
