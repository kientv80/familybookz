package com.vnsoftware.socialplugin;

import facebook4j.Facebook;

public interface FaceBookPluginService extends Runnable{
	public void postFeed(FaceBookFeed feed);
	public void setFacebook(Facebook facebook);
	public Facebook getFacebook();
}
