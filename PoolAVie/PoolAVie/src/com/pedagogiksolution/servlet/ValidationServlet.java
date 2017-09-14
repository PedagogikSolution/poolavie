package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.model.CreationPoolModel;
import com.pedagogiksolution.model.ValidationModel;

public class ValidationServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 968403070826255091L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	CreationPoolModel mModel = new CreationPoolModel();
	Boolean creationNewPoolOpen = mModel.checkIfDateIsGoodForNewPool();
	if(creationNewPoolOpen){
	req.getRequestDispatcher("jsp/accueil/validation.jsp").forward(req, resp);
	}else {
	    MessageErreurBeans mBeanErreur = new MessageErreurBeans();
	    mBeanErreur.setErreurFormulaireRegistration("Vous ne pouvez pas créer de nouveau pool à ce moment d'une saison en cours. Merci de revenir entre le 1 juillet et le 31 décembre pour débuter un nouveau Pool.");
	    req.setAttribute("mauvaiseDate", mBeanErreur);
	    req.getRequestDispatcher("jsp/accueil/creationnouveaupool.jsp").forward(req, resp);   
	}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// initialisation de la classe metier
	ValidationModel mModel = new ValidationModel();
	// recuperation de la provenance du client et du type de formulaire
	String mParam = req.getParameter("formulaire");
	int mSwitch = Integer.parseInt(mParam);

	String mCode = req.getParameter("validation");

	switch (mSwitch) {

	case 1:
	    Boolean validationDuCode = mModel.validerCode(mCode, req);

	    if (validationDuCode) {

		
		//connectFilter Session Attribute
		    req.getSession().setAttribute("connectUser", 1);
		resp.sendRedirect("/Nouvelles");
	    } else {
		req.getRequestDispatcher("jsp/accueil/validation.jsp").forward(req, resp);
	    }
	    break;
	case 2:
	    mModel.envoyerNouveauCode();
	    break;
	case 3:
	    mModel.changementDeCourriel();
	    break;

	}

    }

}
