package com.csi.sbs.investment.business.clientmodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CheckAccountNumberModel {

	
	@ApiModelProperty(notes="the accountnumber which need to be validated"
	,example="HK780001001000001001")
	private String accountnumber;

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
}