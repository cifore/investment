package com.csi.sbs.investment.business.clientmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class StockTradingPlatformModel{
	
	@NotNull(message = "stkaccountnumber is a require field")
	@NotBlank(message = "stkaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Stock Trading Account Number",example="")
	private String stkaccountnumber;
	
	@NotNull(message = "tradingOption is a require field")
	@NotBlank(message = "tradingOption is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Trading Optionr Buy or Sell",example="Buy")
	private String tradingOption;
	
	@NotNull(message = "stocknumber is a require field")
	@NotBlank(message = "stocknumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Stock Number",example="00001")
	private String stocknumber;
	
	@NotNull(message = "tradingPrice is a require field")
	@NotBlank(message = "tradingPrice is a require field")
	@ApiModelProperty(allowEmptyValue=true,required=false,notes = "Buy/Sell Price",example="93.50")
	private BigDecimal tradingPrice;
	
	@NotNull(message = "sharingNo is a require field")
	@NotBlank(message = "sharingNo is a require field")
	@ApiModelProperty(allowEmptyValue=true,required=false,notes = "No. of Shares",example="400")
	private BigDecimal sharingNo;
	
	public String getStkaccountnumber() {
		return stkaccountnumber;
	}
	public void setStkaccountnumber(String stkaccountnumber) {
		this.stkaccountnumber = stkaccountnumber;
	}
	public String getTradingOption() {
		return tradingOption;
	}
	public void setTradingOption(String tradingOption) {
		this.tradingOption = tradingOption;
	}
	public String getStocknumber() {
		return stocknumber;
	}
	public void setStocknumber(String stocknumber) {
		this.stocknumber = stocknumber;
	}
	public BigDecimal getTradingPrice() {
		return tradingPrice;
	}
	public void setTradingPrice(BigDecimal tradingPrice) {
		this.tradingPrice = tradingPrice;
	}
	public BigDecimal getSharingNo() {
		return sharingNo;
	}
	public void setSharingNo(BigDecimal sharingNo) {
		this.sharingNo = sharingNo;
	}
	
}