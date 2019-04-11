package com.csi.sbs.investment.business.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codingapi.tx.annotation.TxTransaction;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.InvestmentOpeningAccountModel;
import com.csi.sbs.investment.business.clientmodel.QueryMutualModel;
import com.csi.sbs.investment.business.clientmodel.ReMutualModel;
import com.csi.sbs.investment.business.clientmodel.CheckAccountNumberModel;
import com.csi.sbs.investment.business.clientmodel.DebitAccountModel;
import com.csi.sbs.investment.business.constant.ExceptionConstant;
import com.csi.sbs.investment.business.constant.SysConstant;
import com.csi.sbs.investment.business.dao.MutualFundDao;
import com.csi.sbs.investment.business.entity.MutualFundEntity;
import com.csi.sbs.investment.business.exception.CallOtherException;
import com.csi.sbs.investment.business.exception.NotFoundException;
import com.csi.sbs.investment.business.service.MutualFundService;
import com.csi.sbs.investment.business.util.AvailableNumberUtil;
import com.csi.sbs.investment.business.util.GenerateAccountNumberUtil;
import com.csi.sbs.investment.business.util.LogUtil;
import com.csi.sbs.investment.business.util.PostUtil;
import com.csi.sbs.investment.business.util.ResultUtil;

@Service("MutualFundService")
public class MutualFundServiceImpl implements MutualFundService {

	@SuppressWarnings("rawtypes")
	@Resource
	private MutualFundDao mutualFundDao;

	private SimpleDateFormat format = new SimpleDateFormat();

	@Resource
	private RestTemplate restTemplate;

	@SuppressWarnings("unchecked")
	@Override
	@TxTransaction(isStart = true)
	@Transactional
	public Map<String, Object> openingFUNccount(HeaderModel header, InvestmentOpeningAccountModel fun,
			RestTemplate restTemplate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		// 获取API的内网地址
		String path = getInternalUrl("accountNumberValidation");
		if (path.length() == 0) {
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),
					ExceptionConstant.ERROR_CODE500002);
		}
		// 校验关联账号格式
		CheckAccountNumberModel checknumber = new CheckAccountNumberModel();
		checknumber.setAccountnumber(fun.getAccountnumber());
		String param = JsonProcess.changeEntityTOJSON(checknumber);
		String debitRes = getResponse(path, param);
		if (debitRes.length() == 0) {
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),
					ExceptionConstant.ERROR_CODE404005);
		}
		JSONObject debitObject = JsonProcess.changeToJSONObject(debitRes);
		String code = JsonProcess.returnValue(debitObject, "code");
		if (code.equals("0")) {
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),
					ExceptionConstant.ERROR_CODE404010);
		}
		// 获取API accountSearch的内网地址
		String path1 = getInternalUrl("accountSearch");
		if (path1.length() == 0) {
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),
					ExceptionConstant.ERROR_CODE500002);
		}
		// 根据 debitAccountNum
		String debitAccountParam = "{\"customerNumber\":\"" + header.getCustomerNumber() + "\",\"accountNumber\":\""
				+ fun.getAccountnumber() + "\",\"countrycode\":\"" + header.getCountryCode() + "\",\"clearingcode\":\""
				+ header.getClearingCode() + "\",\"branchcode\":\"" + header.getBranchCode() + "\"}";
		String debitAccountRes = getResponse(path1, debitAccountParam);
		if (debitAccountRes.length() == 0) {
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),
					ExceptionConstant.ERROR_CODE404005);
		}
		JSONObject transObject = JsonProcess.changeToJSONObject(debitAccountRes);
		String debitAccountInfo = JsonProcess.returnValue(transObject, "account");
		DebitAccountModel resavaccount = JSON.parseObject(debitAccountInfo, DebitAccountModel.class);
		if (resavaccount == null) {
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),
					ExceptionConstant.ERROR_CODE404010);
		}

		// 获取账号
		String accountNumber = null;
		// String localccy = null;
		Map<String, Object> map3 = GenerateAccountNumberUtil.getAccountNumber(SysConstant.ACCOUNT_TYPE8, header,
				restTemplate);
		if (map3.get("code").equals("1")) {
			accountNumber = map3.get("accountNumber").toString();
			// localccy = map3.get("localCCy").toString();
		}
		// model change
		MutualFundEntity account = new MutualFundEntity();
		account.setAccountnumber(accountNumber);
		account.setRelaccountnumber(fun.getAccountnumber());
		account.setAccountstatus(SysConstant.ACCOUNT_STATE2);
		account.setId(UUIDUtil.generateUUID());
		account.setLastupdateddate(format.parse(format.format(new Date())));
		account.setCustomernumber(header.getCustomerNumber());
		account.setCountrycode(fun.getCountrycode());
		account.setClearingcode(fun.getClearingcode());
		account.setBranchcode(fun.getBranchcode());
		account.setSandboxid(header.getSandBoxId());

		mutualFundDao.insert(account);

		// 写入日志
		String logstr = "create accountNumber:" + account.getAccountnumber() + " success!";
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		AvailableNumberUtil.availableNumberIncrease(restTemplate, SysConstant.NEXT_AVAILABLE_ACCOUNTNUMBER);
		map.put("msg", SysConstant.CREATE_SUCCESS_TIP);
		map.put("accountNumber", account.getAccountnumber());
		map.put("code", "1");
		return map;
	}

	// 获取内网地址公共方法
	private String getInternalUrl(String apiname) {
		String param = "{\"apiname\":\"" + apiname + "\"}";
		ResponseEntity<String> result = restTemplate.postForEntity(
				"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
				PostUtil.getRequestEntity(param), String.class);
		JSONObject res = JsonProcess.changeToJSONObject(result.getBody());
		String code = JsonProcess.returnValue(res, "code");
		String responseString = JsonProcess.changeEntityTOJSON(res);
		if (responseString == null || responseString.length() == 0 || ("0").equals(code)) {
			return "";
		} else {
			String path = JsonProcess.returnValue(res, "internaURL");
			return path;
		}
	}

	// 根据传参获取response string
	private String getResponse(String path, String param) {
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param),
				String.class);
		String responseString = responseEntity.getBody();
		if (responseString == null || responseString.length() == 0) {
			return "";
		} else {
			JSONObject responseObject = JsonProcess.changeToJSONObject(responseEntity.getBody());
			String resCode = JsonProcess.returnValue(responseObject, "code");
			if ("0".equals(resCode)) {
				return "";
			}
			return responseString;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public ResultUtil getMutualAccount(QueryMutualModel qmm) {
		List<ReMutualModel> reMutual = new ArrayList<ReMutualModel>();
		ResultUtil result = new ResultUtil();
		//model change
		MutualFundEntity mfe = new MutualFundEntity();
		mfe.setCustomernumber(qmm.getCustomerNumber());
		List<MutualFundEntity> remfe = mutualFundDao.findMany(mfe);
		if(remfe!=null && remfe.size()>0){
			for(int i=0;i<remfe.size();i++){
				ReMutualModel rm = new ReMutualModel();
				rm.setAccountnumber(remfe.get(i).getAccountnumber());
				reMutual.add(rm);
			}
			result.setCode("1");
			result.setMsg("Search Success");
			result.setData(reMutual);
			
			return result;
		}
		result.setCode("0");
		result.setMsg("Search Fail");
		result.setData(reMutual);
		return result;
	}

}