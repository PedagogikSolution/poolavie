package com.pedagogiksolution.dao;

import java.util.ArrayList;

public interface TeamsDao {

	void addTeamsFromApiToDatabase(ArrayList<Integer> id, ArrayList<String> teamNameAbbreviation) throws DAOException;;

	void truncateTeamsFromApiDatabase() throws DAOException;;
    
    
    
    

}
