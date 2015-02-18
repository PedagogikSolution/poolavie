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
		
		ResultSet rs;
		/* récupérer les trades offers du DG connecté */
		
		req.setAttribute("erreur", false);
		String team_that_trade_string = (String) req.getSession().getAttribute("mTeamId");
		int team_that_trade = Integer.parseInt(team_that_trade_string);
		RecuperationMyOffer recupOffer = new RecuperationMyOffer();
		rs = recupOffer.recuperationMyOffer(team_that_trade);
		req.setAttribute("my_offer", rs);
		
		
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
		
		/* vérification si échange avec lui-même, si c'est pas le cas, on récupère les équipes,les picks et les rookies dispo pour échange */
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
		
		/* on envoie les données récupérer à la page make_offer_formulaire pour afficher les options au joueur voulant faire une échange */
		req.getRequestDispatcher("/jsp/make_offer_formulaire.jsp").forward(req, resp);
		
		/* le gars a essayé d'échanger avec lui-même, on le revoit sur la page make_offer.jsp avec valeur vrai dans l'attribut erreur ce qui aura pour
		 * effet d'afficher un message d'Erreur sur la page make_offer
		 */
		} else {
		req.setAttribute("erreur", true);
		req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
		}
		
	}
	
	

}
