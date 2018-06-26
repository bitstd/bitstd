package com.bitstd.model;

import java.math.BigDecimal;

public class MarketBean {
	String coinID;
	String shortName;
	String market;
	String marketName;
	String symbol;
	String currencyPair;
	BigDecimal price;

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	BigDecimal priceUSD;
	BigDecimal priceRMB;
	BigDecimal degree24h;
	BigDecimal vol24h;
	BigDecimal trade24h;
	
	BigDecimal netInflow;
	

	public BigDecimal getNetInflow() {
		return netInflow;
	}

	public void setNetInflow(BigDecimal netInflow) {
		this.netInflow = netInflow;
	}

	public BigDecimal getMFNInflow() {
		return MFNInflow;
	}

	public void setMFNInflow(BigDecimal mFNInflow) {
		MFNInflow = mFNInflow;
	}

	BigDecimal MFNInflow;
	
	BigDecimal trade24hUSD;
	public BigDecimal getTrade24hUSD() {
		return trade24hUSD;
	}

	public void setTrade24hUSD(BigDecimal trade24hUSD) {
		this.trade24hUSD = trade24hUSD;
	}

	public BigDecimal getNetInflowUSD() {
		return netInflowUSD;
	}

	public void setNetInflowUSD(BigDecimal netInflowUSD) {
		this.netInflowUSD = netInflowUSD;
	}

	public BigDecimal getMFNInflowUSD() {
		return MFNInflowUSD;
	}

	public void setMFNInflowUSD(BigDecimal mFNInflowUSD) {
		MFNInflowUSD = mFNInflowUSD;
	}

	BigDecimal netInflowUSD;
	BigDecimal MFNInflowUSD;
	

	public void MarketBeanToString() {
		System.out.println(" coinID : " + coinID + "\n shortName : " + shortName + "\n market : " + market
				+ "\n marketName : " + marketName + "\n symbol : " + symbol + "\n currencyPair : " + currencyPair
				+ "\n price : " + price + "\n priceUSD : " + priceUSD + "\n priceRMB : " + priceRMB + "\n degree24h : "
				+ degree24h + "\n vol24h : " + vol24h + "\n trade24h : " + trade24h + "\n netInflow : " + netInflow
				+ "\n MFNInflow : " + MFNInflow + "\n");
	}

	public String getCoinID() {
		return coinID;
	}

	public void setCoinID(String coinID) {
		this.coinID = coinID;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCurrencyPair() {
		return currencyPair;
	}

	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}

	public BigDecimal getPriceUSD() {
		return priceUSD;
	}

	public void setPriceUSD(BigDecimal priceUSD) {
		this.priceUSD = priceUSD;
	}

	public BigDecimal getPriceRMB() {
		return priceRMB;
	}

	public void setPriceRMB(BigDecimal priceRMB) {
		this.priceRMB = priceRMB;
	}

	public BigDecimal getDegree24h() {
		return degree24h;
	}

	public void setDegree24h(BigDecimal degree24h) {
		this.degree24h = degree24h;
	}

	public BigDecimal getVol24h() {
		return vol24h;
	}

	public void setVol24h(BigDecimal vol24h) {
		this.vol24h = vol24h;
	}

	public BigDecimal getTrade24h() {
		return trade24h;
	}

	public void setTrade24h(BigDecimal trade24h) {
		this.trade24h = trade24h;
	}

}
