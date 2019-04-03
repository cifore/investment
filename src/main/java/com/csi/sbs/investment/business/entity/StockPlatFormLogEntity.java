package com.csi.sbs.investment.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class StockPlatFormLogEntity {
    private String id;

    private String countrycode;

    private String clearingcode;

    private String branchcode;

    private String accountnumber;

    private String tradingoption;

    private String stocknumber;

    private BigDecimal sharingno;

    private BigDecimal stocktrdingamount;

    private BigDecimal stocktrdingcommission;

    private BigDecimal custodycharges;

    private BigDecimal transactionamount;

    private Date transactiondate;
    
    //表外字段
    private String transFromDate;
    
    private String transToDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode == null ? null : countrycode.trim();
    }

    public String getClearingcode() {
        return clearingcode;
    }

    public void setClearingcode(String clearingcode) {
        this.clearingcode = clearingcode == null ? null : clearingcode.trim();
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode == null ? null : branchcode.trim();
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
    }

    public String getTradingoption() {
        return tradingoption;
    }

    public void setTradingoption(String tradingoption) {
        this.tradingoption = tradingoption == null ? null : tradingoption.trim();
    }

    public String getStocknumber() {
        return stocknumber;
    }

    public void setStocknumber(String stocknumber) {
        this.stocknumber = stocknumber == null ? null : stocknumber.trim();
    }

    public BigDecimal getSharingno() {
        return sharingno;
    }

    public void setSharingno(BigDecimal sharingno) {
        this.sharingno = sharingno;
    }

    public BigDecimal getStocktrdingamount() {
        return stocktrdingamount;
    }

    public void setStocktrdingamount(BigDecimal stocktrdingamount) {
        this.stocktrdingamount = stocktrdingamount;
    }

    public BigDecimal getStocktrdingcommission() {
        return stocktrdingcommission;
    }

    public void setStocktrdingcommission(BigDecimal stocktrdingcommission) {
        this.stocktrdingcommission = stocktrdingcommission;
    }

    public BigDecimal getCustodycharges() {
        return custodycharges;
    }

    public void setCustodycharges(BigDecimal custodycharges) {
        this.custodycharges = custodycharges;
    }

    public BigDecimal getTransactionamount() {
        return transactionamount;
    }

    public void setTransactionamount(BigDecimal transactionamount) {
        this.transactionamount = transactionamount;
    }

    public Date getTransactiondate() {
        return transactiondate;
    }

    public void setTransactiondate(Date transactiondate) {
        this.transactiondate = transactiondate;
    }

	public String getTransFromDate() {
		return transFromDate;
	}

	public void setTransFromDate(String transFromDate) {
		this.transFromDate = transFromDate;
	}

	public String getTransToDate() {
		return transToDate;
	}

	public void setTransToDate(String transToDate) {
		this.transToDate = transToDate;
	}
}