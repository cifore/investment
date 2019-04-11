package com.csi.sbs.investment.business.constant;

import com.csi.sbs.common.business.constant.CommonConstant;

public class PathConstant {
	
	
	//获取单个saving account
	public static final String GET_SAV = "http://DEPOSIT/deposit/account/getOneSavingAccount";
    //获取单个 current account
	public static final String GET_CURRENT = "http://DEPOSIT/deposit/account/getOneCurrentAccount";
    //更新account balance 信息
	public static final String UPDATE_BALANCE = "http://DEPOSIT/deposit/account/updateABalance";
    //写交易日志
	public static final String WRITE_LOG = "http://DEPOSIT/deposit/transactionLog/transactionLogAdding";
	//获取内部服务地址
	public static final String SERVICE_INTERNAL_URL = "http://" + CommonConstant.getSYSADMIN() + "/sysadmin/getServiceInternalURL";
	//获取下一个可用的递增序列号服务地址
    public static final String NEXT_AVAILABLE = "http://SYSADMIN/sysadmin/generate/getNextAvailableNumber/";
    //更新下一个可用的递增序列号服务地址
    public static final String SAVE_NEXT_AVAILABLE = "http://SYSADMIN/sysadmin/generate/saveNextAvailableNumber";
    
	
}
