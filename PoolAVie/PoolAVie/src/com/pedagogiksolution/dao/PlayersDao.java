package com.pedagogiksolution.dao;

import javax.servlet.http.HttpServletRequest;


public interface PlayersDao {

    void createPlayersTable(int poolID) throws DAOException;

    void createPlayersArchiveTable(int poolID) throws DAOException;

    void cronJobGetPlayersbyPoolIdAndPosition(int poolId, int numberOfTeam, String positionString, int recrue, String fromTag) throws DAOException;

    void cronJobPlayersAvailableForDraft(int poolId) throws DAOException;

    void persistPlayerPick(int playerId, int salaireId, int poolId, int teamId, int clubEcoleId, int acquire_years) throws DAOException;

    void getPlayersForDatastoreFromPoolIdAndTeamNumber(String poolID, String counter, String string, int i) throws DAOException;

    void getPlayersForDatastoreFromPoolId(String poolID) throws DAOException;

    void getPlayersThatCanBeSign(int teamId, int poolId, HttpServletRequest req) throws DAOException;

    void signPlayerAfterDraft(int teamId, int poolId, String draft_player_id, String salaire, int numberOfYear) throws DAOException;

    void persistPlayerPickRookie(int playersId, int salaireId, int poolId, int teamId, int clubEcoleId, int acquireYearsId)throws DAOException;

    int getPj(int i)throws DAOException;
    
    int getBut(int i)throws DAOException;
    
    int getPasse(int i)throws DAOException;
    
    int getPts(int i)throws DAOException;


   

}
