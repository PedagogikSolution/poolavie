package com.pedagogiksolution.poolavie.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;
import com.pedagogiksolution.poolavie.utils.PasswordEncryption;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username;
	String password;
	String url;
	int success;
	String statement;
	Connection conn;
	ResultSet rs;
	String mPassword;
	int teamIdentifiant;
	String mTeam;
	DatabaseConnector dbHelper;
	PasswordEncryption pEncrypt;
	Boolean validateHash;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// recupération des input du formulaire de login
		username = req.getParameter("user");
		password = req.getParameter("password");

		// connexion aux serveurs de base de donnée
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
        
		pEncrypt = new PasswordEncryption();
		// si connexion est bonne on récupère les informations de la table
		// utilisateur
		if (conn != null) {
			/* récupération des informations selon le username */
			try {

				try {
					statement = "SELECT password,identifiant_equipe,team FROM utilisateurs WHERE utilisateurs.username='"
							+ username + "'";
					rs = conn.createStatement().executeQuery(statement);

					if (rs != null) {
						if (rs.next()) {
							mPassword = rs.getString("password");
							teamIdentifiant = rs.getInt("identifiant_equipe");
							mTeam = rs.getString("team");
							/*
							 * si recupération ok, testing du password sur le
							 * username
							 */
							try {
								validateHash = pEncrypt.validatePassword(password, mPassword);
							} catch (NoSuchAlgorithmException
									| InvalidKeySpecException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							if (validateHash) {
								req.setAttribute("mTeamId", teamIdentifiant);
								req.setAttribute("mTeam", mTeam);
								req.getRequestDispatcher("/jsp/login.jsp")
										.forward(req, resp);

							} else {
								
								req.setAttribute("login_message", "2");
								req.getRequestDispatcher(
										"/jsp/home.jsp").forward(req,
										resp);
							}
						} else {
							req.setAttribute("login_message", "1");
							req.getRequestDispatcher("/jsp/home.jsp")
									.forward(req, resp);
						}

					} else {
						req.setAttribute("login_message", "0");
						req.getRequestDispatcher("/jsp/home.jsp")
								.forward(req, resp);
					}
				} finally {
					conn.close();
					// dbHelper.close(conn);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// fin du if connected
		} else {
			// une erreur de connexion s'est produite, gérer ce problème pour
			// être transparent pour l'utilisateur
			req.getRequestDispatcher("/jsp/no_connexion.jsp")
					.forward(req, resp);
		}
		/* fin du doPost */
	}
	/* fin du servlet */

}
