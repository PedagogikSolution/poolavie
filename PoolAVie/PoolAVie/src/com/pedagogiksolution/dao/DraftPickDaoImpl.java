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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.utils.EMF;

public class DraftPickDaoImpl implements DraftPickDao {

    private static final String CREATE_DRAFT_PICK = "CREATE TABLE draft_pick? LIKE draft_pick";
    private static final String INSERT_DRAFT_PICK = "INSERT INTO draft_pick? (team_id,pick_no,original_team_id) VALUE (?,?,?)";
    private static final String GET_DRAFT_PICK_BY_POOL_ID = "SELECT * FROM draft_pick? WHERE team_id=? ORDER BY _id ASC";

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
    public DraftPick cronJobGetDraftPickbyPoolId(int poolId, int numberOfTeam) throws DAOException {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	List<Integer> team_id = new ArrayList<Integer>();
	List<Integer> pick_no = new ArrayList<Integer>();
	List<Integer> original_team_id = new ArrayList<Integer>();
	List<Integer> orderForTheRound = new ArrayList<Integer>();
	// instanciation du bean Utilisateur
	DraftPick mBean = new DraftPick();

	try {
	    connexion = daoFactory.getConnection();
	    for (int i = 1; i < (numberOfTeam + 1);) {

		// on crée le beans avec le processus JPA qui va créer le datastore en même temps
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;
		try {
		    em = emf.createEntityManager();

		    String datastoreId = String.valueOf(poolId) + "_" + i;

		    
		    preparedStatement = initialisationRequetePreparee(connexion, GET_DRAFT_PICK_BY_POOL_ID, false, poolId);
		    rs = preparedStatement.executeQuery();

		    while (rs.next()) {

			int m_team_id = (rs.getInt("team_id"));
			team_id.add(m_team_id);

			int m_pick_no = (rs.getInt("pick_no"));
			pick_no.add(m_pick_no);

			int m_original_team_id = (rs.getInt("original_team_id"));
			original_team_id.add(m_original_team_id);

			int m_orderForTheRound = (rs.getInt("orderForTheRound"));
			orderForTheRound.add(m_orderForTheRound);

		    }

		    mBean.setPoolTeamId(datastoreId);
		    mBean.setPick_no(pick_no);
		    mBean.setTeam_id(team_id);
		    mBean.setOriginal_pick_id(original_team_id);
		    mBean.setOrderForTheRound(orderForTheRound);

		    // on persiste dans le datastore via notre EntityManager
		    em.persist(mBean);
		    // on persist le datastore/bean dans la MemCache
		    MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		    Key userPrefsKey = KeyFactory.createKey("DraftPick", datastoreId);
		    memcache.put(userPrefsKey, mBean);
		    
		    i++;
		    return mBean;

		} finally {

		    // on ferme le manager pour libérer la mémoire
		    if (em != null) {
			em.close();

		    }
		}

	    }

	} catch (SQLException e) {

	    throw new DAOException(e);

	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);

	}
	return null;

    }

}
