package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.Signature;

public class SignatureABServlet extends HttpServlet {

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
	int mPlayerId,mSalaire,mNombreAnnee,mPosition = 0,mMoyenne;
	String sPlayerId, sSignatureType,sSalaire,sNombreAnnee,sNomJoueur,sPosition,sMoyenne;
	Boolean goodToGo = false;
	Signature mBean = new Signature();
	SignatureAB mClass = new SignatureAB();

	sSignatureType = req.getParameter("signatureAB");
	
	switch (sSignatureType) {

	case "signatureABverif":
	    
	        sSalaire = req.getParameter("salaire");		
		mSalaire = Integer.parseInt(sSalaire);
		sPlayerId = req.getParameter("player_id");		
		mPlayerId = Integer.parseInt(sPlayerId);
		sNombreAnnee = req.getParameter("nombre_annee");		
		mNombreAnnee = Integer.parseInt(sNombreAnnee);
		sNomJoueur = req.getParameter("nom_du_joueur");
		sPosition = req.getParameter("position");		
		switch (sPosition) {
		case "attaquant":
		    mPosition = 1;
		    break;
		case "defenseur":
		    mPosition = 2;
		    break;
		case "gardien":
		    mPosition = 3;
		    break;

		}
		
		
		
		
		goodToGo = mClass.verifierSiArgent(req,mSalaire,mBean);

		if (goodToGo) {
		    mBean.setCodePourMessageConfirmation(1);
		    mBean.setMontant(mSalaire);
		    mBean.setNomDuJoueur(sNomJoueur);
		    mBean.setJoueurId(mPlayerId);
		    mBean.setNombreAnnee(mNombreAnnee);
		    mBean.setPosition(mPosition);
		    req.setAttribute("information_signature", mBean);
		    req.getRequestDispatcher("/jsp/signature_ab_confirmation.jsp").forward(req, resp);

		} else {
		    mBean.setCodePourMessageConfirmation(2);
		    req.setAttribute("information_signature", mBean);
		    req.getRequestDispatcher("/jsp/signature_ab_confirmation.jsp").forward(req, resp);
		}

	
	    break;

	case "confirmationSignatureAB":
	    sPlayerId = req.getParameter("playerId");
	    sSalaire = req.getParameter("salaire");
	    sNombreAnnee = req.getParameter("nombreAnnee");
	    sPosition = req.getParameter("position");
	    mPosition = Integer.parseInt(sPosition);
	    sMoyenne = req.getParameter("moyenne");
	    mMoyenne = Integer.parseInt(sMoyenne);
	    mClass.signerCeJoueur(sPlayerId,sSalaire,sNombreAnnee,mPosition,mMoyenne,req);
	    resp.sendRedirect("/equipes");
	    break;
	    
	case "annulationSignatureAB":
	    resp.sendRedirect("/signatureAB");
	    break;

	}

    }

}
