package com.vnsoftware.giapha.entirty;

import java.util.Date;

public class Comment {
	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	/**
	 * @return the profileAvatar
	 */
	public String getProfileAvatar() {
		return profileAvatar;
	}

	/**
	 * @param profileAvatar the profileAvatar to set
	 */
	public void setProfileAvatar(String profileAvatar) {
		this.profileAvatar = profileAvatar;
	}
	String profileName;
	String profileAvatar;
	private String commenterDomainName;
	
	public Comment(long commentId, long replyCommentId, long ownerId, long feedId, String comment, String profileName,String commenterDomainName,String profileAvatar) {
		super();
		this.commentId = commentId;
		this.replyCommentId = replyCommentId;
		this.ownerId = ownerId;
		this.feedId = feedId;
		this.comment = comment;
		this.profileAvatar = profileAvatar;
		this.profileName = profileName;
		this.setCommenterDomainName(commenterDomainName);
	}
	
	/**
	 * @return the commentId
	 */
	public long getCommentId() {
		return commentId;
	}
	/**
	 * @param commentId the commentId to set
	 */
	public void setCommentId(long commentId) {
		this.commentId = commentId;
	}
	/**
	 * @return the replyCommentId
	 */
	public long getReplyCommentId() {
		return replyCommentId;
	}
	/**
	 * @param replyCommentId the replyCommentId to set
	 */
	public void setReplyCommentId(long replyCommentId) {
		this.replyCommentId = replyCommentId;
	}
	/**
	 * @return the ownerId
	 */
	public long getOwnerId() {
		return ownerId;
	}
	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	/**
	 * @return the feedId
	 */
	public long getFeedId() {
		return feedId;
	}
	/**
	 * @param feedId the feedId to set
	 */
	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	long commentId;
	long replyCommentId;
	long ownerId;
	long feedId;
	String comment;
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	public String getCommenterDomainName() {
		return commenterDomainName;
	}

	public void setCommenterDomainName(String commenterDomainName) {
		this.commenterDomainName = commenterDomainName;
	}
	String image;
	String title;
	String url;
	String desc;
	int type;
	private Date postedDate;
	public static int TYPE_TEXT = 0;
	public static int TYPE_IMAGE = 2;
	public static int TYPE_LINK = 3;
	public static int TYPE_EMOTICON = 4;
}
