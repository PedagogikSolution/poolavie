package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;
import com.pedagogiksolution.model.MenuPrincipalModel;
import com.pedagogiksolution.model.NouvellesModel;

public class NouvellesServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7516956807248666211L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	MenuPrincipalModel mModel = new MenuPrincipalModel();

	// on verifie si processus de creation de cette utilisateur est termine terminer
	Boolean checkIfRegistrationFinish = mModel.checkIfRegistrationFinish(req);

	if (!checkIfRegistrationFinish) {

	    // si pas termine, on verifie le type d'Utilisateur
	    int checkTypeOfUser = mModel.checkTypeOfUser(req);
	    
	    // si type 1 on envoie finir la creation du Pool, si type 2, on envoie finir Creation d'un joueur du pool
	    if (checkTypeOfUser == 1) {
		resp.sendRedirect("/CreationPool");
	    } else {
		resp.sendRedirect("/CreationDirecteurGeneral");
	    }
	// si termine, on envoie a la page principal
	} else {
	    
	    NouvellesModel mModelNouvelles = new NouvellesModel();
	    mModelNouvelles.putNewsInBean(req);
	    
	    LoginModel mModel2 = new LoginModel(req);
	    mModel2.createSessionEquipeBean();
	    mModel2.createSessionAttaquantBean();
	    mModel2.createSessionDefenseurBean();
	    mModel2.createSessionGardienBean();
	    mModel2.createSessionRecrueBean();
	    mModel2.createSessionDraftPickBean();
	    mModel2.createSessionDraftRoundBean();
	    mModel2.createSessionPoolBean();
	    
	    // on check si la date du pool est en cours
	    Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	    int cycleAnnuel = mBeanPool.getCycleAnnuel();
	    
	    if(cycleAnnuel==2){
		AdminModel mAdminModel = new AdminModel();
		mAdminModel.checkIfDraftDay(mBeanPool,req);
	    }
	   
		if (cycleAnnuel == 3) {
		   	    
		    DraftPlayersModel mModelDraft = new DraftPlayersModel();	    	   
		    mModelDraft.putDatastoreIntoBean(mBeanPool,req);
		}
	    req.getRequestDispatcher("jsp/main/nouvelles.jsp").forward(req, resp);
	}

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	String titre = req.getParameter("titre");
	String body = req.getParameter("body");
	
	NouvellesModel mModelNouvelles = new NouvellesModel();
	mModelNouvelles.createMessageForNewsByUser(titre, body, req);
	
	resp.sendRedirect("/Nouvelles");
	
    }

}
