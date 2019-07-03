package com.csi.sbs.investment.business.clientmodel;

import java.math.BigDecimal;

public class StockInvestmentModel {

    private String accountnumber;

    private String stockcode;
    
    private String currencycode;

    private BigDecimal sharesholdingno;

    private BigDecimal averageprice;
    
    private String lastupdatedate;

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public BigDecimal getAverageprice() {
        return averageprice;
    }

    public void setAverageprice(BigDecimal averageprice) {
        this.averageprice = averageprice;
    }

    public String getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(String lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

	public BigDecimal getSharesholdingno() {
		return sharesholdingno;
	}

	public void setSharesholdingno(BigDecimal sharesholdingno) {
		this.sharesholdingno = sharesholdingno;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
}