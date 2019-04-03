package com.csi.sbs.investment.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class FundMarketInfoEntity {
    private String id;

    private String fundcode;

    private String fundtype;

    private String fundhouse;

    private String sector;

    private String geographic;

    private String fundname;

    private String fundcurrency;

    private BigDecimal managementfee;

    private BigDecimal lastnav;

    private Date valuationdate;

    private Date issuedate;
    
    /*
     * 表外字段
     * */
    private BigDecimal managerangefee;

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

    public String getFundtype() {
        return fundtype;
    }

    public void setFundtype(String fundtype) {
        this.fundtype = fundtype == null ? null : fundtype.trim();
    }

    public String getFundhouse() {
        return fundhouse;
    }

    public void setFundhouse(String fundhouse) {
        this.fundhouse = fundhouse == null ? null : fundhouse.trim();
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector == null ? null : sector.trim();
    }

    public String getGeographic() {
        return geographic;
    }

    public void setGeographic(String geographic) {
        this.geographic = geographic == null ? null : geographic.trim();
    }

    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname == null ? null : fundname.trim();
    }

    public String getFundcurrency() {
        return fundcurrency;
    }

    public void setFundcurrency(String fundcurrency) {
        this.fundcurrency = fundcurrency == null ? null : fundcurrency.trim();
    }

    public BigDecimal getManagementfee() {
        return managementfee;
    }

    public void setManagementfee(BigDecimal managementfee) {
        this.managementfee = managementfee;
    }

    public BigDecimal getLastnav() {
        return lastnav;
    }

    public void setLastnav(BigDecimal lastnav) {
        this.lastnav = lastnav;
    }

    public Date getValuationdate() {
        return valuationdate;
    }

    public void setValuationdate(Date valuationdate) {
        this.valuationdate = valuationdate;
    }

    public Date getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Date issuedate) {
        this.issuedate = issuedate;
    }

	public BigDecimal getManagerangefee() {
		return managerangefee;
	}

	public void setManagerangefee(BigDecimal managerangefee) {
		this.managerangefee = managerangefee;
	}
}