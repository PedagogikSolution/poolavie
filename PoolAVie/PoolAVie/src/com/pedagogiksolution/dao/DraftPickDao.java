package com.pedagogiksolution.dao;

import com.pedagogiksolution.beans.TradeBeanTemp;
import com.pedagogiksolution.datastorebeans.Pool;



public interface DraftPickDao {
    
    void createDraftPickTable(int poolID) throws DAOException;

    void insertPickByTeam(int poolID, int nombreEquipe, int numPickByTeam);
    
    void cronJobGetDraftPickbyPoolId(int poolId, int numberOfTeam, String fromTag) throws DAOException;

    void getDraftPickForDatastoreFromPoolIdAndTeamNumber(String poolID, String counter)throws DAOException;

    TradeBeanTemp getNameOfTeam(String poolID, int toInt, Pool mBeanPool)throws DAOException;

    Boolean checkIfPicksStillInTeam(int poolId, int teamId, int pickId)throws DAOException;
    
    

}
