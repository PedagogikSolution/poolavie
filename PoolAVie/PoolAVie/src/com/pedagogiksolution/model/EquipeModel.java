package com.pedagogiksolution.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.beans.NonSessionAttaquant;
import com.pedagogiksolution.beans.NonSessionBean;
import com.pedagogiksolution.beans.NonSessionDefenseur;
import com.pedagogiksolution.beans.NonSessionDraftPick;
import com.pedagogiksolution.beans.NonSessionEquipe;
import com.pedagogiksolution.beans.NonSessionGardien;
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.beans.NonSessionRecrue;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class EquipeModel {

    @SuppressWarnings("unchecked")
    public void getBeanByTeam(HttpServletRequest req) {
	String draftPickName = null;
	String equipeName = null;
	String s_teamNumberToShow = req.getParameter("team");
	String attaquantName = null;
	String defenseurName = null;
	String gardienName = null;
	String recrueName = null;
	String logoDuTeam = null;
	String nomDuTeam = null;
	NonSessionBean mBean = new NonSessionBean();
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    
	    

	if (s_teamNumberToShow == null) {
	    Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	    int teamNumberToShow = mBeanUser.getTeamId();
	    draftPickName = "DraftPick" + teamNumberToShow;
	    equipeName = "Equipe" + teamNumberToShow;
	    attaquantName = "Attaquant" + teamNumberToShow;
	    defenseurName = "Defenseur" + teamNumberToShow;
	    gardienName = "Gardien" + teamNumberToShow;
	    recrueName = "Recrue" + teamNumberToShow;
	    String datastoreID = poolID + "_" + teamNumberToShow;
	    Key mKeyClassement = KeyFactory.createKey("Classement", poolID);
	    Key mKey = KeyFactory.createKey("Classement_Progression", datastoreID);
	    try {
		Entity entity = datastore.get(mKey);
		Entity entityClassement = datastore.get(mKeyClassement);
		
		Long position = (Long)entity.getProperty("position");
		
		mBean.setPositionDuTeam(position.intValue());
		
		List<Long> hierArray = (List<Long>) entityClassement.getProperty("hier");
		List<Long> pointsArray = (List<Long>) entityClassement.getProperty("points");
		
		Long hierLong = hierArray.get(position.intValue()-1);
		int hier = hierLong.intValue();
		
		Long pointsLong = pointsArray.get(position.intValue()-1);
		int points = pointsLong.intValue();
		
		mBean.setPointHierDuTeam(hier);
		mBean.setPointTotalDuTeam(points);
		
		
	    } catch (EntityNotFoundException e) {

	    }
	    
	    
	    
	    
	    
	    switch (teamNumberToShow) {
	   
	    case 1: 
		logoDuTeam = mBeanPool.getLogoTeam1();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam1();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 2: 
		logoDuTeam = mBeanPool.getLogoTeam2();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam2();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 3: 
		logoDuTeam = mBeanPool.getLogoTeam3();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam3();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 4: 
		logoDuTeam = mBeanPool.getLogoTeam4();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam4();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 5: 
		logoDuTeam = mBeanPool.getLogoTeam5();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam5();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 6: 
		logoDuTeam = mBeanPool.getLogoTeam6();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam6();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 7: 
		logoDuTeam = mBeanPool.getLogoTeam7();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam7();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 8: 
		logoDuTeam = mBeanPool.getLogoTeam8();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam8();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 9: 
		logoDuTeam = mBeanPool.getLogoTeam9();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam9();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 10: 
		logoDuTeam = mBeanPool.getLogoTeam10();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam10();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 11: 
		logoDuTeam = mBeanPool.getLogoTeam11();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam11();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 12: 
		logoDuTeam = mBeanPool.getLogoTeam12();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam12();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    }

	} else {
	    draftPickName = "DraftPick" + s_teamNumberToShow;
	    equipeName = "Equipe" + s_teamNumberToShow;
	    attaquantName = "Attaquant" + s_teamNumberToShow;
	    defenseurName = "Defenseur" + s_teamNumberToShow;
	    gardienName = "Gardien" + s_teamNumberToShow;
	    recrueName = "Recrue" + s_teamNumberToShow;
	    
	    int teamNumberId = Integer.parseInt(s_teamNumberToShow);
	    
	    String datastoreID = poolID + "_" + teamNumberId;
	    Key mKeyClassement = KeyFactory.createKey("Classement", poolID);
	    Key mKey = KeyFactory.createKey("Classement_Progression", datastoreID);
	    try {
		Entity entity = datastore.get(mKey);
		Entity entityClassement = datastore.get(mKeyClassement);
		
		Long position = (Long)entity.getProperty("position");
		
		mBean.setPositionDuTeam(position.intValue());
		
		List<Long> hierArray = (List<Long>) entityClassement.getProperty("hier");
		List<Long> pointsArray = (List<Long>) entityClassement.getProperty("points");
		
		Long hierLong = hierArray.get(position.intValue()-1);
		int hier = hierLong.intValue();
		
		Long pointsLong = pointsArray.get(position.intValue()-1);
		int points = pointsLong.intValue();
		
		mBean.setPointHierDuTeam(hier);
		mBean.setPointTotalDuTeam(points);
		
		
	    } catch (EntityNotFoundException e) {

	    }
	    
	    switch (teamNumberId) {
		   
	    case 1: 
		logoDuTeam = mBeanPool.getLogoTeam1();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam1();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 2: 
		logoDuTeam = mBeanPool.getLogoTeam2();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam2();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 3: 
		logoDuTeam = mBeanPool.getLogoTeam3();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam3();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 4: 
		logoDuTeam = mBeanPool.getLogoTeam4();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam4();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 5: 
		logoDuTeam = mBeanPool.getLogoTeam5();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam5();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 6: 
		logoDuTeam = mBeanPool.getLogoTeam6();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam6();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 7: 
		logoDuTeam = mBeanPool.getLogoTeam7();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam7();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 8: 
		logoDuTeam = mBeanPool.getLogoTeam8();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam8();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 9: 
		logoDuTeam = mBeanPool.getLogoTeam9();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam9();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 10: 
		logoDuTeam = mBeanPool.getLogoTeam10();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam10();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 11: 
		logoDuTeam = mBeanPool.getLogoTeam11();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam11();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    case 12: 
		logoDuTeam = mBeanPool.getLogoTeam12();
		mBean.setLogoDuTeam(logoDuTeam);
		nomDuTeam = mBeanPool.getNomTeam12();
		mBean.setNomDuTeam(nomDuTeam);
		
		break;
	    }
	}

	Equipe mBeanEquipe = (Equipe) req.getSession().getAttribute(equipeName);
	NonSessionEquipe mNonSessionEquipeBean = new NonSessionEquipe();

	DraftPick mBeanDraftPick = (DraftPick) req.getSession().getAttribute(draftPickName);
	NonSessionDraftPick mNonSessionDraftBean = new NonSessionDraftPick();

	Attaquant mBeanAttaquant = (Attaquant) req.getSession().getAttribute(attaquantName);
	if(mBeanAttaquant==null){
	    mBeanAttaquant= new Attaquant();
	}
	NonSessionAttaquant mNonSessionAttaquantBean = new NonSessionAttaquant();

	Defenseur mBeanDefenseur = (Defenseur) req.getSession().getAttribute(defenseurName);
	if(mBeanDefenseur==null){
	    mBeanDefenseur= new Defenseur();
	}
	NonSessionDefenseur mNonSessionDefenseurBean = new NonSessionDefenseur();

	Gardien mBeanGardien = (Gardien) req.getSession().getAttribute(gardienName);
	if(mBeanGardien==null){
	    mBeanGardien= new Gardien();
	}
	NonSessionGardien mNonSessionGardienBean = new NonSessionGardien();

	Recrue mBeanRecrue = (Recrue) req.getSession().getAttribute(recrueName);
	if(mBeanRecrue==null){
	    mBeanRecrue= new Recrue();
	}
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
	mNonSessionEquipeBean.setNb_contrat(mBeanEquipe.getNb_contrat());
	mNonSessionEquipeBean.setNum_annee(mBeanEquipe.getNum_annee());
	mNonSessionEquipeBean.setNum_champion(mBeanEquipe.getNum_champion());
	mNonSessionEquipeBean.setPoolId(mBeanEquipe.getPoolId());
	mNonSessionEquipeBean.setPoolTeamId(mBeanEquipe.getPoolTeamId());
	mNonSessionEquipeBean.setTeamId(mBeanEquipe.getTeamId());
	mNonSessionEquipeBean.setTotal_salaire_now(mBeanEquipe.getTotal_salaire_now());

	
	int cycleAnnuel = mBeanPool.getCycleAnnuel();
	if(cycleAnnuel>1){
	// Draft
	mNonSessionDraftBean.setPick_no(mBeanDraftPick.getPick_no());
	mNonSessionDraftBean.setTeamNameOriginalPick(mBeanDraftPick.getTeamNameOriginalPick());
	}
	// Attaquant
	mNonSessionAttaquantBean.setAide_overtime(mBeanAttaquant.getAide_overtime());
	mNonSessionAttaquantBean.setBirthday(mBeanAttaquant.getBirthday());
	mNonSessionAttaquantBean.setBut_victoire(mBeanAttaquant.getBut_victoire());
	mNonSessionAttaquantBean.setCan_be_rookie(mBeanAttaquant.getCan_be_rookie());
	mNonSessionAttaquantBean.setClub_ecole(mBeanAttaquant.getClub_ecole());
	mNonSessionAttaquantBean.setContrat(mBeanAttaquant.getContrat());
	mNonSessionAttaquantBean.setAcquire_years(mBeanAttaquant.getAcquire_years());
	mNonSessionAttaquantBean.setNom(mBeanAttaquant.getNom());
	mNonSessionAttaquantBean.setPj(mBeanAttaquant.getPj());
	mNonSessionAttaquantBean.setPosition(mBeanAttaquant.getPosition());
	mNonSessionAttaquantBean.setProjection(mBeanAttaquant.getProjection());
	mNonSessionAttaquantBean.setPts(mBeanAttaquant.getPts());
	mNonSessionAttaquantBean.setSalaire_draft(mBeanAttaquant.getSalaire_draft());
	mNonSessionAttaquantBean.setTeam_id(mBeanAttaquant.getTeam_id());
	mNonSessionAttaquantBean.setTeamOfPlayer(mBeanAttaquant.getTeamOfPlayer());
	mNonSessionAttaquantBean.setYears_1(mBeanAttaquant.getYears_1());
	mNonSessionAttaquantBean.setYears_2(mBeanAttaquant.getYears_2());
	mNonSessionAttaquantBean.setYears_3(mBeanAttaquant.getYears_3());
	mNonSessionAttaquantBean.setYears_4(mBeanAttaquant.getYears_4());
	mNonSessionAttaquantBean.setYears_5(mBeanAttaquant.getYears_5());

	// Defenseur
	mNonSessionDefenseurBean.setAide_overtime(mBeanDefenseur.getAide_overtime());
	mNonSessionDefenseurBean.setBirthday(mBeanDefenseur.getBirthday());
	mNonSessionDefenseurBean.setBlanchissage(mBeanDefenseur.getBlanchissage());
	mNonSessionDefenseurBean.setBut_victoire(mBeanDefenseur.getBut_victoire());
	mNonSessionDefenseurBean.setCan_be_rookie(mBeanDefenseur.getCan_be_rookie());
	mNonSessionDefenseurBean.setClub_ecole(mBeanDefenseur.getClub_ecole());
	mNonSessionDefenseurBean.setContrat(mBeanDefenseur.getContrat());
	mNonSessionDefenseurBean.setAcquire_years(mBeanDefenseur.getAcquire_years());
	mNonSessionDefenseurBean.setNom(mBeanDefenseur.getNom());
	mNonSessionDefenseurBean.setPj(mBeanDefenseur.getPj());
	mNonSessionDefenseurBean.setPosition(mBeanDefenseur.getPosition());
	mNonSessionDefenseurBean.setProjection(mBeanDefenseur.getProjection());
	mNonSessionDefenseurBean.setPts(mBeanDefenseur.getPts());
	mNonSessionDefenseurBean.setSalaire_draft(mBeanDefenseur.getSalaire_draft());
	mNonSessionDefenseurBean.setTeam_id(mBeanDefenseur.getTeam_id());
	mNonSessionDefenseurBean.setTeamOfPlayer(mBeanDefenseur.getTeamOfPlayer());
	mNonSessionDefenseurBean.setYears_1(mBeanDefenseur.getYears_1());
	mNonSessionDefenseurBean.setYears_2(mBeanDefenseur.getYears_2());
	mNonSessionDefenseurBean.setYears_3(mBeanDefenseur.getYears_3());
	mNonSessionDefenseurBean.setYears_4(mBeanDefenseur.getYears_4());
	mNonSessionDefenseurBean.setYears_5(mBeanDefenseur.getYears_5());

	// Gardien
	mNonSessionGardienBean.setAide_overtime(mBeanGardien.getAide_overtime());
	mNonSessionGardienBean.setBirthday(mBeanGardien.getBirthday());
	mNonSessionGardienBean.setBlanchissage(mBeanGardien.getBlanchissage());
	mNonSessionGardienBean.setBut_victoire(mBeanGardien.getBut_victoire());
	mNonSessionGardienBean.setCan_be_rookie(mBeanGardien.getCan_be_rookie());
	mNonSessionGardienBean.setClub_ecole(mBeanGardien.getClub_ecole());
	mNonSessionGardienBean.setContrat(mBeanGardien.getContrat());
	mNonSessionGardienBean.setAcquire_years(mBeanGardien.getAcquire_years());
	mNonSessionGardienBean.setNom(mBeanGardien.getNom());
	mNonSessionGardienBean.setPj(mBeanGardien.getPj());
	mNonSessionGardienBean.setPosition(mBeanGardien.getPosition());
	mNonSessionGardienBean.setProjection(mBeanGardien.getProjection());
	mNonSessionGardienBean.setPts(mBeanGardien.getPts());
	mNonSessionGardienBean.setSalaire_draft(mBeanGardien.getSalaire_draft());
	mNonSessionGardienBean.setTeam_id(mBeanGardien.getTeam_id());
	mNonSessionGardienBean.setTeamOfPlayer(mBeanGardien.getTeamOfPlayer());
	mNonSessionGardienBean.setYears_1(mBeanGardien.getYears_1());
	mNonSessionGardienBean.setYears_2(mBeanGardien.getYears_2());
	mNonSessionGardienBean.setYears_3(mBeanGardien.getYears_3());
	mNonSessionGardienBean.setYears_4(mBeanGardien.getYears_4());
	mNonSessionGardienBean.setYears_5(mBeanGardien.getYears_5());

	// Recrue
	mNonSessionRecrueBean.setAide_overtime(mBeanRecrue.getAide_overtime());
	mNonSessionRecrueBean.setBirthday(mBeanRecrue.getBirthday());
	mNonSessionRecrueBean.setBlanchissage(mBeanRecrue.getBlanchissage());
	mNonSessionRecrueBean.setBut_victoire(mBeanRecrue.getBut_victoire());
	mNonSessionRecrueBean.setCan_be_rookie(mBeanRecrue.getCan_be_rookie());
	mNonSessionRecrueBean.setClub_ecole(mBeanRecrue.getClub_ecole());
	mNonSessionRecrueBean.setContrat(mBeanRecrue.getContrat());
	mNonSessionRecrueBean.setAcquire_years(mBeanRecrue.getAcquire_years());
	mNonSessionRecrueBean.setNom(mBeanRecrue.getNom());
	mNonSessionRecrueBean.setPj(mBeanRecrue.getPj());
	mNonSessionRecrueBean.setPosition(mBeanRecrue.getPosition());
	mNonSessionRecrueBean.setProjection(mBeanRecrue.getProjection());
	mNonSessionRecrueBean.setPts(mBeanRecrue.getPts());
	mNonSessionRecrueBean.setSalaire_draft(mBeanRecrue.getSalaire_draft());
	mNonSessionRecrueBean.setTeam_id(mBeanRecrue.getTeam_id());
	mNonSessionRecrueBean.setTeamOfPlayer(mBeanRecrue.getTeamOfPlayer());
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
	req.setAttribute("NonSessionBean", mBean);

    }

	public void updateEquipeBudget(HttpServletRequest req) {
		String budget_restant = req.getParameter("budget_restant");
		String argent_recu = req.getParameter("argent_recu");
		String teamId = req.getParameter("team");
		int budget_restant_int = Integer.parseInt(budget_restant);
		int argent_recu_int = Integer.parseInt(argent_recu);	
		int max_salaire_begin = Integer.parseInt(req.getParameter("max_salaire_begin"));
		int total_salaire_now = Integer.parseInt(req.getParameter("total_salaire_now"));
		
		
		
		int teamId_int = Integer.parseInt(teamId);
		
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");		
		String poolIdString = mBeanPool.getPoolID();
		int poolId = Integer.parseInt(poolIdString);
		
		String datastoreID = poolId + "_" + teamId_int;

		Key equipeKey = KeyFactory.createKey("Equipe", datastoreID);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity equipeEntity;
		Equipe mBeanEquipe = new Equipe();
		try {
			equipeEntity = datastore.get(equipeKey);			
			mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(equipeEntity, mBeanEquipe);			
			mBeanEquipe.setArgent_recu(argent_recu_int);
			mBeanEquipe.setBudget_restant(budget_restant_int);
			mBeanEquipe.setMax_salaire_begin(max_salaire_begin);
			mBeanEquipe.setTotal_salaire_now(total_salaire_now);
			if(mBeanEquipe.getNb_equipe()!=0||mBeanEquipe.getManquant_equipe()!=0) {
			mBeanEquipe.setMoy_sal_restant_draft(budget_restant_int/mBeanEquipe.getManquant_equipe());
			} else {
				mBeanEquipe.setMoy_sal_restant_draft(0);
			}
			equipeEntity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, datastoreID);
			datastore.put(equipeEntity);
						
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}

	public void updateEquipeStats(HttpServletRequest req) {
		int nb_attaquant = Integer.parseInt(req.getParameter("nb_attaquant"));
		int nb_defenseur = Integer.parseInt(req.getParameter("nb_defenseur"));
		int nb_gardien = Integer.parseInt(req.getParameter("nb_gardien"));
		int nb_rookie = Integer.parseInt(req.getParameter("nb_rookie"));
		int nb_contrat = Integer.parseInt(req.getParameter("nb_contrat"));
		int nb_equipe = Integer.parseInt(req.getParameter("nb_equipe"));
		int manquant_equipe = Integer.parseInt(req.getParameter("manquant_equipe"));
		int manquant_att = Integer.parseInt(req.getParameter("manquant_att"));
		int manquant_def = Integer.parseInt(req.getParameter("manquant_def"));
		int manquant_gardien = Integer.parseInt(req.getParameter("manquant_gardien"));
		int manquant_recrue =Integer.parseInt( req.getParameter("manquant_recrue"));		
		int teamId = Integer.parseInt(req.getParameter("team"));

		
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");		
		String poolIdString = mBeanPool.getPoolID();
		int poolId = Integer.parseInt(poolIdString);
		
		String datastoreID = poolId + "_" + teamId;

		Key equipeKey = KeyFactory.createKey("Equipe", datastoreID);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity equipeEntity;
		Equipe mBeanEquipe = new Equipe();
		try {
			equipeEntity = datastore.get(equipeKey);			
			mBeanEquipe = mBeanEquipe.mapEquipeFromDatastore(equipeEntity, mBeanEquipe);			
			
			mBeanEquipe.setNb_attaquant(nb_attaquant);
			mBeanEquipe.setNb_defenseur(nb_defenseur);
			mBeanEquipe.setNb_gardien(nb_gardien);
			mBeanEquipe.setNb_rookie(nb_rookie);
			mBeanEquipe.setNb_contrat(nb_contrat);
			mBeanEquipe.setNb_equipe(nb_equipe);
			mBeanEquipe.setManquant_att(manquant_att);
			mBeanEquipe.setManquant_def(manquant_def);
			mBeanEquipe.setManquant_gardien(manquant_gardien);
			mBeanEquipe.setManquant_recrue(manquant_recrue);
			mBeanEquipe.setManquant_equipe(manquant_equipe);			
			
			equipeEntity = mBeanEquipe.mapBeanToEntityForDatastore(mBeanEquipe, datastoreID);
			datastore.put(equipeEntity);
						
		} catch (EntityNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}

	
	public void getAllPlayersInTeams(HttpServletRequest req) {
		
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		
		NonSessionPlayers mNonSessionPlayersBean = new NonSessionPlayers();
		String attaquantName = null;
		String defenseurName = null;
		String gardienName = null;
		String recrueName = null;
		String jspSessionName=null;
		List<String> nom = new ArrayList<String>();
		List<Long> pj = new ArrayList<Long>();
		List<Long> pts = new ArrayList<Long>();
		List<String> owner = new ArrayList<String>();
		
		for(int i=1;i<=mBeanPool.getNumberTeam();i++) {
			jspSessionName = "Equipe" + i;
			attaquantName = "Attaquant" + i;
		    defenseurName = "Defenseur" + i;
		    gardienName = "Gardien"  + i;
		    recrueName = "Recrue"  + i;
		    Equipe mBeanEquipe = (Equipe) req.getSession().getAttribute(jspSessionName);
			Attaquant mBeanAttaquant = (Attaquant) req.getSession().getAttribute(attaquantName);
			if(mBeanEquipe.getNb_attaquant()<=0||mBeanAttaquant==null){
				
			}	else {
				if(mBeanAttaquant.getNom()!=null) {
				nom.addAll(mBeanAttaquant.getNom());
				pj.addAll(mBeanAttaquant.getPj());
				pts.addAll(mBeanAttaquant.getPts());
				}
			}
			Defenseur mBeanDefenseur = (Defenseur) req.getSession().getAttribute(defenseurName);
			if(mBeanEquipe.getNb_defenseur()<=0||mBeanDefenseur==null){
				
				
			} else {
				if(mBeanDefenseur.getNom()!=null) {
				nom.addAll(mBeanDefenseur.getNom());
				pj.addAll(mBeanDefenseur.getPj());
				pts.addAll(mBeanDefenseur.getPts());
				}
			}
			Gardien mBeanGardien = (Gardien) req.getSession().getAttribute(gardienName);	
			if(mBeanEquipe.getNb_gardien()<=0||mBeanGardien==null){
				
			} else {
				if(mBeanGardien.getNom()!=null) {
				nom.addAll(mBeanGardien.getNom());
				pj.addAll(mBeanGardien.getPj());
				pts.addAll(mBeanGardien.getPts());
				}
			}
			Recrue mBeanRecrue = (Recrue) req.getSession().getAttribute(recrueName);
			if(mBeanEquipe.getNb_rookie()<=0||mBeanRecrue==null){
				
			} else {
				if(mBeanRecrue.getNom()!=null) {
				nom.addAll(mBeanRecrue.getNom());
				pj.addAll(mBeanRecrue.getPj());
				pts.addAll(mBeanRecrue.getPts());
				}
			}
			
			int nb_equipe = mBeanEquipe.getNb_equipe()+mBeanEquipe.getNb_rookie();
			
		
			
			for(int j=1;j<nb_equipe+1;j++) {
				owner.add(getNomDuTeam(i,mBeanPool));
			}
						
			
		}
		
		mNonSessionPlayersBean.setNom(nom);
		mNonSessionPlayersBean.setPj2(pj);
		mNonSessionPlayersBean.setPts2(pts);
		mNonSessionPlayersBean.setTeamOfPlayer(owner);
			
		req.setAttribute("NonSessionPlayersBean", mNonSessionPlayersBean);
	}

	private String getNomDuTeam(int teamNumberId, Pool mBeanPool) {
		String nomDuTeam = null;
		switch (teamNumberId) {
		   
	    case 1: 
		
		nomDuTeam = mBeanPool.getNomTeam1();
		
		
		break;
	    case 2: 
		
		nomDuTeam = mBeanPool.getNomTeam2();
		
		
		break;
	    case 3: 
		
		nomDuTeam = mBeanPool.getNomTeam3();
		
		
		break;
	    case 4: 
		
		nomDuTeam = mBeanPool.getNomTeam4();
	
		
		break;
	    case 5: 
		
		nomDuTeam = mBeanPool.getNomTeam5();
		
		
		break;
	    case 6: 
		
		nomDuTeam = mBeanPool.getNomTeam6();
		
		break;
	    case 7: 
		
		nomDuTeam = mBeanPool.getNomTeam7();
		
		
		break;
	    case 8: 
	
		nomDuTeam = mBeanPool.getNomTeam8();
	
		
		break;
	    case 9: 
		
		nomDuTeam = mBeanPool.getNomTeam9();
	
		
		break;
	    case 10: 
		
		nomDuTeam = mBeanPool.getNomTeam10();
	
		
		break;
	    case 11: 
		
		nomDuTeam = mBeanPool.getNomTeam11();
	
		
		break;
	    case 12: 
		
		nomDuTeam = mBeanPool.getNomTeam12();
		
		
		break;
	    }
		return nomDuTeam;
	}


}