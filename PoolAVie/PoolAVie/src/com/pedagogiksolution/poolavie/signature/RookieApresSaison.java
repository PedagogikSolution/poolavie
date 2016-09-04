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
	conn = dbHelper.open();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	String birthdayMax2 = "1991-09-15";

	QueryA = "SELECT * FROM players WHERE club_ecole=? AND birthday>? AND team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, 0);
	    mPreparedStatement.setString(2, birthdayMax2);
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

    public Boolean verifierSiArgent(HttpServletRequest req, int mPlayerId, Signature mBean) {
	int coutPourDescendre = 1000000;
	String QueryB;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);
	int budget_restant = 0;
	int argent_recu = 0;
	int total_argent = 0;

	conn = dbHelper.open();

	QueryB = "SELECT * FROM equipes WHERE team_id=?";

	try {

	    mBean.setMontant(coutPourDescendre);

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

	if ((budget_restant > coutPourDescendre) || (total_argent > coutPourDescendre)) {
	    return true;
	} else {
	    return false;
	}
    }

    public void descendreRookie(String mPlayerIdForRachat, HttpServletRequest req) {
	String QueryA, QueryB, QueryC, QueryD, QueryE, QueryF, QueryG;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	int salaire_matrice = 0, pts = 0, proj = 0, take_proj = 0, salaire_contrat = 0, points, iPosition = 0, salaire_type_a = 0, salaire_type_b = 0, salaire_type_2 = 0, salaire_type_4 = 0, salaire_type_5 = 0;
	int salaire_type_3 = 0, budgetRestant = 0;
	String position = "", years2 = null, years3 = null, years4 = null, years5 = null;

	int players_id = Integer.parseInt(mPlayerIdForRachat);

	conn = dbHelper.open();

	QueryA = "SELECT * FROM players WHERE _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, players_id);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		position = rs.getString("position");
		pts = rs.getInt("pts");
		proj = rs.getInt("projection");
		take_proj = rs.getInt("take_proj");
		salaire_contrat = rs.getInt("salaire_contrat");
		years2 = rs.getString("years_2");
		years3 = rs.getString("years_3");
		years4 = rs.getString("years_4");
		years5 = rs.getString("years_5");
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (take_proj == 1) {
		points = proj;
	    } else {
		points = pts;
	    }

	    switch (position) {
	    case "attaquant":
		iPosition = 1;
		break;
	    case "defenseur":
		iPosition = 2;
		break;
	    case "gardien":
		iPosition = 3;
		break;

	    }

	    QueryB = "SELECT salaire FROM salaires_matrice WHERE position=? AND points=?";

	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, iPosition);
	    mPreparedStatement.setInt(2, points);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		salaire_matrice = rs.getInt("salaire");
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (years2.equals("A")) {
		salaire_type_2 = salaire_matrice;

	    } else if (years2.equals("B")) {
		salaire_type_a = salaire_matrice + 1000000;
		salaire_type_b = salaire_contrat + 1000000;
		if (salaire_type_a >= salaire_type_b) {
		    salaire_type_2 = salaire_type_b;
		} else {
		    salaire_type_2 = salaire_type_a;
		}
	    } else {
		salaire_type_2 = salaire_contrat;
	    }

	    if (years3.equals("A")) {
		salaire_type_3 = salaire_matrice;

	    } else if (years3.equals("B")) {
		salaire_type_a = salaire_matrice + 1000000;
		salaire_type_b = salaire_contrat + 1000000;
		if (salaire_type_a >= salaire_type_b) {
		    salaire_type_3 = salaire_type_b;
		} else {
		    salaire_type_3 = salaire_type_a;
		}
	    } else if (years3.equals("X")) {
		salaire_type_3 = 0;
	    } else {
		salaire_type_3 = salaire_contrat;
	    }

	    if (years4.equals("A")) {
		salaire_type_4 = salaire_matrice;

	    } else if (years4.equals("B")) {
		salaire_type_a = salaire_matrice + 1000000;
		salaire_type_b = salaire_contrat + 1000000;
		if (salaire_type_a >= salaire_type_b) {
		    salaire_type_4 = salaire_type_b;
		} else {
		    salaire_type_4 = salaire_type_a;
		}
	    } else if (years4.equals("X")) {
		salaire_type_4 = 0;
	    } else {
		salaire_type_4 = salaire_contrat;
	    }

	    if (years5.equals("A")) {
		salaire_type_5 = salaire_matrice;

	    } else if (years5.equals("B")) {
		salaire_type_a = salaire_matrice + 1000000;
		salaire_type_b = salaire_contrat + 1000000;
		if (salaire_type_a >= salaire_type_b) {
		    salaire_type_5 = salaire_type_b;
		} else {
		    salaire_type_5 = salaire_type_a;
		}
	    } else if (years5.equals("X")) {
		salaire_type_5 = 0;
	    } else {
		salaire_type_5 = salaire_contrat;
	    }

	    QueryC = "UPDATE players SET club_ecole=?,years_2=?,years_3=?,years_4=?,years_5=? WHERE _id=?";
	    mPreparedStatement = conn.prepareStatement(QueryC);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.setInt(2, salaire_type_2);
	    mPreparedStatement.setInt(3, salaire_type_3);
	    mPreparedStatement.setInt(4, salaire_type_4);
	    mPreparedStatement.setInt(5, salaire_type_5);
	    mPreparedStatement.setInt(6, players_id);
	    mPreparedStatement.executeUpdate();

	    mPreparedStatement.close();

	    if (salaire_type_3 == 0) {
		QueryD = "UPDATE players SET years_3=? WHERE _id=?";
		mPreparedStatement = conn.prepareStatement(QueryD);
		mPreparedStatement.setString(1, "X");
		mPreparedStatement.setInt(2, players_id);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();
	    }

	    if (salaire_type_4 == 0) {
		QueryD = "UPDATE players SET years_4=? WHERE _id=?";
		mPreparedStatement = conn.prepareStatement(QueryD);
		mPreparedStatement.setString(1, "X");
		mPreparedStatement.setInt(2, players_id);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();
	    }

	    if (salaire_type_5 == 0) {
		QueryD = "UPDATE players SET years_5=? WHERE _id=?";
		mPreparedStatement = conn.prepareStatement(QueryD);
		mPreparedStatement.setString(1, "X");
		mPreparedStatement.setInt(2, players_id);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();
	    }

	    String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	    int iTeamId = Integer.parseInt(team_id_temp);

	    QueryE = "UPDATE equipes SET budget_restant=budget_restant-? WHERE team_id=?";
	    mPreparedStatement = conn.prepareStatement(QueryE);
	    mPreparedStatement.setInt(1, 1000000);
	    mPreparedStatement.setInt(2, iTeamId);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();

	    QueryF = "SELECT budget_restant FROM equipes WHERE team_id=?";
	    QueryG = "UPDATE equipes SET argent_recu=argent_recu+?,budget_restant=0 WHERE team_id=?";

	    mPreparedStatement = conn.prepareStatement(QueryF);
	    mPreparedStatement.setInt(1, iTeamId);
	    rs = mPreparedStatement.executeQuery();
	    if (rs.next()) {
		budgetRestant = rs.getInt("budget_restant");
	    }
	    rs.close();
	    mPreparedStatement.close();

	    if (budgetRestant < 0) {

		mPreparedStatement = conn.prepareStatement(QueryG);
		mPreparedStatement.setInt(1, budgetRestant);
		mPreparedStatement.setInt(2, iTeamId);
		mPreparedStatement.executeUpdate();
		mPreparedStatement.close();
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    public void dropperRookie(int mPlayerId, HttpServletRequest req) {
	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);

	String QueryA, QueryB;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;

	conn = dbHelper.open();

	QueryA = "UPDATE equipes SET nb_rookie=nb_rookie-1,manquant_recrue=manquant_recrue+1 WHERE team_id=?";
	QueryB = "UPDATE players SET equipe=null,contrat=0,salaire_contrat=null,contrat_cours=null,contrat_max_years=null,"
		+ "type_contrat=null,club_ecole=null,years_1=null,years_2=null,years_3=null,years_4=null,years_5=null," + "team_id=null,projection=null WHERE _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, iTeamId);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();

	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, mPlayerId);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    public void preparationRookieApresSaison2(HttpServletRequest req) {

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
	List<String> age2 = new ArrayList<String>();
	conn = dbHelper.open();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	QueryA = "SELECT * FROM players WHERE club_ecole=? AND team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.setInt(2, m_team_id);
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

		String age = (rs.getString("age"));
		age2.add(age);
		mBean.setAge(age2);

	    }
	    rs.close();
	    req.setAttribute("players_list", mBean);

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

    public Boolean monterRookieVerifArgentContrat(int mPlayerId, int nbAnnee, String salaire, HttpServletRequest req, Signature mBean) {
	String QueryA, QueryB;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	int budget_restant = 0, manquant_equipe = 0, nombre_contrat = 0;
	int mSalaire = 0;
	String playerName = null;

	if (salaire != null) {
	    mSalaire = Integer.parseInt(salaire);
	}

	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);

	conn = dbHelper.open();

	QueryA = "SELECT * FROM equipes WHERE team_id=?";
	QueryB = "SELECT nom FROM players WHERE _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, iTeamId);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		budget_restant = rs.getInt("budget_restant");
		manquant_equipe = rs.getInt("manquant_equipe");
		nombre_contrat = rs.getInt("nb_contrat");
	    }
	    rs.close();
	    mPreparedStatement.close();

	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, mPlayerId);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {
		playerName = rs.getString("nom");
	    }
	    rs.close();
	    mPreparedStatement.close();

	    mBean.setNomDuJoueur(playerName);
	    mBean.setSalaire(mSalaire);

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	if (nombre_contrat + 1 > 12) {
	    return false;
	}

	int moyenne = (budget_restant - mSalaire) / (manquant_equipe - 1);

	if ((moyenne >= 1000000) && (nombre_contrat < 12)) {

	    return true;
	} else {
	    return false;
	}

    }

    public void monterRookie(String sPlayerId, String nb_annee, String salaire, HttpServletRequest req) {
	int playerId = 0, nbAnnee = 0, salaireFirstYear = 0;

	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);

	if (sPlayerId != null) {
	    playerId = Integer.parseInt(sPlayerId);
	}
	if (nb_annee != null) {
	    nbAnnee = Integer.parseInt(nb_annee);
	}
	if (salaire != null) {
	    salaireFirstYear = Integer.parseInt(salaire);
	}

	String QueryA, QueryB = null, QueryC=null;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	String position = null;
	String years_1 = null, years_2 = null, years_3= null, years_4= null;

	conn = dbHelper.open();

	QueryA = "SELECT * FROM players WHERE _id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, playerId);
	    rs = mPreparedStatement.executeQuery();

	    if (rs.next()) {

		position = rs.getString("position");
		years_1 = rs.getString("years_1");
		years_2 = rs.getString("years_2");
		years_3 = rs.getString("years_3");
		years_4 = rs.getString("years_4");

	    }
	    rs.close();
	    mPreparedStatement.close();

	    switch (position) {
	    case "attaquant":
		QueryB = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,"
			+ "nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,"
			+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_attaquant=nb_attaquant+1,manquant_att=manquant_att-1,"
			+ "nb_rookie=nb_rookie-1,manquant_recrue=manquant_recrue+1 WHERE team_id=?";
		break;
	    case "defenseur":
		QueryB = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,"
			+ "nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,"
			+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_defenseur=nb_defenseur+1,manquant_def=manquant_def-1,"
			+ "nb_rookie=nb_rookie-1,manquant_recrue=manquant_recrue+1 WHERE team_id=?";
		break;
	    case "gardien":
		QueryB = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,"
			+ "nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,"
			+ "moy_sal_restant_draft=budget_restant/manquant_equipe,nb_gardien=nb_gardien+1,manquant_gardien=manquant_gardien-1,"
			+ "nb_rookie=nb_rookie-1,manquant_recrue=manquant_recrue+1 WHERE team_id=?";
		break;
	    }

	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, salaireFirstYear);
	    mPreparedStatement.setInt(2, salaireFirstYear);
	    mPreparedStatement.setInt(3, iTeamId);
	    mPreparedStatement.executeUpdate();

	    mPreparedStatement.close();

	    switch (nbAnnee) {

	    case 1:
		QueryC = "UPDATE players SET club_ecole=0,contrat=1,years_1=?,years_2='X',years_3='X',years_4='X',years_5='X' WHERE _id=?";
		mPreparedStatement = conn.prepareStatement(QueryC);
		mPreparedStatement.setString(1, years_1);
		mPreparedStatement.setInt(2, playerId);
		mPreparedStatement.executeUpdate();

		mPreparedStatement.close();

		break;

	    case 2:
		QueryC = "UPDATE players SET club_ecole=0,contrat=1,years_1=?,years_2=?,years_3='X',years_4='X',years_5='X' WHERE _id=?";
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryC);
		mPreparedStatement.setString(1, years_1);
		mPreparedStatement.setString(2, years_2);
		mPreparedStatement.setInt(3, playerId);
		mPreparedStatement.executeUpdate();

		mPreparedStatement.close();
		break;

	    case 3:
		QueryC = "UPDATE players SET club_ecole=0,contrat=1,years_1=?,years_2=?,years_3=?,years_4='X',years_5='X' WHERE _id=?";
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryC);
		mPreparedStatement.setString(1, years_1);
		mPreparedStatement.setString(2, years_2);
		mPreparedStatement.setString(3, years_3);
		mPreparedStatement.setInt(4, playerId);
		mPreparedStatement.executeUpdate();

		mPreparedStatement.close();
		break;

	    case 4:
		QueryC = "UPDATE players SET club_ecole=0,contrat=1,years_1=?,years_2=?,years_3=?,years_4=?,years_5='X' WHERE _id=?";
		mPreparedStatement.close();
		mPreparedStatement = conn.prepareStatement(QueryC);
		mPreparedStatement.setString(1, years_1);
		mPreparedStatement.setString(2, years_2);
		mPreparedStatement.setString(3, years_3);
		mPreparedStatement.setString(4, years_4);
		mPreparedStatement.setInt(5, playerId);
		mPreparedStatement.executeUpdate();

		mPreparedStatement.close();
		break;

	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

    }

}
