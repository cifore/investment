package com.csi.sbs.investment.business.entity;

import java.math.BigDecimal;

public class StockInformationEntity {
    private String id;

    private String stockcode;

    private BigDecimal lastprice;

    private String changed;

    private String changedpercent;

    private BigDecimal buyprice;

    private BigDecimal sellprice;

    private BigDecimal volume;

    private BigDecimal turnover;

    private BigDecimal eps;

    private BigDecimal ratio;

    private BigDecimal lotsize;
    
    private BigDecimal tradingpoint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStockcode() {
        return stockcode;
    }

    public void setStockcode(String stockcode) {
        this.stockcode = stockcode == null ? null : stockcode.trim();
    }

    public BigDecimal getLastprice() {
        return lastprice;
    }

    public void setLastprice(BigDecimal lastprice) {
        this.lastprice = lastprice;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed == null ? null : changed.trim();
    }

    public String getChangedpercent() {
        return changedpercent;
    }

    public void setChangedpercent(String changedpercent) {
        this.changedpercent = changedpercent == null ? null : changedpercent.trim();
    }

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    public BigDecimal getSellprice() {
        return sellprice;
    }

    public void setSellprice(BigDecimal sellprice) {
        this.sellprice = sellprice;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    public BigDecimal getEps() {
        return eps;
    }

    public void setEps(BigDecimal eps) {
        this.eps = eps;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public BigDecimal getLotsize() {
        return lotsize;
    }

    public void setLotsize(BigDecimal lotsize) {
        this.lotsize = lotsize;
    }

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getTradingpoint() {
		return tradingpoint;
	}

	public void setTradingpoint(BigDecimal tradingpoint) {
		this.tradingpoint = tradingpoint;
	}
}