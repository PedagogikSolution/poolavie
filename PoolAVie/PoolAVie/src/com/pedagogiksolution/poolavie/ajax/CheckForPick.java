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
	String result_of_test, first_result, second_result, team_id;
	int team_id2;
	String mTeamID;
	Connection conn;
	DatabaseConnector dbHelper;
	ResultSet rs;
	String statement, statement2, statement3;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2732125333743563410L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		team_id = (String) req.getSession().getAttribute("mTeamId");

		team_id2 = Integer.parseInt(team_id);

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
			mTeamID = "team_0";
			break;
		case 9:
			mTeamID = "team_9";
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
						first_result = rs.getString("reload");
						second_result = rs.getString(mTeamID);
					}

					if (second_result.equals("0")) {

						if (first_result.equals("1")) {

							statement2 = "UPDATE reload_counter SET counter = counter+1,"
									+ mTeamID + "=1 WHERE _id=1";
							conn.createStatement().executeUpdate(statement2);

							int counterCount2 = rs.getInt("counter");
							int counterCount = counterCount2 + 1;

							if (counterCount == 10) {

								statement3 = "UPDATE reload_counter SET counter = 0 AND reload=0 WHERE _id=1";
								conn.createStatement()
										.executeUpdate(statement3);
								result_of_test = "1";

							} else {
								result_of_test = "1";

							}

						} else {
							result_of_test = "0";
						}

					} else {

						result_of_test = "0";
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

		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(result_of_test);

	}

}
