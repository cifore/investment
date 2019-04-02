package com.csi.sbs.investment.business.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.UUIDUtil;
import com.csi.sbs.investment.business.clientmodel.CurrentAccountMasterModel;
import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.InsertTransactionLogModel;
import com.csi.sbs.investment.business.clientmodel.SavingAccountMasterModel;
import com.csi.sbs.investment.business.clientmodel.StockTradingModel;
import com.csi.sbs.investment.business.clientmodel.StockTradingPlatformModel;
import com.csi.sbs.investment.business.clientmodel.UpdateAccountBalanceModel;
import com.csi.sbs.investment.business.constant.ExceptionConstant;
import com.csi.sbs.investment.business.constant.PathConstant;
import com.csi.sbs.investment.business.constant.SysConstant;
import com.csi.sbs.investment.business.dao.StockHoldingDao;
import com.csi.sbs.investment.business.dao.StockInformationDao;
import com.csi.sbs.investment.business.dao.StockInvestmentDao;
import com.csi.sbs.investment.business.dao.StockPlatFormLogDao;
import com.csi.sbs.investment.business.entity.StockHoldingEntity;
import com.csi.sbs.investment.business.entity.StockInformationEntity;
import com.csi.sbs.investment.business.entity.StockInvestmentEntity;
import com.csi.sbs.investment.business.entity.StockPlatFormLogEntity;
import com.csi.sbs.investment.business.exception.AcceptException;
import com.csi.sbs.investment.business.exception.CallOtherException;
import com.csi.sbs.investment.business.exception.DateException;
import com.csi.sbs.investment.business.exception.NotFoundException;
import com.csi.sbs.investment.business.exception.OtherException;
import com.csi.sbs.investment.business.service.StockInvestmentService;
import com.csi.sbs.investment.business.util.LogUtil;
import com.csi.sbs.investment.business.util.PostUtil;
import com.csi.sbs.investment.business.util.SRUtil;


@Service
public class StockInvestmentServiceImpl implements StockInvestmentService {
	
	

	@SuppressWarnings("rawtypes")
	@Resource
	private StockInvestmentDao stockInvestmentDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private StockInformationDao stockInformationDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private StockHoldingDao stockHoldingDao;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private StockPlatFormLogDao stockPlatFormLogDao;
	
	

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

	

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Map<String, Object> stockTradingService(HeaderModel header, StockTradingModel stm,
			RestTemplate restTemplate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> map2 = new HashMap<String, String>();
		BigDecimal potentialTransactionAmount = null;
		BigDecimal newBalance = null;
		String tradingaction = stm.getTradingOption().toUpperCase();
		String ordertype = stm.getOrderType().toUpperCase();
		SavingAccountMasterModel resavaccount = new SavingAccountMasterModel();
		
		//If the option is not equal to Buy or Sell, reject the transaction
		if(!tradingaction.equals("BUY") && !tradingaction.equals("SELL")){
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202022),ExceptionConstant.ERROR_CODE202022);
		}
		
		// If the Order Type is not equal to Market Price or Fix Price, reject the transaction
		if(!ordertype.equals("MARKET PRICE") && !ordertype.equals("FIX PRICE")){
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202022),ExceptionConstant.ERROR_CODE202022);
		}
		
		//For Order Type=Market price, reject the transaction with message, “invalid Order Type”, if the Buy/Sell Prices is not empty.
		if(stm.getTradingPrice() != null && stm.getTradingPrice().length() > 0 && ordertype.equals("MARKET PRICE") ){
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202016),ExceptionConstant.ERROR_CODE202016);
		}
		
		if(!CheckDate(stm.getExpiredate())){
			throw new DateException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500004),ExceptionConstant.ERROR_CODE500004);
		}
		
		// If the Order Type is equal to Fix Price, but the trading price is empty ,reject
		if(ordertype.equals("FIX PRICE") && (stm.getTradingPrice() == null || stm.getTradingPrice().length() == 0)){
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202022),ExceptionConstant.ERROR_CODE202022);
		}
		
		//Read Investment Account Master file with the inputted Stock Trading Account Number. If record does not exist, reject the transaction 
		StockInvestmentEntity stockInvestmentEntity =  new StockInvestmentEntity();
		stockInvestmentEntity.setAccountnumber(stm.getStkaccountnumber());
		stockInvestmentEntity.setCountrycode(header.getCountryCode());
		stockInvestmentEntity.setClearingcode(header.getClearingCode());
		stockInvestmentEntity.setBranchcode(header.getBranchCode());
		stockInvestmentEntity.setCustomernumber(header.getCustomerNumber());
		StockInvestmentEntity stkaccount = (StockInvestmentEntity) stockInvestmentDao.findOne(stockInvestmentEntity);
		if(stkaccount == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
		}
		if(!stkaccount.getAccountstatus().equals("A")){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202001),ExceptionConstant.ERROR_CODE202001);
			
		}
		
		//Read Account Master File by using the Input Transfer From/To account number. If record does not exist, reject the transaction
		String relaccountType = stm.getDebitaccountnumber().substring(stm.getDebitaccountnumber().length() - 3);
		if(relaccountType.equals(SysConstant.ACCOUNT_TYPE1)== false && relaccountType.equals(SysConstant.ACCOUNT_TYPE2)== false){
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201001),ExceptionConstant.ERROR_CODE201001);
		}
		
		if(relaccountType.equals(SysConstant.ACCOUNT_TYPE1)){
			SavingAccountMasterModel savaccount = new SavingAccountMasterModel();
			savaccount.setCountrycode(header.getCountryCode());
			savaccount.setClearingcode(header.getClearingCode());
			savaccount.setBranchcode(header.getBranchCode());
			savaccount.setCustomernumber(header.getCustomerNumber());
			savaccount.setAccountnumber(stm.getDebitaccountnumber());
			ResponseEntity<String> result = SRUtil.sendOne(PathConstant.GET_SAV, JsonProcess.changeEntityTOJSON(savaccount));
			//resavaccount =  (SavingAccountMasterModel) savingAccountMasterDao.findOne(savaccount);
			if(resavaccount == null){
				throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
			}
		}
		if(relaccountType.equals(SysConstant.ACCOUNT_TYPE2)){
			CurrentAccountMasterModel current = new CurrentAccountMasterModel();
			current.setCountrycode(header.getCountryCode());
			current.setClearingcode(header.getClearingCode());
			current.setBranchcode(header.getBranchCode());
			current.setCustomernumber(header.getCustomerNumber());
			current.setAccountnumber(stm.getDebitaccountnumber());
			ResponseEntity<String> result = SRUtil.sendOne(PathConstant.GET_CURRENT, JsonProcess.changeEntityTOJSON(current));
			//CurrentAccountMasterEntity recurrent = (CurrentAccountMasterEntity) currentAccountMasterDao.findOne(current);
//			if(recurrent == null){
//				throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404010),ExceptionConstant.ERROR_CODE404010);
//			}
			resavaccount.setAccountnumber(stm.getDebitaccountnumber());
//			resavaccount.setBalance(recurrent.getBalance());
//			resavaccount.setAccountstatus(recurrent.getAccountstatus());
//			resavaccount.setId(recurrent.getId());
//			resavaccount.setCurrencycode(recurrent.getCurrencycode());
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
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
		
		// 调用系统参数服务接口
		String param2 = "{\"item\":\"SystemDate\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		JSONArray restr = JsonProcess.changeToJSONArray(result2.getBody());
		String redate = JsonProcess.returnValue(JsonProcess.changeToJSONObject(restr.get(0).toString()), "value");
		
		if(format1.parse(stm.getExpiredate()).getTime() < format.parse(redate).getTime() ){
			throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202017),ExceptionConstant.ERROR_CODE202017);
		}
		
		StockInformationEntity  stockInformationEntity = new StockInformationEntity();
		stockInformationEntity.setStockcode(stm.getStocknumber());
		StockInformationEntity stkInfo = (StockInformationEntity) stockInformationDao.findOne(stockInformationEntity);
		if(stkInfo == null){
			throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404011),ExceptionConstant.ERROR_CODE404011);
		}
		
		//validate the number of shares against the lot size information from the Stock Quotation Service. If not to the multiple, reject the transaction
		if(tradingaction.equals("BUY")){
			if(stm.getSellAll() != null && stm.getSellAll().length() > 0){
				throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202022),ExceptionConstant.ERROR_CODE202022);
			}
			
			if(new BigDecimal(stm.getSharingNo()).divideAndRemainder(stkInfo.getLotsize())[1].compareTo(BigDecimal.ZERO) > 0){
				throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202018),ExceptionConstant.ERROR_CODE202018);
			}
			
			//For Order Type = Fix Price
			if(ordertype.equals("FIX PRICE")){
				//If the buy price is larger than 30 trading points,  reject the transaction
				if(new BigDecimal(stm.getTradingPrice()).compareTo(stkInfo.getSellprice().add(stkInfo.getTradingpoint().multiply(new BigDecimal("30")))) > 0 || new BigDecimal(stm.getTradingPrice()).compareTo(stkInfo.getSellprice().subtract(stkInfo.getTradingpoint().multiply(new BigDecimal("30")))) <0){
					throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202019),ExceptionConstant.ERROR_CODE202019);
				}
			}
			
			//For Order Type=Market Price, call Stock Quotation Service API to retrieve the latest stock price- Sell. Assign this price to Stock Trading Price
			if(ordertype.equals("MARKET PRICE")){
				stm.setTradingPrice(stkInfo.getSellprice().toString());
			}
			
			// potential transaction amount = Number of shares * Buy price or Market price.
			potentialTransactionAmount = new BigDecimal(stm.getTradingPrice()).multiply(new BigDecimal(stm.getSharingNo()));
			
			//If the potential transaction amount is larger than Account Balance, reject the transaction
			if(potentialTransactionAmount.compareTo(resavaccount.getBalance()) > 0){
				throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202002),ExceptionConstant.ERROR_CODE202002);
			}
		}
		
		//If the Trading Option is Sell
		if(tradingaction.equals("SELL")){
			
			//Validate the Stock Number against the Stock Holding Master file. If stock code does not exist, reject the transaction 
			StockHoldingEntity stockHoldingEntity = new StockHoldingEntity();
			stockHoldingEntity.setAccountnumber(stm.getStkaccountnumber());
			stockHoldingEntity.setStockcode(stm.getStocknumber());
			StockHoldingEntity stkHoldInfo =  (StockHoldingEntity) stockHoldingDao.findOne(stockHoldingEntity);
			if(stkHoldInfo == null){
				throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202020),ExceptionConstant.ERROR_CODE202020);
			}
			
			//If Sell All option is selected, retrieve the number of shares from Stock Holding Master File. Use this number of shares data for subsequent processing.
			if(stm.getSellAll().equals("Y")){
				//全部卖出时， sharingNo应为空或不存在
				if( stm.getSharingNo()!= null && stm.getSharingNo().length() > 0){
					throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202022),ExceptionConstant.ERROR_CODE202022);
				}
				stm.setSharingNo(stkHoldInfo.getSharesholdingno().toString());
			}else{
				if(new BigDecimal(stm.getSharingNo()).compareTo(stkHoldInfo.getSharesholdingno()) > 0){
					throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202021),ExceptionConstant.ERROR_CODE202021);
				}
			}
			
			//Validate the number of shares against the lot size information from the Stock Quotation Service
			if(new BigDecimal(stm.getSharingNo()).divideAndRemainder(stkInfo.getLotsize())[1].compareTo(BigDecimal.ZERO) > 0){
				throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202018),ExceptionConstant.ERROR_CODE202018);
			}
			
			//For Order Type = Fix Price
			if(ordertype.equals("FIX PRICE")){
				//If the sell price is larger than 30 trading points,  reject the transaction
				if(new BigDecimal(stm.getTradingPrice()).compareTo(stkInfo.getBuyprice().add(stkInfo.getTradingpoint().multiply(new BigDecimal("30")))) > 0 || new BigDecimal(stm.getTradingPrice()).compareTo(stkInfo.getBuyprice().subtract(stkInfo.getTradingpoint().multiply(new BigDecimal("30")))) < 0){
					throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202019),ExceptionConstant.ERROR_CODE202019);
				}
			}
			
			//For Order Type=Market Price,call Stock Quotation Service API to retrieve the latest stock price- Buy. Assign this price to Stock Trading Price.
			if(ordertype.equals("MARKET PRICE")){
				stm.setTradingPrice(stkInfo.getBuyprice().toString());
			}
		}
		
		//Stock Trading Platform API returned with the transaction amount
		StockTradingPlatformModel stockTradingPlatformModel =  new StockTradingPlatformModel();
		stockTradingPlatformModel.setSharingNo(new BigDecimal(stm.getSharingNo()));
		stockTradingPlatformModel.setStkaccountnumber(stm.getStkaccountnumber());
		stockTradingPlatformModel.setStocknumber(stm.getStocknumber());
		stockTradingPlatformModel.setTradingOption(stm.getTradingOption());
		stockTradingPlatformModel.setTradingPrice(new BigDecimal(stm.getTradingPrice()));
		map2 = stockTradingPlatform(header,stockTradingPlatformModel,restTemplate);
		String transactionAmount = map2.get("transactionAmount");
		
		//Add/reduce transaction amount from Transfer From/To account depends on the trading option.
		if(tradingaction.equals("BUY")){
			if(new BigDecimal(transactionAmount).compareTo(resavaccount.getBalance()) > 0){
				throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202002),ExceptionConstant.ERROR_CODE202002);
			}
			newBalance = resavaccount.getBalance().subtract(new BigDecimal(transactionAmount));
			UpdateAccountBalanceModel accountInfo = new UpdateAccountBalanceModel();
			accountInfo.setBalance(newBalance);
			accountInfo.setAccountnumber(stm.getDebitaccountnumber());
			accountInfo.setBranchCode(header.getBranchCode());
			accountInfo.setClearingCode(header.getClearingCode());
			accountInfo.setCountryCode(header.getCountryCode());
			accountInfo.setCustomerNumber(header.getCustomerNumber());
			accountInfo.setUserID(header.getUserID());
			SRUtil.sendOne(path, JsonProcess.changeEntityTOJSON(accountInfo));
			//accountMasterService.updateAccountBalance(header, accountInfo, restTemplate);
		}
		if(tradingaction.equals("SELL")){
			newBalance = resavaccount.getBalance().add(new BigDecimal(transactionAmount));
			HeaderModel header1 = new HeaderModel();
			UpdateAccountBalanceModel accountInfo = new UpdateAccountBalanceModel();
			accountInfo.setBalance(newBalance);
			accountInfo.setAccountnumber(stm.getDebitaccountnumber());
			accountInfo.setBranchCode(header.getBranchCode());
			accountInfo.setClearingCode(header.getClearingCode());
			accountInfo.setCountryCode(header.getCountryCode());
			accountInfo.setCustomerNumber(header.getCustomerNumber());
			accountInfo.setUserID(header.getUserID());
			SRUtil.sendOne(path, JsonProcess.changeEntityTOJSON(accountInfo));
			//accountMasterService.updateAccountBalance(header1, accountInfo, restTemplate);
		}
		
		//Update the transaction history file
		InsertTransactionLogModel insertTransacitonlog = new InsertTransactionLogModel();
		insertTransacitonlog.setAccountnumber(stm.getDebitaccountnumber());
		insertTransacitonlog.setActualbalamt(newBalance);
		insertTransacitonlog.setBranchcode(header.getBranchCode());
		insertTransacitonlog.setCcy(resavaccount.getCurrencycode());
		insertTransacitonlog.setChannel(SysConstant.CHANNEL_TYPE);
		insertTransacitonlog.setChannelid(header.getUserID());
		insertTransacitonlog.setClearingcode(header.getClearingCode());
		insertTransacitonlog.setCountrycode(header.getCountryCode());
		insertTransacitonlog.setPreviousbalamt(resavaccount.getBalance());
		insertTransacitonlog.setTranamt(new BigDecimal(transactionAmount));
		insertTransacitonlog.setTrandesc("stock sell");
		insertTransacitonlog.setCrdrmaintind(SysConstant.CR_DR_MAINT_IND_TYPE2);
		insertTransacitonlog.setTrantype(SysConstant.TRANSACTION_TYPE10);
		if(tradingaction.equals("BUY")){
			insertTransacitonlog.setCrdrmaintind(SysConstant.CR_DR_MAINT_IND_TYPE1);
			insertTransacitonlog.setTrantype(SysConstant.TRANSACTION_TYPE9);
			insertTransacitonlog.setTrandesc("stock buy");
		}
		SRUtil.sendTwo(PathConstant.WRITE_LOG, header, JsonProcess.changeEntityTOJSON(insertTransacitonlog));
		//transactionLogService.insertTransacitonLog(restTemplate, insertTransacitonlog);
		
		// 写入日志
		String logstr = "Transaction Accepted:" + stm.getDebitaccountnumber();
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		map.put("msg", "Transaction Accepted");
		map.put("code", "1");
		return map;
	}
	

	
	
	
	
	private boolean CheckDate(String date){
		String rexp1 = "((\\d{2}(([02468][048])|([13579][26]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]((((0?[13578])|(1[02]))[\\-]((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-]((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-]((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if(date.matches(rexp1)){
			return true;
		}else{
			return false;
		}
	}






	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> stockTradingPlatform(HeaderModel header, StockTradingPlatformModel stp,
			RestTemplate restTemplate) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		BigDecimal tradingAmount = null;
		BigDecimal tradingcommission = null;
		BigDecimal transactionAmount = null;
		BigDecimal avaeragePrice = null;
		BigDecimal sharingsHoldingNum = null;
		String tradingOption = stp.getTradingOption().toLowerCase();
		
		// 调用服务接口地址
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(
				"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");
		 // 调用系统参数服务接口
		 String param2 =
		 "{\"item\":\"StockTradingCommissionRate,CustodyCharges\"}";
		 ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
		 String.class);
		 if (result2.getStatusCodeValue() != 200) {
			 throw new CallOtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		 }
		
		 // 返回数据处理
		 String StockTradingCommissionRate = "";
		 String CustodyCharges = "";
		 JSONObject jsonObject1 = null;
		 String revalue = null;
		 String temp = null;
		 for (int i = 0; i <JsonProcess.changeToJSONArray(result2.getBody()).size(); i++) {
			 jsonObject1 = JsonProcess.changeToJSONObject(JsonProcess.changeToJSONArray(result2.getBody()).get(i).toString());
			 revalue = JsonProcess.returnValue(jsonObject1, "item");
			 temp = JsonProcess.returnValue(jsonObject1, "value");
			 if (revalue.equals("StockTradingCommissionRate")) {
				 StockTradingCommissionRate = temp;
			 }
			 if (revalue.equals("CustodyCharges")) {
				 CustodyCharges = temp;
			 }
		 }
		 tradingAmount = stp.getTradingPrice().multiply(stp.getSharingNo());
		 tradingcommission = tradingAmount.multiply(new BigDecimal(StockTradingCommissionRate));
		 if(tradingOption.equals("buy")){
			 transactionAmount = tradingAmount.add(tradingcommission).add(new BigDecimal(CustodyCharges));
		 }
		 if(tradingOption.equals("sell")){
			 transactionAmount = tradingAmount.subtract(tradingcommission).subtract(new BigDecimal(CustodyCharges));
		 }
		 
		//check account number holding info
		StockHoldingEntity stockHoldingEntity = new StockHoldingEntity();
		stockHoldingEntity.setAccountnumber(stp.getStkaccountnumber());
		stockHoldingEntity.setStockcode(stp.getStocknumber());
		StockHoldingEntity stkHoldInfo =  (StockHoldingEntity) stockHoldingDao.findOne(stockHoldingEntity);
		if(stkHoldInfo == null){
			if(tradingOption.equals("buy")){
				avaeragePrice = stp.getTradingPrice();
				stockHoldingEntity.setId(UUIDUtil.generateUUID());
				stockHoldingEntity.setAverageprice(avaeragePrice);
				stockHoldingEntity.setSharesholdingno(stp.getSharingNo());
				stockHoldingEntity.setLastupdatedate(format.parse(format.format(new Date())));
				stockHoldingDao.insert(stockHoldingEntity);
				
			}
			if(tradingOption.equals("sell")){
				throw new AcceptException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE202020),ExceptionConstant.ERROR_CODE202020);
			}
		}else{
			if(tradingOption.equals("buy")){
				//new average price = (number of shares holding from file * average price from file+Stock trading amount)/(Number of shares holding from file + No. of shares)
				avaeragePrice =(stkHoldInfo.getSharesholdingno().multiply(stkHoldInfo.getAverageprice()).add(tradingAmount)).divide(stkHoldInfo.getSharesholdingno().add(stp.getSharingNo()),2,BigDecimal.ROUND_HALF_UP);
				sharingsHoldingNum = stkHoldInfo.getSharesholdingno().add(stp.getSharingNo());
				stockHoldingEntity.setAverageprice(avaeragePrice);
				stockHoldingEntity.setSharesholdingno(sharingsHoldingNum);
				stockHoldingEntity.setId(stkHoldInfo.getId());
				stockHoldingEntity.setLastupdatedate(format.parse(format.format(new Date())));
				stockHoldingDao.update(stockHoldingEntity);
			}
			if(tradingOption.equals("sell")){
				if(stp.getSharingNo().compareTo(stkHoldInfo.getSharesholdingno()) == 0){
					stockHoldingDao.delete(stkHoldInfo.getId());
				}else{
					//new average price = (number of shares holding from file * average price from file+Stock trading amount)/(Number of shares holding from file + No. of shares)
					avaeragePrice =(stkHoldInfo.getSharesholdingno().multiply(stkHoldInfo.getAverageprice()).subtract(tradingAmount)).divide(stkHoldInfo.getSharesholdingno().subtract(stp.getSharingNo()),2,BigDecimal.ROUND_HALF_UP);
					sharingsHoldingNum = stkHoldInfo.getSharesholdingno().subtract(stp.getSharingNo());
					stockHoldingEntity.setAverageprice(avaeragePrice);
					stockHoldingEntity.setSharesholdingno(sharingsHoldingNum);
					stockHoldingEntity.setId(stkHoldInfo.getId());
					stockHoldingEntity.setLastupdatedate(format.parse(format.format(new Date())));
					stockHoldingDao.update(stockHoldingEntity);
				}
			}
		}

		//Update Stock Trading Platform Log File 
		StockPlatFormLogEntity stockPlatFormLogEntity =  new StockPlatFormLogEntity();
		stockPlatFormLogEntity.setId(UUIDUtil.generateUUID());
		stockPlatFormLogEntity.setCountrycode(header.getCountryCode());
		stockPlatFormLogEntity.setClearingcode(header.getClearingCode());
		stockPlatFormLogEntity.setBranchcode(header.getBranchCode());
		stockPlatFormLogEntity.setAccountnumber(stp.getStkaccountnumber());
		stockPlatFormLogEntity.setCustodycharges(new BigDecimal(CustodyCharges));
		stockPlatFormLogEntity.setSharingno(stp.getSharingNo());
		stockPlatFormLogEntity.setStocknumber(stp.getStocknumber());
		stockPlatFormLogEntity.setTradingoption(stp.getTradingOption());
		stockPlatFormLogEntity.setStocktrdingamount(tradingAmount);
		stockPlatFormLogEntity.setStocktrdingcommission(tradingcommission);
		stockPlatFormLogEntity.setTransactionamount(transactionAmount);
		stockPlatFormLogEntity.setTransactiondate(format.parse(format.format(new Date())));
		stockPlatFormLogDao.insert(stockPlatFormLogEntity);
		
		// 写入日志
		String logstr = "Transaction Accepted:" + stp.getStkaccountnumber();
		LogUtil.saveLog(restTemplate, SysConstant.OPERATION_CREATE, SysConstant.LOCAL_SERVICE_NAME,
				SysConstant.OPERATION_SUCCESS, logstr);
		map.put("code", "1");
		map.put("msg", "Succeeded");
		map.put("transactionAmount", transactionAmount.toString());
		return map;
	}	
}