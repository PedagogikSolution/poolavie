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
	/* R�cup�ration d'une instance de notre nos DAO */
	this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	int fromId;
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	int cycleAnnuel = mBeanPool.getCycleAnnuel();
	if (cycleAnnuel == 3) {

	    DraftPlayersModel mModelDraft = new DraftPlayersModel();
	    mModelDraft.putDatastoreIntoBean(mBeanPool, req);
	}

	String from = req.getParameter("from");
	if(from!=null){
	 fromId = Integer.parseInt(from);
	} else {
	    fromId = 1;
	}
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
	
	if(cycleAnnuel==7){
		fromId=3;
		req.getSession().removeAttribute("beanConfirmationRachat");
		SignatureModel mModelSignature = new SignatureModel(playersDao);
		
		mModelSignature.preparationRachatApresSaison(req);
		
		
		
	}
	switch (fromId) {
	case 1: // cycle 0 a 3 ou 5 et 6
	    req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
	    break;
	case 2: // siganture apres darft
	    req.getRequestDispatcher("jsp/signature/signatureAfterDraft.jsp").forward(req, resp);
	    break;
	case 3: // rachat de contrat fin de saison
	    req.getRequestDispatcher("jsp/signature/rachatContrat.jsp").forward(req, resp);
	    break;
	case 4:
	    req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
	    break;
	case 5:
	    req.getRequestDispatcher("jsp/signature/managerCenter.jsp").forward(req, resp);
	    break;
	case 992: // message deja 12 contrat
	    req.setAttribute("messageErreurs", "Vous avez le nombre maximal de joueur sous contrat dans votre �quipe");
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
		
		
		Boolean checkIfCashIsGood = mModel.checkIfCashAvailablePourRachat(req);
		if(checkIfCashIsGood) {
			req.getRequestDispatcher("jsp/signature/rachatContrat.jsp").forward(req, resp);
		} else {
			req.setAttribute("messageErreurs", "Vous n'avez pas le budget nécessaire pour racheter ce joueur");
			req.getRequestDispatcher("jsp/signature/rachatContrat.jsp").forward(req, resp);
		}
		
		    
	    break;
	case 3:
	    break;
	case 4:
	    break;

	}

    }

}
