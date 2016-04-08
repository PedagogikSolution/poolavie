package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.datastorebeans.Utilisateur;

public class MainModel {

    public Boolean checkIfRegistrationFinish(HttpServletRequest req) {
	
	// on recupère la propriété du Bean pour vérifier si elle est a 0 ou 1
	Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int firstConnexionFinish = mBean.getFirstConnexionFinish();
	// si égal 1, on retourne true afin d'envoyer le client à la page du Menu Principale
	if(firstConnexionFinish==1){
	    return true;	    
	} else {
	    return false;
	}
    }

}
