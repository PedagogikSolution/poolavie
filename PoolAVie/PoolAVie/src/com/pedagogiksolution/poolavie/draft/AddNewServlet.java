package com.pedagogiksolution.poolavie.draft;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class AddNewServlet extends HttpServlet {
	Connection conn;
	DatabaseConnector dbHelper;
	/**
	 * 
	 */
	private static final long serialVersionUID = 4240425587709045108L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/jsp/add_new_player.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String nom = req.getParameter("nom");
		String team = req.getParameter("team");
		String position = req.getParameter("position");
		String birthday = req.getParameter("birthday");
		String salaire_draft = req.getParameter("salaire_draft");
		String can_be_rookie = req.getParameter("can_be_rookie");
		
		// connexion aux serveurs4 de base de donnée
					dbHelper = new DatabaseConnector();
					conn = dbHelper.open();
		
					if (conn != null) {
						
						String statement = "INSERT INTO players (nom,team,position,birthday,salaire_draft,can_be_rookie,contrat)" +
								" VALUES ('" + nom + "','" + team + "','" + position + "','" + birthday + "','" + salaire_draft + "','" +can_be_rookie+"','0')";
									
						
						try {
							
							try {
								conn.createStatement().executeUpdate(statement);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
							
							
						
							
						}finally {
							dbHelper.close(conn);
						}
					} else {
						// une erreur de connexion s'est produite, gérer ce problème pour
						// être transparent pour l'utilisateur
						req.getRequestDispatcher("/jsp/no_connexion.jsp")
								.forward(req, resp);
					}
		
		
		
		
		req.getRequestDispatcher("/draft").forward(req,
				resp);
		
		
	}

}
