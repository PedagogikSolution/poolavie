package com.pedagogiksolution.dao;



import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.beans.TradeBeanTemp;
import com.pedagogiksolution.datastorebeans.Pool;




public interface PlayersDao {

    void createPlayersTable(int poolID) throws DAOException;

    void createPlayersArchiveTable(int poolID) throws DAOException;

    void cronJobGetPlayersbyPoolIdAndPosition(int poolId, int numberOfTeam, String positionString, int recrue, String fromTag) throws DAOException;

    void cronJobPlayersAvailableForDraft(int poolId) throws DAOException;

    void persistPlayerPick(int playerId, int salaireId, int poolId, int teamId, int clubEcoleId) throws DAOException;

    void getPlayersForDatastoreFromPoolIdAndTeamNumber(String poolID, String counter, String string, int i) throws DAOException;

    void getPlayersForDatastoreFromPoolId(String poolID) throws DAOException;

    void getPlayersThatCanBeSign(int teamId, int poolId, HttpServletRequest req, SalaireDao salaireDao) throws DAOException;

    void signPlayerAfterDraft(int teamId, int poolId, String draft_player_id, String salaire, int numberOfYear) throws DAOException;

    void persistPlayerPickRookie(int playersId, int salaireId, int poolId, int teamId, int clubEcoleId, int yearsOfC)throws DAOException;

    int getPj(int i, int poolId)throws DAOException;
    
    int getBut(int i, int poolId)throws DAOException;
    
    int getPasse(int i, int poolId)throws DAOException;
    
    int getPts(int i, int poolId)throws DAOException;

    TradeBeanTemp getPlayersById(String poolID, int toInt, int i) throws DAOException;

    Boolean checkIfPlayersStillInTeam(int poolId, int teamId, int playerId) throws DAOException;

    void makeTrade(Pool mBeanPool, int teamId1, int teamId2, int playerId2) throws DAOException;

	void getPlayersThatCanBeRachatAfterSeason(int teamId, int poolId, HttpServletRequest req, int cycleAnnuel) throws DAOException;

	Boolean getUniquePlayersById(String player_id, String poolID, int teamId, HttpServletRequest req, String position, int checkForMoyenne) throws DAOException;
	
	void removePlayersFromTeamAfterRachat(int playersId, String poolID) throws DAOException;

	void updateAgeForRookie(HttpServletRequest req) throws DAOException;

	void getRookieThatCanDropInClubEcoleAfterSeason(int teamId, int poolId, HttpServletRequest req) throws DAOException;

	String getPlayersName(String player_id, String poolID) throws DAOException;

	String putPlayersInClubEcole(int playersId, String poolID) throws DAOException;

	void insertionDansArchives(HttpServletRequest req) throws DAOException;

	void dropPlayersJaAndX(String poolID) throws DAOException;

	void updateProjection(String poolID) throws DAOException;

	void setCanBeRookie(String poolID) throws DAOException;

	void setTakeProj(String poolID) throws DAOException;

	void migratePtsToLastYear(String poolID) throws DAOException;

	void moveYearsToYearsContract(String poolID) throws DAOException;

	void setSalaireDraft(String poolID) throws DAOException;

	int getTotalSalaireNow(String poolID, int i) throws DAOException;

	int getNbAttaquant(String poolID, int i) throws DAOException;

	int getNbDefenseur(String poolID, int i) throws DAOException;

	int getNbGardien(String poolID, int i) throws DAOException;

	int getNbRookie(String poolID, int i) throws DAOException;

	int getNbContrat(String poolID, int i) throws DAOException;

	void dropPlayersAandB(String poolID) throws DAOException;

	void getRookieInClubEcole(int teamId, int poolId, HttpServletRequest req) throws DAOException;

	void dropRookie(int poolId, String players_id) throws DAOException;

	int monterRookie(int poolId, String players_id, int numberOfYearSign, String salaire, PlayersDao playersDao, String years_1, String years_2, String years_3, String years_4, String years_5)throws DAOException;

	void setSalaireForRookie(String poolID, SalaireDao salaireDao) throws DAOException;

	int getYears0(int poolId, String players_id) throws DAOException;

	void dropPlayersCetD(HttpServletRequest req, String poolID) throws DAOException;

	int checkIfPlayersWillHaveMoreThan25DuringContract(int poolId, int playersId) throws DAOException;

	void updateCAfterRetro(int playersId, String poolID, int teamId, int checker) throws DAOException;

	void resetStatsToZeroForNewYear(String poolID, String years_for_archive)  throws DAOException;

	String getYears1(int poolId, String players_id) throws DAOException;

	void addPlayer(String poolID, String nom, String team, String position, String birthday) throws DAOException;

	void setAgeForNewPlayer(HttpServletRequest req) throws DAOException;

	void putNewPlayersInDatastore(String poolID) throws DAOException;

	void addPlayersFromSportFeed(ArrayList<Integer> id, ArrayList<String> nom, ArrayList<String> abbreviation) throws DAOException;

	int getLastIdFromApi() throws DAOException;

	void truncateTeamsFromApiDatabase() throws DAOException;

	void truncatePlayersTemplate() throws DAOException;

	void addPlayersFromApiToPlayersTemplate() throws DAOException;

	NonSessionPlayers getAllPlayersID() throws DAOException;

	void updateBirthdayAndPosition(int playerId, String positionAbbreviation, String birthDate) throws DAOException;

	void updateStatsFromNHLAPI(int playerId, String games, String assists, String goals, String points, String wins, String shutouts,
			String ot) throws DAOException;

	void updateStatsFromNHLAPI2(int playerId, String games, String points, String wins, String shutouts, String ot) throws DAOException;

	void updateStatsFromNHLAPI3(int playerId, String games, String points, String wins, String shutouts, String ot) throws DAOException;

	void deleteOldPlayers() throws DAOException;

	void deletePlayersWithNoTeam(int i) throws DAOException;

	void addPlayersNotThere(int i) throws DAOException;

	void updateStats(int i) throws DAOException;

	void updateTeam(int i) throws DAOException;

	void updatePlayersWithNoStats() throws DAOException;
	

	

	



   

}
