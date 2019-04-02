package com.csi.sbs.investment.business.service;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.StockTradingModel;
import com.csi.sbs.investment.business.clientmodel.StockTradingPlatformModel;



public interface StockInvestmentService{
	
	public Map<String,String> stockTradingPlatform(HeaderModel header, StockTradingPlatformModel stp,RestTemplate restTemplate) throws Exception;
	
	public Map<String,Object> stockTradingService(HeaderModel header, StockTradingModel stm,RestTemplate restTemplate) throws Exception;

}