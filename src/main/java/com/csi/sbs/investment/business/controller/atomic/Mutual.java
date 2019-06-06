package com.csi.sbs.investment.business.controller.atomic;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.csi.sbs.common.business.model.HeaderModel;
import com.csi.sbs.common.business.util.HeaderModelUtil;
import com.csi.sbs.investment.business.exception.NotFoundException;
import com.csi.sbs.investment.business.service.MutualFundService;
import com.csi.sbs.investment.business.util.ResultUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin // 解决跨域请求
@Controller
@RequestMapping("/investment/fund")

@Api(value = "Then controller is investment")
public class Mutual {
	
	
	@Resource
	private MutualFundService mutualFundService;
	
	
	/**
	 * 获取基金持有信息
	 * @param accountNumber
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({"rawtypes" })
	@RequestMapping(value = "/fundQuotation/{accountNumber}", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "This API is designed to return fundQuotation.", notes = "version 0.0.1")
	@ApiResponses({ @ApiResponse(code = 200, message = "Query completed successfully.(Returned By Get)"),
			@ApiResponse(code = 404, message = "The requested deposit account does not exist.Action: Please make sure the account number and account type you’re inputting are correct."),
			@ApiResponse(code = 201, message = "Normal execution. The request has succeeded. (Returned By Post)"),
			@ApiResponse(code = 403, message = "Token has incorrect scope or a security policy was violated. Action: Please check whether you’re using the right token with the legal authorized user account."),
			@ApiResponse(code = 500, message = "Something went wrong on the API gateway or micro-service. Action: check your network and try again later."), })
	public ResultUtil fundQuotation(
			@ApiParam(name = "accountNumber", value = "This parameter is required in the GET method. eg: HK720001001000000001001", required = true) @PathVariable("accountNumber") String accountNumber,
			HttpServletRequest request) throws Exception {
		try {
			HeaderModel header = HeaderModelUtil.getHeader(request);
			return mutualFundService.fundQuotation(header, accountNumber);
		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e){
			throw e;
		}
	}

}
