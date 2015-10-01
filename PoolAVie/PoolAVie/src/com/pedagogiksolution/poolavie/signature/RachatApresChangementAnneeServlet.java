package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.Signature;

public class RachatApresChangementAnneeServlet extends HttpServlet {
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
		RachatApresChangementAnnee mClass = new RachatApresChangementAnnee();
		goodToGo = mClass.verifierSiArgent(req,mPlayerId,mBean);

		if (goodToGo) {
		    mBean.setCodePourMessageConfirmation(1);
		    mBean.setJoueurId(mPlayerId);
		    req.setAttribute("codeConfirmation", mBean);
		    req.getRequestDispatcher("/jsp/rachat2_confirmation.jsp").forward(req, resp);

		} else {
		    mBean.setCodePourMessageConfirmation(2);
		    req.setAttribute("codeConfirmation", mBean);
		    req.getRequestDispatcher("/jsp/rachat2_confirmation.jsp").forward(req, resp);
		}

	    } else {
		mBean.setCodePourMessageConfirmation(3);
		req.setAttribute("codeConfirmation", mBean);
		req.getRequestDispatcher("/jsp/rachat2_confirmation.jsp").forward(req, resp);
	    }
	    break;

	case "confirmationRachat":
	    String mPlayerIdForRachat = req.getParameter("playerId");
	    String mCoutForRachat = req.getParameter("coutPourRachat");
	    String mPosition = req.getParameter("position");
	    String salaire = req.getParameter("salaire");
	    int position = Integer.parseInt(mPosition);
	    RachatApresChangementAnnee mClass = new RachatApresChangementAnnee();
	    mClass.racheterCeJoueur(mPlayerIdForRachat,mCoutForRachat,position,salaire,req);
	    resp.sendRedirect("/equipes");
	    break;
	    
	case "annulationRachat":
	    resp.sendRedirect("/rachat");
	    break;

	}

    }

}
