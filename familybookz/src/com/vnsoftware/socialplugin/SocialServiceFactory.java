package com.vnsoftware.socialplugin;


public class SocialServiceFactory {
	public static ZaloService getZaloService(){
		return ZaloServiceImpl.getInstance();
	}
	public static FaceBookPluginService getFaceBookService(){
		return FaceBookPluginServiceImpl.getInstance();
	}
}
