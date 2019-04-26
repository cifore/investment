package com.csi.sbs.investment.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class StockHoldingEnquiryModel{
	
	@NotNull(message = "stkaccountnumber is a require field")
	@NotBlank(message = "stkaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Stock Trading Account Number",example="HK060001001000000008300")
	private String stkaccountnumber;
	
	public String getStkaccountnumber() {
		return stkaccountnumber;
	}
	public void setStkaccountnumber(String stkaccountnumber) {
		this.stkaccountnumber = stkaccountnumber;
	}
	
}