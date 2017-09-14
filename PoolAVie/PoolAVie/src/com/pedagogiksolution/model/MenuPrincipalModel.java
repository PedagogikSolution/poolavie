package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.datastorebeans.Utilisateur;

public class MenuPrincipalModel {

    public Boolean checkIfRegistrationFinish(HttpServletRequest req) {

	// on recupere la propriete du Bean pour verifier si elle est a 0 ou 1
	Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int firstConnexionFinish = mBean.getFirstConnexionFinish();
	// si egal 1, on retourne true afin d'envoyer le client e la page du Menu Principale
	if (firstConnexionFinish == 1) {
	    return true;
	} else {
	    return false;
	}
    }

    public int checkTypeOfUser(HttpServletRequest req) {
	// on recupere la propriete du Bean pour verifier si elle est a 0 ou 1
	Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int typeUser = mBean.getTypeUtilisateur();

	return typeUser;
    }


}
