package com.pedagogiksolution.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.beans.NonSessionAttaquant;
import com.pedagogiksolution.beans.NonSessionDefenseur;
import com.pedagogiksolution.beans.NonSessionDraftPick;
import com.pedagogiksolution.beans.NonSessionEquipe;
import com.pedagogiksolution.beans.NonSessionGardien;
import com.pedagogiksolution.beans.NonSessionRecrue;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class TradeModel {

    HttpServletRequest req;
    Pool mBeanPool;
    Utilisateur mBeanUser;

    public TradeModel(HttpServletRequest req, Pool mBean) {
	this.req = req;
	this.mBeanPool = mBean;

    }

    public TradeModel(Utilisateur mBeanUser, Pool mBeanPool2, HttpServletRequest req2) {
	this.req = req2;
	this.mBeanPool = mBeanPool2;
	this.mBeanUser = mBeanUser;
    }

    public void getTradeOfferReceived() {
	// TODO Auto-generated method stub

    }

    public void getTradeOfferMade() {
	// TODO Auto-generated method stub

    }

    public void getMyTrade() {
	// TODO Auto-generated method stub

    }

    public void getAllTrade() {
	// TODO Auto-generated method stub

    }

    public Boolean checkIfTradeOpen() {
	// TODO Auto-generated method stub
	return null;
    }

    public Boolean getDataOnTeamThatTrade() {
	String teamThatReceivedOfferID = req.getParameter("tradeWith");
	int teamThatReceivedOfferId = Integer.parseInt(teamThatReceivedOfferID);
	int teamThatMakeOfferId = mBeanUser.getTeamId();

	if (teamThatReceivedOfferId == teamThatMakeOfferId) {

	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire un échange avec vous-même... veuillez choisir une autre équipe");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return true;

	}

	String draftPickNameTeamMakingOffer = null;
	String attaquantNameTeamMakingOffer = null;
	String defenseurNameTeamMakingOffer = null;
	String gardienNameTeamMakingOffer = null;
	String recrueNameTeamMakingOffer = null;
	String draftPickNameTeamReceivingOffer = null;
	String attaquantNameTeamReceivingOffer = null;
	String defenseurNameTeamReceivingOffer = null;
	String gardienNameTeamReceivingOffer = null;
	String recrueNameTeamReceivingOffer = null;

	draftPickNameTeamMakingOffer = "DraftPick" + teamThatMakeOfferId;
	attaquantNameTeamMakingOffer = "Attaquant" + teamThatMakeOfferId;
	defenseurNameTeamMakingOffer = "Defenseur" + teamThatMakeOfferId;
	gardienNameTeamMakingOffer = "Gardien" + teamThatMakeOfferId;
	recrueNameTeamMakingOffer = "Recrue" + teamThatMakeOfferId;
	draftPickNameTeamReceivingOffer = "DraftPick" + teamThatReceivedOfferId;
	attaquantNameTeamReceivingOffer = "Attaquant" + teamThatReceivedOfferId;
	defenseurNameTeamReceivingOffer = "Defenseur" + teamThatReceivedOfferId;
	gardienNameTeamReceivingOffer = "Gardien" + teamThatReceivedOfferId;
	recrueNameTeamReceivingOffer = "Recrue" + teamThatReceivedOfferId;

	DraftPick mBeanDraftPickThatMakingOffer = (DraftPick) req.getSession().getAttribute(draftPickNameTeamMakingOffer);
	NonSessionDraftPick mNonSessionDraftBeanThatMakingOffer = new NonSessionDraftPick();

	DraftPick mBeanDraftPickThatReceivedOffer = (DraftPick) req.getSession().getAttribute(draftPickNameTeamReceivingOffer);
	NonSessionDraftPick mNonSessionDraftBeanThatReceivedOffer = new NonSessionDraftPick();

	DraftRound mBeanDraftRound = (DraftRound) req.getSession().getAttribute("DraftRound");
	NonSessionDraftPick mNonSessionDraftBeanThatMakingOffer2 = new NonSessionDraftPick();
	NonSessionDraftPick mNonSessionDraftBeanThatReceivedOffer2 = new NonSessionDraftPick();

	Attaquant mBeanAttaquantThatMakingOffer = (Attaquant) req.getSession().getAttribute(attaquantNameTeamMakingOffer);
	NonSessionAttaquant mNonSessionAttaquantBeanThatMakingOffer = new NonSessionAttaquant();

	Attaquant mBeanAttaquantThatReceivedOffer = (Attaquant) req.getSession().getAttribute(attaquantNameTeamReceivingOffer);
	NonSessionAttaquant mNonSessionAttaquantBeanThatReceivedOffer = new NonSessionAttaquant();

	Defenseur mBeanDefenseurThatMakingOffer = (Defenseur) req.getSession().getAttribute(defenseurNameTeamMakingOffer);
	NonSessionDefenseur mNonSessionDefenseurBeanThatMakingOffer = new NonSessionDefenseur();

	Defenseur mBeanDefenseurThatReceivedOffer = (Defenseur) req.getSession().getAttribute(defenseurNameTeamReceivingOffer);
	NonSessionDefenseur mNonSessionDefenseurBeanThatReceivedOffer = new NonSessionDefenseur();

	Gardien mBeanGardienThatMakingOffer = (Gardien) req.getSession().getAttribute(gardienNameTeamMakingOffer);
	NonSessionGardien mNonSessionGardienBeanThatMakingOffer = new NonSessionGardien();

	Gardien mBeanGardienThatReceivedOffer = (Gardien) req.getSession().getAttribute(gardienNameTeamReceivingOffer);
	NonSessionGardien mNonSessionGardienBeanThatReceivedOffer = new NonSessionGardien();

	Recrue mBeanRecrueThatMakingOffer = (Recrue) req.getSession().getAttribute(recrueNameTeamMakingOffer);
	NonSessionRecrue mNonSessionRecrueBeanThatMakingOffer = new NonSessionRecrue();

	Recrue mBeanRecrueThatReceivedOffer = (Recrue) req.getSession().getAttribute(recrueNameTeamReceivingOffer);
	NonSessionRecrue mNonSessionRecrueBeanThatReceivedOffer = new NonSessionRecrue();

	// DraftPick From DraftPick for next year or for nonDraftTrade
	mNonSessionDraftBeanThatMakingOffer.setPick_no(mBeanDraftPickThatMakingOffer.getPick_no());
	mNonSessionDraftBeanThatMakingOffer.setTeamNameOriginalPick(mBeanDraftPickThatMakingOffer.getTeamNameOriginalPick());

	mNonSessionDraftBeanThatReceivedOffer.setPick_no(mBeanDraftPickThatReceivedOffer.getPick_no());
	mNonSessionDraftBeanThatReceivedOffer.setTeamNameOriginalPick(mBeanDraftPickThatReceivedOffer.getTeamNameOriginalPick());

	// DraftPick From DraftRound for trade while draft is up
	List<Long> pick_no = new ArrayList<Long>();
	List<Long> ronde = new ArrayList<Long>();
	List<Long> team_id = new ArrayList<Long>();
	List<String> teamNameOriginePick = new ArrayList<String>();

	List<Long> mTeamId = mBeanDraftRound.getTeam_id();
	List<Long> mRonde = mBeanDraftRound.getRonde();
	List<Long> mPickNo = mBeanDraftRound.getDraft_pick_no();
	List<String> mTeamName = mBeanDraftRound.getEquipe();
	ListIterator<Long> mIterator = mTeamId.listIterator();
	while (mIterator.hasNext()) {
	    int indexListIterator = mIterator.nextIndex();
	    Long teamIdIterator = mIterator.next();
	    if (teamIdIterator == teamThatMakeOfferId) {
		Long draft_pick_no = mPickNo.get(indexListIterator);
		Long mRondeId = mRonde.get(indexListIterator);
		ronde.add(mRondeId);
		String mName = mTeamName.get(indexListIterator);
		teamNameOriginePick.add(mName);
		team_id.add(teamIdIterator);
		pick_no.add(draft_pick_no);

	    }
	}
	mNonSessionDraftBeanThatMakingOffer2.setPick_no(pick_no);
	mNonSessionDraftBeanThatMakingOffer2.setTeamNameOriginalPick(teamNameOriginePick);
	mNonSessionDraftBeanThatMakingOffer2.setTeam_id(team_id);
	mNonSessionDraftBeanThatMakingOffer2.setRonde(ronde);

	List<Long> pick_no2 = new ArrayList<Long>();
	List<Long> ronde2 = new ArrayList<Long>();
	List<Long> team_id2 = new ArrayList<Long>();
	List<String> teamNameOriginePick2 = new ArrayList<String>();
	List<Long> mTeamId2 = mBeanDraftRound.getTeam_id();
	List<Long> mRonde2 = mBeanDraftRound.getRonde();
	List<Long> mPickNo2 = mBeanDraftRound.getDraft_pick_no();
	List<String> mTeamName2 = mBeanDraftRound.getEquipe();
	ListIterator<Long> mIterator2 = mTeamId2.listIterator();
	/*
	 * while(mIterator2.hasNext()){
	 * Long indexListIterator = (long) mIterator2.nextIndex();
	 * Long teamIdIterator = mIterator2.next();
	 * if(teamIdIterator==teamThatReceivedOfferId){
	 * int draft_pick_no = mPickNo2.get(indexListIterator);
	 * int mRondeId = mRonde2.get(indexListIterator);
	 * ronde2.add(mRondeId);
	 * String mName = mTeamName2.get(indexListIterator);
	 * teamNameOriginePick2.add(mName);
	 * team_id2.add(teamIdIterator);
	 * pick_no2.add(draft_pick_no);
	 * }
	 * }
	 */

	mNonSessionDraftBeanThatReceivedOffer2.setPick_no(pick_no2);
	mNonSessionDraftBeanThatReceivedOffer2.setTeamNameOriginalPick(teamNameOriginePick2);
	mNonSessionDraftBeanThatReceivedOffer2.setTeam_id(team_id2);
	mNonSessionDraftBeanThatReceivedOffer2.setRonde(ronde2);

	// Attaquant
	mNonSessionAttaquantBeanThatMakingOffer.set_id(mBeanAttaquantThatMakingOffer.getPlayers_id());
	mNonSessionAttaquantBeanThatMakingOffer.setNom(mBeanAttaquantThatMakingOffer.getNom());
	mNonSessionAttaquantBeanThatMakingOffer.setAide_overtime(mBeanAttaquantThatMakingOffer.getAide_overtime());
	mNonSessionAttaquantBeanThatMakingOffer.setBut_victoire(mBeanAttaquantThatMakingOffer.getBut_victoire());
	mNonSessionAttaquantBeanThatMakingOffer.setPj(mBeanAttaquantThatMakingOffer.getPj());
	mNonSessionAttaquantBeanThatMakingOffer.setPosition(mBeanAttaquantThatMakingOffer.getPosition());
	mNonSessionAttaquantBeanThatMakingOffer.setPts(mBeanAttaquantThatMakingOffer.getPts());
	mNonSessionAttaquantBeanThatMakingOffer.setTeamOfPlayer(mBeanAttaquantThatMakingOffer.getTeamOfPlayer());
	mNonSessionAttaquantBeanThatMakingOffer.setYears_1(mBeanAttaquantThatMakingOffer.getYears_1());
	mNonSessionAttaquantBeanThatMakingOffer.setYears_2(mBeanAttaquantThatMakingOffer.getYears_2());
	mNonSessionAttaquantBeanThatMakingOffer.setYears_3(mBeanAttaquantThatMakingOffer.getYears_3());
	mNonSessionAttaquantBeanThatMakingOffer.setYears_4(mBeanAttaquantThatMakingOffer.getYears_4());
	mNonSessionAttaquantBeanThatMakingOffer.setYears_5(mBeanAttaquantThatMakingOffer.getYears_5());

	mNonSessionAttaquantBeanThatReceivedOffer.set_id(mBeanAttaquantThatReceivedOffer.getPlayers_id());
	mNonSessionAttaquantBeanThatReceivedOffer.setNom(mBeanAttaquantThatReceivedOffer.getNom());
	mNonSessionAttaquantBeanThatReceivedOffer.setAide_overtime(mBeanAttaquantThatReceivedOffer.getAide_overtime());
	mNonSessionAttaquantBeanThatReceivedOffer.setBut_victoire(mBeanAttaquantThatReceivedOffer.getBut_victoire());
	mNonSessionAttaquantBeanThatReceivedOffer.setPj(mBeanAttaquantThatReceivedOffer.getPj());
	mNonSessionAttaquantBeanThatReceivedOffer.setPosition(mBeanAttaquantThatReceivedOffer.getPosition());
	mNonSessionAttaquantBeanThatReceivedOffer.setPts(mBeanAttaquantThatReceivedOffer.getPts());
	mNonSessionAttaquantBeanThatReceivedOffer.setTeamOfPlayer(mBeanAttaquantThatReceivedOffer.getTeamOfPlayer());
	mNonSessionAttaquantBeanThatReceivedOffer.setYears_1(mBeanAttaquantThatReceivedOffer.getYears_1());
	mNonSessionAttaquantBeanThatReceivedOffer.setYears_2(mBeanAttaquantThatReceivedOffer.getYears_2());
	mNonSessionAttaquantBeanThatReceivedOffer.setYears_3(mBeanAttaquantThatReceivedOffer.getYears_3());
	mNonSessionAttaquantBeanThatReceivedOffer.setYears_4(mBeanAttaquantThatReceivedOffer.getYears_4());
	mNonSessionAttaquantBeanThatReceivedOffer.setYears_5(mBeanAttaquantThatReceivedOffer.getYears_5());

	// Defenseur
	mNonSessionDefenseurBeanThatMakingOffer.set_id(mBeanDefenseurThatMakingOffer.getPlayers_id());
	mNonSessionDefenseurBeanThatMakingOffer.setNom(mBeanDefenseurThatMakingOffer.getNom());
	mNonSessionDefenseurBeanThatMakingOffer.setAide_overtime(mBeanDefenseurThatMakingOffer.getAide_overtime());
	mNonSessionDefenseurBeanThatMakingOffer.setBut_victoire(mBeanDefenseurThatMakingOffer.getBut_victoire());
	mNonSessionDefenseurBeanThatMakingOffer.setPj(mBeanDefenseurThatMakingOffer.getPj());
	mNonSessionDefenseurBeanThatMakingOffer.setPosition(mBeanDefenseurThatMakingOffer.getPosition());
	mNonSessionDefenseurBeanThatMakingOffer.setPts(mBeanDefenseurThatMakingOffer.getPts());
	mNonSessionDefenseurBeanThatMakingOffer.setTeamOfPlayer(mBeanDefenseurThatMakingOffer.getTeamOfPlayer());
	mNonSessionDefenseurBeanThatMakingOffer.setYears_1(mBeanDefenseurThatMakingOffer.getYears_1());
	mNonSessionDefenseurBeanThatMakingOffer.setYears_2(mBeanDefenseurThatMakingOffer.getYears_2());
	mNonSessionDefenseurBeanThatMakingOffer.setYears_3(mBeanDefenseurThatMakingOffer.getYears_3());
	mNonSessionDefenseurBeanThatMakingOffer.setYears_4(mBeanDefenseurThatMakingOffer.getYears_4());
	mNonSessionDefenseurBeanThatMakingOffer.setYears_5(mBeanDefenseurThatMakingOffer.getYears_5());

	mNonSessionDefenseurBeanThatReceivedOffer.set_id(mBeanDefenseurThatReceivedOffer.getPlayers_id());
	mNonSessionDefenseurBeanThatReceivedOffer.setNom(mBeanDefenseurThatReceivedOffer.getNom());
	mNonSessionDefenseurBeanThatReceivedOffer.setAide_overtime(mBeanDefenseurThatReceivedOffer.getAide_overtime());
	mNonSessionDefenseurBeanThatReceivedOffer.setBut_victoire(mBeanDefenseurThatReceivedOffer.getBut_victoire());
	mNonSessionDefenseurBeanThatReceivedOffer.setPj(mBeanDefenseurThatReceivedOffer.getPj());
	mNonSessionDefenseurBeanThatReceivedOffer.setPosition(mBeanDefenseurThatReceivedOffer.getPosition());
	mNonSessionDefenseurBeanThatReceivedOffer.setPts(mBeanDefenseurThatReceivedOffer.getPts());
	mNonSessionDefenseurBeanThatReceivedOffer.setTeamOfPlayer(mBeanDefenseurThatReceivedOffer.getTeamOfPlayer());
	mNonSessionDefenseurBeanThatReceivedOffer.setYears_1(mBeanDefenseurThatReceivedOffer.getYears_1());
	mNonSessionDefenseurBeanThatReceivedOffer.setYears_2(mBeanDefenseurThatReceivedOffer.getYears_2());
	mNonSessionDefenseurBeanThatReceivedOffer.setYears_3(mBeanDefenseurThatReceivedOffer.getYears_3());
	mNonSessionDefenseurBeanThatReceivedOffer.setYears_4(mBeanDefenseurThatReceivedOffer.getYears_4());
	mNonSessionDefenseurBeanThatReceivedOffer.setYears_5(mBeanDefenseurThatReceivedOffer.getYears_5());

	// Gardien
	mNonSessionGardienBeanThatMakingOffer.set_id(mBeanGardienThatMakingOffer.getPlayers_id());
	mNonSessionGardienBeanThatMakingOffer.setNom(mBeanGardienThatMakingOffer.getNom());
	mNonSessionGardienBeanThatMakingOffer.setAide_overtime(mBeanGardienThatMakingOffer.getAide_overtime());
	mNonSessionGardienBeanThatMakingOffer.setBut_victoire(mBeanGardienThatMakingOffer.getBut_victoire());
	mNonSessionGardienBeanThatMakingOffer.setBlanchissage(mBeanGardienThatMakingOffer.getBlanchissage());
	mNonSessionGardienBeanThatMakingOffer.setPj(mBeanGardienThatMakingOffer.getPj());
	mNonSessionGardienBeanThatMakingOffer.setPosition(mBeanGardienThatMakingOffer.getPosition());
	mNonSessionGardienBeanThatMakingOffer.setPts(mBeanGardienThatMakingOffer.getPts());
	mNonSessionGardienBeanThatMakingOffer.setTeamOfPlayer(mBeanGardienThatMakingOffer.getTeamOfPlayer());
	mNonSessionGardienBeanThatMakingOffer.setYears_1(mBeanGardienThatMakingOffer.getYears_1());
	mNonSessionGardienBeanThatMakingOffer.setYears_2(mBeanGardienThatMakingOffer.getYears_2());
	mNonSessionGardienBeanThatMakingOffer.setYears_3(mBeanGardienThatMakingOffer.getYears_3());
	mNonSessionGardienBeanThatMakingOffer.setYears_4(mBeanGardienThatMakingOffer.getYears_4());
	mNonSessionGardienBeanThatMakingOffer.setYears_5(mBeanGardienThatMakingOffer.getYears_5());

	mNonSessionGardienBeanThatReceivedOffer.set_id(mBeanGardienThatReceivedOffer.getPlayers_id());
	mNonSessionGardienBeanThatReceivedOffer.setNom(mBeanGardienThatReceivedOffer.getNom());
	mNonSessionGardienBeanThatReceivedOffer.setAide_overtime(mBeanGardienThatReceivedOffer.getAide_overtime());
	mNonSessionGardienBeanThatReceivedOffer.setBut_victoire(mBeanGardienThatReceivedOffer.getBut_victoire());
	mNonSessionGardienBeanThatReceivedOffer.setPj(mBeanGardienThatReceivedOffer.getPj());
	mNonSessionGardienBeanThatReceivedOffer.setPosition(mBeanGardienThatReceivedOffer.getPosition());
	mNonSessionGardienBeanThatReceivedOffer.setPts(mBeanGardienThatReceivedOffer.getPts());
	mNonSessionGardienBeanThatReceivedOffer.setTeamOfPlayer(mBeanGardienThatReceivedOffer.getTeamOfPlayer());
	mNonSessionGardienBeanThatReceivedOffer.setYears_1(mBeanGardienThatReceivedOffer.getYears_1());
	mNonSessionGardienBeanThatReceivedOffer.setYears_2(mBeanGardienThatReceivedOffer.getYears_2());
	mNonSessionGardienBeanThatReceivedOffer.setYears_3(mBeanGardienThatReceivedOffer.getYears_3());
	mNonSessionGardienBeanThatReceivedOffer.setYears_4(mBeanGardienThatReceivedOffer.getYears_4());
	mNonSessionGardienBeanThatReceivedOffer.setYears_5(mBeanGardienThatReceivedOffer.getYears_5());

	// Recrue
	mNonSessionRecrueBeanThatMakingOffer.set_id(mBeanRecrueThatMakingOffer.getPlayers_id());
	mNonSessionRecrueBeanThatMakingOffer.setNom(mBeanRecrueThatMakingOffer.getNom());
	mNonSessionRecrueBeanThatMakingOffer.setAide_overtime(mBeanRecrueThatMakingOffer.getAide_overtime());
	mNonSessionRecrueBeanThatMakingOffer.setBut_victoire(mBeanRecrueThatMakingOffer.getBut_victoire());
	mNonSessionRecrueBeanThatMakingOffer.setPj(mBeanRecrueThatMakingOffer.getPj());
	mNonSessionRecrueBeanThatMakingOffer.setPosition(mBeanRecrueThatMakingOffer.getPosition());
	mNonSessionRecrueBeanThatMakingOffer.setPts(mBeanRecrueThatMakingOffer.getPts());
	mNonSessionRecrueBeanThatMakingOffer.setTeamOfPlayer(mBeanRecrueThatMakingOffer.getTeamOfPlayer());
	mNonSessionRecrueBeanThatMakingOffer.setYears_1(mBeanRecrueThatMakingOffer.getYears_1());
	mNonSessionRecrueBeanThatMakingOffer.setYears_2(mBeanRecrueThatMakingOffer.getYears_2());
	mNonSessionRecrueBeanThatMakingOffer.setYears_3(mBeanRecrueThatMakingOffer.getYears_3());
	mNonSessionRecrueBeanThatMakingOffer.setYears_4(mBeanRecrueThatMakingOffer.getYears_4());
	mNonSessionRecrueBeanThatMakingOffer.setYears_5(mBeanRecrueThatMakingOffer.getYears_5());

	mNonSessionRecrueBeanThatReceivedOffer.set_id(mBeanRecrueThatReceivedOffer.getPlayers_id());
	mNonSessionRecrueBeanThatReceivedOffer.setNom(mBeanRecrueThatReceivedOffer.getNom());
	mNonSessionRecrueBeanThatReceivedOffer.setAide_overtime(mBeanRecrueThatReceivedOffer.getAide_overtime());
	mNonSessionRecrueBeanThatReceivedOffer.setBut_victoire(mBeanRecrueThatReceivedOffer.getBut_victoire());
	mNonSessionRecrueBeanThatReceivedOffer.setPj(mBeanRecrueThatReceivedOffer.getPj());
	mNonSessionRecrueBeanThatReceivedOffer.setPosition(mBeanRecrueThatReceivedOffer.getPosition());
	mNonSessionRecrueBeanThatReceivedOffer.setPts(mBeanRecrueThatReceivedOffer.getPts());
	mNonSessionRecrueBeanThatReceivedOffer.setTeamOfPlayer(mBeanRecrueThatReceivedOffer.getTeamOfPlayer());
	mNonSessionRecrueBeanThatReceivedOffer.setYears_1(mBeanRecrueThatReceivedOffer.getYears_1());
	mNonSessionRecrueBeanThatReceivedOffer.setYears_2(mBeanRecrueThatReceivedOffer.getYears_2());
	mNonSessionRecrueBeanThatReceivedOffer.setYears_3(mBeanRecrueThatReceivedOffer.getYears_3());
	mNonSessionRecrueBeanThatReceivedOffer.setYears_4(mBeanRecrueThatReceivedOffer.getYears_4());
	mNonSessionRecrueBeanThatReceivedOffer.setYears_5(mBeanRecrueThatReceivedOffer.getYears_5());

	req.setAttribute("NonSessionDraftPickMaking", mNonSessionDraftBeanThatMakingOffer);
	req.setAttribute("NonSessionDraftPickReciving", mNonSessionDraftBeanThatReceivedOffer);

	req.setAttribute("NonSessionDraftPickMakingThisYear", mNonSessionDraftBeanThatMakingOffer2);
	req.setAttribute("NonSessionDraftPickRecivingThisYear", mNonSessionDraftBeanThatReceivedOffer2);

	req.setAttribute("NonSessionAttaquantPickMaking", mNonSessionAttaquantBeanThatMakingOffer);
	req.setAttribute("NonSessionAttaquantPickReciving", mNonSessionAttaquantBeanThatReceivedOffer);

	req.setAttribute("NonSessionDefenseurPickMaking", mNonSessionDefenseurBeanThatMakingOffer);
	req.setAttribute("NonSessionDefenseurPickReciving", mNonSessionDefenseurBeanThatReceivedOffer);

	req.setAttribute("NonSessionGardienPickMaking", mNonSessionGardienBeanThatMakingOffer);
	req.setAttribute("NonSessionGardienPickReciving", mNonSessionGardienBeanThatReceivedOffer);

	req.setAttribute("NonSessionRecruePickMaking", mNonSessionRecrueBeanThatMakingOffer);
	req.setAttribute("NonSessionRecruePickReciving", mNonSessionRecrueBeanThatReceivedOffer);

	req.setAttribute("TradeWith", teamThatReceivedOfferID);

	return false;

    }

    public Boolean checkIfTradeIsValidDuringDraft() {
	int nbPlayersTeamMakingOffer, nbPlayersTeamReceivingOffer, nbPicksTeamMakingOffer, nbPicksTeamReceivingOffer;
	// retourne false si trade pas possible // retourne true if trade is good to go at this point
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	String[] playersIdTeamThatMakeOffer = req.getParameterValues("players_id_my_team");
	String[] playersIdTeamThatReceiveOffer = req.getParameterValues("players_id_trade_with_team");
	if (playersIdTeamThatMakeOffer != null) {
	    nbPlayersTeamMakingOffer = playersIdTeamThatMakeOffer.length;
	} else {
	    nbPlayersTeamMakingOffer = 0;
	}
	if (playersIdTeamThatReceiveOffer != null) {
	    nbPlayersTeamReceivingOffer = playersIdTeamThatReceiveOffer.length;
	} else {
	    nbPlayersTeamReceivingOffer = 0;
	}

	String[] picksIdTeamThatMakeOffer = req.getParameterValues("picks_id_my_team");
	String[] picksIdTeamThatReceiveOffer = req.getParameterValues("picks_id_trade_with_team");

	if (picksIdTeamThatMakeOffer != null) {
	    nbPicksTeamMakingOffer = picksIdTeamThatMakeOffer.length;
	} else {
	    nbPicksTeamMakingOffer = 0;
	}
	if (picksIdTeamThatReceiveOffer != null) {
	    nbPicksTeamReceivingOffer = picksIdTeamThatReceiveOffer.length;
	} else {
	    nbPicksTeamReceivingOffer = 0;
	}
	String cashIncludeTeamThatMakeOffer = req.getParameter("cashMakingOffer");
	int cashIncludeTeamThatMakeOfferInt = Integer.parseInt(cashIncludeTeamThatMakeOffer);
	String cashIncludeThatReceiveOffer = req.getParameter("cashReceivingOffer");
	int cashIncludeThatReceiveOfferInt = Integer.parseInt(cashIncludeThatReceiveOffer);
	String tradeWithID = req.getParameter("tradeWith");
	int teamId = mBeanUser.getTeamId();
	String nomTeamThatOffer = "Equipe" + teamId;
	String nomTeamThatReceived = "Equipe" + tradeWithID;
	Equipe mBeanEquipeThatIsMakingOffer = (Equipe) req.getSession().getAttribute(nomTeamThatOffer);
	Equipe mBeanEquipeThatIsReceivingOffer = (Equipe) req.getSession().getAttribute(nomTeamThatReceived);
	MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();

	// 0- check si au moins un joueur

	// 1- checking for cash on the two side
	if (cashIncludeTeamThatMakeOfferInt > 0 && cashIncludeThatReceiveOfferInt > 0) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas échangez de l'argent contre de l'argent (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	// 2- check si nombre contrat trop elevé dans une des deux equipes seulement si durant draft ou été

	if (mBeanPool.getCycleAnnuel() == 3) {
	    int nbContratTeamThatOfferTrade = mBeanEquipeThatIsMakingOffer.getNb_contrat();
	    int nbContratTeamThatReceivedOfferTrade = mBeanEquipeThatIsReceivingOffer.getNb_contrat();

	    int nbPlayersWithContratTeamThatMakeOffer = getNbPlayersWithContratTeamThatMakeOffer(datastore, playersIdTeamThatMakeOffer, mBeanPool);
	    int nbPlayersWithContratTeamThatReceiveOffer = getNbPlayersWithContratTeamThatReceiveOffer(datastore, playersIdTeamThatReceiveOffer, mBeanPool);

	    if ((nbContratTeamThatOfferTrade - nbPlayersWithContratTeamThatMakeOffer + nbPlayersWithContratTeamThatReceiveOffer > 12) || (nbContratTeamThatReceivedOfferTrade - nbPlayersWithContratTeamThatReceiveOffer + nbPlayersWithContratTeamThatMakeOffer > 12)) {
		return false;
	    }

	}

	// 3- check si argent dispo pour faire draft apres echange

	// 4- check if number of players is 7 or less

	if (nbPlayersTeamMakingOffer > 7 || nbPlayersTeamReceivingOffer > 7) {

	    mBeanMessageErreur.setErreurTrade("Un maximum de 7 joueurs par équipe peut être inclus dans un échange unique (Reglement 4.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	// 5- check if number of pick is 3 or less

	if (nbPicksTeamMakingOffer > 3 || nbPicksTeamReceivingOffer > 3) {
	    mBeanMessageErreur.setErreurTrade("Un maximum de 3 choix au repêchage par équipe peut être inclus dans un échange unique (Reglement 4.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	return true;
    }

    public void persistTradeOffer() {
	// TODO Auto-generated method stub

    }

    public void sendAlertViaChannel() {
	// TODO Auto-generated method stub

    }

    public void sendEmailForOffer() {
	// TODO Auto-generated method stub

    }

    /******************************* methode privée à la classe **********************************/

    private int getNbPlayersWithContratTeamThatMakeOffer(DatastoreService datastore, String[] playersIdTeamThatMakeOffer, Pool mBeanPool) {
	int nbPlayersWithContratTeamThatMakeOffer = 0;
	String kindName = "Players_" + mBeanPool.getPoolID();

	for (String s : playersIdTeamThatMakeOffer) {

	    Key mKey = KeyFactory.createKey(kindName, s);
	    try {
		Entity entity = datastore.get(mKey);
		Long isUnderContrat = (Long) entity.getProperty("contrat");
		if (isUnderContrat == 1) {
		    nbPlayersWithContratTeamThatMakeOffer++;
		}

	    } catch (EntityNotFoundException e) {

	    }
	}

	return nbPlayersWithContratTeamThatMakeOffer;
    }

    private int getNbPlayersWithContratTeamThatReceiveOffer(DatastoreService datastore, String[] playersIdTeamThatReceiveOffer, Pool mBeanPool) {
	int nbPlayersWithContratTeamThatReceiveOffer = 0;
	String kindName = "Players_" + mBeanPool.getPoolID();

	for (String s : playersIdTeamThatReceiveOffer) {

	    Key mKey = KeyFactory.createKey(kindName, s);
	    try {
		Entity entity = datastore.get(mKey);
		Long isUnderContrat = (Long) entity.getProperty("contrat");
		if (isUnderContrat == 1) {
		    nbPlayersWithContratTeamThatReceiveOffer++;
		}

	    } catch (EntityNotFoundException e) {

	    }
	}

	return nbPlayersWithContratTeamThatReceiveOffer;
    }

}
