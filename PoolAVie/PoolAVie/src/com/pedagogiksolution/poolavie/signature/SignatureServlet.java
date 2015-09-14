package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    Date today, start1, finish1, start2, finish2, start3, finish3, start4, finish4, start5, finish5, start6, finish6;
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
	    req.getRequestDispatcher("/jsp/signature_fin_saison.jsp").forward(req,resp);
	} else if (today.after(start3) && today.before(finish3)) {
	    req.getRequestDispatcher("/jsp/rachat_after_signature.jsp").forward(req,resp);
	} else if (today.after(start4) && today.before(finish4)) {
	    req.getRequestDispatcher("/jsp/rachat_after_trade.jsp").forward(req,resp);
	} else if (today.after(start5) && today.before(finish5)) {
	    req.getRequestDispatcher("/jsp/drop_upgrade_rookie.jsp").forward(req,resp);
	} else if (today.after(start6) && today.before(finish6)){
	    req.getRequestDispatcher("/jsp/drop_upgrade_rookie.jsp").forward(req,resp);
	} else {
	    req.getRequestDispatcher("/jsp/aucune_signature_possible.jsp").forward(req,resp);
	}

	

    }
/* ******************************* methode prive  *****************************/
    private void initialisationDate() {
	String date_start1 = "14-09-2015";
	String date_finish1 = "16-09-2015";
	String date_start2 = "18-09-2015";
	String date_finish2 = "22-09-2015";
	String date_start3 = "01-01-2000";
	String date_finish3 = "01-01-2000";
	String date_start4 = "29-09-2015";
	String date_finish4 = "30-09-2015";
	String date_start5 = "01-10-2015";
	String date_finish5 = "05-10-2015";
	String date_start6 = "01-01-2015";
	String date_finish6 = "01-01-2015";
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
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	
    }

/* **********************   Do Post    ********************************* */    

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {
	/***** récupération de l'équipe active *****/

	team_id_temp = (String) req.getSession().getAttribute("mTeamId");
	team_id = Integer.parseInt(team_id_temp);

	/****
	 * vérification si la date est éligible pour la période de siganture
	 * apres draft
	 *****/

	String date_start = "23-10-2014";
	try {
	    start1 = sdf.parse(date_start);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	String date_finish = "31-10-2014";
	try {
	    finish1 = sdf.parse(date_finish);
	} catch (ParseException e) {
	    e.printStackTrace();
	}

	isBefore = today.before(finish1);
	isAfter = today.after(start1);

	if (isBefore && isAfter) {
	    /*** connection au bd ****/
	    dbHelper = new DatabaseConnector();

	    conn = dbHelper.open();

	    try {

		/****** on calcul le nombre de contrat actuel du team actif ************/
		statement1 = "SELECT * FROM equipes WHERE team_id =" + team_id
			+ "";
		try {
		    rs1 = conn.createStatement().executeQuery(statement1);

		    if (rs1.next()) {
			nb_contrat = rs1.getInt("nb_contrat");
		    }

		    /** on vérifie si il y a de la place sur les contrats ***/

		    if (nb_contrat < 12) {

			/****** recupération de la liste des joueurs autonome *****/

			statement2 = "SELECT * FROM players WHERE team_id = "
				+ team_id
				+ " AND club_ecole=0 AND years_2='JA'";

			rs2 = conn.createStatement().executeQuery(statement2);

			req.setAttribute("all_possible_signature", rs2);

			req.getRequestDispatcher(
				"/jsp/signature_draft_fini.jsp").forward(req,
				resp);

		    } else {
			resp.sendRedirect("/jsp/signature_max_atteint.jsp");

		    }

		} catch (SQLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	    } finally {
		dbHelper.close(conn);
	    }

	} else {

	    resp.sendRedirect("/jsp/signature_after_draft.jsp");

	}

    }
}
