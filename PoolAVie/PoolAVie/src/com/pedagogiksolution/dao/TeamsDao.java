package com.pedagogiksolution.dao;

import java.util.ArrayList;

import com.pedagogiksolution.beans.ApiTeams;

public interface TeamsDao {

	void addTeamsFromApiToDatabase(ArrayList<Integer> id, ArrayList<String> teamNameAbbreviation) throws DAOException;;

	void truncateTeamsFromApiDatabase() throws DAOException;

	ApiTeams getAllTeamsId() throws DAOException;
    
    
    
    

}
