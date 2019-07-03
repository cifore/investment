package com.csi.sbs.investment.business.clientmodel;

import java.math.BigDecimal;

public class FundInvestmentModel {
    private String id;

    private String accountnumber;

    private String fundcode;
    
    private String currencycode;

    private BigDecimal sharesholdingno;

    private BigDecimal averageprice;
    
    private String lastupdatedate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
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

	public String getFundcode() {
		return fundcode;
	}

	public void setFundcode(String fundcode) {
		this.fundcode = fundcode;
	}

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
}