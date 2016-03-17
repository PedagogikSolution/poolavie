package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailOffreTradeReceivedServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ResultSet rs,rs2,rs3,rs4,rs5;
		String cash_team_1 = null,cash_team_2 = null, team_2=null;
		
		String trade_id_2=req.getParameter("trade_id");
		int trade_id = Integer.parseInt(trade_id_2);
		RecuperationMyOffer recupOffer = new RecuperationMyOffer();
		rs = recupOffer.recuperationMyOfferPrecis(trade_id);
		
		ShowDetailOffer mShowDetail= new ShowDetailOffer();
		//COntient le resultats pour afficher les joueurs impliquer du team1
		rs2 = mShowDetail.recupPlayerTeam1(rs);
		//COntient le resultats pour afficher les joueurs impliquer du team2
		rs3 = mShowDetail.recupPlayerTeam2(rs);
		
		try {
			if(rs.first()){
			cash_team_1 = rs.getString("t1_cash");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			if(rs.first()){
			cash_team_2 = rs.getString("t2_cash");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(rs.first()){
			team_2 = rs.getString("team_2");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		rs4 = mShowDetail.recupPickTeam1(rs);		
		
		rs5 = mShowDetail.recupPickTeam2(rs);
		
		req.setAttribute("team_2", team_2);
		req.setAttribute("cash_team_1", cash_team_1);
		req.setAttribute("cash_team_2", cash_team_2);
		req.setAttribute("player_team_1", rs2);
		req.setAttribute("player_team_2", rs3);
		if(rs4!=null){
		req.setAttribute("pick_team_1", rs4);}
		if(rs5!=null){
		req.setAttribute("pick_team_2", rs5);}
		
		
		
		
		req.getRequestDispatcher("/jsp/trade/show_offer_received.jsp").forward(req, resp);
	}

}
