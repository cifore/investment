package com.csi.sbs.investment.business.service;

import java.util.List;

import com.csi.sbs.investment.business.entity.BranchDataSearchEntity;

public interface BranchDataSearchService {
	
	@SuppressWarnings("rawtypes")
	public List getSearchInfo(BranchDataSearchEntity bdse) throws Exception;

}
