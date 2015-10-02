package com.pedagogiksolution.poolavie.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.trade.RecuperationTeamForExchange;
import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class TorontoServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9126048396062272819L;
	String statement,statement2,statement3,statement4,statement5,statement6;
	Connection conn;
	ResultSet rs, rs2, rs3, rs4,rs5,rs6;
	String mTeamIdentifiant;
	int mTeam;
	String mUsername;
	DatabaseConnector dbHelper;
	int max_salaire,total_salaire,budget_restant,moy_restante,nb_att,nb_def,nb_gar,
	nb_rook,nb_contrat,nb_equipe,manq_equipe,manq_att,manq_def,manq_gar,manq_rook,
	bonus_5,argent_recu,bonus_penalite;
	int draft_pick_no,ronde,team_id,team_id_from,team_count;
	String equipe_who_draft,from;
	int pick_manquant;

	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Object> dataList = new ArrayList<Object>();
		List<Object> dataList2 = new ArrayList<Object>();
		List<Object> dataList3 = new ArrayList<Object>();
		List<Object> dataList4 = new ArrayList<Object>();
		List<Object> dataList5 = new ArrayList<Object>();
		String teamIdentifiant = (String) req.getSession().getAttribute("mTeamId");
		if(teamIdentifiant!=null){mTeam = Integer.parseInt(teamIdentifiant);}
		// connexion aux serveurs4 de base de donnée
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		// si connexion est bonne on récupère les informations de la table
		// utilisateur
		if (conn != null) {

			try {
				/* récupération des informations selon le identifiant equipe */
				statement = "SELECT * FROM players WHERE players.position='attaquant' AND players.club_ecole='0' AND players.team_id=6 ORDER BY pts DESC";
				statement2 = "SELECT * FROM players WHERE players.position='defenseur' AND players.club_ecole='0' AND players.team_id=6 ORDER BY pts DESC";
				statement3 = "SELECT * FROM players WHERE players.position='gardien' AND players.club_ecole='0' AND players.team_id=6 ORDER BY pts DESC";
				statement4 = "SELECT * FROM players WHERE players.club_ecole='1' AND players.team_id=6 ORDER BY pts DESC";
				statement5 = "SELECT * FROM equipes WHERE team_id=6";
				try {
					rs = conn.createStatement().executeQuery(statement);

					while (rs.next()) {

						dataList.add(rs.getString("nom"));
						dataList.add(rs.getString("team"));
						dataList.add(rs.getInt("pj"));
						dataList.add(rs.getInt("but_victoire"));
						dataList.add(rs.getInt("aide_overtime"));
						dataList.add(rs.getInt("pts"));
						dataList.add(rs.getString("years_1"));
						dataList.add(rs.getString("years_2"));
						dataList.add(rs.getString("years_3"));
						dataList.add(rs.getString("years_4"));
						dataList.add(rs.getString("years_5"));
					}
					rs.close();
					rs2 = conn.createStatement().executeQuery(statement2);

					while (rs2.next()) {

						dataList2.add(rs2.getString("nom"));
						dataList2.add(rs2.getString("team"));
						dataList2.add(rs2.getInt("pj"));
						dataList2.add(rs2.getInt("but_victoire"));
						dataList2.add(rs2.getInt("aide_overtime"));
						dataList2.add(rs2.getInt("pts"));
						dataList2.add(rs2.getString("years_1"));
						dataList2.add(rs2.getString("years_2"));
						dataList2.add(rs2.getString("years_3"));
						dataList2.add(rs2.getString("years_4"));
						dataList2.add(rs2.getString("years_5"));

						

					}
					rs2.close();
					rs3 = conn.createStatement().executeQuery(statement3);

					while (rs3.next()) {

						dataList3.add(rs3.getString("nom"));
						dataList3.add(rs3.getString("team"));
						dataList3.add(rs3.getInt("pj"));
						dataList3.add(rs3.getInt("but_victoire"));
						dataList3.add(rs3.getInt("aide_overtime"));
						dataList3.add(rs3.getInt("blanchissage"));
						dataList3.add(rs3.getInt("pts"));
						dataList3.add(rs3.getString("years_1"));
						dataList3.add(rs3.getString("years_2"));
						dataList3.add(rs3.getString("years_3"));
						dataList3.add(rs3.getString("years_4"));
						dataList3.add(rs3.getString("years_5"));

						

					}
					rs3.close();
					rs4 = conn.createStatement().executeQuery(statement4);

					while (rs4.next()) {

						dataList4.add(rs4.getString("nom"));
						dataList4.add(rs4.getString("team"));
						dataList4.add(rs4.getInt("pj"));
						dataList4.add(rs4.getInt("but_victoire"));
						dataList4.add(rs4.getInt("aide_overtime"));
						dataList4.add(rs4.getInt("pts"));
						dataList4.add(rs4.getString("years_1"));
						dataList4.add(rs4.getString("years_2"));
						dataList4.add(rs4.getString("years_3"));
						dataList4.add(rs4.getString("years_4"));
						dataList4.add(rs4.getString("years_5"));

						

					}
					rs4.close();
					rs5 = conn.createStatement().executeQuery(statement5);
					if (rs5.next()) {
					max_salaire = rs5.getInt("max_salaire_begin");
					total_salaire= rs5.getInt("total_salaire_now");
					budget_restant= rs5.getInt("budget_restant");
					moy_restante= rs5.getInt("moy_sal_restant_draft");
					nb_att= rs5.getInt("nb_attaquant");
					nb_def= rs5.getInt("nb_defenseur");
					nb_gar= rs5.getInt("nb_gardien");
					nb_rook= rs5.getInt("nb_rookie");
					nb_contrat= rs5.getInt("nb_contrat");
					nb_equipe= rs5.getInt("nb_equipe");
					manq_equipe= rs5.getInt("manquant_equipe");
					manq_att= rs5.getInt("manquant_att");
					manq_def= rs5.getInt("manquant_def");
					manq_gar= rs5.getInt("manquant_gardien");
					manq_rook= rs5.getInt("manquant_recrue");
					bonus_5= rs5.getInt("bonus_5m");
					argent_recu= rs5.getInt("argent_recu");
					bonus_penalite = rs5.getInt("bonus_penalite");
					pick_manquant = rs5.getInt("manquant_equipe") + rs5.getInt("manquant_recrue");
					}
					rs5.close();
	
					
					statement6 = "SELECT * FROM draft_round WHERE team_id=6 LIMIT " + pick_manquant;
					
					rs6 = conn.createStatement().executeQuery(statement6);
					while (rs6.next()) {
						dataList5.add( rs6.getInt("team_count"));
						dataList5.add(rs6.getInt("ronde"));
						dataList5.add(rs6.getString("from"));	
						dataList5.add(rs6.getInt("draft_pick_no"));
						
						
					}
					rs6.close();
					RecuperationTeamForExchange recupTeam = new RecuperationTeamForExchange();
					ResultSet rs9 = recupTeam.recuperationPick(6);
					
					req.setAttribute("picks", rs9);
					
					
					req.setAttribute("dataDraftRound", dataList5);
					
					req.getSession().setAttribute("max_salaire", max_salaire);
					req.getSession().setAttribute("total_salaire", total_salaire);
					req.getSession().setAttribute("budget_restant", budget_restant);
					req.getSession().setAttribute("moy_restante", moy_restante);
					req.getSession().setAttribute("nb_att", nb_att);
					req.getSession().setAttribute("nb_def", nb_def);
					req.getSession().setAttribute("nb_gar", nb_gar);
					req.getSession().setAttribute("nb_rook", nb_rook);
					req.getSession().setAttribute("nb_contrat", nb_contrat);
					req.getSession().setAttribute("nb_equipe", nb_equipe);
					req.getSession().setAttribute("manq_equipe", manq_equipe);
					req.getSession().setAttribute("manq_att",manq_att);
					req.getSession().setAttribute("manq_def", manq_def);
					req.getSession().setAttribute("manq_gar", manq_gar);
					req.getSession().setAttribute("manq_rook", manq_rook);
					req.getSession().setAttribute("bonus_5", bonus_5);
					req.getSession().setAttribute("argent_recu", argent_recu);
					req.getSession().setAttribute("bonus_penalite", bonus_penalite);
					
					
					
					
					req.setAttribute("mTeamDataListAttaquant", dataList);
					req.setAttribute("mTeamDataListDefenseur", dataList2);
					req.setAttribute("mTeamDataListGardien", dataList3);
					req.setAttribute("mTeamDataListRecrue", dataList4);
					req.getRequestDispatcher("/jsp/toronto.jsp").forward(
							req, resp);
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} finally {
				dbHelper.close(conn);
			}

		} else {
			// une erreur de connexion s'est produite, gérer ce problème pour
			// être transparent pour l'utilisateur
			req.getRequestDispatcher("/jsp/no_connexion.jsp")
					.forward(req, resp);
		}

	}

}
