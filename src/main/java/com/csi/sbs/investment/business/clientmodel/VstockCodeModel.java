package com.csi.sbs.investment.business.clientmodel;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class VstockCodeModel {
	
	@NotNull(message="stockCode is a required field")
	@NotBlank(message="stockCode is a required field")
	@ApiModelProperty(notes="Stock Code",example="0100.HK")
	private String stockCode;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

}
