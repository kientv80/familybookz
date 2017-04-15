package com.vnsoftware.socialplugin;

public class ImangeMassage extends ZaloMessage{
	private String imageUrl = "";
	public ImangeMassage(long phone, String message) {
		super(phone, message);
	}
	String getImageUrl() {
		return imageUrl;
	}
	void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
