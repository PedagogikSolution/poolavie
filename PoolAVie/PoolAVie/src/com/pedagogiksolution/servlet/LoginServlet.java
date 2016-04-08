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

	String testLogin = req.getParameter("notLoggin");
	if (testLogin != null) {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurNotLogIn(NOT_LOG_IN);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	}
	req.getRequestDispatcher("jsp/accueil/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// recuperation des 3 inputs du formulaire de la page register
	String nomUtilisateur = req.getParameter("username");
	String motDePasse = req.getParameter("password");

	// Instantiation de la classe métier pour le processus de registration
	LoginModel mModel = new LoginModel();

	// verifie si le compte est valide
	Boolean validateCredential = mModel.validateCredential(nomUtilisateur, motDePasse, req);

	// si valide on verifie les identifiants, si pas bon , on retourne a login page avec message d'erreur
	if (validateCredential) {

	    Boolean checkIfValidateAccount = mModel.checkIfValidateAccount(req);

	    if (checkIfValidateAccount) {
		resp.sendRedirect("/MenuPrincipal");
	    } else {
		req.getRequestDispatcher("jsp/accueil/validation.jsp").forward(req, resp);
	    }
	} else {
	    req.getRequestDispatcher("jsp/accueil/login.jsp").forward(req, resp);
	}

    }

}
