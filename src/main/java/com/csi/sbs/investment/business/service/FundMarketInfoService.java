package com.csi.sbs.investment.business.service;


import org.springframework.web.client.RestTemplate;

import com.csi.sbs.investment.business.clientmodel.FundBuyTradingModel;
import com.csi.sbs.investment.business.clientmodel.FundSellTradingModel;
import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.FundHoldingEnquiryModel;
import com.csi.sbs.investment.business.util.ResultUtil;

public interface FundMarketInfoService{
	
	@SuppressWarnings("rawtypes")
	public ResultUtil fundBuyTradingService(HeaderModel header, FundBuyTradingModel ase,RestTemplate restTemplate) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ResultUtil fundSellTradingService(HeaderModel header, FundSellTradingModel ase,RestTemplate restTemplate) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public ResultUtil mutualfundHoldingEnquiry(HeaderModel header, FundHoldingEnquiryModel ase,RestTemplate restTemplate) throws Exception;
}