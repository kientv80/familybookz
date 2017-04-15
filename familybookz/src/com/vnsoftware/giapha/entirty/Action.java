package com.vnsoftware.giapha.entirty;

import java.util.ArrayList;
import java.util.List;

public class Action {
	public enum ACTION_TYPE{LIKE, SHARE,COMMENT}
	private long profileId;
	private long itemId;
	private String comment;
	private ACTION_TYPE act;
	int likeCount;
	private List<Comment> comments = new ArrayList<>();
	public Action(long itemId,int likeCount, String likeIds, int shareCount, String shareIds,int commentCount, String commentIds) {
		this.itemId = itemId;
		this.likeCount = likeCount;
		this.shareCount = shareCount;
		this.commentCount = commentCount;
	}
	/**
	 * @return the likeCount
	 */
	public int getLikeCount() {
		return likeCount;
	}
	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	/**
	 * @return the likeIds
	 */
	public String getLikeIds() {
		return likeIds;
	}
	/**
	 * @param likeIds the likeIds to set
	 */
	public void setLikeIds(String likeIds) {
		this.likeIds = likeIds;
	}
	/**
	 * @return the shareCount
	 */
	public int getShareCount() {
		return shareCount;
	}
	/**
	 * @param shareCount the shareCount to set
	 */
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	/**
	 * @return the shareIds
	 */
	public String getShareIds() {
		return shareIds;
	}
	/**
	 * @param shareIds the shareIds to set
	 */
	public void setShareIds(String shareIds) {
		this.shareIds = shareIds;
	}
	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}
	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	/**
	 * @return the commetnIds
	 */
	public String getCommetnIds() {
		return commetnIds;
	}
	/**
	 * @param commetnIds the commetnIds to set
	 */
	public void setCommetnIds(String commetnIds) {
		this.commetnIds = commetnIds;
	}
	String likeIds;
	int shareCount;
	String shareIds;
	int commentCount;
	String commetnIds;
	
	public Action(ACTION_TYPE act, long profileId, long itemId) {
		this.setProfileId(profileId);
		this.setItemId(itemId);
		this.setAct(act);
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getProfileId() {
		return profileId;
	}
	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}
	public ACTION_TYPE getAct() {
		return act;
	}
	public void setAct(ACTION_TYPE act) {
		this.act = act;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
