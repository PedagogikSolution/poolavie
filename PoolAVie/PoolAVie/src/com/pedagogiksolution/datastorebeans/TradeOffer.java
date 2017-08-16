package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;


public class TradeOffer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4318584651402488036L;

	    private String poolTeamId;

		public String getPoolTeamId() {
			return poolTeamId;
		}

		public void setPoolTeamId(String poolTeamId) {
			this.poolTeamId = poolTeamId;
		}
	
}
