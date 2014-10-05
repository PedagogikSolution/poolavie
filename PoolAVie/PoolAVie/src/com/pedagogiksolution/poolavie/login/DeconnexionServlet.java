package com.pedagogiksolution.poolavie.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeconnexionServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8556013338234489936L;
	public static final String URL_REDIRECTION = "http://localhost:8888/";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération et destruction de la session en cours */
        HttpSession session = request.getSession();
        session.invalidate();

        /* Redirection vers le Site du Zéro ! */
        response.sendRedirect( URL_REDIRECTION );
    }
}
