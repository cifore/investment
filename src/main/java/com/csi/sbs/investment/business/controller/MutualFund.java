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

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

import com.csi.sbs.common.business.exception.CallOtherException;
import com.csi.sbs.common.business.model.HeaderModel;
import com.csi.sbs.common.business.util.HeaderModelUtil;
import com.csi.sbs.investment.business.clientmodel.CloseAccountModel;
import com.csi.sbs.investment.business.clientmodel.FundBuyTradingModel;
import com.csi.sbs.investment.business.clientmodel.FundHoldingEnquiryModel;
import com.csi.sbs.investment.business.clientmodel.FundSellTradingModel;
import com.csi.sbs.investment.business.clientmodel.InvestmentOpeningAccountModel;
import com.csi.sbs.investment.business.clientmodel.QueryMutualModel;
import com.csi.sbs.investment.business.clientmodel.otherservice.AddMutualDepositModel;
import com.csi.sbs.investment.business.constant.ExceptionConstant;
import com.csi.sbs.investment.business.exception.AcceptException;
import com.csi.sbs.investment.business.exception.AuthorityException;
import com.csi.sbs.investment.business.exception.DateException;
import com.csi.sbs.investment.business.exception.InsertException;
import com.csi.sbs.investment.business.exception.NotFoundException;
import com.csi.sbs.investment.business.exception.OtherException;
import com.csi.sbs.investment.business.service.FundMarketInfoService;
import com.csi.sbs.investment.business.service.MutualFundService;
import com.csi.sbs.investment.business.util.ResultUtil;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/fund")

@Api(value = "Then controller is mutual fund account")
public class MutualFund {

	@Resource
	private MutualFundService mutualFundService;
	
	@Resource
	private FundMarketInfoService fundMarketInfoService;

	@Resource
	private RestTemplate restTemplate;

	ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 开基金账号
	 * 
	 * @param InvestmentOpeningAccountModel
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/fundAccountOpening", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to create a new mutual fund account.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil fundAccountOpening(
			@RequestBody @Validated InvestmentOpeningAccountModel investmentOpeningAccountModel,
			HttpServletRequest request) throws Exception {
		Map<String, Object> normalmap = null;
		ResultUtil result = new ResultUtil();
		try {
			HeaderModel header = HeaderModelUtil.getHeader(request);
			investmentOpeningAccountModel.setCountrycode(header.getCountryCode());
			investmentOpeningAccountModel.setClearingcode(header.getClearingCode());
			investmentOpeningAccountModel.setBranchcode(header.getBranchCode());

			normalmap = mutualFundService.openingFUNccount(header, investmentOpeningAccountModel, restTemplate);
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
	 * Alina基金买入
	 * 
	 * @param fundBuyTradingService
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/subscription", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to subscribe funds.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil subscription(@RequestBody @Validated FundBuyTradingModel fundBuyTradingModel, HttpServletRequest request)
			throws Exception {
		try {
			HeaderModel header = HeaderModelUtil.getHeader(request);
			return fundMarketInfoService.fundBuyTradingService(header, fundBuyTradingModel, restTemplate);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Alina基金卖出
	 * 
	 * @param fundBuyTradingService
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/redemption", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to redeem funds.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil redemption(@RequestBody @Validated FundSellTradingModel fundSellTradingModel, HttpServletRequest request)
			throws Exception {
		try {
			HeaderModel header = HeaderModelUtil.getHeader(request);
			return fundMarketInfoService.fundSellTradingService(header, fundSellTradingModel, restTemplate);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/holdingEnquiry", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to retrieve fund holding information.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil holdingEnquiry(@RequestBody @Validated FundHoldingEnquiryModel fundHoldingEnquiryModel, HttpServletRequest request)
			throws Exception {
		try {
			HeaderModel header = HeaderModelUtil.getHeader(request);
			return fundMarketInfoService.mutualfundHoldingEnquiry(header, fundHoldingEnquiryModel, restTemplate);
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * 获取基金账号(多个)
	 * @param qmm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getMutualAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to retrieve Mutual information.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	@ApiIgnore()
	public ResultUtil getMutualAccount(@RequestBody @Validated QueryMutualModel queryMutualModel,
			HttpServletRequest request) throws Exception {
		try {
			return mutualFundService.getMutualAccount(queryMutualModel);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 获取基金账号(单个)
	 * @param qmm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getOneMutualAccount", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "This API is designed to retrieve Mutual information.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	@ApiIgnore()
	public ResultUtil getOneMutualAccount(@RequestBody @Validated QueryMutualModel queryMutualModel,
			HttpServletRequest request) throws Exception {
		try {
			return mutualFundService.getOneMutualAccount(queryMutualModel);
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
			return mutualFundService.closeAccount(closeAccountModel, restTemplate);
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
	public ResultUtil addAccount(@RequestBody AddMutualDepositModel addMutualDepositModel) throws Exception {
		try {
			int i = mutualFundService.save(addMutualDepositModel);
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
