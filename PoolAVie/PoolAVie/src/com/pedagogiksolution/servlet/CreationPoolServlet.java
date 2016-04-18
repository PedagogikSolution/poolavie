package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.model.CreationPoolModel;

public class CreationPoolServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -2677090836243950997L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getRequestDispatcher("jsp/accueil/creationnouveaupool.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	// initilisation du métier
	CreationPoolModel mModel = new CreationPoolModel();

	// test des parametres formulaire bien rempli
	Boolean validationFormulaireCreation = mModel.validationFormulaireCreation(req);

	// si false, retourne a la page de creation avec message d'erreur approprié
	if (!validationFormulaireCreation) {
	    req.getRequestDispatcher("jsp/accueil/creationnouveaupool.jsp").forward(req, resp);
	} else {

	    mModel.createPoolBean(req);
	    mModel.createEquipeBean(req);
	  //  mModel.sendEmail(req);
	  //  mModel.createDatabase(req);
	    mModel.createSucceed(req);

	    resp.sendRedirect("/MenuPrincipal");

	}

    }

}
