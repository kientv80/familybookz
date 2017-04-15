package com.vnsoftware.giapha.entirty;

public class ShortProfile {
	private String domainName;

	public ShortProfile() {
	}
	
	
	public ShortProfile(Long id, String name, String domainName,String image) {
		super();
		this.id = id;
		this.name = name;
		this.image = image;
		this.setDomainName(domainName);
	}
	private long facebookId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
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
	public long getFacebookId() {
		return facebookId;
	}


	public void setFacebookId(long facebookId) {
		this.facebookId = facebookId;
	}
	public String getDomainName() {
		return domainName;
	}


	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	Long id;
	String name;
	String image;
}
