package com.vnsoftware.giapha.entirty;

import java.sql.Date;

public class RelationRequest {
	public static final String STATUS_REQUESTING = "requesting";
	public static final String STATUS_CONFIRMED = "confirmed";
	public static final String STATUS_CANCEL = "cancel";
	public static final String STATUS_DELETED = "deleted";
	public RelationRequest(long id, long requestingId, long requestedId, String requestMsg, String relation, String status, String requesterName, String requesterImage) {
		super();
		this.id = id;
		this.requestingId = requestingId;
		this.requestedId = requestedId;
		this.requestMsg = requestMsg;
		this.relation = relation;
		this.status = status;
		this.requesterName = requesterName;
		this.requesterImage = requesterImage;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the requestingId
	 */
	public long getRequestingId() {
		return requestingId;
	}
	/**
	 * @param requestingId the requestingId to set
	 */
	public void setRequestingId(long requestingId) {
		this.requestingId = requestingId;
	}
	/**
	 * @return the requestedId
	 */
	public long getRequestedId() {
		return requestedId;
	}
	/**
	 * @param requestedId the requestedId to set
	 */
	public void setRequestedId(long requestedId) {
		this.requestedId = requestedId;
	}
	/**
	 * @return the requestMsg
	 */
	public String getRequestMsg() {
		return requestMsg;
	}
	/**
	 * @param requestMsg the requestMsg to set
	 */
	public void setRequestMsg(String requestMsg) {
		this.requestMsg = requestMsg;
	}
	/**
	 * @return the relation
	 */
	public String getRelation() {
		return relation;
	}
	/**
	 * @param relation the relation to set
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the requestedDate
	 */
	public Date getRequestedDate() {
		return requestedDate;
	}
	/**
	 * @param requestedDate the requestedDate to set
	 */
	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	long id;
	long requestingId;
	long requestedId;
	String requestMsg;
	String relation;
	String status;
	Date requestedDate;
	String requesterName;
	/**
	 * @return the requesterName
	 */
	public String getRequesterName() {
		return requesterName;
	}
	/**
	 * @param requesterName the requesterName to set
	 */
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	/**
	 * @return the requesterImage
	 */
	public String getRequesterImage() {
		return requesterImage;
	}
	/**
	 * @param requesterImage the requesterImage to set
	 */
	public void setRequesterImage(String requesterImage) {
		this.requesterImage = requesterImage;
	}
	String requesterImage;
}
