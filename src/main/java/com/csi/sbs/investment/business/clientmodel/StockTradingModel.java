package com.csi.sbs.investment.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class StockTradingModel{
	
	@NotNull(message = "stkaccountnumber is a require field")
	@NotBlank(message = "stkaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Stock Trading Account Number",example="HK060001001000000008300")
	private String stkaccountnumber;
	
	@NotNull(message = "tradingOption is a require field")
	@NotBlank(message = "tradingOption is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "To buy or sell stocks.",example="Buy")
	private String tradingOption;
	
	@NotNull(message = "stockCode is a require field")
	@NotBlank(message = "stockCode is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "Stock code.",example="0100.HK")
	private String stockCode;
	
	@NotNull(message = "orderType is a require field")
	@NotBlank(message = "orderType is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "The order type for the trading price. Order Type: Market Price or Fix Price",example="Fix Price")
	private String orderType;
	
	@ApiModelProperty(allowEmptyValue=true,required=false,notes = "The price set for the stock trading when orderType is set Fix price.",example="7.2")
	private String tradingPrice;
	
	@ApiModelProperty(allowEmptyValue=true,required=false,notes = "The stock number you want to buy/sell.",example="200")
	private String sharingNo;

	@ApiModelProperty(allowEmptyValue=true,required=false,notes = "Whether to sell all the holding stocks. Keep sellAll empty when tradingOption is Buy",example="N")
	private String sellAll;
	

	@ApiModelProperty(allowEmptyValue=true,required=false,notes="Expiry date set for fix price."
			+ "Keep expiry date empty when orderType is Market Price. "
			,example="YYYY-MM-DD")
	private String expiryDate;
	
	@NotNull(message = "debitaccountnumber is a require field")
	@NotBlank(message = "debitaccountnumber is a require field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes = "A debit account number is used to make transactions with the associated stock trading account.",example="HK720001001000000001001")
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
	
	public String getStockCode() {
		return stockCode;
	}
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
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
	
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
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