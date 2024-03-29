package com.csi.sbs.investment.business.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.model.HeaderModel;
import com.csi.sbs.common.business.util.HeaderModelUtil;
import com.csi.sbs.investment.business.clientmodel.CloseAccountModel;
import com.csi.sbs.investment.business.clientmodel.InvestmentOpeningAccountModel;
import com.csi.sbs.investment.business.clientmodel.QueryStockModel;
import com.csi.sbs.investment.business.clientmodel.StockHoldingEnquiryModel;
import com.csi.sbs.investment.business.clientmodel.StockTradingModel;
import com.csi.sbs.investment.business.clientmodel.otherservice.AddStockDepositModel;
import com.csi.sbs.investment.business.constant.ExceptionConstant;
import com.csi.sbs.investment.business.exception.AcceptException;
import com.csi.sbs.investment.business.exception.AuthorityException;
import com.csi.sbs.investment.business.exception.CallOtherException;
import com.csi.sbs.investment.business.exception.DateException;
import com.csi.sbs.investment.business.exception.InsertException;
import com.csi.sbs.investment.business.exception.NotFoundException;
import com.csi.sbs.investment.business.exception.OtherException;
import com.csi.sbs.investment.business.service.StockInvestmentService;
import com.csi.sbs.investment.business.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/investment/stock")

@Api(value = "Then controller is investment")
public class Stock {
	
	
	
	@Resource
	private StockInvestmentService stockInvestmentService;

	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();
	
	
	
	
	
	/**
	 * 开股票账号
	 * 
	 * @param InvestmentOpeningAccountModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/stkAccountOpening", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to create a new stock trading account.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil stkAccountOpening(
			@RequestBody @Validated InvestmentOpeningAccountModel investmentOpeningAccountModel,
			HttpServletRequest request) throws Exception {
		Map<String, Object> normalmap = null;
		ResultUtil result = new ResultUtil();
		try {
			HeaderModel header = HeaderModelUtil.getHeader(request);
			investmentOpeningAccountModel.setCountrycode(header.getCountryCode());
			investmentOpeningAccountModel.setClearingcode(header.getClearingCode());
			investmentOpeningAccountModel.setBranchcode(header.getBranchCode());

			normalmap = stockInvestmentService.openingSTKccount(header, investmentOpeningAccountModel, restTemplate);
			result.setCode(normalmap.get("code").toString());
			result.setMsg(normalmap.get("msg").toString());
			result.setData(normalmap.get("accountNumber") != null ? normalmap.get("accountNumber").toString() : "");
			return result;
		} catch (AcceptException e) {
			throw e;
		} catch (AuthorityException e) {
			throw e;
		} catch (CallOtherException e) {
			throw e;
		} catch (DateException e) {
			throw e;
		} catch (InsertException e) {
			throw e;
		} catch (NotFoundException e) {
			throw e;
		} catch (OtherException e) {
			throw e;
		} catch (Exception e) {
			throw new InsertException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500012),
					ExceptionConstant.ERROR_CODE500012);
		}
	}
	
	
	
	/**
	 * 股票交易
	 * @param stm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/stockTrading", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API allows you to buy/sell stocks.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil stockTrading(@RequestBody @Validated StockTradingModel stockTradingModel,HttpServletRequest request)
			throws Exception {
		Map<String, Object> normalmap = null;
		ResultUtil result = new ResultUtil();
		try {
			HeaderModel header = HeaderModelUtil.getHeader(request);
			normalmap = stockInvestmentService.stockTradingService(header, stockTradingModel, restTemplate);
			result.setCode(normalmap.get("code").toString());
			result.setMsg(normalmap.get("msg").toString());
			result.setData("");
			return result;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 股票持有信息查询
	 * @param sth
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/stockHoldingEnquiry", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to retrieve stock holding information.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil stockHoldingEnquiry(@RequestBody @Validated StockHoldingEnquiryModel stockHoldingEnquiryModel,
			HttpServletRequest request) throws Exception {
		Map<String, Object> normalmap = null;
		ResultUtil result = new ResultUtil();
		try {
			HeaderModel header = HeaderModelUtil.getHeader(request);
			normalmap = stockInvestmentService.stockHoldingEnquiry(header, stockHoldingEnquiryModel, restTemplate);
			result.setCode(normalmap.get("code").toString());
			result.setMsg(normalmap.get("msg").toString());
			result.setData(normalmap.get("list"));
			return result;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 获取股票账号(多个)
	 * @param sth
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getStockAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to retrieve stock information.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	@ApiIgnore()
	public ResultUtil getStockAccount(@RequestBody @Validated QueryStockModel queryStockModel,
			HttpServletRequest request) throws Exception {
		try {
			return stockInvestmentService.getStockAccount(queryStockModel);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 获取股票账号(单个)
	 * @param sth
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getOneStockAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to retrieve stock information.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	@ApiIgnore()
	public ResultUtil getOneStockAccount(@RequestBody @Validated QueryStockModel queryStockModel,
			HttpServletRequest request) throws Exception {
		try {
			return stockInvestmentService.getOneStockAccount(queryStockModel);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 账号关闭
	 * @param cam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/accountClosure", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to set an account status to closed.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	@ApiIgnore()
	public ResultUtil accountClosure(@RequestBody CloseAccountModel closeAccountModel) throws Exception {
		try {
			return stockInvestmentService.closeAccount(closeAccountModel, restTemplate);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/addAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to add Account.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	@ApiIgnore()
	public ResultUtil addAccount(@RequestBody AddStockDepositModel addStockDepositModel) throws Exception {
		try {
			int i = stockInvestmentService.save(addStockDepositModel);
			ResultUtil result = new ResultUtil();
			if(i>0){
				result.setCode("1");
				result.setMsg("Created Success");
			}else{
				result.setCode("0");
				result.setMsg("Created Fail");
			}
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

}
