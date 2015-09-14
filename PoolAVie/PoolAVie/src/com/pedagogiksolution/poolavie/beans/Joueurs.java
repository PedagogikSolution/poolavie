package com.pedagogiksolution.poolavie.beans;

import java.io.Serializable;
import java.util.List;

public class Joueurs implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 8518965469537588120L;
    private List<Integer> player_id;   
    private List<String> nom;
    private List<String> team_name;
    private List<String> position;
    private List<String> years_1;
    private List<String> years_2;
    private List<String> years_3;
    private List<String> years_4;
    private List<String> years_5;
    
    
    
    public List<Integer> getPlayer_id() {
	return player_id;
    }
    public void setPlayer_id(List<Integer> player_id) {
	this.player_id = player_id;
    }
    public List<String> getNom() {
	return nom;
    }
    public void setNom(List<String> nom) {
	this.nom = nom;
    }
    public List<String> getTeam_name() {
	return team_name;
    }
    public void setTeam_name(List<String> team_name) {
	this.team_name = team_name;
    }
    public List<String> getPosition() {
	return position;
    }
    public void setPosition(List<String> position) {
	this.position = position;
    }
    public List<String> getYears_1() {
	return years_1;
    }
    public void setYears_1(List<String> years_1) {
	this.years_1 = years_1;
    }
    public List<String> getYears_2() {
	return years_2;
    }
    public void setYears_2(List<String> years_2) {
	this.years_2 = years_2;
    }
    public List<String> getYears_3() {
	return years_3;
    }
    public void setYears_3(List<String> years_3) {
	this.years_3 = years_3;
    }
    public List<String> getYears_4() {
	return years_4;
    }
    public void setYears_4(List<String> years_4) {
	this.years_4 = years_4;
    }
    public List<String> getYears_5() {
	return years_5;
    }
    public void setYears_5(List<String> years_5) {
	this.years_5 = years_5;
    }

}
