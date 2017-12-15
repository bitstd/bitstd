package com.bitstd.model;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/2/17
 */
public class AvgInfoBean {
	String bittype;
	double price;
	String currency;
	double rate;
	double usdprice;

	public double getUsdprice() {
		return usdprice;
	}

	public void setUsdprice(double usdprice) {
		this.usdprice = usdprice;
	}

	public String getBittype() {
		return bittype;
	}

	public void setBittype(String bittype) {
		this.bittype = bittype;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

}
