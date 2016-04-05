package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.model.RecuperationModel;

public class RecuperationServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7516956807248666211L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getRequestDispatcher("jsp/accueil/recuperation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	String courriel= req.getParameter("email");

	RecuperationModel mModel = new RecuperationModel();
	
	Boolean checkIfEmailExist = mModel.checkIfEmailExist(courriel,req);
	
	if(checkIfEmailExist){
	    mModel.sendInfoForRecuperation(req);
	    resp.sendRedirect("/login");
	} else {
	    req.getRequestDispatcher("jsp/accueil/recuperation.jsp").forward(req, resp);
	}
	
    }
    
    
    

}
