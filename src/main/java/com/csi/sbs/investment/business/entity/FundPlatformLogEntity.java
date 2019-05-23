package com.csi.sbs.investment.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class FundPlatformLogEntity {
    private String id;

    private String countrycode;

    private String clearingcode;

    private String branchcode;
    
    private String sandboxid;
    
    private String dockerid;
    
    private String currencycode;

    private String accountnumber;

    private String tradingoption;

    private String fundcode;

    private BigDecimal sharingno;

    private BigDecimal tradingamount;

    private BigDecimal trdingcommission;

    private BigDecimal transactionamount;

    private Date transactiondate;
    
    
    
    

    public String getDockerid() {
		return dockerid;
	}

	public void setDockerid(String dockerid) {
		this.dockerid = dockerid;
	}

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

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode == null ? null : fundcode.trim();
    }

    public BigDecimal getSharingno() {
        return sharingno;
    }

    public void setSharingno(BigDecimal sharingno) {
        this.sharingno = sharingno;
    }

    public BigDecimal getTradingamount() {
        return tradingamount;
    }

    public void setTradingamount(BigDecimal tradingamount) {
        this.tradingamount = tradingamount;
    }

    public BigDecimal getTrdingcommission() {
        return trdingcommission;
    }

    public void setTrdingcommission(BigDecimal trdingcommission) {
        this.trdingcommission = trdingcommission;
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

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

	public String getSandboxid() {
		return sandboxid;
	}

	public void setSandboxid(String sandboxid) {
		this.sandboxid = sandboxid;
	}
}