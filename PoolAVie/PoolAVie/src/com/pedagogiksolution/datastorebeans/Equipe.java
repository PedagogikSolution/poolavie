package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Equipe")
public class Equipe implements Serializable{
  
    /**
     * 
     */
    private static final long serialVersionUID = 8271529301149242196L;
    @Id
    private String poolTeamId;
    private int teamId;
    private int poolId;
    private int max_salaire_begin;
    private int total_salaire_now;
    private int budget_restant;
    private int moy_sal_restant_draft;
    private int nb_attaquant;
    private int nb_defenseur;
    private int nb_gardien;
    private int nb_rookie;
    private int nb_contrat;
    private int nb_equipe;
    private int manquant_equipe;
    private int manquant_att;
    private int manquant_def;
    private int manquant_gardien;
    private int manquant_recrue;
    private int bonus_5m;
    private int argent_recu;
    private int bonus_penalite;
    private int num_annee;
    private int num_champion;
    private int meilleur_classement;
    private int classement_last_year;
    
    
    public String getPoolTeamId() {
	return poolTeamId;
    }
    public void setPoolTeamId(String poolTeamId) {
	this.poolTeamId = poolTeamId;
    }
    public int getMax_salaire_begin() {
	return max_salaire_begin;
    }
    public void setMax_salaire_begin(int max_salaire_begin) {
	this.max_salaire_begin = max_salaire_begin;
    }
    public int getTotal_salaire_now() {
	return total_salaire_now;
    }
    public void setTotal_salaire_now(int total_salaire_now) {
	this.total_salaire_now = total_salaire_now;
    }
    public int getBudget_restant() {
	return budget_restant;
    }
    public void setBudget_restant(int budget_restant) {
	this.budget_restant = budget_restant;
    }
    public int getMoy_sal_restant_draft() {
	return moy_sal_restant_draft;
    }
    public void setMoy_sal_restant_draft(int moy_sal_restant_draft) {
	this.moy_sal_restant_draft = moy_sal_restant_draft;
    }
    public int getNb_attaquant() {
	return nb_attaquant;
    }
    public void setNb_attaquant(int nb_attaquant) {
	this.nb_attaquant = nb_attaquant;
    }
    public int getNb_defenseur() {
	return nb_defenseur;
    }
    public void setNb_defenseur(int nb_defenseur) {
	this.nb_defenseur = nb_defenseur;
    }
    public int getNb_gardien() {
	return nb_gardien;
    }
    public void setNb_gardien(int nb_gardien) {
	this.nb_gardien = nb_gardien;
    }
    public int getNb_rookie() {
	return nb_rookie;
    }
    public void setNb_rookie(int nb_rookie) {
	this.nb_rookie = nb_rookie;
    }
    public int getNb_contrat() {
	return nb_contrat;
    }
    public void setNb_contrat(int nb_contrat) {
	this.nb_contrat = nb_contrat;
    }
    public int getNb_equipe() {
	return nb_equipe;
    }
    public void setNb_equipe(int nb_equipe) {
	this.nb_equipe = nb_equipe;
    }
    public int getManquant_equipe() {
	return manquant_equipe;
    }
    public void setManquant_equipe(int manquant_equipe) {
	this.manquant_equipe = manquant_equipe;
    }
    public int getManquant_att() {
	return manquant_att;
    }
    public void setManquant_att(int manquant_att) {
	this.manquant_att = manquant_att;
    }
    public int getManquant_def() {
	return manquant_def;
    }
    public void setManquant_def(int manquant_def) {
	this.manquant_def = manquant_def;
    }
    public int getManquant_gardien() {
	return manquant_gardien;
    }
    public void setManquant_gardien(int manquant_gardien) {
	this.manquant_gardien = manquant_gardien;
    }
    public int getManquant_recrue() {
	return manquant_recrue;
    }
    public void setManquant_recrue(int manquant_recrue) {
	this.manquant_recrue = manquant_recrue;
    }
    public int getBonus_5m() {
	return bonus_5m;
    }
    public void setBonus_5m(int bonus_5m) {
	this.bonus_5m = bonus_5m;
    }
    public int getArgent_recu() {
	return argent_recu;
    }
    public void setArgent_recu(int argent_recu) {
	this.argent_recu = argent_recu;
    }
    public int getBonus_penalite() {
	return bonus_penalite;
    }
    public void setBonus_penalite(int bonus_penalite) {
	this.bonus_penalite = bonus_penalite;
    }
    public int getTeamId() {
	return teamId;
    }
    public void setTeamId(int teamId) {
	this.teamId = teamId;
    }
    public int getPoolId() {
	return poolId;
    }
    public void setPoolId(int poolId) {
	this.poolId = poolId;
    }
    public int getNum_annee() {
	return num_annee;
    }
    public void setNum_annee(int num_annee) {
	this.num_annee = num_annee;
    }
    public int getNum_champion() {
	return num_champion;
    }
    public void setNum_champion(int num_champion) {
	this.num_champion = num_champion;
    }
    public int getMeilleur_classement() {
	return meilleur_classement;
    }
    public void setMeilleur_classement(int meilleur_classement) {
	this.meilleur_classement = meilleur_classement;
    }
    public int getClassement_last_year() {
	return classement_last_year;
    }
    public void setClassement_last_year(int classement_last_year) {
	this.classement_last_year = classement_last_year;
    }
    
   
    public Equipe mapEquipeFromDatastore(com.google.appengine.api.datastore.Entity mEntity, Equipe mBeanEquipe) {
	Long argent_recu_long =(Long)mEntity.getProperty("argent_recu");
	int argent_recu = argent_recu_long.intValue();	
	mBeanEquipe.setArgent_recu(argent_recu);
	
	Long bonus_5m_long =(Long)mEntity.getProperty("bonus_5m");
	int bonus_5m = bonus_5m_long.intValue();	
	mBeanEquipe.setBonus_5m(bonus_5m);
	
	Long bonus_penalite_long =(Long)mEntity.getProperty("bonus_penalite");
	int bonus_penalite = bonus_penalite_long.intValue();	
	mBeanEquipe.setBonus_penalite(bonus_penalite);
	
	Long budget_restant_long =(Long)mEntity.getProperty("budget_restant");
	int budget_restant = budget_restant_long.intValue();	
	mBeanEquipe.setBudget_restant(budget_restant);
	
	Long classement_last_year_long =(Long)mEntity.getProperty("classement_last_year");
	int classement_last_year = classement_last_year_long.intValue();	
	mBeanEquipe.setClassement_last_year(classement_last_year);
	
	Long manquant_att_long =(Long)mEntity.getProperty("manquant_att");
	int manquant_att = manquant_att_long.intValue();	
	mBeanEquipe.setManquant_att(manquant_att);
	
	Long manquant_def_long =(Long)mEntity.getProperty("manquant_def");
	int manquant_def = manquant_def_long.intValue();	
	mBeanEquipe.setManquant_def(manquant_def);
	
	Long manquant_gardien_long =(Long)mEntity.getProperty("manquant_gardien");
	int manquant_gardien = manquant_gardien_long.intValue();	
	mBeanEquipe.setManquant_gardien(manquant_gardien);
	
	Long manquant_recrue_long =(Long)mEntity.getProperty("manquant_recrue");
	int manquant_recrue = manquant_recrue_long.intValue();	
	mBeanEquipe.setManquant_recrue(manquant_recrue);
	
	Long manquant_equipe_long =(Long)mEntity.getProperty("manquant_equipe");
	int manquant_equipe = manquant_equipe_long.intValue();	
	mBeanEquipe.setManquant_equipe(manquant_equipe);
	
	Long max_salaire_begin_long =(Long)mEntity.getProperty("max_salaire_begin");
	int max_salaire_begin = max_salaire_begin_long.intValue();	
	mBeanEquipe.setMax_salaire_begin(max_salaire_begin);
	
	Long moy_sal_restant_draft_long =(Long)mEntity.getProperty("moy_sal_restant_draft");
	int moy_sal_restant_draft = moy_sal_restant_draft_long.intValue();	
	mBeanEquipe.setMoy_sal_restant_draft(moy_sal_restant_draft);
	
	Long nb_attaquant_long =(Long)mEntity.getProperty("nb_attaquant");
	int nb_attaquant = nb_attaquant_long.intValue();	
	mBeanEquipe.setNb_attaquant(nb_attaquant);
	
	Long nb_defenseur_long =(Long)mEntity.getProperty("nb_defenseur");
	int nb_defenseur = nb_defenseur_long.intValue();	
	mBeanEquipe.setNb_defenseur(nb_defenseur);
	
	Long nb_gardien_long =(Long)mEntity.getProperty("nb_gardien");
	int nb_gardien = nb_gardien_long.intValue();	
	mBeanEquipe.setNb_gardien(nb_gardien);
	
	Long nb_rookie_long =(Long)mEntity.getProperty("nb_rookie");
	int nb_rookie = nb_rookie_long.intValue();	
	mBeanEquipe.setNb_rookie(nb_rookie);
	
	Long nb_equipe_long =(Long)mEntity.getProperty("nb_equipe");
	int nb_equipe = nb_equipe_long.intValue();	
	mBeanEquipe.setNb_equipe(nb_equipe);
	
	Long nb_contrat_long =(Long)mEntity.getProperty("nb_contrat");
	int nb_contrat = nb_contrat_long.intValue();	
	mBeanEquipe.setNb_contrat(nb_contrat);
	
	Long total_salaire_now_long =(Long)mEntity.getProperty("total_salaire_now");
	int total_salaire_now = total_salaire_now_long.intValue();	
	mBeanEquipe.setTotal_salaire_now(total_salaire_now);
		
	return mBeanEquipe;
    }
}
