package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class PersitenceTradeOffer {

	DatabaseConnector dbHelper;
	Connection conn;
	String statement1, statement2, statement3, statement4;
	ResultSet rs1, rs2, rs3, rs4;

	public void persistOffer(String team1, String team2, String t1j1, String t1j2,
			String t1j3, String t1j4, String t1j5, String t1j6, String t1j7,
			String t2j1, String t2j2, String t2j3, String t2j4, String t2j5,
			String t2j6, String t2j7, String t1p1, String t1p2, String t1p3,
			String t2p1, String t2p2, String t2p3, String cash_by,
			String cash_for) {

		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		
		
		
		long now = System.currentTimeMillis();

		
		String annee="2015";
		String periode="2";
		
		
		statement1 = "INSERT INTO trade_offer (team_1, team_2, t1j1, t1j2, t1j3, t1j4, t1j5,"
				+ " t1j6, t1j7, t2j1, t2j2, t2j3, t2j4, t2j5, t2j6, t2j7, t1p1, t1p2, t1p3, t2p1, t2p2, t2p3,t1_cash, t2_cash,periode_echange,annee)"
				+ " VALUES (" + team1 + ", " + team2+ ", " + t1j1+ ", " + t1j2+ ", " + t1j3+ ", " + t1j4+ ", " + t1j5+ ", " + t1j6+ ", " + t1j7+ ","
				+ " "+ t2j1 +", "+t2j2+", "+t2j3+", "+t2j4+","+t2j5+", "+t2j6+", "+t2j7+", "+t1p1+","
				+ " "+t1p2+", "+t1p3+", "+t2p1+", "+t2p2+", "+t2p3+","+cash_by+", "+cash_for+", "+periode+", "+annee+")";

		try {
			conn.prepareStatement(statement1).executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	

}
