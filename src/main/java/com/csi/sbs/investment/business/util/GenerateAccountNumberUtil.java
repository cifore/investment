package com.csi.sbs.investment.business.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.csi.sbs.common.business.constant.CommonConstant;
import com.csi.sbs.common.business.json.JsonProcess;
import com.csi.sbs.investment.business.clientmodel.HeaderModel;
import com.csi.sbs.investment.business.constant.ExceptionConstant;
import com.csi.sbs.investment.business.constant.SysConstant;
import com.csi.sbs.investment.business.exception.OtherException;

public class GenerateAccountNumberUtil {

	public static Map<String, Object> getAccountNumber(String accountType,
			HeaderModel header, RestTemplate restTemplate) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 调用服务接口地址
		String param1 = "{\"apiname\":\"getSystemParameter\"}";
		ResponseEntity<String> result1 = restTemplate.postForEntity(
				"http://" + CommonConstant.getSYSADMIN() + SysConstant.SERVICE_INTERNAL_URL + "",
				PostUtil.getRequestEntity(param1), String.class);
		if (result1.getStatusCodeValue() != 200) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500002),ExceptionConstant.ERROR_CODE500002);
		}
		String path = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result1.getBody()), "internaURL");

		// 调用系统参数服务接口
		String param2 = "{\"item\":\"LocalCcy,NextAvailableCustomerNumber\"}";
		ResponseEntity<String> result2 = restTemplate.postForEntity(path, PostUtil.getRequestEntity(param2),
				String.class);
		if (result2.getStatusCodeValue() != 200) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}

		// 返回数据处理
		String localCCy = "";
		String accountnumber = "";
		JSONObject jsonObject1 = null;
		String revalue = null;
		String temp = null;
		for (int i = 0; i < JsonProcess.changeToJSONArray(result2.getBody()).size(); i++) {
			jsonObject1 = JsonProcess
					.changeToJSONObject(JsonProcess.changeToJSONArray(result2.getBody()).get(i).toString());
			revalue = JsonProcess.returnValue(jsonObject1, "item");
			temp = JsonProcess.returnValue(jsonObject1, "value");
			if (revalue.equals("LocalCcy")) {
				localCCy = temp;
			}
		}

		// 调用可用accountNumber服务接口
		String result3 = restTemplate
				.getForEntity("http://SYSADMIN/sysadmin/generate/getNextAvailableNumber/NextAvailableAccountNumber",
						String.class)
				.getBody();
		String nextAccountNumber = "";
		if (result3 == null) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}

		// 调用可用customerNumber服务接口
		String result4 = restTemplate
				.getForEntity("http://SYSADMIN/sysadmin/generate/getNextAvailableNumber/NextAvailableCustomerNumber",
						String.class)
				.getBody();
		String customerNumber = "";
		if (result4 == null) {
			throw new OtherException(ExceptionConstant.getExceptionMap().get(ExceptionConstant.ERROR_CODE500003),ExceptionConstant.ERROR_CODE500003);
		}

		// 返回数据处理
		nextAccountNumber = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result3), "nextAvailableNumber");

		customerNumber = JsonProcess.returnValue(JsonProcess.changeToJSONObject(result4), "nextAvailableNumber");;
		
		accountnumber = header.getCountryCode() + "00" + header.getClearingCode() + header.getBranchCode()
				+ nextAccountNumber + accountType;

		accountnumber = getAccountNumberFunc(accountnumber);

		map.put("msg", "处理成功");
		map.put("localCCy", localCCy);
		map.put("accountNumber", accountnumber);
		map.put("customerNumber", header.getBranchCode() + customerNumber);
		map.put("code", "1");

		return map;
	}
	
	private static String getAccountNumberFunc(String D) {
		if (D != null && D.length() == 20) {
			String accountnumber = "";
			String still = D.substring(4);
			String countrycode = D.substring(0, 2);
			String countrycodenumber = getCountryCodeNumber(countrycode);
			String checkDigit = "";
			D = still + countrycodenumber + "00";
			D = D.replaceFirst("^0*", "");
			String N = D.substring(0, 9);
			D = D.substring(9);
			for (@SuppressWarnings("unused")
			int i = 0; N.length() > 2; i++) {
				Integer j = Integer.parseInt(N);
				String remainer = String.valueOf(j % 97);
				if (D.length() > 7) {
					N = remainer + D.substring(0, 7);
					D = D.substring(7);
				} else {
					N = remainer + D.substring(0);
					N = String.valueOf(Integer.parseInt(N) % 97);
				}
			}
			checkDigit = String.valueOf(98 - Integer.parseInt(N));
			if (checkDigit.length() == 1) {
				checkDigit = "0" + checkDigit;
			}
			accountnumber = countrycode + checkDigit + still;
			return accountnumber;
		} else {
			return "N";
		}
	}
	
	private static String getCountryCodeNumber(String countrycode) {
		String number = "";
		for (int i = 0; i < countrycode.length(); i++) {
			switch (countrycode.substring(i, i + 1).toUpperCase()) {
			case "A":
				number += "10";
				break;
			case "B":
				number += "11";
				break;
			case "C":
				number += "12";
				break;
			case "D":
				number += "13";
				break;
			case "E":
				number += "14";
				break;
			case "F":
				number += "15";
				break;
			case "G":
				number += "16";
				break;
			case "H":
				number += "17";
				break;
			case "I":
				number += "18";
				break;
			case "J":
				number += "19";
				break;
			case "K":
				number += "20";
				break;
			case "L":
				number += "21";
				break;
			case "M":
				number += "22";
				break;
			case "N":
				number += "23";
				break;
			case "O":
				number += "24";
				break;
			case "P":
				number += "25";
				break;
			case "Q":
				number += "26";
				break;
			case "R":
				number += "27";
				break;
			case "S":
				number += "28";
				break;
			case "T":
				number += "29";
				break;
			case "U":
				number += "30";
				break;
			case "V":
				number += "31";
				break;
			case "W":
				number += "32";
				break;
			case "X":
				number += "33";
				break;
			case "Y":
				number += "34";
				break;
			case "Z":
				number += "35";
				break;
			default:
				break;
			}
		}
		return number;
	}

}
