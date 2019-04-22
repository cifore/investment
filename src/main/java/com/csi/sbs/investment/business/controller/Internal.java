package com.csi.sbs.investment.business.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.investment.business.service.StockInvestmentService;
import com.csi.sbs.investment.business.util.ResultUtil;

import springfox.documentation.annotations.ApiIgnore;



@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/investment/datehandle")
public class Internal {
	
	
	@Resource
	private StockInvestmentService stockInvestmentService;
	
	
	
	/**
	 * 账号日期做旧处理
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/accountDateProcess", method = RequestMethod.GET)
	@ResponseBody
	@ApiIgnore()
	public ResultUtil accountDateProcess(HttpServletRequest request)
			throws Exception {
		ResultUtil result = new ResultUtil();
		try {
			stockInvestmentService.accountDateProcess();
			result.setCode("1");
			result.setMsg("Excute Success");
			return result;
		} catch (Exception e) {
			
			result.setCode("0");
			result.setMsg("Excute Fail");
			return result;
		}
	}

}
