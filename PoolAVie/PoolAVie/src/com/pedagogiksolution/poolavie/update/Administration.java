package com.pedagogiksolution.poolavie.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class Administration {

    public void archivageFinSaison() {
	String QueryA,QueryB,QueryC;
	DatabaseConnector mDbHelper = new DatabaseConnector();
	Connection conn = mDbHelper.open();
	PreparedStatement mPreparedStatement;
// ouverture de la connexion a la bdd	
	mDbHelper.open();
// insertion du classement dans la table archives	
	QueryA = "INSERT INTO classement_archives (`equipe`,`pj`,`but`,`passe`,`points`,`hier`,`semaine`,`mois`,`moyenne`,`difference`,`team_id`,`last_day_pts_total`,`pool_id`,`year`) SELECT equipe,pj,but,passe,points,hier,semaine,mois,moyenne,difference,team_id,last_day_pts_total,pool_id, year FROM classement WHERE pool_id=?";
	QueryB = "INSERT INTO draft_round_archives (`draft_pick_no`,`ronde`,`equipe`,`team_id`,`from_who`,`team_id_from`,`team_count`,`follow_up`,`player_drafted`,`year_of_draft`,`pool_id`) SELECT draft_pick_no,ronde,equipe,team_id,from_who,team_id_from,team_count,follow_up,player_drafted,year_of_draft,pool_id FROM draft_round WHERE pool_id=?";
	QueryC = "INSERT INTO trade_made_archives (team_1,team_2,t1j1,t1j2,t1j3,t1j4,t1j5,t1j6,t1j7,t1p1,t1p2,t1p3,t1_cash,t2j1,t2j2,t2j3,t2j4,t2j5,t2j6,t2j7,t2p1,t2p2,t2p3,t2_cash,date_heure,periode_echange,years,pool_id) SELECT team_1,team_2,t1j1,t1j2,t1j3,t1j4,t1j5,t1j6,t1j7,t1p1,t1p2,t1p3,t1_cash,t2j1,t2j2,t2j3,t2j4,t2j5,t2j6,t2j7,t2p1,t2p2,t2p3,t2_cash,date_heure,periode_echange,years,pool_id FROM trade_made WHERE pool_id=?";
	try {
	    mPreparedStatement = conn.prepareStatement(QueryA);
	    mPreparedStatement.setInt(1,1);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement = conn.prepareStatement(QueryB);
	    mPreparedStatement.setInt(1,1);
	    mPreparedStatement.executeUpdate();
	    mPreparedStatement = conn.prepareStatement(QueryC);
	    mPreparedStatement.setInt(1,1);
	    mPreparedStatement.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}
	
	
	
	
	
    }

}
