package com.bitstd.model;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 2/6/18
 */

public class SupplyBean {
	String symbol;
	double total_supply;
	double max_supply;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public double getTotal_supply() {
		return total_supply;
	}
	public void setTotal_supply(double total_supply) {
		this.total_supply = total_supply;
	}
	public double getMax_supply() {
		return max_supply;
	}
	public void setMax_supply(double max_supply) {
		this.max_supply = max_supply;
	}
	
}
