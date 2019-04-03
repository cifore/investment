package com.csi.sbs.investment.business.constant;

public class PathConstant {
	
	
	//获取单个saving account
	public static final String GET_SAV = "http://DEPOSIT/deposit/account/getOneSavingAccount";
    //获取单个 current account
	public static final String GET_CURRENT = "http://DEPOSIT/deposit/account/getOneCurrentAccount";
    //更新account balance 信息
	public static final String UPDATE_BALANCE = "http://DEPOSIT/deposit/account/updateABalance";
    //写交易日志
	public static final String WRITE_LOG = "http://DEPOSIT/deposit/transactionLog/transactionLogAdding";
}
