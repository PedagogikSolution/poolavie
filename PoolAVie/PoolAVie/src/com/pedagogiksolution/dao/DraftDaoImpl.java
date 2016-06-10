package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.pedagogiksolution.datastorebeans.Pool;

public class DraftDaoImpl implements DraftDao {

    private static final String CREATE_DRAFT = "CREATE TABLE draft? LIKE draft";
    private static final String CREATE_DRAFT_ARCHIVES = "CREATE TABLE draft_archive_? LIKE draft";
    private static final String INSERT_DRAFT_FIRST_YEAR = "INSERT INTO draft? (draft_pick_no,team_id,ronde,team_count,pool_id,follow_up,year_of_draft,equipe) VALUE(?,?,?,?,?,?,?,?)";
    private static final String GET_DRAFT_ROUND_ORDER = "SELECT * FROM draft? ORDER BY draft_pick_no ASC";
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
    public void populateFirstYearsDraft(int poolId, List<Integer> permutation, Pool mBeanPool) throws DAOException {

	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	try {

	    connexion = daoFactory.getConnection();

	    int draft_pick_no = 1;
	    int ronde = 1;
	    int years_of_the_draft=1;
	    String nomTeam = null;

	    for (int teamId : permutation) {
		switch(teamId){
		case 1: nomTeam =mBeanPool.getNomTeam1();
		    break;
		case 2: nomTeam =mBeanPool.getNomTeam2();
		    break;
		case 3: nomTeam =mBeanPool.getNomTeam3();
		    break;
		case 4: nomTeam =mBeanPool.getNomTeam4();
		    break;
		case 5: nomTeam =mBeanPool.getNomTeam5();
		    break;
		case 6: nomTeam =mBeanPool.getNomTeam6();
		    break;
		case 7: nomTeam =mBeanPool.getNomTeam7();
		    break;
		case 8: nomTeam =mBeanPool.getNomTeam8();
		    break;
		case 9: nomTeam =mBeanPool.getNomTeam9();
		    break;
		case 10: nomTeam =mBeanPool.getNomTeam10();
		    break;
		case 11: nomTeam =mBeanPool.getNomTeam11();
		    break;
		case 12: nomTeam =mBeanPool.getNomTeam12();
		    break;
		    
		}
		preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false,poolId,draft_pick_no, teamId, ronde, ronde, poolId, 0,years_of_the_draft,nomTeam);
		preparedStatement.execute();
		draft_pick_no++;
	    }
	    
	    ronde++;

	    for (ronde = 2; ronde < 32; ronde++) {

		Collections.reverse(permutation);
		for (int teamId : permutation) {

		    preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_FIRST_YEAR, false,poolId,draft_pick_no, teamId, ronde, ronde, poolId, 0,years_of_the_draft,nomTeam);
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

    @Override
    public ResultSet getDraftRoundOrder(int poolId) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, GET_DRAFT_ROUND_ORDER, false, poolId);
	    rs = preparedStatement.executeQuery();
	    return rs;
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(rs, preparedStatement, connexion);
	}
	
	
    }

}
