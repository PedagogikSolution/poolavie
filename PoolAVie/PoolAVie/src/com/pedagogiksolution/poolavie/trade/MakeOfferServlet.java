package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.TradeBeans;

public class MakeOfferServlet extends HttpServlet {
   
    /**
	 * 
	 */
    private static final long serialVersionUID = 763180796186293473L;

/*  ************************************  DO POST   *************************************************************/
   
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
// initialisation du metier pour les trade et des beans en meme temps
	TradeModel mClass = new TradeModel();
// methode qui verifie si le client a fais des offres de trade, retourne true et un bean representant les offres faites si positif	
	Boolean isThereAOfferMadeByMe = mClass.isThereAOfferMadeByMe(req);
// si des offres sont fait, retourne a la page make offer avec le bean pour le tableau
	if (isThereAOfferMadeByMe) {
	    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
// si pas d'offre, message de pas d'offre plus bouton faire une offre	    
	} else {
	    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
	}

    }
    
/*  ************************************  DO POST   *************************************************************/

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

	 TradeBeans mBean = new TradeBeans();
	
// initialisation du metier pour les trade et des beans en meme temps	
	TradeModel mClass = new TradeModel();
// recup�ration du parametre etape qui permet un filtrage du type d'operation voulu	
	String etape = req.getParameter("etape");
	
	switch(etape){
	
	case "getTeam" :
	    
	    String team_to_trade_string = req.getParameter("team_to_trade");
		String team_that_trade_string = (String) req.getSession().getAttribute("mTeamId");
		int team_to_trade = Integer.parseInt(team_to_trade_string);
		int team_that_trade = Integer.parseInt(team_that_trade_string);


		if (team_to_trade != team_that_trade) {

		   
		    mClass.getTheTeamsThatTrade(team_to_trade,team_that_trade,req);
		    mClass.getDraftPick(team_to_trade,team_that_trade,req);
		    req.getRequestDispatcher("/jsp/make_offer_formulaire.jsp").forward(req, resp);

		} else {
		    mBean.setEchangeAvecSoiMeme(1);
		    req.setAttribute("beanTrade", mBean);
		    req.setAttribute("erreur", true);
		    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
		}
	    
	    
	    break;
	    
	case "need_confirm" :
	    
	    String periode_echange_number_string = (String)req.getSession().getAttribute("periode_echange_number");
	    int periode_echange_number = Integer.parseInt(periode_echange_number_string);
	    
	    boolean tradeIsValid;
	    
	    switch(periode_echange_number){
	    
	    case 1: tradeIsValid = mClass.validationTradeEte(req);
	    		if(tradeIsValid){
	    		    req.getRequestDispatcher("/jsp/confirmation_offre_trade.jsp").forward(req, resp);
	    		} else {
	    		    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
	    		}		
		break;
	    case 2: tradeIsValid = mClass.validationTradeAnnee(req);
		if(tradeIsValid){
    		    req.getRequestDispatcher("/jsp/confirmation_offre_trade.jsp").forward(req, resp);
    		} else {
    		    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
    		}
		break;
	    case 3:
		tradeIsValid = mClass.validationTradeDraft(req);
		if(tradeIsValid){
    		    req.getRequestDispatcher("/jsp/confirmation_offre_trade.jsp").forward(req, resp);
    		} else {
    		    req.getRequestDispatcher("/jsp/make_offer.jsp").forward(req, resp);
    		}
		break;
	    
	    }
	    break;
	    
	case "showOffer" :

// methode permettant de recuperer les informations pour afficher les details d'une offre de trade en les placant dans un bean	    
	    mClass.showOfferNumberX(req);
	    req.getRequestDispatcher("/jsp/show_offer.jsp").forward(req, resp);
	    
	    break;
	
	case "cancelOffer":
	    
	    mClass.annulerOffre(req);
	    resp.sendRedirect("/make_offer");
	    
	    break;
	    
	
	}

	

    }

}
