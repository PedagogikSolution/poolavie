package com.pedagogiksolution.model;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.datastorebeans.Utilisateur;

public class MainModel {

    public Boolean checkIfRegistrationFinish(HttpServletRequest req) {
	
	// on recup�re la propri�t� du Bean pour v�rifier si elle est a 0 ou 1
	Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int firstConnexionFinish = mBean.getFirstConnexionFinish();
	// si �gal 1, on retourne true afin d'envoyer le client � la page du Menu Principale
	if(firstConnexionFinish==1){
	    return true;	    
	} else {
	    return false;
	}
    }

}
