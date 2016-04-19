package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DraftPickDaoImpl implements DraftPickDao {

    private static final String CREATE_DRAFT_PICK = "CREATE TABLE draft_pick? LIKE draft_pick";
    private static final String INSERT_DRAFT_PICK = "INSERT INTO draft_pick? (team_id,pick_no,original_team_id) VALUE (?,?,?)";
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
	int maxTeamInt = nombreEquipe+1;
	int maxPickInt = numPickByTeam+1;
	
	Connection connexion = null;
	PreparedStatement preparedStatement = null;
	
	try {
	    connexion = daoFactory.getConnection();
	    
	    for (teamInt=1;teamInt<maxTeamInt;teamInt++){
		    
		    for(pickInt=1;pickInt<maxPickInt;pickInt++){
			
			preparedStatement = initialisationRequetePreparee(connexion, INSERT_DRAFT_PICK, false, poolID,teamInt,pickInt,teamInt);
			preparedStatement.executeUpdate();
						
		    }
		    
		}	    
	
	} catch (SQLException e) {
	    throw new DAOException(e);
	} finally {
	    fermeturesSilencieuses(preparedStatement, connexion);
	}
	
	
    }


}
