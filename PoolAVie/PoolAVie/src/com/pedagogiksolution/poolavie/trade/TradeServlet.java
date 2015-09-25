package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TradeServlet extends HttpServlet {
    
  
    Date today, start1, finish1, start2, finish2, start3, finish3, start4, finish4, start5, finish5;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");

    /**
     * 
     */
    private static final long serialVersionUID = 1496997195364376579L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	

	long now = System.currentTimeMillis();
	today = new Date(now);
	
	initialisationDate();

	if (today.after(start1) && today.before(finish1)) {
	    req.getSession().setAttribute("periode_echange_number", "1");
	    req.getSession().setAttribute("periode_echange_ouverte", "1");
	    resp.sendRedirect("/trade_received");
	} else if (today.after(start2) && today.before(finish2)) {
	    req.getSession().setAttribute("periode_echange_number", "2");
	    req.getSession().setAttribute("periode_echange_ouverte", "1");
	    resp.sendRedirect("/trade_received");
	} else if (today.after(start3) && today.before(finish3)) {
	    req.getSession().setAttribute("periode_echange_number", "2");
	    req.getSession().setAttribute("periode_echange_ouverte", "1");
	    resp.sendRedirect("/trade_received");
	} else if (today.after(start4) && today.before(finish4)) {
	    req.getSession().setAttribute("periode_echange_number", "2");
	    req.getSession().setAttribute("periode_echange_ouverte", "1");
	    resp.sendRedirect("/trade_received");
	} else if (today.after(start5) && today.before(finish5)) {
	    req.getSession().setAttribute("periode_echange_number", "3");
	    req.getSession().setAttribute("periode_echange_ouverte", "1");
	    resp.sendRedirect("/trade_received");
	} else {
	    req.getSession().setAttribute("periode_echange_ouverte", "0");
	    resp.sendRedirect("/all_trade");
	}

	

    }
/* ******************************* methode prive  *****************************/
    private void initialisationDate() {
	String date_start1 = "26-09-2015";
	String date_finish1 = "30-09-2015";
	String date_start2 = "10-12-2015";
	String date_finish2 = "10-12-2015";
	String date_start3 = "10-12-2015";
	String date_finish3 = "10-12-2015";
	String date_start4 = "10-12-2015";
	String date_finish4 = "10-12-2015";
	String date_start5 = "09-10-2015";
	String date_finish5 = "10-10-2015";
	try {
	    start1 = sdf.parse(date_start1);
	    finish1 = sdf.parse(date_finish1);
	    start2 = sdf.parse(date_start2);
	    finish2 = sdf.parse(date_finish2);
	    start3 = sdf.parse(date_start3);
	    finish3 = sdf.parse(date_finish3);
	    start4 = sdf.parse(date_start4);
	    finish4 = sdf.parse(date_finish4);
	    start5 = sdf.parse(date_start5);
	    finish5 = sdf.parse(date_finish5);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
    }
    
    
    
    
    
    

}
