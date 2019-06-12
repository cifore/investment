package com.csi.sbs.investment.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class AccountNumber {
	
	@NotNull(message="accountNumber is a required field")
	@NotBlank(message="accountNumber is a required field")
	private String accountNumber;
	
	@ApiModelProperty(hidden=true)
	private String countryCode;

	@ApiModelProperty(hidden=true)
	private String clearingCode;

	@ApiModelProperty(hidden=true)
	private String branchCode;
	
	@ApiModelProperty(hidden=true)
	private String customerNumber;
	
	@ApiModelProperty(hidden=true)
	private String sandboxid;
	
	@ApiModelProperty(hidden=true)
	private String dockerid;
	
	
	

	public String getSandboxid() {
		return sandboxid;
	}

	public void setSandboxid(String sandboxid) {
		this.sandboxid = sandboxid;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getClearingCode() {
		return clearingCode;
	}

	public void setClearingCode(String clearingCode) {
		this.clearingCode = clearingCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getDockerid() {
		return dockerid;
	}

	public void setDockerid(String dockerid) {
		this.dockerid = dockerid;
	}
}
