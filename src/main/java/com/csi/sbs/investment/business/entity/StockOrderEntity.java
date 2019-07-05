package com.csi.sbs.investment.business.entity;

import java.math.BigDecimal;

public class StockOrderEntity {
    private String id;

    private String stockaccountnumber;

    private String relaccountnumber;

    private String stockcode;

    private String ordertype;

    private String tradingoption;

    private Long sharingno;

    private BigDecimal tradingprice;

    private BigDecimal totalamount;

    private String currencycode;

    private BigDecimal requesttime;

    private BigDecimal expirydate;

    private String status;

    private String operationreasons;

    private BigDecimal operationdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStockaccountnumber() {
        return stockaccountnumber;
    }

    public void setStockaccountnumber(String stockaccountnumber) {
        this.stockaccountnumber = stockaccountnumber == null ? null : stockaccountnumber.trim();
    }

    public String getRelaccountnumber() {
        return relaccountnumber;
    }

    public void setRelaccountnumber(String relaccountnumber) {
        this.relaccountnumber = relaccountnumber == null ? null : relaccountnumber.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public String getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(String ordertype) {
        this.ordertype = ordertype == null ? null : ordertype.trim();
    }

    public String getTradingoption() {
        return tradingoption;
    }

    public void setTradingoption(String tradingoption) {
        this.tradingoption = tradingoption == null ? null : tradingoption.trim();
    }

    public Long getSharingno() {
        return sharingno;
    }

    public void setSharingno(Long sharingno) {
        this.sharingno = sharingno;
    }

    public BigDecimal getTradingprice() {
        return tradingprice;
    }

    public void setTradingprice(BigDecimal tradingprice) {
        this.tradingprice = tradingprice;
    }

    public BigDecimal getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(BigDecimal totalamount) {
        this.totalamount = totalamount;
    }

    public String getCurrencycode() {
        return currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode == null ? null : currencycode.trim();
    }

    public BigDecimal getRequesttime() {
        return requesttime;
    }

    public void setRequesttime(BigDecimal requesttime) {
        this.requesttime = requesttime;
    }

    public BigDecimal getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(BigDecimal expirydate) {
        this.expirydate = expirydate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getOperationreasons() {
        return operationreasons;
    }

    public void setOperationreasons(String operationreasons) {
        this.operationreasons = operationreasons == null ? null : operationreasons.trim();
    }

    public BigDecimal getOperationdate() {
        return operationdate;
    }

    public void setOperationdate(BigDecimal operationdate) {
        this.operationdate = operationdate;
    }
}