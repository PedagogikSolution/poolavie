package com.pedagogiksolution.servlet;

import static com.pedagogiksolution.constants.MessageErreurConstants.NOT_LOG_IN;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.model.LoginModel;

public class LoginServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8061471922583438668L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//  test pour voir si arrive au servlet via le processus de validation de l'objet sesssion dans chaque page jsp des sections privées via
	//  <c:if test="${Utilisateur.loginReussi != 1 }">
	//  <c:redirect url="/login?notLoggin=1"></c:redirect>
	//  </c:if>
	String testLogin = req.getParameter("notLoggin");
	if (testLogin != null) {
	    // Si arrive avec le parametre notLoggin, on renvoie à la page de connexion avec message vous n'êtes pas login
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurNotLogIn(NOT_LOG_IN);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	} else {
	    // sinon on renvoie normalement à la page de login sans message
	    req.getRequestDispatcher("jsp/accueil/login.jsp").forward(req, resp); 
	}
	
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// recuperation des 2 inputs du formulaire de la page login.jsp
	String nomUtilisateur = req.getParameter("username");
	String motDePasse = req.getParameter("password");

	// Instantiation de la classe métier pour le processus de registration
	LoginModel mModel = new LoginModel(nomUtilisateur,motDePasse,req);

	// verifie si le compte est valide, si oui creation du Bean Utilisateur
	Boolean validateCredential = mModel.validateCredential();

	// si valide on verifie les identifiants, si pas bon , on retourne a login page avec message d'erreur
	if (validateCredential) {

	    Boolean checkIfValidateAccount = mModel.checkIfValidateAccount();

	    if (checkIfValidateAccount) {
		// creation des bean Pool et Classement et Equipe
		Boolean createSessionPoolBean= mModel.createSessionPoolBean();
		Boolean createSessionEquipeBean= mModel.createSessionEquipeBean();
		Boolean createSessionClassementBean= mModel.createSessionClassementBean();
		
		if(createSessionPoolBean&&createSessionClassementBean&&createSessionEquipeBean){
		   
		    resp.sendRedirect("/Nouvelles");
		    
		} else {
		    req.getRequestDispatcher("jsp/accueil/creationnouveaupool.jsp").forward(req, resp);    
		}
	    } else {
		req.getRequestDispatcher("jsp/accueil/validation.jsp").forward(req, resp);
	    }
	} else {
	    req.getRequestDispatcher("jsp/accueil/login.jsp").forward(req, resp);
	}

    }

}
