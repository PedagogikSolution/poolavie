package com.pedagogiksolution.poolavie.signature;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.poolavie.beans.Joueurs;
import com.pedagogiksolution.poolavie.beans.Signature;
import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class RachatApresChangementAnnee {

/*
 * ************************ Métier pour recuperer les possiblités de rachat lors de la premiere période de rachat a la
 * fin de la saison ***********
 */
    public void preparationRachatChangementAnnee(HttpServletRequest req) {
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

	conn = dbHelper.open();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	QueryA = "SELECT * FROM players WHERE years_2>? AND club_ecole=? AND team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.setInt(2, 0);
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
    
    public void preparationRachatChangementAnnee2(HttpServletRequest req) {
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

	conn = dbHelper.open();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	QueryA = "SELECT * FROM players WHERE years_1>? AND club_ecole=? AND team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.setInt(2, 0);
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
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

/* ************************** methode pour premier periode de rachat 5.5 ******************* */
    public Boolean verifierSiArgent(HttpServletRequest req, int mPlayerId,
	    Signature mBean) {
	int coutDuRachat = 0;
	String QueryA, QueryB;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	String sYears1 = null, sYears2 = null, sYears3 = null, sYears4 = null, sYears5 = null;
	Boolean bYears1 = false, bYears2 = false, bYears3 = null, bYears4 = null, bYears5 = null;
	int iYears1 = 0, iYears2 = 0, iYears3 = 0, iYears4 = 0, iYears5 = 0;
	String nomDuJoueurRachat = "Bug de récupération du nom";
	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);
	int budget_restant = 0;
	int argent_recu = 0;
	int total_argent = 0;
	String position = null;

	conn = dbHelper.open();

	QueryA = "SELECT * FROM players WHERE _id=?";
	QueryB = "SELECT * FROM equipes WHERE team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, mPlayerId);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		sYears1 = rs.getString("years_1");
		sYears2 = rs.getString("years_2");
		sYears3 = rs.getString("years_3");
		sYears4 = rs.getString("years_4");
		sYears5 = rs.getString("years_5");
		nomDuJoueurRachat = rs.getString("nom");
		position = rs.getString("position");
	    }
	    rs.close();
	    mPreparedStatement.close();

	    /***********   ************/

	    if (sYears1.equals("A") || sYears1.equals("B")
		    || sYears1.equals("X")) {
		bYears1 = false;
	    } else {
		bYears1 = true;
		iYears1 = Integer.parseInt(sYears1);
	    }
	    if (sYears2.equals("A") || sYears2.equals("B")
		    || sYears2.equals("X")) {
		bYears2 = false;
	    } else {
		bYears2 = true;
		iYears2 = Integer.parseInt(sYears2);
	    }
	    if (sYears3.equals("A") || sYears3.equals("B")
		    || sYears3.equals("X")) {
		bYears3 = false;
	    } else {
		bYears3 = true;
		iYears3 = Integer.parseInt(sYears3);
	    }
	    if (sYears4.equals("A") || sYears4.equals("B")
		    || sYears4.equals("X")) {
		bYears4 = false;
	    } else {
		bYears4 = true;
		iYears4 = Integer.parseInt(sYears4);
	    }
	    if (sYears5.equals("A") || sYears5.equals("B")
		    || sYears5.equals("X")) {
		bYears5 = false;
	    } else {
		bYears5 = true;
		iYears5 = Integer.parseInt(sYears5);
	    }

	    /***********   ************/

	    if (bYears1 && bYears2 && !bYears3 && !bYears4 && !bYears5) {
		coutDuRachat = (iYears1 + iYears2) / 2;
	    }
	    if (bYears1 && bYears2 && bYears3 && !bYears4 && !bYears5) {
		coutDuRachat = (iYears1 + iYears2 + iYears3) / 2;
	    }
	    if (bYears1 && bYears2 && bYears3 && bYears4 && !bYears5) {
		coutDuRachat = (iYears1 + iYears2 + iYears3 + iYears4) / 2;
	    }
	    if (bYears1 && bYears2 && bYears3 && bYears4 && bYears5) {
		coutDuRachat = (iYears1 + iYears2 + iYears3 + iYears4 + iYears5) / 2;
	    }
	    int position2 = 0;
	    switch(position){
	    case "attaquant":
		position2 = 1;
		break;
	    case "defenseur":
		position2 = 2;
		break;
	    case "gardien":
		position2 = 3;
		break;
		
	    }

	    mBean.setMontant(coutDuRachat);
	    mBean.setNomDuJoueur(nomDuJoueurRachat);
	    mBean.setPosition(position2);
	    mBean.setSalaire(iYears1);

	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, iTeamId);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		budget_restant = rs.getInt("budget_restant");
		argent_recu = rs.getInt("argent_recu");
	    }
	    rs.close();
	    mPreparedStatement.close();

	    total_argent = budget_restant + argent_recu;

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	if ((budget_restant > coutDuRachat) || (total_argent > coutDuRachat)) {
	    return true;
	} else {
	    return false;
	}
    }

    public void racheterCeJoueur(String mPlayerIdForRachat,
	    String mCoutForRachat, int position, String salaire2, HttpServletRequest req) {
	String QueryA, QueryB = null;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);
	int players_id = Integer.parseInt(mPlayerIdForRachat);
	int coutDuRachat = Integer.parseInt(mCoutForRachat);
	int salaire = Integer.parseInt(salaire2);
	

	conn = dbHelper.open();

	QueryA = "UPDATE players SET equipe=null,contrat=0,salaire_contrat=null,contrat_cours=null,contrat_max_years=null,type_contrat=null,club_ecole=null,years_1=null,years_2=null,years_3=null,years_4=null,years_5=null,team_id=null,projection=null WHERE _id=?";
	

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, players_id);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();
	   	    
	    
	    switch(position){
	    case 1: QueryB = "UPDATE equipes SET max_salaire_begin=max_salaire_begin-?,total_salaire_now=total_salaire_now-?,budget_restant=budget_restant+?-?,manquant_equipe=manquant_equipe+1,nb_equipe=nb_equipe-1,"
			+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat-1,nb_attaquant=nb_attaquant-1,manquant_att=manquant_att+1 WHERE team_id=?";
		break;
	    case 2: QueryB = "UPDATE equipes SET max_salaire_begin=max_salaire_begin-?,total_salaire_now=total_salaire_now-?,budget_restant=budget_restant+?-?,manquant_equipe=manquant_equipe+1,nb_equipe=nb_equipe-1,"
			+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat-1,nb_defenseur=nb_defenseur-1,manquant_def=manquant_def+1 WHERE team_id=?";
		break;
	    case 3: QueryB = "UPDATE equipes SET max_salaire_begin=max_salaire_begin-?,total_salaire_now=total_salaire_now-?,budget_restant=budget_restant+?-?,manquant_equipe=manquant_equipe+1,nb_equipe=nb_equipe-1,"
			+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_contrat=nb_contrat-1,nb_gardien=nb_gardien-1,manquant_gardien=manquant_gardien+1 WHERE team_id=?";
		break;
	    }
	    mPreparedStatement.close();
	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, coutDuRachat);
	    mPreparedStatement.setInt(2, salaire);
	    mPreparedStatement.setInt(3, coutDuRachat);
	    mPreparedStatement.setInt(4, salaire);
	    mPreparedStatement.setInt(5, iTeamId);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();
	   

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	// retire l'argent de la table equipe

    }

}
