package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class SignatureModel {
    private PlayersDao playersDao;
    
    public SignatureModel(PlayersDao playersDao) {
	this.playersDao = playersDao;
    }

    public void putPlayersThatCanBeSignInBean(HttpServletRequest req) {
	
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int teamId = mBeanUser.getTeamId();
	int poolId = mBeanUser.getPoolId();
	
	
	playersDao.getPlayersThatCanBeSign(teamId,poolId,req);
	
    }

    
    public void signatureAfterDraft(HttpServletRequest req) {
	String nombreAnneeSignature = req.getParameter("nombreAnneeSignature");
	int numberOfYear = Integer.parseInt(nombreAnneeSignature);
	String draft_player_id = req.getParameter("draft_player_id");
	String nom = req.getParameter("nom");
	String position = req.getParameter("position");
	String team = req.getParameter("team");
	String salaire = req.getParameter("salaire");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int teamId = mBeanUser.getTeamId();
	int poolId = mBeanUser.getPoolId();
	
	// A: on change la valeur des years_x dans la bdd players_x
	playersDao.signPlayerAfterDraft(teamId,poolId,draft_player_id,salaire,numberOfYear);
	
	// B: on modifie les datatstore attaquant/def/goal/
	
	// C: on modifie le datastore equipe
	   	
    }
    

}
