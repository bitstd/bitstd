package com.bitstd.model;

import java.math.BigDecimal;

public class QuotationBean {
	String coinID;
	String shortName;
	String englishName;
	String chineseName;
	BigDecimal marketCapUSD;
	BigDecimal marketCapRMB;
	BigDecimal turnover24hUSD;
	BigDecimal turnover24hRMB;
	BigDecimal volume24;
	BigDecimal liquidity;
	BigDecimal priceUSD;
	BigDecimal priceRMB;
	BigDecimal increasePercent;

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

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	public BigDecimal getMarketCapUSD() {
		return marketCapUSD;
	}

	public void setMarketCapUSD(BigDecimal marketCapUSD) {
		this.marketCapUSD = marketCapUSD;
	}

	public BigDecimal getMarketCapRMB() {
		return marketCapRMB;
	}

	public void setMarketCapRMB(BigDecimal marketCapRMB) {
		this.marketCapRMB = marketCapRMB;
	}

	public BigDecimal getTurnover24hUSD() {
		return turnover24hUSD;
	}

	public void setTurnover24hUSD(BigDecimal turnover24hUSD) {
		this.turnover24hUSD = turnover24hUSD;
	}

	public BigDecimal getTurnover24hRMB() {
		return turnover24hRMB;
	}

	public void setTurnover24hRMB(BigDecimal turnover24hRMB) {
		this.turnover24hRMB = turnover24hRMB;
	}

	public BigDecimal getVolume24() {
		return volume24;
	}

	public void setVolume24(BigDecimal volume24) {
		this.volume24 = volume24;
	}

	public BigDecimal getLiquidity() {
		return liquidity;
	}

	public void setLiquidity(BigDecimal liquidity) {
		this.liquidity = liquidity;
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

	public BigDecimal getIncreasePercent() {
		return increasePercent;
	}

	public void setIncreasePercent(BigDecimal increasePercent) {
		this.increasePercent = increasePercent;
	}

	public void QuotationBeanToString() {
		System.out.println(" coinID : " + coinID + "\n shortName : " + shortName + "\n englishName : " + englishName
				+ "\n chineseName : " + chineseName + "\n marketCapUSD : " + marketCapUSD + "\n marketCapRMB : "
				+ marketCapRMB + "\n turnover24hUSD : " + turnover24hUSD + "\n turnover24hRMB : " + turnover24hRMB
				+ "\n volume24 : " + volume24 + "\n liquidity : " + liquidity + "\n priceUSD : " + priceUSD
				+ "\n priceRMB : " + priceRMB + "\n increasePercent : " + increasePercent + "\n");
	}

}
