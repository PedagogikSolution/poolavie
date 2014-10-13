package com.pedagogiksolution.poolavie.login;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;
import com.pedagogiksolution.poolavie.utils.PasswordEncryption;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2718138746239425792L;
	String username, email;
	String password;
	String team;
	String statement, statement2, statement3;
	String mStrongPassword;
	PasswordEncryption pEncrypt;
	DatabaseConnector dbHelper;
	Connection conn;
	ResultSet rs,rs2;
	int teamIdentifiant, mTeamIdentifiant;
	int mId;
	PreparedStatement ps;
	
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		username = req.getParameter("user_reg");
		password = req.getParameter("password_reg");
		team = req.getParameter("team_reg");
		email = req.getParameter("email_reg");
		teamIdentifiant = teamToId(team);

		pEncrypt = new PasswordEncryption();
			try {
				mStrongPassword = pEncrypt.passwordEncryption(password);
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		

		// connexion aux serveurs de base de donnée
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();
		List<Object> dataList = new ArrayList<Object>();
		if (conn != null) {
			
					try {
				
					statement = "SELECT identifiant_equipe FROM utilisateurs WHERE utilisateurs.identifiant_equipe="
							+ teamIdentifiant;
					try {
						rs = conn.createStatement().executeQuery(statement);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						if (!rs.isBeforeFirst()) {

							statement2 = "INSERT INTO utilisateurs"
									+ "(username, password, team,identifiant_equipe,email) VALUES"
									+ "(?,?,?,?,?)";

							ps = conn.prepareStatement(statement2);
							ps.setString(1, username);
							ps.setString(2, mStrongPassword);
							ps.setString(3, team);
							ps.setInt(4, teamIdentifiant);
							ps.setString(5, email);
							int insertGood = ps.executeUpdate();
							
							statement3 = "SELECT * FROM classement ORDER BY points DESC";
							
							rs2 = conn.createStatement().executeQuery(statement3);	 

							  while (rs2.next ()){

							  //Add records into data list



							  dataList.add(rs2.getString("equipe"));
							  dataList.add(rs2.getInt("pj"));
							  dataList.add(rs2.getInt("but"));
							  dataList.add(rs2.getInt("passe"));
							  dataList.add(rs2.getInt("points"));
							  dataList.add(rs2.getInt("moyenne"));
							  dataList.add(rs2.getInt("hier"));
							  dataList.add(rs2.getInt("semaine"));
							  dataList.add(rs2.getInt("mois"));
							  dataList.add(rs2.getInt("difference"));
							  
							  }
							
							if(insertGood==1){
								
								req.setAttribute("classement",dataList);							
								req.setAttribute("mTeam", team);
								req.setAttribute("mTeamId", teamIdentifiant);
								req.setAttribute("mUsername", username);								
								req.getRequestDispatcher("/jsp/main.jsp").forward(req,
										resp);
								
								
							} else {
								// erreur d'insertion a trouver
								req.setAttribute("login_message", "4");
								req.getRequestDispatcher("/jsp/home.jsp").forward(req,
										resp);
								
							}

						} else {
							req.setAttribute("login_message", "3");
							req.getRequestDispatcher("/jsp/home.jsp").forward(req,
									resp);

						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
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

	private int teamToId(String team2) {
		if (team2.equals("Los Angeles")) {
			mId = 0;
		} else if (team2.equals("Detroit")) {
			mId = 1;
		} else if (team2.equals("Montreal")) {
			mId = 2;
		} else if (team2.equals("Chicago")) {
			mId = 3;
		} else if (team2.equals("New York")) {
			mId = 4;
		} else if (team2.equals("Philadelphie")) {
			mId = 5;
		} else if (team2.equals("Toronto")) {
			mId = 6;
		} else if (team2.equals("St-Louis")) {
			mId = 7;
		} else if (team2.equals("Boston")) {
			mId = 8;
		} else if (team2.equals("Pittsburgh")) {
			mId = 9;
		}
		return mId;
	}

}
