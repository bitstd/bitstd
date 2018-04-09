package com.bitstd.model;

/**
 * @file
 * @copyright defined in BitSTD/LICENSE.txt
 * @author BitSTD
 * @created 4/9/18
 */
public class IndexBean {
	String type;
	String currentIndex;
	String riseAndfall;
	String risefallIndex;
	String openingIndex;
	String highIndex;
	String lowIndex;
	String closingIndex;
	String weekHighIndex;
	String weeklowIndex;
	String time;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(String currentIndex) {
		this.currentIndex = currentIndex;
	}

	public String getRiseAndfall() {
		return riseAndfall;
	}

	public void setRiseAndfall(String riseAndfall) {
		this.riseAndfall = riseAndfall;
	}

	public String getRisefallIndex() {
		return risefallIndex;
	}

	public void setRisefallIndex(String risefallIndex) {
		this.risefallIndex = risefallIndex;
	}

	public String getOpeningIndex() {
		return openingIndex;
	}

	public void setOpeningIndex(String openingIndex) {
		this.openingIndex = openingIndex;
	}

	public String getHighIndex() {
		return highIndex;
	}

	public void setHighIndex(String highIndex) {
		this.highIndex = highIndex;
	}

	public String getLowIndex() {
		return lowIndex;
	}

	public void setLowIndex(String lowIndex) {
		this.lowIndex = lowIndex;
	}

	public String getClosingIndex() {
		return closingIndex;
	}

	public void setClosingIndex(String closingIndex) {
		this.closingIndex = closingIndex;
	}

	public String getWeekHighIndex() {
		return weekHighIndex;
	}

	public void setWeekHighIndex(String weekHighIndex) {
		this.weekHighIndex = weekHighIndex;
	}

	public String getWeeklowIndex() {
		return weeklowIndex;
	}

	public void setWeeklowIndex(String weeklowIndex) {
		this.weeklowIndex = weeklowIndex;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void IndexBeanToPrint() {
		System.out.println("type : " + type + " currentIndex : " + currentIndex + " riseAndfall : " + riseAndfall
				+ " risefallIndex : " + risefallIndex + " openingIndex : " + openingIndex + " highIndex : " + highIndex
				+ " lowIndex : " + lowIndex + " weekHighIndex : " + weekHighIndex + " weeklowIndex : " + weeklowIndex
				+ " time : " + time);
	}
}
