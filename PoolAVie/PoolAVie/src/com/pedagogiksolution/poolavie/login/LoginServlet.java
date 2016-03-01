package com.pedagogiksolution.poolavie.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.update.UpdatePlayersStats;
import com.pedagogiksolution.poolavie.utils.DatabaseConnector;
import com.pedagogiksolution.poolavie.utils.PasswordEncryption;

@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {

	
	String username;
	String password;
	String url;
	int success;
	String statement, statement3, statement11, statement22, statement33,
			statement44;
	Connection conn;
	ResultSet rs, rs2, rs22, rs33;
	ResultSet rs11;
	String mPassword;
	int teamIdentifiant;
	String mTeam;
	String mUsername;
	DatabaseConnector dbHelper;
	PasswordEncryption pEncrypt;
	Boolean validateHash;
	UpdatePlayersStats updateProcess;
	int pts_att_0, pts_goal_0, pts_total_0, pts_def_0;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		updateProcess = new UpdatePlayersStats();
		updateProcess.updateClassement();
		
	
		String date_start = "16 décembre 2015";
		req.getSession().setAttribute("dateLastUpdate", date_start);
		
		// recupï¿½ration des input du formulaire de login
		username = req.getParameter("user");
		password = req.getParameter("password");

		// connexion aux serveurs de base de donnï¿½e
		dbHelper = new DatabaseConnector();

		List<Object> dataList = new ArrayList<Object>();
		pEncrypt = new PasswordEncryption();
		 
		conn = dbHelper.open();
		// si connexion est bonne on rï¿½cupï¿½re les informations de la table
		// utilisateur

		if (conn != null) {

			// 
			/* rï¿½cupï¿½ration des informations selon le username */
			try {

				

				try {
					statement = "SELECT password,identifiant_equipe,team,username FROM utilisateurs WHERE utilisateurs.username='"
							+ username + "'";
					rs = conn.createStatement().executeQuery(statement);

					if (rs != null) {
						if (rs.next()) {
							mPassword = rs.getString("password");
							teamIdentifiant = rs.getInt("identifiant_equipe");
							mTeam = rs.getString("team");
							mUsername = rs.getString("username");
							/*
							 * si recupï¿½ration ok, testing du password sur le
							 * username
							 */
							rs.close();
							try {
								validateHash = pEncrypt.validatePassword(
										password, mPassword);
							} catch (NoSuchAlgorithmException
									| InvalidKeySpecException e) {
								
								e.printStackTrace();
							}

							if (validateHash) {

								statement3 = "SELECT * FROM classement ORDER BY points DESC";

								rs2 = conn.createStatement().executeQuery(
										statement3);

								while (rs2.next()) {

									// Add records into data list

									dataList.add(rs2.getString("equipe"));
									dataList.add(rs2.getInt("pj"));
									dataList.add(rs2.getInt("but"));
									dataList.add(rs2.getInt("passe"));
									dataList.add(rs2.getInt("points"));
									dataList.add(rs2.getBigDecimal("moyenne"));
									dataList.add(rs2.getInt("hier"));
									dataList.add(rs2.getInt("semaine"));
									dataList.add(rs2.getInt("mois"));
									dataList.add(rs2.getInt("difference"));

								}
								rs2.close();
								if(teamIdentifiant==1){
								    req.getSession().setAttribute("adminIn","ok");
								}
								req.setAttribute("classement", dataList);
								req.setAttribute("mTeamId", teamIdentifiant);
								req.setAttribute("mTeam", mTeam);
								req.setAttribute("mUsername", mUsername);
								req.getRequestDispatcher("/jsp/main.jsp")
										.forward(req, resp);

							} else {

								req.setAttribute("login_message", "2");
								req.getRequestDispatcher("/jsp/home.jsp")
										.forward(req, resp);
							}
						} else {
							req.setAttribute("login_message", "1");
							req.getRequestDispatcher("/jsp/home.jsp").forward(
									req, resp);
						}

					} else {
						req.setAttribute("login_message", "0");
						req.getRequestDispatcher("/jsp/home.jsp").forward(req,
								resp);
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
			// une erreur de connexion s'est produite, gï¿½rer ce problï¿½me pour
			// ï¿½tre transparent pour l'utilisateur
			req.getRequestDispatcher("/jsp/no_connexion.jsp")
					.forward(req, resp);
		}
		/* fin du doPost */
	}
	/* fin du servlet */

}
