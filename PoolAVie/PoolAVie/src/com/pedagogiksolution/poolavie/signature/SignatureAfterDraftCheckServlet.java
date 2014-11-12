package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class SignatureAfterDraftCheckServlet extends HttpServlet {
	DatabaseConnector dbHelper;
	Connection conn;
	String nom,salaire, player_id;


	String statement1, statement2, statement3;
	int team_id;
	ResultSet rs1, rs2, rs3;
	String team_id_temp;
	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		nom = req.getParameter("nom");
		salaire = req.getParameter("salaire");
		player_id = req.getParameter("player_id");
			
		req.setAttribute("nom", nom);
		req.setAttribute("salaire", salaire);
		req.setAttribute("player_id", player_id);
		
		req.getRequestDispatcher("/jsp/signature_after_draft_confirmation.jsp").forward(req, resp);

	}

}
