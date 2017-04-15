package com.vnsoftware.giapha.entirty;

import java.util.List;

public class Feed implements Comparable<Object> {
	public static enum TYPE {
		STATUS, LINK
	};

	private Action act;
	long ownerId;
	private long feedId;
	String content;

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 *            the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	String desc;
	String website;
	private List<String> images;
	private ShortProfile ownerInfo;
	private String postedDate = "";

	public Feed() {
		// TODO Auto-generated constructor stub
	}

	public Feed(long ownerId, String content) {
		super();
		this.ownerId = ownerId;
		this.content = content;
	}

	/**
	 * @return the ownerId
	 */
	public long getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId
	 *            the ownerId to set
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public ShortProfile getOwnerInfo() {
		return ownerInfo;
	}

	public void setOwnerInfo(ShortProfile ownerInfo) {
		this.ownerInfo = ownerInfo;
	}

	public int getType() {
		// TODO Auto-generated method stub
		return type;
	}

	private String title;

	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	private String url;

	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

	long id;

	public void setId(long id) {
		this.id = id;

	}

	public long getId() {
		return this.id;
	}

	int type;

	public void setType(int type) {
		this.type = type;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(String postedDate) {
		this.postedDate = postedDate;
	}

	public Action getAct() {
		return act;
	}

	public void setAct(Action act) {
		this.act = act;
	}

	@Override
	public int compareTo(Object o) {
		Feed f = (Feed) o;
		if (this.getId() > f.getId())
			return -1;
		else
			return 1;
	}
}
