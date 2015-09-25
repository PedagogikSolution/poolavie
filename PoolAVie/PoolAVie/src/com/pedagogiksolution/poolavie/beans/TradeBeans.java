package com.pedagogiksolution.poolavie.beans;

public class TradeBeans {
    
    private int isThereAOfferForMe;
    private int echangeAvecSoiMeme;
    private int codeErreurOffreTrade;
    /* 
     * 1:Trop de contrat
     * 2:Pas assez d'argent pour draft
     * 3:Manque attaquant
     * 4:Manque defenseur
     * 5:Manque gardien
     * 6:Deja eu dans le club
     * 7:Pas de joueur ou argent contre argent
     * 
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

    

}
