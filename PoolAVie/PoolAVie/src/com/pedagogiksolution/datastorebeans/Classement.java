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
    private List<Long> team_id;
    private List<String> equipe;
    private List<Long> pj;
    private List<Long> but;
    private List<Long> passe;
    private List<Long> points;
    private List<Long> hier;
    private List<Long> semaine;
    private List<Long> mois;
    private List<Float> moyenne;
    private List<Long> difference;

    public String getPoolId() {
	return poolId;
    }

    public void setPoolId(String poolId) {
	this.poolId = poolId;
    }

    public List<Long> getTeam_id() {
	return team_id;
    }

    public void setTeam_id(List<Long> team_id) {
	this.team_id = team_id;
    }

    public List<String> getEquipe() {
	return equipe;
    }

    public void setEquipe(List<String> equipe) {
	this.equipe = equipe;
    }

    public List<Long> getPj() {
	return pj;
    }

    public void setPj(List<Long> pj) {
	this.pj = pj;
    }

    public List<Long> getBut() {
	return but;
    }

    public void setBut(List<Long> but) {
	this.but = but;
    }

    public List<Long> getPasse() {
	return passe;
    }

    public void setPasse(List<Long> passe) {
	this.passe = passe;
    }

    public List<Long> getPoints() {
	return points;
    }

    public void setPoints(List<Long> points) {
	this.points = points;
    }

    public List<Long> getHier() {
	return hier;
    }

    public void setHier(List<Long> hier) {
	this.hier = hier;
    }

    public List<Long> getSemaine() {
	return semaine;
    }

    public void setSemaine(List<Long> semaine) {
	this.semaine = semaine;
    }

    public List<Long> getMois() {
	return mois;
    }

    public void setMois(List<Long> mois) {
	this.mois = mois;
    }

    public List<Float> getMoyenne() {
	return moyenne;
    }

    public void setMoyenne(List<Float> m_moyenne) {
	this.moyenne = m_moyenne;
    }

    public List<Long> getDifference() {
	return difference;
    }

    public void setDifference(List<Long> difference) {
	this.difference = difference;
    }

    @SuppressWarnings("unchecked")
    public Classement mapClassementFromDatastore(com.google.appengine.api.datastore.Entity mEntity, Classement mBeanClassement) {
	List<Long> m_but = (List<Long>) mEntity.getProperty("but");
	mBeanClassement.setBut(m_but);

	List<Long> m_difference = (List<Long>) mEntity.getProperty("difference");
	mBeanClassement.setDifference(m_difference);

	List<String> m_equipe = (List<String>) mEntity.getProperty("equipe");
	mBeanClassement.setEquipe(m_equipe);
	
	List<Long> m_hier = (List<Long>) mEntity.getProperty("hier");
	mBeanClassement.setHier(m_hier);

	List<Long> m_mois = (List<Long>) mEntity.getProperty("mois");
	mBeanClassement.setMois(m_mois);

	List<Float> m_moyenne = (List<Float>) mEntity.getProperty("moyenne");
	mBeanClassement.setMoyenne(m_moyenne);

	List<Long> m_passe = (List<Long>) mEntity.getProperty("passe");
	mBeanClassement.setPasse(m_passe);

	List<Long> m_pj = (List<Long>) mEntity.getProperty("pj");
	mBeanClassement.setPj(m_pj);

	List<Long> m_points = (List<Long>) mEntity.getProperty("points");
	mBeanClassement.setPoints(m_points);

	List<Long> m_semaine = (List<Long>) mEntity.getProperty("semaine");
	mBeanClassement.setSemaine(m_semaine);
	
	List<Long> m_team_id = (List<Long>) mEntity.getProperty("team_id");
	mBeanClassement.setTeam_id(m_team_id);

	String m_poolId = (String) mEntity.getProperty("poolId");
	mBeanClassement.setPoolId(m_poolId);

	return mBeanClassement;
    }

}
