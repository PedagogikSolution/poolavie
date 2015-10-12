package com.pedagogiksolution.poolavie.draft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class PickConfirmerServlet extends HttpServlet {

    int manquant_equipe, manquant_att, manquant_def, manquant_gardien, manquant_recrue, budget_restant;
    String manquant_min;
    int calcul_1, calcul_2, total_manquant;
    int nb_rookie;
    ResultSet rs, rs42;
    String draft_player_id;
    String team_id;
    String is_rookie;
    String salaire;
    DatabaseConnector dbHelper;
    Connection conn;
    String statement, statement2, statement3, statement4, statement5, statement6, statement8, statement42;
    String mTeamID;
    String equipe;
    String nom;
    String draft_pick_no;
    int next_draft_pick;
    String position;
    int salaireInt, team_id2, draft_pick_no2;
    int moyenneRestante;

    /**
	 * 
	 */
    private static final long serialVersionUID = -7979330643530437866L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	team_id = req.getParameter("team_id");
	is_rookie = req.getParameter("is_rookie");
	salaire = req.getParameter("salaire");
	salaireInt = Integer.parseInt(salaire);
	position = req.getParameter("position");
	nom = req.getParameter("nom");	
	draft_player_id = req.getParameter("draft_player_id");
	draft_pick_no = req.getParameter("draft_pick_no");
	equipe = (String) req.getSession().getAttribute("mTeamName");
	
	if (team_id != null) {
	    team_id2 = Integer.parseInt(team_id);
	} else {
	    resp.sendRedirect("/equipes");
	    return;
	}

	if (draft_pick_no != null) {
	    draft_pick_no2 = Integer.parseInt(draft_pick_no);
	} else {
	    resp.sendRedirect("/equipes");
	    return;
	}
		
	statement42 = "SELECT draft_pick_no FROM draft_round WHERE follow_up=0 LIMIT 1";
	statement = "SELECT * FROM equipes WHERE team_id='" + team_id + "'";
	
	dbHelper = new DatabaseConnector();
	conn = dbHelper.open();
	// si connexion est bonne on récupère les informations de la table
	// utilisateur

	
	try {
	    rs = conn.createStatement().executeQuery(statement);
	    rs42 = conn.createStatement().executeQuery(statement42);
	    
	    
	    
	    if (rs.next()) {
		nb_rookie = rs.getInt("nb_rookie");
		manquant_equipe = rs.getInt("manquant_equipe");
		manquant_att = rs.getInt("manquant_att");
		manquant_def = rs.getInt("manquant_def");
		manquant_recrue = rs.getInt("manquant_recrue");
		manquant_gardien = rs.getInt("manquant_gardien");
		budget_restant = rs.getInt("budget_restant");
		calcul_1 = budget_restant - salaireInt;
		calcul_2 = manquant_equipe - 1;
		
		if (calcul_2==0){
		    calcul_2=1;
		}
		if (calcul_2 != 0) {
		    moyenneRestante = calcul_1 / calcul_2;
		} 
		total_manquant = manquant_att + manquant_def + manquant_gardien;
	    }

	    rs.close();

	    if (rs42.next()) {
		next_draft_pick = rs42.getInt("draft_pick_no");
	    }
	    rs42.close();

	    /****** verification si rookie possible ******/

	    if (is_rookie.equals("1")) {

		if (nb_rookie >= 8) {
		    resp.sendRedirect("/jsp/to_much_rookie.jsp");
		    return;
		}

	    }

	    /**** verification si draft possible ******/

	    if (position.equals("defenseur") || position.equals("gardien")) {
		if (manquant_att >= manquant_equipe) {

		    resp.sendRedirect("/jsp/miss_attaquant.jsp");
		    return;
		}
	    }

	    if (position.equals("attaquant") || position.equals("gardien")) {
		if (manquant_def >= manquant_equipe) {

		    resp.sendRedirect("/jsp/miss_defenseur.jsp");
		    return;
		}
	    }

	    if (position.equals("defenseur") || position.equals("attaquant")) {
		if (manquant_gardien >= manquant_equipe) {

		    resp.sendRedirect("/jsp/miss_gardien.jsp");
		    return;

		}
	    }

	    /**** verification si draft possible ******/

	    if (calcul_2 != 0) {
		
		
		
		if (moyenneRestante < 0) {

		    resp.sendRedirect("/jsp/miss_cash.jsp");
		    return;

		}
		
	    }
	    
	    
	    
	    switch (team_id2) {
	    case 0:
		mTeamID = "team_0";
		break;
	    case 1:
		mTeamID = "team_1";
		break;
	    case 2:
		mTeamID = "team_2";
		break;
	    case 3:
		mTeamID = "team_3";
		break;
	    case 4:
		mTeamID = "team_4";
		break;
	    case 5:
		mTeamID = "team_5";
		break;
	    case 6:
		mTeamID = "team_6";
		break;
	    case 7:
		mTeamID = "team_7";
		break;
	    case 8:
		mTeamID = "team_8";
		break;
	    case 9:
		mTeamID = "team_9";
		break;

	    }

	    statement3 = "UPDATE reload_counter SET reload=0,counter=0,team_0=0,team_1=0,team_2=0,team_3=0,team_4=0,team_5=0,team_6=0,team_7=0,team_8=0,team_9=0";

	    conn.createStatement().executeUpdate(statement3);

	    statement8 = "UPDATE reload_counter SET reload=1,counter=1," + mTeamID + "=1 WHERE _id=1";

	    if (is_rookie.equalsIgnoreCase("1")) {

		statement2 = "UPDATE players SET equipe='" + equipe + "',salaire_contrat='" + salaire + "',contrat=1,team_id='" + team_id
			+ "',club_ecole=1, contrat_cours=2015,contrat_max_years=2019,type_contrat=4,years_1='" + salaire
			+ "',years_2='JA',years_3='JA',years_4='JA',years_5='JA' WHERE _id='" + draft_player_id + "' ";

		statement4 = "UPDATE equipes SET nb_rookie= nb_rookie+1, manquant_recrue=manquant_recrue -1,"
			+ " total_salaire_now=total_salaire_now , budget_restant=budget_restant WHERE team_id = '" + team_id + "'";

	    } else {

		statement2 = "UPDATE players SET equipe='" + equipe + "',salaire_contrat='" + salaire + "',contrat=1,team_id='" + team_id
			+ "',club_ecole=0, contrat_cours=2015,contrat_max_years=2019,type_contrat=4,years_1='" + salaire
			+ "',years_2='JA',years_3='JA',years_4='JA',years_5='JA' WHERE _id='" + draft_player_id + "' ";
		if (position.equals("attaquant")) {
		    statement4 = "UPDATE equipes SET moy_sal_restant_draft=budget_restant/manquant_equipe,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,nb_attaquant= nb_attaquant+1, "
			    + "manquant_att=manquant_att -1,total_salaire_now=total_salaire_now + "
			    + salaire
			    + ", budget_restant=budget_restant - "
			    + salaire
			    + " WHERE team_id = '" + team_id + "'";
		} else if (position.equals("defenseur")) {
		    statement4 = "UPDATE equipes SET moy_sal_restant_draft=budget_restant/manquant_equipe,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,nb_defenseur= nb_defenseur+1, "
			    + "manquant_def=manquant_def -1,total_salaire_now=total_salaire_now + "
			    + salaire
			    + ", budget_restant=budget_restant - "
			    + salaire
			    + " WHERE team_id = '" + team_id + "'";

		} else if (position.equals("gardien")) {
		    statement4 = "UPDATE equipes SET moy_sal_restant_draft=budget_restant/manquant_equipe,nb_equipe=nb_equipe+1,manquant_equipe=manquant_equipe-1,nb_gardien= nb_gardien+1, "
			    + "manquant_gardien=manquant_gardien -1,total_salaire_now=total_salaire_now + "
			    + salaire
			    + ", budget_restant=budget_restant - "
			    + salaire
			    + " WHERE team_id = '" + team_id + "'";
		}

	    }

	    statement3 = "UPDATE draft_round SET player_drafted='" + nom + "',follow_up=1 WHERE draft_pick_no='" + draft_pick_no2 + "'";

	    statement5 = "UPDATE draft_round SET follow_up=2 WHERE draft_pick_no='" + next_draft_pick + "'";

	    conn.createStatement().executeUpdate(statement8);
	    conn.createStatement().executeUpdate(statement2);
	    conn.createStatement().executeUpdate(statement3);
	    conn.createStatement().executeUpdate(statement4);
	    conn.createStatement().executeUpdate(statement5);

	    if (manquant_equipe == 1 && nb_rookie == 8 || manquant_equipe == 0 && nb_rookie == 7) {
		String statementFinal = "DELETE FROM draft_round WHERE follow_up=0 AND team_id='" + team_id + "'";
		
		conn.createStatement().executeUpdate(statementFinal);
		
		resp.sendRedirect("/jsp/draft_fini.jsp");
		return;
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    dbHelper.close(conn);
	}

	resp.sendRedirect("/draft");

    }

}
