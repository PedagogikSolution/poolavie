package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Classement")
public class Classement implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4619507573570488993L;
    
    @Id
    private String poolId;
    private List<Integer> team_id;   
    private List<String> equipe;
    private List<Integer> pj;
    private List<Integer> but;
    private List<Integer> passe;
    private List<Integer> points;
    private List<Integer> hier;
    private List<Integer> semaine;
    private List<Integer> mois;
    private List<Integer> moyenne;
    private List<Integer> difference;
    
    
    public List<Integer> getTeam_id() {
	return team_id;
    }
    public void setTeam_id(List<Integer> team_id) {
	this.team_id = team_id;
    }
    public List<String> getEquipe() {
	return equipe;
    }
    public void setEquipe(List<String> equipe) {
	this.equipe = equipe;
    }
    public List<Integer> getPj() {
	return pj;
    }
    public void setPj(List<Integer> pj) {
	this.pj = pj;
    }
    public List<Integer> getBut() {
	return but;
    }
    public void setBut(List<Integer> but) {
	this.but = but;
    }
    public List<Integer> getPasse() {
	return passe;
    }
    public void setPasse(List<Integer> passe) {
	this.passe = passe;
    }
    public List<Integer> getPoints() {
	return points;
    }
    public void setPoints(List<Integer> points) {
	this.points = points;
    }
    public List<Integer> getHier() {
	return hier;
    }
    public void setHier(List<Integer> hier) {
	this.hier = hier;
    }
    public List<Integer> getSemaine() {
	return semaine;
    }
    public void setSemaine(List<Integer> semaine) {
	this.semaine = semaine;
    }
    public List<Integer> getMois() {
	return mois;
    }
    public void setMois(List<Integer> mois) {
	this.mois = mois;
    }
    public List<Integer> getMoyenne() {
	return moyenne;
    }
    public void setMoyenne(List<Integer> moyenne) {
	this.moyenne = moyenne;
    }
    public List<Integer> getDifference() {
	return difference;
    }
    public void setDifference(List<Integer> difference) {
	this.difference = difference;
    }
    public String getPoolId() {
	return poolId;
    }
    public void setPoolId(String poolId) {
	this.poolId = poolId;
    }

}
