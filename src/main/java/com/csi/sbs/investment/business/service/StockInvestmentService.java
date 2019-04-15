package com.csi.sbs.investment.business.service;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.investment.business.clientmodel.CloseAccountModel;
import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.InvestmentOpeningAccountModel;
import com.csi.sbs.investment.business.clientmodel.QueryStockModel;
import com.csi.sbs.investment.business.clientmodel.StockHoldingEnquiryModel;
import com.csi.sbs.investment.business.clientmodel.StockTradingModel;
import com.csi.sbs.investment.business.clientmodel.StockTradingPlatformModel;
import com.csi.sbs.investment.business.clientmodel.otherservice.AddStockDepositModel;
import com.csi.sbs.investment.business.util.ResultUtil;



public interface StockInvestmentService{
	
	public Map<String,Object> openingSTKccount(HeaderModel header, InvestmentOpeningAccountModel fam,RestTemplate restTemplate) throws Exception;
	
	public Map<String,String> stockTradingPlatform(HeaderModel header, StockTradingPlatformModel stp,RestTemplate restTemplate) throws Exception;
	
	public Map<String,Object> stockTradingService(HeaderModel header, StockTradingModel stm,RestTemplate restTemplate) throws Exception;

	public Map<String,Object> stockHoldingEnquiry(HeaderModel header, StockHoldingEnquiryModel sth,RestTemplate restTemplate) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ResultUtil getStockAccount(QueryStockModel qsm);
	
	@SuppressWarnings("rawtypes")
	public ResultUtil getOneStockAccount(QueryStockModel qsm);
	
	@SuppressWarnings("rawtypes")
	public ResultUtil closeAccount(CloseAccountModel cam,RestTemplate restTemplate) throws Exception;
	
	public int save(AddStockDepositModel sie);
}