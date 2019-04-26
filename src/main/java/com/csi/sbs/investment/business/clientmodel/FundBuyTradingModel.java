package com.csi.sbs.investment.business.clientmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FundBuyTradingModel{
	
	@NotNull(message = "fundaccountnumber is a require field")
	@NotBlank(message = "fundaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Mutual Fund Account Number",example="HK720001001000000007500")
	private String fundaccountnumber;
	
	@NotNull(message = "fundcode is a require field")
	@NotBlank(message = "fundcode is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Mutual Fund Code",example="U000001")
	private String fundcode;
	
	@NotNull(message = "tradingamount is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Trading Amount",example="100")
	private BigDecimal tradingamount;
		
	@NotNull(message = "debitaccountnumber is a require field")
	@NotBlank(message = "debitaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "A debit account number is used to make transactions with the associated mutual fund account.",example="HK720001001000000001001")
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
	public BigDecimal getTradingamount() {
		return tradingamount;
	}
	public void setTradingamount(BigDecimal tradingamount) {
		this.tradingamount = tradingamount;
	}
	
}