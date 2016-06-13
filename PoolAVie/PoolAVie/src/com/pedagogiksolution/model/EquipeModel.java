package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.beans.NonSessionDraftPick;
import com.pedagogiksolution.beans.NonSessionEquipe;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class EquipeModel {

    public void getBeanByTeam(HttpServletRequest req) {
	String draftPickName = null;
	String equipeName = null;
	String s_teamNumberToShow = (String) req.getParameter("team");
	
	if(s_teamNumberToShow==null){
	    Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	    int teamNumberToShow = mBeanUser.getTeamId(); 
	    draftPickName = "DraftPick" + teamNumberToShow;
	    equipeName = "Equipe"+teamNumberToShow;
	}else{
	    draftPickName = "DraftPick" + s_teamNumberToShow;
	    equipeName = "Equipe"+s_teamNumberToShow;
	}
	
	Equipe mBeanEquipe = (Equipe) req.getSession().getAttribute(equipeName);
	NonSessionEquipe mNonSessionEquipeBean = new NonSessionEquipe();
	
	DraftPick mBeanDraftPick = (DraftPick) req.getSession().getAttribute(draftPickName);
	NonSessionDraftPick mNonSessionDraftBean = new NonSessionDraftPick();
	
	
	// Equipe 
	mNonSessionEquipeBean.setArgent_recu(mBeanEquipe.getArgent_recu());
	mNonSessionEquipeBean.setBonus_5m(mBeanEquipe.getBonus_5m());
	mNonSessionEquipeBean.setBonus_penalite(mBeanEquipe.getBonus_penalite());
	mNonSessionEquipeBean.setBudget_restant(mBeanEquipe.getBudget_restant());
	mNonSessionEquipeBean.setClassement_last_year(mBeanEquipe.getClassement_last_year());
	mNonSessionEquipeBean.setManquant_att(mBeanEquipe.getManquant_att());
	mNonSessionEquipeBean.setManquant_def(mBeanEquipe.getManquant_def());
	mNonSessionEquipeBean.setManquant_gardien(mBeanEquipe.getManquant_gardien());
	mNonSessionEquipeBean.setManquant_recrue(mBeanEquipe.getManquant_recrue());
	mNonSessionEquipeBean.setManquant_equipe(mBeanEquipe.getManquant_equipe());
	mNonSessionEquipeBean.setMax_salaire_begin(mBeanEquipe.getMax_salaire_begin());
	mNonSessionEquipeBean.setMeilleur_classement(mBeanEquipe.getMeilleur_classement());
	mNonSessionEquipeBean.setMoy_sal_restant_draft(mBeanEquipe.getMoy_sal_restant_draft());
	mNonSessionEquipeBean.setNb_attaquant(mBeanEquipe.getNb_attaquant());
	mNonSessionEquipeBean.setNb_defenseur(mBeanEquipe.getNb_defenseur());
	mNonSessionEquipeBean.setNb_gardien(mBeanEquipe.getNb_gardien());
	mNonSessionEquipeBean.setNb_rookie(mBeanEquipe.getNb_rookie());
	mNonSessionEquipeBean.setNb_equipe(mBeanEquipe.getNb_equipe());
	mNonSessionEquipeBean.setNum_annee(mBeanEquipe.getNum_annee());
	mNonSessionEquipeBean.setNum_champion(mBeanEquipe.getNum_champion());
	mNonSessionEquipeBean.setPoolId(mBeanEquipe.getPoolId());
	mNonSessionEquipeBean.setPoolTeamId(mBeanEquipe.getPoolTeamId());
	mNonSessionEquipeBean.setTeamId(mBeanEquipe.getTeamId());
	mNonSessionEquipeBean.setTotal_salaire_now(mBeanEquipe.getTotal_salaire_now());
		
	// Draft
	mNonSessionDraftBean.setPick_no(mBeanDraftPick.getPick_no());
	mNonSessionDraftBean.setTeamNameOriginalPick(mBeanDraftPick.getTeamNameOriginalPick());
	
	req.setAttribute("NonSessionEquipe", mNonSessionEquipeBean);
	req.setAttribute("NonSessionDraftPick", mNonSessionDraftBean);
	
    }

}
