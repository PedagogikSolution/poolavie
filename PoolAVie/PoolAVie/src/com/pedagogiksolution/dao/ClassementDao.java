package com.pedagogiksolution.dao;


public interface ClassementDao {
    
    void createClassementTable(int poolID) throws DAOException;
    void createClassementArchiveTable(int poolID) throws DAOException;
    void insertTeamInClassement(String nomDuTeam, int teamID, int poolID, int years);
   
    
    

}
