package com.pedagogiksolution.beans;

public class MessageErreurBeans implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1120541391585424797L;

    // liste des beans d'erreur selon la page où elle doit être afficher
    private String erreurFormulaireRegistration;
    private String erreurCodeValidationType1; // mauvais code
    private String erreurCodeValidationType2; // suite a demande de nouveau code
    private String erreurFormulaireLogin;
    private String erreurFormulaireRecuperation;
    private String erreurNotLogIn;
    private String erreurCreateNewTeam;

    public String getErreurFormulaireRegistration() {
	return erreurFormulaireRegistration;
    }

    public void setErreurFormulaireRegistration(String erreurFormulaireRegistration) {
	this.erreurFormulaireRegistration = erreurFormulaireRegistration;
    }

    public String getErreurCodeValidationType2() {
	return erreurCodeValidationType2;
    }

    public void setErreurCodeValidationType2(String erreurCodeValidationType2) {
	this.erreurCodeValidationType2 = erreurCodeValidationType2;
    }

    public String getErreurCodeValidationType1() {
	return erreurCodeValidationType1;
    }

    public void setErreurCodeValidationType1(String erreurCodeValidationType1) {
	this.erreurCodeValidationType1 = erreurCodeValidationType1;
    }

    public String getErreurFormulaireLogin() {
	return erreurFormulaireLogin;
    }

    public void setErreurFormulaireLogin(String erreurFormulaireLogin) {
	this.erreurFormulaireLogin = erreurFormulaireLogin;
    }

    public String getErreurFormulaireRecuperation() {
	return erreurFormulaireRecuperation;
    }

    public void setErreurFormulaireRecuperation(String erreurFormulaireRecuperation) {
	this.erreurFormulaireRecuperation = erreurFormulaireRecuperation;
    }

    public String getErreurNotLogIn() {
	return erreurNotLogIn;
    }

    public void setErreurNotLogIn(String erreurNotLogIn) {
	this.erreurNotLogIn = erreurNotLogIn;
    }

    public String getErreurCreateNewTeam() {
	return erreurCreateNewTeam;
    }

    public void setErreurCreateNewTeam(String erreurCreateNewTeam) {
	this.erreurCreateNewTeam = erreurCreateNewTeam;
    }

}
