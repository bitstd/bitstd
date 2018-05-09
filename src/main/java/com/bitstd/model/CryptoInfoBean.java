package com.bitstd.model;

import java.math.BigDecimal;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 5/9/18
 */

public class CryptoInfoBean {
	int rank;
	String assetCode;
	BigDecimal usdPrice;
	BigDecimal toBtcPrice;
	String currencyType;
	BigDecimal circulatingSupply;
	BigDecimal volume_24h;
	BigDecimal market_cap;
	double percent_change_1h;
	double percent_change_24h;
	double percent_change_7d;

	public void CryptoInfoBeanToString() {
		System.out.println("rank : " + rank + " assetCode : " + assetCode + " usdPrice : " + usdPrice + " toBtcPrice : "
				+ toBtcPrice + " currencyType : " + currencyType + " circulatingSupply : " + circulatingSupply
				+ " volume_24h : " + volume_24h + " market_cap : " + market_cap + " percent_change_1h : "
				+ percent_change_1h + " percent_change_24h : " + percent_change_24h + " percent_change_7d : "
				+ percent_change_7d);
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public BigDecimal getUsdPrice() {
		return usdPrice;
	}

	public void setUsdPrice(BigDecimal usdPrice) {
		this.usdPrice = usdPrice;
	}

	public BigDecimal getToBtcPrice() {
		return toBtcPrice;
	}

	public void setToBtcPrice(BigDecimal toBtcPrice) {
		this.toBtcPrice = toBtcPrice;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public BigDecimal getCirculatingSupply() {
		return circulatingSupply;
	}

	public void setCirculatingSupply(BigDecimal circulatingSupply) {
		this.circulatingSupply = circulatingSupply;
	}

	public BigDecimal getVolume_24h() {
		return volume_24h;
	}

	public void setVolume_24h(BigDecimal volume_24h) {
		this.volume_24h = volume_24h;
	}

	public BigDecimal getMarket_cap() {
		return market_cap;
	}

	public void setMarket_cap(BigDecimal market_cap) {
		this.market_cap = market_cap;
	}

	public double getPercent_change_1h() {
		return percent_change_1h;
	}

	public void setPercent_change_1h(double percent_change_1h) {
		this.percent_change_1h = percent_change_1h;
	}

	public double getPercent_change_24h() {
		return percent_change_24h;
	}

	public void setPercent_change_24h(double percent_change_24h) {
		this.percent_change_24h = percent_change_24h;
	}

	public double getPercent_change_7d() {
		return percent_change_7d;
	}

	public void setPercent_change_7d(double percent_change_7d) {
		this.percent_change_7d = percent_change_7d;
	}

}
