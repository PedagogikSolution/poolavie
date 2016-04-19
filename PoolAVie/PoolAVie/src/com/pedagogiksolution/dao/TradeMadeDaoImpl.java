package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TradeMadeDaoImpl implements TradeMadeDao {

    private static final String CREATE_TRADE_MADE = "CREATE TABLE trade_made? LIKE trade_made";
    private static final String CREATE_TRADE_MADE_ARCHIVES = "CREATE TABLE trade_made_archive_? LIKE trade_made";
    private DAOFactory daoFactory;

    TradeMadeDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createTradeMadeTable(int poolID) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_TRADE_MADE, false, poolID);
	    preparedStatement.execute();
	    	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public void createTradeMadeArchiveTable(int poolID) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_TRADE_MADE_ARCHIVES, false, poolID);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

  

   

}
