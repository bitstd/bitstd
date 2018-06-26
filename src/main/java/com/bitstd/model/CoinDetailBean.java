package com.bitstd.model;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

public class CoinDetailBean {
	String coinID;
	long publishTime;
	String publish_price;
	BigDecimal publish_amount;
	String algorithm;
	String describe;
	String linkMap;
	String teamMap;
	String developMap;

	public void CoinDetailBeanToString() {
		System.out.println(" coinID : " + coinID + "\n publishTime : " + publishTime + "\n publish_price : "
				+ publish_price + "\n publish_amount : " + publish_amount + "\n algorithm : " + algorithm
				+ "\n describe : " + describe + "\n linkMap : " + linkMap + "\n teamMap : " + teamMap
				+ "\n developMap : " + developMap + "\n");
	}

	public String getCoinID() {
		return coinID;
	}

	public void setCoinID(String coinID) {
		this.coinID = coinID;
	}

	public long getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(long publishTime) {
		this.publishTime = publishTime;
	}

	public String getPublish_price() {
		return publish_price;
	}

	public void setPublish_price(String publish_price) {
		this.publish_price = publish_price;
	}

	public BigDecimal getPublish_amount() {
		return publish_amount;
	}

	public void setPublish_amount(BigDecimal publish_amount) {
		this.publish_amount = publish_amount;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getLinkMap() {
		return linkMap;
	}

	public void setLinkMap(String linkMap) {
		this.linkMap = linkMap;
	}

	public String getTeamMap() {
		return teamMap;
	}

	public void setTeamMap(String teamMap) {
		this.teamMap = teamMap;
	}

	public String getDevelopMap() {
		return developMap;
	}

	public void setDevelopMap(String developMap) {
		this.developMap = developMap;
	}

}
