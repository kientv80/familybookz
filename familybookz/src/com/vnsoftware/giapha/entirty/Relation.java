package com.vnsoftware.giapha.entirty;

public class Relation {
	
	public Relation(long monOrDadId, Person relation) {
		super();
		this.monOrDadId = monOrDadId;
		this.relation = relation;
	}
	String type;
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the monOrDadId
	 */
	public long getMonOrDadId() {
		return monOrDadId;
	}
	/**
	 * @param monOrDadId the monOrDadId to set
	 */
	public void setMonOrDadId(long monOrDadId) {
		this.monOrDadId = monOrDadId;
	}
	/**
	 * @return the relation
	 */
	public Person getRelation() {
		return relation;
	}
	/**
	 * @param relation the relation to set
	 */
	public void setRelation(Person relation) {
		this.relation = relation;
	}
	long monOrDadId;
	Person relation;
}
