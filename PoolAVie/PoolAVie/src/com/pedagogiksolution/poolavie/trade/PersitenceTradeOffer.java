package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.ResultSet;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class PersitenceTradeOffer {
	
	DatabaseConnector dbHelper;
	Connection conn;
	String statement1,statement2,statement3,statement4;
	ResultSet rs1,rs2,rs3,rs4;

	public void persistOffer(int team1, int team2, String[] player_trade_by, int cash_trade_by, String[] pick_trade_by, String[] player_trade_for, int cash_trade_for, String[] pick_trade_for) {
	
	
		
		
	
	dbHelper = new DatabaseConnector();
	conn = dbHelper.open();
	
	if(conn != null){
		
		for (String s:player_trade_by){
			
			
		//	String t1j = "t1j" + i;
			
			
			
			
			
			
		}
		
		
		
	}
	
		
		
	}

}
