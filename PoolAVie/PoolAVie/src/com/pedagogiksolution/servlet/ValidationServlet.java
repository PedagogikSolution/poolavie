package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.model.ValidationModel;

public class ValidationServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 968403070826255091L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getRequestDispatcher("jsp/accueil/validation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// initialisation de la classe métier
	ValidationModel mModel = new ValidationModel();
	// recuperation de la provenance du client et du type de formulaire
	String mParam = req.getParameter("formulaire");
	int mSwitch = Integer.parseInt(mParam);

	String mCode = req.getParameter("validation");

	switch (mSwitch) {

	case 1:
	    Boolean validationDuCode = mModel.validerCode(mCode, req);

	    if (validationDuCode) {
		resp.sendRedirect("/MenuPrincipal");
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
