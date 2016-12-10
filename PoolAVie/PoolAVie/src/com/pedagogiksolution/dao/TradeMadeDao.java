package com.pedagogiksolution.dao;


public interface TradeMadeDao {
    
    void createTradeMadeTable(int poolID) throws DAOException;
    void createTradeMadeArchiveTable(int poolID) throws DAOException;
    void insertTradeMade(int poolId, int trade_id)throws DAOException;
    
    

}
