package com.csi.sbs.investment.business.clientmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;

@ApiModel
public class UpdateAccountBalanceModel {
	
    @NotNull(message="accountNumber is a required field")
	@NotBlank(message="accountNumber is a required field")		
    private String accountnumber;
    		
    private String currencycode;

    private BigDecimal balance;
    
    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

	public String getCurrencycode() {
		return currencycode;
	}

	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}
}