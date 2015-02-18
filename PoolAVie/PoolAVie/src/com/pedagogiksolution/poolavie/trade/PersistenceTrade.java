package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.SQLException;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class PersistenceTrade {
	
	DatabaseConnector dbHelper;
	Connection conn;
	
	public PersistenceTrade(){
		
	}

	public void persitenceTrade(int trade_id) {
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		String statement = "INSERT INTO trade_made SELECT * FROM trade_offer WHERE _id="+ trade_id;
		try {
			conn.prepareStatement(statement).executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
