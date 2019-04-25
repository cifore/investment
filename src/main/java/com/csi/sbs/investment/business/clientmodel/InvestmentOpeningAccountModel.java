package com.csi.sbs.investment.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class InvestmentOpeningAccountModel {
	
	@NotNull(message="accountnumber is a required field")
	@NotBlank(message="accountnumber is a required field")
	@ApiModelProperty(notes="A unique number used to identify a deposit bank account."
	,example="HK780001001000001001")
    private String accountnumber;
	
	@ApiModelProperty(hidden=true)
	private String countrycode;

	@ApiModelProperty(hidden=true)
    private String clearingcode;

	@ApiModelProperty(hidden=true)
    private String branchcode;
	
	
	
	
	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getClearingcode() {
		return clearingcode;
	}

	public void setClearingcode(String clearingcode) {
		this.clearingcode = clearingcode;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

   

}
