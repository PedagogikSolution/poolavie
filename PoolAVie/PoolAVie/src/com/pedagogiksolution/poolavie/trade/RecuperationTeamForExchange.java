package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class RecuperationTeamForExchange {
	
	DatabaseConnector dbHelper;
	Connection conn;
	String statement;
	ResultSet rs;
	
	
	public RecuperationTeamForExchange(){
		
	}

	public ResultSet recuperationTeamThatTrade(int team_that_trade) {
		
		statement = "SELECT * FROM players WHERE club_ecole=0 AND team_id=" + team_that_trade;
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		
		if(conn != null){
		
			try {
				rs = conn.createStatement().executeQuery(statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		return rs;
	}
	
public ResultSet recuperationRookieThatTrade(int team_that_trade) {
		
		statement = "SELECT * FROM players WHERE club_ecole=1 AND team_id=" + team_that_trade;
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		
		if(conn != null){
		
			try {
				rs = conn.createStatement().executeQuery(statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		return rs;
	}

	public ResultSet recuperationPick(int team_that_trade) {
		
		statement = "SELECT * FROM draft_pick_current_year WHERE team_id=" + team_that_trade;
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		
		if(conn != null){
		
			try {
				rs = conn.createStatement().executeQuery(statement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
			
		return rs;
	}
	
	

}
