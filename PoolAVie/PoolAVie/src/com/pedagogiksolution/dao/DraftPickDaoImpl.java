package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.pedagogiksolution.beans.TradeBeanTemp;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.utils.EMF;

public class DraftPickDaoImpl implements DraftPickDao {

    private static final String CREATE_DRAFT_PICK = "CREATE TABLE draft_pick? LIKE draft_pick";
    private static final String INSERT_DRAFT_PICK = "INSERT INTO draft_pick? (team_id,pick_no,original_team_id) VALUE (?,?,?)";
    private static final String GET_DRAFT_PICK_BY_POOL_ID = "SELECT * FROM draft_pick? WHERE team_id=? ORDER BY _id ASC";
    private static final String GET_ROUND_BY_ID = "SELECT * FROM draft_pick? WHERE _id=?";
    private static final String GET_ROUND_BY_ID_AND_TEAM = "SELECT * FROM draft_pick? WHERE team_id=? AND _id=?";

    private DAOFactory daoFactory;

    DraftPickDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createDraftPickTable(int poolID) throws DAOException {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_DRAFT_PICK, false, poolID);
	    preparedStatement.execute();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void insertPickByTeam(int poolID, int nombreEquipe, int numPickByTeam) {
	// TODO insertion des picks de la nouvelle année selon les parametres du pool

	int teamInt;
	int pickInt;
	int maxTeamInt = nombreEquipe + 1;
	int maxPickInt = numPickByTeam + 1;

	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();

	    for (teamInt = 1; teamInt < maxTeamInt; teamInt++) {

		for (pickInt = 1; pickInt < maxPickInt; pickInt++) {

		    preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_PICK, false, poolID, teamInt, pickInt, teamInt);
		    preparedStatement.executeUpdate();

		}

	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void cronJobGetDraftPickbyPoolId(int poolId, int numberOfTeam, String fromTag) throws DAOException {

	for (int i = 1; i < (numberOfTeam + 1); i++) {
	    
	    Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("/TaskQueueCreationPool")
			.param("counter",String.valueOf(i))
			.param("poolID",String.valueOf(poolId))
			.param("fromTag", fromTag)
			
			);
	    
	}

    }

    @Override
    public void getDraftPickForDatastoreFromPoolIdAndTeamNumber(String poolID, String counter) {
	Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;
	    DraftPick mBean = new DraftPick();
	    List<Long> team_id = new ArrayList<Long>();
	    List<Long> pick_no = new ArrayList<Long>();
	    List<Long> original_team_id = new ArrayList<Long>();
	    List<Long> orderForTheRound = new ArrayList<Long>();
	    List<String> teamNameOriginalPick = new ArrayList<String>();
	    String datastoreId;
	    
	    int poolId = Integer.parseInt(poolID);

	    
	    

	    try {

		connexion = daoFactory.getConnection();

		// instanciation du bean Utilisateur

		datastoreId = poolID + "_" + counter;

		preparedStatement = initialisationRequetePreparee(connexion, GET_DRAFT_PICK_BY_POOL_ID, false, poolId, counter);
		rs = preparedStatement.executeQuery();

		while (rs.next()) {

		    int m_team_id = (rs.getInt("team_id"));
		    team_id.add(Long.valueOf(m_team_id));

		    int m_pick_no = (rs.getInt("pick_no"));
		    pick_no.add(Long.valueOf(m_pick_no));

		    int m_original_team_id = (rs.getInt("original_team_id"));
		    original_team_id.add(Long.valueOf(m_original_team_id));

		    int m_orderForTheRound = (rs.getInt("orderForTheRound"));
		    orderForTheRound.add(Long.valueOf(m_orderForTheRound));
		    
		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key clefDatastore = KeyFactory.createKey("Pool", String.valueOf(poolId));
		    Entity mEntity;
		    String nomTeam = null;
		    try {
			mEntity = datastore.get(clefDatastore);
			nomTeam = (String) mEntity.getProperty("nomTeam"+m_original_team_id);
			teamNameOriginalPick.add(nomTeam);
		    } catch (EntityNotFoundException e) {
			e.printStackTrace();

		    }
		    
		    
		    

		}

	    } catch (SQLException e) {

		throw new DAOException(e);

	    } finally {
		fermeturesSilencieuses(rs, preparedStatement, connexion);

	    }

	    mBean.setPoolTeamId(datastoreId);
	    mBean.setPick_no(pick_no);
	    mBean.setTeam_id(team_id);
	    mBean.setOriginal_pick_id(original_team_id);
	    mBean.setOrderForTheRound(orderForTheRound);
	    mBean.setTeamNameOriginalPick(teamNameOriginalPick);

	    // on crée le beans avec le processus JPA qui va créer le datastore en même temps
	    EntityManagerFactory emf = EMF.get();
	    EntityManager em = null;
	    try {
		em = emf.createEntityManager();

		// on persiste dans le datastore via notre EntityManager
		em.persist(mBean);

	    } finally {

		// on ferme le manager pour libérer la mémoire
		if (em != null) {
		    em.close();

		}
	    }
	    
	    
	   

	}

    
    @Override
    public TradeBeanTemp getNameOfTeam(String poolID, int toInt,Pool mBeanPool) throws DAOException {
	Connection connexion = null;
	ResultSet rs =null;
	PreparedStatement preparedStatement = null;
	TradeBeanTemp mBean = new TradeBeanTemp();
	
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_ROUND_BY_ID, false,Integer.parseInt(poolID), toInt);
	    rs = preparedStatement.executeQuery();
	    while (rs.next()) {
		int round_temp = rs.getInt("pick_no");
		int from_temp = rs.getInt("original_team_id");
		String from_temp2 = null;
		switch (from_temp) {
		
		case 1:
		    from_temp2 = mBeanPool.getNomTeam1();
		    break;
		case 2:
		    from_temp2 = mBeanPool.getNomTeam2();
		    break;
		case 3:
		    from_temp2 = mBeanPool.getNomTeam3();
		    break;
		case 4:
		    from_temp2 = mBeanPool.getNomTeam4();
		    break;
		case 5:
		    from_temp2 = mBeanPool.getNomTeam5();
		    break;
		case 6:
		    from_temp2 = mBeanPool.getNomTeam6();
		    break;
		case 7:
		    from_temp2 = mBeanPool.getNomTeam7();
		    break;
		case 8:
		    from_temp2 = mBeanPool.getNomTeam8();
		    break;
		case 9:
		    from_temp2 = mBeanPool.getNomTeam9();
		    break;
		case 10:
		    from_temp2 = mBeanPool.getNomTeam10();
		    break;
		case 11:
		    from_temp2 = mBeanPool.getNomTeam11();
		    break;
		case 12:
		    from_temp2 = mBeanPool.getNomTeam12();
		    break;
		}
		String round_temp2 = Integer.toString(round_temp);

		mBean.setRoundPick(round_temp2);
		mBean.setFromPick(from_temp2);
	    }
	    
	    return mBean;

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs,preparedStatement, connexion);
	}
    }

    
    @Override
    public Boolean checkIfPicksStillInTeam(int poolId, int teamId, int pickId) throws DAOException {
	Connection connexion = null;
	ResultSet rs =null;
	PreparedStatement preparedStatement = null;
	try {
	    connexion = daoFactory.getConnection();
	    
		preparedStatement = initialisationRequetePreparee(connexion, GET_ROUND_BY_ID_AND_TEAM, false,poolId, teamId,pickId);
	   
	    rs = preparedStatement.executeQuery();
	    if (rs.next()) {
		return true;
	    }
	    
	    return false;

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs,preparedStatement, connexion);
	}
    }
	
    }




