package com.pedagogiksolution.dao;


public interface TradeOfferDao {
    
    void createTradeOfferTable(int poolID) throws DAOException;
    void createTradeOfferArchiveTable(int poolID) throws DAOException;
    
    

}
