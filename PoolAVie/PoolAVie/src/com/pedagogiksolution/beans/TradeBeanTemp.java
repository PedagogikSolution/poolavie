package com.pedagogiksolution.beans;

import java.io.Serializable;

public class TradeBeanTemp implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6471200049268846738L;
    
    private String positionDuJoueurTrade;
    
    private int total_salaire_team_making_offer;
    private String nomMakingOfferString;
    private String[] nomMakingOffer;
    private String roundPick;
    private String fromPick;

    private int total_salaire_team_receiving_offer;
    private String nomReceivingOfferString;
    private String[] nomReceivingOffer;
    public int getTotal_salaire_team_making_offer() {
	return total_salaire_team_making_offer;
    }
    public void setTotal_salaire_team_making_offer(int total_salaire_team_making_offer) {
	this.total_salaire_team_making_offer = total_salaire_team_making_offer;
    }
    public String[] getNomMakingOffer() {
	return nomMakingOffer;
    }
    public void setNomMakingOffer(String[] nomMakingOffer) {
	this.nomMakingOffer = nomMakingOffer;
    }
    public String getNomMakingOfferString() {
	return nomMakingOfferString;
    }
    public void setNomMakingOfferString(String nomMakingOfferString) {
	this.nomMakingOfferString = nomMakingOfferString;
    }
    public int getTotal_salaire_team_receiving_offer() {
	return total_salaire_team_receiving_offer;
    }
    public void setTotal_salaire_team_receiving_offer(int total_salaire_team_receiving_offer) {
	this.total_salaire_team_receiving_offer = total_salaire_team_receiving_offer;
    }
    public String getNomReceivingOfferString() {
	return nomReceivingOfferString;
    }
    public void setNomReceivingOfferString(String nomReceivingOfferString) {
	this.nomReceivingOfferString = nomReceivingOfferString;
    }
    public String[] getNomReceivingOffer() {
	return nomReceivingOffer;
    }
    public void setNomReceivingOffer(String[] nomReceivingOffer) {
	this.nomReceivingOffer = nomReceivingOffer;
    }
    public String getPositionDuJoueurTrade() {
	return positionDuJoueurTrade;
    }
    public void setPositionDuJoueurTrade(String positionDuJoueurTrade) {
	this.positionDuJoueurTrade = positionDuJoueurTrade;
    }
    public String getRoundPick() {
	return roundPick;
    }
    public void setRoundPick(String roundPick) {
	this.roundPick = roundPick;
    }
    public String getFromPick() {
	return fromPick;
    }
    public void setFromPick(String fromPick) {
	this.fromPick = fromPick;
    }
   
}
