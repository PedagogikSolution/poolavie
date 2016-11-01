package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Players;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.utils.EMF;

public class PlayersDaoImpl implements PlayersDao {

    private static final String CREATE_PLAYERS = "CREATE TABLE players_? LIKE players_template";
    private static final String INSERT_PLAYERS = "INSERT INTO players_? SELECT * FROM players_template";
    private static final String CREATE_PLAYERS_ARCHIVES = "CREATE TABLE players_archive_? LIKE players_template";
    private static final String GET_PLAYERS_BY_POOL_ID_AND_POSITION = "SELECT * FROM players_? WHERE team_id=? AND position=? AND club_ecole=? ORDER BY pts DESC";
    private static final String GET_PLAYERS_FOR_DRAFT = "SELECT * FROM players_?";
    private static final String GET_PLAYERS_BY_POOL_ID_FOR_ROOKIE = "SELECT * FROM players_? WHERE team_id=? AND club_ecole=? ORDER BY pts DESC";
    private static final String UPDATE_PLAYERS_AFTER_DRAFT_PICK = "UPDATE players_? SET team_id=?,contrat=?,acquire_years=?,salaire_contrat=?,club_ecole=?,years_1=?,years_2='JA',years_3='A',years_4='A',years_5='A' WHERE _id=?";
    private static final String GET_PLAYERS_FOR_SIGNATURE_AFTER_DRAFT = "SELECT * FROM players_? WHERE contrat=1 AND club_ecole=0 AND team_id=? AND years_2='JA'";
    private static final String UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT = "UPDATE players_? SET years_1=?,years_2=?,years_3=?,years_4=?,years_5=? WHERE _id=?";
    private static final String GET_FOWARD_PJ_TOP_X = "SELECT SUM(pj) AS sommePts FROM (SELECT pj FROM players_? WHERE position='attaquant' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_DEFENSE_PJ_TOP_X = "SELECT SUM(pj)AS sommePts FROM (SELECT pj FROM players_? WHERE position='defenseur' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_GOALER_PJ_TOP_X = "SELECT SUM(pj) AS sommePts FROM (SELECT pj FROM players_? WHERE position='gardien' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_FOWARD_BUT_TOP_X = "SELECT SUM(but_victoire) AS sommePts FROM (SELECT but_victoire FROM players_? WHERE position='attaquant' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_DEFENSE_BUT_TOP_X = "SELECT SUM(but_victoire)AS sommePts FROM (SELECT but_victoire FROM players_? WHERE position='defenseur' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_GOALER_BUT_TOP_X = "SELECT SUM(but_victoire) AS sommePts FROM (SELECT but_victoire FROM players_? WHERE position='gardien' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_FOWARD_PASSE_TOP_X = "SELECT SUM(aide_overtime) AS sommePts FROM (SELECT aide_overtime FROM players_? WHERE position='attaquant' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_DEFENSE_PASSE_TOP_X = "SELECT SUM(aide_overtime)AS sommePts FROM (SELECT aide_overtime FROM players_? WHERE position='defenseur' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_GOALER_PASSE_TOP_X = "SELECT SUM(aide_overtime) AS sommePts FROM (SELECT aide_overtime FROM players_? WHERE position='gardien' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_FOWARD_PTS_TOP_X = "SELECT SUM(pts) AS sommePts FROM (SELECT pts FROM players_? WHERE position='attaquant' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_DEFENSE_PTS_TOP_X = "SELECT SUM(pts)AS sommePts FROM (SELECT pts FROM players_? WHERE position='defenseur' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    private static final String GET_GOALER_PTS_TOP_X = "SELECT SUM(pts) AS sommePts FROM (SELECT pts FROM players_? WHERE position='gardien' AND team_id=? ORDER BY pts DESC LIMIT ?) AS subquery";
    
    private DAOFactory daoFactory;

    PlayersDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createPlayersTable(int poolID) throws DAOException {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_PLAYERS, false, poolID);
	    preparedStatement.execute();
	    preparedStatement = initialisationRequetePreparee(connexion, INSERT_PLAYERS, false, poolID);
	    preparedStatement.execute();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void createPlayersArchiveTable(int poolID) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_PLAYERS_ARCHIVES, false, poolID);
	    preparedStatement.execute();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void cronJobGetPlayersbyPoolIdAndPosition(int poolId, int numberOfTeam, String positionString, int isRookie, String fromTag) {

	for (int i = 1; i < (numberOfTeam + 1); i++) {
	    Queue queue = QueueFactory.getDefaultQueue();
	    queue.add(TaskOptions.Builder.withUrl("/TaskQueueCreationPool").param("isRookie", String.valueOf(isRookie)).param("positionString", positionString).param("counter", String.valueOf(i)).param("poolID", String.valueOf(poolId)).param("fromTag", fromTag)

	    );

	}

    }

    @Override
    public void cronJobPlayersAvailableForDraft(int poolId) {

	Queue queue = QueueFactory.getDefaultQueue();
	queue.add(TaskOptions.Builder.withUrl("/TaskQueueCreationPool").param("poolID", String.valueOf(poolId)).param("fromTag", "2"));

    }

    @Override
    public void persistPlayerPick(int playerId, int salaireId, int poolId, int teamId, int clubEcoleId, int acquire_years) {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	int contrat=1;
	

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_AFTER_DRAFT_PICK, false, poolId, teamId, contrat, acquire_years, salaireId, clubEcoleId,salaireId, playerId);
	    preparedStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void getPlayersForDatastoreFromPoolIdAndTeamNumber(String poolID, String counter, String positionString, int isRookie) {
	String datastoreId = poolID + "_" + counter;
	int poolId = Integer.parseInt(poolID);

	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	List<Integer> players_id = new ArrayList<Integer>();
	List<Integer> team_id = new ArrayList<Integer>();
	List<String> nom = new ArrayList<String>();
	List<String> teamOfPlayer = new ArrayList<String>();
	List<Integer> pj = new ArrayList<Integer>();
	List<Integer> but_victoire = new ArrayList<Integer>();
	List<Integer> aide_overtime = new ArrayList<Integer>();
	List<Integer> blanchissage = new ArrayList<Integer>();
	List<Integer> pts = new ArrayList<Integer>();
	List<Integer> projection = new ArrayList<Integer>();
	List<String> position = new ArrayList<String>();
	List<Date> birthday = new ArrayList<Date>();
	List<Integer> can_be_rookie = new ArrayList<Integer>();
	List<Integer> take_proj = new ArrayList<Integer>();
	List<Integer> salaire_draft = new ArrayList<Integer>();
	List<Integer> contrat = new ArrayList<Integer>();
	List<Integer> acquire_years = new ArrayList<Integer>();
	List<Integer> salaire_contrat = new ArrayList<Integer>();
	List<Integer> contrat_cours = new ArrayList<Integer>();
	List<Integer> contrat_max_years = new ArrayList<Integer>();
	List<Integer> type_contrat = new ArrayList<Integer>();
	List<Integer> club_ecole = new ArrayList<Integer>();
	List<Date> date_calcul = new ArrayList<Date>();
	List<String> years_1 = new ArrayList<String>();
	List<String> years_2 = new ArrayList<String>();
	List<String> years_3 = new ArrayList<String>();
	List<String> years_4 = new ArrayList<String>();
	List<String> years_5 = new ArrayList<String>();
	List<Integer> team_was_update = new ArrayList<Integer>();
	List<Integer> age = new ArrayList<Integer>();
	List<Integer> hier = new ArrayList<Integer>();
	List<Integer> semaine = new ArrayList<Integer>();
	List<Integer> mois = new ArrayList<Integer>();

	try {

	    connexion = daoFactory.getConnection();

	    if (isRookie == 0) {
		preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_POOL_ID_AND_POSITION, false, poolId, counter, positionString, isRookie);
	    } else {
		preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_POOL_ID_FOR_ROOKIE, false, poolId, counter, isRookie);
	    }

	    rs = preparedStatement.executeQuery();

	    while (rs.next()) {

		int m_players_id = rs.getInt("_id");
		players_id.add(m_players_id);

		int m_team_id = rs.getInt("team_id");
		team_id.add(m_team_id);

		String m_nom = rs.getString("nom");
		nom.add(m_nom);

		String m_teamOfPlayer = rs.getString("team");
		teamOfPlayer.add(m_teamOfPlayer);

		int m_pj = rs.getInt("pj");
		pj.add(m_pj);

		int m_but_victoire = rs.getInt("but_victoire");
		but_victoire.add(m_but_victoire);

		int m_aide_overtime = rs.getInt("aide_overtime");
		aide_overtime.add(m_aide_overtime);

		int m_blanchissage = rs.getInt("blanchissage");
		blanchissage.add(m_blanchissage);

		int m_pts = rs.getInt("pts");
		pts.add(m_pts);

		int m_projection = rs.getInt("projection");
		projection.add(m_projection);

		String m_position = rs.getString("position");
		position.add(m_position);

		Date m_birthday = rs.getDate("birthday");
		birthday.add(m_birthday);

		int m_can_be_rookie = rs.getInt("can_be_rookie");
		can_be_rookie.add(m_can_be_rookie);

		int m_take_proj = rs.getInt("take_proj");
		take_proj.add(m_take_proj);

		int m_salaire_draft = rs.getInt("salaire_draft");
		salaire_draft.add(m_salaire_draft);

		int m_contrat = rs.getInt("contrat");
		contrat.add(m_contrat);

		int m_acquire_years = rs.getInt("acquire_years");
		acquire_years.add(m_acquire_years);

		int m_salaire_contrat = rs.getInt("salaire_contrat");
		salaire_contrat.add(m_salaire_contrat);

		int m_contrat_cours = rs.getInt("contrat_cours");
		contrat_cours.add(m_contrat_cours);

		int m_contrat_max_years = rs.getInt("contrat_max_years");
		contrat_max_years.add(m_contrat_max_years);

		int m_type_contrat = rs.getInt("type_contrat");
		type_contrat.add(m_type_contrat);

		int m_club_ecole = rs.getInt("club_ecole");
		club_ecole.add(m_club_ecole);

		Date m_date_calcul = rs.getDate("date_calcul");
		date_calcul.add(m_date_calcul);

		String m_years_1 = rs.getString("years_1");
		years_1.add(m_years_1);

		String m_years_2 = rs.getString("years_2");
		years_2.add(m_years_2);

		String m_years_3 = rs.getString("years_3");
		years_3.add(m_years_3);

		String m_years_4 = rs.getString("years_4");
		years_4.add(m_years_4);

		String m_years_5 = rs.getString("years_5");
		years_5.add(m_years_5);

		int m_team_was_update = rs.getInt("team_was_update");
		team_was_update.add(m_team_was_update);

		int m_age = rs.getInt("age");
		age.add(m_age);

		int m_hier = rs.getInt("hier");
		hier.add(m_hier);

		int m_semaine = rs.getInt("semaine");
		semaine.add(m_semaine);

		int m_mois = rs.getInt("mois");
		mois.add(m_mois);

	    }

	} catch (SQLException e) {

	    throw new DAOException(e);

	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);

	}
	
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	
	switch (isRookie) {
	case 0:
	    switch (positionString) {

	    case "attaquant":
		Attaquant mBeanA = new Attaquant();
		

		mBeanA.setPlayers_id(players_id);
		mBeanA.setAge(age);
		mBeanA.setAide_overtime(aide_overtime);
		mBeanA.setBlanchissage(blanchissage);
		mBeanA.setBut_victoire(but_victoire);
		mBeanA.setCan_be_rookie(can_be_rookie);
		mBeanA.setClub_ecole(club_ecole);
		mBeanA.setContrat(contrat);
		mBeanA.setContrat_cours(contrat_cours);
		mBeanA.setContrat_max_years(contrat_max_years);
		mBeanA.setAcquire_years(acquire_years);
		mBeanA.setNom(nom);
		mBeanA.setPj(pj);
		mBeanA.setPoolTeamId(datastoreId);
		mBeanA.setPosition(position);
		mBeanA.setPts(pts);
		mBeanA.setSalaire_contrat(salaire_contrat);
		mBeanA.setSalaire_draft(salaire_draft);
		mBeanA.setSemaine(semaine);
		mBeanA.setTake_proj(take_proj);
		mBeanA.setTeam_id(team_id);
		mBeanA.setTeam_was_update(team_was_update);
		mBeanA.setTeamOfPlayer(teamOfPlayer);
		mBeanA.setType_contrat(type_contrat);
		mBeanA.setYears_1(years_1);
		mBeanA.setYears_2(years_2);
		mBeanA.setYears_3(years_3);
		mBeanA.setYears_4(years_4);
		mBeanA.setYears_5(years_5);

		// on crée le beans avec le processus JPA qui va créer le datastore en même temps

		try {
		    em = emf.createEntityManager();

		    // on persiste dans le datastore via notre EntityManager
		    em.persist(mBeanA);

		} finally {

		    // on ferme le manager pour libérer la mémoire
		    if (em != null) {
			em.close();

		    }
		}


		break;

	    case "defenseur":
		Defenseur mBeanD = new Defenseur();
		

		mBeanD.setPlayers_id(players_id);
		mBeanD.setAge(age);
		mBeanD.setAide_overtime(aide_overtime);
		mBeanD.setBlanchissage(blanchissage);
		mBeanD.setBut_victoire(but_victoire);
		mBeanD.setCan_be_rookie(can_be_rookie);
		mBeanD.setClub_ecole(club_ecole);
		mBeanD.setContrat(contrat);
		mBeanD.setContrat_cours(contrat_cours);
		;
		mBeanD.setAcquire_years(acquire_years);
		mBeanD.setHier(hier);
		mBeanD.setMois(mois);
		mBeanD.setNom(nom);
		mBeanD.setPj(pj);
		mBeanD.setPoolTeamId(datastoreId);
		mBeanD.setPosition(position);
		mBeanD.setProjection(projection);
		mBeanD.setPts(pts);
		mBeanD.setSalaire_contrat(salaire_contrat);
		mBeanD.setSalaire_draft(salaire_draft);
		mBeanD.setSemaine(semaine);
		mBeanD.setTake_proj(take_proj);
		mBeanD.setTeam_id(team_id);
		mBeanD.setTeam_was_update(team_was_update);
		mBeanD.setTeamOfPlayer(teamOfPlayer);
		mBeanD.setType_contrat(type_contrat);
		mBeanD.setYears_1(years_1);
		mBeanD.setYears_2(years_2);
		mBeanD.setYears_3(years_3);
		mBeanD.setYears_4(years_4);
		mBeanD.setYears_5(years_5);

		// on crée le beans avec le processus JPA qui va créer le datastore en même temps

		try {
		    em = emf.createEntityManager();

		    // on persiste dans le datastore via notre EntityManager
		    em.persist(mBeanD);

		} finally {

		    // on ferme le manager pour libérer la mémoire
		    if (em != null) {
			em.close();

		    }
		}
	

		break;
	    case "gardien":
		Gardien mBeanG = new Gardien();
		

		mBeanG.setPlayers_id(players_id);
		mBeanG.setAge(age);
		mBeanG.setAide_overtime(aide_overtime);
		mBeanG.setBlanchissage(blanchissage);
		mBeanG.setBut_victoire(but_victoire);
		mBeanG.setCan_be_rookie(can_be_rookie);
		mBeanG.setClub_ecole(club_ecole);
		mBeanG.setContrat(contrat);
		mBeanG.setContrat_cours(contrat_cours);
		mBeanG.setAcquire_years(acquire_years);
		mBeanG.setHier(hier);
		mBeanG.setMois(mois);
		mBeanG.setNom(nom);
		mBeanG.setPj(pj);
		mBeanG.setPoolTeamId(datastoreId);
		mBeanG.setPosition(position);
		mBeanG.setProjection(projection);
		mBeanG.setPts(pts);
		mBeanG.setAcquire_years(acquire_years);
		mBeanG.setSalaire_contrat(salaire_contrat);
		mBeanG.setSalaire_draft(salaire_draft);
		mBeanG.setSemaine(semaine);
		mBeanG.setTake_proj(take_proj);
		mBeanG.setTeam_id(team_id);
		mBeanG.setTeam_was_update(team_was_update);
		mBeanG.setTeamOfPlayer(teamOfPlayer);
		mBeanG.setType_contrat(type_contrat);
		mBeanG.setYears_1(years_1);
		mBeanG.setYears_2(years_2);
		mBeanG.setYears_3(years_3);
		mBeanG.setYears_4(years_4);
		mBeanG.setYears_5(years_5);

		// on crée le beans avec le processus JPA qui va créer le datastore en même temps

		try {
		    em = emf.createEntityManager();

		    // on persiste dans le datastore via notre EntityManager
		    em.persist(mBeanG);

		} finally {

		    // on ferme le manager pour libérer la mémoire
		    if (em != null) {
			em.close();

		    }
		}
	

		break;
	    }
	    break;
	case 1:
	    Recrue mBeanR = new Recrue();
	   

	    mBeanR.setPlayers_id(players_id);
	    mBeanR.setAge(age);
	    mBeanR.setAide_overtime(aide_overtime);
	    mBeanR.setBlanchissage(blanchissage);
	    mBeanR.setBut_victoire(but_victoire);
	    mBeanR.setCan_be_rookie(can_be_rookie);
	    mBeanR.setClub_ecole(club_ecole);
	    mBeanR.setContrat(contrat);
	    mBeanR.setContrat_cours(contrat_cours);
	    mBeanR.setAcquire_years(acquire_years);
	    mBeanR.setHier(hier);
	    mBeanR.setMois(mois);
	    mBeanR.setNom(nom);
	    mBeanR.setPj(pj);
	    mBeanR.setAcquire_years(acquire_years);
	    mBeanR.setPoolTeamId(datastoreId);
	    mBeanR.setPosition(position);
	    mBeanR.setProjection(projection);
	    mBeanR.setPts(pts);
	    mBeanR.setSalaire_contrat(salaire_contrat);
	    mBeanR.setSalaire_draft(salaire_draft);
	    mBeanR.setSemaine(semaine);
	    mBeanR.setTake_proj(take_proj);
	    mBeanR.setTeam_id(team_id);
	    mBeanR.setTeam_was_update(team_was_update);
	    mBeanR.setTeamOfPlayer(teamOfPlayer);
	    mBeanR.setType_contrat(type_contrat);
	    mBeanR.setYears_1(years_1);
	    mBeanR.setYears_2(years_2);
	    mBeanR.setYears_3(years_3);
	    mBeanR.setYears_4(years_4);
	    mBeanR.setYears_5(years_5);

	    // on crée le beans avec le processus JPA qui va créer le datastore en même temps

	    try {
		em = emf.createEntityManager();

		// on persiste dans le datastore via notre EntityManager
		em.persist(mBeanR);

	    } finally {

		// on ferme le manager pour libérer la mémoire
		if (em != null) {
		    em.close();

		}
	    }


	    break;
	}

    }

    @Override
    public void getPlayersForDatastoreFromPoolId(String poolID) {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	

	int _id;
	int players_id = 0;
	int team_id = 0;
	String nom = null;
	String teamOfPlayer = null;
	int pj = 0;
	int but_victoire = 0;
	int aide_overtime = 0;
	int blanchissage = 0;
	int pts = 0;
	int projection = 0;
	String position = null;
	Date birthday = null;
	int can_be_rookie = 0;
	int take_proj = 0;
	int salaire_draft = 0;
	int contrat = 0;
	int acquire_years = 0;
	int salaire_contrat = 0;
	int contrat_cours = 0;
	int contrat_max_years = 0;
	int type_contrat = 0;
	int club_ecole = 0;
	Date date_calcul = null;
	String years_1 = null;
	String years_2 = null;
	String years_3 = null;
	String years_4 = null;
	String years_5 = null;
	int team_was_update = 0;
	int age = 0;
	int hier = 0;
	int semaine = 0;
	int mois = 0;

	try {

	    connexion = daoFactory.getConnection();

	    preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_FOR_DRAFT, false, Integer.parseInt(poolID));
	    rs = preparedStatement.executeQuery();

	    while (rs.next()) {
		_id = rs.getInt("_id");
		players_id = rs.getInt("_id");
		team_id = rs.getInt("team_id");
		nom = rs.getString("nom");
		teamOfPlayer = rs.getString("team");
		pj = rs.getInt("pj");
		but_victoire = rs.getInt("but_victoire");
		aide_overtime = rs.getInt("aide_overtime");
		blanchissage = rs.getInt("blanchissage");
		pts = rs.getInt("pts");
		projection = rs.getInt("projection");
		position = rs.getString("position");
		birthday = rs.getDate("birthday");
		can_be_rookie = rs.getInt("can_be_rookie");
		take_proj = rs.getInt("take_proj");
		salaire_draft = rs.getInt("salaire_draft");
		contrat = rs.getInt("contrat");
		acquire_years = rs.getInt("acquire_years");
		salaire_contrat = rs.getInt("salaire_contrat");
		contrat_cours = rs.getInt("contrat_cours");
		contrat_max_years = rs.getInt("contrat_max_years");
		type_contrat = rs.getInt("type_contrat");
		club_ecole = rs.getInt("club_ecole");
		date_calcul = rs.getDate("date_calcul");
		years_1 = rs.getString("years_1");
		years_2 = rs.getString("years_2");
		years_3 = rs.getString("years_3");
		years_4 = rs.getString("years_4");
		years_5 = rs.getString("years_5");
		team_was_update = rs.getInt("team_was_update");
		age = rs.getInt("age");
		hier = rs.getInt("hier");
		semaine = rs.getInt("semaine");
		mois = rs.getInt("mois");

		

		Players mBean = new Players();

		mBean.set_id(_id);
		mBean.setPlayers_id(players_id);
		mBean.setAge(age);
		mBean.setAide_overtime(aide_overtime);
		mBean.setBirthday(birthday);
		mBean.setBlanchissage(blanchissage);
		mBean.setBut_victoire(but_victoire);
		mBean.setCan_be_rookie(can_be_rookie);
		mBean.setClub_ecole(club_ecole);
		mBean.setContrat(contrat);
		mBean.setContrat_cours(contrat_cours);
		mBean.setContrat_max_years(contrat_max_years);
		mBean.setDate_calcul(date_calcul);
		mBean.setAcquire_years(acquire_years);
		mBean.setHier(hier);
		mBean.setMois(mois);
		mBean.setNom(nom);
		mBean.setPj(pj);
		mBean.setPosition(position);
		mBean.setProjection(projection);
		mBean.setPts(pts);
		mBean.setSalaire_contrat(salaire_contrat);
		mBean.setSalaire_draft(salaire_draft);
		mBean.setSemaine(semaine);
		mBean.setTake_proj(take_proj);
		mBean.setTeam_id(team_id);
		mBean.setTeam_was_update(team_was_update);
		mBean.setTeamOfPlayer(teamOfPlayer);
		mBean.setType_contrat(type_contrat);
		mBean.setYears_1(years_1);
		mBean.setYears_2(years_2);
		mBean.setYears_3(years_3);
		mBean.setYears_4(years_4);
		mBean.setYears_5(years_5);

		
				

		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/TaskQueueCreationPool")
			.param("players_id",String.valueOf(players_id))
			.param("nom",nom)
			.param("teamOfPlayer",teamOfPlayer)
			.param("pj",String.valueOf(pj))
			.param("but_victoire",String.valueOf(but_victoire))
			.param("aide_overtime",String.valueOf(aide_overtime))
			.param("blanchissage",String.valueOf(blanchissage))
			.param("pts",String.valueOf(pts))
			.param("projection",String.valueOf(projection))
			.param("position",position)
			.param("can_be_rookie",String.valueOf(can_be_rookie))
			.param("salaire_draft",String.valueOf(salaire_draft))
			.param("poolID",String.valueOf(poolID))
			.param("fromTag", "8")
			.param("contrat", String.valueOf(contrat))
			.param("club_ecole", String.valueOf(club_ecole))
			
			);
	    }

	} catch (SQLException e) {

	    throw new DAOException(e);

	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);

	}
	
    }

    @Override
    public void getPlayersThatCanBeSign(int teamId, int poolId,HttpServletRequest req) throws DAOException {
	
	
	NonSessionPlayers mBeanPlayers = new NonSessionPlayers();
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	List<Integer> players_id = new ArrayList<Integer>();
	List<Integer> team_id = new ArrayList<Integer>();
	List<String> nom = new ArrayList<String>();
	List<String> teamOfPlayer = new ArrayList<String>();
	List<Integer> pj = new ArrayList<Integer>();
	List<Integer> but_victoire = new ArrayList<Integer>();
	List<Integer> aide_overtime = new ArrayList<Integer>();
	List<Integer> blanchissage = new ArrayList<Integer>();
	List<Integer> pts = new ArrayList<Integer>();
	List<Integer> projection = new ArrayList<Integer>();
	List<String> position = new ArrayList<String>();
	List<Date> birthday = new ArrayList<Date>();
	List<Integer> can_be_rookie = new ArrayList<Integer>();
	List<Integer> take_proj = new ArrayList<Integer>();
	List<Integer> salaire_draft = new ArrayList<Integer>();
	List<Integer> contrat = new ArrayList<Integer>();
	List<Integer> acquire_years = new ArrayList<Integer>();
	List<Integer> salaire_contrat = new ArrayList<Integer>();
	List<Integer> contrat_cours = new ArrayList<Integer>();
	List<Integer> contrat_max_years = new ArrayList<Integer>();
	List<Integer> type_contrat = new ArrayList<Integer>();
	List<Integer> club_ecole = new ArrayList<Integer>();
	List<Date> date_calcul = new ArrayList<Date>();
	List<String> years_1 = new ArrayList<String>();
	List<String> years_2 = new ArrayList<String>();
	List<String> years_3 = new ArrayList<String>();
	List<String> years_4 = new ArrayList<String>();
	List<String> years_5 = new ArrayList<String>();
	List<Integer> team_was_update = new ArrayList<Integer>();
	List<Integer> age = new ArrayList<Integer>();
	List<Integer> hier = new ArrayList<Integer>();
	List<Integer> semaine = new ArrayList<Integer>();
	List<Integer> mois = new ArrayList<Integer>();
	

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_FOR_SIGNATURE_AFTER_DRAFT, false, poolId, teamId);
	    rs = preparedStatement.executeQuery();
	    
	    while(rs.next()){
		int m_players_id = rs.getInt("_id");
		players_id.add(m_players_id);

		int m_team_id = rs.getInt("team_id");
		team_id.add(m_team_id);

		String m_nom = rs.getString("nom");
		nom.add(m_nom);

		String m_teamOfPlayer = rs.getString("team");
		teamOfPlayer.add(m_teamOfPlayer);

		int m_pj = rs.getInt("pj");
		pj.add(m_pj);

		int m_but_victoire = rs.getInt("but_victoire");
		but_victoire.add(m_but_victoire);

		int m_aide_overtime = rs.getInt("aide_overtime");
		aide_overtime.add(m_aide_overtime);

		int m_blanchissage = rs.getInt("blanchissage");
		blanchissage.add(m_blanchissage);

		int m_pts = rs.getInt("pts");
		pts.add(m_pts);

		int m_projection = rs.getInt("projection");
		projection.add(m_projection);

		String m_position = rs.getString("position");
		position.add(m_position);

		Date m_birthday = rs.getDate("birthday");
		birthday.add(m_birthday);

		int m_can_be_rookie = rs.getInt("can_be_rookie");
		can_be_rookie.add(m_can_be_rookie);

		int m_take_proj = rs.getInt("take_proj");
		take_proj.add(m_take_proj);

		int m_salaire_draft = rs.getInt("salaire_draft");
		salaire_draft.add(m_salaire_draft);

		int m_contrat = rs.getInt("contrat");
		contrat.add(m_contrat);

		int m_acquire_years = rs.getInt("acquire_years");
		acquire_years.add(m_acquire_years);

		int m_salaire_contrat = rs.getInt("salaire_contrat");
		salaire_contrat.add(m_salaire_contrat);

		int m_contrat_cours = rs.getInt("contrat_cours");
		contrat_cours.add(m_contrat_cours);

		int m_contrat_max_years = rs.getInt("contrat_max_years");
		contrat_max_years.add(m_contrat_max_years);

		int m_type_contrat = rs.getInt("type_contrat");
		type_contrat.add(m_type_contrat);

		int m_club_ecole = rs.getInt("club_ecole");
		club_ecole.add(m_club_ecole);

		Date m_date_calcul = rs.getDate("date_calcul");
		date_calcul.add(m_date_calcul);

		String m_years_1 = rs.getString("years_1");
		years_1.add(m_years_1);

		String m_years_2 = rs.getString("years_2");
		years_2.add(m_years_2);

		String m_years_3 = rs.getString("years_3");
		years_3.add(m_years_3);

		String m_years_4 = rs.getString("years_4");
		years_4.add(m_years_4);

		String m_years_5 = rs.getString("years_5");
		years_5.add(m_years_5);

		int m_team_was_update = rs.getInt("team_was_update");
		team_was_update.add(m_team_was_update);

		int m_age = rs.getInt("age");
		age.add(m_age);

		int m_hier = rs.getInt("hier");
		hier.add(m_hier);

		int m_semaine = rs.getInt("semaine");
		semaine.add(m_semaine);

		int m_mois = rs.getInt("mois");
		mois.add(m_mois);
		
		
		
	    }
	    	mBeanPlayers.setPlayers_id(players_id);
		mBeanPlayers.setAge(age);
		mBeanPlayers.setAide_overtime(aide_overtime);
		mBeanPlayers.setBlanchissage(blanchissage);
		mBeanPlayers.setBut_victoire(but_victoire);
		mBeanPlayers.setCan_be_rookie(can_be_rookie);
		mBeanPlayers.setClub_ecole(club_ecole);
		mBeanPlayers.setContrat(contrat);
		mBeanPlayers.setContrat_cours(contrat_cours);
		mBeanPlayers.setAcquire_years(acquire_years);
		mBeanPlayers.setHier(hier);
		mBeanPlayers.setMois(mois);
		mBeanPlayers.setNom(nom);
		mBeanPlayers.setPj(pj);
		mBeanPlayers.setPosition(position);
		mBeanPlayers.setProjection(projection);
		mBeanPlayers.setPts(pts);
		mBeanPlayers.setAcquire_years(acquire_years);
		mBeanPlayers.setSalaire_contrat(salaire_contrat);
		mBeanPlayers.setSalaire_draft(salaire_draft);
		mBeanPlayers.setSemaine(semaine);
		mBeanPlayers.setTake_proj(take_proj);
		mBeanPlayers.setTeam_id(team_id);
		mBeanPlayers.setTeam_was_update(team_was_update);
		mBeanPlayers.setTeamOfPlayer(teamOfPlayer);
		mBeanPlayers.setType_contrat(type_contrat);
		mBeanPlayers.setYears_1(years_1);
		mBeanPlayers.setYears_2(years_2);
		mBeanPlayers.setYears_3(years_3);
		mBeanPlayers.setYears_4(years_4);
		mBeanPlayers.setYears_5(years_5);
		
		req.getSession().setAttribute("NonSessionPlayers", mBeanPlayers);
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
    }

    
    @Override
    public void signPlayerAfterDraft(int teamId, int poolId, String draft_player_id, String salaire, int numberOfYear) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	try {
	    connexion = daoFactory.getConnection();
	    switch(numberOfYear){
	    case 1: preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT, false, poolId,salaire,"JA","X","X","X",draft_player_id);
	    break;
	    case 2: preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT, false, poolId,salaire,salaire,"X","X","X",draft_player_id);
	    break;
	    case 3: preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT, false, poolId,salaire,salaire,salaire,"X","X",draft_player_id);
	    break;
	    case 4: preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT, false, poolId,salaire,salaire,salaire,salaire,"X",draft_player_id);
	    break;
	    case 5: preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_SIGNATURE_AFTER_DRAFT, false, poolId,salaire,salaire,salaire,salaire,salaire,draft_player_id);
	    break;
	    
	    }
	    preparedStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    
    @Override
    public void persistPlayerPickRookie(int playersId, int salaireId, int poolId, int teamId, int clubEcoleId, int acquireYearsId) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	int contrat=1;
	

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_AFTER_DRAFT_PICK, false, poolId, teamId, contrat, acquireYearsId, salaireId, clubEcoleId,salaireId, playersId);
	    preparedStatement.executeUpdate();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

   
    @Override
    public int getPj(int teamId, int poolId) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	int pts=0;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_FOWARD_PJ_TOP_X, false, poolId, teamId, 8);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_DEFENSE_PJ_TOP_X, false, poolId, teamId, 5);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_GOALER_PJ_TOP_X, false, poolId, teamId, 2);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	
	
	
	return pts;
    }


    @Override
    public int getBut(int teamId, int poolId) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	int pts=0;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_FOWARD_BUT_TOP_X, false, poolId, teamId, 8);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_DEFENSE_BUT_TOP_X, false, poolId, teamId, 5);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_GOALER_BUT_TOP_X, false, poolId, teamId, 2);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	
	
	
	return pts;
    }

    @Override
    public int getPasse(int teamId, int poolId) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	int pts=0;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_FOWARD_PASSE_TOP_X, false, poolId, teamId, 8);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_DEFENSE_PASSE_TOP_X, false, poolId, teamId, 5);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_GOALER_PASSE_TOP_X, false, poolId, teamId, 2);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	
	
	
	return pts;
    }

    @Override
    public int getPts(int teamId, int poolId) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	int pts=0;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_FOWARD_PTS_TOP_X, false, poolId, teamId, 8);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_DEFENSE_PTS_TOP_X, false, poolId, teamId, 5);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_GOALER_PTS_TOP_X, false, poolId, teamId, 2);
	    ResultSet rs = preparedStatement.executeQuery();
	    while(rs.next()){
		String pts_temp = rs.getString("sommePts");
		int ptsTemp = Integer.parseInt(pts_temp);
		pts = pts+ptsTemp;
		
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	
	
	
	return pts;
	
    }

    
   
    
   
    
   

    
    
}
