package com.pedagogiksolution.dao;


public interface PlayersDao {
    
    void createPlayersTable(int poolID) throws DAOException;
    void createPlayersArchiveTable(int poolID) throws DAOException;
    
    

}
