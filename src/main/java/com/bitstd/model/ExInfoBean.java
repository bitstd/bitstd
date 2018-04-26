package com.bitstd.model;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 12/2/17
 */
public class ExInfoBean {
	private double price;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	private double volume;

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public void ExBeanToPrint(String tag) {
		System.out.println(tag + " ==> price : " + price + "\t" + "volume : " + volume);
	}
}
