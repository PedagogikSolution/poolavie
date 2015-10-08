package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.Signature;

public class SignatureAfterDraftCheckServlet extends HttpServlet {

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
	int mPlayerId,mSalaire,mNombreAnnee,mPosition = 0;
	String sPlayerId, sSignatureType,sSalaire,sNombreAnnee,sNomJoueur,sPosition;
	Boolean goodToGo = false;
	Signature mBean = new Signature();
	SignatureAfterDraft mClass = new SignatureAfterDraft();

	sSignatureType = req.getParameter("signatureAfterDraft");
	
	switch (sSignatureType) {

	case "signatureADverif":
	    
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
		
		
		
		
		goodToGo = mClass.verifierSiPlaceDispo(req,mSalaire,mBean);

		if (goodToGo) {
		    mBean.setCodePourMessageConfirmation(1);
		    mBean.setMontant(mSalaire);
		    mBean.setNomDuJoueur(sNomJoueur);
		    mBean.setJoueurId(mPlayerId);
		    mBean.setNombreAnnee(mNombreAnnee);
		    mBean.setPosition(mPosition);
		    req.setAttribute("information_signature", mBean);
		    req.getRequestDispatcher("/jsp/signature_after_draft_confirmation.jsp").forward(req, resp);

		} else {
		    mBean.setCodePourMessageConfirmation(2);
		    req.setAttribute("information_signature", mBean);
		    req.getRequestDispatcher("/jsp/signature_after_draft_confirmation.jsp").forward(req, resp);
		}

	
	    break;

	case "confirmationSignatureAD":
	    sPlayerId = req.getParameter("playerId");
	    sSalaire = req.getParameter("salaire");
	    sNombreAnnee = req.getParameter("nombreAnnee");
	    sPosition = req.getParameter("position");
	    mPosition = Integer.parseInt(sPosition);
	    mClass.signerCeJoueur(sPlayerId,sSalaire,sNombreAnnee,mPosition,req,resp);
	    resp.sendRedirect("/equipes");
	    break;
	    
	case "annulationSignatureAD":
	    resp.sendRedirect("/signature");
	    break;

	}

    }

}
