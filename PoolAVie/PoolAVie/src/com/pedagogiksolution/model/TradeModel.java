package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.beans.NonSessionAttaquant;
import com.pedagogiksolution.beans.NonSessionDefenseur;
import com.pedagogiksolution.beans.NonSessionDraftPick;
import com.pedagogiksolution.beans.NonSessionGardien;
import com.pedagogiksolution.beans.NonSessionRecrue;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class TradeModel {

	HttpServletRequest req;
	Pool mBeanPool;
	Utilisateur mBeanUser;
	
	public TradeModel(HttpServletRequest req, Pool mBean) {
		this.req=req;
		this.mBeanPool=mBean;
		
	}

	public TradeModel(Utilisateur mBeanUser, Pool mBeanPool2, HttpServletRequest req2) {
		this.req=req2;
		this.mBeanPool=mBeanPool2;
		this.mBeanUser=mBeanUser;
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
		
		if(teamThatReceivedOfferId==teamThatMakeOfferId){
			
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurTrade("Vous ne pouvez pas faire un échange avec vous-même... veuillez choisir une autre équipe");
			req.setAttribute("messageErreur", mBeanMessageErreur);
			return true;
			
		}
		
		
		
		String poolID = mBeanPool.getPoolID();
		int poolId = Integer.parseInt(poolID);
		
		String draftPickNameTeamMakingOffer = null;		
		String attaquantNameTeamMakingOffer=null;
		String defenseurNameTeamMakingOffer=null;
		String gardienNameTeamMakingOffer=null;
		String recrueNameTeamMakingOffer=null;
		String draftPickNameTeamReceivingOffer = null;
		String attaquantNameTeamReceivingOffer=null;
		String defenseurNameTeamReceivingOffer=null;
		String gardienNameTeamReceivingOffer=null;
		String recrueNameTeamReceivingOffer=null;
		
		
		draftPickNameTeamMakingOffer = "DraftPick" + teamThatMakeOfferId;
		attaquantNameTeamMakingOffer = "Attaquant"+teamThatMakeOfferId;
		defenseurNameTeamMakingOffer =  "Defenseur"+teamThatMakeOfferId;
		gardienNameTeamMakingOffer =  "Gardien"+teamThatMakeOfferId;
		recrueNameTeamMakingOffer =  "Recrue"+teamThatMakeOfferId;
		draftPickNameTeamReceivingOffer = "DraftPick" + teamThatReceivedOfferId;
		attaquantNameTeamReceivingOffer = "Attaquant"+teamThatReceivedOfferId;
		defenseurNameTeamReceivingOffer =  "Defenseur"+teamThatReceivedOfferId;
		gardienNameTeamReceivingOffer =  "Gardien"+teamThatReceivedOfferId;
		recrueNameTeamReceivingOffer =  "Recrue"+teamThatReceivedOfferId;
		
		
		DraftPick mBeanDraftPickThatMakingOffer = (DraftPick) req.getSession().getAttribute(draftPickNameTeamMakingOffer);
		NonSessionDraftPick mNonSessionDraftBeanThatMakingOffer = new NonSessionDraftPick();
		
		DraftPick mBeanDraftPickThatReceivedOffer = (DraftPick) req.getSession().getAttribute(draftPickNameTeamReceivingOffer);
		NonSessionDraftPick mNonSessionDraftBeanThatReceivedOffer = new NonSessionDraftPick();
		
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
					
		// Draft
		mNonSessionDraftBeanThatMakingOffer.setPick_no(mBeanDraftPickThatMakingOffer.getPick_no());
		mNonSessionDraftBeanThatMakingOffer.setTeamNameOriginalPick(mBeanDraftPickThatMakingOffer.getTeamNameOriginalPick());
		
		mNonSessionDraftBeanThatReceivedOffer.setPick_no(mBeanDraftPickThatReceivedOffer.getPick_no());
		mNonSessionDraftBeanThatReceivedOffer.setTeamNameOriginalPick(mBeanDraftPickThatReceivedOffer.getTeamNameOriginalPick());
		
		//Attaquant
			mNonSessionAttaquantBeanThatMakingOffer.set_id(mBeanAttaquantThatMakingOffer.getPlayers_id());
			mNonSessionAttaquantBeanThatMakingOffer.setNom(mBeanAttaquantThatMakingOffer.getNom());
		    mNonSessionAttaquantBeanThatMakingOffer.setAide_overtime(mBeanAttaquantThatMakingOffer.getAide_overtime());		   
		    mNonSessionAttaquantBeanThatMakingOffer.setBut_victoire(mBeanAttaquantThatMakingOffer.getBut_victoire());
		    mNonSessionAttaquantBeanThatMakingOffer.setPj(mBeanAttaquantThatMakingOffer.getPj());
		    mNonSessionAttaquantBeanThatMakingOffer.setPosition(mBeanAttaquantThatMakingOffer.getPosition());
		    mNonSessionAttaquantBeanThatMakingOffer.setPts(mBeanAttaquantThatMakingOffer.getPts());
		    mNonSessionAttaquantBeanThatMakingOffer.setSalaire_contrat(mBeanAttaquantThatMakingOffer.getSalaire_contrat());
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
		    mNonSessionAttaquantBeanThatReceivedOffer.setSalaire_contrat(mBeanAttaquantThatReceivedOffer.getSalaire_contrat());
		    mNonSessionAttaquantBeanThatReceivedOffer.setTeamOfPlayer(mBeanAttaquantThatReceivedOffer.getTeamOfPlayer());
		    mNonSessionAttaquantBeanThatReceivedOffer.setYears_1(mBeanAttaquantThatReceivedOffer.getYears_1());
		    mNonSessionAttaquantBeanThatReceivedOffer.setYears_2(mBeanAttaquantThatReceivedOffer.getYears_2());
		    mNonSessionAttaquantBeanThatReceivedOffer.setYears_3(mBeanAttaquantThatReceivedOffer.getYears_3());
		    mNonSessionAttaquantBeanThatReceivedOffer.setYears_4(mBeanAttaquantThatReceivedOffer.getYears_4());
		    mNonSessionAttaquantBeanThatReceivedOffer.setYears_5(mBeanAttaquantThatReceivedOffer.getYears_5());
		    
		  //Defenseur
		    mNonSessionDefenseurBeanThatMakingOffer.set_id(mBeanDefenseurThatMakingOffer.getPlayers_id());
			mNonSessionDefenseurBeanThatMakingOffer.setNom(mBeanDefenseurThatMakingOffer.getNom());
		    mNonSessionDefenseurBeanThatMakingOffer.setAide_overtime(mBeanDefenseurThatMakingOffer.getAide_overtime());		   
		    mNonSessionDefenseurBeanThatMakingOffer.setBut_victoire(mBeanDefenseurThatMakingOffer.getBut_victoire());
		    mNonSessionDefenseurBeanThatMakingOffer.setPj(mBeanDefenseurThatMakingOffer.getPj());
		    mNonSessionDefenseurBeanThatMakingOffer.setPosition(mBeanDefenseurThatMakingOffer.getPosition());
		    mNonSessionDefenseurBeanThatMakingOffer.setPts(mBeanDefenseurThatMakingOffer.getPts());
		    mNonSessionDefenseurBeanThatMakingOffer.setSalaire_contrat(mBeanDefenseurThatMakingOffer.getSalaire_contrat());
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
		    mNonSessionDefenseurBeanThatReceivedOffer.setSalaire_contrat(mBeanDefenseurThatReceivedOffer.getSalaire_contrat());
		    mNonSessionDefenseurBeanThatReceivedOffer.setTeamOfPlayer(mBeanDefenseurThatReceivedOffer.getTeamOfPlayer());
		    mNonSessionDefenseurBeanThatReceivedOffer.setYears_1(mBeanDefenseurThatReceivedOffer.getYears_1());
		    mNonSessionDefenseurBeanThatReceivedOffer.setYears_2(mBeanDefenseurThatReceivedOffer.getYears_2());
		    mNonSessionDefenseurBeanThatReceivedOffer.setYears_3(mBeanDefenseurThatReceivedOffer.getYears_3());
		    mNonSessionDefenseurBeanThatReceivedOffer.setYears_4(mBeanDefenseurThatReceivedOffer.getYears_4());
		    mNonSessionDefenseurBeanThatReceivedOffer.setYears_5(mBeanDefenseurThatReceivedOffer.getYears_5());
		    
		  //Gardien
		    mNonSessionGardienBeanThatMakingOffer.set_id(mBeanGardienThatMakingOffer.getPlayers_id());
			mNonSessionGardienBeanThatMakingOffer.setNom(mBeanGardienThatMakingOffer.getNom());
		    mNonSessionGardienBeanThatMakingOffer.setAide_overtime(mBeanGardienThatMakingOffer.getAide_overtime());		   
		    mNonSessionGardienBeanThatMakingOffer.setBut_victoire(mBeanGardienThatMakingOffer.getBut_victoire());
		    mNonSessionGardienBeanThatMakingOffer.setBlanchissage(mBeanGardienThatMakingOffer.getBlanchissage());
		    mNonSessionGardienBeanThatMakingOffer.setPj(mBeanGardienThatMakingOffer.getPj());
		    mNonSessionGardienBeanThatMakingOffer.setPosition(mBeanGardienThatMakingOffer.getPosition());
		    mNonSessionGardienBeanThatMakingOffer.setPts(mBeanGardienThatMakingOffer.getPts());
		    mNonSessionGardienBeanThatMakingOffer.setSalaire_contrat(mBeanGardienThatMakingOffer.getSalaire_contrat());
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
		    mNonSessionGardienBeanThatReceivedOffer.setSalaire_contrat(mBeanGardienThatReceivedOffer.getSalaire_contrat());
		    mNonSessionGardienBeanThatReceivedOffer.setTeamOfPlayer(mBeanGardienThatReceivedOffer.getTeamOfPlayer());
		    mNonSessionGardienBeanThatReceivedOffer.setYears_1(mBeanGardienThatReceivedOffer.getYears_1());
		    mNonSessionGardienBeanThatReceivedOffer.setYears_2(mBeanGardienThatReceivedOffer.getYears_2());
		    mNonSessionGardienBeanThatReceivedOffer.setYears_3(mBeanGardienThatReceivedOffer.getYears_3());
		    mNonSessionGardienBeanThatReceivedOffer.setYears_4(mBeanGardienThatReceivedOffer.getYears_4());
		    mNonSessionGardienBeanThatReceivedOffer.setYears_5(mBeanGardienThatReceivedOffer.getYears_5());
		    
		  //Recrue
		    mNonSessionRecrueBeanThatMakingOffer.set_id(mBeanRecrueThatMakingOffer.getPlayers_id());
			mNonSessionRecrueBeanThatMakingOffer.setNom(mBeanRecrueThatMakingOffer.getNom());
		    mNonSessionRecrueBeanThatMakingOffer.setAide_overtime(mBeanRecrueThatMakingOffer.getAide_overtime());		   
		    mNonSessionRecrueBeanThatMakingOffer.setBut_victoire(mBeanRecrueThatMakingOffer.getBut_victoire());
		    mNonSessionRecrueBeanThatMakingOffer.setPj(mBeanRecrueThatMakingOffer.getPj());
		    mNonSessionRecrueBeanThatMakingOffer.setPosition(mBeanRecrueThatMakingOffer.getPosition());
		    mNonSessionRecrueBeanThatMakingOffer.setPts(mBeanRecrueThatMakingOffer.getPts());
		    mNonSessionRecrueBeanThatMakingOffer.setSalaire_contrat(mBeanRecrueThatMakingOffer.getSalaire_contrat());
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
		    mNonSessionRecrueBeanThatReceivedOffer.setSalaire_contrat(mBeanRecrueThatReceivedOffer.getSalaire_contrat());
		    mNonSessionRecrueBeanThatReceivedOffer.setTeamOfPlayer(mBeanRecrueThatReceivedOffer.getTeamOfPlayer());
		    mNonSessionRecrueBeanThatReceivedOffer.setYears_1(mBeanRecrueThatReceivedOffer.getYears_1());
		    mNonSessionRecrueBeanThatReceivedOffer.setYears_2(mBeanRecrueThatReceivedOffer.getYears_2());
		    mNonSessionRecrueBeanThatReceivedOffer.setYears_3(mBeanRecrueThatReceivedOffer.getYears_3());
		    mNonSessionRecrueBeanThatReceivedOffer.setYears_4(mBeanRecrueThatReceivedOffer.getYears_4());
		    mNonSessionRecrueBeanThatReceivedOffer.setYears_5(mBeanRecrueThatReceivedOffer.getYears_5());
		
		
		req.setAttribute("NonSessionDraftPickMaking", mNonSessionDraftBeanThatMakingOffer);
		req.setAttribute("NonSessionDraftPickReciving", mNonSessionDraftBeanThatReceivedOffer);
		
		req.setAttribute("NonSessionAttaquantPickMaking", mNonSessionAttaquantBeanThatMakingOffer);
		req.setAttribute("NonSessionAttaquantPickReciving", mNonSessionAttaquantBeanThatReceivedOffer);
		
		req.setAttribute("NonSessionDefenseuPickMakingr", mNonSessionDefenseurBeanThatMakingOffer);
		req.setAttribute("NonSessionDefenseurPickReciving", mNonSessionDefenseurBeanThatReceivedOffer);
		
		req.setAttribute("NonSessionGardienPickMaking", mNonSessionGardienBeanThatMakingOffer);
		req.setAttribute("NonSessionGardienPickReciving", mNonSessionGardienBeanThatReceivedOffer);
		
		req.setAttribute("NonSessionRecruePickMaking", mNonSessionRecrueBeanThatMakingOffer);
		req.setAttribute("NonSessionRecruePickReciving", mNonSessionRecrueBeanThatReceivedOffer);
		
		return false;
		
	}
	
	

}
