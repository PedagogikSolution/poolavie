package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmOfferServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;
	String[] player_trade_by,pick_trade_by,player_trade_for,pick_trade_for;
	int number_of_player_trade_by,number_of_pick_trade_by,number_of_player_trade_for,number_of_pick_trade_for;
	String t1j1, t1j2, t1j3, t1j4, t1j5, t1j6, t1j7, t2j1, t2j2, t2j3, t2j4,
			t2j5, t2j6, t2j7, t1p1, t1p2, t1p3,t2p1, t2p2, t2p3;
	
	String cash_by, cash_for;
	String team_id_1;
	String team_id_2;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// numéro de l'équipe qui trade
		team_id_1 = req.getParameter("team_id_1");
		// numéro de l'équipe avec qui le trade est proposé
		team_id_2 = req.getParameter("team_id_2");
		
		
		récupérationDuPost(req);
		
		PersitenceTradeOffer p = new PersitenceTradeOffer();
		p.persistOffer(team_id_1, team_id_2, t1j1, t1j2, t1j3, t1j4, t1j5, t1j6, t1j7, t2j1, t2j2, t2j3, t2j4,
				t2j5, t2j6, t2j7, t1p1, t1p2, t1p3,t2p1, t2p2, t2p3,cash_by, cash_for  );

	//	EvaluationPreliminaireTrade evaluatePreTrade = new EvaluationPreliminaireTrade();

	//	boolean evaluationResult = evaluatePreTrade.evaluationResult(team1,
	//			team2, player_trade_by, cash_trade_by, pick_trade_by,
	//			player_trade_for, cash_trade_for, pick_trade_for);

	//	if (evaluationResult == false) {

			// PersitenceTradeOffer p = new PersitenceTradeOffer();
			// p.persistOffer(team1, team2, player_trade_by, cash_trade_by,
			// pick_trade_by, player_trade_for, cash_trade_for, pick_trade_for);

		resp.sendRedirect("/make_offer");

	//	} else {

	//		req.getRequestDispatcher("/jsp/trade_offer_reject.jsp").forward(
	//				req, resp);

	//	}

	}
	
	
	private void récupérationDuPost(HttpServletRequest req){
		/* recupération des joueurs de l'équipe qui trade */
		boolean check_if_team1_trade_a_player = req
				.getParameter("player_trade_by") != null;
		if (check_if_team1_trade_a_player) {
			player_trade_by = req.getParameterValues("player_trade_by");
			number_of_player_trade_by = player_trade_by.length;
			
			switch (number_of_player_trade_by) {
			case 1:
				t1j1 = player_trade_by[0];
				t1j2 = null;
				t1j3 = null;
				t1j4 = null;
				t1j5 = null;
				t1j6 = null;
				t1j7 = null;
				
				break;
			case 2:
				t1j1 = player_trade_by[0];
				t1j2 = player_trade_by[1];
				t1j3 = null;
				t1j4 = null;
				t1j5 = null;
				t1j6 = null;
				t1j7 = null;
				
				break;
			case 3:
				t1j1 = player_trade_by[0];
				t1j2 = player_trade_by[1];
				t1j3 = player_trade_by[2];
				t1j4 = null;
				t1j5 = null;
				t1j6 = null;
				t1j7 = null;

				break;
			case 4:
				t1j1 = player_trade_by[0];
				t1j2 = player_trade_by[1];
				t1j3 = player_trade_by[2];
				t1j4 = player_trade_by[3];
				t1j5 = null;
				t1j6 = null;
				t1j7 = null;

				break;
			case 5:
				t1j1 = player_trade_by[0];
				t1j2 = player_trade_by[1];
				t1j3 = player_trade_by[2];
				t1j4 = player_trade_by[3];
				t1j5 = player_trade_by[4];
				t1j6 = null;
				t1j7 = null;

				break;
			case 6:
				t1j1 = player_trade_by[0];
				t1j2 = player_trade_by[1];
				t1j3 = player_trade_by[2];
				t1j4 = player_trade_by[3];
				t1j5 = player_trade_by[4];
				t1j6 = player_trade_by[5];
				t1j7 = null;

				break;
			case 7:
				t1j1 = player_trade_by[0];
				t1j2 = player_trade_by[1];
				t1j3 = player_trade_by[2];
				t1j4 = player_trade_by[3];
				t1j5 = player_trade_by[4];
				t1j6 = player_trade_by[5];
				t1j7 = player_trade_by[6];
				break;

			}

		}
		/* recupération du montant de l'équipe qui trade */
		cash_by = req.getParameter("cash_trade_by");
		
		/* recupération des picks de l'équipe qui trade */
		boolean check_if_team1_trade_a_pick = req
				.getParameter("pick_trade_by") != null;
		if (check_if_team1_trade_a_pick) {
			pick_trade_by = req.getParameterValues("pick_trade_by");
			number_of_pick_trade_by = pick_trade_by.length;
			
			switch (number_of_pick_trade_by) {
			case 1:
				t1p1 = pick_trade_by[0];
				t1p2 = null;
				t1p3 = null;
				
				
				break;
			case 2:
				t1p1 = pick_trade_by[0];
				t1p2 = pick_trade_by[1];
				t1p3 = null;				
				
				break;
			case 3:
				t1p1 = pick_trade_by[0];
				t1p2 = pick_trade_by[1];
				t1p3 = pick_trade_by[2];

				break;
		
			}

		} else {
			t1p1 = null;
			t1p2 = null;
			t1p3 = null;
		}
		
		/* recupération des joueurs de l'équipe avec qui le joueur veut trader */
		boolean check_if_team2_trade_a_player = req.getParameter("player_trade_for") != null;
		if (check_if_team2_trade_a_player) {
			player_trade_for = req.getParameterValues("player_trade_for");
			number_of_player_trade_for = player_trade_for.length;
			
			switch (number_of_player_trade_for) {
			case 1:
				t2j1 = player_trade_for[0];
				t2j2 = null;
				t2j3 = null;
				t2j4 = null;
				t2j5 = null;
				t2j6 = null;
				t2j7 = null;
				
				break;
			case 2:
				t2j1 = player_trade_for[0];
				t2j2 = player_trade_for[1];
				t2j3 = null;
				t2j4 = null;
				t2j5 = null;
				t2j6 = null;
				t2j7 = null;
				
				break;
			case 3:
				t2j1 = player_trade_for[0];
				t2j2 = player_trade_for[1];
				t2j3 = player_trade_for[2];
				t2j4 = null;
				t2j5 = null;
				t2j6 = null;
				t2j7 = null;

				break;
			case 4:
				t2j1 = player_trade_for[0];
				t2j2 = player_trade_for[1];
				t2j3 = player_trade_for[2];
				t2j4 = player_trade_for[3];
				t2j5 = null;
				t2j6 = null;
				t2j7 = null;

				break;
			case 5:
				t2j1 = player_trade_for[0];
				t2j2 = player_trade_for[1];
				t2j3 = player_trade_for[2];
				t2j4 = player_trade_for[3];
				t2j5 = player_trade_for[4];
				t2j6 = null;
				t2j7 = null;

				break;
			case 6:
				t2j1 = player_trade_for[0];
				t2j2 = player_trade_for[1];
				t2j3 = player_trade_for[2];
				t2j4 = player_trade_for[3];
				t2j5 = player_trade_for[4];
				t2j6 = player_trade_for[5];
				t2j7 = null;

				break;
			case 7:
				t2j1 = player_trade_for[0];
				t2j2 = player_trade_for[1];
				t2j3 = player_trade_for[2];
				t2j4 = player_trade_for[3];
				t2j5 = player_trade_for[4];
				t2j6 = player_trade_for[5];
				t2j7 = player_trade_for[6];
				break;

			}

		}
		/* recupération du montant de l'équipe qui trade */
		cash_for = req.getParameter("cash_trade_for");
		
		/* recupération des picks de l'équipe qui trade */
		boolean check_if_team2_trade_a_pick = req
				.getParameter("pick_trade_for") != null;
		if (check_if_team2_trade_a_pick) {
			pick_trade_for = req.getParameterValues("pick_trade_for");
			number_of_pick_trade_for = pick_trade_for.length;
			
			switch (number_of_pick_trade_by) {
			case 1:
				t2p1 = pick_trade_for[0];
				t2p2 = null;
				t2p3 = null;
				
				
				break;
			case 2:
				t2p1 = pick_trade_for[0];
				t2p2 = pick_trade_for[1];
				t2p3 = null;
				
				
				break;
			case 3:
				t2p1 = pick_trade_for[0];
				t2p2 = pick_trade_for[1];
				t2p3 = pick_trade_for[2];
				

				break;
		
			}

		} else {
			t2p1 = null;
			t2p2 = null;
			t2p3 = null;
		}
		
	}

}
