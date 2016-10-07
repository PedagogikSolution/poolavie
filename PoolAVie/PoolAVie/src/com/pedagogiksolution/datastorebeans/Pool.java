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
    private int cycleAnnuel;
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
    private String logoTeam1;
    private String logoTeam2;
    private String logoTeam3;
    private String logoTeam4;
    private String logoTeam5;
    private String logoTeam6;
    private String logoTeam7;
    private String logoTeam8;
    private String logoTeam9;
    private String logoTeam10;
    private String logoTeam11;
    private String logoTeam12;
    private String derniereMAJ;
    private String codeValidationPool;
    private String draftDate;
    private String firstYear;
    private String thisYear;


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


    public int getCycleAnnuel() {
	return cycleAnnuel;
    }


    public void setCycleAnnuel(int cycleAnnuel) {
	this.cycleAnnuel = cycleAnnuel;
    }


    public String getDraftDate() {
	return draftDate;
    }


    public void setDraftDate(String dt) {
	this.draftDate = dt;
    }


    public String getFirstYear() {
	return firstYear;
    }


    public void setFirstYear(String firstYear) {
	this.firstYear = firstYear;
    }


    public String getThisYear() {
	return thisYear;
    }


    public void setThisYear(String thisYear) {
	this.thisYear = thisYear;
    }


    public String getLogoTeam1() {
	return logoTeam1;
    }


    public void setLogoTeam1(String logoTeam1) {
	this.logoTeam1 = logoTeam1;
    }


    public String getLogoTeam2() {
	return logoTeam2;
    }


    public void setLogoTeam2(String logoTeam2) {
	this.logoTeam2 = logoTeam2;
    }


    public String getLogoTeam3() {
	return logoTeam3;
    }


    public void setLogoTeam3(String logoTeam3) {
	this.logoTeam3 = logoTeam3;
    }


    public String getLogoTeam4() {
	return logoTeam4;
    }


    public void setLogoTeam4(String logoTeam4) {
	this.logoTeam4 = logoTeam4;
    }


    public String getLogoTeam5() {
	return logoTeam5;
    }


    public void setLogoTeam5(String logoTeam5) {
	this.logoTeam5 = logoTeam5;
    }


    public String getLogoTeam6() {
	return logoTeam6;
    }


    public void setLogoTeam6(String logoTeam6) {
	this.logoTeam6 = logoTeam6;
    }


    public String getLogoTeam7() {
	return logoTeam7;
    }


    public void setLogoTeam7(String logoTeam7) {
	this.logoTeam7 = logoTeam7;
    }


    public String getLogoTeam8() {
	return logoTeam8;
    }


    public void setLogoTeam8(String logoTeam8) {
	this.logoTeam8 = logoTeam8;
    }


    public String getLogoTeam9() {
	return logoTeam9;
    }


    public void setLogoTeam9(String logoTeam9) {
	this.logoTeam9 = logoTeam9;
    }


    public String getLogoTeam10() {
	return logoTeam10;
    }


    public void setLogoTeam10(String logoTeam10) {
	this.logoTeam10 = logoTeam10;
    }


    public String getLogoTeam11() {
	return logoTeam11;
    }


    public void setLogoTeam11(String logoTeam11) {
	this.logoTeam11 = logoTeam11;
    }


    public String getLogoTeam12() {
	return logoTeam12;
    }


    public void setLogoTeam12(String logoTeam12) {
	this.logoTeam12 = logoTeam12;
    }
    
    
}
