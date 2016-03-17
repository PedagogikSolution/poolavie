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
	int mPlayerId,nbAnnee;
	String sPlayerId, sRachatType,salaire;
	Boolean goodToGo = false;
	Signature mBean = new Signature();
	String nb_annee;
	RookieApresSaison mClass;

	sRachatType = req.getParameter("rookieFin_type");
	
	switch (sRachatType) {
	
	case "dropRookie" :
	    
	    sPlayerId = req.getParameter("player_id");
	    if (sPlayerId != null) {
		mPlayerId = Integer.parseInt(sPlayerId);
		mClass = new RookieApresSaison();
		mClass.dropperRookie(mPlayerId,req);
		resp.sendRedirect("/equipes");
	    }
	    
	    break;
	    
	case "monterRookieVerif":
	    sPlayerId = req.getParameter("player_id");
	    nb_annee = req.getParameter("nombre_annee");
	    salaire = req.getParameter("salaire");
	    
	    if(nb_annee != null){
		nbAnnee=Integer.parseInt(nb_annee);
		if (sPlayerId != null) {
			mPlayerId = Integer.parseInt(sPlayerId);
			mClass = new RookieApresSaison();
			goodToGo = mClass.monterRookieVerifArgentContrat(mPlayerId,nbAnnee,salaire,req,mBean);
			
			if(goodToGo){
			    mBean.setJoueurId(mPlayerId);
			    mBean.setNombreAnnee(nbAnnee);
			    req.setAttribute("monterRookie", mBean);
			    req.getRequestDispatcher("/jsp/signature/rookie_confirmation_monter.jsp").forward(req, resp);}
			 
			else {
			    mBean.setCodePourMessageConfirmation(2);
			    req.setAttribute("codeErreur", mBean);
			    req.getRequestDispatcher("/jsp/signature/dropRookie.jsp").forward(req, resp); 
			 
			}
		    }
		
	    } else {
	    mClass = new RookieApresSaison();
	    mClass.preparationRookieApresSaison2(req);
	    mBean.setCodePourMessageConfirmation(1);
	    req.setAttribute("codeErreur", mBean);
	    req.getRequestDispatcher("/jsp/signature/drop_rookie.jsp").forward(req, resp);
	    }
	    
	    break;

	case "verifRookieFin":
	    sPlayerId = req.getParameter("player_id");
	    if (sPlayerId != null) {
		mPlayerId = Integer.parseInt(sPlayerId);
		mClass = new RookieApresSaison();
		goodToGo = mClass.verifierSiArgent(req,mPlayerId,mBean);

		if (goodToGo) {
		    mBean.setCodePourMessageConfirmation(1);
		    mBean.setJoueurId(mPlayerId);
		    req.setAttribute("codeConfirmation", mBean);
		    req.getRequestDispatcher("/jsp/signature/rookie_fin_confirmation.jsp").forward(req, resp);

		} else {
		    mBean.setCodePourMessageConfirmation(2);
		    req.setAttribute("codeConfirmation", mBean);
		    req.getRequestDispatcher("/jsp/signature/rookie_fin_confirmation.jsp").forward(req, resp);
		}

	    } else {
		mBean.setCodePourMessageConfirmation(3);
		req.setAttribute("codeConfirmation", mBean);
		req.getRequestDispatcher("/jsp/signature/rookie_fin_confirmation.jsp").forward(req, resp);
	    }
	    break;

	case "confirmationRookieFin":
	    String mPlayerIdForRachat = req.getParameter("playerId");
	    mClass = new RookieApresSaison();
	    mClass.descendreRookie(mPlayerIdForRachat,req);
	    resp.sendRedirect("/equipes");
	    break;
	    
	case "annulationRookieFin":
	    resp.sendRedirect("/rookieFin");
	    break;
	    
	case "monter":
	    sPlayerId = req.getParameter("player_id");
	    nb_annee = req.getParameter("nombre_annee");
	    salaire = req.getParameter("salaire");
	    mClass = new RookieApresSaison();
	    mClass.monterRookie(sPlayerId, nb_annee, salaire,req);
	    resp.sendRedirect("/equipes");
	    
	    break;
	    
	case "nePasMonter":
	    resp.sendRedirect("/rookieFin");
	    break;

	}

    }

}
