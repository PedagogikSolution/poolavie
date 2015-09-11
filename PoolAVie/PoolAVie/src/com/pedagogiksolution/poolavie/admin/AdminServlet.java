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
	case 5:
	    break;
	case 6 :
	    break;
	case 7:
	    break;
	case 8 :
	    break;    
	
	
	
	
	
	
	
	}
	
    }

   

}
