package com.csi.sbs.investment.business.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.InvestmentOpeningAccountModel;
import com.csi.sbs.investment.business.clientmodel.QueryStockModel;
import com.csi.sbs.investment.business.clientmodel.StockHoldingEnquiryModel;
import com.csi.sbs.investment.business.clientmodel.StockTradingModel;
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
			// 获取请求头参数
			String userID = request.getHeader("developerID");
			String countryCode = request.getHeader("countryCode");
			String clearingCode = request.getHeader("clearingCode");
			String branchCode = request.getHeader("branchCode");
			String sandBoxId = request.getHeader("sandBoxId");
			String customerNumber = request.getHeader("customerNumber");
			HeaderModel header = new HeaderModel();
			header.setUserID(userID);
			header.setCountryCode(countryCode);
			header.setClearingCode(clearingCode);
			header.setBranchCode(branchCode);
			header.setSandBoxId(sandBoxId);
			header.setCustomerNumber(customerNumber);
			investmentOpeningAccountModel.setCountrycode(countryCode);
			investmentOpeningAccountModel.setClearingcode(clearingCode);
			investmentOpeningAccountModel.setBranchcode(branchCode);

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
	public ResultUtil stockTradingService(@RequestBody @Validated StockTradingModel stm,HttpServletRequest request)
			throws Exception {
		Map<String, Object> normalmap = null;
		ResultUtil result = new ResultUtil();
		try {
			// 获取请求头参数
			String userID = request.getHeader("developerID");
			String countryCode = request.getHeader("countryCode");
			String clearingCode = request.getHeader("clearingCode");
			String branchCode = request.getHeader("branchCode");
			String sandBoxId = request.getHeader("sandBoxId");
			String customerNumber = request.getHeader("customerNumber");
			HeaderModel header = new HeaderModel();
			header.setUserID(userID);
			header.setCountryCode(countryCode);
			header.setClearingCode(clearingCode);
			header.setBranchCode(branchCode);
			header.setSandBoxId(sandBoxId);
			header.setCustomerNumber(customerNumber);
			normalmap = stockInvestmentService.stockTradingService(header, stm, restTemplate);
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
	public ResultUtil stockHoldingEnquiry(@RequestBody @Validated StockHoldingEnquiryModel sth,
			HttpServletRequest request) throws Exception {
		Map<String, Object> normalmap = null;
		ResultUtil result = new ResultUtil();
		try {
			// 获取请求头参数
			String userID = request.getHeader("developerID");
			String countryCode = request.getHeader("countryCode");
			String clearingCode = request.getHeader("clearingCode");
			String branchCode = request.getHeader("branchCode");
			String sandBoxId = request.getHeader("sandBoxId");
			String customerNumber = request.getHeader("customerNumber");
			HeaderModel header = new HeaderModel();
			header.setUserID(userID);
			header.setCountryCode(countryCode);
			header.setClearingCode(clearingCode);
			if(!StringUtils.isEmpty(sandBoxId)){
				header.setBranchCode(null);
			}else{
				header.setBranchCode(branchCode);
			}
			header.setSandBoxId(sandBoxId);
			header.setCustomerNumber(customerNumber);
			normalmap = stockInvestmentService.stockHoldingEnquiry(header, sth, restTemplate);
			result.setCode(normalmap.get("code").toString());
			result.setMsg(normalmap.get("msg").toString());
			result.setData(normalmap.get("list"));
			return result;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * 获取股票账号
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
	public ResultUtil getStockAccount(@RequestBody @Validated QueryStockModel qsm,
			HttpServletRequest request) throws Exception {
		try {
			return stockInvestmentService.getStockAccount(qsm);
		} catch (Exception e) {
			throw e;
		}
	}

}
