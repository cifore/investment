package com.csi.sbs.investment.business.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.csi.sbs.investment.business.entity.SandboxSearchEntity;
import com.csi.sbs.investment.business.service.SandboxSearchService;
import com.csi.sbs.investment.business.dao.SandboxSearchDao;

@Service("SandboxSearchService")
public class SandboxSearchServiceImpl implements SandboxSearchService{
	
	@Resource
	private SandboxSearchDao sandboxSearchDao;

	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> getSearchInfo(SandboxSearchEntity ase) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List list = sandboxSearchDao.findMany(ase);
		map.put("list", list);
		return map;
	}

	@Override
	public void delSandBoxData(String sandBoxId) {
		sandboxSearchDao.delSandBoxData(sandBoxId);
	}
	
}