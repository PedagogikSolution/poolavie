package com.pedagogiksolution.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiTeams implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4968073248363549410L;

	
	private int id;
	private String abbreviation;
	private List<Integer> allId;
	private List<String> allAbbreviation;
	
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
	public List<Integer> getAllId() {
		return allId;
	}
	public void setAllId(List<Integer> allId) {
		this.allId = allId;
	}
	public List<String> getAllAbbreviation() {
		return allAbbreviation;
	}
	public void setAllAbbreviation(List<String> allAbbreviation) {
		this.allAbbreviation = allAbbreviation;
	}
	
	
	
	
}
