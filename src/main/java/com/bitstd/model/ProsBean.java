package com.bitstd.model;

public class ProsBean {
	String init_url;
	String url_prex;
	String links_reg;
	String thumbnai_reg;
	String title_reg;
	String content_reg;
	String picture_reg;
	String author;
	String type;
	long sleepTime;
	String isEn; // 0 en 1 ch

	public void ProsBeanToString() {
		System.out.println(" init_url : " + init_url + "\n url_prex : " + url_prex + "\n links_reg : " + links_reg
				+ "\n thumbnai_reg : " + thumbnai_reg + "\n title_reg : " + title_reg + "\n content_reg : "
				+ content_reg + "\n picture_reg : " + picture_reg + "\n author : " + author + "\n type : " + type
				+ "\n sleepTime : " + sleepTime + "\n isEn : " + isEn + "\n");
	}

	public String getThumbnai_reg() {
		return thumbnai_reg;
	}

	public void setThumbnai_reg(String thumbnai_reg) {
		this.thumbnai_reg = thumbnai_reg;
	}

	public String getIsEn() {
		return isEn;
	}

	public void setIsEn(String isEn) {
		this.isEn = isEn;
	}

	public long getSleepTime() {
		return sleepTime;
	}

	public void setSleepTime(long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInit_url() {
		return init_url;
	}

	public void setInit_url(String init_url) {
		this.init_url = init_url;
	}

	public String getUrl_prex() {
		return url_prex;
	}

	public void setUrl_prex(String url_prex) {
		this.url_prex = url_prex;
	}

	public String getLinks_reg() {
		return links_reg;
	}

	public void setLinks_reg(String links_reg) {
		this.links_reg = links_reg;
	}

	public String getTitle_reg() {
		return title_reg;
	}

	public void setTitle_reg(String title_reg) {
		this.title_reg = title_reg;
	}

	public String getContent_reg() {
		return content_reg;
	}

	public void setContent_reg(String content_reg) {
		this.content_reg = content_reg;
	}

	public String getPicture_reg() {
		return picture_reg;
	}

	public void setPicture_reg(String picture_reg) {
		this.picture_reg = picture_reg;
	}

}
