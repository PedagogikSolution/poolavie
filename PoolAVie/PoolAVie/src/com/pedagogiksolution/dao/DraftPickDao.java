package com.pedagogiksolution.dao;

import com.pedagogiksolution.datastorebeans.DraftPick;


public interface DraftPickDao {
    
    void createDraftPickTable(int poolID) throws DAOException;

    void insertPickByTeam(int poolID, int nombreEquipe, int numPickByTeam);
    
    DraftPick cronJobGetDraftPickbyPoolId(int poolId, int numberOfTeam) throws DAOException;
    
    

}
