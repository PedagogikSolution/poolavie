package com.pedagogiksolution.poolavie.beans;

public class Rachat {

    private int joueurIdPourRachat;
    private int codePourMessageConfirmation;
    private String nomDuJoueurRachat;
    private int coutDuRachat;
    
    
    public int getCodePourMessageConfirmation() {
	return codePourMessageConfirmation;
    }
    public void setCodePourMessageConfirmation(int codePourMessageConfirmation) {
	this.codePourMessageConfirmation = codePourMessageConfirmation;
    }
    public int getJoueurIdPourRachat() {
	return joueurIdPourRachat;
    }
    public void setJoueurIdPourRachat(int joueurIdPourRachat) {
	this.joueurIdPourRachat = joueurIdPourRachat;
    }
    public String getNomDuJoueurRachat() {
	return nomDuJoueurRachat;
    }
    public void setNomDuJoueurRachat(String nomDuJoueurRachat) {
	this.nomDuJoueurRachat = nomDuJoueurRachat;
    }
    public int getCoutDuRachat() {
	return coutDuRachat;
    }
    public void setCoutDuRachat(int coutDuRachat) {
	this.coutDuRachat = coutDuRachat;
    }
    
    
    
}
