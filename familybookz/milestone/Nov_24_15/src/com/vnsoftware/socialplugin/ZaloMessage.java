package com.vnsoftware.socialplugin;

public class ZaloMessage {
	private long phone;
	private String message;
	public ZaloMessage(long phone, String message) {
		this.setPhone(phone);
		this.setMessage(message);
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
