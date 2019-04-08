package com.csi.sbs.investment.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class StockTradingModel{
	
	@NotNull(message = "stkaccountnumber is a require field")
	@NotBlank(message = "stkaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Stock Trading Account Number",example="HK750001001000009300")
	private String stkaccountnumber;
	
	@NotNull(message = "tradingOption is a require field")
	@NotBlank(message = "tradingOption is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Trading Optionr Buy or Sell",example="Buy")
	private String tradingOption;
	
	@NotNull(message = "stocknumber is a require field")
	@NotBlank(message = "stocknumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Stock Number",example="0100.HK")
	private String stocknumber;
	
	@NotNull(message = "orderType is a require field")
	@NotBlank(message = "orderType is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Order Type: Market Price or Fix Price",example="Fix Price")
	private String orderType;
	
	@ApiModelProperty(allowEmptyValue=true,required=false,notes = "Keep tradingPrice empty when orderType is Market Price",example="7.2")
	private String tradingPrice;
	
	@ApiModelProperty(allowEmptyValue=true,required=false,notes = "No. of Shares",example="200")
	private String sharingNo;

	@ApiModelProperty(allowEmptyValue=true,required=false,notes = "Keep sellAll empty when tradingOption is Buy",example="N")
	private String sellAll;
	

	@ApiModelProperty(allowEmptyValue=true,required=false,notes="Keep expiredate empty when orderType is Market Price"
			,example="YYYY-MM-DD")
	private String expiredate;
	
	@NotNull(message = "debitaccountnumber is a require field")
	@NotBlank(message = "debitaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Transfer from/to account number",example="HK780001001000001001")
	private String debitaccountnumber;
	
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
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getSellAll() {
		return sellAll;
	}
	public void setSellAll(String sellAll) {
		this.sellAll = sellAll;
	}
	public String getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}
	public String getDebitaccountnumber() {
		return debitaccountnumber;
	}
	public void setDebitaccountnumber(String debitaccountnumber) {
		this.debitaccountnumber = debitaccountnumber;
	}

	public String getTradingPrice() {
		return tradingPrice;
	}
	public void setTradingPrice(String tradingPrice) {
		this.tradingPrice = tradingPrice;
	}
	public String getSharingNo() {
		return sharingNo;
	}
	public void setSharingNo(String sharingNo) {
		this.sharingNo = sharingNo;
	}
	
}