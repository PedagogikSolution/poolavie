package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Utilisateur")
public class Utilisateur implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 540268344402566783L;
    
    @Id
    private String nomUtilisateur;
    private String motDePasse;
    private String courriel;
    private String codeValidation;
    private String dateCreation;
    private int poolId;
    private int teamId;
    private int typeUtilisateur;
    private int validationReussi;
    
    
    public String getNomUtilisateur() {
	return nomUtilisateur;
    }
    public void setNomUtilisateur(String nomUtilisateur) {
	this.nomUtilisateur = nomUtilisateur;
    }
    public String getMotDePasse() {
	return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
	this.motDePasse = motDePasse;
    }
    public String getCourriel() {
	return courriel;
    }
    public void setCourriel(String courriel) {
	this.courriel = courriel;
    }
    public String getCodeValidation() {
	return codeValidation;
    }
    public void setCodeValidation(String codeValidation) {
	this.codeValidation = codeValidation;
    }
   
    public String getDateCreation() {
	return dateCreation;
    }
    public void setDateCreation(String dateCreation) {
	this.dateCreation = dateCreation;
    }
    public int getPoolId() {
	return poolId;
    }
    public void setPoolId(int poolId) {
	this.poolId = poolId;
    }
    public int getTeamId() {
	return teamId;
    }
    public void setTeamId(int teamId) {
	this.teamId = teamId;
    }
    public int getTypeUtilisateur() {
	return typeUtilisateur;
    }
    public void setTypeUtilisateur(int typeUtilisateur) {
	this.typeUtilisateur = typeUtilisateur;
    }
    public int getValidationReussi() {
	return validationReussi;
    }
    public void setValidationReussi(int validationReussi) {
	this.validationReussi = validationReussi;
    }
    
    
    
    
    
    

}
