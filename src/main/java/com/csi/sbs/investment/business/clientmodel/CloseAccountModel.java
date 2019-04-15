package com.csi.sbs.investment.business.clientmodel;

public class CloseAccountModel {

	private String userID;
	private String countryCode;
	private String clearingCode;
	private String branchCode;
	private String customerNumber;
	private String accountNumber;
	private String sandBoxId;
	
	
	

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getClearingCode() {
		return clearingCode;
	}

	public void setClearingCode(String clearingCode) {
		this.clearingCode = clearingCode;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getSandBoxId() {
		return sandBoxId;
	}

	public void setSandBoxId(String sandBoxId) {
		this.sandBoxId = sandBoxId;
	}

}
