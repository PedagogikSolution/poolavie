package com.pedagogiksolution.dao;


public interface TradeOfferDao {
    
    void createTradeOfferTable(int poolID) throws DAOException;
    void createTradeOfferArchiveTable(int poolID) throws DAOException;
    void insertTradeOffer(String poolID, int teamMakingOfferId, int teamReceivingOfferId, String t1j1, String t1j2, String t1j3, String t1j4, String t1j5, String t1j6, String t1j7, String t2j1, String t2j2, String t2j3, String t2j4, String t2j5, String t2j6, String t2j7, String t1p1, String t1p2, String t1p3, String t2p1, String t2p2, String t2p3, int cashMakingOffer, int cashReceivingOffer, int i, int annee,
	    String messageOffre) throws DAOException;
    
    

}
