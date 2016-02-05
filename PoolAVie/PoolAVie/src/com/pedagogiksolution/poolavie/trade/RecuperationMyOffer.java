package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class RecuperationMyOffer {
	
	DatabaseConnector dbHelper;
	Connection conn;
	String statement;
	ResultSet rs;
	
public RecuperationMyOffer(){
		
	}

public ResultSet recuperationMyOffer(int team_that_offer) {
	
	statement = "SELECT * FROM trade_offer WHERE team_1=" + team_that_offer;
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

public void cancelMyOffer(int trade_id) {
	statement = "DELETE FROM trade_offer WHERE _id=" + trade_id;
	dbHelper = new DatabaseConnector();
	conn = dbHelper.open();
	
	if(conn != null){
	
		try {
			conn.prepareStatement(statement).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}

public ResultSet recuperationMyOfferPrecis(int trade_id) {
	statement = "SELECT * FROM trade_offer WHERE _id=" + trade_id;
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

public ResultSet recuperationMyTradeShowDetail(int trade_id) {
	statement = "SELECT * FROM trade_made WHERE _id=" + trade_id;
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

public ResultSet recuperationOfferToMe(int team_that_trade) {
	statement = "SELECT * FROM trade_offer WHERE team_2=" + team_that_trade;
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

public ResultSet recuperationAllTrade() {
	statement = "SELECT * FROM trade_made ORDER BY date_heure ASC";
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

public ResultSet recuperationMyTrade(int team_that_trade) {
	statement = "SELECT * FROM trade_made WHERE team_2=" + team_that_trade + " OR team_1=" + team_that_trade;
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
