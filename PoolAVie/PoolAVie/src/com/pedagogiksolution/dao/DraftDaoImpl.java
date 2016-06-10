package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DraftDaoImpl implements DraftDao {

    private static final String CREATE_DRAFT = "CREATE TABLE draft? LIKE draft";
    private static final String CREATE_DRAFT_ARCHIVES = "CREATE TABLE draft_archive_? LIKE draft";
    private static final String INSERT_DRAFT_FIRST_YEAR = "INSERT INTO draft? (draft_pick_no,team_id,ronde,team_count,pool_id,follow_up) VALUE(?,?,?,?,?,?)";
    private DAOFactory daoFactory;

    DraftDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createDraftTable(int poolID) throws DAOException {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_DRAFT, false, poolID);
	    preparedStatement.execute();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void createDraftArchiveTable(int poolID) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_DRAFT_ARCHIVES, false, poolID);
	    preparedStatement.execute();

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

    @Override
    public void populateFirstYearsDraft(int poolId, List<Integer> permutation) throws DAOException {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	try {

	    connexion = daoFactory.getConnection();

	    int draft_pick_no = 1;
	    int ronde = 1;

	    for (int teamId : permutation) {

		preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false,poolId,draft_pick_no, teamId, ronde, ronde, poolId, 0);
		preparedStatement.execute();
		draft_pick_no++;
	    }
	    
	    ronde++;

	    for (ronde = 2; ronde < 32; ronde++) {

		Collections.reverse(permutation);
		for (int teamId : permutation) {

		    preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false,poolId,draft_pick_no, teamId, ronde, ronde, poolId, 0);
		    preparedStatement.execute();
		    draft_pick_no++;
		}

	    }

	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}

    }

}
