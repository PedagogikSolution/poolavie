package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.model.RegisterModel;

public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = -3396449704222494947L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getRequestDispatcher("jsp/accueil/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// recuperation des 3 inputs du formulaire de la page register
	String nomUtilisateur = req.getParameter("username");
	String motDePasse = req.getParameter("password");
	String courriel = req.getParameter("email");

	// Instantiation de la classe métier pour le processus de registration
	RegisterModel mModel = new RegisterModel();

	// validation des parametres du formulaire
	boolean mValidation = mModel.validationParametre(nomUtilisateur, motDePasse, courriel, req);

	if (mValidation) {
	    req.getRequestDispatcher("jsp/accueil/register.jsp").forward(req, resp);
	    return;
	}

	

	// on verifie si username existe dans le Datastore, si existe on verifie si password match, sinon on crée un nouveau compte client admin

	if (mModel.checkIfUsernameExist(nomUtilisateur,req)) {
	    		
	    req.getRequestDispatcher("jsp/accueil/register.jsp").forward(req, resp);
	  	   
	} else {
	    

	    // étape 1 : on encrypte mot de passe, créer un code de validation, assigne un poolId et player ID, et crée
	    // le bean Utilisateur, le dattastore et le Memcache

	    String validationCode = mModel.createDatastoreUserEntity(nomUtilisateur, motDePasse, courriel,1,1, req);

	    // si le code est retourné, c'est que tout à réussi, donc on envoie un courriel avec Code Validation à l'utilisateur
	    if (validationCode != null) {

		boolean sendingEmail = mModel.sendingValidationCode(nomUtilisateur, courriel, req);
		// si succes du courriel, on envoie vers la page permettant a l'utilisateur d'entrée son code de Validation et ainsi confirmer son abonnement
		if (sendingEmail) {
		    resp.sendRedirect("/validation");
		} else {
		    req.getRequestDispatcher("jsp/accueil/register.jsp").forward(req, resp);
		}

	    } else {
		req.getRequestDispatcher("jsp/accueil/register.jsp").forward(req, resp);
	    }

	}
    }

}
