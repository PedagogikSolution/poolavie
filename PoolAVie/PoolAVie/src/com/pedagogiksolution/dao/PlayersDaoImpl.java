package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Players;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.utils.EMF;

public class PlayersDaoImpl implements PlayersDao {

    private static final String CREATE_PLAYERS = "CREATE TABLE players_? AS SELECT * FROM players_template;";
    private static final String CREATE_PLAYERS_ARCHIVES = "CREATE TABLE players_archive_? LIKE players_template";
    private static final String GET_PLAYERS_BY_POOL_ID_AND_POSITION = "SELECT * FROM players_? WHERE team_id=? AND position=? AND club_ecole=? ORDER BY pts DESC";
    private static final String GET_PLAYERS_FOR_DRAFT = "SELECT * FROM players_?";
    private static final String GET_PLAYERS_BY_POOL_ID_FOR_ROOKIE = "SELECT * FROM players_? WHERE team_id=? AND club_ecole=? ORDER BY pts DESC";
    private static final String UPDATE_PLAYERS_AFTER_DRAFT_PICK = "UPDATE players_? SET team_id=?,contrat=?,acquire_years=?,salaire_contrat=?,club_ecole=?,years_1='A',years_2='A',years_3='A',years_4='A',years_5='A' WHERE _id=?"; 
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
    public void cronJobGetPlayersbyPoolIdAndPosition(int poolId, int numberOfTeam, String positionString, int isRookie) {

	for (int i = 1; i < (numberOfTeam + 1); i++) {
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
	    String datastoreId;

	    try {

		connexion = daoFactory.getConnection();

		datastoreId = String.valueOf(poolId) + "_" + i;
		
		if(isRookie==0){
		preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_POOL_ID_AND_POSITION, false, poolId, i, positionString, isRookie);
		} else {
		preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_BY_POOL_ID_FOR_ROOKIE, false, poolId, i, isRookie);
		}
		
		
		rs = preparedStatement.executeQuery();

		while (rs.next()) {

		    int m_players_id = rs.getInt("players_id");
		    players_id.add(m_players_id);

		    int m_team_id = rs.getInt("team_id");
		    team_id.add(m_team_id);

		    String m_nom = rs.getString("nom");
		    nom.add(m_nom);

		    String m_teamOfPlayer = rs.getString("teamOfPlayer");
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
	    MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	    EntityManagerFactory emf = EMF.get();
	    EntityManager em = null;
	    String nomBean;
	    switch (isRookie) {
	    case 0:
		switch (positionString) {

		case "attaquant":
		    Attaquant mBeanA = new Attaquant();
		    nomBean = "Attaquant";

		    mBeanA.setAge(age);
		    mBeanA.setAide_overtime(aide_overtime);
		    mBeanA.setBirthday(birthday);
		    mBeanA.setBlanchissage(blanchissage);
		    mBeanA.setBut_victoire(but_victoire);
		    mBeanA.setCan_be_rookie(can_be_rookie);
		    mBeanA.setClub_ecole(club_ecole);
		    mBeanA.setContrat(contrat);
		    mBeanA.setContrat_cours(contrat_cours);
		    mBeanA.setContrat_max_years(contrat_max_years);
		    mBeanA.setDate_calcul(date_calcul);
		    mBeanA.setAcquire_years(acquire_years);
		    mBeanA.setHier(hier);
		    mBeanA.setMois(mois);
		    mBeanA.setNom(nom);
		    mBeanA.setPj(pj);
		    mBeanA.setPoolTeamId(datastoreId);
		    mBeanA.setPosition(position);
		    mBeanA.setProjection(projection);
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
		    // on persist le datastore/bean dans la MemCache

		    Key userPrefsKeyA = KeyFactory.createKey(nomBean, datastoreId);
		    memcache.put(userPrefsKeyA, mBeanA);

		    break;

		case "defenseur":
		    Defenseur mBeanD = new Defenseur();
		    nomBean = "Defenseur";

		    mBeanD.setAge(age);
		    mBeanD.setAide_overtime(aide_overtime);
		    mBeanD.setBirthday(birthday);
		    mBeanD.setBlanchissage(blanchissage);
		    mBeanD.setBut_victoire(but_victoire);
		    mBeanD.setCan_be_rookie(can_be_rookie);
		    mBeanD.setClub_ecole(club_ecole);
		    mBeanD.setContrat(contrat);
		    mBeanD.setContrat_cours(contrat_cours);
		    mBeanD.setContrat_max_years(contrat_max_years);
		    mBeanD.setDate_calcul(date_calcul);
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
		    // on persist le datastore/bean dans la MemCache

		    Key userPrefsKeyD = KeyFactory.createKey(nomBean, datastoreId);
		    memcache.put(userPrefsKeyD, mBeanD);

		    break;
		case "gardien":
		    Gardien mBeanG = new Gardien();
		    nomBean = "Gardien";

		    mBeanG.setAge(age);
		    mBeanG.setAide_overtime(aide_overtime);
		    mBeanG.setBirthday(birthday);
		    mBeanG.setBlanchissage(blanchissage);
		    mBeanG.setBut_victoire(but_victoire);
		    mBeanG.setCan_be_rookie(can_be_rookie);
		    mBeanG.setClub_ecole(club_ecole);
		    mBeanG.setContrat(contrat);
		    mBeanG.setContrat_cours(contrat_cours);
		    mBeanG.setContrat_max_years(contrat_max_years);
		    mBeanG.setDate_calcul(date_calcul);
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
		    // on persist le datastore/bean dans la MemCache

		    Key userPrefsKeyG = KeyFactory.createKey(nomBean, datastoreId);
		    memcache.put(userPrefsKeyG, mBeanG);

		    break;
		}
		break;
	    case 1:
		Recrue mBeanR = new Recrue();
		nomBean = "Recrue";
		mBeanR.setAge(age);
		mBeanR.setAide_overtime(aide_overtime);
		mBeanR.setBirthday(birthday);
		mBeanR.setBlanchissage(blanchissage);
		mBeanR.setBut_victoire(but_victoire);
		mBeanR.setCan_be_rookie(can_be_rookie);
		mBeanR.setClub_ecole(club_ecole);
		mBeanR.setContrat(contrat);
		mBeanR.setContrat_cours(contrat_cours);
		mBeanR.setContrat_max_years(contrat_max_years);
		mBeanR.setDate_calcul(date_calcul);
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
		// on persist le datastore/bean dans la MemCache

		Key userPrefsKeyG = KeyFactory.createKey(nomBean, datastoreId);
		memcache.put(userPrefsKeyG, mBeanR);

		break;
	    }

	}

    }

    @Override
    public void cronJobPlayersAvailableForDraft(int poolId) throws DAOException {

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

	    preparedStatement = initialisationRequetePreparee(connexion, GET_PLAYERS_FOR_DRAFT, false, poolId);
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

		String nomBean = "Players_" + poolId;

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

		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		Key userPrefsKey = KeyFactory.createKey(nomBean, _id);
		memcache.put(userPrefsKey, mBean);

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Entity entity = mapEntityFromBeanToDatastore(mBean, poolId, _id);
		datastore.put(entity);

	    }

	} catch (SQLException e) {

	    throw new DAOException(e);

	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);

	}

    }

    @Override
	public void persistPlayerPick(int playerId, int salaireId, int poolId, int teamId, int clubEcoleId,int acquire_years) {
		
    	Connection connexion = null;
    	PreparedStatement preparedStatement = null;
    	
    	try {
    	    connexion = daoFactory.getConnection();
    	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_PLAYERS_AFTER_DRAFT_PICK, false, poolId,teamId,1,acquire_years,salaireId,clubEcoleId,playerId);
    	    preparedStatement.executeUpdate();

    	} catch (SQLException e) {
    	    throw new DAOException(e);
    	} finally {
    	    fermeturesSilencieuses(preparedStatement, connexion);
    	}

        
		
	}
    
    private Entity mapEntityFromBeanToDatastore(Players mBean, int poolId, int players_id) {
	String birthday=null;
	String date_calcul=null;
	String nomEntity = "Players_" + poolId;
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	try {
	    birthday = dateFormat.format(mBean.getBirthday());
	    date_calcul = dateFormat.format(mBean.getDate_calcul());
	} catch (Exception ex) {
	}

	Entity mEntity = new Entity(nomEntity, players_id);

	mEntity.setProperty("age", mBean.getAge());
	mEntity.setProperty("aide_overtime", mBean.getAide_overtime());
	mEntity.setProperty("birthday", birthday);
	mEntity.setProperty("blanchissage", mBean.getBlanchissage());
	mEntity.setProperty("but_victoire", mBean.getBut_victoire());
	mEntity.setProperty("can_be_rookie", mBean.getCan_be_rookie());
	mEntity.setProperty("club_ecole", mBean.getClub_ecole());
	mEntity.setProperty("contrat", mBean.getContrat());
	mEntity.setProperty("contrat_cours", mBean.getContrat_cours());
	mEntity.setProperty("contrat_max_years", mBean.getContrat_max_years());
	mEntity.setProperty("date_calcul", date_calcul);
	mEntity.setProperty("acquire_years", mBean.getAcquire_years());
	mEntity.setProperty("hier", mBean.getHier());
	mEntity.setProperty("mois", mBean.getMois());
	mEntity.setProperty("nom", mBean.getNom());
	mEntity.setProperty("pj", mBean.getPj());
	mEntity.setProperty("players_id", mBean.getPlayers_id());
	mEntity.setProperty("position", mBean.getPosition());
	mEntity.setProperty("projection", mBean.getProjection());
	mEntity.setProperty("pts", mBean.getPts());
	mEntity.setProperty("salaire_contrat", mBean.getSalaire_contrat());
	mEntity.setProperty("salaire_draft", mBean.getSalaire_draft());
	mEntity.setProperty("semaine", mBean.getSemaine());
	mEntity.setProperty("take_proj", mBean.getTake_proj());
	mEntity.setProperty("team_id", mBean.getTeam_id());
	mEntity.setProperty("team_was_update", mBean.getTeam_was_update());
	mEntity.setProperty("teamOfPlayer", mBean.getTeamOfPlayer());
	mEntity.setProperty("type_contrat", mBean.getType_contrat());
	mEntity.setProperty("years_1", mBean.getYears_1());
	mEntity.setProperty("years_2", mBean.getYears_2());
	mEntity.setProperty("years_3", mBean.getYears_3());
	mEntity.setProperty("years_4", mBean.getYears_4());
	mEntity.setProperty("years_5", mBean.getYears_5());

	return mEntity;

    }

	
}
