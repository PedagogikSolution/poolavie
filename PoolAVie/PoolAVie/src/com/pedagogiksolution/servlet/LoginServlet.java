package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;

public class LoginServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 8061471922583438668L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	    req.getSession().invalidate();
	    req.getRequestDispatcher("jsp/accueil/login.jsp").forward(req, resp); 
	
	
	
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

	// si valide on verifie si le compte est Valider
	if (validateCredential) {

	    Boolean checkIfValidateAccount = mModel.checkIfValidateAccount();

	    if (checkIfValidateAccount) {
		// creation des bean Pool et Classement et Equipe
		Boolean createSessionPoolBean= mModel.createSessionPoolBean();
		Boolean createSessionEquipeBean= mModel.createSessionEquipeBean();
		Boolean createSessionClassementBean= mModel.createSessionClassementBean();
		
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		if(cycleAnnuel>=2){
		mModel.createSessionDraftRoundBean();
		}
		Boolean createSessionDraftPickBean = mModel.createSessionDraftPickBean();
		Boolean createSessionAttaquantBean = mModel.createSessionAttaquantBean();
		Boolean createSessionDefenseurBean = mModel.createSessionDefenseurBean();
		Boolean createSessionGardienBean = mModel.createSessionGardienBean();
		Boolean createSessionRecrueBean = mModel.createSessionRecrueBean();
		
		if(createSessionDefenseurBean&&createSessionGardienBean&&createSessionRecrueBean&&createSessionPoolBean&&createSessionClassementBean&&createSessionEquipeBean&&createSessionDraftPickBean&&createSessionAttaquantBean){
		   
		    DraftPlayersModel mModelDraft = new DraftPlayersModel();
		    
		    Boolean checkIfDatastoreCreate=  mModelDraft.checkIfDatastoreCreate(mBeanPool);
		    if(!checkIfDatastoreCreate){
			mModelDraft.createDraftDatastoreForThatPool(mBeanPool);
		    } 
		    
		    //connectFilter Session Attribute
		    req.getSession().setAttribute("connectUser", 1);
		    
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
