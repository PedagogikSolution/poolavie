package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ClassementDaoImpl implements ClassementDao {

    private static final String CREATE_CLASSEMENT = "CREATE TABLE classement_? LIKE classement";
    private static final String CREATE_CLASSEMENT_ARCHIVES = "CREATE TABLE classement_archive_? LIKE classement";
    private static final String INSERT_TEAM_CLASSEMENT = "INSERT INTO classement_? (equipe,team_id,pool_id,year_of_the_standing) VALUE (?,?,?,?)";
    private static final String CHECK_IF_TEAM_EXIST = "SELECT * FROM classement_? WHERE team_id=?";
    private static final String UPDATE_TEAM_CLASSEMENT = "UPDATE classement_? SET equipe=? WHERE team_id=?";
    private DAOFactory daoFactory;

    ClassementDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    @Override
    public void createClassementTable(int poolID) throws DAOException {
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_CLASSEMENT, false, poolID);
	    preparedStatement.execute();
	    	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public void createClassementArchiveTable(int poolID) throws DAOException {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CREATE_CLASSEMENT_ARCHIVES, false, poolID);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public void insertTeamInClassement(String nomDuTeam, int teamID, int poolID,int years) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, INSERT_TEAM_CLASSEMENT, false, poolID,nomDuTeam,teamID,poolID,years);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

    @Override
    public Boolean checkIfTeamAlreadyCreate(int teamID, int poolID) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, CHECK_IF_TEAM_EXIST, false, poolID,teamID);
	    ResultSet rs = preparedStatement.executeQuery();
	    
	    if(rs.first()){
		return true;
	    } else {
		return false;
	    }
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
    }

    @Override
    public void updateTeamInClassement(String nomDuTeam, int teamID, int poolID) {
	Connection connexion = null;
	PreparedStatement preparedStatement = null;

	try {
	    connexion = daoFactory.getConnection();
	    preparedStatement = initialisationRequetePreparee(connexion, UPDATE_TEAM_CLASSEMENT, false, poolID,nomDuTeam,teamID);
	    preparedStatement.execute();
	    
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
    }

   

}
