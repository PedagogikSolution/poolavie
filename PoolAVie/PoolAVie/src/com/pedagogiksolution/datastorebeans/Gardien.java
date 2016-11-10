package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Gardien")
public class Gardien implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -20243800335314393L;
    @Id
    private String poolTeamId;
    private List<Long> players_id;
    private List<Long> team_id;
    private List<String> nom;
    private List<String> teamOfPlayer;
    private List<Long> pj;
    private List<Long> but_victoire;
    private List<Long> aide_overtime;
    private List<Long> blanchissage;
    private List<Long> pts;
    private List<Long> projection;
    private List<String> position;
    private List<Date> birthday;
    private List<Long> can_be_rookie;
    private List<Long> salaire_draft;
    private List<Long> contrat;
    private List<Long> acquire_years;
    private List<Long> club_ecole;
    private List<String> years_1;
    private List<String> years_2;
    private List<String> years_3;
    private List<String> years_4;
    private List<String> years_5;

    public String getPoolTeamId() {
	return poolTeamId;
    }

    public void setPoolTeamId(String poolTeamId) {
	this.poolTeamId = poolTeamId;
    }

    public List<Long> getPlayers_id() {
	return players_id;
    }

    public void setPlayers_id(List<Long> players_id) {
	this.players_id = players_id;
    }

    public List<Long> getTeam_id() {
	return team_id;
    }

    public void setTeam_id(List<Long> team_id) {
	this.team_id = team_id;
    }

    public List<String> getNom() {
	return nom;
    }

    public void setNom(List<String> nom) {
	this.nom = nom;
    }

    public List<String> getTeamOfPlayer() {
	return teamOfPlayer;
    }

    public void setTeamOfPlayer(List<String> teamOfPlayer) {
	this.teamOfPlayer = teamOfPlayer;
    }

    public List<Long> getPj() {
	return pj;
    }

    public void setPj(List<Long> pj) {
	this.pj = pj;
    }

    public List<Long> getBut_victoire() {
	return but_victoire;
    }

    public void setBut_victoire(List<Long> but_victoire) {
	this.but_victoire = but_victoire;
    }

    public List<Long> getAide_overtime() {
	return aide_overtime;
    }

    public void setAide_overtime(List<Long> aide_overtime) {
	this.aide_overtime = aide_overtime;
    }

    public List<Long> getBlanchissage() {
	return blanchissage;
    }

    public void setBlanchissage(List<Long> blanchissage) {
	this.blanchissage = blanchissage;
    }

    public List<Long> getPts() {
	return pts;
    }

    public void setPts(List<Long> pts) {
	this.pts = pts;
    }

    public List<Long> getProjection() {
	return projection;
    }

    public void setProjection(List<Long> projection) {
	this.projection = projection;
    }

    public List<String> getPosition() {
	return position;
    }

    public void setPosition(List<String> position) {
	this.position = position;
    }

    public List<Date> getBirthday() {
	return birthday;
    }

    public void setBirthday(List<Date> birthday) {
	this.birthday = birthday;
    }

    public List<Long> getCan_be_rookie() {
	return can_be_rookie;
    }

    public void setCan_be_rookie(List<Long> can_be_rookie) {
	this.can_be_rookie = can_be_rookie;
    }

    public List<Long> getSalaire_draft() {
	return salaire_draft;
    }

    public void setSalaire_draft(List<Long> salaire_draft) {
	this.salaire_draft = salaire_draft;
    }

    public List<Long> getContrat() {
	return contrat;
    }

    public void setContrat(List<Long> contrat) {
	this.contrat = contrat;
    }

    public List<Long> getAcquire_years() {
	return acquire_years;
    }

    public void setAcquire_years(List<Long> acquire_years) {
	this.acquire_years = acquire_years;
    }

    public List<Long> getClub_ecole() {
	return club_ecole;
    }

    public void setClub_ecole(List<Long> club_ecole) {
	this.club_ecole = club_ecole;
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

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    @SuppressWarnings("unchecked")
    public Gardien mapGardienFromDatastore(com.google.appengine.api.datastore.Entity mEntity, Gardien mBeanGardien) {
	List<Long> acquire_years = (List<Long>) mEntity.getProperty("acquire_years");
	mBeanGardien.setAcquire_years(acquire_years);

	List<Long> aide_overtime = (List<Long>) mEntity.getProperty("aide_overtime");
	mBeanGardien.setAide_overtime(aide_overtime);

	List<Date> birthday = (List<Date>) mEntity.getProperty("birthday");
	mBeanGardien.setBirthday(birthday);

	List<Long> blanchissage = (List<Long>) mEntity.getProperty("blanchissage");
	mBeanGardien.setBlanchissage(blanchissage);

	List<Long> but_victoire = (List<Long>) mEntity.getProperty("but_victoire");
	mBeanGardien.setBut_victoire(but_victoire);

	List<Long> can_be_rookie = (List<Long>) mEntity.getProperty("can_be_rookie");
	mBeanGardien.setCan_be_rookie(can_be_rookie);

	List<Long> club_ecole = (List<Long>) mEntity.getProperty("club_ecole");
	mBeanGardien.setClub_ecole(club_ecole);

	List<Long> contrat = (List<Long>) mEntity.getProperty("contrat");
	mBeanGardien.setContrat(contrat);

	List<String> nom = (List<String>) mEntity.getProperty("nom");
	mBeanGardien.setNom(nom);

	List<Long> pj = (List<Long>) mEntity.getProperty("pj");
	mBeanGardien.setPj(pj);

	List<Long> players_id = (List<Long>) mEntity.getProperty("players_id");
	mBeanGardien.setPlayers_id(players_id);

	List<String> position = (List<String>) mEntity.getProperty("position");
	mBeanGardien.setPosition(position);

	List<Long> projection = (List<Long>) mEntity.getProperty("projection");
	mBeanGardien.setProjection(projection);

	List<Long> pts = (List<Long>) mEntity.getProperty("pts");
	mBeanGardien.setPts(pts);

	List<Long> salaire_draft = (List<Long>) mEntity.getProperty("salaire_draft");
	mBeanGardien.setSalaire_draft(salaire_draft);

	List<Long> team_id = (List<Long>) mEntity.getProperty("team_id");
	mBeanGardien.setTeam_id(team_id);

	List<String> teamOfPlayer = (List<String>) mEntity.getProperty("teamOfPlayer");
	mBeanGardien.setTeamOfPlayer(teamOfPlayer);

	List<String> years_1 = (List<String>) mEntity.getProperty("years_1");
	mBeanGardien.setYears_1(years_1);

	List<String> years_2 = (List<String>) mEntity.getProperty("years_2");
	mBeanGardien.setYears_2(years_2);

	List<String> years_3 = (List<String>) mEntity.getProperty("years_3");
	mBeanGardien.setYears_3(years_3);

	List<String> years_4 = (List<String>) mEntity.getProperty("years_4");
	mBeanGardien.setYears_4(years_4);

	List<String> years_5 = (List<String>) mEntity.getProperty("years_5");
	mBeanGardien.setYears_5(years_5);

	return mBeanGardien;
    }

    

}