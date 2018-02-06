package com.bitstd.model;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/6/18
 */

public class TradeParam {
	String bitfinexParam;
	String bitstampParam;
	String coinbaseParam;
	String krakenParam;
	String bithumbParam;
	String bitType;
	String currencyType;

	public String getBitType() {
		return bitType;
	}

	public void setBitType(String bitType) {
		this.bitType = bitType;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getBitfinexParam() {
		return bitfinexParam;
	}

	public void setBitfinexParam(String bitfinexParam) {
		this.bitfinexParam = bitfinexParam;
	}

	public String getBitstampParam() {
		return bitstampParam;
	}

	public void setBitstampParam(String bitstampParam) {
		this.bitstampParam = bitstampParam;
	}

	public String getCoinbaseParam() {
		return coinbaseParam;
	}

	public void setCoinbaseParam(String coinbaseParam) {
		this.coinbaseParam = coinbaseParam;
	}

	public String getKrakenParam() {
		return krakenParam;
	}

	public void setKrakenParam(String krakenParam) {
		this.krakenParam = krakenParam;
	}

	public String getBithumbParam() {
		return bithumbParam;
	}

	public void setBithumbParam(String bithumbParam) {
		this.bithumbParam = bithumbParam;
	}

}
