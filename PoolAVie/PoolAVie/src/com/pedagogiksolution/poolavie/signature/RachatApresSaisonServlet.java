package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.Signature;

public class RachatApresSaisonServlet extends HttpServlet {
    Signature mBean = new Signature();
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
	

	sRachatType = req.getParameter("rachat_type");
	
	switch (sRachatType) {

	case "verifRachat":
	    sPlayerId = req.getParameter("player_id");
	    if (sPlayerId != null) {
		mPlayerId = Integer.parseInt(sPlayerId);
		RachatApresSaison mClass = new RachatApresSaison();
		goodToGo = mClass.verifierSiArgent(req,mPlayerId,mBean);

		if (goodToGo) {
		    mBean.setCodePourMessageConfirmation(1);
		    mBean.setJoueurId(mPlayerId);
		    req.setAttribute("codeConfirmation", mBean);
		    req.getRequestDispatcher("/jsp/signature/rachat_confirmation.jsp").forward(req, resp);

		} else {
		    mBean.setCodePourMessageConfirmation(2);
		    req.setAttribute("codeConfirmation", mBean);
		    req.getRequestDispatcher("/jsp/signature/rachat_confirmation.jsp").forward(req, resp);
		}

	    } else {
		mBean.setCodePourMessageConfirmation(3);
		req.setAttribute("codeConfirmation", mBean);
		req.getRequestDispatcher("/jsp/signature/rachat_confirmation.jsp").forward(req, resp);
	    }
	    break;

	case "confirmationRachat":
	    String mPlayerIdForRachat = req.getParameter("playerId");
	    String mCoutForRachat = req.getParameter("coutPourRachat");
	    RachatApresSaison mClass = new RachatApresSaison();
	    mClass.racheterCeJoueur(mPlayerIdForRachat,mCoutForRachat,req);
	    resp.sendRedirect("/equipes");
	    break;
	    
	case "annulationRachat":
	    resp.sendRedirect("/rachat");
	    break;

	}

    }

}
