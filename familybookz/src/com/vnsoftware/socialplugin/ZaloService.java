package com.vnsoftware.socialplugin;



public interface ZaloService extends Runnable{
	public void sendLinkMessageByPhonenumber(ZaloLinkMessage link);
	public void sendTextMessageByPhoneNum(ZaloMessage msg);
	public void pushLinkFeed(ZaloLinkFeed feed);
	public void pushImageFeed(ImangeMassage feed);
}
