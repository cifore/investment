package com.csi.sbs.investment.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class FundHoldingEntity {
    private String id;

    private String accountnumber;
    
    private String currencycode;

    private String fundcode;

    private BigDecimal sharesholding;

    private BigDecimal averageprice;

    private Date lastupdatedate;

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

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode == null ? null : fundcode.trim();
    }

    public BigDecimal getSharesholding() {
        return sharesholding;
    }

    public void setSharesholding(BigDecimal sharesholding) {
        this.sharesholding = sharesholding;
    }

    public BigDecimal getAverageprice() {
        return averageprice;
    }

    public void setAverageprice(BigDecimal averageprice) {
        this.averageprice = averageprice;
    }

    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
}