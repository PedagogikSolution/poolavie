package com.pedagogiksolution.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiTeams implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4968073248363549410L;

	
	private int id;
	private String abbreviation;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	
	
	
}
