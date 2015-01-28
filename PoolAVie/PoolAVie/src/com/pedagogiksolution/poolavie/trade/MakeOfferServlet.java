package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeOfferServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		/* récupérer les trades offers du DG connecté */
		
		req.setAttribute("erreur", false);
		
		
		
		req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/* récupération des deux équipes impliqués dans le trade  */
		ResultSet rs,rs2,rs3,rs4,rs5,rs6;
		String team_to_trade_string = req.getParameter("team_to_trade");		
		String team_that_trade_string = (String) req.getSession().getAttribute("mTeamId");
		int team_to_trade = Integer.parseInt(team_to_trade_string);
		int team_that_trade = Integer.parseInt(team_that_trade_string);
		
		if(team_to_trade!=team_that_trade) {
		
		RecuperationTeamForExchange recupTeam = new RecuperationTeamForExchange();
		rs = recupTeam.recuperationTeamThatTrade(team_that_trade);
		rs2 = recupTeam.recuperationTeamThatTrade(team_to_trade);
		
		rs3 = recupTeam.recuperationPick(team_that_trade);
		rs4 = recupTeam.recuperationPick(team_to_trade);
		
		rs5 = recupTeam.recuperationRookieThatTrade(team_that_trade);
		rs6 = recupTeam.recuperationRookieThatTrade(team_to_trade);
		
		req.setAttribute("team_that_trade", rs);
		req.setAttribute("team_to_trade", rs2);
		req.setAttribute("pick_team_that_trade", rs3);
		req.setAttribute("pick_team_to_trade", rs4);
		req.setAttribute("rookie_team_that_trade", rs5);
		req.setAttribute("rookie_team_to_trade", rs6);
		
		req.getRequestDispatcher("/jsp/make_offer_formulaire.jsp").forward(req, resp);
		
		} else {
		req.setAttribute("erreur", true);
		req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
		}
		
	}
	
	

}
