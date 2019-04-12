package com.csi.sbs.investment.business.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.common.business.util.XmlToJsonUtil;
import com.csi.sbs.investment.business.clientmodel.CurrentAccountMasterModel;
import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.clientmodel.SavingAccountMasterModel;
import com.csi.sbs.investment.business.constant.ExceptionConstant;
import com.csi.sbs.investment.business.constant.PathConstant;
import com.csi.sbs.investment.business.constant.SysConstant;
import com.csi.sbs.investment.business.exception.NotFoundException;



public class ValidateAccountTypeUtil {

	
	public static Map<String, Object> checkRelAccountNumber(RestTemplate restTemplate,HeaderModel header,String accountType, String relaccountNumber) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (accountType.equals(SysConstant.ACCOUNT_TYPE1)) {
			// get customerID
			SavingAccountMasterModel savaccount = new SavingAccountMasterModel();
			savaccount.setAccountnumber(relaccountNumber);
			savaccount.setCountrycode(header.getCountryCode());
			savaccount.setClearingcode(header.getClearingCode());
			if(!StringUtils.isEmpty(header.getSandBoxId())){
				savaccount.setBranchcode(null);
			}else{
				savaccount.setBranchcode(header.getBranchCode());
			}
			savaccount.setSandboxid(header.getSandBoxId());
			savaccount.setCustomernumber(header.getCustomerNumber());
			ResponseEntity<String> result = SRUtil.sendOne(restTemplate,PathConstant.GET_SAV, JsonProcess.changeEntityTOJSON(savaccount));
            JSONObject temp = XmlToJsonUtil.xmlToJson(result.getBody());
            String temp_ = JsonProcess.returnValue(temp, "SavingAccountInternalModel");
            SavingAccountMasterModel savresult = JSON.parseObject(temp_, SavingAccountMasterModel.class);
			if(savresult==null || "".equals(savresult.getAccountnumber()) || !savresult.getAccountnumber().equals(relaccountNumber)){
				throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404001),ExceptionConstant.ERROR_CODE404001);
			}else{
				map.put("msg", savresult != null ? savresult.getCustomernumber() : ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404001));
				map.put("code", savresult != null ? "1" : ExceptionConstant.ERROR_CODE404001);
			}
			return map;
		}
		if (accountType.equals(SysConstant.ACCOUNT_TYPE2)) {
			// get customerID
			CurrentAccountMasterModel currentaccount = new CurrentAccountMasterModel();
			currentaccount.setAccountnumber(relaccountNumber);
			currentaccount.setCountrycode(header.getCountryCode());
			currentaccount.setClearingcode(header.getClearingCode());
			if(!StringUtils.isEmpty(header.getSandBoxId())){
				currentaccount.setBranchcode(null);
			}else{
				currentaccount.setBranchcode(header.getBranchCode());
			}
			currentaccount.setSandboxid(header.getSandBoxId());
			currentaccount.setCustomernumber(header.getCustomerNumber());
			ResponseEntity<String> result = SRUtil.sendOne(restTemplate,PathConstant.GET_SAV, JsonProcess.changeEntityTOJSON(currentaccount));
            JSONObject temp = XmlToJsonUtil.xmlToJson(result.getBody());
            String temp_ = JsonProcess.returnValue(temp, "CurrentAccountInternalModel");
            CurrentAccountMasterModel currentresult = JSON.parseObject(temp_, CurrentAccountMasterModel.class);
			if(currentresult==null || "".equals(currentresult.getAccountnumber()) || !currentresult.getAccountnumber().equals(relaccountNumber)){
				throw new NotFoundException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404001),ExceptionConstant.ERROR_CODE404001);
			}else{
				map.put("msg", currentresult != null ? currentresult.getCustomernumber() : ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE404001));
				map.put("code", currentresult != null ? "1" : ExceptionConstant.ERROR_CODE404001);
			}
			
			return map;
		}
		map.put("msg", ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE201001));
		map.put("code", ExceptionConstant.ERROR_CODE201001);

		return map;

	}

}
