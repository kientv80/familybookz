package com.vnsoftware.socialplugin;

public class ZaloLinkFeed{
	private String message;
	String link;
	String linkTitle;
	String linkDesc;
	String linkThumb;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public String getLinkDesc() {
		return linkDesc;
	}
	public void setLinkDesc(String linkDesc) {
		this.linkDesc = linkDesc;
	}
	public String getLinkThumb() {
		return linkThumb;
	}
	public void setLinkThumb(String linkThumb) {
		this.linkThumb = linkThumb;
	}
	public ZaloLinkFeed(String link, String linkTitle, String linkDesc, String linkThumb) {
		this.link = link;
		this.linkTitle = linkTitle;
		this.linkDesc = linkDesc;
		this.linkThumb = linkThumb;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
