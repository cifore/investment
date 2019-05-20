package com.csi.sbs.investment.business.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.investment.business.dao.BranchDataSearchDao;
import com.csi.sbs.investment.business.entity.BranchDataSearchEntity;
import com.csi.sbs.investment.business.service.BranchDataSearchService;

@Service("BranchDataSearchService")
public class BranchDataSearchServiceImpl implements BranchDataSearchService{

	@SuppressWarnings("rawtypes")
	@Resource
	private BranchDataSearchDao branchDataSearchDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getSearchInfo(BranchDataSearchEntity bdse) throws Exception {
		return branchDataSearchDao.findMany(bdse);
	}

}
