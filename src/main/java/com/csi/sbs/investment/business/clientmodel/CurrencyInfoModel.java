package com.csi.sbs.investment.business.clientmodel;

import java.math.BigDecimal;

public class CurrencyInfoModel {
    private String id;

    private String currency;

    private String ccycode;

    private String ccyplaces;

    private BigDecimal bankbuy;

    private BigDecimal banksell;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public String getCcycode() {
        return ccycode;
    }

    public void setCcycode(String ccycode) {
        this.ccycode = ccycode == null ? null : ccycode.trim();
    }

    public String getCcyplaces() {
        return ccyplaces;
    }

    public void setCcyplaces(String ccyplaces) {
        this.ccyplaces = ccyplaces == null ? null : ccyplaces.trim();
    }

    public BigDecimal getBankbuy() {
        return bankbuy;
    }

    public void setBankbuy(BigDecimal bankbuy) {
        this.bankbuy = bankbuy;
    }

    public BigDecimal getBanksell() {
        return banksell;
    }

    public void setBanksell(BigDecimal banksell) {
        this.banksell = banksell;
    }
}