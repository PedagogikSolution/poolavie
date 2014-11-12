package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class SignatureAfterDraftConfirmationServlet extends HttpServlet {
	DatabaseConnector dbHelper;
	Connection conn;
	String nb_annee_temp,player_id_temp;
	int  player_id,nb_annee;

	String statement1, statement2, statement3;
	int team_id;
	ResultSet rs1, rs2, rs3;
	String team_id_temp;
	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		team_id_temp = (String) req.getSession().getAttribute("mTeamId");
		team_id = Integer.parseInt(team_id_temp);

		nb_annee_temp = req.getParameter("nb_annee");
		nb_annee = Integer.parseInt(nb_annee_temp);
		player_id_temp = req.getParameter("player_id");
		player_id = Integer.parseInt(player_id_temp);
		
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		
		
		try {
		
		switch(nb_annee){
		case 1 : statement1 = "UPDATE players SET years_2=salaire_contrat,years_3='X',years_4='X',years_5='X',contrat_max_years=contrat_cours+1 WHERE _id="+player_id+"";
				 statement2 = "UPDATE equipes SET nb_contrat=nb_contrat+1 WHERE team_id="+team_id+"";
			break;
		case 2 : statement1 = "UPDATE players SET years_2=salaire_contrat,years_3=salaire_contrat,years_4='X',years_5='X',contrat_max_years=contrat_cours+2 WHERE _id="+player_id+"";
				 statement2 = "UPDATE equipes SET nb_contrat=nb_contrat+1 WHERE team_id="+team_id+"";
			break;
		case 3 : statement1 = "UPDATE players SET years_2=salaire_contrat,years_3=salaire_contrat,years_4=salaire_contrat,years_5='X',contrat_max_years=contrat_cours+3 WHERE _id="+player_id+"";
				 statement2 = "UPDATE equipes SET nb_contrat=nb_contrat+1 WHERE team_id="+team_id+"";
			break;
		case 4 : statement1 = "UPDATE players SET years_2=salaire_contrat,years_3=salaire_contrat,years_4=salaire_contrat,years_5=salaire_contrat,contrat_max_years=contrat_cours+4 WHERE _id="+player_id+"";
				 statement2 = "UPDATE equipes SET nb_contrat=nb_contrat+1 WHERE team_id="+team_id+"";
			break;
		}
			
		try {
			conn.createStatement().executeUpdate(statement1);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		try {
			conn.createStatement().executeUpdate(statement2);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
			
		}finally {
			dbHelper.close(conn);
		}
		
		
		resp.sendRedirect("/equipes");

	}

}
