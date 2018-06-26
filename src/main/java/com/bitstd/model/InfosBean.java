package com.bitstd.model;

public class InfosBean {
	int typeid;
	String type;
	String author;
	String title;
	String picture;
	String content;
	String synopsis;
	String source;
	String isEn;
	
	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getIsEn() {
		return isEn;
	}

	public void setIsEn(String isEn) {
		this.isEn = isEn;
	}

	public void InfosBeanToString() {
		System.out.println(" type : " + type + "\n author : " + author + "\n title : " + title + "\n picture : "
				+ picture + "\n content : " + content + "\n synopsis : " + synopsis + "\n source : " + source + "\n");
	}

	public int getTypeid() {
		return typeid;
	}

	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
