package com.pedagogiksolution.beans;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

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
	private String rosterStatus;
	private String birthDate;
	private boolean rookie;
	private boolean active;
	private JsonNode primaryPosition;
	
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
	public String getRosterStatus() {
		return rosterStatus;
	}
	public void setRosterStatus(String rosterStatus) {
		this.rosterStatus = rosterStatus;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public JsonNode getPrimaryPosition() {
		return primaryPosition;
	}
	public void setPrimaryPosition(JsonNode primaryPosition) {
		this.primaryPosition = primaryPosition;
	}
	
	
	
	
	

}
