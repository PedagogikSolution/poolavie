package com.pedagogiksolution.dao;

import java.sql.ResultSet;

import com.pedagogiksolution.datastorebeans.Classement;


public interface ClassementDao {
    
    void createClassementTable(int poolID) throws DAOException;
    void createClassementArchiveTable(int poolID) throws DAOException;
    void insertTeamInClassement(String nomDuTeam, int teamID, int poolID, int years);
    Boolean checkIfTeamAlreadyCreate(int teamID, int poolID);
    void updateTeamInClassement(String nomDuTeam, int teamID, int poolID);
    Classement cronJobGetClassementbyPoolId(int poolId);
   
    
    

}
