package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "TradeOffer")
public class TradeOffer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4318584651402488036L;

	 @Id
	    private String poolTeamId;
	
}
