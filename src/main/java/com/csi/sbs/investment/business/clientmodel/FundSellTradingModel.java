package com.csi.sbs.investment.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FundSellTradingModel{
	
	@NotNull(message = "fundaccountnumber is a require field")
	@NotBlank(message = "fundaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Mutual Fund Account Number",example="HK120001001000066500")
	private String fundaccountnumber;
	
	@NotNull(message = "fundcode is a require field")
	@NotBlank(message = "fundcode is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Mutual Fund Code",example="U000001")
	private String fundcode;
	
	@NotNull(message = "sharingNo is a require field")
	@NotBlank(message = "sharingNo is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Mutual Fund Sharing No",example="1")
	private String sharingNo;
		
	@NotNull(message = "debitaccountnumber is a require field")
	@NotBlank(message = "debitaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Transfer from/to account number",example="HK780001001000001001")
	private String debitaccountnumber;
	
	public String getDebitaccountnumber() {
		return debitaccountnumber;
	}
	public void setDebitaccountnumber(String debitaccountnumber) {
		this.debitaccountnumber = debitaccountnumber;
	}
	public String getFundaccountnumber() {
		return fundaccountnumber;
	}
	public void setFundaccountnumber(String fundaccountnumber) {
		this.fundaccountnumber = fundaccountnumber;
	}
	public String getFundcode() {
		return fundcode;
	}
	public void setFundcode(String fundcode) {
		this.fundcode = fundcode;
	}
	public String getSharingNo() {
		return sharingNo;
	}
	public void setSharingNo(String sharingNo) {
		this.sharingNo = sharingNo;
	}	
}