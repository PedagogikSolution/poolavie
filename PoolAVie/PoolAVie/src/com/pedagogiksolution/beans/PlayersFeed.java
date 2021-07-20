package com.pedagogiksolution.beans;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayersFeed implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1648417646462777863L;
	
	
	private int id;
	private String firstName;
	private String fullName;
	private String lastName;
	private String primaryPosition;
	private String currentRosterStatus;
	private Date birthDate;
	private int age;
	private boolean rookie;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPrimaryPosition() {
		return primaryPosition;
	}
	public void setPrimaryPosition(String primaryPosition) {
		this.primaryPosition = primaryPosition;
	}
	
	public String getCurrentRosterStatus() {
		return currentRosterStatus;
	}
	public void setCurrentRosterStatus(String currentRosterStatus) {
		this.currentRosterStatus = currentRosterStatus;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public boolean isRookie() {
		return rookie;
	}
	public void setRookie(boolean rookie) {
		this.rookie = rookie;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	
	

}
