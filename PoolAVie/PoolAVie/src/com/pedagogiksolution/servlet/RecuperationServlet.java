package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.model.RecuperationModel;

public class RecuperationServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7516956807248666211L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// verifie si provient de home page ou du courriel de recuperation
	String email = req.getParameter("courriel");
	
	if(email!=null){
	    // on recupere la valeur code des parametre du courriel GET de recuperation
	    String codeValidation = req.getParameter("codeValidation");
	    
	    // on verifie si le code de validation est bon
	    RecuperationModel mModel = new RecuperationModel();
	    Boolean checkIfCodeGood = mModel.checkIfValidationCodeIsGood(req,email,codeValidation);
	    
	    if(checkIfCodeGood){
		// si code est bon, on va chercher les compte utilisateur associer a ce courriel
		//et on propose de changer le courriel de celui cliquer et on revient a la page d'Accueil avec message de reussite du changement de mot de passe
		mModel.prepareResetOfAllAccount(req,email);
		req.getRequestDispatcher("jsp/accueil/recuperation2.jsp").forward(req, resp);
	    } else {
		// si code pas bon, on retourne a page de recuperation avec message erreur
		req.getRequestDispatcher("jsp/accueil/recuperation.jsp").forward(req, resp);
	    }
	    
	   
	    
	} else {	
	    req.getRequestDispatcher("jsp/accueil/recuperation.jsp").forward(req, resp);
	}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	RecuperationModel mModel = new RecuperationModel();
	
	
	String username = req.getParameter("username");
	
	if(username!=null){
	    String password = req.getParameter("password");
	    mModel.changePassword(username,password,req);
	    
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireLogin("Votre mot de passe a été changé ave  succès");
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    req.getRequestDispatcher("jsp/accueil/home.jsp").forward(req, resp);
	    
	    
	} else {
	
	String courriel= req.getParameter("email");
	
	Boolean checkIfEmailExist = mModel.checkIfEmailExist(courriel,req);
	
	if(checkIfEmailExist){
	    String codeValidation = "A1B2C3D4";
	    mModel.sendInfoForRecuperation(req,courriel,codeValidation);
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireLogin("Un courriel vous a été envoyé afin de récupérer vos identifiants");
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    req.getRequestDispatcher("jsp/accueil/home.jsp").forward(req, resp);
	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRecuperation("Cette adresse courriel n'existe pas");
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    req.getRequestDispatcher("jsp/accueil/recuperation.jsp").forward(req, resp);
	}
	
	}
	
    }
    
    
    

}
