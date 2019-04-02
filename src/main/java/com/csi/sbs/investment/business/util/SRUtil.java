package com.csi.sbs.investment.business.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.csi.sbs.common.business.util.InitRestTemplateUtil;
import com.csi.sbs.investment.business.clientmodel.HeaderModel;


public class SRUtil {
	
	
	/**
	 * 不带header
	 * @param path
	 * @param json
	 * @return
	 */
	public static ResponseEntity<String> sendOne(String path, String json) {
		ResponseEntity<String> result = InitRestTemplateUtil.getRestTemplate().postForEntity(path, PostUtil.getRequestEntity(json),String.class);
	    System.out.println("====="+result);
	    if(result.getStatusCodeValue()==200){
	    	return result;
	    }
		return result;
	}
	
	/**
	 * 带header
	 */
	public static ResponseEntity<String> sendTwo(String path, HeaderModel header,String json) {
		HttpHeaders requestHeaders = new HttpHeaders();
		
        requestHeaders.add("developerID", header.getUserID());
        requestHeaders.add("countryCode", header.getCountryCode());
        requestHeaders.add("clearingCode", header.getClearingCode());
        requestHeaders.add("branchCode", header.getBranchCode());
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, requestHeaders);

		ResponseEntity<String> result = InitRestTemplateUtil.getRestTemplate().postForEntity(path, requestEntity,String.class);
	    System.out.println("====="+result);
	    if(result.getStatusCodeValue()==200){
	    	return result;
	    }
		return result;
	}

}
