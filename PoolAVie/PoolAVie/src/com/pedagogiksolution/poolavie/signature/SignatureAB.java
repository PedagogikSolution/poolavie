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

public class SignatureAB {

/*
 * ************************ Métier pour recuperer les possiblités de rachat lors de la premiere période de rachat a la
 * fin de la saison ***********
 */
    public void preparationSignatureAB(HttpServletRequest req) {
	String QueryA, QueryB;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement, mPreparedStatement2;
	ResultSet rs, rs2;
	Joueurs mBean = new Joueurs();
	List<Integer> player_id = new ArrayList<Integer>();
	List<String> nom2 = new ArrayList<String>();
	List<String> team_name2 = new ArrayList<String>();
	List<String> position2 = new ArrayList<String>();
	List<String> years_12 = new ArrayList<String>();
	List<String> years_22 = new ArrayList<String>();
	List<String> years_32 = new ArrayList<String>();
	List<String> years_42 = new ArrayList<String>();
	List<Integer> salaire2 = new ArrayList<Integer>();
	List<Integer> max_number_years2 = new ArrayList<Integer>();
	int salaire = 0, max_number_years = 0;
	int salaireB = 0, iPosition = 0;

	conn = dbHelper.open();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	int m_team_id = Integer.parseInt(team_id);

	QueryA = "SELECT * FROM players WHERE (years_1='A' OR years_1='B') AND club_ecole=? AND team_id=?";

	QueryB = "SELECT salaire FROM salaires_matrice WHERE position=? AND points=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, 0);
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
		

		int take_proj = (rs.getInt("take_proj"));
		int pts = (rs.getInt("pts"));
		int projection = (rs.getInt("projection"));
		int salaire_contrat = (rs.getInt("salaire_contrat"));
		int contrat_max_years = (rs.getInt("contrat_max_years"));

		if (years_1.equals("A")) {
		    if (take_proj == 0) {
			mPreparedStatement2 = conn.prepareStatement(QueryB);
			mPreparedStatement2.setInt(1, iPosition);
			mPreparedStatement2.setInt(2, pts);
			rs2 = mPreparedStatement2.executeQuery();

			if (rs2.next()) {
			    salaire = (rs2.getInt("salaire"));
			    salaire2.add(salaire);
			    mBean.setSalaire(salaire2);

			}
			rs2.close();
			mPreparedStatement2.close();

		    } else {

			mPreparedStatement2 = conn.prepareStatement(QueryB);
			mPreparedStatement2.setInt(1, iPosition);
			mPreparedStatement2.setInt(2, projection);
			rs2 = mPreparedStatement2.executeQuery();

			if (rs2.next()) {
			    salaire = (rs2.getInt("salaire"));
			    salaire2.add(salaire);
			    mBean.setSalaire(salaire2);

			}
			rs2.close();
			mPreparedStatement2.close();

		    }

		} else {

		    if (take_proj == 0) {
			mPreparedStatement2 = conn.prepareStatement(QueryB);
			mPreparedStatement2.setInt(1, iPosition);
			mPreparedStatement2.setInt(2, pts);
			rs2 = mPreparedStatement2.executeQuery();

			if (rs2.next()) {
			    salaireB = (rs2.getInt("salaire")) + 1000000;

			}
			rs2.close();
			mPreparedStatement2.close();

		    } else {

			mPreparedStatement2 = conn.prepareStatement(QueryB);
			mPreparedStatement2.setInt(1, iPosition);
			mPreparedStatement2.setInt(2, projection);
			rs2 = mPreparedStatement2.executeQuery();

			if (rs2.next()) {
			    salaireB = (rs2.getInt("salaire")) + 1000000;

			}
			rs2.close();
			mPreparedStatement2.close();

		    }

		    if (salaireB < salaire_contrat+1000000) {
			salaire = salaireB;
			salaire2.add(salaire);
			mBean.setSalaire(salaire2);
		    } else {

			salaire = salaire_contrat+1000000;
			salaire2.add(salaire);
			mBean.setSalaire(salaire2);
		    }

		}

		max_number_years = contrat_max_years - 2015;

		max_number_years2.add(max_number_years);
		mBean.setNumber_years_max(max_number_years2);

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

/* ************************** methode pour premier periode de rachat 5.5 ******************* */
    public Boolean verifierSiArgent(HttpServletRequest req, int mSalaire, Signature mBean) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	int budget_restant = 0, manquant_equipe = 0, nombre_contrat=0;

	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);

	conn = dbHelper.open();

	QueryA = "SELECT * FROM equipes WHERE team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, iTeamId);
	    rs = mPreparedStatement.executeQuery();
	    
	    if(rs.next()){
	    budget_restant = rs.getInt("budget_restant");
	    manquant_equipe = rs.getInt("manquant_equipe");
	    nombre_contrat = rs.getInt("nb_contrat");
	    }
	    rs.close();
	    mPreparedStatement.close();

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	int moyenne = (budget_restant - mSalaire) / (manquant_equipe - 1);

	if ((moyenne >= 1000000) && (nombre_contrat<12) ) {
	    mBean.setMoyenne(moyenne);
	    return true;
	} else {
	    return false;
	}
    }

    @SuppressWarnings("resource")
    public void signerCeJoueur(String sPlayerId, String sSalaire,
	    String sNombreAnnee, int mPosition, int mMoyenne, HttpServletRequest req) {
	String QueryA, QueryB, QueryC, QueryD;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);
	int iPlayerId = Integer.parseInt(sPlayerId);
	int iSalaire = Integer.parseInt(sSalaire);
	int iNombreAnnee = Integer.parseInt(sNombreAnnee);

	conn = dbHelper.open();

	QueryA = "UPDATE players SET years_1=?,years_2=?,years_3=?,years_4=?,years_5='X' WHERE _id=?";
	QueryB = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,moy_sal_restant_draft=?,nb_attaquant=nb_attaquant+1,nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,manquant_att=manquant_att-1 WHERE team_id=?";
	QueryC = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,moy_sal_restant_draft=?,nb_defenseur=nb_defenseur+1,nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,manquant_def=manquant_def-1 WHERE team_id=?";
	QueryD = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,moy_sal_restant_draft=?,nb_gardien=nb_gardien+1,nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,manquant_gardien=manquant_gardien-1 WHERE team_id=?";
	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    switch (iNombreAnnee) {
	    case 1:
		mPreparedStatement.setInt(1, iSalaire);
		mPreparedStatement.setString(2, "X");
		mPreparedStatement.setString(3, "X");
		mPreparedStatement.setString(4, "X");
		break;
	    case 2:
		mPreparedStatement.setInt(1, iSalaire);
		mPreparedStatement.setInt(2, iSalaire);
		mPreparedStatement.setString(3, "X");
		mPreparedStatement.setString(4, "X");
		break;
	    case 3:
		mPreparedStatement.setInt(1, iSalaire);
		mPreparedStatement.setInt(2, iSalaire);
		mPreparedStatement.setInt(3, iSalaire);
		mPreparedStatement.setString(4, "X");
		break;
	    case 4:
		mPreparedStatement.setInt(1, iSalaire);
		mPreparedStatement.setInt(2, iSalaire);
		mPreparedStatement.setInt(3, iSalaire);
		mPreparedStatement.setInt(4, iSalaire);
		break;

	    }

	    mPreparedStatement.setInt(5, iPlayerId);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();

	    switch (mPosition) {
	    case 1:
		mPreparedStatement = conn.prepareStatement(QueryB);
		break;
	    case 2:
		mPreparedStatement = conn.prepareStatement(QueryC);
		break;
	    case 3:
		mPreparedStatement = conn.prepareStatement(QueryD);
		break;
	    }

	    mPreparedStatement.setInt(1, iSalaire);
	    mPreparedStatement.setInt(2, iSalaire);
	    mPreparedStatement.setInt(3, mMoyenne);
	    mPreparedStatement.setInt(4, iTeamId);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement.close();

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	// retire l'argent de la table equipe

    }

}
