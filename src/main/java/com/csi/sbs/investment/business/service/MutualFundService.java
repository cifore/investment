package com.csi.sbs.investment.business.service;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

import com.csi.sbs.common.business.model.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.CloseAccountModel;
import com.csi.sbs.investment.business.clientmodel.InvestmentOpeningAccountModel;
import com.csi.sbs.investment.business.clientmodel.QueryMutualModel;
import com.csi.sbs.investment.business.clientmodel.otherservice.AddMutualDepositModel;
import com.csi.sbs.investment.business.util.ResultUtil;

public interface MutualFundService{
	
	public Map<String,Object> openingFUNccount(HeaderModel header, InvestmentOpeningAccountModel fam,RestTemplate restTemplate) throws Exception;

    @SuppressWarnings("rawtypes")
	public ResultUtil getMutualAccount(QueryMutualModel qmm);
    
    @SuppressWarnings("rawtypes")
	public ResultUtil getOneMutualAccount(QueryMutualModel qmm);
    
    @SuppressWarnings("rawtypes")
	public ResultUtil closeAccount(CloseAccountModel cam,RestTemplate restTemplate) throws Exception;

    public int save(AddMutualDepositModel amdm) throws Exception;
    
    @SuppressWarnings("rawtypes")
	public ResultUtil fundQuotation(HeaderModel header,String accountNumber) throws Exception;
    
}