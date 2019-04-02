package com.csi.sbs.investment.business.clientmodel;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class InsertTransactionLogModel {

	@NotNull(message="accountnumber is a required field")
	@NotBlank(message="accountnumber is a required field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes="the transaction accountnumber"
	,example="HK440001001000002001")
    private String accountnumber;

	@NotNull(message="channel is a required field")
	@NotBlank(message="channel is a required field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes="The transaction channel.",
	example="API")
    private String channel;

	@NotNull(message="channelid is a required field")
	@NotBlank(message="channelid is a required field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes="The unique id that identifies a specific transaction terminal."
	,example="123")
    private String channelid;

	@ApiModelProperty(hidden=true)
    private String countrycode;

	@ApiModelProperty(hidden=true)
    private String clearingcode;

	@ApiModelProperty(hidden=true)
    private String branchcode;

	@NotNull(message="trantype is a required field")
	@NotBlank(message="trantype is a required field")
	@ApiModelProperty(allowEmptyValue=false,required=true,
	notes="The transaction type.</br> Possible Values:0001-TD Application,0002-TD Drawdown,0003 - TD Renewal,</br>0004-Deposit,0005–Transfer,0006-Withdraw,0007-Foreign Buy,0008-Foreign Sell",
	example="0001")
    private String trantype;

	@NotNull(message="tranamt is a required field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes="Transaction amount. ",example="300")
    private BigDecimal tranamt;

	@NotNull(message="previousbalamt is a required field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes="The initial account balance before the transaction happened. ",example="30000")
    private BigDecimal previousbalamt;

	@NotNull(message="actualbalamt is a required field")
	@ApiModelProperty(allowEmptyValue=false,required=true,notes="The final account balance after the transaction happened.",example="25000")
    private BigDecimal actualbalamt;

	@ApiModelProperty(allowEmptyValue=true,required=false,notes="The relevant account number with whom the transaction happened.",example="HK500001001000063100")
    private String refaccountnumber;

	@ApiModelProperty(allowEmptyValue=true,required=false,notes="The transfer trading flow number.",example="")
    private String tfrseqno;

    @NotNull(message="crdrmaintind is a required field")
	@NotBlank(message="crdrmaintind is a required field")
    @ApiModelProperty(allowEmptyValue=false,required=true,notes="A unique sign to show whether the transaction is deposit or withdraw.</br>Sample: D (means Deposit),Sample: C (means Withdraw)",example="D")
    private String crdrmaintind;

    
    @ApiModelProperty(allowEmptyValue=true,required=false,notes="Transaction description.",example="")
    private String trandesc;

    @NotNull(message="ccy is a required field")
	@NotBlank(message="ccy is a required field")
    @ApiModelProperty(allowEmptyValue=false,required=true,example="HKD")
    private String ccy;


    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber == null ? null : accountnumber.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid == null ? null : channelid.trim();
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

    public String getTrantype() {
        return trantype;
    }

    public void setTrantype(String trantype) {
        this.trantype = trantype == null ? null : trantype.trim();
    }

    public BigDecimal getTranamt() {
        return tranamt;
    }

    public void setTranamt(BigDecimal tranamt) {
        this.tranamt = tranamt;
    }

    public BigDecimal getPreviousbalamt() {
        return previousbalamt;
    }

    public void setPreviousbalamt(BigDecimal previousbalamt) {
        this.previousbalamt = previousbalamt;
    }

    public BigDecimal getActualbalamt() {
        return actualbalamt;
    }

    public void setActualbalamt(BigDecimal actualbalamt) {
        this.actualbalamt = actualbalamt;
    }

    public String getRefaccountnumber() {
        return refaccountnumber;
    }

    public void setRefaccountnumber(String refaccountnumber) {
        this.refaccountnumber = refaccountnumber == null ? null : refaccountnumber.trim();
    }

    public String getTfrseqno() {
        return tfrseqno;
    }

    public void setTfrseqno(String tfrseqno) {
        this.tfrseqno = tfrseqno == null ? null : tfrseqno.trim();
    }

    public String getCrdrmaintind() {
        return crdrmaintind;
    }

    public void setCrdrmaintind(String crdrmaintind) {
        this.crdrmaintind = crdrmaintind == null ? null : crdrmaintind.trim();
    }

    public String getTrandesc() {
        return trandesc;
    }

    public void setTrandesc(String trandesc) {
        this.trandesc = trandesc == null ? null : trandesc.trim();
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy == null ? null : ccy.trim();
    }
}