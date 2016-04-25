package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Pool")
public class Pool implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1746403313888742471L;

    @Id
    private String poolID;
    private String poolName;
    private int poolType;
    private int draftType;
    private int tradeType;
    private int numberTeam;
    private int poolYear;
    private String creationDate;
    private int numTeamCreate;
    private String nomTeam1;
    private String nomTeam2;
    private String nomTeam3;
    private String nomTeam4;
    private String nomTeam5;
    private String nomTeam6;
    private String nomTeam7;
    private String nomTeam8;
    private String nomTeam9;
    private String nomTeam10;
    private String nomTeam11;
    private String nomTeam12;
    private String derniereMAJ;
    private String codeValidationPool;


    public String getPoolID() {
	return poolID;
    }


    public void setPoolID(String poolID) {
	this.poolID = poolID;
    }


    public int getPoolType() {
	return poolType;
    }


    public void setPoolType(int poolType) {
	this.poolType = poolType;
    }


    public String getPoolName() {
	return poolName;
    }


    public void setPoolName(String poolName) {
	this.poolName = poolName;
    }


    public int getDraftType() {
	return draftType;
    }


    public void setDraftType(int draftType) {
	this.draftType = draftType;
    }


    public int getTradeType() {
	return tradeType;
    }


    public void setTradeType(int tradeType) {
	this.tradeType = tradeType;
    }


    public int getNumberTeam() {
	return numberTeam;
    }


    public void setNumberTeam(int numberTeam) {
	this.numberTeam = numberTeam;
    }


    public int getPoolYear() {
	return poolYear;
    }


    public void setPoolYear(int poolYear) {
	this.poolYear = poolYear;
    }


    public String getCreationDate() {
	return creationDate;
    }


    public void setCreationDate(String creationDate) {
	this.creationDate = creationDate;
    }


    public int getNumTeamCreate() {
	return numTeamCreate;
    }


    public void setNumTeamCreate(int numTeamCreate) {
	this.numTeamCreate = numTeamCreate;
    }


    public String getNomTeam1() {
	return nomTeam1;
    }


    public void setNomTeam1(String nomTeam1) {
	this.nomTeam1 = nomTeam1;
    }


    public String getNomTeam2() {
	return nomTeam2;
    }


    public void setNomTeam2(String nomTeam2) {
	this.nomTeam2 = nomTeam2;
    }


    public String getNomTeam3() {
	return nomTeam3;
    }


    public void setNomTeam3(String nomTeam3) {
	this.nomTeam3 = nomTeam3;
    }


    public String getNomTeam4() {
	return nomTeam4;
    }


    public void setNomTeam4(String nomTeam4) {
	this.nomTeam4 = nomTeam4;
    }


    public String getNomTeam5() {
	return nomTeam5;
    }


    public void setNomTeam5(String nomTeam5) {
	this.nomTeam5 = nomTeam5;
    }


    public String getNomTeam6() {
	return nomTeam6;
    }


    public void setNomTeam6(String nomTeam6) {
	this.nomTeam6 = nomTeam6;
    }


    public String getNomTeam7() {
	return nomTeam7;
    }


    public void setNomTeam7(String nomTeam7) {
	this.nomTeam7 = nomTeam7;
    }


    public String getNomTeam8() {
	return nomTeam8;
    }


    public void setNomTeam8(String nomTeam8) {
	this.nomTeam8 = nomTeam8;
    }


    public String getNomTeam9() {
	return nomTeam9;
    }


    public void setNomTeam9(String nomTeam9) {
	this.nomTeam9 = nomTeam9;
    }


    public String getNomTeam10() {
	return nomTeam10;
    }


    public void setNomTeam10(String nomTeam10) {
	this.nomTeam10 = nomTeam10;
    }


    public String getNomTeam11() {
	return nomTeam11;
    }


    public void setNomTeam11(String nomTeam11) {
	this.nomTeam11 = nomTeam11;
    }


    public String getNomTeam12() {
	return nomTeam12;
    }


    public void setNomTeam12(String nomTeam12) {
	this.nomTeam12 = nomTeam12;
    }


    public String getDerniereMAJ() {
	return derniereMAJ;
    }


    public void setDerniereMAJ(String derniereMAJ) {
	this.derniereMAJ = derniereMAJ;
    }


    public String getCodeValidationPool() {
	return codeValidationPool;
    }


    public void setCodeValidationPool(String codeValidationPool) {
	this.codeValidationPool = codeValidationPool;
    }
    
    
}
