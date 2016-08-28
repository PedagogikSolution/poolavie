package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SalaireDaoImpl implements SalaireDao {

    private static final String CREATE_SALAIRE = "CREATE TABLE salaire? LIKE salaire";
    private static final String INSERT_SALAIRE = "INSERT INTO salaire? SELECT * FROM salaire";
    private DAOFactory daoFactory;

    SalaireDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createSalaireTable(int poolID) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_SALAIRE, false, poolID);
	    preparedStatement.execute();
	    preparedStatement = initialisationRequetePreparee(connexion, INSERT_SALAIRE, false, poolID);
	    preparedStatement.execute();
	    	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

   

}
