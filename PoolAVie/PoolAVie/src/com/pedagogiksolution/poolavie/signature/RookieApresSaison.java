package com.pedagogiksolution.poolavie.signature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.poolavie.beans.Joueurs;
import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class RookieApresSaison {

    public void preparationRookieApresSaison(HttpServletRequest req) {
	
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	Joueurs mBean = new Joueurs();
	List<Integer> player_id = new ArrayList<Integer>();
	List<String> nom2 = new ArrayList<String>();
	List<String> team_name2 = new ArrayList<String>();
	List<String> position2 = new ArrayList<String>();
	List<String> years_12 = new ArrayList<String>();
	List<String> years_22 = new ArrayList<String>();
	List<String> years_32 = new ArrayList<String>();
	List<String> years_42 = new ArrayList<String>();
	List<String> years_52 = new ArrayList<String>();
	Date birthdayMax = null;
	conn = dbHelper.open();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
	String birthdayMax2 = "1990-09-15";
	try {
	    birthdayMax = sdf.parse(birthdayMax2);
	} catch (ParseException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	QueryA = "SELECT * FROM players WHERE club_ecole=? AND birthday>? AND team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, 0);
	    mPreparedStatement.setDate(2, (java.sql.Date) birthdayMax);
	    mPreparedStatement.setInt(3, m_team_id);
	    rs = mPreparedStatement.executeQuery();

	    while (rs.next()) {
		int p_id = (rs.getInt("_id"));
		player_id.add(p_id);
		mBean.setPlayer_id(player_id);

		String nom = (rs.getString("nom"));
		nom2.add(nom);
		mBean.setNom(nom2);

		String team_name = (rs.getString("team"));
		team_name2.add(team_name);
		mBean.setTeam_name(team_name2);

		String position = (rs.getString("position"));
		position2.add(position);
		mBean.setPosition(position2);

		String years_1 = (rs.getString("years_1"));
		years_12.add(years_1);
		mBean.setYears_1(years_12);

		String years_2 = (rs.getString("years_2"));
		years_22.add(years_2);
		mBean.setYears_2(years_22);

		String years_3 = (rs.getString("years_3"));
		years_32.add(years_3);
		mBean.setYears_3(years_32);

		String years_4 = (rs.getString("years_4"));
		years_42.add(years_4);
		mBean.setYears_4(years_42);

		String years_5 = (rs.getString("years_5"));
		years_52.add(years_5);
		mBean.setYears_5(years_52);

	    }
	    rs.close();
	    req.setAttribute("players_list", mBean);

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}
	
    }
    
    

}
