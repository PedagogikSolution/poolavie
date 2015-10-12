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

public class DraftServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9126048396062272819L;
	String statement, statement5,statement6,statement7;
	Connection conn;
	ResultSet rs, rs5,rs6,rs7;
	String teamIdentifiant;
	String sortBy;
	int team_id,mTeam_id,draft_pick_no;

	DatabaseConnector dbHelper;
	int max_salaire, total_salaire, budget_restant, moy_restante, nb_att,
			nb_def, nb_gar, nb_rook, nb_contrat, nb_equipe, manq_equipe,
			manq_att, manq_def, manq_gar, manq_rook, bonus_5, argent_recu,
			bonus_penalite;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
			sortBy = req.getParameter("sortby");
			if(sortBy==null){
				sortBy="null";
			}
			teamIdentifiant = (String) req.getSession().getAttribute("mTeamId");
			if(teamIdentifiant!=null){mTeam_id = Integer.parseInt(teamIdentifiant);}
			// connexion aux serveurs4 de base de donnée
			dbHelper = new DatabaseConnector();
			conn = dbHelper.open();
			// si connexion est bonne on récupère les informations de la table
			// utilisateurdraft
			if (conn != null) {

				try {
					/* récupération des informations selon le identifiant equipe */
					if (sortBy.equalsIgnoreCase("pts")) {
						statement = "SELECT * FROM players WHERE contrat=0 ORDER BY pts DESC";
					} else if (sortBy.equalsIgnoreCase("proj")) {
						statement = "SELECT * FROM players WHERE contrat=0 ORDER BY projection DESC";
					} else if (sortBy.equalsIgnoreCase("salaire")) {
						statement = "SELECT * FROM players WHERE contrat=0 ORDER BY salaire_draft DESC";
					} else {
						statement = "SELECT * FROM players WHERE contrat=0";
					}

					statement5 = "SELECT * FROM equipes WHERE team_id='"
							+ teamIdentifiant + "'";
					
					statement6 = "SELECT * FROM draft_round WHERE follow_up=2";
					
					
					
					
						
						rs6 = conn.createStatement().executeQuery(statement6);
						
						if(rs6.next()){
						
							team_id = rs6.getInt("team_id");
							draft_pick_no = rs6.getInt("draft_pick_no");
						}
						
						rs6.close();
						statement7 = "SELECT team_id,equipe FROM draft_round WHERE draft_pick_no > '" + draft_pick_no + "' LIMIT 10";
						rs7 = conn.createStatement().executeQuery(statement7);
						req.setAttribute("next_10", rs7);
						
						if(team_id==mTeam_id){
							req.setAttribute("draft_pick_now", team_id);
							req.setAttribute("myTurn", true);
							req.setAttribute("draft_pick_no", draft_pick_no);
							
						} else {
							req.setAttribute("draft_pick_now", team_id);
							req.setAttribute("myTurn", false);
							req.setAttribute("draft_pick_no", draft_pick_no);
						}

						rs = conn.createStatement().executeQuery(statement);

						req.setAttribute("draft_all_player", rs);
						
						rs5 = conn.createStatement().executeQuery(statement5);

						if (rs5.next()) {
							max_salaire = rs5.getInt("max_salaire_begin");
							total_salaire = rs5.getInt("total_salaire_now");
							budget_restant = rs5.getInt("budget_restant");
							moy_restante = rs5.getInt("moy_sal_restant_draft");
							nb_att = rs5.getInt("nb_attaquant");
							nb_def = rs5.getInt("nb_defenseur");
							nb_gar = rs5.getInt("nb_gardien");
							nb_rook = rs5.getInt("nb_rookie");
							nb_contrat = rs5.getInt("nb_contrat");
							nb_equipe = rs5.getInt("nb_equipe");
							manq_equipe = rs5.getInt("manquant_equipe");
							manq_att = rs5.getInt("manquant_att");
							manq_def = rs5.getInt("manquant_def");
							manq_gar = rs5.getInt("manquant_gardien");
							manq_rook = rs5.getInt("manquant_recrue");
							bonus_5 = rs5.getInt("bonus_5m");
							argent_recu = rs5.getInt("argent_recu");
							bonus_penalite = rs5.getInt("bonus_penalite");
						}
						rs5.close();
						
						req.getSession().setAttribute("max_salaire", max_salaire);
						req.getSession().setAttribute("total_salaire",
								total_salaire);
						req.getSession().setAttribute("budget_restant",
								budget_restant);
						req.getSession().setAttribute("moy_restante", moy_restante);
						req.getSession().setAttribute("nb_att", nb_att);
						req.getSession().setAttribute("nb_def", nb_def);
						req.getSession().setAttribute("nb_gar", nb_gar);
						req.getSession().setAttribute("nb_rook", nb_rook);
						req.getSession().setAttribute("nb_contrat", nb_contrat);
						req.getSession().setAttribute("nb_equipe", nb_equipe);
						req.getSession().setAttribute("manq_equipe", manq_equipe);
						req.getSession().setAttribute("manq_att", manq_att);
						req.getSession().setAttribute("manq_def", manq_def);
						req.getSession().setAttribute("manq_gar", manq_gar);
						req.getSession().setAttribute("manq_rook", manq_rook);
						req.getSession().setAttribute("bonus_5", bonus_5);
						req.getSession().setAttribute("argent_recu", argent_recu);
						req.getSession().setAttribute("bonus_penalite",
								bonus_penalite);
						
						
						
						
						
						

						req.getRequestDispatcher("/jsp/draft.jsp").forward(req,
								resp);

					} catch (SQLException e) {
						e.printStackTrace();
					
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		sortBy = req.getParameter("sortby");
		if(sortBy==null){
			sortBy="null";
		}
		teamIdentifiant = (String) req.getSession().getAttribute("mTeamId");
		if(teamIdentifiant!=null){mTeam_id = Integer.parseInt(teamIdentifiant);}
		// connexion aux serveurs4 de base de donnée
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		// si connexion est bonne on récupère les informations de la table
		// utilisateur
		if (conn != null) {

			try {
				/* récupération des informations selon le identifiant equipe */
				if (sortBy.equalsIgnoreCase("pts")) {
					statement = "SELECT * FROM players WHERE contrat=0 ORDER BY pts DESC";
				} else if (sortBy.equalsIgnoreCase("proj")) {
					statement = "SELECT * FROM players WHERE contrat=0 ORDER BY projection DESC";
				} else if (sortBy.equalsIgnoreCase("salaire")) {
					statement = "SELECT * FROM players WHERE contrat=0 ORDER BY salaire_draft DESC";
				} else {
					statement = "SELECT * FROM players WHERE contrat=0";
				}

				statement5 = "SELECT * FROM equipes WHERE team_id='"
						+ teamIdentifiant + "'";
				
				statement6 = "SELECT * FROM draft_round WHERE follow_up=2";
				
				
				try {
					
					rs6 = conn.createStatement().executeQuery(statement6);
					
					if(rs6.next()){
						draft_pick_no = rs6.getInt("draft_pick_no");
						team_id = rs6.getInt("team_id");
					}
					rs6.close();
					statement7 = "SELECT team_id,equipe FROM draft_round WHERE draft_pick_no > '" + draft_pick_no + "' LIMIT 10";
					rs7 = conn.createStatement().executeQuery(statement7);
					req.setAttribute("next_10", rs7);
					
					if(team_id==mTeam_id){
						req.setAttribute("myTurn", true);
						req.setAttribute("draft_pick_now", team_id);
						req.setAttribute("draft_pick_no", draft_pick_no);
					} else {
						req.setAttribute("myTurn", false);
						req.setAttribute("draft_pick_now", team_id);
						req.setAttribute("draft_pick_no", draft_pick_no);
					}

					rs = conn.createStatement().executeQuery(statement);

					req.setAttribute("draft_all_player", rs);
					
					rs5 = conn.createStatement().executeQuery(statement5);

					if (rs5.next()) {
						max_salaire = rs5.getInt("max_salaire_begin");
						total_salaire = rs5.getInt("total_salaire_now");
						budget_restant = rs5.getInt("budget_restant");
						moy_restante = rs5.getInt("moy_sal_restant_draft");
						nb_att = rs5.getInt("nb_attaquant");
						nb_def = rs5.getInt("nb_defenseur");
						nb_gar = rs5.getInt("nb_gardien");
						nb_rook = rs5.getInt("nb_rookie");
						nb_contrat = rs5.getInt("nb_contrat");
						nb_equipe = rs5.getInt("nb_equipe");
						manq_equipe = rs5.getInt("manquant_equipe");
						manq_att = rs5.getInt("manquant_att");
						manq_def = rs5.getInt("manquant_def");
						manq_gar = rs5.getInt("manquant_gardien");
						manq_rook = rs5.getInt("manquant_recrue");
						bonus_5 = rs5.getInt("bonus_5m");
						argent_recu = rs5.getInt("argent_recu");
						bonus_penalite = rs5.getInt("bonus_penalite");
					}
					rs5.close();
					req.getSession().setAttribute("max_salaire", max_salaire);
					req.getSession().setAttribute("total_salaire",
							total_salaire);
					req.getSession().setAttribute("budget_restant",
							budget_restant);
					req.getSession().setAttribute("moy_restante", moy_restante);
					req.getSession().setAttribute("nb_att", nb_att);
					req.getSession().setAttribute("nb_def", nb_def);
					req.getSession().setAttribute("nb_gar", nb_gar);
					req.getSession().setAttribute("nb_rook", nb_rook);
					req.getSession().setAttribute("nb_contrat", nb_contrat);
					req.getSession().setAttribute("nb_equipe", nb_equipe);
					req.getSession().setAttribute("manq_equipe", manq_equipe);
					req.getSession().setAttribute("manq_att", manq_att);
					req.getSession().setAttribute("manq_def", manq_def);
					req.getSession().setAttribute("manq_gar", manq_gar);
					req.getSession().setAttribute("manq_rook", manq_rook);
					req.getSession().setAttribute("bonus_5", bonus_5);
					req.getSession().setAttribute("argent_recu", argent_recu);
					req.getSession().setAttribute("bonus_penalite",
							bonus_penalite);
					
					
					
					
					
					

					req.getRequestDispatcher("/jsp/draft.jsp").forward(req,
							resp);

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
