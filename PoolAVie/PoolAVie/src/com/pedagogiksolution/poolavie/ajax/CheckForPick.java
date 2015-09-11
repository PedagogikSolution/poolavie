package com.pedagogiksolution.poolavie.ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class CheckForPick extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2732125333743563410L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		int first_result=0;
		int second_result=0;
		String team_id;
		String result_of_test="0";
		int team_id2=0;
		int mTeamID = 1;
		Connection conn;
		DatabaseConnector dbHelper;
		ResultSet rs;
		String statement, statement2, statement3;
		int counterCount=0;
		int counterCount2=0;
	
		team_id = (String) req.getSession().getAttribute("mTeamId");

		team_id2 = Integer.parseInt(team_id);

		switch (team_id2) {
		case 0:
			mTeamID = 4;
			break;
		case 1:
			mTeamID = 5;
			break;
		case 2:
			mTeamID = 6;
			break;
		case 3:
			mTeamID = 7;
			break;
		case 4:
			mTeamID = 8;
			break;
		case 5:
			mTeamID = 9;
			break;
		case 6:
			mTeamID = 10;
			break;
		case 7:
			mTeamID = 11;
			break;
		case 8:
			mTeamID = 12;
			break;
		case 9:
			mTeamID = 13;
			break;
		}
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();

		if (conn != null) {

			try {
				statement = "SELECT * FROM reload_counter WHERE _id=1";
				try {

					rs = conn.createStatement().executeQuery(statement);
					if (rs.next()) {
						first_result = rs.getInt(2);
						second_result = rs.getInt(mTeamID);
						counterCount2 = rs.getInt(3);
						counterCount = counterCount2 + 1;
					}
					rs.close();
					if (second_result==0) {

						if (first_result==1) {

							statement2 = "UPDATE reload_counter SET counter ="+counterCount+",team_" + team_id2 + "=1 WHERE _id=1";
							
							conn.createStatement().executeUpdate(statement2);
						
							

							if (counterCount==10) {

								statement3 = "UPDATE reload_counter SET reload=0,counter=0,team_0=0,team_1=0,team_2=0,team_3=0,team_4=0,team_5=0,team_6=0,team_7=0,team_8=0,team_9=0";
								conn.createStatement()
										.executeUpdate(statement3);
								result_of_test = "1";
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write(result_of_test);

							} else {
								result_of_test = "1";
								resp.setContentType("text/plain");
								resp.setCharacterEncoding("UTF-8");
								resp.getWriter().write(result_of_test);

							}

						} else {
							result_of_test = "0";
							resp.setContentType("text/plain");
							resp.setCharacterEncoding("UTF-8");
							resp.getWriter().write(result_of_test);
						}

					} else {

						result_of_test = "0";
						resp.setContentType("text/plain");
						resp.setCharacterEncoding("UTF-8");
						resp.getWriter().write(result_of_test);
					}

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
