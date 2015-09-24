package com.pedagogiksolution.poolavie.trade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.poolavie.beans.TeamMakingOffer;
import com.pedagogiksolution.poolavie.beans.TeamReceivingOffer;
import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class TradeModel {

    public boolean isThereAOfferForMe(HttpServletRequest req) {
	return false;
	
	
    }

    public Boolean isThereAOfferMadeByMe() {
	
	
	return false;
    }

    public void getTheOfferThatIMade(HttpServletRequest req) {
	
	
    }

    public void getTheTeamsThatTrade(int team_to_trade2, int team_that_trade, HttpServletRequest req) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	TeamMakingOffer mBeanMaking = new TeamMakingOffer();
	TeamReceivingOffer mBeanReceiving = new TeamReceivingOffer();
	List<Integer> player_id = new ArrayList<Integer>();
	List<String> nom2 = new ArrayList<String>();
	List<String> team_name2 = new ArrayList<String>();
	List<String> position2 = new ArrayList<String>();
	List<String> years_12 = new ArrayList<String>();
	List<String> years_22 = new ArrayList<String>();
	List<String> years_32 = new ArrayList<String>();
	List<String> years_42 = new ArrayList<String>();
	List<String> years_52 = new ArrayList<String>();
	List<Integer> club_ecole2 = new ArrayList<Integer>();
	List<Integer> points2 = new ArrayList<Integer>();
	
	List<Integer> player_id3 = new ArrayList<Integer>();
	List<String> nom3 = new ArrayList<String>();
	List<String> team_name3 = new ArrayList<String>();
	List<String> position3 = new ArrayList<String>();
	List<String> years_13 = new ArrayList<String>();
	List<String> years_23 = new ArrayList<String>();
	List<String> years_33 = new ArrayList<String>();
	List<String> years_43 = new ArrayList<String>();
	List<String> years_53 = new ArrayList<String>();
	List<Integer> club_ecole3 = new ArrayList<Integer>();
	List<Integer> points3 = new ArrayList<Integer>();

	conn = dbHelper.open();


	QueryA = "SELECT * FROM players WHERE team_id=? AND years_1>?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, team_that_trade);
	    mPreparedStatement.setInt(2, 1);
	    rs = mPreparedStatement.executeQuery();

	    while (rs.next()) {
		int p_id = (rs.getInt("_id"));
		player_id.add(p_id);
		mBeanMaking.setPlayer_id(player_id);

		String nom = (rs.getString("nom"));
		nom2.add(nom);
		mBeanMaking.setNom(nom2);

		String team_name = (rs.getString("team"));
		team_name2.add(team_name);
		mBeanMaking.setTeam_name(team_name2);

		String position = (rs.getString("position"));
		position2.add(position);
		mBeanMaking.setPosition(position2);

		String years_1 = (rs.getString("years_1"));
		years_12.add(years_1);
		mBeanMaking.setYears_1(years_12);

		String years_2 = (rs.getString("years_2"));
		years_22.add(years_2);
		mBeanMaking.setYears_2(years_22);

		String years_3 = (rs.getString("years_3"));
		years_32.add(years_3);
		mBeanMaking.setYears_3(years_32);

		String years_4 = (rs.getString("years_4"));
		years_42.add(years_4);
		mBeanMaking.setYears_4(years_42);

		String years_5 = (rs.getString("years_5"));
		years_52.add(years_5);
		mBeanMaking.setYears_5(years_52);
		
		int club_ecole = (rs.getInt("club_ecole"));
		club_ecole2.add(club_ecole);
		mBeanMaking.setClub_ecole(club_ecole2);
		
		int points = (rs.getInt("pts"));
		points2.add(points);
		mBeanMaking.setPoints(points2);

	    }
	    rs.close();
	    mPreparedStatement.close();	    
	    req.setAttribute("teamMakingOffer", mBeanMaking);
	    
	   
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, team_to_trade2);
	    mPreparedStatement.setInt(2, 1);
	    rs = mPreparedStatement.executeQuery();

	    while (rs.next()) {
		int p_id = (rs.getInt("_id"));
		player_id3.add(p_id);
		mBeanReceiving.setPlayer_id(player_id3);

		String nom = (rs.getString("nom"));
		nom3.add(nom);
		mBeanReceiving.setNom(nom3);

		String team_name = (rs.getString("team"));
		team_name3.add(team_name);
		mBeanReceiving.setTeam_name(team_name3);

		String position = (rs.getString("position"));
		position3.add(position);
		mBeanReceiving.setPosition(position3);

		String years_1 = (rs.getString("years_1"));
		years_13.add(years_1);
		mBeanReceiving.setYears_1(years_13);

		String years_2 = (rs.getString("years_2"));
		years_23.add(years_2);
		mBeanReceiving.setYears_2(years_23);

		String years_3 = (rs.getString("years_3"));
		years_33.add(years_3);
		mBeanReceiving.setYears_3(years_33);

		String years_4 = (rs.getString("years_4"));
		years_43.add(years_4);
		mBeanReceiving.setYears_4(years_43);

		String years_5 = (rs.getString("years_5"));
		years_53.add(years_5);
		mBeanReceiving.setYears_5(years_53);
		
		int club_ecole = (rs.getInt("club_ecole"));
		club_ecole3.add(club_ecole);
		mBeanReceiving.setClub_ecole(club_ecole3);
		
		int points = (rs.getInt("pts"));
		points3.add(points);
		mBeanReceiving.setPoints(points3);

	    }
	    rs.close();
	    mPreparedStatement.close();
	    req.setAttribute("teamReceivingOffer", mBeanReceiving);
	    
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}
	
    }
    
    
    

}
