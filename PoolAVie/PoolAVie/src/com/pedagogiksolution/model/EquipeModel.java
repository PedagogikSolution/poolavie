package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.beans.NonSessionAttaquant;
import com.pedagogiksolution.beans.NonSessionDefenseur;
import com.pedagogiksolution.beans.NonSessionDraftPick;
import com.pedagogiksolution.beans.NonSessionEquipe;
import com.pedagogiksolution.beans.NonSessionGardien;
import com.pedagogiksolution.beans.NonSessionRecrue;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class EquipeModel {

    public void getBeanByTeam(HttpServletRequest req) {
	String draftPickName = null;
	String equipeName = null;
	String s_teamNumberToShow = (String) req.getParameter("team");
	String attaquantName=null;
	String defenseurName=null;
	String gardienName=null;
	String recrueName=null;
	
	if(s_teamNumberToShow==null){
	    Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	    int teamNumberToShow = mBeanUser.getTeamId(); 
	    draftPickName = "DraftPick" + teamNumberToShow;
	    equipeName = "Equipe"+teamNumberToShow;
	    attaquantName =  "Attaquant"+teamNumberToShow;
	    defenseurName =  "Defenseur"+teamNumberToShow;
	    gardienName =  "Gardien"+teamNumberToShow;
	    recrueName =  "Recrue"+teamNumberToShow;
	    
	}else{
	    draftPickName = "DraftPick" + s_teamNumberToShow;
	    equipeName = "Equipe"+s_teamNumberToShow;
	    attaquantName =  "Attaquant"+s_teamNumberToShow;
	    defenseurName =  "Defenseur"+s_teamNumberToShow;
	    gardienName =  "Gardien"+s_teamNumberToShow;
	    recrueName =  "Recrue"+s_teamNumberToShow;
	}
	
	Equipe mBeanEquipe = (Equipe) req.getSession().getAttribute(equipeName);
	NonSessionEquipe mNonSessionEquipeBean = new NonSessionEquipe();
	
	DraftPick mBeanDraftPick = (DraftPick) req.getSession().getAttribute(draftPickName);
	NonSessionDraftPick mNonSessionDraftBean = new NonSessionDraftPick();
	
	Attaquant mBeanAttaquant = (Attaquant) req.getSession().getAttribute(attaquantName);
	NonSessionAttaquant mNonSessionAttaquantBean = new NonSessionAttaquant();
	
	Defenseur mBeanDefenseur = (Defenseur) req.getSession().getAttribute(defenseurName);
	NonSessionDefenseur mNonSessionDefenseurBean = new NonSessionDefenseur();
	
	Gardien mBeanGardien = (Gardien) req.getSession().getAttribute(gardienName);
	NonSessionGardien mNonSessionGardienBean = new NonSessionGardien();
	
	Recrue mBeanRecrue = (Recrue) req.getSession().getAttribute(recrueName);
	NonSessionRecrue mNonSessionRecrueBean = new NonSessionRecrue();
	
	
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
	
	//Attaquant
	    mNonSessionAttaquantBean.setAge(mBeanAttaquant.getAge());
	    mNonSessionAttaquantBean.setAide_overtime(mBeanAttaquant.getAide_overtime());
	    mNonSessionAttaquantBean.setBirthday(mBeanAttaquant.getBirthday());
	    mNonSessionAttaquantBean.setBlanchissage(mBeanAttaquant.getBlanchissage());
	    mNonSessionAttaquantBean.setBut_victoire(mBeanAttaquant.getBut_victoire());
	    mNonSessionAttaquantBean.setCan_be_rookie(mBeanAttaquant.getCan_be_rookie());
	    mNonSessionAttaquantBean.setClub_ecole(mBeanAttaquant.getClub_ecole());
	    mNonSessionAttaquantBean.setContrat(mBeanAttaquant.getContrat());
	    mNonSessionAttaquantBean.setContrat_cours(mBeanAttaquant.getContrat_cours());
	    mNonSessionAttaquantBean.setContrat_max_years(mBeanAttaquant.getContrat_max_years());
	    mNonSessionAttaquantBean.setDate_calcul(mBeanAttaquant.getDate_calcul());
	    mNonSessionAttaquantBean.setEquipe(mBeanAttaquant.getEquipe());
	    mNonSessionAttaquantBean.setHier(mBeanAttaquant.getHier());
	    mNonSessionAttaquantBean.setMois(mBeanAttaquant.getMois());
	    mNonSessionAttaquantBean.setNom(mBeanAttaquant.getNom());
	    mNonSessionAttaquantBean.setPj(mBeanAttaquant.getPj());
	    mNonSessionAttaquantBean.setPosition(mBeanAttaquant.getPosition());
	    mNonSessionAttaquantBean.setProjection(mBeanAttaquant.getProjection());
	    mNonSessionAttaquantBean.setPts(mBeanAttaquant.getPts());
	    mNonSessionAttaquantBean.setSalaire_contrat(mBeanAttaquant.getSalaire_contrat());
	    mNonSessionAttaquantBean.setSalaire_draft(mBeanAttaquant.getSalaire_draft());
	    mNonSessionAttaquantBean.setSemaine(mBeanAttaquant.getSemaine());
	    mNonSessionAttaquantBean.setTake_proj(mBeanAttaquant.getTake_proj());
	    mNonSessionAttaquantBean.setTeam_id(mBeanAttaquant.getTeam_id());
	    mNonSessionAttaquantBean.setTeam_was_update(mBeanAttaquant.getTeam_was_update());
	    mNonSessionAttaquantBean.setTeamOfPlayer(mBeanAttaquant.getTeamOfPlayer());
	    mNonSessionAttaquantBean.setType_contrat(mBeanAttaquant.getType_contrat());
	    mNonSessionAttaquantBean.setYears_1(mBeanAttaquant.getYears_1());
	    mNonSessionAttaquantBean.setYears_2(mBeanAttaquant.getYears_2());
	    mNonSessionAttaquantBean.setYears_3(mBeanAttaquant.getYears_3());
	    mNonSessionAttaquantBean.setYears_4(mBeanAttaquant.getYears_4());
	    mNonSessionAttaquantBean.setYears_5(mBeanAttaquant.getYears_5());
	    
	  //Defenseur
	    mNonSessionDefenseurBean.setAge(mBeanDefenseur.getAge());
	    mNonSessionDefenseurBean.setAide_overtime(mBeanDefenseur.getAide_overtime());
	    mNonSessionDefenseurBean.setBirthday(mBeanDefenseur.getBirthday());
	    mNonSessionDefenseurBean.setBlanchissage(mBeanDefenseur.getBlanchissage());
	    mNonSessionDefenseurBean.setBut_victoire(mBeanDefenseur.getBut_victoire());
	    mNonSessionDefenseurBean.setCan_be_rookie(mBeanDefenseur.getCan_be_rookie());
	    mNonSessionDefenseurBean.setClub_ecole(mBeanDefenseur.getClub_ecole());
	    mNonSessionDefenseurBean.setContrat(mBeanDefenseur.getContrat());
	    mNonSessionDefenseurBean.setContrat_cours(mBeanDefenseur.getContrat_cours());
	    mNonSessionDefenseurBean.setContrat_max_years(mBeanDefenseur.getContrat_max_years());
	    mNonSessionDefenseurBean.setDate_calcul(mBeanDefenseur.getDate_calcul());
	    mNonSessionDefenseurBean.setEquipe(mBeanDefenseur.getEquipe());
	    mNonSessionDefenseurBean.setHier(mBeanDefenseur.getHier());
	    mNonSessionDefenseurBean.setMois(mBeanDefenseur.getMois());
	    mNonSessionDefenseurBean.setNom(mBeanDefenseur.getNom());
	    mNonSessionDefenseurBean.setPj(mBeanDefenseur.getPj());
	    mNonSessionDefenseurBean.setPosition(mBeanDefenseur.getPosition());
	    mNonSessionDefenseurBean.setProjection(mBeanDefenseur.getProjection());
	    mNonSessionDefenseurBean.setPts(mBeanDefenseur.getPts());
	    mNonSessionDefenseurBean.setSalaire_contrat(mBeanDefenseur.getSalaire_contrat());
	    mNonSessionDefenseurBean.setSalaire_draft(mBeanDefenseur.getSalaire_draft());
	    mNonSessionDefenseurBean.setSemaine(mBeanDefenseur.getSemaine());
	    mNonSessionDefenseurBean.setTake_proj(mBeanDefenseur.getTake_proj());
	    mNonSessionDefenseurBean.setTeam_id(mBeanDefenseur.getTeam_id());
	    mNonSessionDefenseurBean.setTeam_was_update(mBeanDefenseur.getTeam_was_update());
	    mNonSessionDefenseurBean.setTeamOfPlayer(mBeanDefenseur.getTeamOfPlayer());
	    mNonSessionDefenseurBean.setType_contrat(mBeanDefenseur.getType_contrat());
	    mNonSessionDefenseurBean.setYears_1(mBeanDefenseur.getYears_1());
	    mNonSessionDefenseurBean.setYears_2(mBeanDefenseur.getYears_2());
	    mNonSessionDefenseurBean.setYears_3(mBeanDefenseur.getYears_3());
	    mNonSessionDefenseurBean.setYears_4(mBeanDefenseur.getYears_4());
	    mNonSessionDefenseurBean.setYears_5(mBeanDefenseur.getYears_5());
	    
	  //Gardien
	    mNonSessionGardienBean.setAge(mBeanGardien.getAge());
	    mNonSessionGardienBean.setAide_overtime(mBeanGardien.getAide_overtime());
	    mNonSessionGardienBean.setBirthday(mBeanGardien.getBirthday());
	    mNonSessionGardienBean.setBlanchissage(mBeanGardien.getBlanchissage());
	    mNonSessionGardienBean.setBut_victoire(mBeanGardien.getBut_victoire());
	    mNonSessionGardienBean.setCan_be_rookie(mBeanGardien.getCan_be_rookie());
	    mNonSessionGardienBean.setClub_ecole(mBeanGardien.getClub_ecole());
	    mNonSessionGardienBean.setContrat(mBeanGardien.getContrat());
	    mNonSessionGardienBean.setContrat_cours(mBeanGardien.getContrat_cours());
	    mNonSessionGardienBean.setContrat_max_years(mBeanGardien.getContrat_max_years());
	    mNonSessionGardienBean.setDate_calcul(mBeanGardien.getDate_calcul());
	    mNonSessionGardienBean.setEquipe(mBeanGardien.getEquipe());
	    mNonSessionGardienBean.setHier(mBeanGardien.getHier());
	    mNonSessionGardienBean.setMois(mBeanGardien.getMois());
	    mNonSessionGardienBean.setNom(mBeanGardien.getNom());
	    mNonSessionGardienBean.setPj(mBeanGardien.getPj());
	    mNonSessionGardienBean.setPosition(mBeanGardien.getPosition());
	    mNonSessionGardienBean.setProjection(mBeanGardien.getProjection());
	    mNonSessionGardienBean.setPts(mBeanGardien.getPts());
	    mNonSessionGardienBean.setSalaire_contrat(mBeanGardien.getSalaire_contrat());
	    mNonSessionGardienBean.setSalaire_draft(mBeanGardien.getSalaire_draft());
	    mNonSessionGardienBean.setSemaine(mBeanGardien.getSemaine());
	    mNonSessionGardienBean.setTake_proj(mBeanGardien.getTake_proj());
	    mNonSessionGardienBean.setTeam_id(mBeanGardien.getTeam_id());
	    mNonSessionGardienBean.setTeam_was_update(mBeanGardien.getTeam_was_update());
	    mNonSessionGardienBean.setTeamOfPlayer(mBeanGardien.getTeamOfPlayer());
	    mNonSessionGardienBean.setType_contrat(mBeanGardien.getType_contrat());
	    mNonSessionGardienBean.setYears_1(mBeanGardien.getYears_1());
	    mNonSessionGardienBean.setYears_2(mBeanGardien.getYears_2());
	    mNonSessionGardienBean.setYears_3(mBeanGardien.getYears_3());
	    mNonSessionGardienBean.setYears_4(mBeanGardien.getYears_4());
	    mNonSessionGardienBean.setYears_5(mBeanGardien.getYears_5());
	    
	  //Recrue
	    mNonSessionRecrueBean.setAge(mBeanRecrue.getAge());
	    mNonSessionRecrueBean.setAide_overtime(mBeanRecrue.getAide_overtime());
	    mNonSessionRecrueBean.setBirthday(mBeanRecrue.getBirthday());
	    mNonSessionRecrueBean.setBlanchissage(mBeanRecrue.getBlanchissage());
	    mNonSessionRecrueBean.setBut_victoire(mBeanRecrue.getBut_victoire());
	    mNonSessionRecrueBean.setCan_be_rookie(mBeanRecrue.getCan_be_rookie());
	    mNonSessionRecrueBean.setClub_ecole(mBeanRecrue.getClub_ecole());
	    mNonSessionRecrueBean.setContrat(mBeanRecrue.getContrat());
	    mNonSessionRecrueBean.setContrat_cours(mBeanRecrue.getContrat_cours());
	    mNonSessionRecrueBean.setContrat_max_years(mBeanRecrue.getContrat_max_years());
	    mNonSessionRecrueBean.setDate_calcul(mBeanRecrue.getDate_calcul());
	    mNonSessionRecrueBean.setEquipe(mBeanRecrue.getEquipe());
	    mNonSessionRecrueBean.setHier(mBeanRecrue.getHier());
	    mNonSessionRecrueBean.setMois(mBeanRecrue.getMois());
	    mNonSessionRecrueBean.setNom(mBeanRecrue.getNom());
	    mNonSessionRecrueBean.setPj(mBeanRecrue.getPj());
	    mNonSessionRecrueBean.setPosition(mBeanRecrue.getPosition());
	    mNonSessionRecrueBean.setProjection(mBeanRecrue.getProjection());
	    mNonSessionRecrueBean.setPts(mBeanRecrue.getPts());
	    mNonSessionRecrueBean.setSalaire_contrat(mBeanRecrue.getSalaire_contrat());
	    mNonSessionRecrueBean.setSalaire_draft(mBeanRecrue.getSalaire_draft());
	    mNonSessionRecrueBean.setSemaine(mBeanRecrue.getSemaine());
	    mNonSessionRecrueBean.setTake_proj(mBeanRecrue.getTake_proj());
	    mNonSessionRecrueBean.setTeam_id(mBeanRecrue.getTeam_id());
	    mNonSessionRecrueBean.setTeam_was_update(mBeanRecrue.getTeam_was_update());
	    mNonSessionRecrueBean.setTeamOfPlayer(mBeanRecrue.getTeamOfPlayer());
	    mNonSessionRecrueBean.setType_contrat(mBeanRecrue.getType_contrat());
	    mNonSessionRecrueBean.setYears_1(mBeanRecrue.getYears_1());
	    mNonSessionRecrueBean.setYears_2(mBeanRecrue.getYears_2());
	    mNonSessionRecrueBean.setYears_3(mBeanRecrue.getYears_3());
	    mNonSessionRecrueBean.setYears_4(mBeanRecrue.getYears_4());
	    mNonSessionRecrueBean.setYears_5(mBeanRecrue.getYears_5());
	
	req.setAttribute("NonSessionEquipe", mNonSessionEquipeBean);
	req.setAttribute("NonSessionDraftPick", mNonSessionDraftBean);
	req.setAttribute("NonSessionAttaquant", mNonSessionAttaquantBean);
	req.setAttribute("NonSessionDefenseur", mNonSessionDefenseurBean);
	req.setAttribute("NonSessionGardien", mNonSessionGardienBean);
	req.setAttribute("NonSessionRecrue", mNonSessionRecrueBean);
	
    }

}