package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DraftDaoImpl implements DraftDao {

    private static final String CREATE_DRAFT = "CREATE TABLE draft? LIKE draft";
    private static final String CREATE_DRAFT_ARCHIVES = "CREATE TABLE draft_archive_? LIKE draft";
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

  

   

}
