package com.pedagogiksolution.dao;


public interface PlayersDao {

    void createPlayersTable(int poolID) throws DAOException;

    void createPlayersArchiveTable(int poolID) throws DAOException;

    void cronJobGetPlayersbyPoolIdAndPosition(int poolId, int numberOfTeam, String positionString, int recrue, String fromTag) throws DAOException;

    void cronJobPlayersAvailableForDraft(int i) throws DAOException;

    void persistPlayerPick(int playerId, int salaireId, int poolId, int teamId, int clubEcoleId, int acquire_years) throws DAOException;;

    void getPlayersForDatastoreFromPoolIdAndTeamNumber(String poolID, String counter, String string, int i) throws DAOException;;

}
