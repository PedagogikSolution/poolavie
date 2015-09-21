package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.Signature;

public class RookieApresSaisonServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -54183488103045269L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	req.getRequestDispatcher("/signature").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	int mPlayerId;
	String sPlayerId, sRachatType;
	Boolean goodToGo = false;
	Signature mBean = new Signature();

	sRachatType = req.getParameter("rookieFin_type");
	
	switch (sRachatType) {

	case "verifRookieFin":
	    sPlayerId = req.getParameter("player_id");
	    if (sPlayerId != null) {
		mPlayerId = Integer.parseInt(sPlayerId);
		RookieApresSaison mClass = new RookieApresSaison();
		goodToGo = mClass.verifierSiArgent(req,mPlayerId,mBean);

		if (goodToGo) {
		    mBean.setCodePourMessageConfirmation(1);
		    mBean.setJoueurId(mPlayerId);
		    req.setAttribute("codeConfirmation", mBean);
		    req.getRequestDispatcher("/jsp/rookie_fin_confirmation.jsp").forward(req, resp);

		} else {
		    mBean.setCodePourMessageConfirmation(2);
		    req.setAttribute("codeConfirmation", mBean);
		    req.getRequestDispatcher("/jsp/rookie_fin_confirmation.jsp").forward(req, resp);
		}

	    } else {
		mBean.setCodePourMessageConfirmation(3);
		req.setAttribute("codeConfirmation", mBean);
		req.getRequestDispatcher("/jsp/rookie_fin_confirmation.jsp").forward(req, resp);
	    }
	    break;

	case "confirmationRookieFin":
	    String mPlayerIdForRachat = req.getParameter("playerId");
	    RookieApresSaison mClass = new RookieApresSaison();
	    mClass.descendreRookie(mPlayerIdForRachat,req);
	    resp.sendRedirect("/equipes");
	    break;
	    
	case "annulationRookieFin":
	    resp.sendRedirect("/rookieFin");
	    break;

	}

    }

}
