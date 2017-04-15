package com.vnsoftware.socialplugin;

public class ZaloLinkMessage {
	long phoneNumber;
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
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
	String link;
	String linkTitle;
	String linkDesc;
	String linkThumb;
	public ZaloLinkMessage(long phoneNumber, String link, String linkTitle, String linkDesc, String linkThumb) {
		this.link = link;
		this.phoneNumber = phoneNumber;
		this.linkTitle = linkTitle;
		this.linkDesc = linkDesc;
		this.linkThumb = linkThumb;
	}
	
}
