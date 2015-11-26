package com.pedagogiksolution.poolavie.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.update.Administration;


public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
	String mTeamId = (String) req.getSession().getAttribute("adminIn");
	
	
	if(mTeamId.equals("ok")){
	    req.getRequestDispatcher("/jsp/admin.jsp").forward(req,resp);
	} else {
	    resp.sendRedirect("/equipes");
	}
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
	String admin = req.getParameter("admin");
	int admin2 = Integer.parseInt(admin);
	Administration administration = new Administration();
	
	
	switch(admin2){
	
	case 1: administration.archivageFinSaison();
		resp.sendRedirect("/admin");
	    break;
	case 2 : administration.vidageFinSaison();
		resp.sendRedirect("/admin");
	    break;
	case 3:	administration.archivageJoueurs();
		resp.sendRedirect("/admin");
	    break;
	case 4 : administration.vidageJoueurs();
		resp.sendRedirect("/admin");
	    break;
	case 5: administration.update_take_proj();
		resp.sendRedirect("/admin");
	    break;
	case 6 : administration.resetBudget();
		resp.sendRedirect("/admin");
	
	    break;
	case 7:  administration.dropJoueurAB();
		resp.sendRedirect("/admin");
	    break;
	case 8 : administration.updateAge();
	resp.sendRedirect("/admin");
	    break; 
	    
	case 9 : administration.dropRookieTropAge();
	resp.sendRedirect("/admin");
	    break; 
	case 10 : administration.updateSalaireDraft();
	resp.sendRedirect("/admin");
	    break; 
	case 11 : administration.updateProjection();
	resp.sendRedirect("/admin");
	    break; 
	case 12 : administration.updateDraftOrder();
	resp.sendRedirect("/admin");
	    break; 
	case 13 : administration.updateDraftCurrentYear();
	resp.sendRedirect("/admin");
	    break;
	
	
	
	
	
	
	
	}
	
    }

   

}
