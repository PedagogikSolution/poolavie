package com.pedagogiksolution.poolavie.beans;

public class Signature {

    private int joueurId;
    private int codePourMessageConfirmation;
    private String nomDuJoueur;
    private int montant;
    private int nombreAnnee;
    private int moyenne;
    private int position;
    
    
    public int getCodePourMessageConfirmation() {
	return codePourMessageConfirmation;
    }
    public void setCodePourMessageConfirmation(int codePourMessageConfirmation) {
	this.codePourMessageConfirmation = codePourMessageConfirmation;
    }
    public int getJoueurId() {
	return joueurId;
    }
    public void setJoueurId(int joueurIdPourRachat) {
	this.joueurId = joueurIdPourRachat;
    }
    public String getNomDuJoueur() {
	return nomDuJoueur;
    }
    public void setNomDuJoueur(String nomDuJoueur) {
	this.nomDuJoueur = nomDuJoueur;
    }
   
    public int getNombreAnnee() {
	return nombreAnnee;
    }
    public void setNombreAnnee(int nombreAnnee) {
	this.nombreAnnee = nombreAnnee;
    }
    public int getMoyenne() {
	return moyenne;
    }
    public void setMoyenne(int moyenne) {
	this.moyenne = moyenne;
    }
    public int getMontant() {
	return montant;
    }
    public void setMontant(int montant) {
	this.montant = montant;
    }
    public int getPosition() {
	return position;
    }
    public void setPosition(int position) {
	this.position = position;
    }
  
    
    
    
    
}
