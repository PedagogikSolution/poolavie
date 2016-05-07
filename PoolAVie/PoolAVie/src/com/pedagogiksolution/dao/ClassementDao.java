package com.pedagogiksolution.dao;

import com.pedagogiksolution.datastorebeans.Classement;


public interface ClassementDao {
    
    void createClassementTable(int poolID) throws DAOException;
    void createClassementArchiveTable(int poolID) throws DAOException;
    void insertTeamInClassement(String nomDuTeam, int teamID, int poolID, int years) throws DAOException;
    Boolean checkIfTeamAlreadyCreate(int teamID, int poolID) throws DAOException;
    void updateTeamInClassement(String nomDuTeam, int teamID, int poolID) throws DAOException;
    Classement cronJobGetClassementbyPoolId(int poolId) throws DAOException;
   
    
    

}
