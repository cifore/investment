package com.csi.sbs.investment.business.service;

import java.util.Map;

import com.csi.sbs.investment.business.entity.SandboxSearchEntity;

public interface SandboxSearchService{
	
	public Map<String, Object> getSearchInfo(SandboxSearchEntity ase) throws Exception;
}