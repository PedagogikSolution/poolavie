package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;
import java.util.Date;


public class Players implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2415967842901100625L;
    
    private int _id;
    private int players_id;
    private int team_id;  
    private String nom;
    private String teamOfPlayer;
    private int pj;
    private int but_victoire;
    private int aide_overtime;
    private int blanchissage;
    private int pts;
    private int projection;
    private String position;
    private Date birthday;
    private int can_be_rookie;
    private int take_proj;
    private int salaire_draft;
    private int contrat;
    private int acquire_years;
    private int salaire_contrat;
    private int contrat_cours;
    private int contrat_max_years;
    private int type_contrat;
    private int club_ecole;
    private Date date_calcul;
    private int years_0;
    private String years_1;
    private String years_2;
    private String years_3;
    private String years_4;
    private String years_5;
    private int team_was_update;
    private int age;
    private int hier;
    private int semaine;
    private int mois;
    
    
    public int getPlayers_id() {
	return players_id;
    }
    public void setPlayers_id(int players_id) {
	this.players_id = players_id;
    }
    public int getTeam_id() {
	return team_id;
    }
    public void setTeam_id(int team_id) {
	this.team_id = team_id;
    }
    public String getNom() {
	return nom;
    }
    public void setNom(String nom) {
	this.nom = nom;
    }
    public String getTeamOfPlayer() {
	return teamOfPlayer;
    }
    public void setTeamOfPlayer(String teamOfPlayer) {
	this.teamOfPlayer = teamOfPlayer;
    }
    public int getPj() {
	return pj;
    }
    public void setPj(int pj) {
	this.pj = pj;
    }
    public int getBut_victoire() {
	return but_victoire;
    }
    public void setBut_victoire(int but_victoire) {
	this.but_victoire = but_victoire;
    }
    public int getAide_overtime() {
	return aide_overtime;
    }
    public void setAide_overtime(int aide_overtime) {
	this.aide_overtime = aide_overtime;
    }
    public int getBlanchissage() {
	return blanchissage;
    }
    public void setBlanchissage(int blanchissage) {
	this.blanchissage = blanchissage;
    }
    
    public int getPts() {
        return pts;
    }
    public void setPts(int pts) {
        this.pts = pts;
    }
    public int getProjection() {
        return projection;
    }
    public void setProjection(int projection) {
        this.projection = projection;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public int getCan_be_rookie() {
        return can_be_rookie;
    }
    public void setCan_be_rookie(int can_be_rookie) {
        this.can_be_rookie = can_be_rookie;
    }
    public int getTake_proj() {
        return take_proj;
    }
    public void setTake_proj(int take_proj) {
        this.take_proj = take_proj;
    }
    public int getSalaire_draft() {
        return salaire_draft;
    }
    public void setSalaire_draft(int salaire_draft) {
        this.salaire_draft = salaire_draft;
    }
    public int getContrat() {
        return contrat;
    }
    public void setContrat(int contrat) {
        this.contrat = contrat;
    }
     public int getSalaire_contrat() {
        return salaire_contrat;
    }
    public void setSalaire_contrat(int salaire_contrat) {
        this.salaire_contrat = salaire_contrat;
    }
    public int getContrat_cours() {
        return contrat_cours;
    }
    public void setContrat_cours(int contrat_cours) {
        this.contrat_cours = contrat_cours;
    }
    public int getContrat_max_years() {
        return contrat_max_years;
    }
    public void setContrat_max_years(int contrat_max_years) {
        this.contrat_max_years = contrat_max_years;
    }
    public int getType_contrat() {
        return type_contrat;
    }
    public void setType_contrat(int type_contrat) {
        this.type_contrat = type_contrat;
    }
    public int getClub_ecole() {
        return club_ecole;
    }
    public void setClub_ecole(int club_ecole) {
        this.club_ecole = club_ecole;
    }
    public Date getDate_calcul() {
        return date_calcul;
    }
    public void setDate_calcul(Date date_calcul) {
        this.date_calcul = date_calcul;
    }
    public String getYears_1() {
        return years_1;
    }
    public void setYears_1(String years_1) {
        this.years_1 = years_1;
    }
    public String getYears_2() {
        return years_2;
    }
    public void setYears_2(String years_2) {
        this.years_2 = years_2;
    }
    public String getYears_3() {
        return years_3;
    }
    public void setYears_3(String years_3) {
        this.years_3 = years_3;
    }
    public String getYears_4() {
        return years_4;
    }
    public void setYears_4(String years_4) {
        this.years_4 = years_4;
    }
    public String getYears_5() {
        return years_5;
    }
    public void setYears_5(String years_5) {
        this.years_5 = years_5;
    }
    public int getTeam_was_update() {
        return team_was_update;
    }
    public void setTeam_was_update(int team_was_update) {
        this.team_was_update = team_was_update;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getHier() {
        return hier;
    }
    public void setHier(int hier) {
        this.hier = hier;
    }
    public int getSemaine() {
        return semaine;
    }
    public void setSemaine(int semaine) {
        this.semaine = semaine;
    }
    public int getMois() {
        return mois;
    }
    public void setMois(int mois) {
        this.mois = mois;
    }
    public int get_id() {
	return _id;
    }
    public void set_id(int _id) {
	this._id = _id;
    }
    public int getAcquire_years() {
	return acquire_years;
    }
    public void setAcquire_years(int acquire_years) {
	this.acquire_years = acquire_years;
    }
	public int getYears_0() {
		return years_0;
	}
	public void setYears_0(int years_0) {
		this.years_0 = years_0;
	}
    
    

}
