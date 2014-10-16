package com.pedagogiksolution.poolavie.draft;

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

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class DraftOrderServlet extends HttpServlet {
	DatabaseConnector dbHelper;
	String statement;
	Connection conn;
	ResultSet rs;
	/**
	 * 
	 */
	private static final long serialVersionUID = -2610473599214661436L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		List<Object> dataList = new ArrayList<Object>();
		// connexion aux serveurs4 de base de donnée
					dbHelper = new DatabaseConnector();
					conn = dbHelper.open();
					// si connexion est bonne on récupère les informations de la table
					// utilisateur
					if (conn != null) {
						
					try {	
						
						try {
							/* récupération des informations selon le identifiant equipe */
							statement = "SELECT * FROM draft_round";
							
							rs = conn.createStatement().executeQuery(statement);
							while (rs.next()) {
								dataList.add(rs.getInt("draft_pick_no"));								
								dataList.add(rs.getInt("ronde"));
								dataList.add( rs.getString("equipe"));
								dataList.add(rs.getString("from"));
								dataList.add(rs.getString("player_drafted"));
								
								
							}
							
							req.setAttribute("dataDraftOrder", dataList);
								
								req.getRequestDispatcher("/jsp/draft_order.jsp").forward(
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
