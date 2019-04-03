package com.csi.sbs.investment.business.entity;

import java.util.Date;

public class MutualFundEntity {
	
    private String id;

    private String customernumber;

    private String accountnumber;

    private String relaccountnumber;

    private String accountstatus;

    private Date lastupdateddate;
    
    private String countrycode;

    private String clearingcode;

    private String branchcode;
    
	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getClearingcode() {
		return clearingcode;
	}

	public void setClearingcode(String clearingcode) {
		this.clearingcode = clearingcode;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber == null ? null : customernumber.trim();
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
    }

    public String getRelaccountnumber() {
        return relaccountnumber;
    }

    public void setRelaccountnumber(String relaccountnumber) {
        this.relaccountnumber = relaccountnumber == null ? null : relaccountnumber.trim();
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus == null ? null : accountstatus.trim();
    }

    public Date getLastupdateddate() {
        return lastupdateddate;
    }

    public void setLastupdateddate(Date lastupdateddate) {
        this.lastupdateddate = lastupdateddate;
    }
}