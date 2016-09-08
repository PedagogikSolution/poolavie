package com.pedagogiksolution.dao;



public interface DraftPickDao {
    
    void createDraftPickTable(int poolID) throws DAOException;

    void insertPickByTeam(int poolID, int nombreEquipe, int numPickByTeam);
    
    void cronJobGetDraftPickbyPoolId(int poolId, int numberOfTeam, String fromTag) throws DAOException;

    void getDraftPickForDatastoreFromPoolIdAndTeamNumber(String poolID, String counter);
    
    

}
