package com.pedagogiksolution.beans;

import java.io.Serializable;

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
	private String assists;
	private String goals;
	private String games;
	private String points;
	private String wins;
	private String shutouts;
	private String ot;
	
	
	public String getAssists() {
		return assists;
	}
	public void setAssists(String assists) {
		this.assists = assists;
	}
	public String getGoals() {
		return goals;
	}
	public void setGoals(String goals) {
		this.goals = goals;
	}
	public String getGames() {
		return games;
	}
	public void setGames(String games) {
		this.games = games;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getWins() {
		return wins;
	}
	public void setWins(String wins) {
		this.wins = wins;
	}
	
	public String getOt() {
		return ot;
	}
	public void setOt(String ot) {
		this.ot = ot;
	}
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
	public String getShutouts() {
		return shutouts;
	}
	public void setShutouts(String shutouts) {
		this.shutouts = shutouts;
	}
	
	
	
	
	

}
