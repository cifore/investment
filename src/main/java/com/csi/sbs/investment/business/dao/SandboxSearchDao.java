package com.csi.sbs.investment.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.csi.sbs.investment.business.entity.SandboxSearchEntity;

@Mapper
public interface SandboxSearchDao {
	
	@SuppressWarnings("rawtypes")
	public List<Map> findMany(SandboxSearchEntity ase);
	
	public void delSandBoxData(String sandBoxId);

}