package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;
import com.pedagogiksolution.model.MenuPrincipalModel;
import com.pedagogiksolution.model.RegisterModel;

public class LoginServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8061471922583438668L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	req.getSession().invalidate();
	req.getRequestDispatcher("jsp/accueil/home.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getSession().invalidate();
		
	// recuperation des 2 inputs du formulaire de la page login.jsp
	String nomUtilisateur = req.getParameter("username");
	String motDePasse = req.getParameter("password");

	RegisterModel mModelReg = new RegisterModel();

	// validation des parametres du formulaire
	boolean mValidation = mModelReg.validationParametre(nomUtilisateur, motDePasse, req);

	if (mValidation) {
	    req.getRequestDispatcher("jsp/accueil/home.jsp").forward(req, resp);
	    return;
	}
	
	
	// Instantiation de la classe métier pour le processus de registration
	LoginModel mModel = new LoginModel(nomUtilisateur, motDePasse, req);

	// verifie si le compte est valide, si oui creation du Bean Utilisateur
	Boolean validateCredential = mModel.validateCredential();

	// si valide on verifie si le compte est Valider
	if (validateCredential) {

	    Boolean checkIfValidateAccount = mModel.checkIfValidateAccount();

	    if (checkIfValidateAccount) {
		MenuPrincipalModel mModel2 = new MenuPrincipalModel();

		// on verifie si processus de creation de cette utilisateur est terminé terminer
		Boolean checkIfRegistrationFinish = mModel2.checkIfRegistrationFinish(req);
		if (checkIfRegistrationFinish) {
		    // creation des bean Pool et Classement et Equipe
		    mModel.createSessionPoolBean();
		    mModel.createSessionEquipeBean();
		    mModel.createSessionClassementBean();

		    Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		    int cycleAnnuel = mBeanPool.getCycleAnnuel();
		    if (cycleAnnuel >= 2) {
			mModel.createSessionDraftRoundBean();
		    }
		    mModel.createSessionDraftPickBean();
		    mModel.createSessionAttaquantBean();
		    mModel.createSessionDefenseurBean();
		    mModel.createSessionGardienBean();
		    mModel.createSessionRecrueBean();

		    DraftPlayersModel mModelDraft = new DraftPlayersModel();

		    Boolean checkIfDatastoreCreate = mModelDraft.checkIfDatastoreCreate(mBeanPool);
		    if (!checkIfDatastoreCreate) {
			mModelDraft.createDraftDatastoreForThatPool(mBeanPool);
		    }
		    mModel.resetConnexionOpen();
		    // connectFilter Session Attribute
		    req.getSession().setAttribute("connectUser", 1);

		    resp.sendRedirect("/Nouvelles");

		} else {
		    req.getRequestDispatcher("jsp/accueil/creationnouveaupool.jsp").forward(req, resp);
		}
	    } else {
		req.getRequestDispatcher("jsp/accueil/validation.jsp").forward(req, resp);
	    }
	} else {
	    req.getRequestDispatcher("jsp/accueil/home.jsp").forward(req, resp);
	}

    }

}
