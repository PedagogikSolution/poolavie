package com.pedagogiksolution.model;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.beans.NonSessionAttaquant;
import com.pedagogiksolution.beans.NonSessionDefenseur;
import com.pedagogiksolution.beans.NonSessionDraftPick;
import com.pedagogiksolution.beans.NonSessionGardien;
import com.pedagogiksolution.beans.NonSessionRecrue;
import com.pedagogiksolution.beans.TradeBeanTemp;
import com.pedagogiksolution.beans.TradeBeans;
import com.pedagogiksolution.cron.model.ClassementCronModel;
import com.pedagogiksolution.cron.model.DraftPickCronModel;
import com.pedagogiksolution.cron.model.PlayersCronModel;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
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

    public void getTradeOfferReceived(HttpServletRequest req, TradeOfferDao tradeOfferDao) {
	TradeBeans mBean = new TradeBeans();

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");

	// on compte le nombre de trade

	int nbTradeReceived = tradeOfferDao.getNumberTradeReceived(mBeanPool.getPoolID(), mBeanUser.getTeamId());

	for (int i = 0; i < nbTradeReceived; i++) {
	    mBean = tradeOfferDao.getTradeOfferReceived(mBeanPool, mBeanPool.getPoolID(), mBeanUser.getTeamId(), i);
	}

	req.setAttribute("tradeOfferReceived", mBean);

    }

    public void getTradeOfferMade(HttpServletRequest req, TradeOfferDao tradeOfferDao) {
	TradeBeans mBean = new TradeBeans();
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");

	// on compte le nombre de trade

	int nbTradeReceived = tradeOfferDao.getNumberTradeMade(mBeanPool.getPoolID(), mBeanUser.getTeamId());

	for (int i = 0; i < nbTradeReceived; i++) {
	    mBean = tradeOfferDao.getTradeOfferMade(mBeanPool, mBeanPool.getPoolID(), mBeanUser.getTeamId(), i);
	}

	req.setAttribute("tradeOfferMade", mBean);

    }

    public void getMyTrade(HttpServletRequest req, TradeMadeDao tradeMadeDao) {
	TradeBeans mBean = new TradeBeans();
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");

	// on compte le nombre de trade

	int nbTradeMade = tradeMadeDao.getNumberTradeMadeByMe(mBeanPool.getPoolID(), mBeanUser.getTeamId());

	for (int i = 0; i < nbTradeMade; i++) {
	    mBean = tradeMadeDao.getTradeMadeByMe(mBeanPool, mBeanPool.getPoolID(), mBeanUser.getTeamId(), i);
	}

	req.setAttribute("tradeOfferMade", mBean);

    }

    public void getAllTrade(HttpServletRequest req, TradeMadeDao tradeMadeDao) {
	TradeBeans mBean = new TradeBeans();
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");

	// on compte le nombre de trade

	int nbTradeMade = tradeMadeDao.getNumberTradeMadeByAll(mBeanPool.getPoolID());

	for (int i = 0; i < nbTradeMade; i++) {
	    mBean = tradeMadeDao.getTradeMadeByAll(mBeanPool, mBeanPool.getPoolID(), mBeanUser.getTeamId(), i);
	}

	req.setAttribute("tradeOfferMade", mBean);

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
	mNonSessionDraftBeanThatMakingOffer.setPick_no(mBeanDraftPickThatMakingOffer.getTotal_pick_number());
	mNonSessionDraftBeanThatMakingOffer.setRonde(mBeanDraftPickThatMakingOffer.getPick_no());
	mNonSessionDraftBeanThatMakingOffer.setTeamNameOriginalPick(mBeanDraftPickThatMakingOffer.getTeamNameOriginalPick());

	mNonSessionDraftBeanThatReceivedOffer.setPick_no(mBeanDraftPickThatReceivedOffer.getTotal_pick_number());
	mNonSessionDraftBeanThatReceivedOffer.setRonde(mBeanDraftPickThatReceivedOffer.getPick_no());
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

	req.setAttribute("TradeWith", teamThatReceivedOfferId);

	return false;

    }

    public Boolean checkIfTradeIsValid(PlayersDao playersDao, DraftPickDao draftPickDao) {

	// retourne false si trade pas possible // retourne true if trade is good to go at this point

	// init variable
	int nbPlayersTeamMakingOffer, nbPlayersTeamReceivingOffer, nbPicksTeamMakingOffer, nbPicksTeamReceivingOffer;
	int nbAttInTeamThatOffer = 0;
	int nbDefInTeamThatOffer = 0;
	int nbGoalInTeamThatOffer = 0;
	int nbRookieTeamMakingOffer = 0;

	int nbAttInTeamThatReceived = 0;
	int nbDefInTeamThatReceived = 0;
	int nbGoalInTeamThatReceived = 0;
	int nbRookieTeamReceivingOffer = 0;

	int nb_attaquant_make_offer = 0;
	int nb_defenseur_make_offer = 0;
	int nb_goaler_make_offer = 0;
	int argent_recu_make_offer = 0;

	int nb_attaquant_rec_offer = 0;
	int nb_defenseur_rec_offer = 0;
	int nb_goaler_rec_offer = 0;
	int argent_recu_rec_offer = 0;

	int budget_restant_make_offer = 0;
	int budget_restant_received_offer = 0;

	int total_salaire_team_making_offer = 0;
	int total_salaire_team_receiving_offer = 0;

	// init objet
	MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	TradeBeans mBean = new TradeBeans();

	// recuperation parametre
	String[] rookie_id_my_team = req.getParameterValues("rookie_id_my_team");

	String[] rookie_id_trade_with_team = req.getParameterValues("rookie_id_trade_with_team");

	if (rookie_id_my_team != null) {
	    nbRookieTeamMakingOffer = rookie_id_my_team.length;
	} else {
	    nbRookieTeamMakingOffer = 0;
	}
	if (rookie_id_trade_with_team != null) {
	    nbRookieTeamReceivingOffer = rookie_id_trade_with_team.length;
	} else {
	    nbRookieTeamReceivingOffer = 0;
	}
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

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();

	// 0 - un joueur ou un pick ou argent contre rien
	if ((nbPlayersTeamMakingOffer == 0 && nbPicksTeamMakingOffer == 0 && cashIncludeTeamThatMakeOfferInt == 0 && nbRookieTeamMakingOffer == 0) || (nbPlayersTeamReceivingOffer == 0 && nbPicksTeamReceivingOffer == 0 && cashIncludeThatReceiveOfferInt == 0 && nbRookieTeamReceivingOffer == 0)) {

	    mBeanMessageErreur.setErreurTrade("L'une des deux équipes ne reçoit rien");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	// 1- check if number of players is 7 or less

	if (nbPlayersTeamMakingOffer > 7 || nbPlayersTeamReceivingOffer > 7) {

	    mBeanMessageErreur.setErreurTrade("Un maximum de 7 joueurs par équipe peut être inclus dans un échange unique (Reglement 4.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	// 2- check if number of pick is 3 or less

	if (nbPicksTeamMakingOffer > 3 || nbPicksTeamReceivingOffer > 3) {
	    mBeanMessageErreur.setErreurTrade("Un maximum de 3 choix au repêchage par équipe peut être inclus dans un échange unique (Reglement 4.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	// 3- checking for cash on the two side
	if (cashIncludeTeamThatMakeOfferInt > 0 && cashIncludeThatReceiveOfferInt > 0) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas échangez de l'argent contre de l'argent (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	// 7- check si echange draft round contre cash **** impossible durant année

	if (mBeanPool.getCycleAnnuel() == 5 || mBeanPool.getCycleAnnuel() == 6) {

	    if ((nbPicksTeamMakingOffer > 0 && cashIncludeThatReceiveOfferInt > 0 && nbPlayersTeamReceivingOffer == 0 && nbRookieTeamReceivingOffer == 0)) {

		mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas échanger un choix contre de l'argent à cette période (Reglement 3.1");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;

	    }

	    if ((nbPicksTeamReceivingOffer > 0 && cashIncludeTeamThatMakeOfferInt > 0 && nbPlayersTeamMakingOffer == 0 && nbRookieTeamMakingOffer == 0)) {

		mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas échanger un choix contre de l'argent à cette période (Reglement 3.1");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;

	    }

	}

	// 8- preparation pour checkup budget et position
	budget_restant_make_offer = mBeanEquipeThatIsMakingOffer.getBudget_restant();
	nb_attaquant_make_offer = mBeanEquipeThatIsMakingOffer.getNb_attaquant();
	nb_defenseur_make_offer = mBeanEquipeThatIsMakingOffer.getNb_defenseur();
	nb_goaler_make_offer = mBeanEquipeThatIsMakingOffer.getNb_gardien();
	argent_recu_make_offer = mBeanEquipeThatIsMakingOffer.getArgent_recu();

	budget_restant_received_offer = mBeanEquipeThatIsReceivingOffer.getBudget_restant();
	nb_attaquant_rec_offer = mBeanEquipeThatIsReceivingOffer.getNb_attaquant();
	nb_defenseur_rec_offer = mBeanEquipeThatIsReceivingOffer.getNb_defenseur();
	nb_goaler_rec_offer = mBeanEquipeThatIsReceivingOffer.getNb_gardien();
	argent_recu_rec_offer = mBeanEquipeThatIsReceivingOffer.getArgent_recu();

	String[] nomMakingOffer = new String[nbPlayersTeamMakingOffer];
	String[] nomReceivingOffer = new String[nbPlayersTeamReceivingOffer];
	String[] RoundPickMakingOffer = new String[nbPicksTeamMakingOffer];
	String[] RoundPickReceivingOffer = new String[nbPicksTeamReceivingOffer];
	String[] FromPickMakingOffer = new String[nbPicksTeamMakingOffer];
	String[] FromPickReceivingOffer = new String[nbPicksTeamReceivingOffer];
	String[] nomRookieMakingOffer = new String[nbRookieTeamMakingOffer];
	String[] nomRookieReceivingOffer = new String[nbRookieTeamReceivingOffer];
	int[] salaireMakingOffer = new int[nbPlayersTeamMakingOffer];
	int[] salaireReceivingOffer = new int[nbPlayersTeamReceivingOffer];
	int[] salaireRookieMakingOffer = new int[nbRookieTeamMakingOffer];
	int[] salaireRookieReceivingOffer = new int[nbRookieTeamReceivingOffer];

	// on calcul le total d'Argent des salaire des joueurs de l'Equipe qui trade, le nombre par position inclus dans
// trade
	// et on ajoute leur nom dans un array

	int i = 0;
	if (playersIdTeamThatMakeOffer != null) {

	    for (String s : playersIdTeamThatMakeOffer) {
		int toInt = Integer.parseInt(s);

		TradeBeanTemp mBeanTemp = playersDao.getPlayersById(poolID, toInt, 0);

		total_salaire_team_making_offer = total_salaire_team_making_offer + mBeanTemp.getTotal_salaire_team_making_offer();
		if (mBeanTemp.getNomMakingOfferString() != null) {
		    nomMakingOffer[i] = mBeanTemp.getNomMakingOfferString();
		    salaireMakingOffer[i] = mBeanTemp.getTotal_salaire_team_making_offer();

		    String positionDuJoueurTrade = mBeanTemp.getPositionDuJoueurTrade();

		    switch (positionDuJoueurTrade) {
		    case "attaquant":
			nbAttInTeamThatOffer = nbAttInTeamThatOffer + 1;
			break;
		    case "defenseur":
			nbDefInTeamThatOffer = nbDefInTeamThatOffer + 1;
			break;
		    case "gardien":
			nbGoalInTeamThatOffer = nbGoalInTeamThatOffer + 1;
			break;

		    }
		}
		i++;

	    }
	}

	// on ajoute les recrues dans un array sans compter leur salaire
	int ii = 0;
	if (rookie_id_my_team != null) {

	    for (String s : rookie_id_my_team) {
		int toInt = Integer.parseInt(s);

		TradeBeanTemp mBeanTemp = playersDao.getPlayersById(poolID, toInt, 1);

		if (mBeanTemp.getNomMakingOfferString() != null) {
		    nomRookieMakingOffer[ii] = mBeanTemp.getNomMakingOfferString();
		    salaireRookieMakingOffer[ii] = mBeanTemp.getTotal_salaire_team_making_offer();
		}
		ii++;

	    }
	}

	// on calcul le total d'Argent des salaire des joueurs de l'Equipe qui recoit et on ajoute leur nom dans un
// array
	int j = 0;
	if (playersIdTeamThatReceiveOffer != null) {

	    for (String s : playersIdTeamThatReceiveOffer) {
		int toInt = Integer.parseInt(s);

		TradeBeanTemp mBeanTemp = playersDao.getPlayersById(poolID, toInt, 0);

		total_salaire_team_receiving_offer = total_salaire_team_receiving_offer + mBeanTemp.getTotal_salaire_team_making_offer();

		if (mBeanTemp.getNomMakingOfferString() != null) {
		    nomReceivingOffer[j] = mBeanTemp.getNomMakingOfferString();
		    salaireReceivingOffer[j] = mBeanTemp.getTotal_salaire_team_making_offer();
		    String positionDuJoueurTrade = mBeanTemp.getPositionDuJoueurTrade();

		    switch (positionDuJoueurTrade) {
		    case "attaquant":
			nbAttInTeamThatReceived = nbAttInTeamThatReceived + 1;
			break;
		    case "defenseur":
			nbDefInTeamThatReceived = nbDefInTeamThatReceived + 1;
			break;
		    case "gardien":
			nbGoalInTeamThatReceived = nbGoalInTeamThatReceived + 1;
			break;

		    }
		}

		j++;

	    }
	}

	// on ajoute les recrues dans un array sans compter leur salaire
	int jj = 0;
	if (rookie_id_trade_with_team != null) {

	    for (String s : rookie_id_trade_with_team) {
		int toInt = Integer.parseInt(s);

		TradeBeanTemp mBeanTemp = playersDao.getPlayersById(poolID, toInt, 1);

		if (mBeanTemp.getNomMakingOfferString() != null) {
		    nomRookieReceivingOffer[jj] = mBeanTemp.getNomMakingOfferString();
		    salaireRookieReceivingOffer[jj] = mBeanTemp.getTotal_salaire_team_making_offer();
		}
		jj++;

	    }
	}

	// check si le nombre par position va resister l'echange (min 8 attaquant, 5 def et 2 goal)

	if ((nb_attaquant_make_offer - nbAttInTeamThatOffer + nbAttInTeamThatReceived) < 8) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine de vous retrouver avec moins de 8 attaquants (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_attaquant_rec_offer + nbAttInTeamThatOffer - nbAttInTeamThatReceived) < 8) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine que la personne avec qui vous échangez se retrouve avec moins de 8 attaquants (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_defenseur_make_offer - nbDefInTeamThatOffer + nbDefInTeamThatReceived) < 5) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine de vous retrouver avec moins de 5 defenseurs (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_defenseur_rec_offer + nbDefInTeamThatOffer - nbDefInTeamThatReceived) < 5) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine que la personne avec qui vous échangez se retrouve avec moins de 5 defenseurs (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_goaler_make_offer - nbGoalInTeamThatOffer + nbGoalInTeamThatReceived) < 2) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine de vous retrouver avec moins de 2 gardiens (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_goaler_rec_offer + nbGoalInTeamThatOffer - nbGoalInTeamThatReceived) < 2) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine que la personne avec qui vous échangez se retrouve avec moins de 2 gardiens (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	// check si budget pour abosrber la transaction

	if ((budget_restant_make_offer + total_salaire_team_making_offer - total_salaire_team_receiving_offer - argent_recu_make_offer + argent_recu_rec_offer + cashIncludeTeamThatMakeOfferInt - cashIncludeThatReceiveOfferInt) < 0) {

	    mBeanMessageErreur.setErreurTrade("Vous n'avez pas assez d'argent pour effectuer cette échange (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;

	}

	if ((budget_restant_received_offer - total_salaire_team_making_offer + total_salaire_team_receiving_offer + argent_recu_make_offer - argent_recu_rec_offer + cashIncludeThatReceiveOfferInt - cashIncludeTeamThatMakeOfferInt) < 0) {

	    mBeanMessageErreur.setErreurTrade("La personne avec qui vous voulez échangez n'a pas le budget pour absorber cette transaction (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;

	}

	// roundpick et frompick a faire pour la persistence
	if (picksIdTeamThatMakeOffer != null) {
	    int k = 0;
	    for (String s : picksIdTeamThatMakeOffer) {
		int toInt = Integer.parseInt(s);

		TradeBeanTemp mBeanTemp = draftPickDao.getNameOfTeam(poolID, toInt, mBeanPool);

		RoundPickMakingOffer[k] = mBeanTemp.getRoundPick();
		FromPickMakingOffer[k] = mBeanTemp.getFromPick();

		k++;

	    }
	}

	if (picksIdTeamThatReceiveOffer != null) {
	    int l = 0;
	    for (String s : picksIdTeamThatReceiveOffer) {
		int toInt = Integer.parseInt(s);

		TradeBeanTemp mBeanTemp = draftPickDao.getNameOfTeam(poolID, toInt, mBeanPool);

		RoundPickReceivingOffer[l] = mBeanTemp.getRoundPick();
		FromPickReceivingOffer[l] = mBeanTemp.getFromPick();

		l++;

	    }
	}

	String messageVente = req.getParameter("message_vente");
	mBean.setMessageOffre(messageVente);

	mBean.setPlayerIdMakingOffer(playersIdTeamThatMakeOffer);
	mBean.setPlayerIdReceivingOffer(playersIdTeamThatReceiveOffer);
	mBean.setRookieIdTeamMakingOffer(rookie_id_my_team);
	mBean.setRookieNomMakingOffer(nomRookieMakingOffer);
	mBean.setRookieIdTeamReceivingOffer(rookie_id_trade_with_team);
	mBean.setRookieNomReceivingOffer(nomRookieReceivingOffer);
	mBean.setPickNumMakingOffer(picksIdTeamThatMakeOffer);
	mBean.setPickNumReceivingOffer(picksIdTeamThatReceiveOffer);
	mBean.setCashMakingOffer(cashIncludeTeamThatMakeOfferInt);
	mBean.setCashReceivingOffer(cashIncludeThatReceiveOfferInt);
	mBean.setNomMakingOffer(nomMakingOffer);
	mBean.setNomReceivingOffer(nomReceivingOffer);
	mBean.setRoundPickMakingOffer(RoundPickMakingOffer);
	mBean.setRoundPickReceivingOffer(RoundPickReceivingOffer);
	mBean.setFromPickMakingOffer(FromPickMakingOffer);
	mBean.setFromPickReceivingOffer(FromPickReceivingOffer);
	mBean.setTeamThatTrade(teamId);
	mBean.setTeamTradeTo(Integer.parseInt(tradeWithID));
	mBean.setSalaireMakingOffer(salaireMakingOffer);
	mBean.setSalaireReceivingOffer(salaireReceivingOffer);
	mBean.setSalaireRookieMakingOffer(salaireRookieMakingOffer);
	mBean.setSalaireRookieReceivingOffer(salaireRookieReceivingOffer);

	req.getSession().setAttribute("tradeOfferBean", mBean);

	return true;
    }

    public void persistTradeOffer(HttpServletRequest req, TradeOfferDao tradeOfferDao) {

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	int annee = mBeanPool.getPoolYear();

	TradeBeans mBean = new TradeBeans();
	mBean = (TradeBeans) req.getSession().getAttribute("tradeOfferBean");

	List<String> playersIdMakingOfferTemp = new ArrayList<String>();

	if (mBean.getPlayerIdMakingOffer() != null) {
	    for (String s : mBean.getPlayerIdMakingOffer()) {
		playersIdMakingOfferTemp.add(s);
	    }
	}
	if (mBean.getRookieIdTeamMakingOffer() != null) {
	    for (String s : mBean.getRookieIdTeamMakingOffer()) {
		playersIdMakingOfferTemp.add(s);
	    }
	}

	List<String> playersIdReceivingOfferTemp = new ArrayList<String>();

	if (mBean.getPlayerIdReceivingOffer() != null) {
	    for (String s : mBean.getPlayerIdReceivingOffer()) {
		playersIdReceivingOfferTemp.add(s);
	    }
	}
	if (mBean.getRookieIdTeamReceivingOffer() != null) {
	    for (String s : mBean.getRookieIdTeamReceivingOffer()) {
		playersIdReceivingOfferTemp.add(s);
	    }
	}

	String[] pickMakingOffer = mBean.getPickNumMakingOffer();
	String[] pickReceivingOffer = mBean.getPickNumReceivingOffer();
	int cashMakingOffer = mBean.getCashMakingOffer();
	int cashReceivingOffer = mBean.getCashReceivingOffer();
	int teamMakingOfferId = mBean.getTeamThatTrade();
	int teamReceivingOfferId = mBean.getTeamTradeTo();
	String messageOffre = mBean.getMessageOffre();

	String t1j1 = null, t1j2 = null, t1j3 = null, t1j4 = null, t1j5 = null, t1j6 = null, t1j7 = null, t2j1 = null, t2j2 = null, t2j3 = null, t2j4 = null, t2j5 = null, t2j6 = null, t2j7 = null, t1p1 = null, t1p2 = null, t1p3 = null, t2p1 = null, t2p2 = null, t2p3 = null;
	int number_of_player_trade_by = 0;
	int number_of_pick_trade_by = 0;
	int number_of_player_trade_to = 0;
	int number_of_pick_trade_to = 0;

	if (playersIdMakingOfferTemp != null) {
	    number_of_player_trade_by = playersIdMakingOfferTemp.size();
	}
	if (pickMakingOffer != null) {
	    number_of_pick_trade_by = pickMakingOffer.length;
	}
	if (playersIdReceivingOfferTemp != null) {
	    number_of_player_trade_to = playersIdReceivingOfferTemp.size();
	}
	if (pickReceivingOffer != null) {
	    number_of_pick_trade_to = pickReceivingOffer.length;
	}

	if (playersIdMakingOfferTemp != null) {
	    switch (number_of_player_trade_by) {

	    case 1:
		t1j1 = playersIdMakingOfferTemp.get(0);
		t1j2 = null;
		t1j3 = null;
		t1j4 = null;
		t1j5 = null;
		t1j6 = null;
		t1j7 = null;

		break;
	    case 2:
		t1j1 = playersIdMakingOfferTemp.get(0);
		t1j2 = playersIdMakingOfferTemp.get(1);
		t1j3 = null;
		t1j4 = null;
		t1j5 = null;
		t1j6 = null;
		t1j7 = null;

		break;
	    case 3:
		t1j1 = playersIdMakingOfferTemp.get(0);
		t1j2 = playersIdMakingOfferTemp.get(1);
		t1j3 = playersIdMakingOfferTemp.get(2);
		t1j4 = null;
		t1j5 = null;
		t1j6 = null;
		t1j7 = null;

		break;
	    case 4:
		t1j1 = playersIdMakingOfferTemp.get(0);
		t1j2 = playersIdMakingOfferTemp.get(1);
		t1j3 = playersIdMakingOfferTemp.get(2);
		t1j4 = playersIdMakingOfferTemp.get(3);
		t1j5 = null;
		t1j6 = null;
		t1j7 = null;

		break;
	    case 5:
		t1j1 = playersIdMakingOfferTemp.get(0);
		t1j2 = playersIdMakingOfferTemp.get(1);
		t1j3 = playersIdMakingOfferTemp.get(2);
		t1j4 = playersIdMakingOfferTemp.get(3);
		t1j5 = playersIdMakingOfferTemp.get(4);
		t1j6 = null;
		t1j7 = null;

		break;
	    case 6:
		t1j1 = playersIdMakingOfferTemp.get(0);
		t1j2 = playersIdMakingOfferTemp.get(1);
		t1j3 = playersIdMakingOfferTemp.get(2);
		t1j4 = playersIdMakingOfferTemp.get(3);
		t1j5 = playersIdMakingOfferTemp.get(4);
		t1j6 = playersIdMakingOfferTemp.get(5);
		t1j7 = null;

		break;
	    case 7:
		t1j1 = playersIdMakingOfferTemp.get(0);
		t1j2 = playersIdMakingOfferTemp.get(1);
		t1j3 = playersIdMakingOfferTemp.get(2);
		t1j4 = playersIdMakingOfferTemp.get(3);
		t1j5 = playersIdMakingOfferTemp.get(4);
		t1j6 = playersIdMakingOfferTemp.get(5);
		t1j7 = playersIdMakingOfferTemp.get(6);
		break;

	    }

	}

	if (pickMakingOffer != null) {
	    switch (number_of_pick_trade_by) {
	    case 1:
		t1p1 = pickMakingOffer[0];
		t1p2 = null;
		t1p3 = null;

		break;
	    case 2:
		t1p1 = pickMakingOffer[0];
		t1p2 = pickMakingOffer[1];
		t1p3 = null;

		break;
	    case 3:
		t1p1 = pickMakingOffer[0];
		t1p2 = pickMakingOffer[1];
		t1p3 = pickMakingOffer[2];

		break;

	    }

	}

	if (playersIdReceivingOfferTemp != null) {
	    switch (number_of_player_trade_to) {

	    case 1:
		t2j1 = playersIdReceivingOfferTemp.get(0);
		t2j2 = null;
		t2j3 = null;
		t2j4 = null;
		t2j5 = null;
		t2j6 = null;
		t2j7 = null;

		break;
	    case 2:
		t2j1 = playersIdReceivingOfferTemp.get(0);
		t2j2 = playersIdReceivingOfferTemp.get(1);
		t2j3 = null;
		t2j4 = null;
		t2j5 = null;
		t2j6 = null;
		t2j7 = null;

		break;
	    case 3:
		t2j1 = playersIdReceivingOfferTemp.get(0);
		t2j2 = playersIdReceivingOfferTemp.get(1);
		t2j3 = playersIdReceivingOfferTemp.get(2);
		t2j4 = null;
		t2j5 = null;
		t2j6 = null;
		t2j7 = null;

		break;
	    case 4:
		t2j1 = playersIdReceivingOfferTemp.get(0);
		t2j2 = playersIdReceivingOfferTemp.get(1);
		t2j3 = playersIdReceivingOfferTemp.get(2);
		t2j4 = playersIdReceivingOfferTemp.get(3);
		t2j5 = null;
		t2j6 = null;
		t2j7 = null;

		break;
	    case 5:
		t2j1 = playersIdReceivingOfferTemp.get(0);
		t2j2 = playersIdReceivingOfferTemp.get(1);
		t2j3 = playersIdReceivingOfferTemp.get(2);
		t2j4 = playersIdReceivingOfferTemp.get(3);
		t2j5 = playersIdReceivingOfferTemp.get(4);
		t2j6 = null;
		t2j7 = null;

		break;
	    case 6:
		t2j1 = playersIdReceivingOfferTemp.get(0);
		t2j2 = playersIdReceivingOfferTemp.get(1);
		t2j3 = playersIdReceivingOfferTemp.get(2);
		t2j4 = playersIdReceivingOfferTemp.get(3);
		t2j5 = playersIdReceivingOfferTemp.get(4);
		t2j6 = playersIdReceivingOfferTemp.get(5);
		t2j7 = null;

		break;
	    case 7:
		t2j1 = playersIdReceivingOfferTemp.get(0);
		t2j2 = playersIdReceivingOfferTemp.get(1);
		t2j3 = playersIdReceivingOfferTemp.get(2);
		t2j4 = playersIdReceivingOfferTemp.get(3);
		t2j5 = playersIdReceivingOfferTemp.get(4);
		t2j6 = playersIdReceivingOfferTemp.get(5);
		t2j7 = playersIdReceivingOfferTemp.get(6);
		break;

	    }

	}

	if (pickReceivingOffer != null) {
	    switch (number_of_pick_trade_to) {
	    case 1:
		t2p1 = pickReceivingOffer[0];
		t2p2 = null;
		t2p3 = null;

		break;
	    case 2:
		t2p1 = pickReceivingOffer[0];
		t2p2 = pickReceivingOffer[1];
		t2p3 = null;

		break;
	    case 3:
		t2p1 = pickReceivingOffer[0];
		t2p2 = pickReceivingOffer[1];
		t2p3 = pickReceivingOffer[2];

		break;

	    }

	}

	String poolID = mBeanPool.getPoolID();

	tradeOfferDao.insertTradeOffer(poolID, teamMakingOfferId, teamReceivingOfferId, t1j1, t1j2, t1j3, t1j4, t1j5, t1j6, t1j7, t2j1, t2j2, t2j3, t2j4, t2j5, t2j6, t2j7, t1p1, t1p2, t1p3, t2p1, t2p2, t2p3, cashMakingOffer, cashReceivingOffer, 0, annee, messageOffre);

    }

    public void sendAlertViaChannel() {
	// TODO Auto-generated method stub

    }

    public void sendEmailForOffer(HttpServletRequest req) {

	TradeBeans mBean = new TradeBeans();
	mBean = (TradeBeans) req.getSession().getAttribute("tradeOfferBean");
	int teamReceivingOfferId = mBean.getTeamTradeTo();

	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);

	FilterPredicate noContrat = new FilterPredicate("teamId", FilterOperator.EQUAL, teamReceivingOfferId);
	FilterPredicate byPosition = new FilterPredicate("poolId", FilterOperator.EQUAL, poolId);
	CompositeFilter mFiltre = CompositeFilterOperator.and(noContrat, byPosition);

	// on appel le service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// Use class Query to assemble a query
	Query q = new Query("Utilisateur");
	q.setFilter(mFiltre);

	PreparedQuery pq = datastore.prepare(q);
	Entity entity = pq.asSingleEntity();

	// Long typeUser = (Long) results.getProperty("typeUtilisateur");

	String courriel = (String) entity.getProperty("courriel");
	String nomTeam = (String) entity.getProperty("teamName");
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	try {
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("pedagogiksolution@gmail.com", "Poolavie.ca"));
	    msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(courriel));
	    msg.setSubject("Vous avez une offre de trade", "utf-8");
	    msg.setContent("Bonjour " + nomTeam + ", \n\n Vous avez reçu une nouvelle offre d'échange. Pour voir celle-ci, l'accepter, la refuser ou faire une contre offre. Connectez-vous et aller dans la section Trade Center.", "text/html");
	    Transport.send(msg);
	} catch (AddressException e) {
	    // ...
	} catch (MessagingException e) {
	    // ...
	} catch (UnsupportedEncodingException e) {
	    // ...
	}

    }

    public void showOfferNumberX(HttpServletRequest req, int makeOrOffer, TradeOfferDao tradeOfferDao, PlayersDao playersDao, DraftPickDao draftPickDao) {

	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);

// initialisation des objets pour le metier
	TradeBeans mBean = new TradeBeans();
	mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	mBeanPool = (Pool) req.getSession().getAttribute("Pool");

	mBean = tradeOfferDao.showOfferX(mBeanPool, mBeanUser, trade_id, playersDao, draftPickDao);

	req.getSession().setAttribute("tradeOfferBean", mBean);

    }

    public void annulerOffre(HttpServletRequest req, TradeOfferDao tradeOfferDao) {

	String trade_id = req.getParameter("trade_id");
	int trade_id_int = Integer.parseInt(trade_id);
	mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	tradeOfferDao.cancelOffreX(trade_id_int, mBeanPool);

    }

    public Boolean checkIfTradeIsStillPossible(PlayersDao playersDao, DraftPickDao draftPickDao, TradeOfferDao tradeOfferDao) {
	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);

	ArrayList<String> playersTeamThatOfferTemp = new ArrayList<String>();
	ArrayList<String> playersTeamThatReceivedTemp = new ArrayList<String>();
	String[] playersTeamThatOffer = null;
	String[] playersTeamThatReceived = null;

	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);

	TradeBeanTemp mBeanTemp = new TradeBeanTemp();

	mBeanTemp = tradeOfferDao.getTradeNumberX(poolId, trade_id);

	MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();

	if (mBeanTemp.getT1j1() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1j1(), playersDao);
	    if (!isStillInTeam) {

		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatOfferTemp.add(mBeanTemp.getT1j1());
	}
	if (mBeanTemp.getT1j2() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1j2(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;

	    }
	    playersTeamThatOfferTemp.add(mBeanTemp.getT1j2());
	}
	if (mBeanTemp.getT1j3() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1j3(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;

	    }
	    playersTeamThatOfferTemp.add(mBeanTemp.getT1j3());
	}
	if (mBeanTemp.getT1j4() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1j4(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;

	    }
	    playersTeamThatOfferTemp.add(mBeanTemp.getT1j4());
	}
	if (mBeanTemp.getT1j5() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1j5(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatOfferTemp.add(mBeanTemp.getT1j5());
	}
	if (mBeanTemp.getT1j6() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1j6(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatOfferTemp.add(mBeanTemp.getT1j6());
	}
	if (mBeanTemp.getT1j7() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1j7(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatOfferTemp.add(mBeanTemp.getT1j7());
	}
	if (mBeanTemp.getT2j1() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2j1(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatReceivedTemp.add(mBeanTemp.getT2j1());
	}
	if (mBeanTemp.getT2j2() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2j2(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatReceivedTemp.add(mBeanTemp.getT2j2());
	}
	if (mBeanTemp.getT2j3() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2j3(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatReceivedTemp.add(mBeanTemp.getT2j3());
	}
	if (mBeanTemp.getT2j4() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2j4(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatReceivedTemp.add(mBeanTemp.getT2j4());
	}
	if (mBeanTemp.getT2j5() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2j5(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatReceivedTemp.add(mBeanTemp.getT2j5());
	}
	if (mBeanTemp.getT2j6() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2j6(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatReceivedTemp.add(mBeanTemp.getT2j6());
	}
	if (mBeanTemp.getT2j7() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2j7(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des joueurs impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	    playersTeamThatReceivedTemp.add(mBeanTemp.getT2j7());
	}
	if (mBeanTemp.getT1p1() != null) {
	    Boolean isStillInTeam = checkIfplayerStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1p1(), playersDao);
	    if (!isStillInTeam) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des picks impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }
	}

	if (mBeanTemp.getT1p2() != null) {
	    Boolean isStillInPick = checkIfPickStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1p2(), draftPickDao);
	    if (!isStillInPick) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des picks impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }

	}

	if (mBeanTemp.getT1p3() != null) {
	    Boolean isStillInPick = checkIfPickStillInTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT1p3(), draftPickDao);
	    if (!isStillInPick) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des picks impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }

	}
	if (mBeanTemp.getT2p1() != null) {
	    Boolean isStillInPick = checkIfPickStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2p1(), draftPickDao);
	    if (!isStillInPick) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des picks impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }

	}
	if (mBeanTemp.getT2p2() != null) {
	    Boolean isStillInPick = checkIfPickStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2p2(), draftPickDao);
	    if (!isStillInPick) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des picks impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }

	}
	if (mBeanTemp.getT2p3() != null) {
	    Boolean isStillInPick = checkIfPickStillInTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT2p3(), draftPickDao);
	    if (!isStillInPick) {
		mBeanMessageErreur.setErreurTrade("Vous n'avez plus l'un des picks impliqués dans l'échange");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return false;
	    }

	}

	playersTeamThatOffer = playersTeamThatOfferTemp.toArray(new String[playersTeamThatOfferTemp.size()]);
	playersTeamThatReceived = playersTeamThatReceivedTemp.toArray(new String[playersTeamThatReceivedTemp.size()]);

	boolean checkIfTradeIsStillValidateByRule = checkIfTradeIsStillValidateByRule(playersDao, req, mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), playersTeamThatOffer, playersTeamThatReceived, mBeanTemp.getT1_cash(), mBeanTemp.getT2_cash());

	if (!checkIfTradeIsStillValidateByRule) {
	    return false;
	}

	return true;
    }

    private boolean checkIfTradeIsStillValidateByRule(PlayersDao playersDao, HttpServletRequest req, String team_1, String team_2, String[] playersTeamThatOffer, String[] playersTeamThatReceived, int t1_cash, int t2_cash) {

	int nbPlayersTeamMakingOffer, nbPlayersTeamReceivingOffer;
	int nbAttInTeamThatOffer = 0;
	int nbDefInTeamThatOffer = 0;
	int nbGoalInTeamThatOffer = 0;

	int nbAttInTeamThatReceived = 0;
	int nbDefInTeamThatReceived = 0;
	int nbGoalInTeamThatReceived = 0;

	int nb_attaquant_make_offer = 0;
	int nb_defenseur_make_offer = 0;
	int nb_goaler_make_offer = 0;
	int argent_recu_make_offer = 0;

	int nb_attaquant_rec_offer = 0;
	int nb_defenseur_rec_offer = 0;
	int nb_goaler_rec_offer = 0;
	int argent_recu_rec_offer = 0;

	int budget_restant_make_offer = 0;
	int budget_restant_received_offer = 0;

	int total_salaire_team_making_offer = 0;
	int total_salaire_team_receiving_offer = 0;

	// init objet
	MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();

	String[] playersIdTeamThatMakeOffer = playersTeamThatOffer;
	String[] playersIdTeamThatReceiveOffer = playersTeamThatReceived;

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

	int cashIncludeTeamThatMakeOfferInt = t1_cash;
	int cashIncludeThatReceiveOfferInt = t2_cash;

	String tradeWithID = team_1;

	String teamId = team_2;

	String nomTeamThatOffer = "Equipe" + teamId;
	String nomTeamThatReceived = "Equipe" + tradeWithID;

	Equipe mBeanEquipeThatIsMakingOffer = (Equipe) req.getSession().getAttribute(nomTeamThatOffer);
	Equipe mBeanEquipeThatIsReceivingOffer = (Equipe) req.getSession().getAttribute(nomTeamThatReceived);

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();

	budget_restant_make_offer = mBeanEquipeThatIsMakingOffer.getBudget_restant();
	nb_attaquant_make_offer = mBeanEquipeThatIsMakingOffer.getNb_attaquant();
	nb_defenseur_make_offer = mBeanEquipeThatIsMakingOffer.getNb_defenseur();
	nb_goaler_make_offer = mBeanEquipeThatIsMakingOffer.getNb_gardien();
	argent_recu_make_offer = mBeanEquipeThatIsMakingOffer.getArgent_recu();

	budget_restant_received_offer = mBeanEquipeThatIsReceivingOffer.getBudget_restant();
	nb_attaquant_rec_offer = mBeanEquipeThatIsReceivingOffer.getNb_attaquant();
	nb_defenseur_rec_offer = mBeanEquipeThatIsReceivingOffer.getNb_defenseur();
	nb_goaler_rec_offer = mBeanEquipeThatIsReceivingOffer.getNb_gardien();
	argent_recu_rec_offer = mBeanEquipeThatIsReceivingOffer.getArgent_recu();

	String[] nomMakingOffer = new String[nbPlayersTeamMakingOffer];
	String[] nomReceivingOffer = new String[nbPlayersTeamReceivingOffer];

	// on calcul le total d'Argent des salaire des joueurs de l'Equipe qui trade, le nombre par position inclus dans
// trade
	// et on ajoute leur nom dans un array

	int i = 0;
	if (playersIdTeamThatMakeOffer != null) {

	    for (String s : playersIdTeamThatMakeOffer) {
		int toInt = Integer.parseInt(s);

		TradeBeanTemp mBeanTemp = playersDao.getPlayersById(poolID, toInt, 0);

		total_salaire_team_making_offer = total_salaire_team_making_offer + mBeanTemp.getTotal_salaire_team_making_offer();
		if (mBeanTemp.getNomMakingOfferString() != null) {
		    nomMakingOffer[i] = mBeanTemp.getNomMakingOfferString();

		    String positionDuJoueurTrade = mBeanTemp.getPositionDuJoueurTrade();

		    switch (positionDuJoueurTrade) {
		    case "attaquant":
			nbAttInTeamThatOffer = nbAttInTeamThatOffer + 1;
			break;
		    case "defenseur":
			nbDefInTeamThatOffer = nbDefInTeamThatOffer + 1;
			break;
		    case "gardien":
			nbGoalInTeamThatOffer = nbGoalInTeamThatOffer + 1;
			break;

		    }
		}
		i++;

	    }
	}

	// on calcul le total d'Argent des salaire des joueurs de l'Equipe qui recoit et on ajoute leur nom dans un
// array
	int j = 0;
	if (playersIdTeamThatReceiveOffer != null) {

	    for (String s : playersIdTeamThatReceiveOffer) {
		int toInt = Integer.parseInt(s);

		TradeBeanTemp mBeanTemp = playersDao.getPlayersById(poolID, toInt, 0);

		total_salaire_team_receiving_offer = total_salaire_team_receiving_offer + mBeanTemp.getTotal_salaire_team_making_offer();

		if (mBeanTemp.getNomMakingOfferString() != null) {
		    nomReceivingOffer[j] = mBeanTemp.getNomMakingOfferString();

		    String positionDuJoueurTrade = mBeanTemp.getPositionDuJoueurTrade();

		    switch (positionDuJoueurTrade) {
		    case "attaquant":
			nbAttInTeamThatReceived = nbAttInTeamThatReceived + 1;
			break;
		    case "defenseur":
			nbDefInTeamThatReceived = nbDefInTeamThatReceived + 1;
			break;
		    case "gardien":
			nbGoalInTeamThatReceived = nbGoalInTeamThatReceived + 1;
			break;

		    }
		}

		j++;

	    }
	}

	// check si le nombre par position va resister l'echange (min 8 attaquant, 5 def et 2 goal)

	if ((nb_attaquant_make_offer + nbAttInTeamThatOffer - nbAttInTeamThatReceived) < 8) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine de vous retrouver avec moins de 8 attaquants (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_attaquant_rec_offer - nbAttInTeamThatOffer + nbAttInTeamThatReceived) < 8) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine que la personne avec qui vous échangez se retrouve avec moins de 8 attaquants (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_defenseur_make_offer + nbDefInTeamThatOffer - nbDefInTeamThatReceived) < 5) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine de vous retrouver avec moins de 5 defenseurs (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_defenseur_rec_offer - nbDefInTeamThatOffer + nbDefInTeamThatReceived) < 5) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine que la personne avec qui vous échangez se retrouve avec moins de 5 defenseurs (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_goaler_make_offer + nbGoalInTeamThatOffer - nbGoalInTeamThatReceived) < 2) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine de vous retrouver avec moins de 2 gardiens (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	if ((nb_goaler_rec_offer - nbGoalInTeamThatOffer + nbGoalInTeamThatReceived) < 2) {
	    mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire cette échange sous peine que la personne avec qui vous échangez se retrouve avec moins de 2 gardiens (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;
	}

	// check si budget pour abosrber la transaction

	if ((budget_restant_make_offer + total_salaire_team_making_offer - total_salaire_team_receiving_offer - argent_recu_make_offer + argent_recu_rec_offer + cashIncludeTeamThatMakeOfferInt - cashIncludeThatReceiveOfferInt) < 0) {

	    mBeanMessageErreur.setErreurTrade("Vous n'avez pas assez d'argent pour effectuer cette échange (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;

	}

	if ((budget_restant_received_offer - total_salaire_team_making_offer + total_salaire_team_receiving_offer + argent_recu_make_offer - argent_recu_rec_offer + cashIncludeThatReceiveOfferInt - cashIncludeTeamThatMakeOfferInt) < 0) {

	    mBeanMessageErreur.setErreurTrade("La personne avec qui vous voulez échangez n'a pas le budget pour absorber cette transaction (Reglement 3.1");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return false;

	}

	return true;

    }

    public void makeTrade(PlayersDao playersDao, DraftPickDao draftPickDao, TradeOfferDao tradeOfferDao) {

	TradeBeanTemp mBeanTemp = new TradeBeanTemp();

	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);

	String tradeId = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(tradeId);

	mBeanTemp = tradeOfferDao.getTradeNumberX(poolId, trade_id);

	if (mBeanTemp.getT1j1() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), mBeanTemp.getT1j1(), playersDao);
	}
	if (mBeanTemp.getT1j2() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), mBeanTemp.getT1j2(), playersDao);
	}
	if (mBeanTemp.getT1j3() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), mBeanTemp.getT1j3(), playersDao);
	}
	if (mBeanTemp.getT1j4() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), mBeanTemp.getT1j4(), playersDao);
	}
	if (mBeanTemp.getT1j5() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), mBeanTemp.getT1j5(), playersDao);
	}
	if (mBeanTemp.getT1j6() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), mBeanTemp.getT1j6(), playersDao);
	}
	if (mBeanTemp.getT1j7() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), mBeanTemp.getT1j7(), playersDao);
	}
	if (mBeanTemp.getT2j1() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getTeam_1(), mBeanTemp.getT2j1(), playersDao);
	}
	if (mBeanTemp.getT2j2() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getTeam_1(), mBeanTemp.getT2j2(), playersDao);
	}
	if (mBeanTemp.getT2j3() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getTeam_1(), mBeanTemp.getT2j3(), playersDao);
	}
	if (mBeanTemp.getT2j4() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getTeam_1(), mBeanTemp.getT2j4(), playersDao);
	}
	if (mBeanTemp.getT2j5() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getTeam_1(), mBeanTemp.getT2j5(), playersDao);
	}
	if (mBeanTemp.getT2j6() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getTeam_1(), mBeanTemp.getT2j6(), playersDao);
	}
	if (mBeanTemp.getT2j7() != null) {
	    movePlayersFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getTeam_1(), mBeanTemp.getT2j7(), playersDao);
	}
	if (mBeanTemp.getT1p1() != null) {
	    movePickFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT1p1(), draftPickDao);
	}
	if (mBeanTemp.getT1p2() != null) {
	    movePickFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT1p2(), draftPickDao);
	}
	if (mBeanTemp.getT1p3() != null) {
	    movePickFromFirstToSecondTeam(mBeanTemp.getTeam_2(), mBeanTemp.getT1p3(), draftPickDao);
	}
	if (mBeanTemp.getT2p1() != null) {
	    movePickFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT2p1(), draftPickDao);
	}
	if (mBeanTemp.getT2p2() != null) {
	    movePickFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT2p2(), draftPickDao);
	}
	if (mBeanTemp.getT2p3() != null) {
	    movePickFromFirstToSecondTeam(mBeanTemp.getTeam_1(), mBeanTemp.getT2p3(), draftPickDao);
	}
	if (mBeanTemp.getT1_cash() > 0) {
	    movCashFromTeamtoArgentRecu(mBeanTemp.getTeam_1(), mBeanTemp.getTeam_2(), mBeanTemp.getT1_cash());
	}
	if (mBeanTemp.getT2_cash() > 0) {
	    movCashFromTeamtoArgentRecu(mBeanTemp.getTeam_2(), mBeanTemp.getTeam_1(), mBeanTemp.getT2_cash());
	}

    }

    private void movCashFromTeamtoArgentRecu(String team1, String team2, int cash) {

	String poolID = mBeanPool.getPoolID();

	String nomEquipeA = poolID + "_" + team1;
	String nomEquipeB = poolID + "_" + team2;

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key mKeyEquipeA = KeyFactory.createKey("Equipe", nomEquipeA);
	Key mKeyEquipeB = KeyFactory.createKey("Equipe", nomEquipeB);

	try {
	    Entity mEntityEquipe = datastore.get(mKeyEquipeA);

	    mEntityEquipe.setProperty("max_salaire_begin", (((Long) mEntityEquipe.getProperty("max_salaire_begin")) - cash));
	    mEntityEquipe.setProperty("budget_restant", (((Long) mEntityEquipe.getProperty("budget_restant")) - cash));

	} catch (EntityNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	try {
	    Entity mEntityEquipe = datastore.get(mKeyEquipeB);

	    mEntityEquipe.setProperty("max_salaire_begin", (((Long) mEntityEquipe.getProperty("max_salaire_begin")) + cash));
	    mEntityEquipe.setProperty("argent_recu", (((Long) mEntityEquipe.getProperty("argent_recu")) + cash));

	} catch (EntityNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    private void movePickFromFirstToSecondTeam(String team, String round, DraftPickDao draftPickDao) {

	int teamId1 = Integer.parseInt(team);
	int roundId2 = Integer.parseInt(round);

	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);

	draftPickDao.makeTrade(poolId, teamId1, roundId2);

    }

    private void movePlayersFromFirstToSecondTeam(String team1, String team2, String playerId, PlayersDao playersDao) {

	int teamId1 = Integer.parseInt(team1);
	int teamId2 = Integer.parseInt(team2);
	int playerId2 = Integer.parseInt(playerId);

	playersDao.makeTrade(mBeanPool, teamId1, teamId2, playerId2);

    }

    public void persistTrade(TradeMadeDao tradeMadeDao) {
	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);
	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);

	tradeMadeDao.insertTradeMade(poolId, trade_id);

    }

    /******************************* methode privée à la classe **********************************/

    private Boolean checkIfplayerStillInTeam(String team, String identifiant, PlayersDao playersDao) {
	int teamId = Integer.parseInt(team);
	int playerId = Integer.parseInt(identifiant);
	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);
	Boolean checkIfPlayersStillInTeam = playersDao.checkIfPlayersStillInTeam(poolId, teamId, playerId);
	return checkIfPlayersStillInTeam;
    }

    private Boolean checkIfPickStillInTeam(String team, String identifiant, DraftPickDao draftPickDao) {
	int teamId = Integer.parseInt(team);
	int pickId = Integer.parseInt(identifiant);
	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);
	Boolean checkIfPicksStillInTeam = draftPickDao.checkIfPicksStillInTeam(poolId, teamId, pickId);
	return checkIfPicksStillInTeam;
    }

    public void putDatabaseInDatastore(ClassementDao classementDao, PlayersDao playersDao, DraftPickDao draftPickDao, HttpServletRequest req) {

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);
	PlayersCronModel mModelPlayer = new PlayersCronModel(playersDao);
	ClassementCronModel mModelClassement = new ClassementCronModel(classementDao, playersDao);
	DraftPickCronModel mModelDraft = new DraftPickCronModel(draftPickDao);
	int numberOfTeam = mModelPlayer.getNumberOfTeamByPool(poolId);
	// Attaquant

	String position = "attaquant";
	int recrue = 0;
	mModelPlayer.putDatabaseInDatastore(poolId, numberOfTeam, position, recrue, "3");

	// Defenseur

	position = "defenseur";
	recrue = 0;
	mModelPlayer.putDatabaseInDatastore(poolId, numberOfTeam, position, recrue, "4");

	// Gardien

	position = "gardien";
	recrue = 0;
	mModelPlayer.putDatabaseInDatastore(poolId, numberOfTeam, position, recrue, "5");

	// Recrue

	position = "recrue";
	recrue = 1;
	mModelPlayer.putDatabaseInDatastore(poolId, numberOfTeam, position, recrue, "6");
	// Classement
	mModelClassement.updateClassementAfterTrade(poolId,Long.valueOf(numberOfTeam));
	mModelClassement.putDatabaseInDatastore(poolId);
	// DraftPick
	mModelDraft.putDatabaseInDatastore(poolId,numberOfTeam,"7");
    }

    public void showOfferNumberY(HttpServletRequest req2, int i, TradeMadeDao tradeMadeDao, PlayersDao playersDao, DraftPickDao draftPickDao) {
	String trade_id_string = req.getParameter("trade_id");
	int trade_id = Integer.parseInt(trade_id_string);

// initialisation des objets pour le metier
	TradeBeans mBean = new TradeBeans();
	mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	mBeanPool = (Pool) req.getSession().getAttribute("Pool");

	mBean = tradeMadeDao.showOfferX(mBeanPool, mBeanUser, trade_id, playersDao, draftPickDao);

	req.getSession().setAttribute("tradeOfferBean", mBean);
	
    }

}
