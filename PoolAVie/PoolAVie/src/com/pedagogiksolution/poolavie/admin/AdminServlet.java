package com.pedagogiksolution.poolavie.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
	String mTeamId = (String) req.getSession().getAttribute("adminIn");
	
	
	if(mTeamId.equals("ok")){
	    req.getRequestDispatcher("/jsp/admin.jsp").forward(req,
			resp);
	} else {
	    resp.sendRedirect("/equipes");
	}
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
    }

   

}
