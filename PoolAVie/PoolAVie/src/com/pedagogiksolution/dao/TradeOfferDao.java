package com.pedagogiksolution.dao;

import com.pedagogiksolution.beans.TradeBeans;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;


public interface TradeOfferDao {
    
    void createTradeOfferTable(int poolID) throws DAOException;
    void createTradeOfferArchiveTable(int poolID) throws DAOException;
    void insertTradeOffer(String poolID, int teamMakingOfferId, int teamReceivingOfferId, String t1j1, String t1j2, String t1j3, String t1j4, String t1j5, String t1j6, String t1j7, String t2j1, String t2j2, String t2j3, String t2j4, String t2j5, String t2j6, String t2j7, String t1p1, String t1p2, String t1p3, String t2p1, String t2p2, String t2p3, int cashMakingOffer, int cashReceivingOffer, int i, int annee,
	    String messageOffre) throws DAOException;
    TradeBeans getTradeOfferReceived(Pool mBeanPool,String poolID, int teamId, int i)throws DAOException;
    int getNumberTradeReceived(String poolID, int teamId)throws DAOException;
    int getNumberTradeMade(String poolID, int teamId)throws DAOException;
    TradeBeans getTradeOfferMade(Pool mBeanPool, String poolID, int teamId, int i)throws DAOException;
    TradeBeans showOfferX(Pool mBeanPool, Utilisateur mBeanUser, int trade_id, PlayersDao playersDao, DraftPickDao draftPickDao)throws DAOException;
    void cancelOffreX(int trade_id_int, Pool mBeanPool)throws DAOException;
    
    
}
