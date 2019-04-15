package com.csi.sbs.investment.business.clientmodel.otherservice;

import java.util.Date;

public class AddMutualDepositModel {

	private String id;

	private String customernumber;

	private String accountnumber;

	private String relaccountnumber;

	private String accountstatus;

	private Date lastupdateddate;

	private String countrycode;

	private String clearingcode;

	private String branchcode;

	private String sandboxid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomernumber() {
		return customernumber;
	}

	public void setCustomernumber(String customernumber) {
		this.customernumber = customernumber;
	}

	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}

	public String getRelaccountnumber() {
		return relaccountnumber;
	}

	public void setRelaccountnumber(String relaccountnumber) {
		this.relaccountnumber = relaccountnumber;
	}

	public String getAccountstatus() {
		return accountstatus;
	}

	public void setAccountstatus(String accountstatus) {
		this.accountstatus = accountstatus;
	}

	public Date getLastupdateddate() {
		return lastupdateddate;
	}

	public void setLastupdateddate(Date lastupdateddate) {
		this.lastupdateddate = lastupdateddate;
	}

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

	public String getSandboxid() {
		return sandboxid;
	}

	public void setSandboxid(String sandboxid) {
		this.sandboxid = sandboxid;
	}

}
