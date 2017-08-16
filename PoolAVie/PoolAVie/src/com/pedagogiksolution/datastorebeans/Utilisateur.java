package com.pedagogiksolution.datastorebeans;

import java.io.Serializable;

import com.google.appengine.api.datastore.Entity;

public class Utilisateur implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 540268344402566783L;

	private String nomUtilisateur;
	private String motDePasse;
	private String courriel;
	private String codeValidation;
	private String codeRecuperation;
	private String dateCreation;
	private int poolId;
	private int teamId;
	private int typeUtilisateur;
	private int validationReussi;
	private int loginReussi;
	private int firstConnexionFinish;
	private String teamName;
	private String urlTeamLogo;

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

	public int getLoginReussi() {
		return loginReussi;
	}

	public void setLoginReussi(int loginReussi) {
		this.loginReussi = loginReussi;
	}

	public int getFirstConnexionFinish() {
		return firstConnexionFinish;
	}

	public void setFirstConnexionFinish(int firstConnexionFinish) {
		this.firstConnexionFinish = firstConnexionFinish;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getUrlTeamLogo() {
		return urlTeamLogo;
	}

	public void setUrlTeamLogo(String urlTeamLogo) {
		this.urlTeamLogo = urlTeamLogo;
	}

	public String getCodeRecuperation() {
		return codeRecuperation;
	}

	public void setCodeRecuperation(String codeRecuperation) {
		this.codeRecuperation = codeRecuperation;
	}

	public Utilisateur mapUtilisateurFromDatastore(com.google.appengine.api.datastore.Entity mEntity,
			Utilisateur mBean) {

		String nomUtilisateur = (String) mEntity.getKey().getName();
		mBean.setNomUtilisateur(nomUtilisateur);
		String motDePasse = (String) mEntity.getProperty("motDePasse");
		mBean.setMotDePasse(motDePasse);
		String courriel = (String) mEntity.getProperty("courriel");
		mBean.setCourriel(courriel);
		String codeValidation = (String) mEntity.getProperty("codeValidation");
		mBean.setCodeValidation(codeValidation);
		String codeRecuperation = (String) mEntity.getProperty("codeRecuperation");
		mBean.setCodeRecuperation(codeRecuperation);
		String dateCreation = (String) mEntity.getProperty("dateCreation");
		mBean.setDateCreation(dateCreation);
		Long poolId = (Long) mEntity.getProperty("poolId");
		mBean.setPoolId(poolId.intValue());
		Long teamId = (Long) mEntity.getProperty("teamId");
		mBean.setTeamId(teamId.intValue());
		Long typeUtilisateur = (Long) mEntity.getProperty("typeUtilisateur");
		mBean.setTypeUtilisateur(typeUtilisateur.intValue());
		Long validationReussi = (Long) mEntity.getProperty("validationReussi");
		mBean.setValidationReussi(validationReussi.intValue());
		Long loginReussi = (Long) mEntity.getProperty("loginReussi");
		mBean.setLoginReussi(loginReussi.intValue());
		Long firstConnexionFinish = (Long) mEntity.getProperty("firstConnexionFinish");
		mBean.setFirstConnexionFinish(firstConnexionFinish.intValue());
		String teamName = (String) mEntity.getProperty("teamName");
		mBean.setTeamName(teamName);
		String urlTeamLogo = (String) mEntity.getProperty("urlTeamLogo");
		mBean.setUrlTeamLogo(urlTeamLogo);

		return mBean;

	}

	public Entity mapBeanToEntityForDatastore(Utilisateur mBean, String nomUtilisateur) {

		Entity mEntity = new Entity("Utilisateur", nomUtilisateur);

		mEntity.setProperty("motDePasse", mBean.getMotDePasse());

		mEntity.setProperty("courriel", mBean.getCourriel());

		mEntity.setProperty("codeValidation", mBean.getCodeValidation());

		mEntity.setProperty("codeRecuperation", mBean.getCodeRecuperation());

		mEntity.setProperty("dateCreation", mBean.getDateCreation());

		mEntity.setProperty("poolId", mBean.getPoolId());

		mEntity.setProperty("teamId", mBean.getTeamId());

		mEntity.setProperty("typeUtilisateur", mBean.getTypeUtilisateur());

		mEntity.setProperty("validationReussi", mBean.getValidationReussi());

		mEntity.setProperty("loginReussi", mBean.getLoginReussi());

		mEntity.setProperty("firstConnexionFinish", mBean.getFirstConnexionFinish());

		mEntity.setProperty("teamName", mBean.getTeamName());

		mEntity.setProperty("urlTeamLogo", mBean.getUrlTeamLogo());

		return mEntity;
	}

}
