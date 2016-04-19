package com.pedagogiksolution.dao;


public interface TradeMadeDao {
    
    void createTradeMadeTable(int poolID) throws DAOException;
    void createTradeMadeArchiveTable(int poolID) throws DAOException;
    
    

}
