package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.SignatureModel;

public class SignatureServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 54388717965389157L;

    public static final String CONF_DAO_FACTORY = "daofactory";
    private PlayersDao playersDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre nos DAO */
	this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	int cycleAnnuel = mBeanPool.getCycleAnnuel();
	if (cycleAnnuel == 3) {

	    DraftPlayersModel mModelDraft = new DraftPlayersModel();
	    mModelDraft.putDatastoreIntoBean(mBeanPool, req);
	}

	String from = req.getParameter("from");
	int fromId = Integer.parseInt(from);
	if (cycleAnnuel == 4) {
	    SignatureModel mModelSignature = new SignatureModel(playersDao);

	    Boolean checkIfSignatureIsPossible = mModelSignature.checkIfSignatureIsPossible(req);
	    
	    if (checkIfSignatureIsPossible) {

		mModelSignature.putPlayersThatCanBeSignInBean(req);

		fromId = 2;
	    } else {
		fromId = 992;
	    }

	}
	switch (fromId) {
	case 1:
	    req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
	    break;
	case 2:
	    req.getRequestDispatcher("jsp/signature/signatureAfterDraft.jsp").forward(req, resp);
	    break;
	case 3:
	    req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
	    break;
	case 4:
	    req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
	    break;
	case 5:
	    req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
	    break;
	case 992: // message deja 8 contrat
	    req.setAttribute("messageErreurs", "Vous avez le nombre maximal de joueur sous contrat dans votre équipe");
	    req.getRequestDispatcher("jsp/signature/signatureAfterDraft.jsp").forward(req, resp);
	    break;

	}

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	SignatureModel mModel = new SignatureModel(playersDao);
	String signatureStep = req.getParameter("signatureStep");
	int signatureStepId = Integer.parseInt(signatureStep);

	switch (signatureStepId) {
	case 1:
	    // on persiste dans bdd et datastore et session les changements

	    mModel.signatureAfterDraft(req);
	    resp.sendRedirect("/Signature?from=2");

	    break;
	case 2:
	    break;
	case 3:
	    break;
	case 4:
	    break;

	}

    }

}
