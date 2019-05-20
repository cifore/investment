package com.csi.sbs.investment.business.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.investment.business.entity.BranchDataSearchEntity;
import com.csi.sbs.investment.business.entity.SandboxSearchEntity;

import com.csi.sbs.investment.business.clientmodel.otherservice.SeSandBoxIdModel;
import com.csi.sbs.investment.business.service.StockInvestmentService;
import com.csi.sbs.investment.business.service.BranchDataSearchService;
import com.csi.sbs.investment.business.service.SandboxSearchService;
import com.csi.sbs.investment.business.util.ResultUtil;

import springfox.documentation.annotations.ApiIgnore;



@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/investment/datehandle")
public class Internal {
	
	
	@Resource
	private StockInvestmentService stockInvestmentService;
	
	@Resource
	private SandboxSearchService sandboxSearchService;
	
	@Resource
	private BranchDataSearchService branchDataSearchService;
	
	
	/**
	 * 账号日期做旧处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/accountDateProcess/{sandBoxId}", method = RequestMethod.GET)
	@ResponseBody
	@ApiIgnore()
	public ResultUtil accountDateProcess(@PathVariable("sandBoxId") String sandBoxId,HttpServletRequest request)
			throws Exception {
		ResultUtil result = new ResultUtil();
		try {
			SeSandBoxIdModel sm = new SeSandBoxIdModel();
			sm.setSandBoxId(sandBoxId);
			stockInvestmentService.accountDateProcess(sm);
			result.setCode("1");
			result.setMsg("Excute Success");
			return result;
		} catch (Exception e) {
			
			result.setCode("0");
			result.setMsg("Excute Fail");
			return result;
		}
	}
	
	@RequestMapping(value = "/sandboxSearch", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public Map<String, Object>sandboxSearch(@RequestBody SandboxSearchEntity ase, HttpServletRequest request) throws Exception {
		return sandboxSearchService.getSearchInfo(ase);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/branchDataSearch", method = RequestMethod.POST)
	@ResponseBody
	@ApiIgnore()
	public List branchDataSearch(@RequestBody BranchDataSearchEntity bdse, HttpServletRequest request) throws Exception {
		return branchDataSearchService.getSearchInfo(bdse);
	}
	
	
	/**
	 * 根据sandBoxId删除 sandBox数据
	 * @param sandBoxId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/delSandBoxId/{sandBoxId}", method = RequestMethod.GET)
	@ResponseBody
	@ApiIgnore()
	public ResultUtil delSandBoxId(@PathVariable("sandBoxId") String sandBoxId,
			HttpServletRequest request)
			throws Exception {
		ResultUtil result = new ResultUtil();
		try {
			sandboxSearchService.delSandBoxData(sandBoxId);
			result.setCode("1");
			result.setMsg("Delete SandBox Data Success");
			return result;
		} catch (Exception e) {
			result.setCode("0");
			result.setMsg("Delete SandBox Data Fail");
			return result;
		}
	}

}
