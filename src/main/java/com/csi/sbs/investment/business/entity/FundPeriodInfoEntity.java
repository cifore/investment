package com.csi.sbs.investment.business.entity;

import java.math.BigDecimal;

public class FundPeriodInfoEntity {
    private String id;

    private String fundcode;

    private BigDecimal onem;

    private BigDecimal threem;

    private BigDecimal sixm;

    private BigDecimal ytd;

    private BigDecimal oney;

    private BigDecimal threey;

    private BigDecimal fivey;

    private BigDecimal sincelaunch;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFundcode() {
        return fundcode;
    }

    public void setFundcode(String fundcode) {
        this.fundcode = fundcode == null ? null : fundcode.trim();
    }

    public BigDecimal getOnem() {
        return onem;
    }

    public void setOnem(BigDecimal onem) {
        this.onem = onem;
    }

    public BigDecimal getThreem() {
        return threem;
    }

    public void setThreem(BigDecimal threem) {
        this.threem = threem;
    }

    public BigDecimal getSixm() {
        return sixm;
    }

    public void setSixm(BigDecimal sixm) {
        this.sixm = sixm;
    }

    public BigDecimal getYtd() {
        return ytd;
    }

    public void setYtd(BigDecimal ytd) {
        this.ytd = ytd;
    }

    public BigDecimal getOney() {
        return oney;
    }

    public void setOney(BigDecimal oney) {
        this.oney = oney;
    }

    public BigDecimal getThreey() {
        return threey;
    }

    public void setThreey(BigDecimal threey) {
        this.threey = threey;
    }

    public BigDecimal getFivey() {
        return fivey;
    }

    public void setFivey(BigDecimal fivey) {
        this.fivey = fivey;
    }

    public BigDecimal getSincelaunch() {
        return sincelaunch;
    }

    public void setSincelaunch(BigDecimal sincelaunch) {
        this.sincelaunch = sincelaunch;
    }
}