package com.pedagogiksolution.beans;

import java.io.Serializable;
import java.util.Date;

public class PlayersFeed implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1648417646462777863L;
	
	
	private int id;
	private String firstName;
	private String lastName;
	private String primaryPosition;
	private String currentTeam;
	private String currentRosterStatus;
	private String currentInjury;
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
	public String getCurrentTeam() {
		return currentTeam;
	}
	public void setCurrentTeam(String currentTeam) {
		this.currentTeam = currentTeam;
	}
	public String getCurrentRosterStatus() {
		return currentRosterStatus;
	}
	public void setCurrentRosterStatus(String currentRosterStatus) {
		this.currentRosterStatus = currentRosterStatus;
	}
	public String getCurrentInjury() {
		return currentInjury;
	}
	public void setCurrentInjury(String currentInjury) {
		this.currentInjury = currentInjury;
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
	
	
	

}
