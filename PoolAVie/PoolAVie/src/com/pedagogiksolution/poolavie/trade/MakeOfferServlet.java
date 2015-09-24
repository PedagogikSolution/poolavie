package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.TradeBeans;

public class MakeOfferServlet extends HttpServlet {
    TradeBeans mBean = new TradeBeans();
    /**
	 * 
	 */
    private static final long serialVersionUID = 763180796186293473L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

	TradeModel mClass = new TradeModel();
	Boolean isThereAOfferMadeByMe = mClass.isThereAOfferMadeByMe();

	if (isThereAOfferMadeByMe) {
	    mClass.getTheOfferThatIMade(req);
	    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
	} else {
	    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
	}

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	/* récupération des deux équipes impliqués dans le trade */

	String team_to_trade_string = req.getParameter("team_to_trade");
	String team_that_trade_string = (String) req.getSession().getAttribute("mTeamId");
	int team_to_trade = Integer.parseInt(team_to_trade_string);
	int team_that_trade = Integer.parseInt(team_that_trade_string);


	if (team_to_trade != team_that_trade) {

	    TradeModel mClass = new TradeModel();
	    mClass.getTheTeamsThatTrade(team_to_trade,team_that_trade,req);
	    req.getRequestDispatcher("/jsp/make_offer_formulaire.jsp").forward(req, resp);

	} else {
	    mBean.setEchangeAvecSoiMeme(1);
	    req.setAttribute("beanTrade", mBean);
	    req.setAttribute("erreur", true);
	    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
	}

    }

}
