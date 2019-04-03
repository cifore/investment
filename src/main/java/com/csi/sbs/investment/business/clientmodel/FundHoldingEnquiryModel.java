package com.csi.sbs.investment.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FundHoldingEnquiryModel{
	
	@NotNull(message = "fundccountnumber is a require field")
	@NotBlank(message = "fundccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Mutual fund Account Number",example="HK120001001000066500")
	private String fundaccountnumber;

	public String getFundaccountnumber() {
		return fundaccountnumber;
	}

	public void setFundaccountnumber(String fundaccountnumber) {
		this.fundaccountnumber = fundaccountnumber;
	}
	
}