package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.Joueurs;
import com.pedagogiksolution.poolavie.beans.Signature;
import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class SignatureAfterDraft {

    public void preparationSignatureAfterDraft(HttpServletRequest req, HttpServletResponse resp) {
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
	List<Integer> salaire2 = new ArrayList<Integer>();
	
	
	int m_team_id = 0;
	conn = dbHelper.open();

	String team_id = (String) req.getSession().getAttribute("mTeamId");
	if(team_id!=null){
	    m_team_id = Integer.parseInt(team_id);
	} else {
	    try {
		resp.sendRedirect("/jsp/home.jsp");
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	QueryA = "SELECT * FROM players WHERE years_1='JA' AND club_ecole=? AND team_id=?";

	

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
		
		int salaire3 = (rs.getInt("salaire_draft"));
		salaire2.add(salaire3);
		mBean.setSalaire(salaire2);

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

    public Boolean verifierSiPlaceDispo(HttpServletRequest req, int mSalaire, Signature mBean) {
	String QueryA;
	DatabaseConnector dbHelper = new DatabaseConnector();
	Connection conn;
	PreparedStatement mPreparedStatement;
	ResultSet rs;
	int nombre_contrat=0;

	String team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	int iTeamId = Integer.parseInt(team_id_temp);

	conn = dbHelper.open();

	QueryA = "SELECT * FROM equipes WHERE team_id=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, iTeamId);
	    rs = mPreparedStatement.executeQuery();
	    
	    if(rs.next()){
	   
	    nombre_contrat = rs.getInt("nb_contrat");
	    }
	    rs.close();
	    mPreparedStatement.close();

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	

	if (nombre_contrat>11) {
	    return false;
	} else {
	    return true;
	}
       }

    public void signerCeJoueur(String sPlayerId, String sSalaire, String sNombreAnnee, int mPosition, HttpServletRequest req, HttpServletResponse resp) {
		
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

		QueryA = "UPDATE players SET years_1=?,years_2=?,years_3=?,years_4=?,years_5=? WHERE _id=?";
		QueryB = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,nb_attaquant=nb_attaquant+1,nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,manquant_att=manquant_att-1 WHERE team_id=?";
		QueryC = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,nb_defenseur=nb_defenseur+1,nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,manquant_def=manquant_def-1 WHERE team_id=?";
		QueryD = "UPDATE equipes SET total_salaire_now=total_salaire_now+?,budget_restant=budget_restant-?,nb_gardien=nb_gardien+1,nb_contrat=nb_contrat+1,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,manquant_gardien=manquant_gardien-1 WHERE team_id=?";
		try {
		    mPreparedStatement = conn.prepareStatement(QueryA);
		    switch (iNombreAnnee) {
		    case 1:
			mPreparedStatement.setInt(1, iSalaire);
			mPreparedStatement.setString(2, "X");
			mPreparedStatement.setString(3, "X");
			mPreparedStatement.setString(4, "X");
			mPreparedStatement.setString(5, "X");
			break;
		    case 2:
			mPreparedStatement.setInt(1, iSalaire);
			mPreparedStatement.setInt(2, iSalaire);
			mPreparedStatement.setString(3, "X");
			mPreparedStatement.setString(4, "X");
			mPreparedStatement.setString(5, "X");
			break;
		    case 3:
			mPreparedStatement.setInt(1, iSalaire);
			mPreparedStatement.setInt(2, iSalaire);
			mPreparedStatement.setInt(3, iSalaire);
			mPreparedStatement.setString(4, "X");
			mPreparedStatement.setString(5, "X");
			break;
		    case 4:
			mPreparedStatement.setInt(1, iSalaire);
			mPreparedStatement.setInt(2, iSalaire);
			mPreparedStatement.setInt(3, iSalaire);
			mPreparedStatement.setInt(4, iSalaire);
			mPreparedStatement.setString(5, "X");
			break;
		    case 5:
			mPreparedStatement.setInt(1, iSalaire);
			mPreparedStatement.setInt(2, iSalaire);
			mPreparedStatement.setInt(3, iSalaire);
			mPreparedStatement.setInt(4, iSalaire);
			mPreparedStatement.setInt(5, iSalaire);
			break;

		    }

		    mPreparedStatement.setInt(6, iPlayerId);
		    mPreparedStatement.executeUpdate();
		    mPreparedStatement.close();

		    switch (mPosition) {
		    case 1:
			 mPreparedStatement.close();
			mPreparedStatement = conn.prepareStatement(QueryB);
			break;
		    case 2:
			 mPreparedStatement.close();
			mPreparedStatement = conn.prepareStatement(QueryC);
			break;
		    case 3:
			 mPreparedStatement.close();
			mPreparedStatement = conn.prepareStatement(QueryD);
			break;
		    }

		    mPreparedStatement.setInt(1, iSalaire);
		    mPreparedStatement.setInt(2, iSalaire);
		    mPreparedStatement.setInt(3, iTeamId);
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
