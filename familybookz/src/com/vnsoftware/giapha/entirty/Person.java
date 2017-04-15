package com.vnsoftware.giapha.entirty;

import java.util.ArrayList;
import java.util.List;


public class Person {
	public enum GENDER {MALE,FEMALE,OTHER};
	private String userName;
	private String passWorld;
	long id;
	private long ownerId;
	String name;
	private int relationType;
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	String firstName;
	String surname;
	String shortDetail;
	String bornedDate;
	String passedDate;
	String idCard;
	GENDER gender;
	String image;
	private String cover;
	String faceBookId;
	private String accessToken;
	String email;
	String phoneNum;
	long mom;
	long dad;
//	private List<Long> wifesOrHusbands;
	String wifeorhusbandIds = "";
	/**
	 * @return the wifeorhusbandIds
	 */
	public String getWifeorhusbandIds() {
		return wifeorhusbandIds;
	}
	/**
	 * @param wifeorhusbandIds the wifeorhusbandIds to set
	 */
	public void setWifeorhusbandIds(String wifeorhusbandIds) {
		this.wifeorhusbandIds = wifeorhusbandIds;
	}
	/**
	 * @return the childIds
	 */
	public String getChildIds() {
		return childIds;
	}
	/**
	 * @param childIds the childIds to set
	 */
	public void setChildIds(String childIds) {
		this.childIds = childIds;
	}
	String childIds = "";
	List<Person> children;
	
	List<Feed> feeds;//list feeds of parent, children etc.
	List<Feed> unreadFeeds;//contain only unread feed.
	
	/**
	 * @return the feeds
	 */
	public List<Feed> getFeeds() {
		if(feeds == null)
			feeds = new ArrayList<>();
			
		return feeds;
	}
	/**
	 * @param feeds the feeds to set
	 */
	public void setFeeds(List<Feed> feeds) {
		this.feeds = feeds;
	}
	/**
	 * @return the unreadFeeds
	 */
	public List<Feed> getUnreadFeeds() {
		if(unreadFeeds == null)
			unreadFeeds = new ArrayList<>();
			
		return unreadFeeds;
	}
	/**
	 * @param unreadFeeds the unreadFeeds to set
	 */
	public void setUnreadFeeds(List<Feed> unreadFeeds) {
		this.unreadFeeds = unreadFeeds;
	}


	
	public Person() {
		// TODO Auto-generated constructor stub
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
	 * @return the name
	 */
	public String getName() {
		return firstName + " " + surname;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the shortDetail
	 */
	public String getShortDetail() {
		return shortDetail;
	}
	/**
	 * @param shortDetail the shortDetail to set
	 */
	public void setShortDetail(String shortDetail) {
		this.shortDetail = shortDetail;
	}
	/**
	 * @return the bornedDate
	 */
	public String getBornedDate() {
		return bornedDate;
	}
	/**
	 * @param bornedDate the bornedDate to set
	 */
	public void setBornedDate(String bornedDate) {
		this.bornedDate = bornedDate;
	}
	/**
	 * @return the passedDate
	 */
	public String getPassedDate() {
		return passedDate;
	}
	/**
	 * @param passedDate the passedDate to set
	 */
	public void setPassedDate(String passedDate) {
		this.passedDate = passedDate;
	}
	/**
	 * @return the idCard
	 */
	public String getIdCard() {
		return idCard;
	}
	/**
	 * @param idCard the idCard to set
	 */
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * @return the gender
	 */
	public GENDER getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(GENDER gender) {
		this.gender = gender;
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
	/**
	 * @return the faceBook
	 */
	public String getFaceBookId() {
		return faceBookId;
	}
	/**
	 * @param faceBookId the faceBook to set
	 */
	public void setFaceBookId(String faceBookId) {
		this.faceBookId = faceBookId;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}
	/**
	 * @param phoneNum the phoneNum to set
	 */
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	/**
	 * @return the mom
	 */
	
	
	/**
	 * @return the children
	 */
	public List<Person> getChildren() {
		if(children == null)
			children = new ArrayList<Person>();
		return children;
	}
	/**
	 * @param children the children to set
	 */
	public void setChildren(List<Person> children) {
		this.children = children;
	}
	/**
	 * @return the mom
	 */
	public long getMom() {
		return mom;
	}
	/**
	 * @param mom the mom to set
	 */
	public void setMom(long mom) {
		this.mom = mom;
	}
	/**
	 * @return the dad
	 */
	public long getDad() {
		return dad;
	}
	/**
	 * @param dad the dad to set
	 */
	public void setDad(long dad) {
		this.dad = dad;
	}
	
	public Person(long id, String firstName, String surname,  String bornedDate, GENDER gender) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.bornedDate = bornedDate;
		this.gender = gender;
	}
	public Person(long id, String firstName, String surname, String bornedDate, GENDER gender,String image) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.surname = surname;
		this.bornedDate = bornedDate;
		this.gender = gender;
		this.image =  image;
	}
//	public void addWifeOrHusband(Long id){
//		if(wifesOrHusbands == null)
//			wifesOrHusbands = new ArrayList<Long>();
//		if(!wifesOrHusbands.contains(id))
//			wifesOrHusbands.add(id);
//	}
	public void addChild(Person child){
		if(children == null)
			children = new ArrayList<Person>();
//		if(!children.contains(child))
			children.add(child);
	}
	
//	public String getWifedInfo(){
//		StringBuilder w  = new StringBuilder();
//		if(wifes != null && wifes.size() > 0){
//			for(Long wif : wifes){
//				w.append("<img src='"+wif.image+"' class='image'>").append("<b>Wife:</b>").append(wif.name).append("<br>");
//			}
//		}
//		return w.toString();
//	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
//	public List<Long> getWifesOrHusbands() {
//		return wifesOrHusbands;
//	}
//	public void setWifesOrHusbands(List<Long> wifesOrHusbands) {
//		this.wifesOrHusbands = wifesOrHusbands;
//	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	public String getPassWorld() {
		return passWorld;
	}
	public void setPassWorld(String passWorld) {
		this.passWorld = passWorld;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public int getRelationType() {
		return relationType;
	}
	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}
	
}
