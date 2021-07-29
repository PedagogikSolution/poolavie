package com.pedagogiksolution.dao;

import static com.pedagogiksolution.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.pedagogiksolution.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;




public class TeamsDaoImpl implements TeamsDao {

	
   
	private static final String ADD_TEAMS_FROM_NHL_API = "INSERT INTO api_teams (_id,abbreviation) VALUES (?,?)";
	private static final String TRUNCATE_TEAMS_FROM_NHL_API = "TRUNCATE api_teams";
	private DAOFactory daoFactory;

    TeamsDaoImpl(DAOFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

	@Override
	public void addTeamsFromApiToDatabase(ArrayList<Integer> id, ArrayList<String> teamNameAbbreviation) {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
			
			for (int i = 0; i < id.size(); i++) {
			preparedStatement = initialisationRequetePreparee(connexion, ADD_TEAMS_FROM_NHL_API, false,
					id.get(i), teamNameAbbreviation.get(i));
			preparedStatement.executeUpdate();
			
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
	}

	@Override
	public void truncateTeamsFromApiDatabase() {
		
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
			
			
			preparedStatement = initialisationRequetePreparee(connexion, TRUNCATE_TEAMS_FROM_NHL_API, false);
			preparedStatement.executeUpdate();
			
		

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(preparedStatement, connexion);
		}
		
	}

   
   

}
