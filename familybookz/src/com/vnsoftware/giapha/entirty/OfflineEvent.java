package com.vnsoftware.giapha.entirty;

public class OfflineEvent {
	/**
	 * @return the relationRequestPending
	 */
	public int getRelationRequestPending() {
		return relationRequestPending;
	}
	/**
	 * @param relationRequestPending the relationRequestPending to set
	 */
	public void setRelationRequestPending(int relationRequestPending) {
		this.relationRequestPending = relationRequestPending;
	}
	/**
	 * @return the newFeeds
	 */
	public int getNewFeeds() {
		return newFeeds;
	}
	/**
	 * @param newFeeds the newFeeds to set
	 */
	public void setNewFeeds(int newFeeds) {
		this.newFeeds = newFeeds;
	}
	int relationRequestPending;
	int newFeeds;
}
