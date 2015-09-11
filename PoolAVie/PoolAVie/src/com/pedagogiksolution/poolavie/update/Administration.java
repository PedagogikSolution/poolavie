package com.pedagogiksolution.poolavie.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class Administration {

    DatabaseConnector mDbHelper = new DatabaseConnector();
    Connection conn = null;
    PreparedStatement mPreparedStatement;

    public void archivageFinSaison() {

// verification si deja fait ou pas ************************************************

	Boolean dejaFait = testSiDejaFait();

	if (dejaFait) {

	} else {
	    insertionDansArchives();
	}

    }

// insertion du classement dans la table archives ************************************
    private void insertionDansArchives() {

	String QueryA, QueryB, QueryC;
	// ouverture de la connexion a la bdd
	mDbHelper.open();

	QueryA = "INSERT INTO classement_archives (`equipe`,`pj`,`but`,`passe`,`points`,`hier`,`semaine`,`mois`,`moyenne`,`difference`,`team_id`,`last_day_pts_total`,`pool_id`,`year`) SELECT equipe,pj,but,passe,points,hier,semaine,mois,moyenne,difference,team_id,last_day_pts_total,pool_id, year FROM classement WHERE pool_id=?";
	QueryB = "INSERT INTO draft_round_archives (`draft_pick_no`,`ronde`,`equipe`,`team_id`,`from_who`,`team_id_from`,`team_count`,`follow_up`,`player_drafted`,`year_of_draft`,`pool_id`) SELECT draft_pick_no,ronde,equipe,team_id,from_who,team_id_from,team_count,follow_up,player_drafted,year_of_draft,pool_id FROM draft_round WHERE pool_id=?";
	QueryC = "INSERT INTO trade_made_archives (team_1,team_2,t1j1,t1j2,t1j3,t1j4,t1j5,t1j6,t1j7,t1p1,t1p2,t1p3,t1_cash,t2j1,t2j2,t2j3,t2j4,t2j5,t2j6,t2j7,t2p1,t2p2,t2p3,t2_cash,date_heure,periode_echange,years,pool_id) SELECT team_1,team_2,t1j1,t1j2,t1j3,t1j4,t1j5,t1j6,t1j7,t1p1,t1p2,t1p3,t1_cash,t2j1,t2j2,t2j3,t2j4,t2j5,t2j6,t2j7,t2p1,t2p2,t2p3,t2_cash,date_heure,periode_echange,years,pool_id FROM trade_made WHERE pool_id=?";
	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement = conn.prepareStatement(QueryC);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}

    }

// methode de verification si archivages deja fait
    private Boolean testSiDejaFait() {
	String QueryD, QueryE, QueryF;
	Boolean testD = false, testE = false, testF = false;
	ResultSet rsD, rsE, rsF;

	// ouverture de la connexion a la bdd
	mDbHelper.open();

	QueryD = "SELECT * FROM classement_archives WHERE pool_id=? AND year=?";
	QueryE = "SELECT * FROM draft_round_archives WHERE pool_id=? AND year_of_draft=?";
	QueryF = "SELECT * FROM trade_made_archives WHERE pool_id=? AND years=?";

	try {
	    mPreparedStatement = conn.prepareStatement(QueryD);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.setInt(2, 2015);
	    rsD = mPreparedStatement.executeQuery();
	    mPreparedStatement = conn.prepareStatement(QueryE);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.setInt(2, 2015);
	    rsE = mPreparedStatement.executeQuery();
	    mPreparedStatement = conn.prepareStatement(QueryF);
	    mPreparedStatement.setInt(1, 1);
	    mPreparedStatement.setInt(2, 2015);
	    rsF = mPreparedStatement.executeQuery();
	    
	    if(rsD.next()){
	    testD = true;
	    }
	    if(rsE.next()){
	    testE = true;
	    }
	    if(rsF.next()){
	    testF = true;
	    }

	    rsD.close();
	    rsE.close();
	    rsF.close();

	} catch (SQLException e) {
	    return true;
	} finally {
	    mDbHelper.close(conn);
	}

	if (testD && testE && testF) {
	    return true;
	} else {
	    return false;
	}

    }

// fin de la class
}
