package com.pedagogiksolution.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pedagogiksolution.beans.TradeBeanTemp;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Pool;


public interface DraftDao {
    
    void createDraftTable(int poolID) throws DAOException;
    void createDraftArchiveTable(int poolID) throws DAOException;
    void populateFirstYearsDraft(int poolID, List<Integer> permutation, Pool mBeanPool) throws DAOException;
    DraftRound setDraftRoundOrder(int poolId) throws DAOException;
	void persistPlayerPick(String nom, int currentPickId, int poolId) throws DAOException;
	void insertionDansArchives(HttpServletRequest req) throws DAOException;
	void resetDraft(String poolID, String years) throws DAOException;
	void populationDraftRoundFromDraftPick(String poolID, List<Integer> classementInverseLastYears, String years, HttpServletRequest req) throws DAOException;
	void putDatabaseInDatastore(String poolID) throws DAOException;
	TradeBeanTemp getRoundAndNameOfTeam(String poolID, int toInt, Pool mBeanPool) throws DAOException;
	Boolean checkIfPickStillInTeam(int poolId, int teamId, int pickId) throws DAOException;
	void makeTrade(int poolId, int teamId1, int roundId2, Pool mBeanPool) throws DAOException;
	void deleteDraftPickWhenFinishPicking(String poolID, String teamID, String currentPick) throws DAOException;
    
    

}
