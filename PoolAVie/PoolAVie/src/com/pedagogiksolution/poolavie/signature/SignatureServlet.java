package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class SignatureServlet extends HttpServlet {
	DatabaseConnector dbHelper;
	Connection conn;
	Date today, start, finish;
	boolean isBefore, isAfter;
	String statement1, statement2, statement3;
	int team_id, nb_contrat;
	ResultSet rs1, rs2, rs3;
	String team_id_temp;
	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/***** récupération de l'équipe active *****/

		team_id_temp = (String) req.getSession().getAttribute("mTeamId");
		team_id = Integer.parseInt(team_id_temp);

		/****
		 * vérification si la date est éligible pour la période de siganture
		 * apres draft
		 *****/
		long now = System.currentTimeMillis();

		today = new Date(now);

		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		String date_start = "23-10-2014";
		try {
			start = sdf.parse(date_start);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String date_finish = "31-10-2014";
		try {
			finish = sdf.parse(date_finish);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		isBefore = today.before(finish);
		isAfter = today.after(start);

		if (isBefore && isAfter) {
			/*** connection au bd ****/
			dbHelper = new DatabaseConnector();
			
			conn = dbHelper.open();

			try {

				/****** on calcul le nombre de contrat actuel du team actif ************/
				statement1 = "SELECT * FROM equipes WHERE team_id ="
						+ team_id + "";
				try {
					rs1 = conn.createStatement().executeQuery(statement1);
					
					if (rs1.next()) {
						nb_contrat = rs1.getInt("nb_contrat");
					}

					/** on vérifie si il y a de la place sur les contrats ***/

					if (nb_contrat < 12) {
						
						/****** recupération de la liste des joueurs autonome  *****/
						
						statement2 = "SELECT * FROM players WHERE team_id = "+ team_id + " AND club_ecole=0 AND years_2='JA'";
						
						rs2= conn.createStatement().executeQuery(statement2);
												
						req.setAttribute("all_possible_signature", rs2);
						
						req.getRequestDispatcher("/jsp/signature_draft_fini.jsp").forward(req,
								resp);
							

					} else {
						resp.sendRedirect("/jsp/signature_max_atteint.jsp");

					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} finally {
				dbHelper.close(conn);
			}

			

		} else {

			resp.sendRedirect("/jsp/signature_after_draft.jsp");

		}

	}

}
