package com.pedagogiksolution.beans;

import java.io.Serializable;

public class CurrentTeam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8911928843717503539L;
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
