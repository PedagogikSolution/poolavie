package com.pedagogiksolution.dao;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.beans.TradeBeans;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;


public interface TradeMadeDao {
    
    void createTradeMadeTable(int poolID) throws DAOException;
    void createTradeMadeArchiveTable(int poolID) throws DAOException;
    void insertTradeMade(int poolId, int trade_id)throws DAOException;
    int getNumberTradeMadeByMe(String poolID, int teamId)throws DAOException;
    TradeBeans getTradeMadeByMe(Pool mBeanPool, String poolID, int teamId, int i)throws DAOException;
    int getNumberTradeMadeByAll(String poolID)throws DAOException;
    TradeBeans getTradeMadeByAll(Pool mBeanPool, String poolID, int teamId, int i)throws DAOException;
    TradeBeans showOfferX(Pool mBeanPool, Utilisateur mBeanUser, int trade_id, PlayersDao playersDao, DraftPickDao draftPickDao);
	void insertionDansArchives(HttpServletRequest req);
	void resetTradeMade(String poolID);
    
    

}
