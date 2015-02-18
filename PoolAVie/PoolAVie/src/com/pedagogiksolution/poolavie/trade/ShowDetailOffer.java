package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class ShowDetailOffer {
	DatabaseConnector dbHelper;
	Connection conn;
	String statement;
	ResultSet rs;
	String statement1;

	public ShowDetailOffer() {

	}

	public ResultSet recupPlayerTeam1(ResultSet rs) {

		try {
			if (rs.first()) {

				String t1j1 = rs.getString("t1j1");
				String t1j2 = rs.getString("t1j2");
				String t1j3 = rs.getString("t1j3");
				String t1j4 = rs.getString("t1j4");
				String t1j5 = rs.getString("t1j5");
				String t1j6 = rs.getString("t1j6");
				String t1j7 = rs.getString("t1j7");

				if (t1j2 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t1j1;
				} else if (t1j2 != null && t1j3 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t1j1
							+ " OR _id=" + t1j2;
				} else if (t1j3 != null && t1j4 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t1j1
							+ " OR _id=" + t1j2 + " OR _id=" + t1j3;
				} else if (t1j4 != null && t1j5 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t1j1
							+ " OR _id=" + t1j2 + " OR _id=" + t1j3
							+ " OR _id=" + t1j4;
				} else if (t1j5 != null && t1j6 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t1j1
							+ " OR _id=" + t1j2 + " OR _id=" + t1j3
							+ " OR _id=" + t1j4 + " OR _id=" + t1j5;
				} else if (t1j6 != null && t1j7 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t1j1
							+ " OR _id=" + t1j2 + " OR _id=" + t1j3
							+ " OR _id=" + t1j4 + " OR _id=" + t1j5
							+ " OR _id=" + t1j6;
				} else {
					statement1 = "SELECT * FROM players WHERE _id=" + t1j1
							+ " OR _id=" + t1j2 + " OR _id=" + t1j3
							+ " OR _id=" + t1j4 + " OR _id=" + t1j5
							+ " OR _id=" + t1j6 + " OR _id=" + t1j7;
				}

				dbHelper = new DatabaseConnector();
				conn = dbHelper.open();

				if (conn != null) {

					try {
						rs = conn.createStatement().executeQuery(statement1);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet recupPlayerTeam2(ResultSet rs) {

		try {
			if (rs.first()) {

				String t2j1 = rs.getString("t2j1");
				String t2j2 = rs.getString("t2j2");
				String t2j3 = rs.getString("t2j3");
				String t2j4 = rs.getString("t2j4");
				String t2j5 = rs.getString("t2j5");
				String t2j6 = rs.getString("t2j6");
				String t2j7 = rs.getString("t2j7");

				if (t2j2 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t2j1;
				} else if (t2j2 != null && t2j3 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t2j1
							+ " OR _id=" + t2j2;
				} else if (t2j3 != null && t2j4 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t2j1
							+ " OR _id=" + t2j2 + " OR _id=" + t2j3;
				} else if (t2j4 != null && t2j5 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t2j1
							+ " OR _id=" + t2j2 + " OR _id=" + t2j3
							+ " OR _id=" + t2j4;
				} else if (t2j5 != null && t2j6 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t2j1
							+ " OR _id=" + t2j2 + " OR _id=" + t2j3
							+ " OR _id=" + t2j4 + " OR _id=" + t2j5;
				} else if (t2j6 != null && t2j7 == null) {
					statement1 = "SELECT * FROM players WHERE _id=" + t2j1
							+ " OR _id=" + t2j2 + " OR _id=" + t2j3
							+ " OR _id=" + t2j4 + " OR _id=" + t2j5
							+ " OR _id=" + t2j6;
				} else {
					statement1 = "SELECT * FROM players WHERE _id=" + t2j1
							+ " OR _id=" + t2j2 + " OR _id=" + t2j3
							+ " OR _id=" + t2j4 + " OR _id=" + t2j5
							+ " OR _id=" + t2j6 + " OR _id=" + t2j7;
				}

				dbHelper = new DatabaseConnector();
				conn = dbHelper.open();

				if (conn != null) {

					try {
						rs = conn.createStatement().executeQuery(statement1);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet recupPickTeam1(ResultSet rs) {

		try {
			if (rs.first()) {

				String t1p1 = rs.getString("t1p1");
				String t1p2 = rs.getString("t1p2");
				String t1p3 = rs.getString("t1p3");
				
				if(t1p1==null){
					return null;
				}
				if (t1p2 == null) {
					statement1 = "SELECT * FROM draft_pick_current_year WHERE _id=" + t1p1;
				} else if (t1p2 != null && t1p3 == null) {
					statement1 = "SELECT * FROM draft_pick_current_year WHERE _id=" + t1p1
							+ " OR _id=" + t1p2;
				} else {
					statement1 = "SELECT * FROM draft_pick_current_year WHERE _id=" + t1p1
							+ " OR _id=" + t1p2 + " OR _id=" + t1p3;
				} 

				dbHelper = new DatabaseConnector();
				conn = dbHelper.open();

				if (conn != null) {

					try {
						rs = conn.createStatement().executeQuery(statement1);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet recupPickTeam2(ResultSet rs) {

		try {
			if (rs.first()) {

				String t2p1 = rs.getString("t2p1");
				String t2p2 = rs.getString("t2p2");
				String t2p3 = rs.getString("t2p3");
				
				if(t2p1==null){
					return null;
				}
				if (t2p2 == null) {
					statement1 = "SELECT * FROM draft_pick_current_year WHERE _id=" + t2p1;
				} else if (t2p2 != null && t2p3 == null) {
					statement1 = "SELECT * FROM draft_pick_current_year WHERE _id=" + t2p1
							+ " OR _id=" + t2p2;
				} else {
					statement1 = "SELECT * FROM draft_pick_current_year WHERE _id=" + t2p1
							+ " OR _id=" + t2p2 + " OR _id=" + t2p3;
				} 

				dbHelper = new DatabaseConnector();
				conn = dbHelper.open();

				if (conn != null) {

					try {
						rs = conn.createStatement().executeQuery(statement1);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}

}
