package com.csi.sbs.investment.business.clientmodel;

import io.swagger.annotations.ApiModelProperty;

public class VstockCodeModel {
	
	@ApiModelProperty(notes="",example="0100.HK")
	private String stockCode;

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

}
