package com.pedagogiksolution.poolavie.beans;

import java.io.Serializable;
import java.util.List;


public class TradeBeans implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8383637969357195236L;
    private int isThereAOfferForMe;
    private int echangeAvecSoiMeme;
    private int codeErreurOffreTrade;
    private String[] nomMakingOffer;
    private String[] pickNumMakingOffer;
    private String[] nomReceivingOffer;
    private String[] pickNumReceivingOffer;
    private String[] playerIdMakingOffer;
    private String[] playerIdReceivingOffer;
    private int cashMakingOffer;
    private int cashReceivingOffer;
    private String[] roundPickMakingOffer;
    private String[] roundPickReceivingOffer;
    private String[] fromPickMakingOffer;
    private String[] fromPickReceivingOffer;
    private List<Integer> tradeOfferId;
    private List<String> tradeOfferNameTeamTradeWith;
    private int teamThatTrade;
    private int teamTradeTo;
    
    
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
     * 
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

    

}
