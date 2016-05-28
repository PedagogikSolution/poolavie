package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.model.MenuPrincipalModel;

public class MenuPrincipalServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7516956807248666211L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	MenuPrincipalModel mModel = new MenuPrincipalModel();

	// on verifie si processus de creation terminer
	Boolean checkIfRegistrationFinish = mModel.checkIfRegistrationFinish(req);

	if (!checkIfRegistrationFinish) {

	    // si pas terminé, on vérifie le type d'Utilisateur
	    int checkTypeOfUser = mModel.checkTypeOfUser(req);
	    
	    // si type 1 on envoie finir la creation du Pool, si type 2, on envoie finir Creation d'un joueur du pool
	    if (checkTypeOfUser == 1) {
		resp.sendRedirect("/CreationPool");
	    } else {
		resp.sendRedirect("/CreationDirecteurGeneral");
	    }
	// si terminé, on envoie a la page principal
	} else {
	    
	    req.getRequestDispatcher("jsp/main/main.jsp").forward(req, resp);
	}

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
