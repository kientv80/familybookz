package com.vnsoftware.giapha.entirty;

import java.util.List;

public class Family {
	
	public Family(Person me, List<Person> wife, List<Person> kids) {
		super();
		this.me = me;
		this.wife = wife;
		this.kids = kids;
	}
	/**
	 * @return the me
	 */
	public Person getMe() {
		return me;
	}
	/**
	 * @param me the me to set
	 */
	public void setMe(Person me) {
		this.me = me;
	}
	/**
	 * @return the wife
	 */
	public List<Person> getWife() {
		return wife;
	}
	/**
	 * @param wife the wife to set
	 */
	public void setWife(List<Person> wife) {
		this.wife = wife;
	}
	/**
	 * @return the kids
	 */
	public List<Person> getKids() {
		return kids;
	}
	/**
	 * @param kids the kids to set
	 */
	public void setKids(List<Person> kids) {
		this.kids = kids;
	}
	Person me;
	List<Person> wife;
	List<Person> kids;
}
