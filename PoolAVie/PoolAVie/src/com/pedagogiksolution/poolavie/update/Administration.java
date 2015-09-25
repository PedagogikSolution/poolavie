package com.pedagogiksolution.poolavie.update;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class Administration {

    DatabaseConnector mDbHelper = new DatabaseConnector();
    Connection conn = null;

    public void archivageFinSaison() {

	Boolean dejaFait = testSiDejaFait();
	if (dejaFait) {
	} else {
	    insertionDansArchives();
	}
    }

    public void vidageFinSaison() {
	Boolean dejaFait = testSiDejaFait();
	if (dejaFait) {
	    reinitialisationTableClassement();
	    reinitialisationTableDraftRound();
	    reinitialisationTableTradeMade();
	} else {

	}

    }

    public void archivageJoueurs() {
	Boolean dejaFaitJoueurs = testSiDejaFaitJoueur();
	if (dejaFaitJoueurs) {
	} else {
	    insertionDansArchivesJoueurs();
	}

    }

    public void vidageJoueurs() {
	Boolean dejaFaitJoueurs = testSiDejaFaitJoueur();
	if (dejaFaitJoueurs) {
	    vidageJoueursFinDeSaison();
	} else {

	}

    }

    public void update_take_proj() {
	Statement mStatementA, mStatementB;
	String QueryA, QueryB;
	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryA = "UPDATE players SET take_proj=0";
	QueryB = "UPDATE players SET take_proj=1 WHERE pj<50";
	try {
	    mStatementA = conn.createStatement();
	    mStatementA.executeUpdate(QueryA);
	    mStatementB = conn.createStatement();
	    mStatementB.executeUpdate(QueryB);
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}

    }

    public void resetBudget() {
	updateEffectif();
	resetYears();

    }
    
    public void dropJoueurAB() {
   	Statement mStatementA;
   	String QueryA;
   	
   	conn = mDbHelper.open();
   	
   	QueryA = "UPDATE players SET equipe=null,contrat=0,salaire_contrat=null,contrat_cours=null,contrat_max_years=null," +
   			"type_contrat=null,club_ecole=null,years_1=null,years_2=null,years_3=null,years_4=null,years_5=null," +
   			"team_id=null,projection=null WHERE years_1='A' OR years_1='B'";
	
   	
   	try {
	    mStatementA = conn.createStatement();
	    mStatementA.executeUpdate(QueryA);
	    mStatementA.close();
	 
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}
   	
       }

    

    /**************************** Methode privée de la class *********************************************/
    
    private void resetYears() {
	Statement mStatementA;
	String QueryA;
	conn = mDbHelper.open();
	
	QueryA = "UPDATE players SET years_1=years_2,years_2=years_3,years_3=years_4,years_4=years_5,years_5='X'";
	
	try {
	    mStatementA = conn.createStatement();
	    mStatementA.executeUpdate(QueryA);
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}
    }


    private void updateEffectif() {
	PreparedStatement mStatementA;
	ResultSet rs;
	String QueryA, QueryB, QueryC, QueryD,QueryG;
	int nb_attaquant = 0, nb_defenseur = 0, nb_gardien = 0, nb_rookie = 0, nb_contrat = 0, nb_equipe = 0, manquant_equipe = 0, manquant_att = 0, manquant_def = 0, manquant_gardien = 0, manquant_recrue = 0, total_salaire_now = 0;
	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();
	int i;
	QueryA = "SELECT COUNT(_id) FROM players WHERE years_2>1 AND team_id=? AND position=? AND club_ecole=0";
	QueryB = "SELECT COUNT(_id) FROM players WHERE years_2>1 AND team_id=? AND club_ecole=1";
	QueryC = "SELECT sum(salaire_contrat) FROM players WHERE years_2>1 AND team_id=? AND club_ecole=0";
	QueryD = "UPDATE equipes SET nb_attaquant=?,nb_defenseur=?,nb_gardien=?,nb_rookie=?,nb_contrat=?,"
		+ "nb_equipe=?,manquant_equipe=?,manquant_att=?,manquant_def=?,manquant_gardien=?,"
		+ "manquant_recrue=?,total_salaire_now=? WHERE team_id=?";
	QueryG = "UPDATE equipes SET max_salaire_begin=52000000+argent_recu+bonus_penalite,budget_restant=max_salaire_begin-total_salaire_now,moy_sal_restant_draft=budget_restant/?,argent_recu=0,bonus_penalite=0 WHERE team_id=?";

	try {

	    for (i = 0; i < 10; i++) {

		mStatementA = conn.prepareStatement(QueryA);
		mStatementA.setInt(1, i);
		mStatementA.setString(2, "attaquant");
		rs = mStatementA.executeQuery();
		if (rs.next()) {
		    nb_attaquant = rs.getInt(1);
		}
		rs.close();
		mStatementA.close();

		mStatementA = conn.prepareStatement(QueryA);
		mStatementA.setInt(1, i);
		mStatementA.setString(2, "defenseur");
		rs = mStatementA.executeQuery();
		if (rs.next()) {
		    nb_defenseur = rs.getInt(1);
		}
		rs.close();
		mStatementA.close();

		mStatementA = conn.prepareStatement(QueryA);
		mStatementA.setInt(1, i);
		mStatementA.setString(2, "gardien");
		rs = mStatementA.executeQuery();
		if (rs.next()) {
		    nb_gardien = rs.getInt(1);
		}
		rs.close();
		mStatementA.close();

		mStatementA = conn.prepareStatement(QueryB);
		mStatementA.setInt(1, i);
		rs = mStatementA.executeQuery();
		if (rs.next()) {
		    nb_rookie = rs.getInt(1);
		}
		rs.close();
		mStatementA.close();

		mStatementA = conn.prepareStatement(QueryC);
		mStatementA.setInt(1, i);
		rs = mStatementA.executeQuery();
		if (rs.next()) {
		    total_salaire_now = rs.getInt(1);
		}
		rs.close();
		mStatementA.close();

		nb_contrat = nb_attaquant + nb_defenseur + nb_gardien;
		nb_equipe = nb_contrat;
		manquant_equipe = 23 - nb_equipe;
		manquant_att = 8 - nb_attaquant;
		manquant_def = 5 - nb_defenseur;
		manquant_gardien = 2 - nb_gardien;
		manquant_recrue = 8 - nb_rookie;

		mStatementA = conn.prepareStatement(QueryD);
		mStatementA.setInt(1, nb_attaquant);
		mStatementA.setInt(2, nb_defenseur);
		mStatementA.setInt(3, nb_gardien);
		mStatementA.setInt(4, nb_rookie);
		mStatementA.setInt(5, nb_contrat);
		mStatementA.setInt(6, nb_equipe);
		mStatementA.setInt(7, manquant_equipe);
		mStatementA.setInt(8, manquant_att);
		mStatementA.setInt(9, manquant_def);
		mStatementA.setInt(10, manquant_gardien);
		mStatementA.setInt(11, manquant_recrue);
		mStatementA.setInt(12, total_salaire_now);
		mStatementA.setInt(13, i);
		mStatementA.executeUpdate();
		rs.close();
		mStatementA.close();

		mStatementA = conn.prepareStatement(QueryG);
		mStatementA.setInt(1, manquant_equipe);
		mStatementA.setInt(2, i);
		mStatementA.executeUpdate();
		mStatementA.close();
		// fin de la boucle
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}

    }

    private void vidageJoueursFinDeSaison() {
	Statement mStatementA;
	String QueryA;
	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryA = "UPDATE players SET equipe=null,contrat=0,salaire_contrat=null,contrat_cours=null,contrat_max_years=null,type_contrat=null,club_ecole=null,years_1=null,years_2=null,years_3=null,years_4=null,years_5=null,team_id=null,projection=null WHERE years_2='X' OR years_2='JA'";
	try {
	    mStatementA = conn.createStatement();
	    mStatementA.executeUpdate(QueryA);
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}

    }

    private void insertionDansArchivesJoueurs() {
	Statement mStatementA;
	String QueryA;
	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryA = "INSERT INTO players_archives SELECT * FROM players WHERE equipe IS NOT NULL";
	try {
	    mStatementA = conn.createStatement();
	    mStatementA.executeUpdate(QueryA);
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}

    }

    private Boolean testSiDejaFaitJoueur() {
	String QueryA;
	Boolean testA = false;
	ResultSet rsA;
	Statement mStatementA;

	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryA = "SELECT * FROM players_archives";

	try {
	    mStatementA = conn.createStatement();

	    rsA = mStatementA.executeQuery(QueryA);

	    if (rsA.next()) {
		testA = true;
	    }

	    rsA.close();

	} catch (SQLException e) {
	    return true;
	} finally {
	    mDbHelper.close(conn);
	}

	if (testA) {
	    return true;
	} else {
	    return false;
	}
    }

    private void reinitialisationTableTradeMade() {
	Statement mStatementA;
	String QueryA;
	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryA = "TRUNCATE trade_made";
	try {
	    mStatementA = conn.createStatement();
	    mStatementA.executeUpdate(QueryA);
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}

    }

    private void reinitialisationTableDraftRound() {
	PreparedStatement mPreparedStatementA;
	String QueryA;
	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryA = "UPDATE draft_round SET equipe=null,team_id=null,from_who=null,team_id_from=null,team_count=null,follow_up=0,player_drafted=null,year_of_draft=2016 WHERE pool_id=?";
	try {
	    mPreparedStatementA = conn.prepareStatement(QueryA);
	    mPreparedStatementA.setInt(1, 1);
	    mPreparedStatementA.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}

    }

    private void reinitialisationTableClassement() {
	PreparedStatement mPreparedStatementA;
	String QueryA;
	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryA = "UPDATE classement SET pj=0,but=0,passe=0,points=0,hier=0,semaine=0,mois=0,moyenne=0,difference=0,last_day_pts_total=0,year_of_the_standing=2016 WHERE pool_id=?";
	try {
	    mPreparedStatementA = conn.prepareStatement(QueryA);
	    mPreparedStatementA.setInt(1, 1);
	    mPreparedStatementA.executeUpdate();
	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    mDbHelper.close(conn);
	}

    }

// insertion du classement dans la table archives ************************************
    private void insertionDansArchives() {
	PreparedStatement mPreparedStatementA, mPreparedStatementB, mPreparedStatementC;
	String QueryA, QueryB, QueryC;
	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryA = "INSERT INTO classement_archives (`equipe`,`pj`,`but`,`passe`,`points`,`hier`,`semaine`,`mois`,`moyenne`,`difference`,`team_id`,`last_day_pts_total`,`pool_id`,`year_of_the_standing`) SELECT equipe,pj,but,passe,points,hier,semaine,mois,moyenne,difference,team_id,last_day_pts_total,pool_id, year_of_the_standing FROM classement WHERE pool_id=?";
	QueryB = "INSERT INTO draft_round_archives (`draft_pick_no`,`ronde`,`equipe`,`team_id`,`from_who`,`team_id_from`,`team_count`,`follow_up`,`player_drafted`,`year_of_draft`,`pool_id`) SELECT draft_pick_no,ronde,equipe,team_id,from_who,team_id_from,team_count,follow_up,player_drafted,year_of_draft,pool_id FROM draft_round WHERE pool_id=?";
	QueryC = "INSERT INTO trade_made_archives (team_1,team_2,t1j1,t1j2,t1j3,t1j4,t1j5,t1j6,t1j7,t1p1,t1p2,t1p3,t1_cash,t2j1,t2j2,t2j3,t2j4,t2j5,t2j6,t2j7,t2p1,t2p2,t2p3,t2_cash,date_heure,periode_echange,years,pool_id) SELECT team_1,team_2,t1j1,t1j2,t1j3,t1j4,t1j5,t1j6,t1j7,t1p1,t1p2,t1p3,t1_cash,t2j1,t2j2,t2j3,t2j4,t2j5,t2j6,t2j7,t2p1,t2p2,t2p3,t2_cash,date_heure,periode_echange,years,pool_id FROM trade_made WHERE pool_id=?";
	try {
	    mPreparedStatementA = conn.prepareStatement(QueryA);
	    mPreparedStatementA.setInt(1, 1);
	    mPreparedStatementA.executeUpdate();
	    mPreparedStatementB = conn.prepareStatement(QueryB);
	    mPreparedStatementB.setInt(1, 1);
	    mPreparedStatementB.executeUpdate();
	    mPreparedStatementC = conn.prepareStatement(QueryC);
	    mPreparedStatementC.setInt(1, 1);
	    mPreparedStatementC.executeUpdate();
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
	PreparedStatement mPreparedStatementD, mPreparedStatementE, mPreparedStatementF;

	// ouverture de la connexion a la bdd
	conn = mDbHelper.open();

	QueryD = "SELECT * FROM classement_archives WHERE pool_id=? AND year_of_the_standing=?";
	QueryE = "SELECT * FROM draft_round_archives WHERE pool_id=? AND year_of_draft=?";
	QueryF = "SELECT * FROM trade_made_archives WHERE pool_id=? AND years=?";

	try {
	    mPreparedStatementD = conn.prepareStatement(QueryD);
	    mPreparedStatementD.setInt(1, 1);
	    mPreparedStatementD.setInt(2, 2015);
	    rsD = mPreparedStatementD.executeQuery();
	    mPreparedStatementE = conn.prepareStatement(QueryE);
	    mPreparedStatementE.setInt(1, 1);
	    mPreparedStatementE.setInt(2, 2015);
	    rsE = mPreparedStatementE.executeQuery();
	    mPreparedStatementF = conn.prepareStatement(QueryF);
	    mPreparedStatementF.setInt(1, 1);
	    mPreparedStatementF.setInt(2, 2015);
	    rsF = mPreparedStatementF.executeQuery();

	    if (rsD.next()) {
		testD = true;
	    }
	    if (rsE.next()) {
		testE = true;
	    }
	    if (rsF.next()) {
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
