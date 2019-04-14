package com.csi.sbs.investment.business.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.investment.business.exception.InsertException;
import com.csi.sbs.investment.business.exception.SearchException;
import com.csi.sbs.investment.business.exception.UpdateException;
import com.csi.sbs.investment.business.clientmodel.DebitAccountModel;
import com.csi.sbs.investment.business.clientmodel.CheckAccountNumberModel;
import com.csi.sbs.investment.business.clientmodel.FundBuyTradingModel;
import com.csi.sbs.investment.business.clientmodel.FundHoldingEnquiryModel;
import com.csi.sbs.investment.business.clientmodel.FundInvestmentModel;
import com.csi.sbs.investment.business.clientmodel.FundSellTradingModel;
import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.CurrencyInfoModel;
import com.csi.sbs.investment.business.clientmodel.InsertTransactionLogModel;
import com.csi.sbs.investment.business.clientmodel.UpdateAccountBalanceModel;
import com.csi.sbs.investment.business.constant.ExceptionConstant;
import com.csi.sbs.investment.business.constant.SysConstant;
import com.csi.sbs.investment.business.service.FundMarketInfoService;
import com.csi.sbs.investment.business.util.LogUtil;
import com.csi.sbs.investment.business.util.PostUtil;
import com.csi.sbs.investment.business.util.ResultUtil;
import com.csi.sbs.investment.business.dao.FundMarketInfoDao;
import com.csi.sbs.investment.business.dao.FundHoldingDao;
import com.csi.sbs.investment.business.dao.MutualFundDao;
import com.csi.sbs.investment.business.dao.FundPlatformLogDao;
import com.csi.sbs.investment.business.entity.FundHoldingEntity;
import com.csi.sbs.investment.business.entity.FundPlatformLogEntity;
import com.csi.sbs.investment.business.entity.MutualFundEntity;
import com.csi.sbs.investment.business.entity.FundMarketInfoEntity;
import com.csi.sbs.investment.business.exception.AcceptException;
import com.csi.sbs.investment.business.exception.CallOtherException;
import com.csi.sbs.investment.business.exception.NotFoundException;
import com.csi.sbs.investment.business.exception.OtherException;

@Service("FundMarketInfoService")
public class FundMarketInfoServiceImpl implements FundMarketInfoService{
	
	@SuppressWarnings("rawtypes")
	@Resource
	private MutualFundDao mutualFundDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private FundHoldingDao fundHoldingDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private FundMarketInfoDao fundMarketInfoDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private FundPlatformLogDao fundPlatformLogDao;
	
	@Resource
	private RestTemplate restTemplate;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil fundBuyTradingService(HeaderModel header, FundBuyTradingModel ase, RestTemplate restTemplate)
			throws Exception {
		ResultUtil result = new ResultUtil();
		//买入金额
		BigDecimal tradingAmount = null;
		//新余额
		BigDecimal newBalance = null;
		//交易金额
		BigDecimal transactionamount = null; 
		//买入股份
		BigDecimal sharingNo = null;
		//交易费用
		BigDecimal trdingcommission = null;
		//新平均金额
		BigDecimal newAverage = null;
		
		//获取API的内网地址
        String path = getInternalUrl("accountNumberValidation");
        if(path.length() == 0){
        	throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
		//校验基金账号格式
		CheckAccountNumberModel  checknumber =  new CheckAccountNumberModel();
		checknumber.setAccountnumber(ase.getFundaccountnumber());
		String param = JsonProcess.changeEntityTOJSON(checknumber);
		String fundRes = getResponse(path,param);
  		if(fundRes.length() == 0){
  			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
  		}
		JSONObject fundObject = JsonProcess.changeToJSONObject(fundRes);
		String code = JsonProcess.returnValue(fundObject, "code");
		if(code.equals("0")){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		//校验debit account格式
		checknumber.setAccountnumber(ase.getDebitaccountnumber());
		param = JsonProcess.changeEntityTOJSON(checknumber);
		String debitRes = getResponse(path,param);
  		if(debitRes.length() == 0){
  			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
  		}
		JSONObject debitObject = JsonProcess.changeToJSONObject(debitRes);
		code = JsonProcess.returnValue(debitObject, "code");
		if(code.equals("0")){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}

		//Read Mutual Fund Account Master file with the inputted Fund Trading Account Number. If record does not exist, reject the transaction 
		MutualFundEntity fundaccountInfo = new MutualFundEntity();
		fundaccountInfo.setAccountnumber(ase.getFundaccountnumber());
		fundaccountInfo.setCountrycode(header.getCountryCode());
		fundaccountInfo.setClearingcode(header.getClearingCode());
		fundaccountInfo.setSandboxid(header.getSandBoxId());
		if(!StringUtils.isEmpty(fundaccountInfo.getSandboxid())){
			fundaccountInfo.setBranchcode(null);
		}else{
			fundaccountInfo.setBranchcode(header.getBranchCode());
		}
		MutualFundEntity fundaccount = (MutualFundEntity) mutualFundDao.findOne(fundaccountInfo);
		if(fundaccount == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		if(!fundaccount.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);	
		}	
		//Read Account Master File by using the Input Transfer From account number. If record does not exist, reject the transaction
		String relaccountType = ase.getDebitaccountnumber().substring(ase.getDebitaccountnumber().length() - 3);
		if(relaccountType.equals(SysConstant.ACCOUNT_TYPE1)== false && relaccountType.equals(SysConstant.ACCOUNT_TYPE2)== false){
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201001),ExceptionConstant.ERROR_CODE201001);
		}
		
		//获取API accountSearch的内网地址
        String path1 = getInternalUrl("accountSearch");
        if(path1.length() == 0){
        	throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
       //根据 debitAccountNum
  		String debitAccountParam = "{\"customerNumber\":\"" + header.getCustomerNumber() +"\",\"accountNumber\":\"" + ase.getDebitaccountnumber() +"\",\"countrycode\":\"" + header.getCountryCode() +"\",\"clearingcode\":\"" + header.getClearingCode() +"\",\"branchcode\":\"" + header.getBranchCode() +"\",\"sandboxid\":\"" + header.getSandBoxId() +"\"}";
  		String debitAccountRes = getResponse(path1,debitAccountParam);
  		if(debitAccountRes.length() == 0){
  			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
  		}
  		JSONObject transObject = JsonProcess.changeToJSONObject(debitAccountRes);
		String debitAccountInfo = JsonProcess.returnValue(transObject, "account");
		DebitAccountModel resavaccount = JSON.parseObject(debitAccountInfo, DebitAccountModel.class);
		if(resavaccount == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		
		if(!resavaccount.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}

		FundMarketInfoEntity fundMarketInfoEntity =  new FundMarketInfoEntity();
		fundMarketInfoEntity.setFundcode(ase.getFundcode());
		FundMarketInfoEntity fundInfo = (FundMarketInfoEntity) fundMarketInfoDao.findOne(fundMarketInfoEntity);
		if(fundInfo == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404013),ExceptionConstant.ERROR_CODE404013);
		}
		
		if(!fundInfo.getFundcurrency().equals(resavaccount.getCurrencycode())){
			// 调用服务接口地址
			String param1 = "{\"apiname\":\"queryByCcyCode\"}";
			ResponseEntity<String> result1 = restTemplate.postForEntity(
					"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
					PostUtil.getRequestEntity(param1), String.class);
			if (result1.getStatusCodeValue() != 200) {
				throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
			}
			String path2 = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
			String currencyParam = "{\"ccycode\":\"" +fundInfo.getFundcurrency() + "\"}";
			String result2 = getResponse(path2,currencyParam);
			if(result2.length() == 0){
				throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500001),ExceptionConstant.ERROR_CODE500001);
			}
			CurrencyInfoModel currencyInfo = JSON.parseObject(result2,CurrencyInfoModel.class);
			BigDecimal rate = currencyInfo.getBanksell();
			transactionamount = ase.getTradingamount().multiply(rate).setScale(4, BigDecimal.ROUND_UP);
		}else{
			transactionamount = ase.getTradingamount();
		}
		
		//If the potential transaction amount is larger than Account Balance, reject the transaction
		if(transactionamount.compareTo(resavaccount.getBalance()) > 0){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202002),ExceptionConstant.ERROR_CODE202002);
		}
		
		//买入手续费
		trdingcommission = ase.getTradingamount().multiply(fundInfo.getManagementfee()).setScale(4, BigDecimal.ROUND_UP);
		//买入金额
		tradingAmount = ase.getTradingamount().subtract(trdingcommission);
		//买入股数
		sharingNo = tradingAmount.divide(fundInfo.getLastnav(),4, BigDecimal.ROUND_UP);
		
		FundHoldingEntity fundHoldingEntity = new FundHoldingEntity();
		fundHoldingEntity.setAccountnumber(ase.getFundaccountnumber());
		fundHoldingEntity.setFundcode(ase.getFundcode());
		FundHoldingEntity fundHoldInfo = (FundHoldingEntity) fundHoldingDao.findOne(fundHoldingEntity);
		if(fundHoldInfo == null){
			fundHoldingEntity.setId(UUIDUtil.generateUUID());
			fundHoldingEntity.setCurrencycode(fundInfo.getFundcurrency());
			fundHoldingEntity.setSharesholding(sharingNo);
			fundHoldingEntity.setAverageprice(ase.getTradingamount().divide(sharingNo, 4, BigDecimal.ROUND_HALF_UP));
			fundHoldingEntity.setLastupdatedate(format.parse(format.format(new Date())));
			fundHoldingDao.insert(fundHoldingEntity);
		}else{
			newAverage = (fundHoldInfo.getAverageprice().multiply(fundHoldInfo.getSharesholding()).add(ase.getTradingamount())).divide(fundHoldInfo.getSharesholding().add(sharingNo),4, BigDecimal.ROUND_UP);
			fundHoldingEntity.setId(fundHoldInfo.getId());
			fundHoldingEntity.setSharesholding(fundHoldInfo.getSharesholding().add(sharingNo));
			fundHoldingEntity.setAverageprice(newAverage);
			fundHoldingEntity.setLastupdatedate(format.parse(format.format(new Date())));
			fundHoldingDao.update(fundHoldingEntity);
		}
		
		FundPlatformLogEntity fundPlatformLogEntity =  new FundPlatformLogEntity();
		fundPlatformLogEntity.setId(UUIDUtil.generateUUID());
		fundPlatformLogEntity.setAccountnumber(ase.getFundaccountnumber());
		fundPlatformLogEntity.setBranchcode(header.getBranchCode());
		fundPlatformLogEntity.setSandboxid(header.getSandBoxId());
		fundPlatformLogEntity.setClearingcode(header.getClearingCode());
		fundPlatformLogEntity.setCountrycode(header.getCountryCode());
		fundPlatformLogEntity.setCurrencycode(fundInfo.getFundcurrency());
		fundPlatformLogEntity.setFundcode(ase.getFundcode());
		fundPlatformLogEntity.setSharingno(sharingNo);
		fundPlatformLogEntity.setTradingamount(tradingAmount);
		fundPlatformLogEntity.setTradingoption("Buy");
		fundPlatformLogEntity.setTransactionamount(ase.getTradingamount());
		fundPlatformLogEntity.setTransactiondate(format.parse(format.format(new Date())));
		fundPlatformLogEntity.setTrdingcommission(trdingcommission);
		fundPlatformLogDao.insert(fundPlatformLogEntity);
		
		// 写入日志
		String logstr = "Transaction Accepted:" + ase.getFundaccountnumber();
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		
		//Add/reduce transaction amount from Transfer From/To account depends on the trading option.
		newBalance = resavaccount.getBalance().subtract(transactionamount);
		String path2 = getInternalUrl("updateAccountBalance");
		if(path2.length() == 0){
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
		//更新 debit account master file 和transaction log file
		String updateDebitAccountParam = "{\"accountnumber\":\""+ ase.getDebitaccountnumber() +"\",\"balance\":\""+ newBalance +"\"}";
		String updateDebitAccountRes = getResponse(path2, updateDebitAccountParam);
		if(updateDebitAccountRes.length() == 0){
			throw new UpdateException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500012),ExceptionConstant.ERROR_CODE500012);
		}

		// 调用服务接口地址 获得transacitonLogAdding得内网地址
		String path3 = getInternalUrl("transactionLogAdding");
		if(path3.length() == 0){
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
		//Update the transaction history file
		InsertTransactionLogModel insertTransacitonlog = new InsertTransactionLogModel();
		insertTransacitonlog.setAccountnumber(ase.getDebitaccountnumber());
		insertTransacitonlog.setActualbalamt(newBalance);
		insertTransacitonlog.setBranchcode(header.getBranchCode());
		insertTransacitonlog.setSandboxid(header.getSandBoxId());
		insertTransacitonlog.setCcy(resavaccount.getCurrencycode());
		insertTransacitonlog.setChannel(SysConstant.CHANNEL_TYPE);
		insertTransacitonlog.setChannelid(header.getUserID());
		insertTransacitonlog.setClearingcode(header.getClearingCode());
		insertTransacitonlog.setCountrycode(header.getCountryCode());
		insertTransacitonlog.setPreviousbalamt(resavaccount.getBalance());
		insertTransacitonlog.setTranamt(transactionamount);
		insertTransacitonlog.setCrdrmaintind(SysConstant.CR_DR_MAINT_IND_TYPE1);
		insertTransacitonlog.setTrantype(SysConstant.TRANSACTION_TYPE11);
		insertTransacitonlog.setTrandesc("fund buy");
		
		//更新 transaction detail file
		String insertTransactionString = JsonProcess.changeEntityTOJSON(insertTransacitonlog);
		
		ResponseEntity<String> insertRes = restTemplate.postForEntity(path3,PostUtil.getRequestEntity(insertTransactionString),String.class);
		if (insertRes.getStatusCodeValue() != 200) {
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		JSONObject str1 = XmlToJsonUtil.xml2JSON(insertRes.getBody().getBytes());
		String str2 = JsonProcess.returnValue(str1, "ResultUtil");
		ResultUtil<JSONArray> str3 = JSON.parseObject(str2, ResultUtil.class);
		if(str3.getCode().substring(1, str3.getCode().length()-1).equals("0")){
			throw new InsertException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500004),ExceptionConstant.ERROR_CODE500004);
		}
		
		// 写入日志
		String logstr1 = "Transaction Accepted:" + ase.getDebitaccountnumber();
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr1);
		result.setCode("1");
		result.setMsg("Transaction Accepted");
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil fundSellTradingService(HeaderModel header, FundSellTradingModel ase, RestTemplate restTemplate)
			throws Exception {
		ResultUtil result = new ResultUtil();
		//卖出金额
		BigDecimal tradingAmount = null;
		//新余额
		BigDecimal newBalance = null;
		//入账金额
		BigDecimal transactionamount = null;
		//剩余股份
		BigDecimal newSharingNo = null;
		//交易费用
		BigDecimal trdingcommission = null;
		
		//获取API的内网地址
        String path = getInternalUrl("accountNumberValidation");
        if(path.length() == 0){
        	throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
		//校验基金账号格式
		CheckAccountNumberModel  checknumber =  new CheckAccountNumberModel();
		checknumber.setAccountnumber(ase.getFundaccountnumber());
		String param = JsonProcess.changeEntityTOJSON(checknumber);
		String fundRes = getResponse(path,param);
  		if(fundRes.length() == 0){
  			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),ExceptionConstant.ERROR_CODE404005);
  		}
		JSONObject fundObject = JsonProcess.changeToJSONObject(fundRes);
		String code = JsonProcess.returnValue(fundObject, "code");
		if(code.equals("0")){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		//校验debit account格式
		checknumber.setAccountnumber(ase.getDebitaccountnumber());
		param = JsonProcess.changeEntityTOJSON(checknumber);
		String debitRes = getResponse(path,param);
  		if(debitRes.length() == 0){
  			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),ExceptionConstant.ERROR_CODE404005);
  		}
		JSONObject debitObject = JsonProcess.changeToJSONObject(debitRes);
		code = JsonProcess.returnValue(debitObject, "code");
		if(code.equals("0")){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		Boolean strResult = ase.getSharingNo().matches("-?[0-9]+.*[0-9]*"); 
		if(!strResult){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202022),ExceptionConstant.ERROR_CODE202022);
		}
		//Read Mutual Fund Account Master file with the inputted Mutual Fund Trading Account Number. If record does not exist, reject the transaction 
		MutualFundEntity fundaccountInfo = new MutualFundEntity();
		fundaccountInfo.setAccountnumber(ase.getFundaccountnumber());
		fundaccountInfo.setCountrycode(header.getCountryCode());
		fundaccountInfo.setSandboxid(header.getSandBoxId());
		fundaccountInfo.setClearingcode(header.getClearingCode());
		fundaccountInfo.setCustomernumber(header.getCustomerNumber());
		if(!StringUtils.isEmpty(fundaccountInfo.getSandboxid())){
			fundaccountInfo.setBranchcode(null);
		}else{
			fundaccountInfo.setBranchcode(header.getBranchCode());
		}
		MutualFundEntity fundaccount = (MutualFundEntity) mutualFundDao.findOne(fundaccountInfo);
		if(fundaccount == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		if(!fundaccount.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		//Read Account Master File by using the Input Transfer From/To account number. If record does not exist, reject the transaction
		String relaccountType = ase.getDebitaccountnumber().substring(ase.getDebitaccountnumber().length() - 3);
		if(relaccountType.equals(SysConstant.ACCOUNT_TYPE1)== false && relaccountType.equals(SysConstant.ACCOUNT_TYPE2)== false){
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201001),ExceptionConstant.ERROR_CODE201001);
		}
		
		//获取API accountSearch的内网地址
        String path1 = getInternalUrl("accountSearch");
        if(path1.length() == 0){
        	throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
       //根据 debitAccountNum
  		String debitAccountParam = "{\"accountNumber\":\"" + ase.getDebitaccountnumber() +"\",\"countrycode\":\"" + header.getCountryCode() +"\",\"clearingcode\":\"" + header.getClearingCode() +"\",\"branchcode\":\"" + header.getBranchCode() +"\",\"sandboxid\":\"" + header.getSandBoxId() +"\"}";
  		String debitAccountRes = getResponse(path1,debitAccountParam);
  		if(debitAccountRes.length() == 0){
  			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404005),ExceptionConstant.ERROR_CODE404005);
  		}
  		JSONObject transObject = JsonProcess.changeToJSONObject(debitAccountRes);
		String debitAccountInfo = JsonProcess.returnValue(transObject, "account");
		DebitAccountModel resavaccount = JSON.parseObject(debitAccountInfo, DebitAccountModel.class);
		if(resavaccount == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		
		if(!resavaccount.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		
		
		// 调用服务接口地址
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(
				"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		String path2 = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
		
		// 调用系统参数服务接口
		String param2 = "{\"item\":\"FundSellManagementFee\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path2, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		JSONArray restr = JsonProcess.changeToJSONArray(result2.getBody());
		String managementfee = JsonProcess.returnValue(JsonProcess.changeToJSONObject(restr.get(0).toString()), "value");
		
		FundMarketInfoEntity fundMarketInfoEntity =  new FundMarketInfoEntity();
		fundMarketInfoEntity.setFundcode(ase.getFundcode());
		FundMarketInfoEntity fundInfo = (FundMarketInfoEntity) fundMarketInfoDao.findOne(fundMarketInfoEntity);
		if(fundInfo == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404013),ExceptionConstant.ERROR_CODE404013);
		}
		FundHoldingEntity fundHoldingEntity = new FundHoldingEntity();
		fundHoldingEntity.setAccountnumber(ase.getFundaccountnumber());
		fundHoldingEntity.setFundcode(ase.getFundcode());
		FundHoldingEntity fundHoldInfo = (FundHoldingEntity) fundHoldingDao.findOne(fundHoldingEntity);
		if(fundHoldInfo == null){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202023),ExceptionConstant.ERROR_CODE202023);
		}
		
		if(new BigDecimal(ase.getSharingNo()).compareTo(fundHoldInfo.getSharesholding()) > 0){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202022),ExceptionConstant.ERROR_CODE202022);
		}
		
		if(new BigDecimal(ase.getSharingNo()).compareTo(fundHoldInfo.getSharesholding()) == 0){
			fundHoldingDao.delete(fundHoldInfo.getId());
		}else{
			newSharingNo = fundHoldInfo.getSharesholding().subtract(new BigDecimal(ase.getSharingNo()));
			fundHoldingEntity.setSharesholding(newSharingNo);
			fundHoldingEntity.setLastupdatedate(format.parse(format.format(new Date())));
			fundHoldingEntity.setId(fundHoldInfo.getId());
			fundHoldingDao.update(fundHoldingEntity);
		}
		
		tradingAmount = new BigDecimal(ase.getSharingNo()).multiply(fundInfo.getLastnav());
		trdingcommission = tradingAmount.multiply(new BigDecimal(managementfee));
		tradingAmount = tradingAmount.subtract(trdingcommission);
		if(!fundInfo.getFundcurrency().equals(resavaccount.getCurrencycode())){
			// 调用服务接口地址
			String param3 = "{\"apiname\":\"queryByCcyCode\"}";
			ResponseEntity<String> result3 = restTemplate.postForEntity(
					"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
					PostUtil.getRequestEntity(param3), String.class);
			if (result3.getStatusCodeValue() != 200) {
				throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
			}
			String path3 = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result3.getBody()), "internaURL");
			String currencyParam =  "{\"ccycode\":\"" + fundInfo.getFundcurrency() + "\"}";
			String result4 = getResponse(path3,currencyParam);
			if(result4.length() == 0){
				throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500001),ExceptionConstant.ERROR_CODE500001);
			}
			CurrencyInfoModel currencyInfo = JSON.parseObject(result4,CurrencyInfoModel.class);
			BigDecimal rate = currencyInfo.getBankbuy();
			transactionamount = tradingAmount.multiply(rate);
		}else{
			transactionamount = tradingAmount;
		}
		FundPlatformLogEntity fundPlatformLogEntity =  new FundPlatformLogEntity();
		fundPlatformLogEntity.setId(UUIDUtil.generateUUID());
		fundPlatformLogEntity.setAccountnumber(ase.getFundaccountnumber());
		fundPlatformLogEntity.setBranchcode(header.getBranchCode());
		fundPlatformLogEntity.setSandboxid(header.getSandBoxId());
		fundPlatformLogEntity.setClearingcode(header.getClearingCode());
		fundPlatformLogEntity.setCountrycode(header.getCountryCode());
		fundPlatformLogEntity.setCurrencycode(fundInfo.getFundcurrency());
		fundPlatformLogEntity.setFundcode(ase.getFundcode());
		fundPlatformLogEntity.setSharingno(new BigDecimal(ase.getSharingNo()));
		fundPlatformLogEntity.setTradingamount(tradingAmount.add(trdingcommission));
		fundPlatformLogEntity.setTradingoption("Sell");
		fundPlatformLogEntity.setTransactionamount(tradingAmount);
		fundPlatformLogEntity.setTransactiondate(format.parse(format.format(new Date())));
		fundPlatformLogEntity.setTrdingcommission(trdingcommission);
		fundPlatformLogDao.insert(fundPlatformLogEntity);
		
		// 写入日志
		String logstr = "Transaction Accepted:" + ase.getFundaccountnumber();
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
						SysConstant.OPERATION_SUCCESS, logstr);
		
		newBalance = resavaccount.getBalance().add(transactionamount);
		UpdateAccountBalanceModel accountInfo = new UpdateAccountBalanceModel();
		accountInfo.setBalance(newBalance);
		accountInfo.setAccountnumber(ase.getDebitaccountnumber());
		String path4 = getInternalUrl("updateAccountBalance");
		if(path4.length() == 0){
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
		//更新 debit account master file 和transaction log file
		String updateDebitAccountParam =JsonProcess.changeEntityTOJSON(accountInfo);
		String updateDebitAccountRes = getResponse(path4, updateDebitAccountParam);
		if(updateDebitAccountRes.length() == 0){
			throw new UpdateException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500012),ExceptionConstant.ERROR_CODE500012);
		}
		
		// 调用服务接口地址 获得transacitonLogAdding得内网地址
		String path3 = getInternalUrl("transactionLogAdding");
		if(path3.length() == 0){
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
		//Update the transaction history file
		InsertTransactionLogModel insertTransacitonlog = new InsertTransactionLogModel();
		insertTransacitonlog.setAccountnumber(ase.getDebitaccountnumber());
		insertTransacitonlog.setActualbalamt(newBalance);
		insertTransacitonlog.setBranchcode(header.getBranchCode());
		insertTransacitonlog.setSandboxid(header.getSandBoxId());
		insertTransacitonlog.setCcy(resavaccount.getCurrencycode());
		insertTransacitonlog.setChannel(SysConstant.CHANNEL_TYPE);
		insertTransacitonlog.setChannelid(header.getUserID());
		insertTransacitonlog.setClearingcode(header.getClearingCode());
		insertTransacitonlog.setCountrycode(header.getCountryCode());
		insertTransacitonlog.setPreviousbalamt(resavaccount.getBalance());
		insertTransacitonlog.setTranamt(transactionamount);
		insertTransacitonlog.setTrandesc("fund sell");
		insertTransacitonlog.setCrdrmaintind(SysConstant.CR_DR_MAINT_IND_TYPE2);
		insertTransacitonlog.setTrantype(SysConstant.TRANSACTION_TYPE12);
		
		//更新 transaction detail file
		String insertTransactionString = JsonProcess.changeEntityTOJSON(insertTransacitonlog);
		
		ResponseEntity<String> insertRes = restTemplate.postForEntity(path3,PostUtil.getRequestEntity(insertTransactionString),String.class);
		if (insertRes.getStatusCodeValue() != 200) {
			throw new SearchException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		JSONObject str1 = XmlToJsonUtil.xml2JSON(insertRes.getBody().getBytes());
		String str2 = JsonProcess.returnValue(str1, "ResultUtil");
		ResultUtil<JSONArray> str3 = JSON.parseObject(str2, ResultUtil.class);
		if(str3.getCode().substring(1, str3.getCode().length()-1).equals("0")){
			throw new InsertException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500004),ExceptionConstant.ERROR_CODE500004);
		}
		
		// 写入日志
		String logstr1 = "Transaction Accepted:" + ase.getDebitaccountnumber();
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
						SysConstant.OPERATION_SUCCESS, logstr1);
		
		
		// TODO Auto-generated method stub
		result.setCode("1");
		result.setMsg("Transaction Accepted");
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultUtil mutualfundHoldingEnquiry(HeaderModel header, FundHoldingEnquiryModel ase,
			RestTemplate restTemplate) throws Exception {
		ResultUtil result = new ResultUtil();
		MutualFundEntity fundaccountInfo = new MutualFundEntity();
		fundaccountInfo.setAccountnumber(ase.getFundaccountnumber());
		fundaccountInfo.setCountrycode(header.getCountryCode());
		fundaccountInfo.setClearingcode(header.getClearingCode());
		fundaccountInfo.setBranchcode(header.getBranchCode());
		fundaccountInfo.setSandboxid(header.getSandBoxId());
		fundaccountInfo.setCustomernumber(header.getCustomerNumber());
		MutualFundEntity fundaccount = (MutualFundEntity) mutualFundDao.findOne(fundaccountInfo);
		if(fundaccount == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		if(!fundaccount.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
		}
		FundHoldingEntity fundHoldingEntity = new FundHoldingEntity();
		fundHoldingEntity.setAccountnumber(ase.getFundaccountnumber());
		List<FundHoldingEntity> list = fundHoldingDao.findMany(fundHoldingEntity);
		List<FundInvestmentModel> list1 = new ArrayList<FundInvestmentModel>();
		if(list.size() > 0 ){
			for(int i=0; i<list.size(); i++){
				FundHoldingEntity fundholdingInfo = list.get(i);
				FundInvestmentModel fundmodel =  new FundInvestmentModel();
				fundmodel.setId(fundholdingInfo.getId());
				fundmodel.setAccountnumber(fundholdingInfo.getAccountnumber());
				fundmodel.setAverageprice(fundholdingInfo.getAverageprice());
				fundmodel.setLastupdatedate(format.format(fundholdingInfo.getLastupdatedate()));
				fundmodel.setSharesholdingno(fundholdingInfo.getSharesholding());
				fundmodel.setFundcode(fundholdingInfo.getFundcode());
				list1.add(fundmodel);
			}
		}else{
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404014),ExceptionConstant.ERROR_CODE404014);
		}
		result.setCode("1");
		result.setMsg("Information collected");
		result.setData(list1);
		// 写入日志
		String logstr1 = "Check Fund Hold Info Succeed:" + ase.getFundaccountnumber();
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_QUERY, SysConstant.LOCAL_SERVICE_NAME,
						SysConstant.OPERATION_SUCCESS, logstr1);
		
		return result;
	}
	
	//获取内网地址公共方法
	private String getInternalUrl(String apiname){
		String param = "{\"apiname\":\""+apiname+"\"}";
		ResponseEntity<String> result = restTemplate.postForEntity("http://"+CommonConstant.getSYSADMIN()+SysConstant.SERVICE_INTERNAL_URL+"", PostUtil.getRequestEntity(param),String.class);
        JSONObject res = JsonProcess.changeToJSONObject(result.getBody());
        String code = JsonProcess.returnValue(res, "code");
		String responseString = JsonProcess.changeEntityTOJSON(res);
        if (responseString == null || responseString.length()==0 || ("0").equals(code)) {
			return "";
		}else{
			String path = JsonProcess.returnValue(res, "internaURL");
			return path;
		}
	}
	
	//根据传参获取response string
	private String getResponse(String path, String param){
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(path,PostUtil.getRequestEntity(param),String.class);
		String responseString = responseEntity.getBody();
		if(responseString== null || responseString.length() == 0){
			return "";
		}else{
			JSONObject responseObject = JsonProcess.changeToJSONObject(responseEntity.getBody());
			String resCode = JsonProcess.returnValue(responseObject, "code");
			if("0".equals(resCode)){
				return "";
			}
			return responseString;
		}
	}
}