package com.pedagogiksolution.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.datastorebeans.Classement;


public interface ClassementDao {
    
    void createClassementTable(int poolID) throws DAOException;
    void createClassementArchiveTable(int poolID) throws DAOException;
    void insertTeamInClassement(String nomDuTeam, int teamID, int poolID, int years) throws DAOException;
    Boolean checkIfTeamAlreadyCreate(int teamID, int poolID) throws DAOException;
    void updateTeamInClassement(String nomDuTeam, int teamID, int poolID) throws DAOException;
    Classement cronJobGetClassementbyPoolId(int poolId) throws DAOException;
    void updateStat(int poolId, int pj, int but, int passe, int pts, int teamId, int hier, int semaine, int mois)throws DAOException;
    void updateDifference(int poolId, int teamId, List<Long> mouvementArray)throws DAOException;
    void updateStat(int poolId, int pj, int but, int passe, int pts, int teamId)throws DAOException;
	void insertionDansArchives(HttpServletRequest req);
	void resetClassement(String poolID, String years);
   
    
    

}
