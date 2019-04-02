package com.csi.sbs.investment.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class StockHoldingEntity {
    private String id;

    private String accountnumber;

    private String stockcode;

    private BigDecimal sharesholdingno;

    private BigDecimal averageprice;

    private Date lastupdatedate;
    
    private String lastupdatedatetime;

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

    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

	public BigDecimal getSharesholdingno() {
		return sharesholdingno;
	}

	public void setSharesholdingno(BigDecimal sharesholdingno) {
		this.sharesholdingno = sharesholdingno;
	}

	public String getLastupdatedatetime() {
		return lastupdatedatetime;
	}

	public void setLastupdatedatetime(String lastupdatedatetime) {
		this.lastupdatedatetime = lastupdatedatetime;
	}
}