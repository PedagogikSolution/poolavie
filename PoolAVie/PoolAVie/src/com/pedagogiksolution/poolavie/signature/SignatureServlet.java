package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class SignatureServlet extends HttpServlet {
    DatabaseConnector dbHelper;
    Connection conn;
    Date today, start1, finish1, start2, finish2, start3, finish3, start4, finish4, start5, finish5, start6, finish6, start7, finish7;
    boolean isBefore, isAfter;
    String statement1, statement2, statement3;
    int team_id, nb_contrat;
    ResultSet rs1, rs2, rs3;
    String team_id_temp;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
    /**
	 * 
	 */
    private static final long serialVersionUID = 763180796186293473L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

	long now = System.currentTimeMillis();
	today = new Date(now);
	
	initialisationDate();

	if (today.after(start1) && today.before(finish1)) {
	    RachatApresSaison mClass = new RachatApresSaison();
	    mClass.preparationRachatApresSaison(req);
	    req.getRequestDispatcher("/jsp/rachat_fin_saison.jsp").forward(req,resp);
	} else if (today.after(start2) && today.before(finish2)) {
	    RookieApresSaison mClass = new RookieApresSaison();
	    mClass.preparationRookieApresSaison(req);	    
	    req.getRequestDispatcher("/jsp/descendre_rookie_fin_saison.jsp").forward(req,resp);
	} else if (today.after(start3) && today.before(finish3)) {
	    RachatApresChangementAnnee mClass = new RachatApresChangementAnnee();
	    mClass.preparationRachatChangementAnnee(req);
	    req.getRequestDispatcher("/jsp/rachat_debut_saison.jsp").forward(req,resp);
	} else if (today.after(start4) && today.before(finish4)) {
	    SignatureAB mClass = new SignatureAB();
	    mClass.preparationSignatureAB(req);
	    req.getRequestDispatcher("/jsp/signature_ab.jsp").forward(req,resp);
	} else if (today.after(start5) && today.before(finish5)) {
	    RachatApresChangementAnnee mClass = new RachatApresChangementAnnee();
	    mClass.preparationRachatChangementAnnee2(req);
	    req.getRequestDispatcher("/jsp/rachat_debut_saison.jsp").forward(req,resp);
	} else if (today.after(start6) && today.before(finish6)){
	    RookieApresSaison mClass = new RookieApresSaison();
	    mClass.preparationRookieApresSaison2(req);
	    req.getRequestDispatcher("/jsp/drop_rookie.jsp").forward(req,resp);
	} else if (today.after(start7) && today.before(finish7)){
	    SignatureAfterDraft mClass = new SignatureAfterDraft();
	    mClass.preparationSignatureAfterDraft(req,resp);
	    req.getRequestDispatcher("/jsp/signature_after_draft.jsp").forward(req,resp);
	} else {
	    req.getRequestDispatcher("/jsp/aucune_signature_possible.jsp").forward(req,resp);
	}

	

    }
/* ******************************* methode prive  *****************************/
    private void initialisationDate() {
	String date_start1 = "14-09-2015";
	String date_finish1 = "18-09-2015";
	String date_start2 = "18-09-2015";
	String date_finish2 = "19-09-2015";
	String date_start3 = "19-09-2015";
	String date_finish3 = "21-09-2015";
	String date_start4 = "21-09-2015";
	String date_finish4 = "25-09-2015";
	String date_start5 = "02-10-2015";
	String date_finish5 = "04-10-2015";
	String date_start6 = "04-10-2015";
	String date_finish6 = "06-10-2015";
	String date_start7 = "06-10-2015";
	String date_finish7 = "09-10-2015";
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
	    start6 = sdf.parse(date_start6);
	    finish6 = sdf.parse(date_finish6);
	    start7 = sdf.parse(date_start7);
	    finish7 = sdf.parse(date_finish7);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	
    }

/* **********************   Do Post    ********************************* */    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	
    }
}
