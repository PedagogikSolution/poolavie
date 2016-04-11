package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreationServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -2677090836243950997L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getRequestDispatcher("jsp/main/creation.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String courriel1 = req.getParameter("email1");
	String courriel2 = req.getParameter("email2");
	String courriel3 = req.getParameter("email3");
	String courriel4 = req.getParameter("email4");
	String courriel5 = req.getParameter("email5");
	String courriel6 = req.getParameter("email6");
	String courriel7 = req.getParameter("email7");
	String courriel8 = req.getParameter("email8");
	String nomDuPool = req.getParameter("nomDuPool");
	
	
    }
    
    

}
