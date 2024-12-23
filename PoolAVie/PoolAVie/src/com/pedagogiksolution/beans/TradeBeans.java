package com.pedagogiksolution.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class TradeBeans implements Serializable{
    
    
    
    /**
     * 
     */
    private static final long serialVersionUID = -2097296140372490773L;
    private int isThereAOfferForMe;
    private int echangeAvecSoiMeme;
    private int codeErreurOffreTrade;
    private int trade_id;
    private String[] nomMakingOffer;
    private String[] nomReceivingOffer;
    private int[] salaireMakingOffer;
    private int[] salaireRookieMakingOffer;
    private String[] pickNumMakingOffer;
    private int[] salaireReceivingOffer;
    private int[] salaireRookieReceivingOffer;
    private String[] pickNumReceivingOffer;
    private String[] playerIdMakingOffer;
    private String[] playerIdReceivingOffer;
    private String[] rookieNomMakingOffer;
    private String[] rookieNomReceivingOffer;
    private int cashMakingOffer;
    private int cashReceivingOffer;
    private int budgetMakingOffer;
    private int budgetReceivingOffer;
    private String[] roundPickMakingOffer;
    private String[] roundPickReceivingOffer;
    private String[] fromPickMakingOffer;
    private String[] fromPickReceivingOffer;
    private List<Integer> tradeOfferId;
    private List<String> tradeOfferNameTeamTradeWith;
    private List<String> tradeOfferNameTeamTradeWith2;
    private int teamThatTrade;
    private int teamTradeTo;
    private String teamThatTradeName;
    private String teamTradeToName;
    private String messageOffre;
    private String nomTeam;
    private Date dateTradeOffer;
    private List<String> dateTradeOfferArray;
    private int vu;
    private String[] rookieIdTeamMakingOffer;
    private String[] rookieIdTeamReceivingOffer;

    
    /* 
     * 1:Trop de contrat
     * 2:Pas assez d'argent pour draft
     * 3:Manque attaquant
     * 4:Manque defenseur
     * 5:Manque gardien
     * 6:Deja eu dans le club
     * 7:argent contre argent
     * 8:pas de joueur
     * 9:trop de joueur max 7
     * 10:trop de pick max 3
     * 11:Joueur ou pick pu disponible
     * 12: l'Equipe offrant ne peux absorber les salaires
     * 13: l'equipe recevant ne paeux absorber les salaires
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     */

     public int getIsThereAOfferForMe() {
	return isThereAOfferForMe;
    }

    public void setIsThereAOfferForMe(int isThereAOfferForMe) {
	this.isThereAOfferForMe = isThereAOfferForMe;
    }

    public int getEchangeAvecSoiMeme() {
	return echangeAvecSoiMeme;
    }

    public void setEchangeAvecSoiMeme(int echangeAvecSoiMeme) {
	this.echangeAvecSoiMeme = echangeAvecSoiMeme;
    }

    public int getCodeErreurOffreTrade() {
	return codeErreurOffreTrade;
    }

    public void setCodeErreurOffreTrade(int codeErreurOffreTrade) {
	this.codeErreurOffreTrade = codeErreurOffreTrade;
    }

   

    public int getCashMakingOffer() {
	return cashMakingOffer;
    }

    public void setCashMakingOffer(int cashMakingOffer) {
	this.cashMakingOffer = cashMakingOffer;
    }

    public int getCashReceivingOffer() {
	return cashReceivingOffer;
    }

    public void setCashReceivingOffer(int cashReceivingOffer) {
	this.cashReceivingOffer = cashReceivingOffer;
    }

    public String[] getPlayerIdMakingOffer() {
	return playerIdMakingOffer;
    }

    public void setPlayerIdMakingOffer(String[] playerIdMakingOffer) {
	this.playerIdMakingOffer = playerIdMakingOffer;
    }

    public String[] getNomMakingOffer() {
	return nomMakingOffer;
    }

    public void setNomMakingOffer(String[] nomMakingOffer) {
	this.nomMakingOffer = nomMakingOffer;
    }

    public String[] getPickNumMakingOffer() {
	return pickNumMakingOffer;
    }

    public void setPickNumMakingOffer(String[] pickNumMakingOffer) {
	this.pickNumMakingOffer = pickNumMakingOffer;
    }

    public String[] getNomReceivingOffer() {
	return nomReceivingOffer;
    }

    public void setNomReceivingOffer(String[] nomReceivingOffer) {
	this.nomReceivingOffer = nomReceivingOffer;
    }

    public String[] getPickNumReceivingOffer() {
	return pickNumReceivingOffer;
    }

    public void setPickNumReceivingOffer(String[] pickNumReceivingOffer) {
	this.pickNumReceivingOffer = pickNumReceivingOffer;
    }

    public String[] getPlayerIdReceivingOffer() {
	return playerIdReceivingOffer;
    }

    public void setPlayerIdReceivingOffer(String[] playerIdReceivingOffer) {
	this.playerIdReceivingOffer = playerIdReceivingOffer;
    }

    public String[] getRoundPickMakingOffer() {
	return roundPickMakingOffer;
    }

    public void setRoundPickMakingOffer(String[] roundPickMakingOffer) {
	this.roundPickMakingOffer = roundPickMakingOffer;
    }

    public String[] getRoundPickReceivingOffer() {
	return roundPickReceivingOffer;
    }

    public void setRoundPickReceivingOffer(String[] roundPickReceivingOffer) {
	this.roundPickReceivingOffer = roundPickReceivingOffer;
    }

    public String[] getFromPickMakingOffer() {
	return fromPickMakingOffer;
    }

    public void setFromPickMakingOffer(String[] fromPickMakingOffer) {
	this.fromPickMakingOffer = fromPickMakingOffer;
    }

    public String[] getFromPickReceivingOffer() {
	return fromPickReceivingOffer;
    }

    public void setFromPickReceivingOffer(String[] fromPickReceivingOffer) {
	this.fromPickReceivingOffer = fromPickReceivingOffer;
    }

    public List<Integer> getTradeOfferId() {
	return tradeOfferId;
    }

    public void setTradeOfferId(List<Integer> tradeOfferId) {
	this.tradeOfferId = tradeOfferId;
    }

    public List<String> getTradeOfferNameTeamTradeWith() {
	return tradeOfferNameTeamTradeWith;
    }

    public void setTradeOfferNameTeamTradeWith(List<String> tradeOfferNameTeamTradeWith) {
	this.tradeOfferNameTeamTradeWith = tradeOfferNameTeamTradeWith;
    }

    public int getTeamThatTrade() {
	return teamThatTrade;
    }

    public void setTeamThatTrade(int teamThatTrade) {
	this.teamThatTrade = teamThatTrade;
    }

    public int getTeamTradeTo() {
	return teamTradeTo;
    }

    public void setTeamTradeTo(int teamTradeTo) {
	this.teamTradeTo = teamTradeTo;
    }

    public int getTrade_id() {
	return trade_id;
    }

    public void setTrade_id(int trade_id) {
	this.trade_id = trade_id;
    }

    public String getMessageOffre() {
	return messageOffre;
    }

    public void setMessageOffre(String messageOffre) {
	this.messageOffre = messageOffre;
    }


    public String getNomTeam() {
	return nomTeam;
    }

    public void setNomTeam(String nomTeam) {
	this.nomTeam = nomTeam;
    }

    public Date getDateTradeOffer() {
	return dateTradeOffer;
    }

    public void setDateTradeOffer(Date date) {
	this.dateTradeOffer = date;
    }

    public List<String> getDateTradeOfferArray() {
	return dateTradeOfferArray;
    }

    public void setDateTradeOfferArray(List<String> dateTradeOfferArray) {
	this.dateTradeOfferArray = dateTradeOfferArray;
    }

    public int getVu() {
	return vu;
    }

    public void setVu(int vu) {
	this.vu = vu;
    }

    public int[] getSalaireMakingOffer() {
	return salaireMakingOffer;
    }

    public void setSalaireMakingOffer(int[] salaireMakingOffer) {
	this.salaireMakingOffer = salaireMakingOffer;
    }

    public int[] getSalaireRookieMakingOffer() {
	return salaireRookieMakingOffer;
    }

    public void setSalaireRookieMakingOffer(int[] salaireRookieMakingOffer) {
	this.salaireRookieMakingOffer = salaireRookieMakingOffer;
    }

    public int[] getSalaireReceivingOffer() {
	return salaireReceivingOffer;
    }

    public void setSalaireReceivingOffer(int[] salaireReceivingOffer) {
	this.salaireReceivingOffer = salaireReceivingOffer;
    }

    public int[] getSalaireRookieReceivingOffer() {
	return salaireRookieReceivingOffer;
    }

    public void setSalaireRookieReceivingOffer(int[] salaireRookieReceivingOffer) {
	this.salaireRookieReceivingOffer = salaireRookieReceivingOffer;
    }

    public String[] getRookieIdTeamMakingOffer() {
	return rookieIdTeamMakingOffer;
    }

    public void setRookieIdTeamMakingOffer(String[] rookieIdTeamMakingOffer) {
	this.rookieIdTeamMakingOffer = rookieIdTeamMakingOffer;
    }

    public String[] getRookieIdTeamReceivingOffer() {
	return rookieIdTeamReceivingOffer;
    }

    public void setRookieIdTeamReceivingOffer(String[] rookieIdTeamReceivingOffer) {
	this.rookieIdTeamReceivingOffer = rookieIdTeamReceivingOffer;
    }

    public String[] getRookieNomMakingOffer() {
	return rookieNomMakingOffer;
    }

    public void setRookieNomMakingOffer(String[] rookieNomMakingOffer) {
	this.rookieNomMakingOffer = rookieNomMakingOffer;
    }

    public String[] getRookieNomReceivingOffer() {
	return rookieNomReceivingOffer;
    }

    public void setRookieNomReceivingOffer(String[] rookieNomReceivingOffer) {
	this.rookieNomReceivingOffer = rookieNomReceivingOffer;
    }

    public List<String> getTradeOfferNameTeamTradeWith2() {
	return tradeOfferNameTeamTradeWith2;
    }

    public void setTradeOfferNameTeamTradeWith2(List<String> tradeOfferNameTeamTradeWith2) {
	this.tradeOfferNameTeamTradeWith2 = tradeOfferNameTeamTradeWith2;
    }

    public String getTeamThatTradeName() {
	return teamThatTradeName;
    }

    public void setTeamThatTradeName(String teamThatTradeName) {
	this.teamThatTradeName = teamThatTradeName;
    }

    public String getTeamTradeToName() {
	return teamTradeToName;
    }

    public void setTeamTradeToName(String teamTradeToName) {
	this.teamTradeToName = teamTradeToName;
    }

    public int getBudgetMakingOffer() {
	return budgetMakingOffer;
    }

    public void setBudgetMakingOffer(int budgetMakingOffer) {
	this.budgetMakingOffer = budgetMakingOffer;
    }

    public int getBudgetReceivingOffer() {
	return budgetReceivingOffer;
    }

    public void setBudgetReceivingOffer(int budgetReceivingOffer) {
	this.budgetReceivingOffer = budgetReceivingOffer;
    }


    

    
    

}
